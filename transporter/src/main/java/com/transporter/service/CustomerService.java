package com.transporter.service;

import com.transporter.vo.CustomerVO;
import com.transporter.vo.UserVO;

public interface CustomerService extends DefaultService{

	CustomerVO registerCustomer(CustomerVO customerVO);

	UserVO isUserExists(CustomerVO customerVO);

	CustomerVO login(UserVO userVO);

	int updateCustomer(CustomerVO customerVO);

	int generateOtp(String mobile);

	CustomerVO validateOtp(String mobile, String otp);

}
