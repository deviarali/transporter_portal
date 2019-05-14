package com.transporter.service;

import com.transporter.vo.UserVO;

public interface UserService extends DefaultService {
	
	public Long registerUser(UserVO userVO);
	
}
