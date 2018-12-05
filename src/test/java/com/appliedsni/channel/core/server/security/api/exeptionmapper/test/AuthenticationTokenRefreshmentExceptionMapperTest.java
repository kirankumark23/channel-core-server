package com.appliedsni.channel.core.server.security.api.exeptionmapper.test;

import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;

import org.junit.Test;

import com.appliedsni.channel.core.server.security.api.exeptionmapper.AuthenticationTokenRefreshmentExceptionMapper;

public class AuthenticationTokenRefreshmentExceptionMapperTest {
	@Test(expected =NullPointerException.class)
	public void toResponseNullTest() throws URISyntaxException {
		AuthenticationTokenRefreshmentExceptionMapper authenticationTokenRefreshmentExceptionMapper = new AuthenticationTokenRefreshmentExceptionMapper();
		assertNull("null pointer exception", authenticationTokenRefreshmentExceptionMapper.toResponse(null));
		
	}
}
