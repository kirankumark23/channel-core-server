package com.appliedsni.channel.core.server.handler.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.handler.ComplexTransactionHandler;
import com.appliedsni.channel.core.server.test.TestApplicationContextInitializer;

import channel.client.dao.ServerDao;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(initializers = TestApplicationContextInitializer.class,locations="classpath:testApplicationContext.xml")
@Transactional
public class ComplexTransactionHandlerTest {
	private ServerDao mServerDao;
	
	@Test(expected =NullPointerException.class)
	public void createTransactionNullTest() throws URISyntaxException {
		ComplexTransactionHandler complexTransactionHadnler = new ComplexTransactionHandler(mServerDao);
		assertNull("null pointer exception", complexTransactionHadnler.createTransaction(null));
		
	}
	
	@Test(expected =NullPointerException.class)
	public void getNextCTStepNullTest() throws URISyntaxException {
		ComplexTransactionHandler complexTransactionHadnler = new ComplexTransactionHandler(mServerDao);
		assertNull("null pointer exception", complexTransactionHadnler.getNextCTStep(null));
		
	}
	@Test(expected =NullPointerException.class)
	public void getPreviousCTStepNullTest() throws URISyntaxException {
		ComplexTransactionHandler complexTransactionHadnler = new ComplexTransactionHandler(mServerDao);
		assertNull("null pointer exception", complexTransactionHadnler.getPreviousCTStep(null));
		
	}
}
