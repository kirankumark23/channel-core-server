package com.appliedsni.channel.core.server.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.services.TestService;

import channel.client.function.MessageEntity;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(initializers = TestApplicationContextInitializer.class,locations="classpath:testApplicationContext.xml")
@Transactional
public class TestServiceTest {
	boolean dbSetUp=false;
	@Autowired
	private TestService testService;
	private static ApplicationContext ctx;
	@Before
	public void dbSetup(){
		if(!DataBaseSetup.dbSetup){
			DataBaseSetup.test();
		}
		dbSetUp=true;
		
	}
	
	@Test
	public void getSTPList() throws URISyntaxException {
		
		List result = testService.getSTPList();
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void getBasketProducts() throws URISyntaxException {
		MessageEntity messageEntity=new MessageEntity();
		String  result = testService.getBasketProducts(messageEntity);
		assertEquals(result, "push sccess");
	}
	@Test(expected =NullPointerException.class)
	public void getAccountBalance() throws URISyntaxException {
		MessageEntity messageEntity=new MessageEntity();
		String result = testService.getAccountBalance(null, null, null, messageEntity);
		assertEquals(result, "{\"response\" : \"Execution Completed !\"}");
	}
	
	@Test
	public void getCTList() throws URISyntaxException {
		
		List result = testService.getCTList();
		assertEquals(result.size(), 0);
	}
	@Test
	public void getCT() throws URISyntaxException {
		
		ComplexTransactionEntity result = testService.getCT(UUID.randomUUID());
		assertEquals(result, null);
	}
	
	@Test(expected=InvalidDataAccessResourceUsageException.class)
	public void getCTSteps() throws URISyntaxException {
		
		List result = testService.getCTSteps(UUID.randomUUID());
		assertEquals(result.size(), 0);
	}
	@Test
	public void getCTS() throws URISyntaxException {
		ComplexTransactionStepEntity result = testService.getCTS(UUID.randomUUID(),UUID.randomUUID());
		assertEquals(result, null);
	}
	
	@Test(expected =NullPointerException.class)
	public void getCTP() throws URISyntaxException {
		ComplexTransactionProductEntity result = testService.getCTP(UUID.randomUUID());
		assertEquals(result, null);
	}
	
	@Test
	public void getCTPList() throws URISyntaxException {
		
		List result = testService.getCTPList();
		assertEquals(result.size(), 0);
	}
	@Test(expected =NullPointerException.class)
	public void getSTSteps() throws URISyntaxException {
		List result = testService.getSTSteps(UUID.randomUUID(),UUID.randomUUID());
		assertEquals(result.size(), 0);
	}
	
}
