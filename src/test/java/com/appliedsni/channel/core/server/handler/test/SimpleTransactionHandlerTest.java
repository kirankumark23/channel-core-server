package com.appliedsni.channel.core.server.handler.test;

import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.handler.SimpleTransactionHandler;
import com.appliedsni.channel.core.server.test.TestApplicationContextInitializer;

import channel.client.dao.ServerDao;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(initializers = TestApplicationContextInitializer.class,locations="classpath:testApplicationContext.xml")
@Transactional
public class SimpleTransactionHandlerTest {
	private ServerDao mServerDao;
	
	
	@Test(expected =NullPointerException.class)
	public void getNextSTStepNullTest() throws URISyntaxException {
		SimpleTransactionHandler complexTransactionHadnler = new SimpleTransactionHandler(mServerDao);
		assertNull("null pointer exception", complexTransactionHadnler.getNextSTStep(null));
		
	}
	@Test(expected =NullPointerException.class)
	public void getPreviousSTStepNullTest() throws URISyntaxException {
		SimpleTransactionHandler complexTransactionHadnler = new SimpleTransactionHandler(mServerDao);
		assertNull("null pointer exception", complexTransactionHadnler.getPreviousSTStep(null));
		
	}
}
