package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.transporter.dao.OwnerDao;
import com.transporter.model.OwnerModel;
import com.transporter.model.UserModel;
import com.transporter.model.UserRoleModel;
import com.transporter.model.UserTypeModel;
import com.transporter.service.OwnerService;
import com.transporter.service.UserService;
import com.transporter.utils.DateTimeUtils;
import com.transporter.utils.ModelVoConvertUtils;
import com.transporter.utils.PasswordUtils;
import com.transporter.vo.OwnerVO;
import com.transporter.vo.UserRoleVO;

@Service
@Transactional
public class OwnerServiceImpl extends DefaultServiceImpl implements OwnerService {

	@Autowired
	private UserService userService;
	
	@Autowired
	public void setDefaultDao(OwnerDao defaultDao) {
		this.defaultDao = defaultDao;
	}

	private OwnerDao getOwnerDao() {
		return (OwnerDao) getDefaultDao();
	}
	
	public OwnerVO getOwnerFromVehicleId(Long vehicleId) {
		OwnerModel ownerModel = getOwnerDao().getOwnerFromVehicleId(vehicleId);
		return ModelVoConvertUtils.convertOwnerModelToOwnerVO(ownerModel);
	}

	@Override
	public Long registerOwner(OwnerVO ownerVO) {
		UserModel userModel = new UserModel();
		userModel.setRowVersion(1);
		userModel.setCreatedDate(DateTimeUtils.getCurrentCalendar());
		userModel.setUserName(ownerVO.getMobileNumber());
		userModel.setLoginAttempts(0);
		userModel.setPassword(PasswordUtils.generateSecurePassword("devaraj"));
		userModel.setStatus(true);
		userModel.setUserType(UserTypeModel.OWNER);
		
		if(ownerVO.getUser() != null && !CollectionUtils.isEmpty(ownerVO.getUser().getUserRoles())) {
			for (UserRoleVO userRoleVO : ownerVO.getUser().getUserRoles()) {
				UserRoleModel userRoleModel = new UserRoleModel();
				userRoleModel.setId(userRoleVO.getId());;
				
				userModel.addUserRole(userRoleModel);
			}
		}
		
		Long l = (Long) userService.saveDomain(userModel);
		
		OwnerModel convertToModel = ModelVoConvertUtils.convertOwnerVOToOwnerModel(ownerVO);;
		convertToModel.setUser(userModel);
		Long ownerModel2 = (Long) saveDomain(convertToModel);
		return ownerModel2;
	}

}
