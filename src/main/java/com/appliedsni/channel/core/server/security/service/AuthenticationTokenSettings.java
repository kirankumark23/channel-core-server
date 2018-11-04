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
@Component
@Scope("singleton")
class AuthenticationTokenSettings {

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

	@PostConstruct
	private void init() {
		try {
			InitialContext initialContext = new InitialContext();
			secret = (String) initialContext.lookup("java:comp/env/secret");
			authoritiesClaimName = (String) initialContext.lookup("java:comp/env/authoritiesClaimName");
			issuer = (String) initialContext.lookup("java:comp/env/issuer");
			audience = (String) initialContext.lookup("java:comp/env/audience");
			clockSkew = (Long) initialContext.lookup("java:comp/env/clockSkew");
			authoritiesClaimName = (String) initialContext.lookup("java:comp/env/authoritiesClaimName");
			refreshCountClaimName = (String) initialContext.lookup("java:comp/env/refreshCountClaimName");
			refreshLimitClaimName = (String) initialContext.lookup("java:comp/env/refreshLimitClaimName");
			validFor = (Long) initialContext.lookup("java:comp/env/validFor");
			refreshLimit = (Integer) initialContext.lookup("java:comp/env/refreshLimit");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
