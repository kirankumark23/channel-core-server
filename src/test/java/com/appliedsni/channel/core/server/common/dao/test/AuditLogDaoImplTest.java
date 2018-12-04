package com.appliedsni.channel.core.server.common.dao.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.common.dao.AuditLogDaoImpl;
import com.appliedsni.channel.core.server.test.TestApplicationContextInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(initializers = TestApplicationContextInitializer.class,locations="classpath:testApplicationContext.xml")
@Transactional
public class AuditLogDaoImplTest {
	
	@Test
	public void getAuditLogEntityTest() throws URISyntaxException {
		AuditLogDaoImpl auditLogDaoImple = new AuditLogDaoImpl();
		
		assertEquals(null, auditLogDaoImple.getAuditLogEntity(UUID.randomUUID(), 0, 0));
	}
	
	@Test
	public void getAuditLogEntityNullUUIDTest() throws URISyntaxException {
		AuditLogDaoImpl auditLogDaoImple = new AuditLogDaoImpl();
		assertNull("null pointer exception", auditLogDaoImple.getAuditLogEntity(null, 0, 0));
	}
	
	@Test
	public void getAuditLogEntityNullCurrentVersionTest() throws URISyntaxException {
		AuditLogDaoImpl auditLogDaoImple = new AuditLogDaoImpl();
		assertNull("null pointer exception", auditLogDaoImple.getAuditLogEntity(UUID.randomUUID(), null, 0));
	}
	@Test
	public void getAuditLogEntityNullOldVersionTest() throws URISyntaxException {
		AuditLogDaoImpl auditLogDaoImple = new AuditLogDaoImpl();
		assertNull("null pointer exception", auditLogDaoImple.getAuditLogEntity(UUID.randomUUID(), 0, null));
	}
}
