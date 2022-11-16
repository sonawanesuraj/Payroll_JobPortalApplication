package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmailOtpDto {
	@NotBlank(message = "email is Required*emailRequired")
	@NotEmpty(message = "email is Required*emailRequired")
	@NotNull(message = "email is Required*emailRequired")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmailOtpDto(String email) {
		super();
		this.email = email;
	}

	public EmailOtpDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
