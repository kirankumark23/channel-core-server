package com.appliedsni.channel.core.server.entity;

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

@Entity
@Table(name="xComplexTransaction")
public class ComplexTransactionEntity {

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "xProduct", nullable = false)
	@JsonIgnore(value=true)
	private ComplexTransactionProductEntity mProduct;
	
	@Column(name="xStatus")
	@Enumerated(EnumType.STRING)
	private Status mStatus;
	
	public ComplexTransactionEntity(){}
	
	public ComplexTransactionEntity(UUID pIdKey, ComplexTransactionProductEntity pCTProduct){
		mIdKey = pIdKey;
		mProduct = pCTProduct;
		mStatus = Status.OPEN;
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public ComplexTransactionProductEntity getProduct() {
		return mProduct;
	}

	public void setProduct(ComplexTransactionProductEntity pProduct) {
		mProduct = pProduct;
	}

	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status pStatus) {
		mStatus = pStatus;
	}

}
