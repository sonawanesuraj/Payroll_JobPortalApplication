package com.app.serviceImpl;

import com.app.dto.LoggerDto;
import com.app.entities.LoggerEntity;
import com.app.entities.UserEntity;
import com.app.repository.LoggerRepository;
import com.app.serviceInterface.LoggerInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerServiceImpl implements LoggerInterface {

	@Autowired
	private LoggerRepository loggerRepository;

	@Override
	public LoggerEntity createLogger(LoggerDto dto, UserEntity entity) {
		LoggerEntity logger = new LoggerEntity();

		logger.setUserId(entity);
		logger.setToken(dto.getToken());
		logger.setExpireAt(dto.getExpireAt());
		loggerRepository.save(logger);
		return logger;
	}

	@Override
	public LoggerEntity getLoggerDetail(String token) {

		LoggerEntity loggerEntity;

		loggerEntity = this.loggerRepository.findByToken(token);
		return loggerEntity;
	}

	@Override
	public void logout(String token) {
		final String token1 = token.substring(7);
		loggerRepository.removeByToken(token1);
	}

}
