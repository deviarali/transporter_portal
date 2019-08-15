package com.transporter.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.transporter.dao.UserDao;
import com.transporter.model.UserModel;
import com.transporter.vo.UserVO;

@Repository
public class UserDaoImpl extends DefaultDaoImpl implements UserDao {

	@Override
	public UserModel isUserExists(String mobileNumber) {
		String sqlQuery = "FROM UserModel usr where usr.userName= :mobileNumber";
		Query query = getSession().createQuery(sqlQuery);
		query.setParameter("mobileNumber", mobileNumber);
		UserModel userModel = (UserModel ) query.uniqueResult();
		return userModel;
	}

	@Override
	public UserModel login(UserVO userVO) {
		Criteria criteria = getSession().createCriteria(UserModel.class);
		criteria.add(Restrictions.eq("userName", userVO.getUserName()));
		criteria.add(Restrictions.eq("password", userVO.getPassword()));
		UserModel userModel = (UserModel) criteria.uniqueResult();
		return userModel;
	}

	@Override
	public int generateOtp(String mobile, String otp) {
		int rows = 0;
		String sqlQuery = "Update UserModel usr set usr.loginOtp= :otp where usr.userName= :mobileNumber";
		Query query = getSession().createQuery(sqlQuery);
		query.setParameter("mobileNumber", mobile);
		query.setParameter("otp", otp);
		rows = query.executeUpdate();
		return rows;
	}

	@Override
	public UserModel validateOtp(String mobile, String otp) {
		String sqlQuery = "From UserModel usr where usr.userName= :mobileNumber and usr.loginOtp= :otp";
		Query query = getSession().createQuery(sqlQuery);
		query.setParameter("mobileNumber", mobile);
		query.setParameter("otp", otp);
		return (UserModel) query.uniqueResult();
		
	}

}
