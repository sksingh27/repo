package com.bkc.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bkc.bean.VillageBean;
import com.bkc.bean.VillageLevelBean;
import com.bkc.model.CropDetails;
import com.bkc.model.Master;
import com.bkc.model.StationDetails;
import com.bkc.model.TehsilDetails;
import com.bkc.model.TehsilVillageDetailsNew;
import com.bkc.staticclasses.GetRoundedLatLon;
@Repository("sddao")
public class StationDetailsDaoImpl implements StationDetailsDao {

	@Autowired
	@Qualifier("agroSessionFactory")
	private SessionFactory sessionFactory;
		
	@SuppressWarnings("unchecked")
	@Override
	public List<StationDetails> listData() {
		// TODO Auto-generated method stub
		return (List<StationDetails>) sessionFactory.getCurrentSession().createCriteria(StationDetails.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StationDetails> getStation(String state) {
		// TODO Auto-generated method stub
		
	//	System.out.println(" state in dao: "+ state );
		
//		return (List<stationDetails>)sessionFactory.getCurrentSession().createQuery("from stationDetails where state='"+ state+"'").list();
		
		
		
		return (List<StationDetails>) sessionFactory.getCurrentSession().createCriteria(StationDetails.class)
				.setProjection(Projections.distinct(Projections.property("cityName")))
				.add(Restrictions.eq("state", state.trim()))
				.addOrder(Order.asc("cityName")).list();
	}

	@Override
	public Integer getStationID(String station,String state) {		
		return (Integer) sessionFactory.getCurrentSession().createCriteria(StationDetails.class)
				.setProjection(Projections.property("id.id"))
				.add(Restrictions.eq("cityName", station))
				.add(Restrictions.eq("state", state)).list().get(0);
	}
	@Override
	public List<String> getDistrictonState(String state) {		
		Session session= sessionFactory.getCurrentSession();
		SQLQuery criteria = session.createSQLQuery("Select district,districtId,dt.stateId from districtDetails dt,stateDetails sd where sd.state like '"+state+"' and sd.stateId=dt.stateId order by district");			
		List temp=criteria.list();
		List<String> districts=new ArrayList<>();
		Iterator itr = temp.iterator();
		while(itr.hasNext()){
			Object []obj = (Object[]) itr.next();
			districts.add(String.valueOf(obj[0]));
		}		
		return districts;
	} 
	
	@Override
	public List<VillageLevelBean> getTehsil(String state,String district,String tehsil) {
		//System.out.println("tehsil------------->"+tehsil);
		VillageLevelBean villageLevelBean;
		Session session= sessionFactory.getCurrentSession();		
		SQLQuery criteria = session.createSQLQuery("Select th.id,th.village, th.tehsil,th.stateId,th.districtId from tehsilVillageDetails th,districtDetails dt,stateDetails sd where sd.state = '"+state+"' and sd.stateId=dt.stateId and dt.district = '"+district+"' and th.stateId=sd.stateId and th.districtId=dt.districtId and th.tehsil = '"+tehsil+"' order by th.village");			
		List temp=criteria.list();
		List<VillageLevelBean> villages=new ArrayList<VillageLevelBean>();
		Iterator itr = temp.iterator();
		while(itr.hasNext()){
			Object []obj = (Object[]) itr.next();
			villageLevelBean = new VillageLevelBean();
			villageLevelBean.setVillageId(Integer.parseInt(String.valueOf(obj[0])));
		//	System.out.println("village id is---------------------------------------->"+String.valueOf(obj[0]));
			villageLevelBean.setVillage(String.valueOf(obj[1]).trim());
			villages.add(villageLevelBean);
		}
		return villages;
	}
	
	@Override
	public List<String> getStationsOnDist(String state,String district){		
		Session session= sessionFactory.getCurrentSession();
		SQLQuery criteria = session.createSQLQuery("Select distinct th.tehsil,th.stateId,th.districtId from tehsilVillageDetails th,districtDetails dt,stateDetails sd where sd.state like '"+state+"' and sd.stateId=dt.stateId and dt.district like '"+district+"' and th.stateId=sd.stateId and th.districtId=dt.districtId order by th.tehsil");
		List temp=criteria.list();
		List<String> districts=new ArrayList<>();
		Iterator itr = temp.iterator();
		while(itr.hasNext()){
			Object []obj = (Object[]) itr.next();
			districts.add(String.valueOf(obj[0]));
		}						
		return districts;
	}
	
	@Override
	public List<VillageLevelBean> villagesOnGeoLocation(float lat,float lon){
		/*lat=getLatLonPoint(lat);
		  lon=getLatLonPoint(lon);*/
		//lat=GetRoundedLatLon.getLatLonPoint(lat);
		//lon=GetRoundedLatLon.getLatLonPoint(lon);
		//System.out.println("latlong after rounding");
		
	//	System.out.println(lon);
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(TehsilDetails.class);
		criteria.add(Restrictions.between("lat", lat-0.05f, lat+0.05f));
		criteria.add(Restrictions.between("lon", lon-0.05f, lon+0.05f));
		criteria.addOrder(Order.asc("village"));
		ProjectionList plist=Projections.projectionList();
		plist.add(Projections.distinct(Projections.property("village")));
		plist.add(Projections.property("id.id"));
		criteria.setProjection(plist);
		List villageList=criteria.list();
	//	System.out.println("size of village list"+villageList.size());
		List<VillageLevelBean> villageBeanList=new ArrayList<>();
		for(Object obj:villageList){
			Object []arr=(Object[])obj;
			VillageLevelBean bean= new VillageLevelBean();
			bean.setVillage(arr[0].toString());
			bean.setVillageId(Integer.parseInt(arr[1].toString()));
			
		//	System.out.println(arr[0].toString());
		//	System.out.println(arr[1].toString());
			villageBeanList.add(bean);
		}
		
		return villageBeanList;
		
	}

	private Float getLatLonPoint(Float point) {
		// Float pts = Float.valueOf(point.trim()).floatValue();
		Integer intPt = point.intValue();
        String point1 = String.valueOf(point);
     //   System.out.println("point1 "+point1);
        
		
		String point2 = point1.substring(point1.indexOf('.') + 1,point1.indexOf('.') + 3);
	//	System.out.println("point2 "+point2);
		Float aa = Float.parseFloat(point2);
		if (aa >= 0.00f && aa < 30.00f)
			point = intPt + 0.00f;
		else if (aa >= 30.00f && aa < 75.00f)
			point = intPt + 0.50f;
		else if (aa > 75.00f)
			point = intPt + 1.00f;

		return point;
	}

	@Override
	public VillageBean getVillageOnId(int id) {
		Session session=sessionFactory.getCurrentSession();
	/*	Criteria criteria=session.createCriteria(TehsilVillageDetailsNew.class); 
		criteria.add(Restrictions.eq("iid", id));
		TehsilVillageDetailsNew detailsNew=null;*/
		String sql="select tvd.lat,tvd.lon,tvd.village,tvd.tehsil,dd.district,sd.state from tehsilVillageDetails as tvd ,districtDetails as dd, stateDetails as sd where tvd.id="+id+" and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and dd.stateId=sd.stateId ";
		Object obj=session.createSQLQuery(sql).uniqueResult();
		VillageBean bean= new VillageBean();
		Object[] arr=(Object[])obj;
		bean.setLat(Float.parseFloat(arr[0].toString()));
		bean.setLon(Float.parseFloat(arr[1].toString()));
		bean.setVillageName(arr[2].toString());
		bean.setTehsil(arr[3].toString());
		bean.setDistrict(arr[4].toString());
		bean.setState(arr[5].toString());
		return bean;
	}
}
