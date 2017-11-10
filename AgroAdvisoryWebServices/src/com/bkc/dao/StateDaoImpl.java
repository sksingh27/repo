package com.bkc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bkc.model.StateDetails;
@Repository("stateDao")
@Transactional
public class StateDaoImpl implements StateDao {

	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public List<StateDetails> getDistinctStates() {
		Session session= sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(StateDetails.class);
		return criteria.list();
	}

}
