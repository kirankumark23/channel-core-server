package com.appliedsni.channel.core.server.services;


import javax.annotation.PostConstruct;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.ActionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.CustomerEntity;
import com.appliedsni.channel.core.server.entity.CustomerMandateEntity;
import com.appliedsni.channel.core.server.entity.CustomerMandateServiceEntity;
import com.appliedsni.channel.core.server.entity.ProductRoleAccessEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.handler.CommonUtils;
import com.appliedsni.channel.core.server.handler.ComplexTransactionHandler;
import com.appliedsni.channel.core.server.queue.SpringAMQPRabbitSender;
import com.appliedsni.channel.core.server.user.domain.RoleEntity;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

import channel.client.function.MessageEntity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;
@Component
@Path("/service")
public class TestService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);
	@Autowired
	SpringAMQPRabbitSender springAMQPRabbitSender;
	
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
		action2.setURL("/complex_transaction_edit/{ctpid}".replace("{ctpid}", pCTPIdKey.toString()));
		
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
	@Path("/complextransactionproducts/{ctpid}/complexsteps/{ctpsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ComplexTransactionProductStepEntity getCTPS(@PathParam("ctpid") UUID pCTPIdKey, @PathParam("ctpsid") UUID pCTPSIdKey){
		ComplexTransactionProductStepEntity ctps = CommonUtils.get().getCTPS(pCTPSIdKey);
		
		ActionEntity action1 = new ActionEntity();
		action1.setCode("EDIT");
		action1.setDisplay(true);
		action1.setRedirect(true);
		action1.setName("Edit");
		action1.setURL("/complex_transaction_step_edit/{ctpsid}".replace("{ctpsid}", pCTPSIdKey.toString()));
		
		ctps.addAction(action1);

		return ctps;
	}	

	@POST
	@Path("/complextransactionproducts/{ctpid}/complexsteps/{ctpsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCTPS(ComplexTransactionProductStepEntity pCTPS){
		CommonUtils.get().create(pCTPS);
	}
	
	@GET
	@Path("/simpletransactionproducts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SimpleTransactionProductEntity> getSTPList(){
		return CommonUtils.get().getSTPList();
	}

	@GET
	@Path("/simpletransactionproducts/{stpid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SimpleTransactionProductEntity getSTP(@PathParam("stpid") UUID pSTPIdKey){
		SimpleTransactionProductEntity stp = CommonUtils.get().getSTP(pSTPIdKey);
		
		ActionEntity action1 = new ActionEntity();
		action1.setCode("ADD");
		action1.setDisplay(true);
		action1.setRedirect(true);
		action1.setName("Add Step");
		action1.setURL("/simple_transaction_add_step/{stpid}".replace("{stpid}", pSTPIdKey.toString()));
		
		stp.addAction(action1);

		ActionEntity action2 = new ActionEntity();
		action2.setCode("EDIT");
		action2.setDisplay(true);
		action2.setRedirect(true);
		action2.setName("Edit");
		action2.setURL("/simple_transaction_edit/{stpid}".replace("{stpid}", pSTPIdKey.toString()));
		
		stp.addAction(action2);

		return stp;
	}
	
	@POST
	@Path("/simpletransactionproducts/{stpid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(SimpleTransactionProductEntity pSTP){
		CommonUtils.get().create(pSTP);
	}
	
	@GET
	@Path("/simpletransactionproducts/{stpid}/simplesteps")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SimpleTransactionProductStepEntity> getSTPSteps(@PathParam("stpid") UUID pSTPIdKey){
		return CommonUtils.get().getSTPSteps(pSTPIdKey);
	}
	
	@GET
	@Path("/simpletransactionproducts/{stpid}/simplesteps/{stpsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SimpleTransactionProductStepEntity getSTPS(@PathParam("stpid") UUID pSTPIdKey, @PathParam("stpsid") UUID pSTPSIdKey){
		
		SimpleTransactionProductStepEntity stps = CommonUtils.get().getSTPS(pSTPSIdKey);
		
		ActionEntity action1 = new ActionEntity();
		action1.setCode("EDIT");
		action1.setDisplay(true);
		action1.setRedirect(true);
		action1.setName("Edit");
		action1.setURL("/simple_transaction_step_edit/{stpsid}".replace("{ctpsid}", pSTPSIdKey.toString()));
		
		stps.addAction(action1);

		return stps;
	}
	
	@POST
	@Path("/simpletransactionproducts/{stpid}/simplesteps/{stpsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(SimpleTransactionProductStepEntity pCTPS){
		CommonUtils.get().create(pCTPS);
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

	@GET
	@Path("/roles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RoleEntity> getRoles(){
		return CommonUtils.get().getRoles();
	}
	
	@GET
	@Path("/channels")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RoleEntity> getChannels(){
		return CommonUtils.get().getChannels();
	}
	
	@GET
	@Path("/channels/{cid}/products")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ComplexTransactionProductEntity> getChannelProductList(@PathParam("cid") UUID pRole){
		return CommonUtils.get().getChannelProductList(pRole);
	}

	@GET
	@Path("/allowedproducts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ComplexTransactionProductEntity> getAllowedProducts(){
		return CommonUtils.get().getAllowdProducts();
	}

//	@GET
//	@Path("/customer/{cid}/mandate/{mid}/allowedproducts")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public List<ComplexTransactionProductEntity> getAllowedCustomerProducts(@PathParam("cid") UUID pCustomer, @PathParam("mid") UUID pMandate){
//		return CommonUtils.get().getAllowdProducts(pCustomer, pMandate);
//	}

	@GET
	@Path("/customer/{cid}/allowedproducts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ComplexTransactionProductEntity> getAllowedCustomerProducts(@PathParam("cid") UUID pCustomer, @PathParam("mid") UUID pMandate){
		return CommonUtils.get().getAllowdProducts(pCustomer);
	}
	
	@GET
	@Path("/complextransactionproducts/{ctpid}/roles")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RoleEntity> getChannelServices(@PathParam("ctpid") UUID pCTPIdKey){
		return CommonUtils.get().getProductRoles(pCTPIdKey);
	}
	
	@POST
	@Path("/complextransactionproducts/{ctpid}/roles/{roleid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProductRoleAccess(@PathParam("ctpid") UUID pCTPIdKey, @PathParam("roleid") UUID pRoleIdKey){
		CommonUtils.get().createProductRoleAccess(pCTPIdKey, pRoleIdKey);
		return "{\"status\" : \"Role Access created\"}";
	}
	
	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CustomerEntity> getCustomerList(){
		return CommonUtils.get().getCustomerList();
	}
	
	@GET
	@Path("/customers/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerEntity getCustomer(@PathParam("email") String pEmail){
		return CommonUtils.get().getCustomer(pEmail);
	}	
		
	@GET
	@Path("/customermandates")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CustomerMandateEntity> getMandateList(){
		return CommonUtils.get().getCustomerMandateList();
	}

	@GET
	@Path("/customermandates/{cmid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerMandateEntity getMandate(@PathParam("cmid") UUID pCMIdKey){
		return CommonUtils.get().getCustomerMandate(pCMIdKey);
	}

	@POST
	@Path("/customermandates/{cmid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerMandateEntity createMandate(@PathParam("cmid") UUID pCMIdKey, CustomerMandateEntity pMandate){
		return CommonUtils.get().createMandate(pMandate);
	}
	
	@GET
	@Path("/customermandates/{cmid}/services")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CustomerMandateServiceEntity> getMandateServiceList(@PathParam("cmid") UUID pCMIdKey){
		return CommonUtils.get().getCustomerMandateServiceList(pCMIdKey);
	}

	@GET
	@Path("/customermandates/{cmid}/services/{cmsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerMandateServiceEntity getMandateService(@PathParam("cmid") UUID pCMIdKey, @PathParam("cmsid") UUID pCMSIdKey){
		return CommonUtils.get().getCustomerMandateService(pCMIdKey, pCMSIdKey);
	}

	@POST
	@Path("/customermandates/{cmid}/services/{cmsid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CustomerMandateServiceEntity createMandateService(@PathParam("cmid") UUID pCMIdKey, @PathParam("cmsid") UUID pCMSIdKey, CustomerMandateServiceEntity pMandateService){
		return CommonUtils.get().createMandateService(pCMIdKey, pCMSIdKey, pMandateService);
	}

}
