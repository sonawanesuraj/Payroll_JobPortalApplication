package com.app.serviceImpl;

import java.util.Date;

import com.app.entities.OtpEntity;
import com.app.repository.OtpRepository;
import com.app.serviceInterface.OtpInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpInterface {

	@Autowired
	OtpRepository otpRepository;

	@Override
	public OtpEntity saveOtp(String email, Integer otp, Long userId, Date expiry) {

		OtpEntity otpEntity = new OtpEntity();

		otpEntity.setEmail(email);
		otpEntity.setOtp(otp);
		otpEntity.setUserId(userId);
		otpEntity.setExpireAt(expiry);
		this.otpRepository.save(otpEntity);

		return otpEntity;

	}
}
