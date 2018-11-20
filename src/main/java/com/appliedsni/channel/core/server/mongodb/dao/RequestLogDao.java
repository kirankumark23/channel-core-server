package com.appliedsni.channel.core.server.mongodb.dao;

import java.util.UUID;

import com.appliedsni.channel.core.server.mongodb.model.RequestLog;

/**
 * @author Gauri
 *
 */
public interface RequestLogDao {
	public void create(RequestLog pRequestLog);

	public RequestLog readById(UUID pIdKey);

}
