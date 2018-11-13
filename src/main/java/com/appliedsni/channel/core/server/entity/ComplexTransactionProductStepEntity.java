package com.appliedsni.channel.core.server.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import channel.client.function.Status;

@Entity
@Table(name="xComplexTransactionProductStep")
@JsonIgnoreProperties(ignoreUnknown=true)
public class ComplexTransactionProductStepEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xComplexTransaction", nullable = false)
	@JsonIgnore(value=true)
	private ComplexTransactionProductEntity mComplexTransaction;

	@Column(name="xSeqNo")
	private int mSeqNo;

	@Column(name="xDecisionStatus")
	@Enumerated(EnumType.STRING)
	private Status mDecisionStatus;
	
	@Column(name="xDelay")
	private int mDelay;	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xSimpleTransaction", nullable = false)
	@JsonIgnore(value=true)
	private SimpleTransactionProductEntity mSimpleTransaction;

	public ComplexTransactionProductStepEntity(){}
	
	public ComplexTransactionProductStepEntity(UUID pIdKey){
		mIdKey = pIdKey;
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public ComplexTransactionProductEntity getComplexTransaction() {
		return mComplexTransaction;
	}

	public void setComplexTransaction(ComplexTransactionProductEntity pComplexTransaction) {
		mComplexTransaction = pComplexTransaction;
	}

	public int getSeqNo() {
		return mSeqNo;
	}

	public void setSeqNo(int pSeqNo) {
		mSeqNo = pSeqNo;
	}

	public Status getDecisionStatus() {
		return mDecisionStatus;
	}

	public void setDecisionStatus(Status pDecisionStatus) {
		mDecisionStatus = pDecisionStatus;
	}

	public SimpleTransactionProductEntity getSimpleTransaction() {
		return mSimpleTransaction;
	}

	public void setSimpleTransaction(SimpleTransactionProductEntity pSimpleTransaction) {
		mSimpleTransaction = pSimpleTransaction;
	}

	public int getDelay() {
		return mDelay;
	}

	public void setDelay(int pDelay) {
		mDelay = pDelay;
	}
}
