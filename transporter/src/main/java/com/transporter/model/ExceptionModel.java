package com.transporter.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity(name = "ExceptionModel")
@Table(name = "exception")
public class ExceptionModel extends AbstractIdDomain {

	private String code;
	private String message;
	private String detail;

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "message", nullable = false, length = 255)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "detail", nullable = true, length = 1000)
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}