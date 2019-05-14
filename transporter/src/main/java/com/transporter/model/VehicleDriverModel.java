package com.transporter.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity(name = "VehicleDriverModel")
@Table(name = "vehicledriver")
public class VehicleDriverModel extends AbstractIdDomain {

	private Calendar createdDate;
	private Boolean isActive;

	private VehicleModel vehicle;
	private DriverModel driver;

	@Column(name = "createddate", nullable = false)
	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "isactive", nullable = false)
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "vehicle_id", nullable = false)
	public VehicleModel getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleModel vehicle) {
		this.vehicle = vehicle;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "driver_id", nullable = false)
	public DriverModel getDriver() {
		return driver;
	}

	public void setDriver(DriverModel driver) {
		this.driver = driver;
	}

}