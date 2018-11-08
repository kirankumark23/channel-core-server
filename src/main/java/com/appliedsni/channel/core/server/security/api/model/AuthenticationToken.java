package com.appliedsni.channel.core.server.security.api.model;

/**
 * API model for the authentication token.
 *
 * @author gauri
 */
public class AuthenticationToken {

    private String token;

    public AuthenticationToken() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}