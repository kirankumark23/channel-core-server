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

import com.appliedsni.channel.core.server.user.domain.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import channel.client.function.Status;

@Entity
@Table(name="xCustomerMandateService")
//@JsonDeserialize(using=ObjectDeserializer.class)
public class CustomerMandateServiceEntity extends AbstractEntity implements Serializable{

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xMandate", nullable = false)
	private CustomerMandateServiceEntity mMandate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xChannel", nullable = false)
	private RoleEntity mChannel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xComplexTransaction", nullable = false)
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

	public CustomerMandateServiceEntity getMandate() {
		return mMandate;
	}

	public void setMandate(CustomerMandateServiceEntity pMandate) {
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

}
