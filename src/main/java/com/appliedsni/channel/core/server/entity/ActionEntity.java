package com.appliedsni.channel.core.server.entity;

public class ActionEntity {

	private String mCode;
	private String mName;
	private boolean mRedirect;
	private String mURL;
	private boolean mDisplay;
	private boolean mConform;
	private String mConformationMessage;
	
	public String getCode() {
		return mCode;
	}
	public void setCode(String pCode) {
		mCode = pCode;
	}
	public String getName() {
		return mName;
	}
	public void setName(String pName) {
		mName = pName;
	}
	public boolean isDisplay() {
		return mDisplay;
	}
	public void setDisplay(boolean pDisplay) {
		mDisplay = pDisplay;
	}
	public boolean isRedirect() {
		return mRedirect;
	}
	public void setRedirect(boolean pRedirect) {
		mRedirect = pRedirect;
	}
	public String getURL() {
		return mURL;
	}
	public void setURL(String pURL) {
		mURL = pURL;
	}
	public boolean isConform() {
		return mConform;
	}
	public void setConform(boolean pConform) {
		mConform = pConform;
	}
	public String getConformationMessage() {
		return mConformationMessage;
	}
	public void setConformationMessage(String pConformationMessage) {
		mConformationMessage = pConformationMessage;
	}
	
}
