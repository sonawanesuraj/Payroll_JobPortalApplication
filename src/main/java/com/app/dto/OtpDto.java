package com.app.dto;

import java.util.Date;

public class OtpDto {

	private long id;

	private Integer otp;

	private String email;

	private Date expireAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}

	public OtpDto(long id, Integer otp, String email, Date expireAt) {
		super();
		this.id = id;
		this.otp = otp;
		this.email = email;
		this.expireAt = expireAt;
	}

	public OtpDto() {
		super();
	}

}
