package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.VehicleDriverDao;
import com.transporter.model.Domain;
import com.transporter.model.VehicleDriverModel;
import com.transporter.service.VehicleDriverService;
import com.transporter.utils.ModelVoConvertUtils;
import com.transporter.vo.VehicleDriverVO;

@Service
@Transactional
public class VehicleDriverServiceImpl extends DefaultServiceImpl implements VehicleDriverService {

	@Autowired
	public void setDefaultDao(VehicleDriverDao defaultDao) {
		this.defaultDao = defaultDao;
	}

	private VehicleDriverDao getVehicleDriverDaoDao() {
		return (VehicleDriverDao) getDefaultDao();
	}
	
	@Override
	protected void initSave(Domain domain) {
		
	}
	
	@Override
	protected void initUpdate(Domain domain2) {
		
	}

	@Override
	public Long registerVehicleDriver(VehicleDriverVO vehicleDriverVO) {
		VehicleDriverModel vehicleDriverModel = ModelVoConvertUtils.convertVehicleDriverVOToVehicleDriverModel(vehicleDriverVO);
		return (Long) this.saveDomain(vehicleDriverModel);
	}


}
