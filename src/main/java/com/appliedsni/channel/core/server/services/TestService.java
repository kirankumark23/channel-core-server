package com.appliedsni.channel.core.server.services;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.MessageEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.handler.CommonUtils;
import com.appliedsni.channel.core.server.handler.ComplexTransactionHandler;
import com.appliedsni.channel.core.server.queue.SpringAMQPRabbitSender;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;

@Path("/service")
public class TestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);
	SpringAMQPRabbitSender springAMQPRabbitSender=(SpringAMQPRabbitSender) ChannelApplicationContext.get().getBean("springAMQPRabbitSender");
	
	@POST
	@Path("/push")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBasketProducts(MessageEntity pMessage){
		springAMQPRabbitSender.pushMessage(pMessage);
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
		
//		try{
//			
//			googleLogin(pAccessToken);
//		} catch (IOException | GeneralSecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "invlaid token";
//		}
		
		ComplexTransactionHandler.get().handle(pMessage);
		
		return "{\"response\" : \"Execution Completed !\"}";
	}
	
	@GET
	@Path("/complextransactions")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ComplexTransactionEntity> getCTList(){
		
		 List<ComplexTransactionEntity> ctList = CommonUtils.get().getCTList();
		
		return ctList;
	}

	@GET
	@Path("/complextransactions/{idkey}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ComplexTransactionEntity getCT(@PathParam("idkey") UUID pIdKey ){		
		return CommonUtils.get().getCT(pIdKey);
	}
	
	@GET
	@Path("/complextransactions/{ctid}/complexsteps")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ComplexTransactionStepEntity> getCTSteps(@PathParam("ctid") UUID pCTIdKey ){
		
		 List<ComplexTransactionStepEntity> ctsList = CommonUtils.get().getCTSteps(pCTIdKey);		
		return ctsList;
	}
	
	@GET
	@Path("/complextransactions/{ctid}/complexsteps/{ctsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ComplexTransactionStepEntity getCTS(@PathParam("ctid") UUID pCTIdKey, @PathParam("ctsid") UUID pCTSIdKey ){
		
		ComplexTransactionStepEntity cts = CommonUtils.get().getCTS(pCTSIdKey);		
		return cts;
	}
	
	@GET
	@Path("/complextransactions/{ctid}/complexsteps/{ctsid}/simplesteps")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SimpleTransactionStepEntity> getSTSteps(@PathParam("ctid") UUID pCTIdKey, @PathParam("ctsid") UUID pCTSIdKey ){
		
		 List<SimpleTransactionStepEntity> stsList = CommonUtils.get().getSTSteps(pCTIdKey, pCTSIdKey);		
		return stsList;
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
