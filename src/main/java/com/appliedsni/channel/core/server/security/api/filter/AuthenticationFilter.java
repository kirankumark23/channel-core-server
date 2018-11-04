package com.appliedsni.channel.core.server.security.api.filter;

import javax.annotation.Priority;

import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.security.api.AuthenticatedUserDetails;
import com.appliedsni.channel.core.server.security.api.AuthenticationTokenDetails;
import com.appliedsni.channel.core.server.security.api.TokenBasedSecurityContext;
import com.appliedsni.channel.core.server.security.service.AuthenticationTokenService;
import com.appliedsni.channel.core.server.user.dao.UserDaoImpl;
import com.appliedsni.channel.core.server.user.domain.UserEntity;

import java.io.IOException;

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
    private AuthenticationTokenService authenticationTokenService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String authenticationToken = authorizationHeader.substring(7);
            handleTokenBasedAuthentication(authenticationToken, requestContext);
            return;
        }

        // Other authentication schemes (such as Basic) could be supported
    }

    private void handleTokenBasedAuthentication(String authenticationToken, ContainerRequestContext requestContext) {

        AuthenticationTokenDetails authenticationTokenDetails = authenticationTokenService.parseToken(authenticationToken);
        UserEntity user = userDao.findByUsernameOrEmail(authenticationTokenDetails.getUsername());
        AuthenticatedUserDetails authenticatedUserDetails = new AuthenticatedUserDetails(user.getEmailaddress(), user.getRoleActionses());

        boolean isSecure = requestContext.getSecurityContext().isSecure();
        SecurityContext securityContext = new TokenBasedSecurityContext(authenticatedUserDetails, authenticationTokenDetails, isSecure);
        requestContext.setSecurityContext(securityContext);
    }
}