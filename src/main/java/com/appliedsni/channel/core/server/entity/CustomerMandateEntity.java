package com.appliedsni.channel.core.server.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import channel.client.dao.ServerDao;

@Entity
@JsonDeserialize(using=CustomerMandateEntity.ObjectDeseriasizer.class)
@Table(name="xCustomerMandate")
public class CustomerMandateEntity extends AbstractEntity implements Serializable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMandateEntity.class);

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xCustomer", nullable = false)
	private CustomerEntity mCustomer;

	@Column(name="xAdded")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mAdded = new Date();

	public CustomerMandateEntity(){
		mIdKey = UUID.randomUUID();
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public Date getAdded() {
		return mAdded;
	}

	public void setAdded(Date pAdded) {
		mAdded = pAdded;
	}

	public CustomerEntity getCustomer() {
		return mCustomer;
	}

	public void setCustomer(CustomerEntity pCustomer) {
		mCustomer = pCustomer;
	}	
	
	static class ObjectDeseriasizer extends JsonDeserializer<CustomerMandateEntity>{
		private ServerDao mServerDao = ChannelApplicationContext.get().getBean(ServerDao.class);

		public ObjectDeseriasizer(){}
		
		@Override
		public CustomerMandateEntity deserialize(JsonParser pJP, DeserializationContext pDC)
				throws IOException, JsonProcessingException {

			ObjectCodec oc = pJP.getCodec();
			JsonNode node = oc.readTree(pJP);
						
			CustomerMandateEntity mandate = new CustomerMandateEntity();
			mandate.setIdKey(UUID.fromString(node.get("idKey").asText()));
			mandate.setCustomer((CustomerEntity)mServerDao.get(CustomerEntity.class, UUID.fromString(node.get("customer").asText())));
			mandate.setVersion(node.get("version").asInt());
//			mandate.setAdded(pAdded);
//			mandate.setLastUpdate(pLastUpdate);
			
			return mandate;
		}
	}
}
