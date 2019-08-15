package com.transporter.dao;

import java.util.List;

import com.transporter.model.UserRoleModel;

public interface UserRoleDao extends DefaultDao {

	List<UserRoleModel> getAllUserRoles();

}
