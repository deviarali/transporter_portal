package com.transporter.service;

import com.transporter.vo.OwnerVO;
import com.transporter.vo.UserVO;

public interface OwnerService extends DefaultService {
	
	public Long registerOwner(OwnerVO ownerVO);

	public UserVO isUserExists(OwnerVO ownerVO);
	
}
