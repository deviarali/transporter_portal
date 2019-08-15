package com.transporter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.response.CommonResponse;
import com.transporter.service.UserRoleService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.UserRoleVO;

@RestController
public class UserRoleController {

	private UserRoleService userRoleService;
	@RequestMapping(value = "getAllUserRoles", method = RequestMethod.GET)
	public CommonResponse getAllUserRoles()
	{
		CommonResponse commonResponse = null;
		List<UserRoleVO> userRoleVOList = userRoleService.getAllUserRoles();
		commonResponse = RestUtils.wrapObjectForSuccess(userRoleVOList);
		return commonResponse;
	}
}
