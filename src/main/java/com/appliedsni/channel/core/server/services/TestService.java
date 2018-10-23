package com.appliedsni.channel.core.server.services;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.MessageEntity;
import com.appliedsni.channel.core.server.entity.UserEntity;
import com.appliedsni.channel.core.server.handler.ComplexTransactionHandler;
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
	
	@POST
	@Path("/balance/{idkey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAccountBalance(
			@PathParam("idkey") String pIdKey, 
			@QueryParam("accessToken") String pAccessToken,
			@QueryParam("idToken") String pIdToken,
			MessageEntity pMessage){

//		if(pAccessToken == null){
//			return "{\"status\":\"Access Token is missing\"}";			
//		} else if(pIdToken == null) {
//			return "{\"status\":\"ID Token is missing\"}";	
//		} else if(pMessage.getData() == null || pMessage.getData().get("ACCOUNT") == null){			
//			return "{\"status\":\"Sure, for which account ?\"}";
//		} else if(true){
//			return "{\"status\":\"Account balance is 100,000\"}";
//		}
		
		ComplexTransactionHandler.get().handle(pMessage);
		
		return "Execution Completed";
	}
	
	
}
