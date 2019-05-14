package com.transporter.service;

import com.transporter.vo.OwnerVO;

public interface OwnerService extends DefaultService {
	
	public Long registerOwner(OwnerVO ownerVO);
	
}
