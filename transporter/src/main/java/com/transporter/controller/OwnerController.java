package com.transporter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.transporter.constants.WebConstants;
import com.transporter.response.CommonResponse;
import com.transporter.service.OwnerService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.OwnerVO;

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
}
