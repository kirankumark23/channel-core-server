package com.appliedsni.channel.core.server.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.user.domain.RoleEntity;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import channel.client.dao.ServerDao;

@Entity
@Table(name="xCustomerMandateService")
@JsonDeserialize(using=CustomerMandateServiceEntity.ObjectDeseriasizer.class)
public class CustomerMandateServiceEntity extends AbstractEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xMandate", nullable = false)
	private CustomerMandateEntity mMandate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xChannel", nullable = false)
	private RoleEntity mChannel;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xProduct", nullable = false)
	private ComplexTransactionProductEntity mProduct;

	public CustomerMandateServiceEntity(){}
	
	public CustomerMandateServiceEntity(UUID pIdKey){
		mIdKey = pIdKey;
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public CustomerMandateEntity getMandate() {
		return mMandate;
	}

	public void setMandate(CustomerMandateEntity pMandate) {
		mMandate = pMandate;
	}

	public RoleEntity getChannel() {
		return mChannel;
	}

	public void setChannel(RoleEntity pChannel) {
		mChannel = pChannel;
	}

	public ComplexTransactionProductEntity getProduct() {
		return mProduct;
	}

	public void setProduct(ComplexTransactionProductEntity pProduct) {
		mProduct = pProduct;
	}
	
	static class ObjectDeseriasizer extends JsonDeserializer<CustomerMandateServiceEntity>{
		private ServerDao mServerDao = ChannelApplicationContext.get().getBean(ServerDao.class);

		public ObjectDeseriasizer(){}
		
		@Override
		public CustomerMandateServiceEntity deserialize(JsonParser pJP, DeserializationContext pDC)
				throws IOException, JsonProcessingException {

			ObjectCodec oc = pJP.getCodec();
			JsonNode node = oc.readTree(pJP);
						
			CustomerMandateServiceEntity service = new CustomerMandateServiceEntity();
			service.setIdKey(UUID.fromString(node.get("idKey").asText()));
			service.setMandate((CustomerMandateEntity)mServerDao.get(CustomerMandateEntity.class, UUID.fromString(node.get("mandate").asText())));
			service.setChannel((RoleEntity)mServerDao.get(RoleEntity.class, UUID.fromString(node.get("channel").asText())));
			service.setProduct((ComplexTransactionProductEntity)mServerDao.get(ComplexTransactionProductEntity.class, UUID.fromString(node.get("product").asText())));
			
			return service;
		}
	}

}
