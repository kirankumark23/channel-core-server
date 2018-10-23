package com.appliedsni.channel.core.server.config;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ChannelApplicationContext implements ApplicationContextAware
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelApplicationContext.class);
	
	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext pContext) throws BeansException {
		LOGGER.warn("Application context has been prepared");
		ctx = pContext;
	}
	
	public static ApplicationContext get(){
		return ctx;
	}

	

}
