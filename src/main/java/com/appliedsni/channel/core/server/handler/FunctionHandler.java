package com.appliedsni.channel.core.server.handler;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.common.utils.PropertiesCache;
import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.CBSIntegrationEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.queue.MQManager;

import channel.client.dao.ServerDao;
import channel.client.function.AbstractIntegration;
import channel.client.function.CustomThreadLocal;
import channel.client.function.IntegratonInterface;
import channel.client.function.ResponseMessageEntity;
import channel.client.function.Status;

public class FunctionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FunctionHandler.class);
	private ServerDao mServerDao;
	private String mCompanyLibPath;
	public FunctionHandler(ServerDao pServerDao){
		mServerDao = pServerDao;

		
		try {
			mCompanyLibPath = PropertiesCache.getInstance().getProperty("companyLibPath");
		} catch (Exception e) {
			LOGGER.error("Failed to get mCompanyLibPath", e);
		}
	}
	
	public static FunctionHandler get(){
		return ChannelApplicationContext.get().getBean("functionHandler", FunctionHandler.class);
	}

	
	public void handle(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
	    try {
	    	LOGGER.warn("Thread : {}", Thread.currentThread());
	    	ResponseMessageEntity response = new ResponseMessageEntity(pCTS.getComplexTransaction().getIdKey());
	    	CustomThreadLocal customThreadLocal = new CustomThreadLocal();
	    	
	    	if(!pSTS.isExecute()){
	    		pSTS.setExecutionStatus(Status.IN_PROGRESS);
	    	} else {
	    		pSTS.setExecutionStatus(Status.IN_PROGRESS);

	    		Method targetMethod = null;
	    		
	    		try{
					targetMethod = Class.forName(pSTS.getFunctionClass()).getMethod(pSTS.getFunction(), ResponseMessageEntity.class, CustomThreadLocal.class);					
					targetMethod.invoke((pSTS.getFunctionClass().contains("FunctionHandler") ? this : Class.forName(pSTS.getFunctionClass()).newInstance()), response, customThreadLocal);
	    		}catch(ClassNotFoundException e){
		    		URLClassLoader loader = new URLClassLoader(new URL[] { new URL("file:" + mCompanyLibPath) }, this.getClass().getClassLoader());
					Class targetClass = Class.forName(pSTS.getFunctionClass(), true, loader);
					
		    		targetMethod = targetClass.getDeclaredMethod("prolog", ResponseMessageEntity.class, CustomThreadLocal.class);
		    		targetMethod.invoke(targetClass.newInstance(), response, customThreadLocal);
					
		    		targetMethod = targetClass.getDeclaredMethod(pSTS.getFunction(), ResponseMessageEntity.class, CustomThreadLocal.class);				
					targetMethod.invoke(targetClass.newInstance(), response, customThreadLocal);

					targetMethod = targetClass.getDeclaredMethod("epilog", ResponseMessageEntity.class, CustomThreadLocal.class);
		    		targetMethod.invoke(targetClass.newInstance(), response, customThreadLocal);
	    		}

				pSTS.setExecutionStatus(response.getExecutionStatus());
	    		pSTS.setResultStatus(response.getResultStatus());
	    		pSTS.setData(response.getData().size()>0?response.getData().values().iterator().next() :null);
	    		pSTS.setAdded(new Date());
	    		
	    		if(response.getMessage() != null){
	    			MQManager.get().handle(response);
	    		}
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
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

//		ResponseMessageEntity response = new ResponseMessageEntity((UUID)CustomThreadLocal.get("CT"));
		pResponse.setCode("STD-2");
		pResponse.setMessage("Your account balance is : " + cts.getData());

		pResponse.setExecutionStatus(Status.COMLETED);
		pResponse.setResultStatus(Status.SUCCESS);

//		MQManager.get().handle(pResponse);

		LOGGER.warn(pResponse.getMessage());
		
		LOGGER.warn("Completed");
	}
	
	public void fn_send_delay(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		
		ResponseMessageEntity response = new ResponseMessageEntity(pCTS.getComplexTransaction().getIdKey());
		response.setCode("STD-3");
		response.setMessage("Hi, I am working on it...will get the balance soon, Thanks for your patience");
		
//		MQManager.get().handle(response);

		LOGGER.warn(response.getMessage());
		
		LOGGER.warn("Completed");
	}

	public void fn_get_ac_list(ComplexTransactionStepEntity pCTS, SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		
		ResponseMessageEntity response = new ResponseMessageEntity(pCTS.getComplexTransaction().getIdKey());
		response.setCode("STD-4");
		response.setMessage("Here is the list of your accounts");
		response.getData().put("1", "A12345");
		response.getData().put("2", "B12345");
		response.getData().put("3", "C12345");
		response.getData().put("4", "D12345");
		response.getData().put("5", "E12345");
		
//		MQManager.get().handle(response);

		LOGGER.warn(response.getMessage());
		
		LOGGER.warn("Completed");
	}

}
