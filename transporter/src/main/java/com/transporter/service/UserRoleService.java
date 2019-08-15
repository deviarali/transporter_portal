package com.transporter.service;

import java.util.List;

import com.transporter.vo.UserRoleVO;

public interface UserRoleService extends DefaultService{

	List<UserRoleVO> getAllUserRoles();

}
