package com.app.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "logger_entity")
public class LoggerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserEntity userId;

	private String token;

	@CreationTimestamp
	private Date createdAt;

	private Date expireAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getUserId() {
		return userId;
	}

	public void setUserId(UserEntity userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}

	public LoggerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoggerEntity(long id, UserEntity userId, String token, Date createdAt, Date expireAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.token = token;
		this.createdAt = createdAt;
		this.expireAt = expireAt;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", userId:" + userId + ", token:" + token + "}";
	}

}
