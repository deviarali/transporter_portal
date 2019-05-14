package com.transporter.model;

public enum UserTypeModel {

	OWNER("Owner"), DRIVER("Driver"), USER("User");

	private String name;

	private UserTypeModel(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}