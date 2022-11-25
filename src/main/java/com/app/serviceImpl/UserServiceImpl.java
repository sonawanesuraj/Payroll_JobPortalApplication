package com.app.serviceImpl;

import java.util.List;

import com.app.dto.IUserListDto;
import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.UserRepository;
import com.app.serviceInterface.UserInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Page<IUserListDto> getAllUsers(String search, String pageNo, String pageSize) {

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		Page<IUserListDto> iUserListDto;
		if ((search == "") || (search == null) || (search.length() == 0)) {

			iUserListDto = userRepository.findByOrderByIdDesc(pageable, IUserListDto.class);
		} else {

			iUserListDto = userRepository.findByNameIgnoreCaseContaining(search, pageable, IUserListDto.class);
		}
		return iUserListDto;

	}

	@Override
	public List<IUserListDto> getUserById(Long id) {
		List<IUserListDto> iUserListDto;
		return iUserListDto = this.userRepository.findById(id, IUserListDto.class);

	}

	@Override
	public UserDto updateUser(Long id, UserDto userDto) {
		UserEntity userEntity = this.userRepository.findById(id).orElseThrow();
		userEntity.setName(userDto.getName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		this.userRepository.save(userEntity);
		return userDto;

	}

	@Override
	public void deleteUser(Long id) {
		this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found for this Id "));
		userRepository.deleteById(id);

	}

}
