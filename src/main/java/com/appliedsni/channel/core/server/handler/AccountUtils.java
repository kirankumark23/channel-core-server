package com.appliedsni.channel.core.server.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.entity.CBSIntegrationEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;

import channel.client.dao.ServerDao;
import channel.client.function.CustomThreadLocal;
import channel.client.function.ResponseMessageEntity;
import channel.client.function.Status;

public class AccountUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountUtils.class);
	private ServerDao mServerDao;
	
	public AccountUtils(){
		mServerDao = CustomThreadLocal.getServerDao();
	}
		
	public void fn_get_ac_balance(ResponseMessageEntity pResponse, CustomThreadLocal pCustomThreadLocal){
		//	Get Account from previous step				
		ComplexTransactionStepEntity cts = (ComplexTransactionStepEntity)mServerDao.find("from ComplexTransactionStepEntity "
				+ " where mComplexTransaction.mIdKey = ? "
				+ " and mSeqNo = ? ", CustomThreadLocal.get("CT") , 1).get(0);
		
		//	TODO : Call TDI / CBS service
		
		CBSIntegrationEntity cbs = (CBSIntegrationEntity)mServerDao.find("from CBSIntegrationEntity").get(0);
		
		LOGGER.warn("CBS Integration Online : {}", cbs.isOnline());
		
		if(cbs.isOnline()){
			LOGGER.warn("Getting balance for : {}", cts.getData());
			BigDecimal balance = BigDecimal.valueOf(10000);
			
			//	Update balance
			Map<String, String> data = new HashMap<String, String>();
			data.put("BALANCE", balance.toString());

			pResponse.setExecutionStatus(Status.COMLETED);
			pResponse.setResultStatus(Status.SUCCESS);
			pResponse.setData(data);
		} else {			
			pResponse.setExecutionStatus(Status.COMLETED);
			pResponse.setResultStatus(Status.ERROR);
		}		
	}
	
	public void fn_send_info(ResponseMessageEntity pResponse, CustomThreadLocal pCustomThreadLocal){
		
		ComplexTransactionStepEntity cts = (ComplexTransactionStepEntity)mServerDao.find("from ComplexTransactionStepEntity "
				+ " where mComplexTransaction.mIdKey = ? "
				+ " and mSeqNo = ? ", CustomThreadLocal.get("CT"), 2).get(0);

		pResponse.setCode("STD-2");
		pResponse.setMessage("Your account balance is : " + cts.getData());

		pResponse.setExecutionStatus(Status.COMLETED);
		pResponse.setResultStatus(Status.SUCCESS);

		LOGGER.warn(pResponse.getMessage());
		
		LOGGER.warn("Completed");
	}
	
	public void fn_send_delay(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		
		ResponseMessageEntity response = new ResponseMessageEntity(pCTS.getComplexTransaction().getIdKey());
		response.setCode("STD-3");
		response.setMessage("Hi, I am working on it...will get the balance soon, Thanks for your patience");
		
		LOGGER.warn(response.getMessage());
		
		LOGGER.warn("Completed");
	}
}
