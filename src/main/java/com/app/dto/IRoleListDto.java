package com.app.dto;

import java.io.Serializable;

public interface IRoleListDto extends Serializable {

	public Long getId();

	public String getRoleName();

	public String getDescription();

}
