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
import com.transporter.service.VehicleService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.VehicleVO;

@Controller
public class VehicleController {

	private static final Logger LOG = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	private VehicleService vehicleService;

	@RequestMapping(value = "owner/vehicle", method = RequestMethod.GET)
	public @ResponseBody VehicleVO vehicle() {
		
		VehicleVO vehicleVo = new VehicleVO();
		vehicleVo.setOwnerId(1L);
		vehicleVo.setVehicleNumber("KA01AA0001");
		vehicleVo.setVehicleType("Type");
		vehicleVo.setVehicleColor("WHITE");
		vehicleVo.setVehicleModel("2019");
		vehicleVo.setNumberOfSeat(5);
		vehicleVo.setRcBook("D");
		
		return vehicleVo;
		
	}
	
	@RequestMapping(value = "owner/registerVehicle", method = RequestMethod.POST)
	public @ResponseBody CommonResponse registerVehicle(@RequestBody VehicleVO vehicleVO) {
		CommonResponse response = null;
		Long model = vehicleService.registerVehicle(vehicleVO);
		if (model != null) {
			response = RestUtils.wrapObjectForSuccess("Registered Successfully");
			LOG.info("Vehicle registed successfully " + vehicleVO.getVehicleNumber());
		} else {
			response = RestUtils.wrapObjectForFailure("Not registered", "error", WebConstants.WEB_RESPONSE_ERORR);
			LOG.error("Vehicle not registed " + vehicleVO.getVehicleNumber());
		}
		return response;
	}
}
