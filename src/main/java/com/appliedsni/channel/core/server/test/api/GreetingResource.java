package com.appliedsni.channel.core.server.test.api;

import javax.annotation.security.PermitAll;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.greeting.service.GreetingService;


/**
 * JAX-RS resource class that provides operations for greetings.
 *
 * @author gauri
 */
@Path("/greetings")
public class GreetingResource {

    @Context
    private SecurityContext securityContext;

    @Autowired
    private GreetingService greetingService;

    @GET
    @Path("/public")
    @PermitAll
    public Response getPublicGreeting() {
        return Response.ok(greetingService.getPublicGreeting()).build();
    }

    @GET
    @Path("protected")
    public Response getProtectedGreeting() {
        String username = securityContext.getUserPrincipal().getName();
        return Response.ok(greetingService.getGreetingForUser(username)).build();
    }
}