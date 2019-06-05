package com.transporter.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.CommonConstants;
import com.transporter.constants.WebConstants;
import com.transporter.response.CommonResponse;
import com.transporter.service.CustomerService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.CustomerVO;
import com.transporter.vo.UserVO;

/**
 * @author Devappa.Arali
 *
 */

@Controller
public class CustomerController {
	
	private static final Logger LOG = LoggerFactory
	        .getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "customer/registerCustomer", method = RequestMethod.POST)
	public @ResponseBody CommonResponse registerCustomer(@RequestBody CustomerVO customerVO)
	{
		CommonResponse response = null;
		
		Map<String, Object> map = validateCustomer(customerVO);
		
		if(!map.isEmpty())
		{
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Validation missing");
			return response;
		}
		
		UserVO userVO = customerService.isUserExists(customerVO);
		if(null != userVO)
		{
			response = RestUtils.wrapObjectForFailure("user already exists with role : "+userVO.getUserType().getName(), "error", WebConstants.WEB_RESPONSE_ERORR);
			return response;
		}
		
		Integer id = customerService.registerCustomer(customerVO);
		if(id != null)
		{
			response = RestUtils.wrapObjectForSuccess("Registered Successfully");
			LOG.info("Owner registed successfully "+customerVO.getFirstName());
		}
		else
		{
			response = RestUtils.wrapObjectForFailure("Not registered", "error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Owner not registed "+customerVO
					.getFirstName());
		}
		
		return response;
	}
	
	@RequestMapping(value = "customer/login", method = RequestMethod.POST)
	public @ResponseBody CommonResponse login(@RequestBody UserVO userVO)
	{
		CommonResponse response = null;
		
		Map<String, Object> map = validateLogin(userVO);
		if(!map.isEmpty())
		{
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Login Validation missing");
		}
		else
		{
			CustomerVO customerVO = customerService.login(userVO);
			if(null != customerVO)
			{
				response = RestUtils.wrapObjectForSuccess(customerVO);
				LOG.info("Logged in successfully "+customerVO.getFirstName());
			}
			else
			{
				response = RestUtils.wrapObjectForFailure("invalid credentials", "error", WebConstants.WEB_RESPONSE_ERORR);
				LOG.error("Invalid credentials "+userVO.getUserName());
			}
		}
		return response;
	}

	private Map<String, Object> validateLogin(UserVO userVO) {
		Map<String, Object> map = new HashMap<>();
		if(userVO == null)
		{
			map.put("All", CommonConstants.ALL_FIELDS_REQUIRED);
		}
		else
		{
			if(StringUtils.isNullOrEmpty(userVO.getUserName()) || StringUtils.isEmptyOrWhitespaceOnly(userVO.getUserName()))
			{
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
			if(StringUtils.isNullOrEmpty(userVO.getPassword()) || StringUtils.isEmptyOrWhitespaceOnly(userVO.getPassword()))
			{
				map.put("password", CommonConstants.PASSWORD_EMPTY);
			}
		}
		return map;
	}

	private Map<String, Object> validateCustomer(CustomerVO customerVO) {
		Map<String, Object> map = new HashMap<>();
		if(customerVO == null)
		{
			map.put("All", CommonConstants.ALL_FIELDS_REQUIRED);
		}
		else
		{
			if(StringUtils.isNullOrEmpty(customerVO.getFirstName()))
			{
				map.put("firstName", CommonConstants.FIRST_NAME_EMPTY);
			}
			if(StringUtils.isNullOrEmpty(customerVO.getEmail()) || StringUtils.isEmptyOrWhitespaceOnly(customerVO.getEmail()))
			{
				map.put("email", CommonConstants.EMAIL_EMPTY);
			}
			if(StringUtils.isNullOrEmpty(customerVO.getMobileNumber()) || StringUtils.isEmptyOrWhitespaceOnly(customerVO.getMobileNumber()))
			{
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
			if(StringUtils.isNullOrEmpty(customerVO.getUser().getPassword()) || StringUtils.isEmptyOrWhitespaceOnly(customerVO.getUser().getPassword()))
			{
				map.put("user.password", CommonConstants.PASSWORD_EMPTY);
			}
		}
		return map;
	}
}
