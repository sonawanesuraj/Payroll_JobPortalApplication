package com.app.dto;

public class RolePermissionDto {
	private Long roleId;
	private Long permissionId;

	public RolePermissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RolePermissionDto(Long roleId, Long permissionId) {
		super();
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

}
