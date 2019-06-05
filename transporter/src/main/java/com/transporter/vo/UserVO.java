package com.transporter.vo;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.transporter.model.UserModel;
import com.transporter.model.UserRoleModel;
import com.transporter.model.UserTypeModel;

public class UserVO {

	private Long id;
	private String userName;
    private String password;
    private UserTypeModel userType;
    private String loginOtp;
	private Integer loginAttempts;
    private Boolean status;
	private Calendar createdDate;
    private Calendar loginDateTime;

	private Set<UserRoleVO> userRoles;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserTypeModel getUserType() {
		return userType;
	}

	public void setUserType(UserTypeModel userType) {
		this.userType = userType;
	}

	public String getLoginOtp() {
		return loginOtp;
	}

	public void setLoginOtp(String loginOtp) {
		this.loginOtp = loginOtp;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(Calendar loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public Integer getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public Set<UserRoleVO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoleVO> userRoles) {
		this.userRoles = userRoles;
	}
    
	public static UserModel convertVOToModel(UserVO userVO)
	{
		UserModel userModel = new UserModel();
		if(null == userVO)
			return null;
		userModel.setId(userVO.getId());
		userModel.setCreatedDate(userVO.getCreatedDate());
		userModel.setLoginAttempts(userVO.getLoginAttempts());
		userModel.setLoginDateTime(userVO.getLoginDateTime());
		userModel.setLoginOtp(userVO.getLoginOtp());
		userModel.setStatus(userVO.getStatus());
		userModel.setUserName(userVO.getUserName());
		userModel.setUserType(userVO.getUserType());
		if(null != userVO.getUserRoles() && userVO.getUserRoles().size() > 0)
		{
			Set<UserRoleModel> roleModels = new HashSet<>();
			for(UserRoleVO userRoleVO : userVO.getUserRoles())
			{
				roleModels.add(UserRoleVO.convertModelToVO(userRoleVO));
			}
			userModel.setUserRoles(roleModels);
		}
		return userModel;
	}
}
