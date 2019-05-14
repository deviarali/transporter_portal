package com.transporter.vo;


import java.util.Calendar;
import java.util.Set;

public class OwnerVO {

	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String mobileNumber;
	private String email;

	//Address
	private String city;
	private String state;
	private String street;
	private String zipCode;

	private Calendar createdDate;
	private Calendar dob;

	private UserVO user;

	private Set<VehicleVO> vehicles;

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

	public Set<VehicleVO> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<VehicleVO> vehicles) {
		this.vehicles = vehicles;
	}
	
}