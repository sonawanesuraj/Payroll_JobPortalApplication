package com.app.serviceInterface;

import java.util.Date;

import com.app.entities.OtpEntity;

public interface OtpInterface {

	public OtpEntity saveOtp(String email, Integer otp, Long userId, Date expiry);

}
