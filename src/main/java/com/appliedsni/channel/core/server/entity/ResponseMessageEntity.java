package com.appliedsni.channel.core.server.entity;

import java.util.UUID;

public class ResponseMessageEntity extends MessageEntity{
	private String mCode;
	private String mMessage;
	
	public ResponseMessageEntity(UUID pIdKey){
		super(pIdKey);
	}
	
	public ResponseMessageEntity(ComplexTransactionStepEntity pCTS){		
		super(pCTS.getComplexTransaction().getIdKey());
	}
	
	public String getCode() {
		return mCode;
	}
	public void setCode(String pCode) {
		mCode = pCode;
	}
	public String getMessage() {
		return mMessage;
	}
	public void setMessage(String pMessage) {
		mMessage = pMessage;
	}
}
