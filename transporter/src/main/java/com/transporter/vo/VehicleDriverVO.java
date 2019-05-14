package com.transporter.vo;

import java.util.Calendar;

@SuppressWarnings("serial")
public class VehicleDriverVO {

	private Long id;
	private Calendar createdDate;
	private Boolean isActive;
	private VehicleVO vehicle;

	private DriverVO driver;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public VehicleVO getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleVO vehicle) {
		this.vehicle = vehicle;
	}

	public DriverVO getDriver() {
		return driver;
	}

	public void setDriver(DriverVO driver) {
		this.driver = driver;
	}

}