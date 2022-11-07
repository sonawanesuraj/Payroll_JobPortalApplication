package com.app.serviceInterface;

import java.util.ArrayList;
import java.util.List;

import com.app.dto.IListRolePermission;
import com.app.dto.RolePermissionDto;

import org.springframework.data.domain.Page;

public interface RolePermissionInterface {

	public RolePermissionDto addRolePermission(RolePermissionDto rolePermissionDto);

	public RolePermissionDto updateRoleOrPermission(Long id, RolePermissionDto rolePermissionDto);

	public void deleteRolePermission(Long id);

	public List<IListRolePermission> getRolePermissionById(Long id);

	Page<IListRolePermission> getAllRolePermission(String search, String pageNumber, String pageSize);

	public ArrayList<String> getPermissionByUserId(Long id);

}
