package com.transporter.vo;

import com.transporter.model.UserRoleModel;

public class UserRoleVO {

	private Long id;
	private String roleName;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static UserRoleModel convertModelToVO(UserRoleVO userRoleVO)
	{
		UserRoleModel userRoleModel = new UserRoleModel();
		if(null == userRoleVO)
			return null;
		userRoleModel.setId(userRoleVO.getId());
		userRoleModel.setRoleName(userRoleVO.getRoleName());
		userRoleModel.setDescription(userRoleVO.getDescription());
		return userRoleModel;
	}
}