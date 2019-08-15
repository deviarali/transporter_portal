package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.UserDao;
import com.transporter.model.CustomerModel;
import com.transporter.model.UserModel;
import com.transporter.service.UserService;
import com.transporter.utils.ModelVoConvertUtils;
import com.transporter.vo.UserVO;

@Service
@Transactional
public class UserServiceImpl extends DefaultServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
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

	@Override
	public UserVO isUserExists(String mobileNumber) {
		UserModel userModel = userDao.isUserExists(mobileNumber);
		return UserModel.convertModelToVO(userModel);
	}

	@Override
	public CustomerModel customerLogin(UserVO userVO) {
		UserModel userModel = userDao.login(userVO);
		if(null != userModel)
		{
			return userModel.getCustomers().get(0);
		}
		return null;
	}

	@Override
	public int generateOtp(String mobile) {
		return userDao.generateOtp(mobile, generateOtp());
	}

	public String generateOtp()
	{
		return "55555";
	}

	@Override
	public UserVO validateOtp(String mobile, String otp) {
		
		UserModel userModel = userDao.validateOtp(mobile, otp);
		return UserModel.convertModelToVO(userModel);
	}

}
