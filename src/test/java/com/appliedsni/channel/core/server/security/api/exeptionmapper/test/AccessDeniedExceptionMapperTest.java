package com.appliedsni.channel.core.server.security.api.exeptionmapper.test;

import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;

import org.junit.Test;

import com.appliedsni.channel.core.server.security.api.exeptionmapper.AccessDeniedExceptionMapper;

public class AccessDeniedExceptionMapperTest {
	
	@Test(expected =NullPointerException.class)
	public void toResponseNullTest() throws URISyntaxException {
		AccessDeniedExceptionMapper accessDeniedExceptionMapper = new AccessDeniedExceptionMapper();
		assertNull("null pointer exception", accessDeniedExceptionMapper.toResponse(null));
		
	}
}
