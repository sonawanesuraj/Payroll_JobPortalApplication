package com.app.serviceInterface;

import java.util.List;

import com.app.dto.IListPermissionDto;
import com.app.dto.PermissionDto;

import org.springframework.data.domain.Page;

public interface PermissionInterface {

	PermissionDto addPermission(PermissionDto permissionDto);

	public PermissionDto updatePermission(Long id, PermissionDto permissionDto);

	public void deletePermission(Long id);

	public List<IListPermissionDto> getPermissionById(Long id);

	Page<IListPermissionDto> getAllPermission(String search, String pageNumber, String pageSize);

}
