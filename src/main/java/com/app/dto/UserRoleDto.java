package com.app.dto;

public class UserRoleDto {

	private Long userId;
	private Long roleId;

	public UserRoleDto() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public UserRoleDto(Long userId, Long roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

}
