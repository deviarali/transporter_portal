package com.transporter.dao;

import com.transporter.model.UserModel;
import com.transporter.vo.UserVO;

public interface UserDao extends DefaultDao {

	UserModel isUserExists(String mobileNumber);

	UserModel login(UserVO userVO);

}
