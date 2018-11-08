package com.appliedsni.channel.core.server.login.api;

import javax.annotation.security.PermitAll;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.security.api.AuthenticationTokenDetails;
import com.appliedsni.channel.core.server.security.api.TokenBasedSecurityContext;
import com.appliedsni.channel.core.server.security.api.model.AuthenticationToken;
import com.appliedsni.channel.core.server.security.api.model.UserCredentials;
import com.appliedsni.channel.core.server.security.service.AuthenticationTokenService;
import com.appliedsni.channel.core.server.security.service.UsernamePasswordValidator;
import com.appliedsni.channel.core.server.user.domain.UserEntity;

/**
 * JAX-RS resource class that provides operations for authentication.
 *
 * @author gauri
 */
@Path("/rest")
public class LoginService {

    @Context
    private SecurityContext securityContext;

    @Autowired
    private UsernamePasswordValidator usernamePasswordValidator;

    @Autowired
    private AuthenticationTokenService authenticationTokenService;

    /**
     * Validate user credentials and issue a token for the user.
     *
     * @param credentials
     * @return
     */
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(UserCredentials credentials) {

        UserEntity user = usernamePasswordValidator.validateCredentials(credentials.getUsername(), credentials.getPassword());
        String token = authenticationTokenService.issueToken(user.getEmailaddress(), user.getRoleActionses());
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        System.out.println(token);
        return Response.ok(authenticationToken).build();
    }

    /**
     * Refresh the authentication token for the current user.
     *
     * @return
     */
    @POST
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refresh() {

        AuthenticationTokenDetails tokenDetails =
                ((TokenBasedSecurityContext) securityContext).getAuthenticationTokenDetails();
        String token = authenticationTokenService.refreshToken(tokenDetails);

        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setToken(token);
        return Response.ok(authenticationToken).build();
    }
}
