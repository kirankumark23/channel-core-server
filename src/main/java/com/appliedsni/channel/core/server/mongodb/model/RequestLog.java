package com.appliedsni.channel.core.server.mongodb.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

/**
 * @author Gauri
 *
 */
public class RequestLog {
	@Id
	private UUID mIdkey;
	
	private String mBody;
	private String mIpAddress;
	private Date mTimeStamp;
	private String mUrl;
	private String mHeader;
	private String mQueryParm;
	private String mMethod;
	public RequestLog(){
		mIdkey = UUID.randomUUID();
	}

	public RequestLog( String pBody, String pIpAddress, String pUrl, String pHeader,
			String pQueryParm,String pMethod) {
		super();
		mIdkey = UUID.randomUUID();
		mBody = pBody;
		mIpAddress = pIpAddress;
		mTimeStamp = new Date();
		mUrl = pUrl;
		mHeader = pHeader;
		mQueryParm = pQueryParm;
		mMethod= pMethod;
	}


	public UUID getIdkey() {
		return mIdkey;
	}
	public void setIdkey(UUID pIdkey) {
		mIdkey = pIdkey;
	}
	public String getBody() {
		return mBody;
	}
	public void setBody(String pBody) {
		mBody = pBody;
	}
	public String getIpAddress() {
		return mIpAddress;
	}
	public void setIpAddress(String pIpAddress) {
		mIpAddress = pIpAddress;
	}
	public Date getTimeStamp() {
		return mTimeStamp;
	}
	public void setTimeStamp(Date pTimeStamp) {
		mTimeStamp = pTimeStamp;
	}
	public String getUrl() {
		return mUrl;
	}
	public void setUrl(String pUrl) {
		mUrl = pUrl;
	}
	public String getHeader() {
		return mHeader;
	}
	public void setHeader(String pHeader) {
		mHeader = pHeader;
	}
	public String getQueryParm() {
		return mQueryParm;
	}
	public void setQueryParm(String pQueryParm) {
		mQueryParm = pQueryParm;
	}

	
	
	

}
