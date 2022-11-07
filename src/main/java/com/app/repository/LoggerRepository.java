package com.app.repository;

import com.app.entities.LoggerEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerRepository extends JpaRepository<LoggerEntity, Long> {

	LoggerEntity findByToken(String token);

	void removeByToken(String token1);

}
