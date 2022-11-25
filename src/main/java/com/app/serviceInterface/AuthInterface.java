package com.app.serviceInterface;

import java.io.IOException;
import java.util.ArrayList;

import com.app.dto.ForgotPasswordConfirmDto;
import com.app.dto.UserDto;
import com.app.entities.OtpEntity;
import com.app.entities.UserEntity;

public interface AuthInterface {

	void addUser(UserDto user);

	boolean forgotPasswordConfirm(ForgotPasswordConfirmDto passwordConfirmDto, UserEntity userEntity,
			OtpEntity otpEntity) throws Exception;

	ArrayList<String> getUserPermission(Long userId) throws IOException;

}
