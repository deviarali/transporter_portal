package com.transporter.vo;

import java.util.Calendar;
import java.util.Set;

import com.transporter.model.OwnerModel;

public class DriverVO {

	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String mobileNumber;
	private String email;
	private String aadharCard;
	private String drivingLicense;

	// Address
	private String city;
	private String state;
	private String street;
	private String zipCode;

	private Calendar createdDate;
	private Calendar dob;

	private UserVO user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAadharCard() {
		return aadharCard;
	}

	public void setAadharCard(String aadharCard) {
		this.aadharCard = aadharCard;
	}

	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getDob() {
		return dob;
	}

	public void setDob(Calendar dob) {
		this.dob = dob;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public static OwnerModel convertToModel(DriverVO ownerVO) {
		OwnerModel ownerModel = new OwnerModel();
		if (ownerVO == null)
			return null;
		ownerModel.setId(ownerVO.getId());
		ownerModel.setFirstName(ownerVO.getFirstName());
		ownerModel.setLastName(ownerVO.getLastName());
		ownerModel.setEmail(ownerVO.getEmail());
		ownerModel.setMobileNumber(ownerVO.getMobileNumber());
		ownerModel.setGender(ownerVO.getGender());
		ownerModel.setCity(ownerVO.getCity());
		ownerModel.setDob(ownerVO.getDob());
		ownerModel.setCreatedDate(ownerVO.getCreatedDate());
		ownerModel.setState(ownerVO.getState());
		ownerModel.setStreet(ownerVO.getStreet());
		// ownerModel.setUser(user);
		return ownerModel;
	}

}