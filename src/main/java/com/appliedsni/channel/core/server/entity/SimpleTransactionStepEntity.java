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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.handler.SimpleTransactionHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="xSimpleTransactionStep")
@JsonIgnoreProperties(ignoreUnknown=true)
public class SimpleTransactionStepEntity implements Serializable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTransactionStepEntity.class);

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
	
	@Column(name="xDelay")
	private int mDelay;	
	
	@Column(name="xAdded")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mAdded = new Date();

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
		mDelay = pSimpleTransactionProductStep.getDelay();
	}
	
	public boolean isExecute(){
		//	Delay in minutes
		LOGGER.warn("Time difference : {} : {}", (new Date()).getTime() - mAdded.getTime(), (mDelay * 60 * 1000));
		return ((new Date()).getTime() - mAdded.getTime()) > (mDelay * 60 * 1000);
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

	public Date getAdded() {
		return mAdded;
	}

	public void setAdded(Date pAdded) {
		mAdded = pAdded;
	}

	public int getDelay() {
		return mDelay;
	}

	public void setDelay(int pDelay) {
		mDelay = pDelay;
	}

}
