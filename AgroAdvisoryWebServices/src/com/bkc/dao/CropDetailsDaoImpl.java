package com.bkc.dao;

import java.util.ArrayList;
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

import com.bkc.bean.CropVarietyBean;
import com.bkc.model.CropDetails;
import com.bkc.model.CropVariety;

@Repository("cropcaldao")
public class CropDetailsDaoImpl  implements CropDetailsdao {

	@Autowired
	@Qualifier("agroSessionFactory")
	private SessionFactory sessionFactory;
		
	@SuppressWarnings("unchecked")
	@Override
	public List<CropDetails> listData() {		
		return (List<CropDetails>) sessionFactory.getCurrentSession().createCriteria(CropDetails.class).addOrder(Order.asc("cropname")).list();		
	}

	@Override
	public Integer getCropId(String cropname) {
		//System.out.println(" cropname : "+ cropname);
		return (Integer) sessionFactory.getCurrentSession().createCriteria(CropDetails.class)
				.setProjection(Projections.property("cropId"))
				.add(Restrictions.eq("cropname", cropname)).list().get(0);
	}
    @Override
	public CropDetails getCropDetails(int cropId){
		
		Session session= sessionFactory.getCurrentSession();
		Criteria c= session.createCriteria(CropDetails.class);
		c.add(Restrictions.eq("cropId", cropId));
		Object cd= (Object)c.list().get(0);
		
		CropDetails cc= (CropDetails)cd;
		return cc;
	}

	/* (non-Javadoc)
	 * @see com.bkc.dao.CropDetailsdao#getVariety(int, java.lang.String)
	 */
	@Override
	public List<CropVarietyBean> getVariety(int cropId, String languageId) {
		List<CropVariety> list=sessionFactory.getCurrentSession().createCriteria(CropVariety.class).add(Restrictions.eq("id.cropId", cropId)).add(Restrictions.eq("id.languageId", languageId)).list();
		List<CropVarietyBean> beanList= new ArrayList<>();
		for(CropVariety m:list){
			CropVarietyBean b= new CropVarietyBean();
			b.setCropId(m.getId().getCropId());
			b.setVariertyId(m.getId().getVarietyId());
			b.setVariety(m.getVarietyName());
			beanList.add(b);
		}
		return beanList;
	}
	
}
