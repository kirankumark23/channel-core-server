package com.appliedsni.channel.core.server.config.test;

import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.config.EntityAuditLogInterceptor;
import com.appliedsni.channel.core.server.config.Profiler;
import com.appliedsni.channel.core.server.test.TestApplicationContextInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(initializers = TestApplicationContextInitializer.class,locations="classpath:testApplicationContext.xml")
@Transactional
public class ProfilerTest {
	@Test
	public void profileMethodTest() throws URISyntaxException {
		Profiler profiler = new Profiler();
		
		try {
			assertNull("null pointer exception", profiler.profile(null));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
