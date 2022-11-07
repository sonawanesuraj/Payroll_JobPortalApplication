package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ForgotPasswordConfirmDto {

	@NotBlank(message = "email is Required*emailRequired")
	@NotEmpty(message = "email is Required*emailRequired")
	@NotNull(message = "email is Required*emailRequired")
	String email;

	Integer otp;

	@NotBlank(message = "password is Required*passwordRequired")
	@NotEmpty(message = "password is Required*passwordRequired")
	@NotNull(message = "password is Required*passwordRequired")
	String password;

	public ForgotPasswordConfirmDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForgotPasswordConfirmDto(String email, Integer otp) {
		super();
		this.email = email;
		this.otp = otp;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
