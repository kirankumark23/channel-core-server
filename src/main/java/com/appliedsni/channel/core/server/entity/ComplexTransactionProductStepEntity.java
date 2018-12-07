package com.appliedsni.channel.core.server.entity;

import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import channel.client.dao.ServerDao;
import channel.client.function.Status;

@Entity
@Table(name="xComplexTransactionProductStep")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonDeserialize(using=ComplexTransactionProductStepEntity.ObjectDeserializer.class)
public class ComplexTransactionProductStepEntity extends AbstractEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xComplexTransaction", nullable = false)
	private ComplexTransactionProductEntity mComplexTransaction;

	@Column(name="xSeqNo")
	private int mSeqNo;

	@Column(name="xDecisionStatus")
	@Enumerated(EnumType.STRING)
	private Status mDecisionStatus;
	
	@Column(name="xDelay")
	private int mDelay;	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xSimpleTransaction", nullable = false)
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

	@JsonIgnore
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
	
	static class ObjectDeserializer extends JsonDeserializer<ComplexTransactionProductStepEntity>{
		private final Logger LOGGER = LoggerFactory.getLogger(ObjectDeserializer.class);
		private ServerDao mServerDao = ChannelApplicationContext.get().getBean(ServerDao.class);

		@Override
		public ComplexTransactionProductStepEntity deserialize(JsonParser pJP, DeserializationContext pDC)
				throws IOException, JsonProcessingException {
			ObjectCodec oc = pJP.getCodec();
			JsonNode node = oc.readTree(pJP);
			
			UUID mIdKey = UUID.fromString(node.get("idKey").asText());
			ComplexTransactionProductEntity mComplexTransaction = (ComplexTransactionProductEntity)mServerDao.get(ComplexTransactionProductEntity.class, UUID.fromString(node.get("complexTransaction").asText()));
			int mSeqNo = node.get("seqNo").asInt();
			Status mDecisionStatus = Status.valueOf(node.get("decisionStatus").asText());
			int mDelay = node.get("delay").asInt();
			SimpleTransactionProductEntity mSimpleTransaction = (SimpleTransactionProductEntity)mServerDao.get(SimpleTransactionProductEntity.class, UUID.fromString(node.get("simpleTransaction").asText()));
			
			ComplexTransactionProductStepEntity ctps = new ComplexTransactionProductStepEntity();
			ctps.setIdKey(mIdKey);
			ctps.setComplexTransaction(mComplexTransaction);
			ctps.setSimpleTransaction(mSimpleTransaction);
			ctps.setSeqNo(mSeqNo);
			ctps.setDelay(mDelay);
			ctps.setDecisionStatus(mDecisionStatus);
			
			LOGGER.warn("Deserializing ComplexTransactionStepEntity !!");
			
			return ctps;
		}
	}
}

