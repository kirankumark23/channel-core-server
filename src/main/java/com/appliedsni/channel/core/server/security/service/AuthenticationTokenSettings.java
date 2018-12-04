package com.appliedsni.channel.core.server.security.service;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Settings for signing and verifying JWT tokens.
 *
 * @author gauri
 */

public class AuthenticationTokenSettings {

	/**
	 * Secret for signing and verifying the token signature.
	 */
	private String secret;

	/**
	 * Allowed clock skew for verifying the token signature (in seconds).
	 */
	private Long clockSkew;

	/**
	 * Identifies the recipients that the JWT token is intended for.
	 */
	private String audience;

	/**
	 * Identifies the JWT token issuer.
	 */
	private String issuer;

	/**
	 * JWT claim for the authorities.
	 */
	private String authoritiesClaimName;

	/**
	 * JWT claim for the token refreshment count.
	 */
	private String refreshCountClaimName;

	/**
	 * JWT claim for the maximum times that a token can be refreshed.
	 */
	private String refreshLimitClaimName;

	/**
	 * How long the token is valid for (in seconds).
	 */
	private Long validFor;

	/**
	 * How many times the token can be refreshed.
	 */
	private Integer refreshLimit;

	

	public AuthenticationTokenSettings(String pSecret, Long pClockSkew, String pAudience, String pIssuer,
			String pAuthoritiesClaimName, String pRefreshCountClaimName, String pRefreshLimitClaimName, Long pValidFor,
			Integer pRefreshLimit) {
		super();
		secret = pSecret;
		clockSkew = pClockSkew;
		audience = pAudience;
		issuer = pIssuer;
		authoritiesClaimName = pAuthoritiesClaimName;
		refreshCountClaimName = pRefreshCountClaimName;
		refreshLimitClaimName = pRefreshLimitClaimName;
		validFor = pValidFor;
		refreshLimit = pRefreshLimit;
	}

	public String getSecret() {
		return secret;
	}

	public Long getClockSkew() {
		return clockSkew;
	}

	public String getAudience() {
		return audience;
	}

	public String getIssuer() {
		return issuer;
	}

	public String getAuthoritiesClaimName() {
		return authoritiesClaimName;
	}

	public String getRefreshCountClaimName() {
		return refreshCountClaimName;
	}

	public String getRefreshLimitClaimName() {
		return refreshLimitClaimName;
	}

	public Long getValidFor() {
		return validFor;
	}

	public Integer getRefreshLimit() {
		return refreshLimit;
	}

}
