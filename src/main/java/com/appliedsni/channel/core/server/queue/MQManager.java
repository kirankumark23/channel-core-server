package com.appliedsni.channel.core.server.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.RequestMessageEntity;
import com.appliedsni.channel.core.server.handler.ComplexTransactionHandler;

import channel.client.dao.ServerDao;
import channel.client.function.ResponseMessageEntity;

/**
 * MQ Manager provides services to PUSH and PULL messages from IN & OUT queues
 * 
 * @author prashantpolshettiwar
 *
 */
public class MQManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MQManager.class);
	
	private ServerDao mServerDao;
	private SpringAMQPRabbitSender mMQSender;
	
	public static MQManager get(){
		return ChannelApplicationContext.get().getBean("MQManager", MQManager.class);
	}

	public MQManager (ServerDao pServerDao, SpringAMQPRabbitSender pMQSender){
		mServerDao = pServerDao;
		mMQSender = pMQSender;
	}

	public void handle(RequestMessageEntity pRequest){
		//	TODO : Initiate request handling
	}
	
	public void handle(ResponseMessageEntity pResponse){
		mMQSender.pushMessage(pResponse);
	}
}
