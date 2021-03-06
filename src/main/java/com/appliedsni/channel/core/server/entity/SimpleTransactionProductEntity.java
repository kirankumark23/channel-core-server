package com.appliedsni.channel.core.server.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.appliedsni.channel.core.server.common.annotations.Auditable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import channel.client.function.Status;

@Entity
@JsonInclude(value=Include.NON_NULL)
@Table(name="xSimpleTransactionProduct")
@Auditable
public class SimpleTransactionProductEntity extends AbstractEntity{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@Column(name="xStatus")
	@Enumerated(EnumType.STRING)
	private Status mStatus;

	@Column(name="xName")
	private String mName;
	
	public SimpleTransactionProductEntity(){}

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
}
