package com.app.serviceInterface;

import java.util.List;

import com.app.dto.IListUserRole;
import com.app.dto.UserRoleDto;
import com.app.entities.UserRoleEntity;

import org.springframework.data.domain.Page;

public interface UserRoleInterface {

	public UserRoleDto addUserRole(UserRoleDto userRoleDto);

	public UserRoleDto updateRoleOrUser(Long id, UserRoleDto userRoleDto);

	public UserRoleEntity deleteUserRole(Long id);

	public List<IListUserRole> getUserRoleById(Long id);

	Page<IListUserRole> getAllUserRole(String userId, String roleId, String pageNumber, String pageSize);

	List<String> getRoleByUserId(Long roleId);

}
