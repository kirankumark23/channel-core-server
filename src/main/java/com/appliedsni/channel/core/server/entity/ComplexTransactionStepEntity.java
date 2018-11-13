package com.appliedsni.channel.core.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Access;
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
import com.fasterxml.jackson.annotation.JsonProperty;

import channel.client.function.Status;

@Entity
@Table(name="xComplexTransactionStep")
@JsonIgnoreProperties(value={"mComplexTransaction", "mSimpleTransaction"})
public class ComplexTransactionStepEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xComplexTransaction", nullable = false)
	@JsonIgnore(value=true)
	private ComplexTransactionEntity mComplexTransaction;

	@Column(name="xSeqNo")
	private int mSeqNo;

	@Column(name="xDecisionStatus")
	@Enumerated(EnumType.STRING)
	private Status mDecisionStatus;
	
	@Column(name="xDelay")
	private int mDelay;	
	
	@Column(name="xExecutionStatus")
	@Enumerated(EnumType.STRING)
	private Status mExecutionStatus;
	
	@Column(name="xResultStatus")
	@Enumerated(EnumType.STRING)
	private Status mResultStatus;
	
	@Column(name="xData")
	private String mData;
	
	@Column(name="xAdded")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mAdded = new Date();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xSimpleTransaction", nullable = false)
	private SimpleTransactionEntity mSimpleTransaction;

	public ComplexTransactionStepEntity(){
		mIdKey = UUID.randomUUID();
	}
	
	public ComplexTransactionStepEntity(
			UUID pIdKey, 
			ComplexTransactionEntity pComplexTransaction,
			SimpleTransactionEntity pSimpleTransaction,
			ComplexTransactionProductStepEntity pComplexTransactionProductStep){
		mIdKey = pIdKey;
		mSeqNo = pComplexTransactionProductStep.getSeqNo();
		mComplexTransaction = pComplexTransaction;
		mDecisionStatus = pComplexTransactionProductStep.getDecisionStatus();
		mSimpleTransaction = pSimpleTransaction;
		mExecutionStatus = Status.OPEN;
		mResultStatus = Status.OPEN;
		mDelay = pComplexTransactionProductStep.getDelay();
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public ComplexTransactionEntity getComplexTransaction() {
		return mComplexTransaction;
	}

	public void setComplexTransaction(ComplexTransactionEntity pComplexTransaction) {
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

	public SimpleTransactionEntity getSimpleTransaction() {
		return mSimpleTransaction;
	}

	public void setSimpleTransaction(SimpleTransactionEntity pSimpleTransaction) {
		mSimpleTransaction = pSimpleTransaction;
	}

	public int getDelay() {
		return mDelay;
	}

	public void setDelay(int pDelay) {
		mDelay = pDelay;
	}

	public Date getAdded() {
		return mAdded;
	}

	public void setAdded(Date pAdded) {
		mAdded = pAdded;
	}
}
