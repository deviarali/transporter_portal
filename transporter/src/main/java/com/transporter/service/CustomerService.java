package com.transporter.service;

import com.transporter.vo.CustomerVO;
import com.transporter.vo.UserVO;

public interface CustomerService extends DefaultService{

	Integer registerCustomer(CustomerVO customerVO);

	UserVO isUserExists(CustomerVO customerVO);

	CustomerVO login(UserVO userVO);

}
