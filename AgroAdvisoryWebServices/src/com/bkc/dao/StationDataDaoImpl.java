package com.bkc.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bkc.model.Master;
import com.bkc.model.StationDetails;
@Repository("stdao")
public class StationDataDaoImpl implements StationDataDao{
	
	@Autowired
	@Qualifier("agroSessionFactory")
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<StationDetails> getStation(String state) {		
		List<StationDetails> list = sessionFactory.getCurrentSession().createCriteria(StationDetails.class)
				.addOrder(Order.asc("cityName"))				
		.add(Restrictions.eq("state", state.trim()))
		.setProjection(Projections.projectionList()
                .add(Projections.distinct(Projections.property("cityName")))
                .add(Projections.property("id.id"))).list();				
		
		return list;
	}



}
