package com.app.serviceInterface;

import com.app.dto.IRoleListDto;
import com.app.dto.RoleDto;

import org.springframework.data.domain.Page;

public interface RoleInterface {

	RoleDto addrole(RoleDto roleDto);

	RoleDto getRoleById(Long id);

	Page<IRoleListDto> getAllRoles(String roleName, String from, String to);

	RoleDto updateRoles(RoleDto roleDto, Long id);

	void deleteRole(Long id);

}
