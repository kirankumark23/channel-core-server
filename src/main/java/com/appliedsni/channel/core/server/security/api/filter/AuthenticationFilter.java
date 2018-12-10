package com.appliedsni.channel.core.server.security.api.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.security.api.AuthenticatedUserDetails;
import com.appliedsni.channel.core.server.security.api.AuthenticationTokenDetails;
import com.appliedsni.channel.core.server.security.api.TokenBasedSecurityContext;
import com.appliedsni.channel.core.server.security.exception.InvalidAuthenticationTokenException;
import com.appliedsni.channel.core.server.security.service.AuthenticationTokenService;
import com.appliedsni.channel.core.server.security.service.AuthenticationTokenSettings;
import com.appliedsni.channel.core.server.user.dao.UserDaoImpl;
import com.appliedsni.channel.core.server.user.domain.UserEntity;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

import channel.client.function.CommonConstants;
import channel.client.function.CustomThreadLocal;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * JWT authentication filter.
 *
 * @author gauri
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Autowired
    private UserDaoImpl userDao;
    @Autowired
	AuthenticationTokenSettings authenticationTokenSettings;
    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && !"".equals(authorizationHeader.trim())) {
        	if(authorizationHeader.startsWith(CommonConstants.GOOGLE_TOKEN_STRING)){
        		handleGoogleLogin(authorizationHeader.substring(8),requestContext);
        	}else if(authorizationHeader.startsWith(CommonConstants.CHANNEL_TOKEN_STRING)){
            handleTokenBasedAuthentication(authorizationHeader.substring(8), requestContext);
        	}
            return;
        }

        // Other authentication schemes (such as Basic) could be supported
    }

    private void handleTokenBasedAuthentication(String authenticationToken, ContainerRequestContext requestContext) {

        AuthenticationTokenDetails authenticationTokenDetails = authenticationTokenService.parseToken(authenticationToken);
        UserEntity user = userDao.findByUsernameOrEmail(authenticationTokenDetails.getUsername());
        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user.getEmailaddress(), user.getRoleActionses());
        CustomThreadLocal.add(CommonConstants.CURRENT_USER, user.getIdkey().toString());
        boolean isSecure = requestContext.getSecurityContext().isSecure();
        SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails, authenticationTokenDetails, isSecure);
        requestContext.setSecurityContext(securityContext);
    }

	public void handleGoogleLogin(String tokenString, ContainerRequestContext requestContext) {
		try {
			JacksonFactory jacksonFactory = new JacksonFactory();
			GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier(new NetHttpTransport(),
					jacksonFactory);

			GoogleIdToken token = GoogleIdToken.parse(jacksonFactory, tokenString);
			GoogleIdToken.Payload payload;
			if (googleIdTokenVerifier.verify(token)) {
				payload = token.getPayload();
				if (!tokenString.equals(payload.getAudience())) {
					throw new InvalidAuthenticationTokenException("Invalid token");
				} else if (!tokenString.equals(payload.getAuthorizedParty())) {
					throw new InvalidAuthenticationTokenException("Invalid token");
				}

			} else {
				throw new InvalidAuthenticationTokenException("Invalid token");
			}
			UserEntity user = userDao.findByUsernameOrEmail(payload.getEmail());
			CustomThreadLocal.add(CommonConstants.CURRENT_USER, user.getEmailaddress());
			AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user.getEmailaddress(),
					user.getRoleActionses());
			String chanelToken = authenticationTokenService.issueToken(payload.getEmail(), user.getRoleActionses());
			AuthenticationTokenDetails authenticationTokenDetails = authenticationTokenService.parseToken(chanelToken);
			boolean isSecure = requestContext.getSecurityContext().isSecure();
			SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails,
					authenticationTokenDetails, isSecure);
			requestContext.setSecurityContext(securityContext);

		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			throw new InvalidAuthenticationTokenException("Invalid token");
		}

	}
}