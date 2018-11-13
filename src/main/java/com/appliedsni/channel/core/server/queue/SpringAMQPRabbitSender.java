package com.appliedsni.channel.core.server.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.google.gson.Gson;

import channel.client.function.MessageEntity;

public class SpringAMQPRabbitSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringAMQPRabbitSender.class);
	private static Gson mGson = new Gson();
    private AmqpTemplate amqpTemplate = (AmqpTemplate)ChannelApplicationContext.get().getBean("amqpTemplate");
    
    public void pushMessage(MessageEntity pMessage){
		amqpTemplate.convertAndSend("channel.out", mGson.toJson(pMessage));
		LOGGER.warn("Message(s) sent : OUT_QUEUE.");
	}
	 
}