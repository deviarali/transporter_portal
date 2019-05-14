package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.UserDao;
import com.transporter.model.UserModel;
import com.transporter.service.UserService;
import com.transporter.utils.ModelVoConvertUtils;
import com.transporter.vo.UserVO;

@Service
@Transactional
public class UserServiceImpl extends DefaultServiceImpl implements UserService {

	@Autowired
	public void setDefaultDao(UserDao defaultDao) {
		this.defaultDao = defaultDao;
	}

	private UserDao getUserDaoDao() {
		return (UserDao) getDefaultDao();
	}
	

	@Override
	public Long registerUser(UserVO userVO) {
		UserModel userModel = ModelVoConvertUtils.convertUserVOToUserModel(userVO);
		return (Long) this.saveDomain(userModel);
	}


}
