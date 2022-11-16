package com.app.serviceInterface;

import com.app.dto.ForgotPasswordConfirmDto;
import com.app.dto.UserDto;
import com.app.entities.OtpEntity;
import com.app.entities.UserEntity;

public interface AuthInterface {

	void addUser(UserDto user);

	boolean forgotPasswordConfirm(ForgotPasswordConfirmDto passwordConfirmDto, UserEntity userEntity,
			OtpEntity otpEntity) throws Exception;
}
