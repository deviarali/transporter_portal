package com.transporter.model;


import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity(name = "OwnerModel")
@Table(name = "owner")
public class OwnerModel extends AbstractIdDomain {

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

	private UserModel user;

	private Set<VehicleModel> vehicles;

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = true)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "gender", nullable = false)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "mobile_number", nullable = false)
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "email", nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "city", nullable = true)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", nullable = true)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "street", nullable = true)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "zipcode", nullable = true)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "created_date")
	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "dob")
	public Calendar getDob() {
		return dob;
	}

	public void setDob(Calendar dob) {
		this.dob = dob;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id", nullable = true)
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@ManyToMany(cascade=CascadeType.DETACH, fetch=FetchType.LAZY)
    @JoinTable(name = "ownervehicles", joinColumns = @JoinColumn(name = "owner_id"), inverseJoinColumns = @JoinColumn(name = "vehicle_id"))
	public Set<VehicleModel> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<VehicleModel> vehicles) {
		this.vehicles = vehicles;
	}

	public void addVehicle(VehicleModel vehicle) {
		if (getVehicles() == null) {
			setVehicles(new HashSet<VehicleModel>());
		}
		getVehicles().add(vehicle);
	}

	public void removeVehicle(VehicleModel vehicle) {
		getVehicles().remove(vehicle);
	}
}