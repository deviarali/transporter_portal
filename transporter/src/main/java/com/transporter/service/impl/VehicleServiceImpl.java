package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.OwnerDao;
import com.transporter.dao.VehicleDao;
import com.transporter.model.OwnerModel;
import com.transporter.model.VehicleModel;
import com.transporter.service.OwnerService;
import com.transporter.service.VehicleService;
import com.transporter.utils.ModelVoConvertUtils;
import com.transporter.vo.VehicleVO;

@Service
@Transactional
public class VehicleServiceImpl extends DefaultServiceImpl implements VehicleService {

	@Autowired
	private OwnerService ownerService;

	@Autowired
	public void setDefaultDao(OwnerDao defaultDao) {
		this.defaultDao = defaultDao;
	}

	private VehicleDao getVehicleDao() {
		return (VehicleDao) getDefaultDao();
	}

	@Override
	public Long registerVehicle(VehicleVO vehicleVO) {
		
		VehicleModel vehicleModel = ModelVoConvertUtils.convertVehicleVoToVehicleModel(vehicleVO);

		Long vehicleId = (Long) this.saveDomain(vehicleModel);

		OwnerModel ownerModel = (OwnerModel) ownerService.loadDomain(OwnerModel.class, vehicleVO.getOwnerId());
		
		ownerModel.addVehicle(vehicleModel);
		
		ownerService.updateDomain(ownerModel);
		
		return vehicleVO.getId();
	}

}
