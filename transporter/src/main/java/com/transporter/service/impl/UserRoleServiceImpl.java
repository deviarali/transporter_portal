package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.transporter.dao.UserRoleDao;
import com.transporter.model.UserRoleModel;
import com.transporter.service.UserRoleService;
import com.transporter.vo.UserRoleVO;

public class UserRoleServiceImpl extends DefaultServiceImpl implements UserRoleService{

	private UserRoleDao userRoleDao;
	
	@Override
	public List<UserRoleVO> getAllUserRoles() {
		List<UserRoleModel> userRoleModelList = userRoleDao.getAllUserRoles();
		List<UserRoleVO> userRoleVOList = new ArrayList<>();
		for(UserRoleModel userRoleModel : userRoleModelList)
		{
			userRoleVOList.add(UserRoleModel.convertModelToVO(userRoleModel));
		}
		return userRoleVOList;
	}

}
