package com.appliedsni.channel.core.server.greeting.service.test;

import static org.junit.Assert.*;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.appliedsni.channel.core.server.config.Profiler;
import com.appliedsni.channel.core.server.greeting.service.GreetingService;
import com.appliedsni.channel.core.server.test.TestApplicationContextInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(initializers = TestApplicationContextInitializer.class,locations="classpath:testApplicationContext.xml")
@Transactional
public class GreetingServiceTest {
	@Test
	public void getGreetingForUserNullTest() throws URISyntaxException {
		GreetingService greetingService = new GreetingService();
		
		
//			assertNull("null pointer exception",greetingService.getGreetingForUser(null));
		
	}
	@Test
	public void getGreetingForUserTest() throws URISyntaxException {
		GreetingService greetingService = new GreetingService();
		
		
			assertEquals("Hello tom!",greetingService.getGreetingForUser("tom"));
		
	}
}
