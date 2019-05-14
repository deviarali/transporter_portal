package com.transporter.vo;

public class VehicleVO {

	private Long id;
	private Long ownerId;
	private String vehicleNumber;
//	private String vehicleDocument;
	private String vehicleType;
	private String vehicleColor;
	private String vehicleModel;
	private Integer numberOfSeat;
	private String rcBook;
	
	private DriverVO driver;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public Integer getNumberOfSeat() {
		return numberOfSeat;
	}

	public void setNumberOfSeat(Integer numberOfSeat) {
		this.numberOfSeat = numberOfSeat;
	}

	public String getRcBook() {
		return rcBook;
	}

	public void setRcBook(String rcBook) {
		this.rcBook = rcBook;
	}

	public DriverVO getDriver() {
		return driver;
	}

	public void setDriver(DriverVO driver) {
		this.driver = driver;
	}

}