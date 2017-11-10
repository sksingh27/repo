package com.bkc.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bkc.model.CropDetails;
import com.bkc.model.CropVarietyDetail;
import com.bkc.model.StateDetails;

@Repository("rDao")
public class CropVarietyDetailDaoImpl implements CropVarietyDetailDao {

	@Autowired
	@Qualifier("agroSessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CropVarietyDetail> listData() {
		// TODO Auto-generated method stub
		
		return (List<CropVarietyDetail>) sessionFactory.getCurrentSession().createCriteria(CropVarietyDetail.class).list();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getState() {
		// TODO Auto-generated method stub
		
		return (List<String>) sessionFactory.getCurrentSession().createCriteria(StateDetails.class)
				
				.setProjection(Projections.distinct(Projections.property("state"))).list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CropVarietyDetail> getVariety(String state,int cropId) {
		// TODO Auto-generated method stub
		return (List<CropVarietyDetail>) sessionFactory.getCurrentSession().createCriteria(CropVarietyDetail.class).setProjection(Projections.distinct(Projections.property("cropVariety")))
				.add(Restrictions.eq("cropId", cropId))
				.add(Restrictions.like("state", state))
				.addOrder(Order.asc("cropVariety"))
				.list();
	}

}
