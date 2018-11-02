package com.appliedsni.channel.core.server.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.dao.ServerDao;
import com.appliedsni.channel.core.server.entity.CBSIntegrationEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.ResponseMessageEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.Status;
import com.appliedsni.channel.core.server.queue.MQManager;

public class FunctionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FunctionHandler.class);
	private ServerDao mServerDao;
	
	public FunctionHandler(ServerDao pServerDao){
		mServerDao = pServerDao;
	}
	
	public static FunctionHandler get(){
		return ChannelApplicationContext.get().getBean("functionHandler", FunctionHandler.class);
	}

	
	public void handle(String pMethodName, ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
	    try {
	    	if(!pSTS.isExecute()){
	    		pSTS.setExecutionStatus(Status.IN_PROGRESS);
	    	} else {
				Method targetMethod = Class.forName("com.appliedsni.channel.core.server.handler.FunctionHandler").getMethod(pMethodName, ComplexTransactionStepEntity.class, SimpleTransactionStepEntity.class);
				targetMethod.invoke(this, pCTS, pSTS);
	    	}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fn_ask_ac(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		pSTS.setAdded(new Date());
		
		if(CustomThreadLocal.get("ACCOUNT") == null){
			ResponseMessageEntity response = new ResponseMessageEntity(pCTS);
			response.setCode("STD-1");
			response.setMessage("Sure, which account ?");
			
			MQManager.get().handle(response);

			LOGGER.warn(response.getMessage());
		}
		
		LOGGER.warn("Completed");
	}

	public void fn_extract_ac(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		
		if(CustomThreadLocal.get("ACCOUNT") == null){
			pSTS.setExecutionStatus(Status.IN_PROGRESS);
		} else {
			pSTS.setData(CustomThreadLocal.get("ACCOUNT").toString());
			pSTS.setExecutionStatus(Status.COMLETED);
			pSTS.setResultStatus(Status.SUCCESS);			
		}
		pSTS.setAdded(new Date());
		
		LOGGER.warn("Completed");
	}
	
	public void fn_get_ac_balance(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		//	Get Account from previous step				
		ComplexTransactionStepEntity cts = (ComplexTransactionStepEntity)mServerDao.find("from ComplexTransactionStepEntity "
				+ " where mComplexTransaction = ? "
				+ " and mSeqNo = ? ", pCTS.getComplexTransaction(), 1).get(0);
		
		//	TODO : Call TDI / CBS service
		
		CBSIntegrationEntity cbs = (CBSIntegrationEntity)mServerDao.find("from CBSIntegrationEntity").get(0);
		
		LOGGER.warn("CBS Integration Online : {}", cbs.isOnline());
		
		if(cbs.isOnline()){
			LOGGER.warn("Getting balance for : {}", cts.getData());
			BigDecimal balance = BigDecimal.valueOf(10000);
			
			//	Update balance
			pSTS.setData(balance.toString());
			pSTS.setExecutionStatus(Status.COMLETED);
			pSTS.setResultStatus(Status.SUCCESS);
			pSTS.setAdded(new Date());			
		} else {			
			pSTS.setExecutionStatus(Status.COMLETED);
			pSTS.setResultStatus(Status.ERROR);
			pSTS.setAdded(new Date());			
		}		
	}
	
	public void fn_send_info(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		pSTS.setAdded(new Date());
		
		ComplexTransactionStepEntity cts = (ComplexTransactionStepEntity)mServerDao.find("from ComplexTransactionStepEntity "
				+ " where mComplexTransaction = ? "
				+ " and mSeqNo = ? ", pCTS.getComplexTransaction(), 2).get(0);

		ResponseMessageEntity response = new ResponseMessageEntity(pCTS);
		response.setCode("STD-2");
		response.setMessage("Your account balance is : " + cts.getData());
		
		MQManager.get().handle(response);

		LOGGER.warn(response.getMessage());
		
		LOGGER.warn("Completed");
	}
	
	public void fn_send_delay(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		
		ResponseMessageEntity response = new ResponseMessageEntity(pCTS);
		response.setCode("STD-3");
		response.setMessage("Hi, I am working on it...will get the balance soon, Thanks for your patience");
		
		MQManager.get().handle(response);

		LOGGER.warn(response.getMessage());
		
		LOGGER.warn("Completed");
	}

	public void fn_get_ac_list(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		
		ResponseMessageEntity response = new ResponseMessageEntity(pCTS);
		response.setCode("STD-4");
		response.setMessage("Here is the list of your accounts");
		response.getData().put("1", "A12345");
		response.getData().put("2", "B12345");
		response.getData().put("3", "C12345");
		response.getData().put("4", "D12345");
		response.getData().put("5", "E12345");
		
		MQManager.get().handle(response);

		LOGGER.warn(response.getMessage());
		
		LOGGER.warn("Completed");
	}

}
