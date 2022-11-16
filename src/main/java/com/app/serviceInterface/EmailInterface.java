package com.app.serviceInterface;

import javax.mail.MessagingException;

import com.app.entities.UserEntity;

public interface EmailInterface {

	public void sendSimpleMessage(String emailTo, String subject, String text) throws MessagingException;

	public String sendMail(String emailTo, String subject, String text, UserEntity userEntity);

	public void generateOtpAndSendEmail(String email, Long id) throws MessagingException;

	int generateOTP();

}
