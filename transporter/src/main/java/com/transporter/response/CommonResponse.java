package com.transporter.response;

import java.util.Date;

public class CommonResponse {
	private Error error;
	private Object resultObject;
	private long currentServerTime;
	
	public CommonResponse() {
		currentServerTime = new Date().getTime();
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public Object getResultObject() {
		return resultObject;
	}
	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}
	public long getCurrentServerTime() {
		return currentServerTime;
	}
}
