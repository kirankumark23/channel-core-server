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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.handler.SimpleTransactionHandler;
import com.appliedsni.channel.core.server.user.domain.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import channel.client.function.Status;

@Entity
@Table(name="xProductRoleAccess")
public class ProductRoleAccessEntity extends AbstractEntity implements Serializable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRoleAccessEntity.class);

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xProduct", nullable = false)
	private ComplexTransactionProductEntity mComplexProduct;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "xRole", nullable = false)
	private RoleEntity mRole;	
	
	@Column(name="xAdded")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mAdded = new Date();

	public ProductRoleAccessEntity(){
		mIdKey = UUID.randomUUID();
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public ComplexTransactionProductEntity getComplexProduct() {
		return mComplexProduct;
	}

	public void setComplexProduct(ComplexTransactionProductEntity pComplexProduct) {
		mComplexProduct = pComplexProduct;
	}

	public RoleEntity getRole() {
		return mRole;
	}

	public void setRole(RoleEntity pRole) {
		mRole = pRole;
	}

	public Date getAdded() {
		return mAdded;
	}

	public void setAdded(Date pAdded) {
		mAdded = pAdded;
	}

}
