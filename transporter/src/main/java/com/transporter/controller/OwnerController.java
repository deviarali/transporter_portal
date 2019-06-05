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
import com.transporter.service.OwnerService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.OwnerVO;
import com.transporter.vo.UserVO;

@Controller
public class OwnerController {
	
	private static final Logger LOG = LoggerFactory
	        .getLogger(OwnerController.class);
	
	@Autowired
	private OwnerService ownerService;
	
	@RequestMapping(value = "owner/registerOwner", method = RequestMethod.POST)
	public @ResponseBody CommonResponse registerOwner(@RequestBody OwnerVO ownerVO)
	{
		CommonResponse response = null;
		Map<String, Object> map =  validateOwner(ownerVO);
		if(!map.isEmpty())
		{
			response = RestUtils.wrapObjectForFailure(map, "validation error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Validation missing");
			return response;
		}
		UserVO userVO = ownerService.isUserExists(ownerVO);
		if(null != userVO)
		{
			response = RestUtils.wrapObjectForFailure("user already exists with role : "+userVO.getUserType().getName(), "error", WebConstants.WEB_RESPONSE_ERORR);
			return response;
		}
		
		Long model = ownerService.registerOwner(ownerVO);
		if(model != null)
		{
			response = RestUtils.wrapObjectForSuccess("Registered Successfully");
			LOG.info("Owner registed successfully "+ownerVO.getFirstName());
		}
		else
		{
			response = RestUtils.wrapObjectForFailure("Not registered", "error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Owner not registed "+ownerVO.getFirstName());
		}
		
		return response;
	}

	private Map<String, Object> validateOwner(OwnerVO ownerVO) {
		Map<String, Object> map = new HashMap<>();
		if(ownerVO == null)
		{
			map.put("all", CommonConstants.ALL_FIELDS_REQUIRED);
		}
		else
		{
			if(StringUtils.isNullOrEmpty(ownerVO.getFirstName()) || StringUtils.isEmptyOrWhitespaceOnly(ownerVO.getFirstName()))
			{
				map.put("firstName", CommonConstants.FIRST_NAME_EMPTY);
			}
			if(StringUtils.isNullOrEmpty(ownerVO.getEmail()) || StringUtils.isEmptyOrWhitespaceOnly(ownerVO.getEmail()))
			{
				map.put("email", CommonConstants.EMAIL_EMPTY);
			}
			if(StringUtils.isNullOrEmpty(ownerVO.getMobileNumber()) || StringUtils.isEmptyOrWhitespaceOnly(ownerVO.getMobileNumber()))
			{
				map.put("mobileNumber", CommonConstants.MOBILE_NUMBER_EMPTY);
			}
			
		}
		return map;
	}
}
