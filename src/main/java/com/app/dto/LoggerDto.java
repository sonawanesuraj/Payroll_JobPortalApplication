package com.app.dto;

import java.util.Date;

public class LoggerDto {

	public String token;

	public Long userId;

	public Date expireAt;

	public LoggerDto(String token, Long userId, Date expireAt) {
		super();
		this.token = token;
		this.userId = userId;
		this.expireAt = expireAt;
	}

	public LoggerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}

}
