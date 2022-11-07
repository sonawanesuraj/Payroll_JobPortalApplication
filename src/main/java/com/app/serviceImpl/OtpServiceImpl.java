package com.app.serviceImpl;

import com.app.dto.OtpDto;
import com.app.entities.OtpEntity;
import com.app.entities.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.OtpRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl {

	@Autowired
	private OtpRepository otpRepository;

	public OtpEntity saveOtp(OtpDto otpDto, UserEntity users) throws Exception {
		try {

			OtpEntity otpEntity = this.otpRepository.findByEmailContainingIgnoreCase(otpDto.getEmail());

			if (otpEntity != null) {

				throw new ResourceNotFoundException("Something went wrong");
			}

			else {

				OtpEntity entity = new OtpEntity();
				entity.setUserId(users);
				entity.setEmail(users.getEmail());
				entity.setOtp(otpDto.getOtp());
				entity.setExpireAt(otpDto.getExpireAt());

				otpRepository.save(entity);
				return entity;
			}
		} catch (Exception e) {

			throw new Exception("Something went wrong !!!!");
		}

	}

}
