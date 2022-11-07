package com.app.dto;

public class SuccessResponseDto {

	public SuccessResponseDto() {

		// TODO Auto-generated constructor stub
	}

	public SuccessResponseDto(String message, String msgKey) {
		super();
		this.message = message;
		this.msgKey = msgKey;
	}

	public SuccessResponseDto(String message, String msgKey, Object data) {

		super();
		this.message = message;
		this.msgKey = msgKey;
		this.data = data;

	}

	private String message;

	private String msgKey;

	private Object data;

	public String getMessage() {

		return message;

	}

	public void setMessage(String message) {

		this.message = message;

	}

	public String getMsgKey() {

		return msgKey;

	}

	public void setMsgKey(String msgKey) {

		this.msgKey = msgKey;

	}

	public Object getData() {

		return data;

	}

	public void setData(Object data) {

		this.data = data;

	}
}
