package com.appliedsni.channel.core.server.entity;

import java.io.IOException;
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

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.dao.ServerDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;

import channel.client.function.Status;

@Entity
@JsonDeserialize(using=ObjectDeseriasizer.class)
@Table(name="xSimpleTransactionProductStep")
public class SimpleTransactionProductStepEntity extends AbstractEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xSimpleTransaction", nullable = false)
	private SimpleTransactionProductEntity mSimpleTransaction;

	@Column(name="xSeqNo")
	private int mSeqNo;

	@Column(name="xDecisionStatus")
	@Enumerated(EnumType.STRING)
	private Status mStatus;
	
	@Column(name="xClass")
	private String mFunctionClass;	

	@Column(name="xFunction")
	private String mFunction;
	
	@Column(name="xDelay")
	private int mDelay;	

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

	@JsonIgnore
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

	public int getDelay() {
		return mDelay;
	}

	public void setDelay(int pDelay) {
		mDelay = pDelay;
	}

	public String getFunctionClass() {
		return mFunctionClass;
	}

	public void setFunctionClass(String pFunctionClass) {
		mFunctionClass = pFunctionClass;
	}
}

class ObjectDeseriasizer extends JsonDeserializer<SimpleTransactionProductStepEntity>{
	private ServerDao mServerDao = ChannelApplicationContext.get().getBean(ServerDao.class);
	
	@Override
	public SimpleTransactionProductStepEntity deserialize(JsonParser pJP, DeserializationContext pDC)
			throws IOException, JsonProcessingException {

		ObjectCodec oc = pJP.getCodec();
		JsonNode node = oc.readTree(pJP);
		
		SimpleTransactionProductStepEntity stps = new SimpleTransactionProductStepEntity();				
		stps.setIdKey(UUID.fromString(node.get("idKey").asText()));
		stps.setDelay(node.get("delay").asInt());
		stps.setFunction(node.get("function").asText());
		stps.setFunctionClass(node.get("functionClass").asText());
		stps.setSeqNo(node.get("seqNo").asInt());
		stps.setSimpleTransaction((SimpleTransactionProductEntity)mServerDao.get(SimpleTransactionProductEntity.class, UUID.fromString(node.get("simpleTransaction").asText())));
		stps.setStatus(Status.valueOf(node.get("status").asText()));
		
		return stps;
	}
	
}
