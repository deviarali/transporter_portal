package com.transporter.dao;

import com.transporter.model.OwnerModel;

public interface OwnerDao extends DefaultDao {

	public OwnerModel getOwnerFromVehicleId(Long vehicleId);
	
}
