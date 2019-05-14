package com.transporter.service;

import com.transporter.vo.UserVO;
import com.transporter.vo.VehicleDriverVO;

public interface VehicleDriverService extends DefaultService {
	
	public Long registerVehicleDriver(VehicleDriverVO vehicleDriverVO);
	
}
