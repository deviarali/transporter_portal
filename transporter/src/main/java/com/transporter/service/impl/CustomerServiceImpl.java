package com.transporter.service.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.transporter.dao.CustomerDao;
import com.transporter.dao.UserDao;
import com.transporter.model.CustomerModel;
import com.transporter.model.OwnerModel;
import com.transporter.model.UserModel;
import com.transporter.model.UserRoleModel;
import com.transporter.model.UserTypeModel;
import com.transporter.service.CustomerService;
import com.transporter.service.UserService;
import com.transporter.utils.DateTimeUtils;
import com.transporter.utils.ModelVoConvertUtils;
import com.transporter.utils.PasswordUtils;
import com.transporter.vo.CustomerVO;
import com.transporter.vo.UserRoleVO;
import com.transporter.vo.UserVO;

@Service
public class CustomerServiceImpl extends DefaultServiceImpl implements CustomerService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	public void setDefaultDao(UserDao defaultDao) {
		this.defaultDao = defaultDao;
	}

	private CustomerDao getCustomerDao() {
		return (CustomerDao) getDefaultDao();
	}
	
	@Override
	@Transactional
	public CustomerVO registerCustomer(CustomerVO customerVO) {
		UserModel userModel = new UserModel();
		userModel.setRowVersion(1);
		userModel.setCreatedDate(DateTimeUtils.getCurrentCalendar());
		userModel.setUserName(customerVO.getMobileNumber());
		userModel.setLoginAttempts(0);
		//userModel.setPassword(PasswordUtils.generateSecurePassword(customerVO.getUser().getPassword()));
		userModel.setStatus(true);
		userModel.setUserType(UserTypeModel.CUSTOMER);
		Set<UserRoleVO> userRoles = new HashSet<>();
		UserRoleVO userRole = new UserRoleVO();
		userRole.setId(2L);
		userRoles.add(userRole);
		/*customerVO.getUser().setUserRoles(userRoles);
		
		if(customerVO.getUser() != null && !CollectionUtils.isEmpty(customerVO.getUser().getUserRoles())) {
			for (UserRoleVO userRoleVO : customerVO.getUser().getUserRoles()) {
				UserRoleModel userRoleModel = new UserRoleModel();
				userRoleModel.setId(userRoleVO.getId());;
				
				userModel.addUserRole(userRoleModel);
			}
		}*/
		UserRoleModel userRoleModel = new UserRoleModel();
		userRoleModel.setId(2L);;
		
		userModel.addUserRole(userRoleModel);
		userService.saveDomain(userModel);
		
		CustomerModel customerModel = ModelVoConvertUtils.convertCustomerVOToCustomerModel(customerVO);
		customerModel.setCreatedDate(DateTimeUtils.getCurrentCalendar());
		customerModel.setUser(userModel);
		
		this.saveDomain(customerModel);
		
		return CustomerModel.convertModelToVO(customerModel);
	}

	@Override
	public UserVO isUserExists(CustomerVO customerVO) {
		return userService.isUserExists(customerVO.getMobileNumber());
	}

	@Override
	public CustomerVO login(UserVO userVO) {
		userVO.setPassword(PasswordUtils.generateSecurePassword(userVO.getPassword()));
		CustomerModel customerModel = userService.customerLogin(userVO);
		CustomerVO customerVO = null;
		if(null != customerModel)
		{
			customerVO = CustomerModel.convertModelToVO(customerModel);
		}
		return customerVO;
	}

	@Override
	public int updateCustomer(CustomerVO customerVO) {
		int result = customerDao.updateCustomer(CustomerVO.convertVOToModel(customerVO));
		return result;
	}

	@Override
	public int generateOtp(String mobile) {
		return userService.generateOtp(mobile);
	}

	@Override
	public CustomerVO validateOtp(String mobile, String otp) {
		UserVO userVO = userService.validateOtp(mobile, otp); 
		CustomerVO customerVO = null;
		if(userVO != null)
		{
			customerVO = findCustomerByUserId(userVO.getId());
		}
		return customerVO;
	}

	private CustomerVO findCustomerByUserId(Long id) {
		return CustomerModel.convertModelToVO(customerDao.findCustomerByUserId(id));
	}

}
