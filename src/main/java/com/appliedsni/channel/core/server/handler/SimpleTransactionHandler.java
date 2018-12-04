package com.appliedsni.channel.core.server.handler;

import java.util.Date;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;

import channel.client.dao.ServerDao;
import channel.client.function.Status;

public class SimpleTransactionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTransactionHandler.class);
	
	private ServerDao mServerDao;
	
	public static SimpleTransactionHandler get(){
		return ChannelApplicationContext.get().getBean("simpleTransactionHandler", SimpleTransactionHandler.class);
	}

	public SimpleTransactionHandler (ServerDao pServerDao){
		mServerDao = pServerDao;
	}
	
	public void handle(ComplexTransactionStepEntity pCTStep){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					pCTStep.getSimpleTransaction().setStatus(Status.IN_PROGRESS);
					while(true){
						SimpleTransactionStepEntity sts = getNextSTStep(pCTStep.getSimpleTransaction());
						
						if(sts == null){
							LOGGER.warn("No furter step to execute in Simple Transaction : {}", pCTStep.getSimpleTransaction().getIdKey());
							
							sts = getPreviousSTStep(pCTStep.getSimpleTransaction());
							
							pCTStep.getSimpleTransaction().setStatus(Status.COMLETED);
							pCTStep.getSimpleTransaction().setAdded(new Date());
							pCTStep.setExecutionStatus(Status.COMLETED);
							//	TODO : Get status from last ST
							pCTStep.setResultStatus(sts.getResultStatus());
							pCTStep.setAdded(new Date());
							
							LOGGER.warn("Complex Transaction : {} : {}", pCTStep.getSimpleTransaction().getIdKey(), pCTStep.getSimpleTransaction().getStatus());
							break;
						}
						
						LOGGER.warn("Executing Simple Transaction Step : {}", sts.getSeqNo());

						FunctionHandler.get().handle(pCTStep, sts);
						
						//	Update data
						pCTStep.setData(sts.getData());
						
						if(!sts.getExecutionStatus().equals(Status.COMLETED)){
							LOGGER.warn("Last step in Simple Transaction is in {}", sts.getExecutionStatus());
							break;
						}
						
						LOGGER.warn("Simple Transaction Step : {} : {}", sts.getSeqNo(), sts.getExecutionStatus());
						
					}					
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
	}
	
	/**
	 * Get next Simple Transaction Step, based on result of previous step
	 * 
	 * <p/>Decision Status and Function should not be same
	 * 
	 * @param pST
	 * @return
	 */
	public SimpleTransactionStepEntity getNextSTStep(SimpleTransactionEntity pST){

		//	TODO : Check previous step
		
		SimpleTransactionStepEntity sts = null;
		
		try{
			Query query = null;
			
			SimpleTransactionStepEntity previousStep = getPreviousSTStep(pST);
			if(previousStep != null){
				query = mServerDao.getSessionFactory().getCurrentSession().createQuery("from SimpleTransactionStepEntity "
						+ " where mExecutionStatus != :ExecutionStatus "
						+ " and mSimpleTransaction = :SimpleTransaction "
						+ " and mDecisionStatus = :ResultStatus "
						+ " and mFunctionClass != :FunctionClass "
						+ " order by mSeqNo ");
				
				query.setParameter("ResultStatus", previousStep.getResultStatus());
				query.setParameter("FunctionClass", previousStep.getFunctionClass());
			} else {
				query = mServerDao.getSessionFactory().getCurrentSession().createQuery("from SimpleTransactionStepEntity "
						+ " where mExecutionStatus != :ExecutionStatus "
						+ " and mSimpleTransaction = :SimpleTransaction "
						+ " order by mSeqNo ");
			}
			
			query.setParameter("ExecutionStatus", Status.COMLETED);
			query.setParameter("SimpleTransaction", pST);
			query.setMaxResults(1);
			
			sts = (SimpleTransactionStepEntity)query.list().get(0);
			LOGGER.debug("STS : {}, {}",  sts.getSeqNo(), sts.getFunction());
		}catch(IndexOutOfBoundsException e){
			LOGGER.warn("No further step found in Simple Transaction {}", pST.getIdKey());
		}
		
		return sts;		
	}
	
	/**
	 * Get previous executed Step
	 * 
	 *<p/> Last executed step, irrespective of the result
	 * 
	 * @param pST
	 * @return
	 */
	public SimpleTransactionStepEntity getPreviousSTStep(SimpleTransactionEntity pST){
		SimpleTransactionStepEntity sts = null;
		try{
			Query query = mServerDao.getSessionFactory().getCurrentSession().createQuery("from SimpleTransactionStepEntity "
					+ " where mExecutionStatus = :ExecutionStatus "
					+ " and mSimpleTransaction = :SimpleTransaction"
					+ " order by mSeqNo DESC ");
			query.setParameter("ExecutionStatus", Status.COMLETED);
			query.setParameter("SimpleTransaction", pST);
			query.setMaxResults(1);
			
			sts = (SimpleTransactionStepEntity)query.list().get(0);
			LOGGER.debug("STS : {}, {}",  sts.getSeqNo(), sts.getFunction());
		}catch(IndexOutOfBoundsException e){
			LOGGER.warn("No completed step found in Simple Transaction {}", pST.getIdKey());
		}
		
		return sts;
	}

}
