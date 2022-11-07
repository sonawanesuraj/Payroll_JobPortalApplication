package com.app.serviceInterface;

import com.app.dto.LoggerDto;
import com.app.entities.LoggerEntity;
import com.app.entities.UserEntity;

public interface LoggerInterface {
	LoggerEntity createLogger(LoggerDto dto, UserEntity entity);

	LoggerEntity getLoggerDetail(String token);

	void logout(String token);

}
