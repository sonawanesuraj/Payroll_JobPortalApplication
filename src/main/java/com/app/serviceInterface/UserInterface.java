package com.app.serviceInterface;

import java.util.List;

import com.app.dto.IUserListDto;
import com.app.dto.UserDto;

public interface UserInterface {

	public List<IUserListDto> getUserById(Long id);

	public UserDto updateUser(Long id, UserDto userDto);

	void deleteUser(Long id);

}
