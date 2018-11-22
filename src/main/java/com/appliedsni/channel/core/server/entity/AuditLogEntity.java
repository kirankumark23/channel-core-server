package com.appliedsni.channel.core.server.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
@Entity
@Table(name="xAuditLog")
public class AuditLogEntity {
	@Id
	@Column(name = "xidkey", unique = true, nullable = false)
	@Type(type="pg-uuid")
	private UUID mIdKey;
	@Column(name = "xPreviousState", length = 400)
	private String mPreviousState;
	@Column(name = "xCurrentState", length = 400)
	private String mCurrentState;
	@Column(name = "xVersion")
	private int mVersion;
	@Column(name = "xRefIdKey")
	@Type(type="pg-uuid")
	private UUID mRefIdKey;
	@Column(name = "xEntityName", length = 400)
	private String mEntityName;
	public AuditLogEntity(){
		mIdKey=UUID.randomUUID();
	}
	
	public AuditLogEntity(String pPreviousState, String pCurrentState, int pVersion, UUID pRefIdKey,
			String pEntityName) {
		super();
		mIdKey=UUID.randomUUID();
		mPreviousState = pPreviousState;
		mCurrentState = pCurrentState;
		mVersion = pVersion;
		mRefIdKey = pRefIdKey;
		mEntityName = pEntityName;
	}

	public UUID getIdKey() {
		return mIdKey;
	}
	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}
	public String getPreviousState() {
		return mPreviousState;
	}
	public void setPreviousState(String pPreviousState) {
		mPreviousState = pPreviousState;
	}
	public String getCurrentState() {
		return mCurrentState;
	}
	public void setCurrentState(String pCurrentState) {
		mCurrentState = pCurrentState;
	}
	public int getVersion() {
		return mVersion;
	}
	public void setVersion(int pVersion) {
		mVersion = pVersion;
	}
	public UUID getRefIdKey() {
		return mRefIdKey;
	}
	public void setRefIdKey(UUID pRefIdKey) {
		mRefIdKey = pRefIdKey;
	}
	public String getEntityName() {
		return mEntityName;
	}
	public void setEntityName(String pEntityName) {
		mEntityName = pEntityName;
	}
	
}
