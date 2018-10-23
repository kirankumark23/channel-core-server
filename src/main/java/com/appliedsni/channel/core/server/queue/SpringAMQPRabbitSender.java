package com.appliedsni.channel.core.server.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;

public class SpringAMQPRabbitSender {
      AmqpTemplate amqpTemplate = (AmqpTemplate)ChannelApplicationContext.get().getBean("amqpTemplate");
      public void pushMessage(){
    	  amqpTemplate.convertAndSend("tp.routingkey.1", "Message # ");
    	  System.out.println( " message(s) sent successfully.");
	  }
	 
}