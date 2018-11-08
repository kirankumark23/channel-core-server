package com.appliedsni.channel.core.server.security.service;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appliedsni.channel.core.server.security.api.AuthenticationTokenDetails;

import java.util.Date;

/**
 * Component which provides operations for issuing JWT tokens.
 *
 * @author gauri
 */
@Component
class AuthenticationTokenIssuer {

    @Autowired
    private AuthenticationTokenSettings authenticationTokenSettings;

    /**
     * Issue a JWT token
     *
     * @param authenticationTokenDetails
     * @return
     */
    public String issueToken(AuthenticationTokenDetails authenticationTokenDetails) {

        return Jwts.builder()
                .setId(authenticationTokenDetails.getId())
                .setIssuer(authenticationTokenSettings.getIssuer())
                .setAudience(authenticationTokenSettings.getAudience())
                .setSubject(authenticationTokenDetails.getUsername())
                .setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
                .claim(authenticationTokenSettings.getAuthoritiesClaimName(), authenticationTokenDetails.getAuthorities())
                .claim(authenticationTokenSettings.getRefreshCountClaimName(), authenticationTokenDetails.getRefreshCount())
                .claim(authenticationTokenSettings.getRefreshLimitClaimName(), authenticationTokenDetails.getRefreshLimit())
                .signWith(SignatureAlgorithm.HS256, authenticationTokenSettings.getSecret())
                .compact();
    }
}