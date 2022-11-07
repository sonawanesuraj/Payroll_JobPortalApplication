package com.app.repository;

import javax.transaction.Transactional;

import com.app.entities.OtpEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

	OtpEntity findByEmailContainingIgnoreCase(String email);

	OtpEntity findByOtp(Integer otp);

	OtpEntity findByEmail(String email);

	@Query(value = "DELETE FROM otp_logger u WHERE u.email=:email", nativeQuery = true)
	@Transactional
	@Modifying
	void deleteAllByEmail(@Param("email") String email);
}
