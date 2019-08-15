package com.transporter.service;

import com.transporter.model.CustomerModel;
import com.transporter.vo.UserVO;

public interface UserService extends DefaultService {
	
	public Long registerUser(UserVO userVO);

	public UserVO isUserExists(String mobileNumber);

	public CustomerModel customerLogin(UserVO userVO);

	public int generateOtp(String mobile);

	public UserVO validateOtp(String mobile, String otp);
	
}
