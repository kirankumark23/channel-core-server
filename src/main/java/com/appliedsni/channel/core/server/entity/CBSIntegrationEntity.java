package com.appliedsni.channel.core.server.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="xCBSIntegration")
public class CBSIntegrationEntity {

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;
	
	@Column(name="xOnline")
	boolean mOnline;
	
	public CBSIntegrationEntity(){}
	
	public CBSIntegrationEntity(UUID pIdKey){
		mIdKey = pIdKey;
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public boolean isOnline() {
		return mOnline;
	}

	public void setOnline(boolean pOnline) {
		mOnline = pOnline;
	}
}
