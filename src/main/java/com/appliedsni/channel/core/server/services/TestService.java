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
import com.appliedsni.channel.core.server.entity.ActionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.handler.CommonUtils;
import com.appliedsni.channel.core.server.handler.ComplexTransactionHandler;
import com.appliedsni.channel.core.server.queue.SpringAMQPRabbitSender;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

import channel.client.function.MessageEntity;

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
	
	//	PRODUCTs
	
	@GET
	@Path("/complextransactionproducts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List <ComplexTransactionProductEntity> getCTPList(){		
		return CommonUtils.get().getCTPList();
	}

	@GET
	@Path("/complextransactionproducts/{ctpid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ComplexTransactionProductEntity getCTP(@PathParam("ctpid") UUID pCTPIdKey){	
		ComplexTransactionProductEntity ctp = CommonUtils.get().getCTP(pCTPIdKey);
		ActionEntity action1 = new ActionEntity();
		action1.setCode("ADD");
		action1.setDisplay(true);
		action1.setRedirect(true);
		action1.setName("Add Step");
		action1.setURL("/complex_transaction_add_step/{ctpid}".replace("{ctpid}", pCTPIdKey.toString()));
		
		ctp.addAction(action1);

		ActionEntity action2 = new ActionEntity();
		action2.setCode("EDIT");
		action2.setDisplay(true);
		action2.setRedirect(true);
		action2.setName("Edit");
		action2.setURL("/complex_transaction_edit_step/{ctpid}".replace("{ctpid}", pCTPIdKey.toString()));
		
		ctp.addAction(action2);

		ActionEntity action3 = new ActionEntity();
		action3.setCode("ACTIVATE");
		action3.setDisplay(true);
		action3.setRedirect(false);
		action3.setName("Activate");
		action3.setURL("/complextransactionproducts/{ctpid}/activate".replace("{ctpid}", pCTPIdKey.toString()));
		
		ctp.addAction(action3);

		return ctp;
	}

	@POST
	@Path("/complextransactionproducts/{ctpid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCTP(ComplexTransactionProductEntity pCTP){
		CommonUtils.get().create(pCTP);
	}

	@POST
	@Path("/complextransactionproducts/{ctpid}/activate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void activateCTP(@PathParam("ctpid") UUID pCTPIdKey){
		CommonUtils.get().activateCTP(pCTPIdKey);
	}

	@GET
	@Path("/complextransactionproducts/{ctpid}/complexsteps")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ComplexTransactionProductStepEntity> getCTPSteps(@PathParam("ctpid") UUID pCTPIdKey){
		return CommonUtils.get().getCTPSteps(pCTPIdKey);
	}

	@GET
	@Path("/simpletransactionproducts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SimpleTransactionProductEntity> getSTPList(){
		return CommonUtils.get().getSTPList();
	}
	
	@GET
	@Path("/simpletransactionproducts/{stpid}/simplesteps")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SimpleTransactionProductStepEntity> getSTPSteps(@PathParam("stpid") UUID pSTPIdKey){
		return CommonUtils.get().getSTPSteps(pSTPIdKey);
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
