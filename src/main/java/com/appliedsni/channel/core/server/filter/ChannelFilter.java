package com.appliedsni.channel.core.server.filter;

import java.io.IOException;


import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ChannelFilter implements ContainerRequestFilter{

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelFilter.class);

	public void filter(ContainerRequestContext pRequest) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Request filter invoked");
		LOGGER.warn("Request filter invoked");
	}
}
