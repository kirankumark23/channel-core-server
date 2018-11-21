package com.appliedsni.channel.core.server.entity;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import channel.client.dao.ServerDao;
import channel.client.function.Status;

public class ObjectDeserializer extends JsonDeserializer<ComplexTransactionProductStepEntity>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectDeserializer.class);
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
