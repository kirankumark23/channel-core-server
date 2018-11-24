package com.appliedsni.channel.core.server.common.exception;

/**
 * @author Gauri
 *
 */
public class ResourceNotFoundException extends RuntimeException{
	 public ResourceNotFoundException(String message) {
	        super(message);
	    }

	    public ResourceNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
