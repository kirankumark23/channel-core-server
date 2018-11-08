package com.appliedsni.channel.core.server.security.service;


import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appliedsni.channel.core.server.security.api.AuthenticationTokenDetails;
import com.appliedsni.channel.core.server.security.domain.Authority;
import com.appliedsni.channel.core.server.security.exception.AuthenticationTokenRefreshmentException;
import com.appliedsni.channel.core.server.user.domain.RoleActionsEntity;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Service which provides operations for authentication tokens.
 *
 * @author gauri
 */
@Component
public class AuthenticationTokenService {

	@Autowired
	AuthenticationTokenSettings authenticationTokenSettings;
    @Autowired
    private AuthenticationTokenIssuer tokenIssuer;

    @Autowired
    private AuthenticationTokenParser tokenParser;

    /**
     * Issue a token for a user with the given authorities.
     *
     * @param username
     * @param authorities
     * @return
     */
    public String issueToken(String username, Set<RoleActionsEntity> authorities) {

        String id = generateTokenIdentifier();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(id)
                .withUsername(username)
                .withAuthorities(authorities)
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .withRefreshCount(0)
                .withRefreshLimit(authenticationTokenSettings.getRefreshLimit())
                .build();

        return tokenIssuer.issueToken(authenticationTokenDetails);
    }

    /**
     * Parse and validate the token.
     *
     * @param token
     * @return
     */
    public AuthenticationTokenDetails parseToken(String token) {
        return tokenParser.parseToken(token);
    }

    /**
     * Refresh a token.
     *
     * @param currentTokenDetails
     * @return
     */
    public String refreshToken(AuthenticationTokenDetails currentTokenDetails) {

        if (!currentTokenDetails.isEligibleForRefreshment()) {
            throw new AuthenticationTokenRefreshmentException("This token cannot be refreshed");
        }

        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        AuthenticationTokenDetails newTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(currentTokenDetails.getId()) // Reuse the same id
                .withUsername(currentTokenDetails.getUsername())
                .withAuthorities(currentTokenDetails.getAuthorities())
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .withRefreshCount(currentTokenDetails.getRefreshCount() + 1)
                .withRefreshLimit(authenticationTokenSettings.getRefreshLimit())
                .build();

        return tokenIssuer.issueToken(newTokenDetails);
    }

    /**
     * Calculate the expiration date for a token.
     *
     * @param issuedDate
     * @return
     */
    private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
        return issuedDate.plusSeconds(authenticationTokenSettings.getValidFor());
    }

    /**
     * Generate a token identifier.
     *
     * @return
     */
    private String generateTokenIdentifier() {
        return UUID.randomUUID().toString();
    }
}