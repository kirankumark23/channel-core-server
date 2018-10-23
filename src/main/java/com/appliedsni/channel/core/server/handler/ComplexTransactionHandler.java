package com.appliedsni.channel.core.server.handler;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.dao.ServerDao;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.MessageEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.Status;

public class ComplexTransactionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComplexTransactionHandler.class);
	
	private ServerDao mServerDao;
	
	public static ComplexTransactionHandler get(){
		return ChannelApplicationContext.get().getBean("complexTransactionHandler", ComplexTransactionHandler.class);
	}

	public ComplexTransactionHandler (ServerDao pServerDao){
		mServerDao = pServerDao;
	}
	
	public void handle(MessageEntity pMessage){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					for(Entry<String, String> entry : pMessage.getData().entrySet()){
						CustomThreadLocal.add(entry.getKey(), entry.getValue());
					}

					ComplexTransactionEntity ct = (ComplexTransactionEntity)mServerDao.get(ComplexTransactionEntity.class, pMessage.getIdKey());
					if(ct == null){
						ct = createTransaction(pMessage);
					}					
					
					while(true){
						ComplexTransactionStepEntity cts = getNextCTStep(ct);
						
						if(cts == null){
							ct.setStatus(Status.COMLETED);
							LOGGER.warn("Complex Transaction : {} : {}", ct.getIdKey(), ct.getStatus());
							break;
						}
						
						LOGGER.warn("Executing Complex Transaction Step : {}", cts.getSeqNo());

						cts.setExecutionStatus(Status.IN_PROGRESS);						
						SimpleTransactionHandler.get().handle(cts);
						
						if(!cts.getExecutionStatus().equals(Status.COMLETED)){
							LOGGER.warn("Last step in Complex Transaction is in {}", cts.getExecutionStatus());
							break;
						}			
						
						LOGGER.warn("Complex Transaction Step : {} : {}", cts.getSeqNo(), cts.getExecutionStatus());
					}
					
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
	}
	
	/**
	 * Create a transaction if it doesn't already exist
	 * 
	 * @param pMessage
	 */
	public ComplexTransactionEntity createTransaction(MessageEntity pMessage){
		ComplexTransactionProductEntity ctp;
		
		try{
			ctp = (ComplexTransactionProductEntity)mServerDao.find("from ComplexTransactionProductEntity where mType = ? and mStatus = ?",  pMessage.getType(), Status.OPEN).get(0);
			LOGGER.warn("Type : " + ctp.getName());
		}catch(Exception e){
			LOGGER.error("Unable to get product of type {}", pMessage.getType(), e);
			throw e;
		}

		ComplexTransactionEntity ct = new ComplexTransactionEntity(pMessage.getIdKey(), ctp);
		mServerDao.save(ct);
		
		List<Object> listCT = mServerDao.find("from ComplexTransactionProductStepEntity where mComplexTransaction = ?", ctp);
		for(Object objCT : listCT){
			ComplexTransactionProductStepEntity ctps = (ComplexTransactionProductStepEntity)objCT;
			LOGGER.debug("Info : " + ctps.getSeqNo());
									
			SimpleTransactionProductEntity stp = (SimpleTransactionProductEntity)mServerDao.get(SimpleTransactionProductEntity.class, ctps.getSimpleTransaction().getIdKey());
			LOGGER.debug("Type : " + stp.getName()); 
			
			SimpleTransactionEntity st = new SimpleTransactionEntity(UUID.randomUUID(), stp);
			mServerDao.save(st);
			
			ComplexTransactionStepEntity cts = new ComplexTransactionStepEntity(UUID.randomUUID(), ct, st, ctps);
			mServerDao.save(cts);
	
			List<Object> listST = mServerDao.find("from SimpleTransactionProductStepEntity where mSimpleTransaction = ?", stp);
			for(Object obj : listST){
				SimpleTransactionProductStepEntity stps = (SimpleTransactionProductStepEntity)obj;
				LOGGER.debug("Info : " + stps.getFunction());
				
				SimpleTransactionStepEntity sts = new SimpleTransactionStepEntity(UUID.randomUUID(), stps, st);
				mServerDao.save(sts);
			}
		}
		
		LOGGER.warn("Created Transactoin : {}", ct.getIdKey());
		
		return ct;
	}
	
	/**
	 * CTStep where executionStatus != COMPLETED order by SeqNo Limit 1
	 */
	public ComplexTransactionStepEntity getNextCTStep(ComplexTransactionEntity pCT){
		
		ComplexTransactionStepEntity cts = null;
		try{
			Query query = mServerDao.getSessionFactory().getCurrentSession().createQuery("from ComplexTransactionStepEntity "
					+ " where mExecutionStatus != :ExecutionStatus "
					+ " and mComplexTransaction = :ComplexTransaction"
					+ " order by mSeqNo ");
			query.setParameter("ExecutionStatus", Status.COMLETED);
			query.setParameter("ComplexTransaction", pCT);
			query.setMaxResults(1);
			
			cts = (ComplexTransactionStepEntity)query.list().get(0);
			LOGGER.warn("CTS : {}",  cts.getSeqNo());
		}catch(IndexOutOfBoundsException e){
			LOGGER.warn("No further step found in Complex Transaction {}", pCT.getIdKey());
		}
		
		return cts;
	}

}
