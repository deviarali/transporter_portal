package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Criteria;

import com.transporter.dao.UserRoleDao;
import com.transporter.model.UserRoleModel;

public class UserRoleDaoImpl extends DefaultDaoImpl implements UserRoleDao {

	@Override
	public List<UserRoleModel> getAllUserRoles() {
		Criteria criteria = getSession().createCriteria(UserRoleModel.class);
		List<UserRoleModel> list = criteria.list();
		return list;
	}

}
