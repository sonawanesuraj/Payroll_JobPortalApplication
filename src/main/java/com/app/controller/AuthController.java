package com.app.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.app.configuration.JwtTokenUtil;
import com.app.dto.AuthResponceDto;
import com.app.dto.EmailOtpDto;
import com.app.dto.ErrorResponseDto;
import com.app.dto.ForgotPasswordConfirmDto;
import com.app.dto.JwtRequestDto;
import com.app.dto.LoggerDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDto;
import com.app.entities.OtpEntity;
import com.app.entities.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AuthRepository;
import com.app.repository.OtpRepository;
import com.app.serviceImpl.AuthServiceImpl;
import com.app.serviceImpl.LoggerServiceImpl;
import com.app.serviceImpl.OtpServiceImpl;
import com.app.serviceInterface.AuthInterface;
import com.app.serviceInterface.EmailInterface;
import com.app.serviceInterface.LoggerInterface;
import com.app.serviceInterface.UserRoleInterface;
import com.app.util.ApiUrls;
import com.app.util.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private AuthInterface authInterface;

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Autowired
	private JwtTokenUtil JwtTokenUtil;

	@Autowired
	private LoggerServiceImpl loggerServiceImpl;

	@Autowired
	private LoggerInterface loggerInterface;

	@Autowired
	private EmailInterface emailInterface;

	@Autowired
	private OtpServiceImpl OtpserviceImpl;

	@Autowired
	private UserRoleInterface userRoleInterface;

	@Autowired
	private OtpRepository otpRepository;

	@PostMapping(ApiUrls.REGISTER)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception, DataIntegrityViolationException {

		String email = userDto.getEmail();
		String password = userDto.getPassword();

		if (PasswordValidator.isValidforEmail(email)) {

			if (PasswordValidator.isValid(password)) {

				UserEntity databaseName = authRepository.findByEmailContainingIgnoreCase(email);
				if (databaseName == null) {
					authInterface.addUser(userDto);
					return new ResponseEntity<>(new SuccessResponseDto("User Created", "userCreated", "data added"),
							HttpStatus.CREATED);

				} else {
					return new ResponseEntity<>(
							new ErrorResponseDto("User Email Id Already Exist", "userEmailIdAlreadyExist"),
							HttpStatus.BAD_REQUEST);
				}
			} else {

				return ResponseEntity.ok(new ErrorResponseDto(
						"Password should have Minimum 8 and maximum 20 characters, at least one uppercase letter, one lowercase letter, one number and one special character and No White Spaces",
						"Password validation not done"));
			}

		} else {
			return new ResponseEntity<>(new ErrorResponseDto("please check Email is not valid ", "Enter valid email"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping(ApiUrls.LOGIN)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequestDto createAuthenticationToken)
			throws Exception {
		try {
			UserEntity user = authRepository.findByEmailContainingIgnoreCase(createAuthenticationToken.getEmail());

			if (this.authServiceImpl.comparePassword(createAuthenticationToken.getPassword(), user.getPassword())) {

				final UserDetails userDetails = this.authServiceImpl
						.loadUserByUsername(createAuthenticationToken.getEmail());

				final UserEntity userEntity = this.authRepository
						.findByEmailContainingIgnoreCase(createAuthenticationToken.getEmail());

				final String token = this.JwtTokenUtil.generateToken(userDetails);

				ArrayList<String> permissions = authInterface.getUserPermission(user.getId());

				List<String> roles = this.userRoleInterface.getRoleByUserId(user.getId());

				LoggerDto loggerDto = new LoggerDto();

				loggerDto.setToken(token);

				Calendar calendar = Calendar.getInstance();

				calendar.add(Calendar.HOUR_OF_DAY, 5);

				loggerDto.setExpireAt(calendar.getTime());

				this.loggerInterface.createLogger(loggerDto, userEntity);

				return new ResponseEntity<>(
						new SuccessResponseDto("Login successfull", "token", new AuthResponceDto(token, permissions,
								userEntity.getName(), userEntity.getEmail(), userEntity.getId(), roles)),
						HttpStatus.OK);

			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Invalid password", "Please enter valid password"),
						HttpStatus.BAD_REQUEST);

			}
		} catch (Exception e) {

			return new ResponseEntity<>(
					new ErrorResponseDto("Invalid email or Password", "Please enter valid email or password"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(ApiUrls.FORGOT_PASSWORD)
	public ResponseEntity<?> forgotPassword(@RequestBody EmailOtpDto emailOtpDto) {
		try {
			UserEntity userEntity = this.authRepository.findByEmailContainingIgnoreCase(emailOtpDto.getEmail());
			if (userEntity == null) {
				return new ResponseEntity<>(new ErrorResponseDto("User not found", "check your email"),
						HttpStatus.BAD_REQUEST);
			}

			this.emailInterface.generateOtpAndSendEmail(emailOtpDto.getEmail(), userEntity.getId());
			return new ResponseEntity<>(
					new SuccessResponseDto("OTP send user email", "OTP send succesfully", userEntity.getEmail()),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("User not found", "Not found"), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(ApiUrls.FORGOT_PASSWORD_CONFIRM)
	public ResponseEntity<?> forgotPasswordConfirm(@RequestBody ForgotPasswordConfirmDto forgotPasswordConfirmDto)
			throws Exception {

		try {
			UserEntity userEntity = this.authRepository
					.findByEmailContainingIgnoreCase(forgotPasswordConfirmDto.getEmail());

			OtpEntity otpEntity = this.otpRepository.findByOtp(forgotPasswordConfirmDto.getOtp());

			if (null == otpEntity) {
				return new ResponseEntity<>(new ErrorResponseDto("Check your OTP", "Enter valid OTP"),
						HttpStatus.BAD_REQUEST);
			} else {
				if (!otpEntity.getEmail().equals(forgotPasswordConfirmDto.getEmail())) {
					return new ResponseEntity<>(new ErrorResponseDto("Invalid email", "Enter valid email"),
							HttpStatus.BAD_REQUEST);
				}
			}

			if (!PasswordValidator.isValid(forgotPasswordConfirmDto.getPassword())) {
				return new ResponseEntity<>(new ErrorResponseDto(
						"Password should have Minimum 8 and maximum 50 characters, at least one uppercase letter, one lowercase letter, one number and one special character and no white spaces",
						"Enter valid password"), HttpStatus.BAD_REQUEST);
			}

			this.authInterface.forgotPasswordConfirm(forgotPasswordConfirmDto, userEntity, otpEntity);

			return new ResponseEntity<>(new SuccessResponseDto("Password updated successfully", "successfull"),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Not found"), HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@PostMapping(ApiUrls.LOGOUT)
	public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token, HttpServletRequest request) {
		loggerInterface.logout(token);
		return new ResponseEntity<>(new ErrorResponseDto("Logout Successfully", "logoutSuccess"), HttpStatus.OK);

	}

}
