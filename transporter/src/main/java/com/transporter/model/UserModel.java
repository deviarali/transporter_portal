package com.transporter.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.transporter.vo.UserRoleVO;
import com.transporter.vo.UserVO;

/**
 * @author SHARAN A
 */

@Entity(name = "UserModel")
@Table(name="user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserModel extends AbstractIdDomain {

	private String userName;
    private String password;
    private UserTypeModel userType;
    private String loginOtp;
	private Integer loginAttempts;
    private Boolean status;
	private Calendar createdDate;
    private Calendar loginDateTime;

	private Set<UserRoleModel> userRoles;
	
	private List<CustomerModel> customers;
	
	@Column(name="name", nullable=false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="password", nullable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type", nullable=false)
	public UserTypeModel getUserType() {
		return userType;
	}

	public void setUserType(UserTypeModel userType) {
		this.userType = userType;
	}

	@Column(name="login_otp", nullable=true)
	public String getLoginOtp() {
		return loginOtp;
	}

	public void setLoginOtp(String loginOtp) {
		this.loginOtp = loginOtp;
	}

	@Column(name="status", nullable=false)
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name="created_date", nullable=false)
	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="login_date_time", nullable=true)
	public Calendar getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(Calendar loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	@Column(name="login_attempts", nullable=true)
	public Integer getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	@ManyToMany(cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
    @JoinTable(name = "userroles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "userrole_id"))
	public Set<UserRoleModel> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoleModel> userRoles) {
		this.userRoles = userRoles;
	}
    
	public void addUserRole(UserRoleModel userRole) {
		if (getUserRoles() == null) {
			setUserRoles(new HashSet<UserRoleModel>());
		}
		getUserRoles().add(userRole);
	}

	public void removeUserRole(UserRoleModel userRole) {
		getUserRoles().remove(userRole);
	}

	@OneToMany(mappedBy = "user")
	public List<CustomerModel> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerModel> customers) {
		this.customers = customers;
	}
	
	public static UserVO convertModelToVO(UserModel userModel)
	{
		UserVO userVO = new UserVO();
		if(null == userModel)
			return null;
		userVO.setId(userModel.getId());
		userVO.setCreatedDate(userModel.getCreatedDate());
		userVO.setLoginAttempts(userModel.getLoginAttempts());
		userVO.setLoginDateTime(userModel.getLoginDateTime());
		userVO.setLoginOtp(userModel.getLoginOtp());
		userVO.setStatus(userModel.getStatus());
		userVO.setUserName(userModel.getUserName());
		userVO.setUserType(userModel.getUserType());
		if(null != userModel.getUserRoles() && userModel.getUserRoles().size() > 0)
		{
			Set<UserRoleVO> roleVOs = new HashSet<>();
			for(UserRoleModel userRoleModel : userModel.getUserRoles())
			{
				roleVOs.add(UserRoleModel.convertModelToVO(userRoleModel));
			}
			userVO.setUserRoles(roleVOs);
		}
		return userVO;
	}

}
