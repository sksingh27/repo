package com.bkc.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.CropBean;
import com.bkc.bean.CropBean2;
import com.bkc.bean.RainfallBean;
import com.bkc.bean.UserNews;
import com.bkc.bean.UserNewsLogin;
import com.bkc.bean.UserNewsOld;
import com.bkc.model.AddNewCrop;
import com.bkc.model.CropCalendar;
import com.bkc.model.CropDetails;
import com.bkc.model.DiseaseDetails;
import com.bkc.model.DiseaseDetailsHindi;
import com.bkc.model.GridWiseCola;
import com.bkc.model.InsectPestHindi;
import com.bkc.model.MgntPracticesHindi;
import com.bkc.model.NewsPojo;
import com.bkc.model.NutrientDetails;
import com.bkc.model.NutrientDetailsHindi;
import com.bkc.model.UserCropCalendar;
import com.bkc.model.UserDetails;
import com.bkc.model.UserTokenRegistration;
import com.bkc.service.UserDetailsService;
import com.bkc.staticclasses.GetRoundedLatLon;

import java.beans.Expression;
import java.text.*;
import com.sun.org.apache.regexp.internal.recompile;

@Repository("advisoryDao")
@Transactional
public class AdvisoryDaoImpl implements AdvisoryDao {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

	//String dateOfStage = "";
	int noOfDays;
	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory SessionFactory;

	public SessionFactory getSessionFactory() {
		return SessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		SessionFactory = sessionFactory;
	}

	@Autowired
	UserDetailsService userService;

	@Override
	public HashMap<String, Float> before7Days(Date d, float lat, float lon) {
		HashMap<String, Float> li = new HashMap<String, Float>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = format.format(d);

		String d2 = format.format(DateUtils.addDays(d, -7));

		Session session = SessionFactory.getCurrentSession();
		Criteria minTempCri = session.createCriteria(GridWiseCola.class);
		minTempCri.setCacheable(true);
		minTempCri.add(Restrictions.between("id.f_date", d2, d1));
		minTempCri.add(Restrictions.between("id.lat", lat - 0.25f, lat + 0.25f));
		minTempCri.add(Restrictions.between("id.lon", lon - 0.25f, lon + 0.25f));
		minTempCri.add(Restrictions.eq("id.gmt", 3));
		minTempCri.setProjection(Projections.avg("min_temp"));

		List minTemp = minTempCri.list();
		Iterator itr = minTemp.iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			li.put("minTemp", Float.parseFloat(obj.toString()));
		}

		Criteria maxTempCri = session.createCriteria(GridWiseCola.class);
		maxTempCri.setCacheable(true);
		maxTempCri.add(Restrictions.between("id.f_date", d2, d1));
		maxTempCri.add(Restrictions.between("id.lat", lat - 0.25f, lat + 0.25f));
		maxTempCri.add(Restrictions.between("id.lon", lon - 0.25f, lon + 0.25f));
		maxTempCri.add(Restrictions.eq("id.gmt", 12));
		maxTempCri.setProjection(Projections.avg("max_temp"));

		List maxTemp = maxTempCri.list();

		itr = maxTemp.iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			li.put("maxTemp", Float.parseFloat(obj.toString()));
		}

		Criteria maxRHCri = session.createCriteria(GridWiseCola.class);
		maxRHCri.setCacheable(true);
		maxRHCri.add(Restrictions.between("id.f_date", d2, d1));
		maxRHCri.add(Restrictions.between("id.lat", lat - 0.25f, lat + 0.25f));
		maxRHCri.add(Restrictions.between("id.lon", lon - 0.25f, lon + 0.25f));
		maxRHCri.add(Restrictions.eq("id.gmt", 3));
		maxRHCri.setProjection(Projections.avg("max_rh"));

		List maxRh = maxRHCri.list();

		itr = maxRh.iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			li.put("maxRh", Float.parseFloat(obj.toString()));
		}

		Criteria minRHCri = session.createCriteria(GridWiseCola.class);
		minTempCri.setCacheable(true);
		minRHCri.add(Restrictions.between("id.f_date", d2, d1));
		minRHCri.add(Restrictions.between("id.lat", lat - 0.25f, lat + 0.25f));
		minRHCri.add(Restrictions.between("id.lon", lon - 0.25f, lon + 0.25f));
		minRHCri.add(Restrictions.eq("id.gmt", 12));
		minRHCri.setProjection(Projections.avg("max_rh"));

		List minRh = minRHCri.list();

		Criteria smCri = session.createCriteria(GridWiseCola.class);
		smCri.setCacheable(true);
		smCri.add(Restrictions.between("id.f_date", d2, d1));
		smCri.add(Restrictions.between("id.lat", lat - 0.25f, lat + 0.25f));
		smCri.add(Restrictions.between("id.lon", lon - 0.25f, lon + 0.25f));
		smCri.add(Restrictions.eq("id.gmt", 12));
		smCri.setProjection(Projections.avg("soil_moisture"));

		List sm = smCri.list();

		itr = sm.iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			li.put("sm", Float.parseFloat(obj.toString()));
		}

		itr = minRh.iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			li.put("minRh", Float.parseFloat(obj.toString()));
		}

		Criteria rfCri = session.createCriteria(GridWiseCola.class);
		rfCri.setCacheable(true);
		rfCri.add(Restrictions.between("id.f_date", d2, d1));
		rfCri.add(Restrictions.between("id.lat", lat - 0.25f, lat + 0.25f));
		rfCri.add(Restrictions.between("id.lon", lon - 0.25f, lon + 0.25f));
		rfCri.add(Restrictions.eq("id.gmt", 3));

		List<GridWiseCola> rf = rfCri.list();
		int i = 0;
		for (GridWiseCola gc : rf) {

			li.put("r" + i, gc.getRainfall());
			i++;
		}
		//System.out.println("stats1 :-" + SessionFactory.getStatistics());
		return li;

	}

	@Override
	public List<GridWiseCola> beforeRainfall(Date d, float lat, float lon) {
		List<GridWiseCola> rf = new ArrayList<GridWiseCola>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = format.format(d);

		String d2 = format.format(DateUtils.addDays(d, -7));

		Session session = SessionFactory.getCurrentSession();
		//System.out.println("stats2 :-" + SessionFactory.getStatistics());
		
		Criteria criteria = session.createCriteria(GridWiseCola.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.between("id.f_date", d2, d1));
		criteria.add(Restrictions.between("id.lat", lat - 0.25f, lat + 0.25f));
		criteria.add(Restrictions.between("id.lon", lon - 0.25f, lon + 0.25f));
		criteria.add(Restrictions.eq("id.gmt", 3));
		rf = criteria.list();
		//System.out.println("stats1 :-" + SessionFactory.getStatistics());
		return rf;
	}

	@Override
	public List<CropCalendar> cropCalList(int cropId, String stage) {
		//System.out.println(cropId + "----------------------------" + stage);
		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CropCalendar.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.add(Restrictions.eq("id.subStage", stage));
		List<CropCalendar> cList = criteria.list();

		return cList;
	}

	@Override
	public HashMap<String, Float> ahead7Days(Date d, float lat, float lon) {

		HashMap<String, Float> li = new HashMap<String, Float>();
		lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = format.format(d);
		String d2 = format.format(DateUtils.addDays(d, 7));
		//System.out.println("inside ahead 7 days list where date 1: " + d1 + " date 2: " + d2);
		String sevenDaysForecastHql = "select avg(max_temp),avg(min_temp),avg(max_rh) from GridWiseCola where max_temp<>999.90 and min_temp<>999.90 and max_rh<>999.90 and id.lat="
				+ lat + " and id.lon =" + lon + " and id.f_date between '" + d1 + "' and '" + d2 + "'";
		//System.out.println(sevenDaysForecastHql);
		List tempList = SessionFactory.getCurrentSession().createQuery(sevenDaysForecastHql).setCacheable(true).list();
		for (Object obj : tempList) {
			Object[] arr = (Object[]) obj;
			//System.out.println("-----------------------inside ahead 7 Days----------");
			li.put("maxTemp", Float.parseFloat(arr[0].toString()));
			li.put("minTemp", Float.parseFloat(arr[1].toString()));
			li.put("maxRh", Float.parseFloat(arr[2].toString()));

		}
		/*
		 * try { System.out.println("lat long after rounding lat:" + lat +
		 * "lon:" + lon); SimpleDateFormat format = new
		 * SimpleDateFormat("yyyy-MM-dd"); String d1 = format.format(d);
		 * 
		 * String d2 = format.format(DateUtils.addDays(d, 7));
		 * System.out.println("inside ahead 7 days list where date 1: " + d1 +
		 * " date 2: " + d2); Session session =
		 * SessionFactory.getCurrentSession(); Criteria minTempCri =
		 * session.createCriteria(GridWiseCola.class);
		 * minTempCri.setCacheable(true);
		 * minTempCri.add(Restrictions.between("id.f_date", d1, d2));
		 * minTempCri.add(Restrictions.eq("id.lat", lat));
		 * minTempCri.add(Restrictions.eq("id.lon", lon));
		 * minTempCri.add(Restrictions.eq("id.gmt", 3));
		 * minTempCri.setProjection(Projections.avg("min_temp"));
		 * 
		 * List minTemp = minTempCri.list(); Iterator itr = minTemp.iterator();
		 * while (itr.hasNext()) { Object obj = (Object) itr.next();
		 * li.put("minTemp", Float.parseFloat(obj.toString())); }
		 * 
		 * Criteria maxTempCri = session.createCriteria(GridWiseCola.class);
		 * maxTempCri.setCacheable(true);
		 * maxTempCri.add(Restrictions.between("id.f_date", d1, d2));
		 * maxTempCri.add(Restrictions.eq("id.lat", lat));
		 * maxTempCri.add(Restrictions.eq("id.lon", lon));
		 * maxTempCri.add(Restrictions.eq("id.gmt", 12));
		 * maxTempCri.setProjection(Projections.avg("max_temp"));
		 * 
		 * List maxTemp = maxTempCri.list();
		 * 
		 * itr = maxTemp.iterator(); while (itr.hasNext()) { Object obj =
		 * (Object) itr.next(); li.put("maxTemp",
		 * Float.parseFloat(obj.toString())); }
		 * 
		 * Criteria maxRHCri = session.createCriteria(GridWiseCola.class);
		 * maxRHCri.setCacheable(true);
		 * maxRHCri.add(Restrictions.between("id.f_date", d1, d2));
		 * maxRHCri.add(Restrictions.eq("id.lat", lat));
		 * maxRHCri.add(Restrictions.eq("id.lon", lon));
		 * maxRHCri.add(Restrictions.eq("id.gmt", 3));
		 * maxRHCri.setProjection(Projections.avg("max_rh"));
		 * 
		 * List maxRh = maxRHCri.list();
		 * 
		 * itr = maxRh.iterator(); while (itr.hasNext()) { Object obj = (Object)
		 * itr.next(); li.put("maxRh", Float.parseFloat(obj.toString())); }
		 * 
		 * Criteria minRHCri = session.createCriteria(GridWiseCola.class);
		 * minRHCri.setCacheable(true);
		 * minRHCri.add(Restrictions.between("id.f_date", d1, d2));
		 * minRHCri.add(Restrictions.eq("id.lat", lat));
		 * minRHCri.add(Restrictions.eq("id.lon", lon));
		 * minRHCri.add(Restrictions.eq("id.gmt", 12));
		 * minRHCri.setProjection(Projections.avg("max_rh"));
		 * 
		 * List minRh = minRHCri.list();
		 * 
		 * itr = minRh.iterator(); while (itr.hasNext()) { Object obj = (Object)
		 * itr.next(); li.put("minRh", Float.parseFloat(obj.toString())); }
		 * 
		 * Criteria smCri = session.createCriteria(GridWiseCola.class);
		 * smCri.setCacheable(true); smCri.add(Restrictions.between("id.f_date",
		 * d1, d2)); smCri.add(Restrictions.eq("id.lat", lat));
		 * smCri.add(Restrictions.eq("id.lon", lon));
		 * smCri.add(Restrictions.eq("id.gmt", 12));
		 * smCri.setProjection(Projections.avg("soil_moisture"));
		 * 
		 * List sm = smCri.list();
		 * 
		 * itr = sm.iterator(); while (itr.hasNext()) { Object obj = (Object)
		 * itr.next(); li.put("sm", Float.parseFloat(obj.toString())); }
		 * 
		 * Criteria rfCri = session.createCriteria(GridWiseCola.class);
		 * rfCri.setCacheable(true); rfCri.add(Restrictions.between("id.f_date",
		 * d1, d2)); // rfCri.add(Restrictions.ge("id.f_date", d1));
		 * rfCri.add(Restrictions.eq("id.lat", lat));
		 * rfCri.add(Restrictions.eq("id.lon", lon));
		 * rfCri.setProjection(Projections.sum("rainfall"));
		 * 
		 * List<GridWiseCola> rf = rfCri.list(); Iterator<GridWiseCola> ite =
		 * rf.iterator(); float rainFall = 0; while (ite.hasNext()) { Object o =
		 * (Object) ite.next(); System.out.println(String.valueOf(0)); rainFall
		 * = Float.parseFloat(String.valueOf(o));
		 * 
		 * }
		 * 
		 * 
		 * int i=0; for(GridWiseCola gc:rf){
		 * 
		 * li.put("r"+i,gc.getRainfall() ); i++; }
		 * 
		 * System.out.println("stats3 :-" + SessionFactory.getStatistics());
		 * li.put("r", rainFall); } catch (NullPointerException e) {
		 * System.out.println("data not found on server"); e.printStackTrace();
		 * }
		 */
		return li;
	}

	/*
	 * @Override public List<DiseaseDetails> ds(String attackStage,int cropId) {
	 * 
	 * String st=attackStage; if(attackStage.isEmpty()){ st=null; }
	 * 
	 * Session session= SessionFactory.getCurrentSession(); Criteria criteria=
	 * session.createCriteria(DiseaseDetails.class);
	 * criteria.add(Restrictions.eq("id.cropId", cropId));
	 * criteria.add(Restrictions.like("id.attackStage", st,MatchMode.ANYWHERE));
	 * List<DiseaseDetails> details= criteria.list();
	 * System.out.println(details.size()+"..........asfljasfkas");
	 * 
	 * 
	 * 
	 * return details; }
	 */

	@Override
	public List<GridWiseCola> sevenDaysAhead(float lat, float lon, Date d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = format.format(d);

		String d2 = format.format(DateUtils.addDays(d, 7));
		//System.out.println("date " + d1 + "-----" + d2);

		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GridWiseCola.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.between("id.f_date", d1, d2));

		/*criteria.add(Restrictions.eq("id.lat", getLatLonPoint(lat)));
		criteria.add(Restrictions.eq("id.lon", getLatLonPoint(lon)));
		*/criteria.add(Restrictions.eq("id.lat", lat));
		criteria.add(Restrictions.eq("id.lon", lon));
		
		criteria.add(Restrictions.eq("id.gmt", 12));
		System.out.println(SessionFactory.getStatistics());
		System.out.println("--------------------area to be cached-----------------------------");
		List<GridWiseCola> lsit = criteria.list();
		System.out.println("--------------------area to be cached-----------------------------");
		/*
		 * List<GridWiseCola> tempListof7days = new ArrayList<GridWiseCola>();
		 * int c = 0; String previousDate = null;
		 * 
		 * // this takes only unique dates from the list for (GridWiseCola gcd :
		 * lsit) { if (c == 0) { tempListof7days.add(gcd); previousDate =
		 * gcd.getId().getF_date(); }
		 * 
		 * else {
		 * 
		 * if (!previousDate.equals(gcd.getId().getF_date())) {
		 * System.out.println("inside if "); tempListof7days.add(gcd);
		 * previousDate = gcd.getId().getF_date(); } } c = c + 1;
		 * 
		 * }
		 */
		//System.out.println("stats5 :-" + SessionFactory.getStatistics());
		return lsit;
	}

	/*
	 * 
	 * to get current stage of current crop!!
	 */
	@Override
	public String getList(int cropId, String phoneNo) {

		Date d1 = new Date();
		SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd");
		String dt = fm1.format(d1);
		String stage = "";
		String tempstage = "";
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Session session1 = SessionFactory.getCurrentSession();
		//formingDateFromUser(cropId, phoneNo);
		Criteria criteria = session1.createCriteria(UserCropCalendar.class);

		criteria.add(Restrictions.eq("id.phoneNo", phoneNo));
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.add(Restrictions.le("dateOfStage", dt));
		criteria.addOrder(Order.asc("noOfDays"));
		criteria.setProjection(Projections.property("subStages"));
		List userDataListFound = criteria.list();

		if (!userDataListFound.isEmpty()) {

			

			for (Object ucd : userDataListFound) {

				stage = ucd.toString();
			
			}
 
			
			return stage;
		}

		else {
			//System.out.println("no stage found");
			stage = "";
			return stage;
		}

	}

	@Override
	public List<NutrientDetailsHindi> nd(String reqStage, int cropId, String languageId) {
		// String st = reqStage;
		/*
		 * if (reqStage.isEmpty()) { st = null; }
		 */
		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(NutrientDetailsHindi.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.add(Restrictions.eq("id.languageId", languageId));
		criteria.add(Restrictions.like("requiredStage", reqStage, MatchMode.ANYWHERE));

		// System.out.println(details.size() + ".......... Nutrient Required in
		// current Stages");

		return criteria.list();
	}

	@Override
	public List<CropCalendar> Ideal(String reqStage, int cropId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<AdvisoryGujarat> advisoryDetail(Date currentdate,
	 * int cropId) { SimpleDateFormat format = new
	 * SimpleDateFormat("yyyy-MM-dd"); String d1 = format.format(currentdate);
	 * List<AdvisoryGujarat> agList =
	 * SessionFactory.getCurrentSession().createCriteria(AdvisoryGujarat.class)
	 * .add(Restrictions.eq("cropId", cropId)).add(Restrictions.le("startDate",
	 * d1)) .add(Restrictions.ge("endDate", d1)).list();
	 * 
	 * return agList; }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<AdvisoryGujarat> advisoryChartDetail(int cropId) {
	 * List<AdvisoryGujarat> chList =
	 * SessionFactory.getCurrentSession().createCriteria(AdvisoryGujarat.class)
	 * .add(Restrictions.eq("cropId",
	 * cropId)).addOrder(Order.asc("startDate")).list();
	 * 
	 * return chList; }
	 */

	@Override
	public List<DiseaseDetailsHindi> ds(String attackStage, int cropId, String languageId) {
		String st = attackStage;
		if (attackStage.isEmpty()) {
			st = null;
		}

		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(DiseaseDetailsHindi.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.add(Restrictions.eq("id.attackStage", attackStage));
		criteria.add(Restrictions.eq("id.languageId", languageId));
		List<DiseaseDetailsHindi> details = criteria.list();
		//System.out.println(details.size() + "..........asfljasfkas");

		return details;

	}

	@Override
	public List<MgntPracticesHindi> getManagementPracticeHindi(int cropId, String state, String languageId,String phoneNo,int noOfDays) {
		//List<MgntPracticesHindi> managementPractice = new ArrayList<MgntPracticesHindi>();

		//int year = Calendar.getInstance().get(Calendar.YEAR);
	//	int df=noOfDays;
		
		Session session = SessionFactory.getCurrentSession();
		
		List<MgntPracticesHindi> tempList = new ArrayList<MgntPracticesHindi>();
		/*Criteria criteria2 = session.createCriteria(MgntPracticesHindi.class);
		criteria2.add(Restrictions.eq("id.cropId", cropId));
		criteria2.add(Restrictions.eq("id.languageId", languageId));
		criteria2.add(Restrictions.le("daysBetween2", noOfDays));
		criteria2.add(Restrictions.ge("daysBetween1", noOfDays));
		tempList = criteria2.list();*/
		String hql="from MgntPracticesHindi where id.cropId="+cropId+" and id.languageId='"+languageId+"' and (daysBetween2 >="+noOfDays+" and daysBetween1 <="+noOfDays+")";
      System.out.println(hql);
		tempList=session.createQuery(hql).list();
		//System.out.println("size of tempList:::::::::::::-" + tempList.size());

		/*List<MgntPracticesHindi> finalList = new ArrayList<MgntPracticesHindi>();*/

	/*	for (MgntPracticesHindi obj : tempList) {

			if (obj.getDaysBetween1() == obj.getDaysBetween2()) {
				if (df == obj.getDaysBetween1()) {
					finalList.add(obj);
					//System.out.println("when  start date and end date are same and days ");
				} else {
					//System.out.println("not found");
				}

			} else {
				if (df >= obj.getDaysBetween1() && df <= obj.getDaysBetween2()) {
					//System.out.println("days are not same");
					finalList.add(obj);
				}

				else {
					//System.out.println("days are not same and not found");
				}
			}

		}
*//*
		if (!finalList.isEmpty()) {
			//System.out.println("finalList is not empty............");
			for (MgntPracticesHindi mgntObj : finalList) {
				if (mgntObj.getState().equals("Common")) {
					//System.out.println("state is common..............>!!!!!!!!!!");
					managementPractice.add(mgntObj);

				} else if (mgntObj.getState() != "Common") {
					if (mgntObj.getState().equals(state)) {
						//System.out.println("state is not common!!");
						managementPractice.add(mgntObj);
					}

				}

			}

		}*/
		//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^" + managementPractice.size());
		return tempList;

	}

	public String returnSubstage(int cropId, String subStage) {

		String subStageHindi = "";

		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CropCalendar.class);
		criteria.add(Restrictions.eq("id.subStage", subStage));
		criteria.add(Restrictions.eq("id.cropId", cropId));
		List<CropCalendar> tempList = criteria.list();

		for (CropCalendar cd : tempList) {
			subStageHindi = cd.getSubStageHindi();
		}

		return subStageHindi;
	}

/*	public void formingDateFromUser(int cropId, String phoneNo) {

		//System.out.println("inside setting forming date method");
		String formingDate = "";
		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserCropCalendar.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		// criteria.add(Restrictions.eq("id.stationId", stationId));
		criteria.add(Restrictions.eq("id.phoneNo", phoneNo));
		criteria.add(Restrictions.eq("noOfDays", 1));
		List<UserCropCalendar> userList = criteria.list();

		for (UserCropCalendar cd : userList) {
			this.dateOfStage = cd.getDateOfStage();
			//System.out.println("date of stage set" + dateOfStage);
		}

	}*/

	// calculates rainfall in stage ie if stage is of 10 days and current day
	// lies on 5th day rain rain fall is calculated 5 days before and 5 days
	// ahead
	public HashMap<String, Float> calculateRainfallInStage(int cropId, String stage, float lat, float lon,
			String phoneNo) {
		HashMap<String, Float> finalList = new HashMap<String, Float>();
		Session session = SessionFactory.getCurrentSession();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// System.out.println("before rounding ----------->" + lat + " : " +
		// lon);

		lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));
		// System.out.println("after rounding ----------->" + lat + " : " +
		// lon);
		Date d = new Date();

		try {
			d = dateFormat.parse(dateFormat.format(d));
		} catch (ParseException e) {

			e.printStackTrace();
		}

		String bDate = "";
		String fDate = "";
		Date cDate = new Date();
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dateFormat2.format(cDate);
		//System.out.println("current date :-" + currentDate);
		Criteria criteria2 = session.createCriteria(UserCropCalendar.class);
		criteria2.add(Restrictions.eq("id.cropId", cropId));
		criteria2.add(Restrictions.eq("id.phoneNo", phoneNo));
		// criteria2.add(Restrictions.eq("id.stationId", stationId));
		criteria2.add(Restrictions.le("dateOfStage", currentDate));
		criteria2.addOrder(Order.desc("noOfDays"));
		criteria2.setProjection(Projections.property("dateOfStage"));
		Object o = criteria2.list().get(0);

		bDate = o.toString();

		//System.out.println("user before date :-" + bDate);
		Criteria criteria3 = session.createCriteria(UserCropCalendar.class);

		try {
			criteria3.add(Restrictions.eq("id.cropId", cropId));
			criteria3.add(Restrictions.eq("id.phoneNo", phoneNo));
			// criteria3.add(Restrictions.eq("id.stationId", stationId));
			criteria3.add(Restrictions.ge("dateOfStage", currentDate));
			criteria3.addOrder(Order.asc("noOfDays"));
			criteria3.setProjection(Projections.property("dateOfStage"));
			Object o1 = criteria3.list().get(0);

			fDate = o1.toString();
		} catch (IndexOutOfBoundsException e) {

			// this is for when the crop is in last stage ......!!
			Criteria criteria4 = session.createCriteria(UserCropCalendar.class);
			criteria4.add(Restrictions.eq("id.cropId", cropId));
			criteria4.add(Restrictions.eq("id.phoneNo", phoneNo));
			// criteria4.add(Restrictions.eq("id.stationId", stationId));
			// criteria3.add(Restrictions.ge("dateOfStage", currentDate));
			criteria4.addOrder(Order.desc("noOfDays"));
			criteria4.setProjection(Projections.property("dateOfStage"));
			Object o1 = criteria4.list().get(0);
			try {
				fDate = DateUtils.addDays(format.parse(o1.toString()), 10).toString();
				//System.out.println("fdate is :-" + fDate);
			} catch (ParseException e1) {

				e1.printStackTrace();
			}
		}
		//System.out.println("user f date:-" + fDate);

		Criteria criteria4 = session.createCriteria(GridWiseCola.class);
		criteria4.setCacheable(true);
		criteria4.add(Restrictions.ge("id.f_date", currentDate)); // initialy
																	// the
																	// rainfall
																	// calculated
																	// here was
																	// between
																	// stages ie
																	// if stage
																	// is from
																	// 24th to
																	// 28th then
																	// current
																	// date is
																	// 26th then
																	// rainfall
																	// will be
																	// calculated
																	// between
																	// 24th to
																	// 28th but
																	// now as by
																	// Kailash
																	// (on 26th
																	// may
																	// 2017)we
																	// will
																	// calculate
																	// ahead 7
																	// days
																	// forecast
																	// rainfall.
		criteria4.add(Restrictions.eq("id.lat", lat));
		criteria4.add(Restrictions.eq("id.lon", lon));

		criteria4.setProjection(Projections.sum("rainfall"));

		Object fRainfall = criteria4.list().get(0);
		finalList.put("forecastRainfall", Float.parseFloat(fRainfall.toString()));

		Criteria criteria5 = session.createCriteria(GridWiseCola.class);
		criteria5.setCacheable(true);
		criteria5.add(Restrictions.between("id.f_date", bDate, currentDate));
		criteria5.add(Restrictions.eq("id.lat", lat));
		criteria5.add(Restrictions.eq("id.lon", lon));
		criteria5.setProjection(Projections.sum("rainfall"));

		Object bRainfall = criteria5.list().get(0);
		finalList.put("beforeRainfall", Float.parseFloat(bRainfall.toString()));

		//System.out.println("before rainfall user:-" + finalList.get("beforeRainfall"));
		//System.out.println("forecast rainfall user:-" + finalList.get("forecastRainfall"));

		//System.out.println("stats6 :-" + SessionFactory.getStatistics());
		return finalList;
	}

	public HashMap<String, Float> calculateRainfallInStageWeatherIndia(int cropId, String stage, float lat, float lon) {

		HashMap<String, Float> finalList = new HashMap<String, Float>();
		Session session = SessionFactory.getCurrentSession();
		int noOfDays = 0;
		int noOfDaysAhead = 0;
		int noOfDaysOfStage = 0;
		int before = 0;
		int after = 0;
		String startDate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		lat = getLatLonPoint(lat);
		lon = getLatLonPoint(lon);
		//System.out.println("lat lon after rounding ----------------------------------------->" + lat + "-------" + lon);
		Date d = new Date();
		Date sDate = null;
		try {
			d = dateFormat.parse(dateFormat.format(d));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Criteria criteria2 = session.createCriteria(CropCalendar.class);
		criteria2.add(Restrictions.eq("id.cropId", cropId));
		criteria2.add(Restrictions.eq("id.subStage", stage));
		criteria2.addOrder(Order.asc("noOfDays"));
		List<CropCalendar> listOfSubStages = criteria2.list();

		for (CropCalendar cd : listOfSubStages) {
			noOfDays = cd.getNoOfDays();
			//System.out.println("no of days are for current stage :-" + noOfDays);

		}
		if (noOfDays != 0) {
			noOfDaysAhead = noOfDaysOfNextStage(cropId, noOfDays);
			noOfDaysOfStage = noOfDaysAhead - noOfDays;
		} else {
			//System.out.println("noOfdays is zero!!!!!!!!!1");
		}

		startDate = startDateOfStage(cropId, noOfDays);
		//System.out.println("start date of stage is :-------------------------------------" + startDate);
		try {
			sDate = dateFormat.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long diff = d.getTime() - sDate.getTime();
		diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		before = (int) diff * -1;
		//System.out.println("before days :=================" + before);
		//System.out.println("no of days in next stage :" + noOfDaysOfStage);
		after = noOfDaysOfStage + before;
		//System.out.println("no of days before  :============" + before);
		//System.out.println("no of days after :============" + (after));

		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String cDate = format.format(currentDate);
		// String aheadDate = format.format(DateUtils.addDays(currentDate,
		// after)); this date the last date of current stage * changed by
		// kailash on 1 sept 2016
		String beforeDate = format.format(DateUtils.addDays(currentDate, before));
		String aheadDate = format.format(DateUtils.addDays(currentDate, 7)); // this
																				// gives
																				// 7
																				// days
																				// aheadrain
																				// fall
																				// *
																				// changed
																				// by
																				// kailash
																				// on
																				// 1
																				// sept
																				// 2016
		//System.out.println("befoore date :-" + beforeDate);
		//System.out.println("c date :-" + cDate);
		//System.out.println("ahead date :-" + aheadDate);
		Criteria criteria3 = session.createCriteria(GridWiseCola.class);
		criteria3.setCacheable(true);
		criteria3.add(Restrictions.between("id.f_date", cDate, aheadDate));
		criteria3.add(Restrictions.eq("id.lat", lat));
		criteria3.add(Restrictions.eq("id.lon", lon));
		criteria3.setProjection(Projections.sum("rainfall"));

		Object fRainfall = criteria3.list().get(0);
		finalList.put("forecastRainfall", Float.parseFloat(fRainfall.toString()));

		//System.out.println("beforeDate: is " + beforeDate);
		Criteria criteria4 = session.createCriteria(GridWiseCola.class);
		criteria4.setCacheable(true);
		criteria4.add(Restrictions.between("id.f_date", beforeDate, cDate));
		criteria4.add(Restrictions.eq("id.lat", lat));
		criteria4.add(Restrictions.eq("id.lon", lon));
		criteria4.setProjection(Projections.sum("rainfall"));

		Object bRainfall = criteria4.list().get(0);
		finalList.put("beforeRainfall", Float.parseFloat(bRainfall.toString()));

		//System.out.println("before rainfall :-" + finalList.get("beforeRainfall"));
		//System.out.println("forecast rainfall :-" + finalList.get("forecastRainfall"));

		//System.out.println("stats6 :-" + SessionFactory.getStatistics());
		return finalList;
	}

	public int noOfDaysOfNextStage(int cropId, int noOfDays) {
		int nextDay = 0;

		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CropCalendar.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.add(Restrictions.gt("noOfDays", noOfDays));
		criteria.addOrder(Order.asc("noOfDays"));
		criteria.setProjection(Projections.property("noOfDays"));
		Object o = criteria.list().get(0);

		nextDay = Integer.parseInt(String.valueOf(o));
		//System.out.println("next no of day of stage is :-------------------" + nextDay);
		return nextDay;
	}

	public String startDateOfStage(int cropId, int noOfDays) {

		//System.out.println(noOfDays);
		Date d = new Date();
		Date fDate = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(d);
		int year = calendar1.get(Calendar.YEAR);
		String date = "";
		String formingDate = "";
		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CropCalendar.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.addOrder(Order.asc("noOfDays"));

		List<CropCalendar> tempList = criteria.list();
		for (CropCalendar calendar : tempList) {

			if (calendar.getNoOfDays() == 1) {

				formingDate = calendar.getFormingDate() + "-" + year;
				//System.out.println(formingDate + ":- forming date");
				try {
					fDate = format.parse(formingDate);
					//System.out.println("fdate :" + fDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (calendar.getNoOfDays() == noOfDays) {
				noOfDays = noOfDays - 1;
				date = format.format(DateUtils.addDays(fDate, noOfDays));
				//System.out.println(date);

			}

		}

		return date;

	}

	private Float getLatLonPoint(Float point) {
		// Float pts = Float.valueOf(point.trim()).floatValue();
		Integer intPt = point.intValue();
		String point1 = String.valueOf(point);

		String point2 = point1.substring(point1.indexOf('.') + 1, point1.indexOf('.') + 3);

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
	public List<String> getInsectPest(int cropId, String phoneNo, String languageId,int noOfDays) {
		List<String> finalList = new ArrayList<String>();
		StringBuffer insectPestName = new StringBuffer();
		

		Session session = SessionFactory.getCurrentSession();
		/*
		 * Criteria criteria = session.createCriteria(UserCropCalendar.class);
		 * criteria.add(Restrictions.eq("id.cropId", cropId)); //
		 * criteria.add(Restrictions.eq("id.stationId", stationId));
		 * criteria.add(Restrictions.eq("id.phoneNo", phoneNo));
		 * List<InsectPestHindi> tempList = criteria.list();
		 */

		Criteria criteria2 = session.createCriteria(InsectPestHindi.class);
		criteria2.add(Restrictions.eq("cropId", cropId));
		criteria2.add(Restrictions.eq("languageId", languageId));
		List<InsectPestHindi> tList = criteria2.list();
		//System.out.println("size of insect pest list :-" + tList.size());

		try {
			noOfDays = getNoOfDaysFromUser(phoneNo, cropId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (InsectPestHindi iph : tList) {
			if (iph.getDaysBetween1() == iph.getDaysBetween2()) {
				//System.out.println("insect days  between1 and 2 are same");
				if (iph.getDaysBetween1() == noOfDays) {
					//System.out.println("found");
					insectPestName.append(String.valueOf(iph.getDaysBetween1() + "_" + "insect"));
					finalList.add(iph.getInsectPest());
				}

			} else {
				//System.out.println("when days are not same");
				if (noOfDays >= iph.getDaysBetween1() & noOfDays <= iph.getDaysBetween2()) {
					//System.out.println("found");
					insectPestName.append(
							String.valueOf(iph.getDaysBetween1() + "_" + iph.getDaysBetween2() + "_" + "insect"));
					finalList.add(iph.getInsectPest());
				}
			}
		}

		//System.out.println(insectPestName);
		//System.out.println("noOfdays in insectpest :-" + noOfDays);
		finalList.add(insectPestName.toString());
		return finalList;

	}

	
    public 	int getNoOfDaysFromUser(String phoneNo, int cropId) throws ParseException {

		int noOfDays = 0;
		String formingDate = null;
		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserCropCalendar.class);
		criteria.add(Restrictions.eq("id.phoneNo", phoneNo));
		criteria.add(Restrictions.eq("id.cropId", cropId));
		// criteria.add(Restrictions.eq("id.stationId", stationId));
		criteria.add(Restrictions.eq("noOfDays", 1));
        Object tList=    criteria.setProjection(Projections.property("dateOfStage")).uniqueResult();
	    formingDate = tList.toString();
		

		String tempDate = format1.format(format.parse(formingDate));
		
		Date d = new Date();
		String cDate = format1.format(d);
		d = format1.parse(cDate);
		Date fDate = format1.parse(tempDate);
		long diff = d.getTime() - fDate.getTime();
		diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		noOfDays = (int) diff;

		return noOfDays;
	}

	public HashMap<String, Float> nextDayForecast(float lat, float lon) {
		HashMap<String, Float> finalList = new HashMap<String, Float>();
		Date cDate = new Date();
		Date tDate = DateUtils.addDays(cDate, 1);
		String date = this.format.format(tDate);

		lat = getLatLonPoint(lat);
		lon = getLatLonPoint(lon);
		float tempVal = 0;

		Session session = SessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(GridWiseCola.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("id.lat", lat));
		criteria.add(Restrictions.eq("id.lon", lon));
		criteria.add(Restrictions.eq("id.f_date", date));
		criteria.setProjection(Projections.max("max_temp"));
		List<Float> maxTemp = criteria.list();

		for (float temp : maxTemp) {
			tempVal = (float) temp;
			//System.out.println(temp);
		}
		finalList.put("maxTemp", tempVal);

		Criteria criteria1 = session.createCriteria(GridWiseCola.class);
		criteria1.setCacheable(true);
		criteria1.add(Restrictions.eq("id.lat", lat));
		criteria1.add(Restrictions.eq("id.lon", lon));
		criteria1.add(Restrictions.eq("id.f_date", date));
		criteria1.setProjection(Projections.avg("max_rh"));
		List<Double> maxrH = criteria1.list();

		for (double temp : maxrH) {
			tempVal = (float) temp;
			//System.out.println(temp);
		}

		finalList.put("maxrH", tempVal);

		Criteria criteria2 = session.createCriteria(GridWiseCola.class);
		criteria2.setCacheable(true);
		criteria2.add(Restrictions.eq("id.lat", lat));
		criteria2.add(Restrictions.eq("id.lon", lon));
		criteria2.add(Restrictions.eq("id.f_date", date));
		criteria2.setProjection(Projections.sum("rainfall"));
		List<Double> rF = criteria2.list();
		//System.out.println(rF.size());
		for (double temp : rF) {
			tempVal = (float) temp;
			//System.out.println(temp);
		}

		finalList.put("rF", tempVal);

		/*
		 * String hql=
		 * "select avg(SUBSTRING(clouds,1,1)) from gridwiseCola where f_date='"
		 * +date+"' and lat="+lat+" and lon="+lon+
		 * " group by f_date order by f_date "; Query
		 * query=session.createSQLQuery(hql); List<Double> cloud=query.list();
		 */

		Criteria criteria3 = session.createCriteria(GridWiseCola.class);
		criteria3.setCacheable(true);
		criteria3.add(Restrictions.eq("id.lat", lat));
		criteria3.add(Restrictions.eq("id.lon", lon));
		criteria3.add(Restrictions.eq("id.f_date", date));
		criteria3.setProjection(Projections.avg("clouds"));
		List<Double> cloud = criteria3.list();

		for (double temp : cloud) {
			tempVal = ((float) temp) / 8;
			//System.out.println(temp);
		}

		finalList.put("cloud", tempVal);
		return finalList;

	}

	@Override
	public HashMap<String, Float> get4DaysForecast(float lat, float lon) {
		HashMap<String, Float> finaList = new HashMap<String, Float>();

		Date date = new Date();
		date = DateUtils.addDays(date, 2);
		String cDate = format.format(date);
		Date tDate = DateUtils.addDays(date, 3);
		String aDate = format.format(tDate);
		lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));
		float tempVal = 0;
		//System.out.println("adate:" + aDate + " cdate:" + cDate);
		Session session = SessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(GridWiseCola.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.between("id.f_date", cDate, aDate));
		criteria.add(Restrictions.eq("id.lat", lat));
		criteria.add(Restrictions.eq("id.lon", lon));
		criteria.add(Restrictions.eq("id.gmt", 12));
		criteria.setProjection(Projections.max("max_temp"));
		List<Float> maxTempMax = criteria.list();

		for (float temp : maxTempMax) {
			//System.out.println(tempVal);
			tempVal = temp;
			finaList.put("maxTempMax", tempVal);

		}

		Criteria criteria1 = session.createCriteria(GridWiseCola.class);
		criteria1.setCacheable(true);
		criteria1.add(Restrictions.between("id.f_date", cDate, aDate));
		criteria1.add(Restrictions.eq("id.lat", lat));
		criteria1.add(Restrictions.eq("id.lon", lon));
		criteria1.add(Restrictions.eq("id.gmt", 3));
		criteria1.setProjection(Projections.min("max_temp"));
		List<Float> maxTempMin = criteria1.list();

		for (float temp : maxTempMin) {
			tempVal = temp;
			//System.out.println(tempVal);
			finaList.put("maxTempMin", tempVal);

		}

		Criteria criteria2 = session.createCriteria(GridWiseCola.class);
		criteria2.setCacheable(true);
		criteria2.add(Restrictions.between("id.f_date", cDate, aDate));
		criteria2.add(Restrictions.eq("id.lat", lat));
		criteria2.add(Restrictions.eq("id.lon", lon));
		criteria2.setProjection(Projections.max("min_temp"));
		List<Float> minTempMax = criteria2.list();

		for (float temp : minTempMax) {
			tempVal = temp;
		//	System.out.println(tempVal);
			finaList.put("minTempMax", tempVal);

		}

		Criteria criteria3 = session.createCriteria(GridWiseCola.class);
		criteria3.setCacheable(true);
		criteria3.add(Restrictions.between("id.f_date", cDate, aDate));
		criteria3.add(Restrictions.eq("id.lat", lat));
		criteria3.add(Restrictions.eq("id.lon", lon));
		criteria3.setProjection(Projections.min("min_temp"));
		List<Float> minTempMin = criteria3.list();

		for (float temp : minTempMin) {
			tempVal = temp;
		//	System.out.println(tempVal);
			finaList.put("minTempMin", tempVal);

		}
	//	System.out.println("stats7 :-" + SessionFactory.getStatistics());
		return finaList;

	}

	// this gives rainfall not rh
	@Override
	public List<GridWiseCola> rfandrh4Days(float lat, float lon) {

		Date date = new Date();
		date = DateUtils.addDays(date, 1);
		String cDate = format.format(date);
		Date tDate = DateUtils.addDays(date, 5);
		String aDate = format.format(tDate);
		lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));

		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GridWiseCola.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.between("id.f_date", cDate, aDate));
		criteria.add(Restrictions.eq("id.lat", lat));
		criteria.add(Restrictions.eq("id.lon", lon));
		criteria.addOrder(Order.asc("id.f_date"));

		List<GridWiseCola> finalList = criteria.list();

		return finalList;

	}

	@Override
	public List<RainfallBean> rf4Days(float lat, float lon) {

		Date date = new Date();
		date = DateUtils.addDays(date, 1);
		String cDate = format.format(date);
		Date tDate = DateUtils.addDays(date, 5);
		String aDate = format.format(tDate);
		lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));

		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(GridWiseCola.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.between("id.f_date", cDate, aDate));
		criteria.add(Restrictions.eq("id.lat", lat));
		criteria.add(Restrictions.eq("id.lon", lon));
		criteria.addOrder(Order.asc("id.f_date"));
		ProjectionList plist = Projections.projectionList();

		plist.add(Projections.groupProperty("id.f_date"));
		plist.add(Projections.sum("rainfall"));
		criteria.setProjection(plist);
		List finalList = criteria.list();
		List<RainfallBean> finalBeanList = new ArrayList<>();
		for (Object obj : finalList) {
			RainfallBean bean = new RainfallBean();

			//System.out.println("/*-/*-/*-/*-/-*/*-/*-/*--*//*--*//*-*//*--*/-*//*--*//*-*//*--*/");

			Object[] arr = (Object[]) obj;
			bean.setDate(arr[0].toString());
			bean.setRainfall(Float.parseFloat(arr[1].toString()));
		//	System.out.println(bean.getDate());
		//	System.out.println(bean.getRainfall());
			finalBeanList.add(bean);
		}
		return finalBeanList;

	}

	@Override
	public List<RainfallBean> rh4Days(float lat, float lon) {
		Date date = new Date();
		date = DateUtils.addDays(date, 2);
		String cDate = format.format(date);
		Date tDate = DateUtils.addDays(date, 3);
		String aDate = format.format(tDate);
		lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));

		Session session = SessionFactory.getCurrentSession();
		//System.out.println("stats4 :-" + SessionFactory.getStatistics());
		;
		Criteria criteria = session.createCriteria(GridWiseCola.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.between("id.f_date", cDate, aDate));
		criteria.add(Restrictions.eq("id.lat", lat));
		criteria.add(Restrictions.eq("id.lon", lon));
		// criteria.add(Restrictions.eq("id.gmt", 12));
		criteria.addOrder(Order.asc("id.f_date"));
		ProjectionList plist = Projections.projectionList();

		plist.add(Projections.groupProperty("id.f_date"));
		plist.add(Projections.max("max_rh"));
		criteria.setProjection(plist);
		List finalList = criteria.list();
		List<RainfallBean> finalBeanList = new ArrayList<>();
		for (Object obj : finalList) {
			RainfallBean bean = new RainfallBean();

			//System.out.println("/*-/*-/*-/*-/-*/*-/*-/*--*//*--*//*-*//*--*/-*//*--*//*-*//*--*/");

			Object[] arr = (Object[]) obj;
			bean.setDate(arr[0].toString());
			bean.setRainfall(Float.parseFloat(arr[1].toString()));
			//System.out.println(bean.getDate());
			//System.out.println(bean.getRainfall());
			finalBeanList.add(bean);
		}
		return finalBeanList;
	}

	@Override
	public List<CropDetails> getCrop() {

		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CropDetails.class);
		criteria.add(Restrictions.eq("cropSeason", "Rabi").ignoreCase());
		return criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CropBean> getNewCrop(String phoneNo) {

		String languageId;
		try {

			languageId = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			languageId = "hi";
			e.printStackTrace();
		}

		//System.out.println(phoneNo);
		List<CropBean> beanList = new ArrayList<>();
		Session session = SessionFactory.getCurrentSession();
		// String sql="select distinct cd.cropId,cd.cropName,cd.cropHindiName
		// from userRegisteredCrop urc,cropDetails as cd where (urc.phoneNo like
		// '"+phoneNo+"' and urc.cropId!=cd.cropId) or
		// urc.phoneNo!='"+phoneNo+"'";
		String sql = "select cropId, cropName, cropHindiName from cropDetails where cropId not in (select cropId from userRegisteredCrop where phoneNo='"
				+ phoneNo + "')";
		List list = session.createSQLQuery(sql).list();
		for (Object obj : list) {
			CropBean bean = new CropBean();
			Object[] arr = (Object[]) obj;
			bean.setCropname(arr[1].toString());
			bean.setCropId(Integer.parseInt(arr[0].toString()));
			if (languageId.equals("en")) {
				bean.setCropHindiName(arr[1].toString());
			} else {
				bean.setCropHindiName(arr[2].toString());
			}

			beanList.add(bean);

		}
		return beanList;
	}

	@Override
	public void saveNews(NewsPojo news) {
		Session session = SessionFactory.getCurrentSession();
		session.saveOrUpdate(news);
	}

	@Override
	public List<UserNews> getNews(String phoneNo) {
		// int phoneNumber= 9873468844;
		String userLanguage;
		try {

			userLanguage = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			e.printStackTrace();
			userLanguage = "hi";
		}
		Session session = SessionFactory.getCurrentSession();
		/*
		 * SQLQuery qur = session.createSQLQuery(
		 * "  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1 from tehsilVillageDetails as tvd ,userRegisteredCrop  as anc,userProfile as ud ,stateDetails sd,districtDetails dd,cropDetails as cd,latestNews as lw where (ud.phoneNo like '"
		 * + phoneNo + "' and anc.phoneNo=ud.phoneNo) or (ud.phoneNo like '"
		 * +phoneNo+
		 * "' )  and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and anc.cropId=cd.cropId and (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All') and (cd.cropName like lw.cropName or lw.cropName like 'All')  order by lw.newsId desc limit 10"
		 * );
		 */
		SQLQuery qur = session.createSQLQuery(
				"  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1,lw.videoName from tehsilVillageDetails as tvd ,userProfile as ud ,stateDetails sd,districtDetails dd,latestNews as lw where ud.phoneNo like '"
						+ phoneNo
						+ "' and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and  (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All') and lw.languageId='"
						+ userLanguage + "' order by lw.newsId desc limit 20");

		List list = qur.list();
		List<UserNews> news = new ArrayList<>();

		if (list.size() > 0) {
			for (Object obj : list) {
				UserNews n = new UserNews();
				Object[] ar = (Object[]) obj;

				n.setNewsId(Integer.parseInt(ar[0].toString()));
				n.setNewsHeadLine(ar[1].toString());
				n.setNews(ar[2].toString());
				n.setNewsType(ar[3].toString());
				n.setNewsDate(ar[4].toString());
				n.setImageName1(ar[5].toString());
				n.setVideoName(ar[6].toString());
				news.add(n);
			}
		}
		//System.out.println("size of news :-" + news.size());
		return news;
	}

	@Override
	public UserNews getIncome() {
		// int phoneNumber= 9873468844;
		Session session = SessionFactory.getCurrentSession();
		/*
		 * SQLQuery qur = session.createSQLQuery(
		 * "  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1 from tehsilVillageDetails as tvd ,userRegisteredCrop  as anc,userProfile as ud ,stateDetails sd,districtDetails dd,cropDetails as cd,latestNews as lw where (ud.phoneNo like '"
		 * + phoneNo + "' and anc.phoneNo=ud.phoneNo) or (ud.phoneNo like '"
		 * +phoneNo+
		 * "' )  and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and anc.cropId=cd.cropId and (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All') and (cd.cropName like lw.cropName or lw.cropName like 'All')  order by lw.newsId desc limit 10"
		 * );
		 */
		SQLQuery qur = session.createSQLQuery(
				"select distinct newsId,headLine,news,newsType,newsDate,imageName1 from incomeAdvice order by newsId desc limit 1");

		List list = qur.list();
		UserNews news = new UserNews();
		if (list.size() > 0) {
			for (Object obj : list) {
				Object[] ar = (Object[]) obj;
				news.setNewsId(Integer.parseInt(ar[0].toString()));
				news.setNewsHeadLine(ar[1].toString());
				news.setNews(ar[2].toString());
				news.setNewsType(ar[3].toString());
				news.setNewsDate(ar[4].toString());
				news.setImageName1(ar[5].toString());
			}
		}

		return news;
	}

	@Override
	public List<UserNews> getIncomeList(int limit, String phoneNo) {
		// int phoneNumber= 9873468844;
		String languageId;
		try {

			languageId = userService.getUserLanguage(phoneNo);

		} catch (Exception e) {

			e.printStackTrace();
			languageId = "hi";
		}
		Session session = SessionFactory.getCurrentSession();
		/*
		 * SQLQuery qur = session.createSQLQuery(
		 * "  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1 from tehsilVillageDetails as tvd ,userRegisteredCrop  as anc,userProfile as ud ,stateDetails sd,districtDetails dd,cropDetails as cd,latestNews as lw where (ud.phoneNo like '"
		 * + phoneNo + "' and anc.phoneNo=ud.phoneNo) or (ud.phoneNo like '"
		 * +phoneNo+
		 * "' )  and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and anc.cropId=cd.cropId and (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All') and (cd.cropName like lw.cropName or lw.cropName like 'All')  order by lw.newsId desc limit 10"
		 * );
		 */
		SQLQuery qur = session.createSQLQuery(
				"select distinct newsId,headLine,news,newsType,newsDate,imageName1,videoName from incomeAdvice where languageId='"
						+ languageId + "' order by newsId desc limit " + limit);
		// System.out.println("query:-"+"select distinct
		// newsId,headLine,news,newsType,newsDate,imageName1 from incomeAdvice
		// where languageId='"+ languageId + "' order by newsId desc limit " +
		// limit);
		List list = qur.list();
		List<UserNews> userIncomelist = new ArrayList<>();

		for (Object obj : list) {
			UserNews news = new UserNews();
			Object[] ar = (Object[]) obj;
			news.setNewsId(Integer.parseInt(ar[0].toString()));
			news.setNewsHeadLine(ar[1].toString());
			news.setNews(ar[2].toString());
			news.setNewsType(ar[3].toString());
			news.setNewsDate(ar[4].toString());
			news.setImageName1(ar[5].toString());
			news.setVideoName(ar[6].toString());
			userIncomelist.add(news);
		}
		//System.out.println("size of income advice list " + userIncomelist.size());

		return userIncomelist;
	}

	@Override
	public List<UserNewsLogin> getNewsForHomepage(String phoneNo, String tokenNo) {

		//System.out.println("inside user homeaoge dao");
		// int phoneNumber=Integer.parseInt(phoneNo);
		Session session = SessionFactory.getCurrentSession();
		/*
		 * SQLQuery qur = session.createSQLQuery(
		 * "  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1 from tehsilVillageDetails as tvd ,userRegisteredCrop  as anc,userProfile as ud ,stateDetails sd,districtDetails dd,cropDetails as cd,latestNews as lw where (ud.phoneNo like '"
		 * + phoneNo + "' and anc.phoneNo=ud.phoneNo) or (ud.phoneNo like '"
		 * +phoneNo+
		 * "')  and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and anc.cropId=cd.cropId and (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All') and (cd.cropName like lw.cropName or lw.cropName like 'All')  order by lw.newsId desc limit 1"
		 * );
		 */
		/*
		 * SQLQuery qur = session.createSQLQuery(
		 * "  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1 from tehsilVillageDetails as tvd ,userProfile as ud ,stateDetails sd,districtDetails dd,latestNews as lw where ud.phoneNo like '"
		 * + phoneNo +
		 * "' and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and  (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All')   order by lw.newsId desc limit 1"
		 * );
		 */

		SQLQuery qur = session
				.createSQLQuery("select hpt.* from HomePageText as hpt,userLanguage as ul where ul.phoneNo=" + phoneNo
						+ " and ul.languageId=hpt.languageId order by id desc limit 1");
		List list = qur.list();
		List<UserNewsLogin> news = new ArrayList<>();

		if (list.size() > 0) {
			for (Object obj : list) {
				UserNewsLogin n = new UserNewsLogin();
				Object[] ar = (Object[]) obj;

				n.setNewsId(0);
				n.setNewsHeadLine(ar[2].toString());
				n.setNews("");
				n.setNewsType("");
				n.setNewsDate("");
				n.setImageName1("");
				// n.setVideoName("");
				news.add(n);
			}
		} else {

			UserNewsLogin n = new UserNewsLogin();
			n.setNewsId(0);
			n.setNewsHeadLine("");
			n.setNews("");
			n.setNewsType("");
			n.setNewsDate("");
			n.setImageName1("");
			news.add(n);

		}
		UserTokenRegistration reg = new UserTokenRegistration();
		reg.setPhoneNo(phoneNo);
		reg.setTokenNo(tokenNo);
		reg.setDate(new Date().toString());
		session.saveOrUpdate(reg);
		return news;
	}

	@Override
	public List getUserForNews(String Crop, String state, String district) {
		Session session = SessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(
				"select distinct ud.phoneNo,ut.tokenNo from tehsilVillageDetails as tvd ,addNewCrop  as anc,userDetails as ud ,stateDetails sd,districtDetails dd,cropDetails as cd,userTokenRegistration ut where anc.userId=ud.userId and tvd.id=anc.statonId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId  and ud.phoneNo=ut.phoneNo and cd.cropId=anc.cropId and (cd.cropName like '"
						+ Crop + "' or 'All' like '" + Crop + "') and (dd.district like '" + district
						+ "' or 'All' like '" + district + "') and (sd.state like '" + state + "' or 'All' like '"
						+ state + "')");
		return query.list();
	}

	@Override
	public String currentStageWeatherIndia(int cropId) {

		Date d1 = new Date();
		SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd");
		String dt = fm1.format(d1);
		String stage = "";
		String tempstage = "";
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Session session1 = SessionFactory.getCurrentSession();

		//System.out.println("getting stage when user not found");
		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CropCalendar.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.addOrder(Order.asc("noOfDays"));
		Date d = new Date();
		List<CropCalendar> l = criteria.list();
		String fd = "";
		String subStageHindi = "";
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d);
		int year = cal1.get(Calendar.YEAR);

		for (CropCalendar c : l) {
			if (c.getNoOfDays() == 1) {

				fd = c.getFormingDate();
			}
		}

		Date dd = null;
		fd = fd + "-" + year;
		//System.out.println(fd);
		SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
		try {
			dd = fm.parse(fd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date swd = dd;
		String cStage = "";
		String preStage = "";
		String preStageHindi = "";
		long diff = d.getTime() - swd.getTime();
		int check = (int) (diff / (24 * 60 * 60 * 1000));
		// int check=d.getDay() - swd.getDay();
		//System.out.println("Diff between days :- " + check);
		if (check >= 0) {
			for (CropCalendar cc : l) {
				//System.out.println("--------   " + swd.compareTo(d));
				//System.out.println("--------" + swd + "  ======" + d);

				if (swd.compareTo(d) == -1) {
					//System.out.println("coming in ------- ");
					preStage = cStage;

					cStage = cc.getId().getSubStage();

					subStageHindi = cc.getSubStageHindi();
					//System.out.println(cStage + "in if block" + subStageHindi);
				} else if (swd.compareTo(d) == 0) {
					preStage = cc.getId().getSubStage();
					subStageHindi = cc.getSubStageHindi();
					//System.out.println(cStage + "in else if block" + subStageHindi);
				}

				swd = DateUtils.addDays(dd, cc.getNoOfDays());

			}

		}
		cStage = preStage;

		Criteria criteria2 = session.createCriteria(CropCalendar.class);
		criteria2.add(Restrictions.eq("id.cropId", cropId));
		criteria2.add(Restrictions.eq("id.subStage", cStage));
		criteria2.setProjection(Projections.distinct(Projections.property("subStageHindi")));
		List hindiStage = criteria2.list();

		for (Object o : hindiStage) {

			subStageHindi = String.valueOf(o);
			//System.out.println("now the substage is substage is:" + subStageHindi);
		}

		String subStageHindiandEnglish = cStage + ":" + subStageHindi;
		//System.out.println("--------" + subStageHindiandEnglish);
		return subStageHindiandEnglish;
	}

	@Override
	public CropDetails cropIdOnCropName(String cropName) {
		CropDetails cd = null;
		try {
			Session session = SessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(CropDetails.class);
			criteria.add(Restrictions.eq("cropname", cropName));
			List<CropDetails> li = criteria.list();
			cd = li.get(0);

		} catch (Exception e) {
			//System.out.println("crop Id not found");
		}
		return cd;
	}

	@Override
	public List<CropBean> getUserCrops(String phoneNo) {
		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			languageId = "hi";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(phoneNo);
		List<CropBean> beanList = new ArrayList<>();
		Session session = SessionFactory.getCurrentSession();
		String sql = "select distinct cd.cropId,cd.cropName,cd.cropHindiName from userRegisteredCrop urc,cropDetails as cd where urc.phoneNo like '"
				+ phoneNo + "' and urc.cropId=cd.cropId";
		List list = session.createSQLQuery(sql).list();
		for (Object obj : list) {
			CropBean bean = new CropBean();
			Object[] arr = (Object[]) obj;
			bean.setCropname(arr[1].toString());
			bean.setCropId(Integer.parseInt(arr[0].toString()));
			if (languageId.contains("en")) {
				bean.setCropHindiName(arr[1].toString());
			} else {
				bean.setCropHindiName(arr[2].toString());
			}

			beanList.add(bean);

		}
		return beanList;

	}

	/* (non-Javadoc)
	 * @see com.bkc.dao.AdvisoryDao#getUserCrops2(java.lang.String)
	 */
	@Override
	public List<CropBean2> getUserCrops2(String phoneNo) {
		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			languageId = "hi";
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(phoneNo);
		List<CropBean2> beanList = new ArrayList<>();
		Session session = SessionFactory.getCurrentSession();
		String sql = "select distinct cd.cropId,cd.cropName,cd.cropHindiName,uc.dateOfStage from userRegisteredCrop urc,cropDetails as cd,userCropCalendar as uc  where urc.phoneNo like '"
				+ phoneNo + "' and urc.cropId=cd.cropId and uc.phoneNo='"+phoneNo+"' and urc.cropId=uc.cropId and uc.stages='Sowing'";
		List list = session.createSQLQuery(sql).list();
		
		for (Object obj : list) {
			CropBean2 bean = new CropBean2();
			Object[] arr = (Object[]) obj;
			bean.setCropname(arr[1].toString());
			bean.setCropId(Integer.parseInt(arr[0].toString()));
			bean.setSowingDate(String.valueOf(arr[3]));
			
			if (languageId.contains("en")) {
				bean.setCropHindiName(arr[1].toString());
			} else {
				bean.setCropHindiName(arr[2].toString());
			}

			beanList.add(bean);

		}
		return beanList;
	}

}
