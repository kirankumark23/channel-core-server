package com.appliedsni.channel.core.server.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.MessageEntity;
import com.google.gson.Gson;

public class SpringAMQPRabbitSender {
	private static Gson mGson = new Gson();
      AmqpTemplate amqpTemplate = (AmqpTemplate)ChannelApplicationContext.get().getBean("amqpTemplate");
      public void pushMessage(MessageEntity pMessage){
    	  amqpTemplate.convertAndSend("channel.out", mGson.toJson(pMessage));
    	  System.out.println( " message(s) sent successfully.");
	  }
	 
}