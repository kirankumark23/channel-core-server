package com.appliedsni.channel.core.server.websocket.model;
public class Message {
    private String mFrom;
    private String mTo;
    private String mContent;

    public Message() {
		super();
	}

	@Override
    public String toString() {
        return super.toString();
    }

	public String getFrom() {
		return mFrom;
	}

	public void setFrom(String pFrom) {
		mFrom = pFrom;
	}

	public String getTo() {
		return mTo;
	}

	public void setTo(String pTo) {
		mTo = pTo;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String pContent) {
		mContent = pContent;
	}

}