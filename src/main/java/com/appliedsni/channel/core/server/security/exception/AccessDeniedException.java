package com.appliedsni.channel.core.server.security.exception;

/**
 * Thrown if errors occur during the authorization process.
 *
 * @author gauri
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
