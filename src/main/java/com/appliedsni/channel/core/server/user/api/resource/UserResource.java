package com.appliedsni.channel.core.server.user.api.resource;

import javax.annotation.security.PermitAll;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.user.api.model.QueryUserResult;
import com.appliedsni.channel.core.server.user.dao.UserDaoImpl;
import com.appliedsni.channel.core.server.user.domain.UserEntity;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * JAX-RS resource class that provides operations for users.
 *
 * @author gauri
 */
@Path("/users")
public class UserResource {

    @Context
    private UriInfo mUriInfo;

    @Context
    private SecurityContext mSecurityContext;

    @Autowired
    private UserDaoImpl mUserDao;

    @GET
    @RolesAllowed({"ADMIN"})
    public Response getUsers() {

        List<QueryUserResult> queryUserResults = mUserDao.findAll().stream()
                .map(this::toQueryUserResult)
                .collect(Collectors.toList());

        return Response.ok(queryUserResults).build();
    }

    @GET
    @Path("{userId}")
    @RolesAllowed({"ADMIN"})
    public Response getUser(@PathParam("userId") UUID userId) {

        UserEntity user = mUserDao.findById(userId);
        QueryUserResult queryUserResult = toQueryUserResult(user);
        return Response.ok(queryUserResult).build();
    }

    @GET
    @Path("me")
    @PermitAll
    public Response getAuthenticatedUser() {

        Principal principal = mSecurityContext.getUserPrincipal();

        if (principal == null) {
            QueryUserResult queryUserResult = new QueryUserResult();
            queryUserResult.setUsername("anonymous");
            queryUserResult.setAuthorities(new HashSet<>());
            return Response.ok(queryUserResult).build();
        }

        UserEntity user = mUserDao.findByUsernameOrEmail(principal.getName());
        QueryUserResult queryUserResult = toQueryUserResult(user);
        return Response.ok(queryUserResult).build();
    }

    /**
     * Maps a {@link UserEntity} instance to a {@link QueryUserResult} instance.
     *
     * @param user
     * @return
     */
    private QueryUserResult toQueryUserResult(UserEntity user) {
        QueryUserResult queryUserResult = new QueryUserResult();
        queryUserResult.setId(user.getIdkey());
        queryUserResult.setFirstName(user.getFirstname());
        queryUserResult.setLastName(user.getLastname());
        queryUserResult.setEmail(user.getEmailaddress());
        queryUserResult.setUsername(user.getEmailaddress());
        queryUserResult.setAuthorities(user.getRoleActionses());
        return queryUserResult;
    }
}