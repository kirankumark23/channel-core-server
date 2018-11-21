package com.appliedsni.channel.core.server.mongodb.dao;

import java.util.UUID;
import org.springframework.data.mongodb.core.MongoOperations;

import com.appliedsni.channel.core.server.mongodb.model.RequestLog;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Gauri
 *
 */
public class RequestLogDaoImpl implements RequestLogDao {
	
	private MongoOperations mMongoOps;
	private static final String REQUEST_LOG_COLLECTION = "RequestLog";
	
	public RequestLogDaoImpl(MongoOperations pMongoOps){
		this.mMongoOps=pMongoOps;
	}

	@Override
	public void create(RequestLog pRequestLog) {
		// TODO Auto-generated method stub
		this.mMongoOps.insert(pRequestLog, REQUEST_LOG_COLLECTION);

	}

	@Override
	public RequestLog readById(UUID pIdKey) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("_id").is(pIdKey));
		return this.mMongoOps.findOne(query, RequestLog.class, REQUEST_LOG_COLLECTION);
	}




}
