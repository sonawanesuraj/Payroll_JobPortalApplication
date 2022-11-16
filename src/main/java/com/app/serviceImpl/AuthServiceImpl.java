package com.app.serviceImpl;

import java.util.ArrayList;

import com.app.dto.ForgotPasswordConfirmDto;
import com.app.dto.UserDto;
import com.app.entities.OtpEntity;
import com.app.entities.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.AuthRepository;
import com.app.repository.OtpRepository;
import com.app.serviceInterface.AuthInterface;
import com.app.serviceInterface.RolePermissionInterface;
import com.app.util.CacheOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthInterface, UserDetailsService {
	private static final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RolePermissionInterface rolePermissionInterface;

	@Autowired
	private CacheOperation cache;

	@Autowired
	private OtpRepository otpRepository;

	@Override
	public void addUser(UserDto user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(user.getName());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		authRepository.save(userEntity);

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = new UserEntity();

		userEntity = this.authRepository.findByEmailContainingIgnoreCase(email);

		if (userEntity.getEmail().isEmpty()) {
			throw new ResourceNotFoundException("User OR Password not found");
		}

		return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
				getAuthority(userEntity));
	}

	// for compare password
	public Boolean comparePassword(String password, String hashPassword) {

		return passwordEncoder.matches(password, hashPassword);

	}

	private ArrayList<SimpleGrantedAuthority> getAuthority(UserEntity user) {
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if ((user.getId() + "permission") != null) {
			ArrayList<SimpleGrantedAuthority> authorities1 = new ArrayList<>();

			ArrayList<String> permissions = this.rolePermissionInterface.getPermissionByUserId(user.getId());

			permissions.forEach(e -> {
				authorities1.add(new SimpleGrantedAuthority("ROLE_" + e));

			});
			authorities = authorities1;
		}
		return authorities;
	}

	@Override
	public boolean forgotPasswordConfirm(ForgotPasswordConfirmDto passwordConfirmDto, UserEntity userEntity,
			OtpEntity otpEntity) throws Exception {
		LOG.info("AuthServiceImpl >> forgotPasswordConfirm() >> ");

		LOG.info("AuthServiceImpl >> forgotPasswordConfirm() >> check user email");

		userEntity.setPassword(passwordEncoder.encode(passwordConfirmDto.getPassword()));

		this.authRepository.save(userEntity);
		this.otpRepository.deleteAll();
		LOG.info("AuthServiceImpl >> forgotPasswordConfirm() >> Done password changed");
		return false;

	}

}
