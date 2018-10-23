package com.appliedsni.channel.core.server.entity;


import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="xUser")
public class UserEntity {
	
	@Id
	@Column(name="xIdkey")
	private UUID idKey;
	UserEntity(){
		idKey=UUID.randomUUID();
	}
	@Column(name="xUserName")
	private String userName;

	public UUID getIdKey() {
		return idKey;
	}

	public void setIdKey(UUID idKey) {
		this.idKey = idKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
}
