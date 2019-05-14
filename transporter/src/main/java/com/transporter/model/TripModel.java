package com.transporter.model;


import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity(name = "TripModel")
@Table(name = "trip")
public class TripModel extends AbstractIdDomain {

	private String pickupLocation;
	private String destinationLocation;
	private String status;
	private String cancelReason;

	private Calendar startTime;
	private Calendar endTime;
	private Calendar tripTime;
	
	private BigDecimal amount;

	private Integer rating;

	private VehicleModel vehicle;
	private CustomerModel customer;
	private DriverModel driver;
	
	private String cashMode;
	private String goodsType;
	private String goodsSize;
	private String tripStartOtp;
	private String tripEndOtp;
	private String amountToDriver;
	private String amountToApp;
	private String cancelledAmountFromCustomer;
	private String cancelledAmountFromDriver;
	private String cancelledAmountStatus;
	private String pickupPersonName;
	private String pickupPersonMobile;
	private String deliveryPersonName;
	private String deliveryPersonMobile;

	@Column(name = "pickuplocation", nullable = false)
	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	@Column(name = "destlocation", nullable = false)
	public String getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "cancelreason", nullable = true)
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name = "starttime", nullable = false)
	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endtime", nullable = false)
	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	@Column(name = "triptime", nullable = false)
	public Calendar getTripTime() {
		return tripTime;
	}

	public void setTripTime(Calendar tripTime) {
		this.tripTime = tripTime;
	}

	@Column(name = "amount", nullable = false)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "rating", nullable = true)
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
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
	@JoinColumn(name = "customer_id", nullable = false)
	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "driver_id", nullable = false)
	public DriverModel getDriver() {
		return driver;
	}

	public void setDriver(DriverModel driver) {
		this.driver = driver;
	}

	@Column(name = "cash_mode")
	public String getCashMode() {
		return cashMode;
	}

	public void setCashMode(String cashMode) {
		this.cashMode = cashMode;
	}

	@Column(name = "goods_type")
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	@Column(name = "goods_size")
	public String getGoodsSize() {
		return goodsSize;
	}

	public void setGoodsSize(String goodsSize) {
		this.goodsSize = goodsSize;
	}

	@Column(name = "trip_start_otp")
	public String getTripStartOtp() {
		return tripStartOtp;
	}

	public void setTripStartOtp(String tripStartOtp) {
		this.tripStartOtp = tripStartOtp;
	}

	@Column(name = "trip_end_otp")
	public String getTripEndOtp() {
		return tripEndOtp;
	}

	public void setTripEndOtp(String tripEndOtp) {
		this.tripEndOtp = tripEndOtp;
	}

	@Column(name = "amount_to_driver")
	public String getAmountToDriver() {
		return amountToDriver;
	}

	public void setAmountToDriver(String amountToDriver) {
		this.amountToDriver = amountToDriver;
	}

	@Column(name = "amount_to_app")
	public String getAmountToApp() {
		return amountToApp;
	}

	public void setAmountToApp(String amountToApp) {
		this.amountToApp = amountToApp;
	}

	@Column(name = "cancelled_amount_fromcustomer")
	public String getCancelledAmountFromCustomer() {
		return cancelledAmountFromCustomer;
	}

	public void setCancelledAmountFromCustomer(String cancelledAmountFromCustomer) {
		this.cancelledAmountFromCustomer = cancelledAmountFromCustomer;
	}

	@Column(name = "cancelled_amount_fromdriver")
	public String getCancelledAmountFromDriver() {
		return cancelledAmountFromDriver;
	}

	public void setCancelledAmountFromDriver(String cancelledAmountFromDriver) {
		this.cancelledAmountFromDriver = cancelledAmountFromDriver;
	}

	@Column(name = "cancelled_amount_status")
	public String getCancelledAmountStatus() {
		return cancelledAmountStatus;
	}

	public void setCancelledAmountStatus(String cancelledAmountStatus) {
		this.cancelledAmountStatus = cancelledAmountStatus;
	}

	@Column(name = "pickup_person_name")
	public String getPickupPersonName() {
		return pickupPersonName;
	}

	public void setPickupPersonName(String pickupPersonName) {
		this.pickupPersonName = pickupPersonName;
	}

	@Column(name = "pickup_person_mobile")
	public String getPickupPersonMobile() {
		return pickupPersonMobile;
	}

	public void setPickupPersonMobile(String pickupPersonMobile) {
		this.pickupPersonMobile = pickupPersonMobile;
	}

	@Column(name = "delivery_person_name")
	public String getDeliveryPersonName() {
		return deliveryPersonName;
	}

	public void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
	}

	@Column(name = "delivery_person_mobile")
	public String getDeliveryPersonMobile() {
		return deliveryPersonMobile;
	}

	public void setDeliveryPersonMobile(String deliveryPersonMobile) {
		this.deliveryPersonMobile = deliveryPersonMobile;
	}

}