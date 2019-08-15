package com.transporter.dao;

import com.transporter.model.CustomerModel;
import com.transporter.vo.CustomerVO;

public interface CustomerDao extends DefaultDao {

	int updateCustomer(CustomerModel convertVOToModel);

	CustomerModel findCustomerByUserId(Long id);

}
