package com.transporter.utils;

import com.transporter.response.CommonResponse;
import com.transporter.response.Error;

public class RestUtils {
	
	public static CommonResponse wrapObjectForSuccess(Object object)
	{
		CommonResponse response = new CommonResponse();
		response.setResultObject(object);
		return response;
	}
	
	public static CommonResponse wrapObjectForFailure(Object object, String errorCode, String errorMsg)
	{
		CommonResponse response = new CommonResponse();
		response.setResultObject(object);
		
		Error error = new Error();
		error.setCode(errorCode);
		error.setMessage(errorMsg);
		
		response.setError(error);
		
		return response;
	}
}
