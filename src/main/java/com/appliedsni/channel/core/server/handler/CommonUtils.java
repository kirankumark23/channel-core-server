package com.appliedsni.channel.core.server.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.ActionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.queue.SpringAMQPRabbitSender;

import channel.client.dao.ServerDao;
import channel.client.function.Status;

public class CommonUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
	private ServerDao mServerDao;
	
	public CommonUtils(ServerDao pServerDao){
		mServerDao = pServerDao;
	}
	
	public static CommonUtils get(){
		return ChannelApplicationContext.get().getBean("commonUtils", CommonUtils.class);
	}
	
	public List<ComplexTransactionEntity> getCTList(){
		
		List<Object> objList = mServerDao.find("from ComplexTransactionEntity order by mAdded");
		
		List<ComplexTransactionEntity> ctList = new ArrayList<ComplexTransactionEntity>();
		
		for(Object obj : objList){
			ctList.add((ComplexTransactionEntity)obj);
		}
		
		return ctList;
	}
	
	public ComplexTransactionEntity getCT(UUID pIdKey){
		return (ComplexTransactionEntity)mServerDao.get(ComplexTransactionEntity.class, pIdKey);
	}
	
	public ComplexTransactionProductEntity getCTP(UUID pIdKey){
		return (ComplexTransactionProductEntity)mServerDao.get(ComplexTransactionProductEntity.class, pIdKey);
	}

	public ComplexTransactionStepEntity getCTS(UUID pIdKey){
		return (ComplexTransactionStepEntity)mServerDao.get(ComplexTransactionStepEntity.class, pIdKey);
	}

	public ComplexTransactionProductStepEntity getCTPS(UUID pCTPS){
		return (ComplexTransactionProductStepEntity)mServerDao.get(ComplexTransactionProductStepEntity.class, pCTPS);
	}
	
	public SimpleTransactionProductEntity getSTP(UUID pIdKey){
		return (SimpleTransactionProductEntity)mServerDao.get(SimpleTransactionProductEntity.class, pIdKey);
	}

	public SimpleTransactionProductStepEntity getSTPS(UUID pIdKey){
		return (SimpleTransactionProductStepEntity)mServerDao.get(SimpleTransactionProductStepEntity.class, pIdKey);
	}
	

	public List<ComplexTransactionStepEntity> getCTSteps(UUID pCT){
		List<Object> objList = mServerDao.find("from ComplexTransactionStepEntity where mComplexTransaction = ? order by mSeqNo", getCT(pCT));
		
		List<ComplexTransactionStepEntity> ctsList = new ArrayList<ComplexTransactionStepEntity>();
		
		for(Object obj : objList){
			ctsList.add((ComplexTransactionStepEntity)obj);
		}
		
		return ctsList;
	}

	public List<SimpleTransactionStepEntity> getSTSteps(UUID pCT, UUID pCTS){
		List<Object> objList = mServerDao.find("from SimpleTransactionStepEntity where mSimpleTransaction = ? order by mSeqNo", getCTS(pCTS).getSimpleTransaction());
		
		List<SimpleTransactionStepEntity> stsList = new ArrayList<SimpleTransactionStepEntity>();
		
		for(Object obj : objList){
			stsList.add((SimpleTransactionStepEntity)obj);
		}
		
		return stsList;
	}
	
	public List<ComplexTransactionProductEntity> getCTPList(){
		List<Object> objList = mServerDao.find("from ComplexTransactionProductEntity order by mIdKey");
		
		List<ComplexTransactionProductEntity> ctpList = new ArrayList<ComplexTransactionProductEntity>();
		
		for(Object obj : objList){
			ComplexTransactionProductEntity ctp = (ComplexTransactionProductEntity)obj;
			ctpList.add(ctp);
		}
		
		return ctpList;
	}
		
	public void create(ComplexTransactionProductEntity pCTP){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					ComplexTransactionProductEntity ctp = getCTP(pCTP.getIdKey());
					if(ctp == null){
						mServerDao.save(pCTP);						
					} else {
						ctp.setName(pCTP.getName());
						ctp.setType(pCTP.getType());
						ctp.setStatus(pCTP.getStatus());
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
	}
	
	public void create(ComplexTransactionProductStepEntity pCTPS){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					ComplexTransactionProductStepEntity ctps = getCTPS(pCTPS.getIdKey());
					if(ctps == null){
						mServerDao.save(pCTPS);
					} else {
						ctps.setDecisionStatus(pCTPS.getDecisionStatus());
						ctps.setDelay(pCTPS.getDelay());
						ctps.setSeqNo(pCTPS.getSeqNo());
						ctps.setSimpleTransaction(pCTPS.getSimpleTransaction());
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});		
	}
	
	public void create(SimpleTransactionProductEntity pSTP){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					SimpleTransactionProductEntity stps = getSTP(pSTP.getIdKey());
					if(stps == null){
						mServerDao.save(pSTP);
					} else {
						stps.setName(pSTP.getName());
						stps.setStatus(pSTP.getStatus());
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});				
	}
	
	public void create(SimpleTransactionProductStepEntity pSTPS){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					SimpleTransactionProductStepEntity stps = getSTPS(pSTPS.getIdKey());
					if(stps == null){
						mServerDao.save(pSTPS);
					} else {
						stps.setDelay(pSTPS.getDelay());
						stps.setFunction(pSTPS.getFunction());
						stps.setFunctionClass(pSTPS.getFunctionClass());
						stps.setSeqNo(pSTPS.getSeqNo());
						stps.setSimpleTransaction(pSTPS.getSimpleTransaction());
						stps.setStatus(pSTPS.getStatus());						
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});						
	}

	public void activateCTP(UUID pCTP){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					ComplexTransactionProductEntity ctp = getCTP(pCTP);
					ctp.setStatus(Status.CLOSE);
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
	}

	public List<ComplexTransactionProductStepEntity> getCTPSteps(UUID pCTP){
		List<Object> objList = mServerDao.find("from ComplexTransactionProductStepEntity where mComplexTransaction = ? order by mSeqNo", getCTP(pCTP));
		
		List<ComplexTransactionProductStepEntity> ctpsList = new ArrayList<ComplexTransactionProductStepEntity>();
		
		for(Object obj : objList){
			ctpsList.add((ComplexTransactionProductStepEntity)obj);
		}
		
		return ctpsList;
	}
	
	public List<SimpleTransactionProductEntity> getSTPList(){
		List<Object> objList = mServerDao.find("from SimpleTransactionProductEntity order by mIdKey");
		
		List<SimpleTransactionProductEntity> stpList = new ArrayList<SimpleTransactionProductEntity>();
		
		for(Object obj : objList){
			stpList.add((SimpleTransactionProductEntity)obj);
		}
		
		return stpList;
	}

	public List<SimpleTransactionProductStepEntity> getSTPSteps(UUID pSTP){
		List<Object> objList = mServerDao.find("from SimpleTransactionProductStepEntity where mSimpleTransaction.mIdKey = ? order by mSeqNo", pSTP);
		
		List<SimpleTransactionProductStepEntity> stpsList = new ArrayList<SimpleTransactionProductStepEntity>();
		
		for(Object obj : objList){
			stpsList.add((SimpleTransactionProductStepEntity)obj);
		}
		
		return stpsList;
	}


}
