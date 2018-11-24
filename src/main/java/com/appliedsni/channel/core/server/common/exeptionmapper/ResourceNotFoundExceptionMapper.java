package com.appliedsni.channel.core.server.common.exeptionmapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.appliedsni.channel.core.server.common.api.model.ApiErrorDetails;
import com.appliedsni.channel.core.server.common.exception.ResourceNotFoundException;
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException>{
	@Context
    private UriInfo uriInfo;
	 @Override
	    public Response toResponse(ResourceNotFoundException exception) {

	        Status status = Status.OK;

	        ApiErrorDetails errorDetails = new ApiErrorDetails();
	        errorDetails.setStatus(status.getStatusCode());
	        errorDetails.setTitle(status.getReasonPhrase());
	        errorDetails.setMessage("There is no record for your request");
	        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());

	        return Response.status(status).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
	    }
}
