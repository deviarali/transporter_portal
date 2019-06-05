package com.transporter.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.transporter.vo.UserRoleVO;

@SuppressWarnings("serial")
@Entity(name = "UserRoleModel")
@Table(name = "userrole")
public class UserRoleModel extends AbstractIdDomain {

	private String roleName;
	private String description;

	@Column(name = "rolename", nullable = false, length = 45)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static UserRoleVO convertModelToVO(UserRoleModel userRoleModel)
	{
		UserRoleVO userRoleVO = new UserRoleVO();
		if(null == userRoleModel)
			return null;
		userRoleVO.setId(userRoleModel.getId());
		userRoleVO.setRoleName(userRoleModel.getRoleName());
		userRoleVO.setDescription(userRoleModel.getDescription());
		return userRoleVO;
	}
	

}