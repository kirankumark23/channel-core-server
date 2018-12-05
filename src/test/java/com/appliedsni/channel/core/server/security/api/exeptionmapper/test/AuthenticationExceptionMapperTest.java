package com.appliedsni.channel.core.server.security.api.exeptionmapper.test;

import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;

import org.junit.Test;

import com.appliedsni.channel.core.server.security.api.exeptionmapper.AuthenticationExceptionMapper;

public class AuthenticationExceptionMapperTest {
	@Test(expected =NullPointerException.class)
	public void toResponseNullTest() throws URISyntaxException {
		AuthenticationExceptionMapper authenticationExceptionMapper = new AuthenticationExceptionMapper();
		assertNull("null pointer exception", authenticationExceptionMapper.toResponse(null));
		
	}
}
