package com.appliedsni.channel.core.server.login.api.test;

import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;

import org.junit.Test;

import com.appliedsni.channel.core.server.login.api.LoginService;

public class LoginServiceTest {
	@Test(expected =NullPointerException.class)
	public void getNextSTStepNullTest() throws URISyntaxException {
		LoginService loginService = new LoginService();
		assertNull("null pointer exception", loginService.authenticate(null));
		
	}
	@Test(expected =NullPointerException.class)
	public void getPreviousSTStepNullTest() throws URISyntaxException {
		LoginService loginService = new LoginService();
		assertNull("null pointer exception", loginService.refresh());
		
	}
}
