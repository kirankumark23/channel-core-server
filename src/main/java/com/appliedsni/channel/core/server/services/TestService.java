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
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;

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
		try {
		googleLogin(pAccessToken);
		} catch (IOException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "invlaid token";
		}

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
	public void googleLogin(String tokenString) throws IOException, GeneralSecurityException{

        JacksonFactory jacksonFactory = new JacksonFactory();
        GoogleIdTokenVerifier googleIdTokenVerifier =
                            new GoogleIdTokenVerifier(new NetHttpTransport(), jacksonFactory);

        GoogleIdToken token = GoogleIdToken.parse(jacksonFactory, tokenString);
        GoogleIdToken.Payload payload;
        if (googleIdTokenVerifier.verify(token)) {
        	payload = token.getPayload();
            if (!tokenString.equals(payload.getAudience())) {
                throw new IllegalArgumentException("Audience mismatch");
            } else if (!tokenString.equals(payload.getAuthorizedParty())) {
                throw new IllegalArgumentException("Client ID mismatch");
            }
            
        } else {
            throw new IllegalArgumentException("id token cannot be verified");
        }
	}

	
}
