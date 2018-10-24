package com.appliedsni.channel.core.server.queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.appliedsni.channel.core.server.entity.MessageEntity;
import com.appliedsni.channel.core.server.handler.ComplexTransactionHandler;
import com.google.gson.Gson;
// Spirng MDP(Message Driven POJO)
public class SpringAMQPRabbitAyncListener implements MessageListener {
	private static Gson mGson = new Gson();
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringAMQPRabbitAyncListener.class);
	
	@Override
	public void onMessage(Message pMessage) {
		LOGGER.warn("Listener received message = " + new String(pMessage.getBody()));
		
		MessageEntity message = mGson.fromJson(new String(pMessage.getBody()), MessageEntity.class);
		
		LOGGER.warn("Message Type : " + message.getType());
	}
}