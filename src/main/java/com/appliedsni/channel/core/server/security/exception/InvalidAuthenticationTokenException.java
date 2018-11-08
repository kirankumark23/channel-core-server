package com.appliedsni.channel.core.server.security.exception;


/**
 * Thrown if an authentication token is invalid.
 *
 * @author gauri
 */
public class InvalidAuthenticationTokenException extends RuntimeException {

    public InvalidAuthenticationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
