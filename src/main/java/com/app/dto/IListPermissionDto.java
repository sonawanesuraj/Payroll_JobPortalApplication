package com.app.dto;

public interface IListPermissionDto {
	public Long getId();

	public String getActionName();

	public String getDescription();

	public String getMethod();

	public String getPath();

	public String getBaseUrl();

}
