package com.appliedsni.channel.core.server.services;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.UserEntity;
import com.appliedsni.channel.core.server.queue.SpringAMQPRabbitSender;


@Path("/service")
public class TestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);
	SpringAMQPRabbitSender springAMQPRabbitSender=(SpringAMQPRabbitSender) ChannelApplicationContext.get().getBean("springAMQPRabbitSender");
	@POST
	@Path("/push")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBasketProducts(UserEntity message){
		springAMQPRabbitSender.pushMessage();
		return "push sccess";
		
		
	}
	
}
