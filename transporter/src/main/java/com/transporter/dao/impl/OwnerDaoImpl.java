package com.transporter.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.transporter.dao.OwnerDao;
import com.transporter.model.OwnerModel;

@Repository
public class OwnerDaoImpl extends DefaultDaoImpl implements OwnerDao {

	@Override
	public OwnerModel getOwnerFromVehicleId(Long vehicleId) {

		StringBuilder builder = new StringBuilder("");
		builder.append(" select owner from OwnerModel owner ");
		builder.append(" inner join owner.vehicles vehicle ");
		builder.append(" where vehicle.id = :vehicleId ");

		Query query = createQuery(builder.toString());

		query.setParameter("vehicleId", vehicleId);
		return (OwnerModel) firstResult(query.list());
	}
}
