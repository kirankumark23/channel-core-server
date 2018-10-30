package com.appliedsni.channel.core.server.handler;

import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.dao.ServerDao;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.Status;

/**
 * Identify Simple transaction steps that are in progress for 1 min or more
 * 
 * 
 * @author prashantpolshettiwar
 *
 */
public class RecoverService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecoverService.class);
	
	private ServerDao mServerDao;
	
	public static RecoverService get(){
		return ChannelApplicationContext.get().getBean("recoverService", RecoverService.class);
	}

	public RecoverService (ServerDao pServerDao){
		mServerDao = pServerDao;
	}

	public void recover(){
		LOGGER.warn("Removery Service looking CT....");
		
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					List<Object> ct_list = mServerDao.find("from ComplexTransactionEntity where mStatus = ?", Status.IN_PROGRESS);
					
					for(Object ctObj : ct_list){
						ComplexTransactionEntity ct = (ComplexTransactionEntity)ctObj;
						LOGGER.warn("Attempting to recover CT : {}", ct.getIdKey());
						ComplexTransactionHandler.get().handle(ct);
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});

	}
}
