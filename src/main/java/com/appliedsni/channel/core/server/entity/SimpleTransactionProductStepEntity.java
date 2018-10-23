package com.appliedsni.channel.core.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;

@Entity
@Table(name="xSimpleTransactionProductStep")
@JsonIgnoreProperties(ignoreUnknown=true)
public class SimpleTransactionProductStepEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xSimpleTransaction", nullable = false)
	@JsonIgnore(value=true)
	private SimpleTransactionProductEntity mSimpleTransaction;

	@Column(name="xSeqNo")
	private int mSeqNo;

	@Column(name="xDecisionStatus")
	@Enumerated(EnumType.STRING)
	private Status mStatus;

	@Column(name="xFunction")
	private String mFunction;

	public SimpleTransactionProductStepEntity(){
		mIdKey = UUID.randomUUID();
	}
	
	public SimpleTransactionProductStepEntity(UUID pIdKey){
		mIdKey = pIdKey;
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public SimpleTransactionProductEntity getSimpleTransaction() {
		return mSimpleTransaction;
	}

	public void setSimpleTransaction(SimpleTransactionProductEntity pSimpleTransaction) {
		mSimpleTransaction = pSimpleTransaction;
	}

	public int getSeqNo() {
		return mSeqNo;
	}

	public void setSeqNo(int pSeqNo) {
		mSeqNo = pSeqNo;
	}

	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status pStatus) {
		mStatus = pStatus;
	}

	public String getFunction() {
		return mFunction;
	}

	public void setFunction(String pFunction) {
		mFunction = pFunction;
	}	

}
