package com.appliedsni.channel.core.server.mongodb.dao.test;

import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;

import com.appliedsni.channel.core.server.mongodb.dao.RequestLogDaoImpl;

public class RequestLogDaoImplTest {
	private MongoOperations mMongoOps;
	
	@Test(expected =NullPointerException.class)
	public void readByIdMethodNullTest() throws URISyntaxException {
		RequestLogDaoImpl requestLogDaoImple = new RequestLogDaoImpl(mMongoOps);
		assertNull("null pointer exception", requestLogDaoImple.readById(null));
		
	}
	@Test(expected =NullPointerException.class)
	public void readByIdMethodRandomUUIDTest() throws URISyntaxException {
		RequestLogDaoImpl requestLogDaoImple = new RequestLogDaoImpl(mMongoOps);
		assertNull("null pointer exception", requestLogDaoImple.readById(UUID.randomUUID()));
		
	}
}
