package com.transporter.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.transporter.dao.CustomerDao;
import com.transporter.model.CustomerModel;
import com.transporter.model.UserModel;
import com.transporter.vo.CustomerVO;
import com.transporter.vo.UserVO;

@Repository
public class CustomerDaoImpl extends DefaultDaoImpl implements CustomerDao {

	@Override
	public int updateCustomer(CustomerModel customerModel) {
		String sqlQuery = "UPDATE CustomerModel cm SET cm.firstName= :firstName, cm.lastName= :lastName WHERE cm.id= :id";
		Query query = getSession().createQuery(sqlQuery);
		query.setParameter("firstName", customerModel.getFirstName());
		query.setParameter("lastName", customerModel.getLastName());
		query.setParameter("id", customerModel.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public CustomerModel findCustomerByUserId(Long id) {
		String sqlQuery = "From CustomerModel cm where cm.user.id= :id";
		Query query = getSession().createQuery(sqlQuery);
		query.setParameter("id", id);
		return (CustomerModel) query.uniqueResult();
	}

}
