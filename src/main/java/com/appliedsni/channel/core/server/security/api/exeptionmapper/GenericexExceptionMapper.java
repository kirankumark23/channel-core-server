package com.appliedsni.channel.core.server.security.api.exeptionmapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.appliedsni.channel.core.server.common.api.model.ApiErrorDetails;
@Provider
public class GenericexExceptionMapper implements ExceptionMapper<Exception>{
	@Context
    private UriInfo uriInfo;
	@Override
	public Response toResponse(Exception pException) {
		// TODO Auto-generated method stub
		 Status status = Status.INTERNAL_SERVER_ERROR;

	        ApiErrorDetails errorDetails = new ApiErrorDetails();
	        errorDetails.setStatus(status.getStatusCode());
	        errorDetails.setTitle(status.getReasonPhrase());
	        errorDetails.setMessage("INTERNAL SERVER ERROR");
	        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

	        return Response.status(status).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
	}


}
