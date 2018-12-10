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
@Table(name="xCustomer")
public class CustomerEntity extends AbstractEntity implements Serializable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEntity.class);

	@Id
	@Column(name="xIdkey")
	@Type(type="pg-uuid")
	private UUID mIdKey;
	
	@Column(name="xNumber")
	private Integer mNumber;

	@Column(name="xEmail")
	private String mEmail;

	@Column(name="xFName")
	private String mFName;

	@Column(name="xLName")
	private String mLName;

	@Column(name="xAdded")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mAdded = new Date();

	public CustomerEntity(){
		mIdKey = UUID.randomUUID();
	}

	public UUID getIdKey() {
		return mIdKey;
	}

	public void setIdKey(UUID pIdKey) {
		mIdKey = pIdKey;
	}

	public Integer getNumber() {
		return mNumber;
	}

	public void setNumber(Integer pNumber) {
		mNumber = pNumber;
	}

	public String getFName() {
		return mFName;
	}

	public void setFName(String pFName) {
		mFName = pFName;
	}

	public String getLName() {
		return mLName;
	}

	public void setLName(String pLName) {
		mLName = pLName;
	}

	public Date getAdded() {
		return mAdded;
	}

	public void setAdded(Date pAdded) {
		mAdded = pAdded;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String pEmail) {
		mEmail = pEmail;
	}

}
