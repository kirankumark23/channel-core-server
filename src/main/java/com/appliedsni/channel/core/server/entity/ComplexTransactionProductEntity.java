package com.appliedsni.channel.core.server.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import channel.client.function.Status;

@Entity
@JsonInclude(value=Include.NON_NULL)
@Table(name="xComplexTransactionProduct")
public class ComplexTransactionProductEntity extends AbstractEntity{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;
	
	@Column(name="xType")
	private String mType;

	@Column(name="xStatus")
	@Enumerated(EnumType.STRING)
	private Status mStatus;

	@Column(name="xName")
	private String mName;
		
	public ComplexTransactionProductEntity(){}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status pStatus) {
		mStatus = pStatus;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String getType() {
		return mType;
	}

	public void setType(String pType) {
		mType = pType;
	}
}
