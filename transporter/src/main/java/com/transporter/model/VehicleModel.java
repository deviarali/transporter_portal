package com.transporter.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity(name = "VehicleModel")
@Table(name = "vehicle")
public class VehicleModel extends AbstractIdDomain {

	private String vehicleNumber;
//	private String vehicleDocument;
	private String vehicleType;
	private String vehicleColor;
	private String vehicleModel;
	private Integer numberOfSeat;
	private String rcBook;
	
	private DriverModel driver;

	@Column(name = "vehiclenumber", nullable = false)
	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	@Column(name = "vehicletype", nullable = false)
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Column(name = "vehiclecolor", nullable = false)
	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	@Column(name = "vehiclemodel", nullable = false)
	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	@Column(name = "numberofseat", nullable = false)
	public Integer getNumberOfSeat() {
		return numberOfSeat;
	}

	public void setNumberOfSeat(Integer numberOfSeat) {
		this.numberOfSeat = numberOfSeat;
	}

	@Column(name = "rc_book")
	public String getRcBook() {
		return rcBook;
	}

	public void setRcBook(String rcBook) {
		this.rcBook = rcBook;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "driverr_id", nullable = true)
	public DriverModel getDriver() {
		return driver;
	}

	public void setDriver(DriverModel driver) {
		this.driver = driver;
	}

}