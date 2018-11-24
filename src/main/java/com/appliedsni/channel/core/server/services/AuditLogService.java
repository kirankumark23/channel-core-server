package com.appliedsni.channel.core.server.services;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.appliedsni.channel.core.server.common.dao.AuditLogDaoImpl;
import com.appliedsni.channel.core.server.common.exception.ResourceNotFoundException;
import com.appliedsni.channel.core.server.entity.AuditLogEntity;
@Path(value = "/rest")
public class AuditLogService {
	@Autowired
	AuditLogDaoImpl auditLogDaoImpl;
   @GET
   @Path("auditLog/{id}")
   @Produces(MediaType.APPLICATION_JSON)
    public Response getAuditLog(@PathParam("id")UUID pIdKey,@QueryParam("currentVersion") Integer pCurrentVersion,@QueryParam("oldVersion") Integer pOldVersion) {

	   List<AuditLogEntity> auditLogs=auditLogDaoImpl.getAuditLogEntity(pIdKey, pCurrentVersion, pOldVersion);
        if(auditLogs!=null&&!auditLogs.isEmpty()){
	   return Response.ok(auditLogs).build();
        }else{
        	throw new ResourceNotFoundException("There is no record for your request");
        }
    }
}
