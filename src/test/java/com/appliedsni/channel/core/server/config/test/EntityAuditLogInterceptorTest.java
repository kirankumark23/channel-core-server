package com.appliedsni.channel.core.server.config.test;




import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.common.dao.AuditLogDaoImpl;
import com.appliedsni.channel.core.server.config.EntityAuditLogInterceptor;
import com.appliedsni.channel.core.server.test.TestApplicationContextInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(initializers = TestApplicationContextInitializer.class,locations="classpath:testApplicationContext.xml")
@Transactional
public class EntityAuditLogInterceptorTest {
	@Test
	public void onDeleteTest() throws URISyntaxException {
		EntityAuditLogInterceptor entityAuditLogInterceptor = new EntityAuditLogInterceptor();
		
//		assertNull("null pointer exception", entityAuditLogInterceptor.onDelete(null, null, null, null, null));
	}
	
	@Test(expected =NullPointerException.class)
	public void onFlushDirtyTest() throws URISyntaxException {
		EntityAuditLogInterceptor entityAuditLogInterceptor = new EntityAuditLogInterceptor();
		
		assertNull("null pointer exception", entityAuditLogInterceptor.onFlushDirty(null, null, null, null, null, null));
	}
}
