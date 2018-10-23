package com.appliedsni.channel.core.server.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.dao.ServerDao;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.Status;

public class FunctionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FunctionHandler.class);
	private ServerDao mServerDao;
	
	public FunctionHandler(ServerDao pServerDao){
		mServerDao = pServerDao;
	}
	
	public static FunctionHandler get(){
		return ChannelApplicationContext.get().getBean("functionHandler", FunctionHandler.class);
	}

	
	public void handle(String pMethodName, SimpleTransactionStepEntity pSTS){
	    try {
			Method targetMethod = Class.forName("com.appliedsni.channel.core.server.handler.FunctionHandler").getMethod(pMethodName, SimpleTransactionStepEntity.class);
			targetMethod.invoke(this, pSTS);
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
	
	public void fn_ask_ac(SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		
		if(CustomThreadLocal.get("ACCOUNT") == null){
			LOGGER.warn("Sure, which account ?");
		}
		
		LOGGER.warn("Completed");
	}

	public void fn_extract_ac(SimpleTransactionStepEntity pSTS){
		
		if(CustomThreadLocal.get("ACCOUNT") == null){
			pSTS.setExecutionStatus(Status.IN_PROGRESS);
		} else {
			pSTS.setData(CustomThreadLocal.get("ACCOUNT").toString());
			pSTS.setExecutionStatus(Status.COMLETED);
			pSTS.setResultStatus(Status.SUCCESS);			
		}
		
		LOGGER.warn("Completed");
	}
	
	public void fn_get_ac_balance(SimpleTransactionStepEntity pSTS){
		//	TODO : Get Account from previous step
		String ac = CustomThreadLocal.get("ACCOUNT").toString();
		
		//	TODO : Call TDI / CBS service
		
		//	Update balance
		pSTS.setData("100,000");
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
	}
	
	public void fn_send_info(SimpleTransactionStepEntity pSTS){
		pSTS.setExecutionStatus(Status.COMLETED);
		pSTS.setResultStatus(Status.SUCCESS);
		
		LOGGER.warn("Your account balance is 100,000");
		
		LOGGER.warn("Completed");
	}

}
