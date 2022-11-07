package com.app.repository;

import com.app.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmailContainingIgnoreCase(String email);

}
