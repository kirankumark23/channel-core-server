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

@Entity
@Table(name="xSimpleTransactionStep")
@JsonIgnoreProperties(ignoreUnknown=true)
public class SimpleTransactionStepEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xSimpleTransaction", nullable = false)
	@JsonIgnore(value=true)
	private SimpleTransactionEntity mSimpleTransaction;

	@Column(name="xSeqNo")
	private int mSeqNo;

	@Column(name="xDecisionStatus")
	@Enumerated(EnumType.STRING)
	private Status mDecisionStatus;
	
	@Column(name="xExecutionStatus")
	@Enumerated(EnumType.STRING)
	private Status mExecutionStatus;
	
	@Column(name="xResultStatus")
	@Enumerated(EnumType.STRING)
	private Status mResultStatus;
	
	@Column(name="xData")
	private String mData;
	
	@Column(name="xFunction")
	private String mFunction;

	public SimpleTransactionStepEntity(){
		mIdKey = UUID.randomUUID();
	}
	
	public SimpleTransactionStepEntity(
			UUID pIdKey, 
			SimpleTransactionProductStepEntity pSimpleTransactionProductStep,
			SimpleTransactionEntity pSimpleTransaction){
		mIdKey = pIdKey;
		mSeqNo = pSimpleTransactionProductStep.getSeqNo();
		mDecisionStatus = pSimpleTransactionProductStep.getStatus();
		mFunction = pSimpleTransactionProductStep.getFunction();
		mSimpleTransaction = pSimpleTransaction;		
		mExecutionStatus = Status.OPEN;
		mResultStatus = Status.OPEN;
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public SimpleTransactionEntity getSimpleTransaction() {
		return mSimpleTransaction;
	}

	public void setSimpleTransaction(SimpleTransactionEntity pSimpleTransaction) {
		mSimpleTransaction = pSimpleTransaction;
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

	public Status getExecutionStatus() {
		return mExecutionStatus;
	}

	public void setExecutionStatus(Status pExecutionStatus) {
		mExecutionStatus = pExecutionStatus;
	}

	public Status getResultStatus() {
		return mResultStatus;
	}

	public void setResultStatus(Status pResultStatus) {
		mResultStatus = pResultStatus;
	}

	public String getData() {
		return mData;
	}

	public void setData(String pData) {
		mData = pData;
	}

	public String getFunction() {
		return mFunction;
	}

	public void setFunction(String pFunction) {
		mFunction = pFunction;
	}

}
