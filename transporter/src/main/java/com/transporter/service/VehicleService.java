package com.transporter.service;

import com.transporter.vo.VehicleVO;

public interface VehicleService extends DefaultService {
	
	public Long registerVehicle(VehicleVO vehicleVO);
	
}
