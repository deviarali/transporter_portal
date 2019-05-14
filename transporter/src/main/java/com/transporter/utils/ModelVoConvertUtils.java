package com.transporter.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transporter.exception.JsonConvertException;
import com.transporter.model.DriverModel;
import com.transporter.model.OwnerModel;
import com.transporter.model.UserModel;
import com.transporter.model.VehicleDriverModel;
import com.transporter.model.VehicleModel;
import com.transporter.vo.DriverVO;
import com.transporter.vo.OwnerVO;
import com.transporter.vo.UserVO;
import com.transporter.vo.VehicleDriverVO;
import com.transporter.vo.VehicleVO;

public class ModelVoConvertUtils {

	public static OwnerModel convertOwnerVOToOwnerModel(OwnerVO ownerVO) {
		OwnerModel ownerModel = new OwnerModel();
		if (ownerVO == null)
			return null;
		ownerModel.setId(ownerVO.getId());
		ownerModel.setFirstName(ownerVO.getFirstName());
		ownerModel.setLastName(ownerVO.getLastName());
		ownerModel.setEmail(ownerVO.getEmail());
		ownerModel.setMobileNumber(ownerVO.getMobileNumber());
		ownerModel.setGender(ownerVO.getGender());
		ownerModel.setCity(ownerVO.getCity());
		ownerModel.setDob(ownerVO.getDob());
		ownerModel.setCreatedDate(ownerVO.getCreatedDate());
		ownerModel.setState(ownerVO.getState());
		ownerModel.setStreet(ownerVO.getStreet());
		// ownerModel.setUser(user);
		return ownerModel;
	}

	public static OwnerVO convertOwnerModelToOwnerVO(OwnerModel ownerModel) {
		OwnerVO ownerVO = new OwnerVO();
		if (ownerModel == null)
			return null;
		ownerVO.setId(ownerModel.getId());
		ownerVO.setFirstName(ownerModel.getFirstName());
		ownerVO.setLastName(ownerModel.getLastName());
		ownerVO.setEmail(ownerModel.getEmail());
		ownerVO.setMobileNumber(ownerModel.getMobileNumber());
		ownerVO.setGender(ownerModel.getGender());
		ownerVO.setCity(ownerModel.getCity());
		ownerVO.setDob(ownerModel.getDob());
		ownerVO.setCreatedDate(ownerModel.getCreatedDate());
		ownerVO.setState(ownerModel.getState());
		ownerVO.setStreet(ownerModel.getStreet());
		// ownerModel.setUser(user);
		return ownerVO;
	}

	public static VehicleModel convertVehicleVoToVehicleModel(VehicleVO vehicleVo) {
		VehicleModel vehicleModel = new VehicleModel();

		vehicleModel.setId(vehicleVo.getId());
		vehicleModel.setVehicleNumber(vehicleVo.getVehicleNumber());
		vehicleModel.setVehicleType(vehicleVo.getVehicleType());
		vehicleModel.setVehicleColor(vehicleVo.getVehicleColor());
		vehicleModel.setVehicleModel(vehicleVo.getVehicleModel());
		vehicleModel.setNumberOfSeat(vehicleVo.getNumberOfSeat());
		vehicleModel.setRcBook(vehicleVo.getRcBook());

//		vehicleModel.setDriver(convertDriverVOToDriverModel(vehicleVo.getDriver()));
		
		vehicleModel = convertObjectToOtherObject(vehicleVo, VehicleModel.class);

		return vehicleModel;
	}

	public static VehicleVO convertVehicleModelToVehicleVo(VehicleModel vehicleModel) {
		VehicleVO vehicleVo = new VehicleVO();

		vehicleVo.setId(vehicleModel.getId());
		vehicleVo.setVehicleNumber(vehicleModel.getVehicleNumber());
		vehicleVo.setVehicleType(vehicleModel.getVehicleType());
		vehicleVo.setVehicleColor(vehicleModel.getVehicleColor());
		vehicleVo.setVehicleModel(vehicleModel.getVehicleModel());
		vehicleVo.setNumberOfSeat(vehicleModel.getNumberOfSeat());
		vehicleVo.setRcBook(vehicleModel.getRcBook());

		vehicleVo.setDriver(convertDriverModelToDriverVo(vehicleModel.getDriver()));
		return vehicleVo;
	}

	public static DriverModel convertDriverVOToDriverModel(DriverVO driverVO) {
		DriverModel driverModel = convertObjectToOtherObject(driverVO, DriverModel.class);
		return driverModel;
	}

	public static DriverVO convertDriverModelToDriverVo(DriverModel driverModel) {
		DriverVO driverVO = convertObjectToOtherObject(driverModel, DriverVO.class);
		return driverVO;
	}

	public static UserModel convertUserVOToUserModel(UserVO userVO) {
		UserModel userModel = convertObjectToOtherObject(userVO, UserModel.class);
		return userModel;
	}

	public static UserVO convertUserModelToUserVo(UserModel userModel) {
		UserVO userVO = convertObjectToOtherObject(userModel, UserVO.class);
		return userVO;
	}

	public static VehicleDriverModel convertVehicleDriverVOToVehicleDriverModel(VehicleDriverVO vehicleDriverVO) {
		VehicleDriverModel vehicleDriverModel = convertObjectToOtherObject(vehicleDriverVO, VehicleDriverModel.class);
		return vehicleDriverModel;
	}

	public static VehicleDriverVO convertVehicleDriverModelToVehicleDriverVO(VehicleDriverModel vehicleDriverModel) {
		VehicleDriverVO vehicleDriverVO = convertObjectToOtherObject(vehicleDriverModel, VehicleDriverVO.class);
		return vehicleDriverVO;
	}

	public static <T> T convertObjectToOtherObject(Object driverModel, Class<T> valueType) {
		T t = null;
		try {
			ObjectMapper Obj = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String jsonStr = Obj.writeValueAsString(driverModel);

			t = Obj.readValue(jsonStr, valueType);

		} catch (IOException e) {
			throw new JsonConvertException(e.getMessage(), e);
		}

		return t;
	}

}
