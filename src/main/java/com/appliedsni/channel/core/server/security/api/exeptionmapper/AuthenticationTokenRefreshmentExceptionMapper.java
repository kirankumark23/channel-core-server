package com.appliedsni.channel.core.server.security.api.exeptionmapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.appliedsni.channel.core.server.common.api.model.ApiErrorDetails;
import com.appliedsni.channel.core.server.security.exception.AuthenticationTokenRefreshmentException;

/**
 * Exception mapper for {@link AuthenticationTokenRefreshmentException}s.
 *
 * @author gauri
 */
@Provider
public class AuthenticationTokenRefreshmentExceptionMapper implements ExceptionMapper<AuthenticationTokenRefreshmentException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(AuthenticationTokenRefreshmentException exception) {

        Status status = Status.FORBIDDEN;

        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage("The authentication token cannot be refreshed.");
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(status).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
    }
}