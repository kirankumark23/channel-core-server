package com.appliedsni.channel.core.server.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageEntity {

	private UUID mIdKey;
	private String mType;	
	private Map<String, String> mData;
	
	public MessageEntity(){}
	public MessageEntity(UUID pIdKey){
		mIdKey = pIdKey;
		mData = new HashMap<String, String>();
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public String getType() {
		return mType;
	}

	public void setType(String pType) {
		mType = pType;
	}

	public Map<String, String> getData() {
		return mData;
	}

	public void setData(Map<String, String> pData) {
		mData = pData;
	}
}
