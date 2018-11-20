package com.appliedsni.channel.core.server.security.api.filter;
import java.io.ByteArrayInputStream;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.mongodb.dao.RequestLogDaoImpl;
import com.appliedsni.channel.core.server.mongodb.model.RequestLog;
import com.google.gson.Gson;

/**
 * @author Gauri
 *
 */
@Provider
public class CustomLoggingFilter implements ContainerRequestFilter {
    @Context
    private ResourceInfo resourceInfo;
    @Context
    HttpServletRequest httpServletRequest;
    private static final Logger log = LoggerFactory.getLogger(CustomLoggingFilter.class);
    Gson gson=new Gson();
    RequestLogDaoImpl reLogDaoImpl=(RequestLogDaoImpl) ChannelApplicationContext.get().getBean("requestLogDao");
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path=requestContext.getUriInfo().getPath();
        String methodName= resourceInfo.getResourceMethod().getName();
        String pathParameters= gson.toJson(requestContext.getUriInfo().getPathParameters());
        String header=gson.toJson(requestContext.getHeaders());
        String body = readEntityStream(requestContext);
        String client_ip = httpServletRequest.getHeader("x-real-ip");
        if(client_ip == null || client_ip.isEmpty()){ // extract from forward ips
            String ipForwarded = httpServletRequest.getHeader("x-forwarded-for");
            String[] ips = ipForwarded == null ? null: ipForwarded.split(",") ;
            client_ip = ( ips == null || ips.length == 0 )? null : ips[0];
            client_ip = ( client_ip == null || client_ip.isEmpty() ) ? httpServletRequest.getRemoteAddr() : client_ip;
        }
        RequestLog reLog=new RequestLog(body, client_ip, path, header, pathParameters,methodName);
        reLogDaoImpl.create(reLog);
    }

    private String readEntityStream(ContainerRequestContext requestContext)
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        final InputStream inputStream = requestContext.getEntityStream();
        final StringBuilder builder = new StringBuilder();
        try
        {
            ReaderWriter.writeTo(inputStream, outStream);
            byte[] requestEntity = outStream.toByteArray();
            if (requestEntity.length == 0) {
                builder.append("");
            } else {
                builder.append(new String(requestEntity));
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity) );
        } catch (IOException ex) {
            log.error("----Exception occurred while reading entity stream :{}",ex.getMessage());
        }
        return builder.toString();
    }

   
}