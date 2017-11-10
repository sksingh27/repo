package com.bkc.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.CropCategoryBean;
import com.bkc.bean.ForecastBean;
import com.bkc.bean.MandiBean;
import com.bkc.bean.MandiCategoryBean;
import com.bkc.bean.Forecast.StateDistrictBean;
import com.bkc.model.CropCategoryDetails;
import com.bkc.model.CropDetails;
import com.bkc.model.GridWiseCola;
import com.bkc.model.webkc.MandiPriceLatLon;
import com.bkc.service.UserDetailsService;
import com.bkc.staticclasses.GetRoundedLatLon;


@Repository("forecastDao")
@Transactional
@PropertySource(value = { "file:/home/dmdd/Desktop/fasalsalah/property/mandi.properties" })
public class ForecastDaoImpl implements ForecastDao {

	@Autowired
	Environment environment;

	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory SessionFactory;

	@Autowired
	@Qualifier("webkcSessionFactory")
	SessionFactory webkcSessionFactory;

	@Autowired
	UserDetailsService userService;

	@Override
	public List<ForecastBean> getForecast(float lat, float lon, int villageId, int noOfDays) {

		List<ForecastBean> returnList = new ArrayList<ForecastBean>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currentdate = new Date();

		String d1 = format.format(currentdate);
		String d2 = format.format(DateUtils.addDays(currentdate, noOfDays));

		lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));
		String forecastHQL="select max(max_temp) ,min(min_temp),max(max_rh),min(max_rh),round(avg(wind_speed),2),sum(rainfall),id.f_date from GridWiseCola  where max_temp <> 999.90 and min_temp  <> 999.90 and max_rh <> 999.90 and id.lat='"+lat+"' and id.lon='"+lon+"' and id.f_date between '"+d1+"' and '"+d2+"' group by id.f_date order by id.f_date";
	//	System.out.println(forecastHQL);
		Session session = SessionFactory.getCurrentSession();
		List tempList=session.createQuery(forecastHQL).list();
		SQLQuery query = session.createSQLQuery(
				" select  td.tehsil,td.village,dd.district,sd.state from tehsilVillageDetails as td , districtDetails as dd,stateDetails as sd where td.id="
						+ villageId
						+ " and td.districtId=dd.districtId and td.stateId=sd.stateId and dd.stateId=sd.stateId");

		Object[] locationList = (Object[])query.uniqueResult();
		
		for(Object obj:tempList){
			Object[] arr= (Object[]) obj;
			ForecastBean bean = new ForecastBean();
			//System.out.println(arr[6].toString());
			bean.setMax_temp(Float.parseFloat(arr[0].toString()));
			bean.setMin_temp(Float.parseFloat(arr[1].toString()));
			bean.setMax_rh(Float.parseFloat(arr[2].toString()));
			bean.setMin_rh(Float.parseFloat(arr[3].toString()));
			bean.setWind_speed(Float.parseFloat(arr[4].toString()));
            bean.setRainfall(Float.parseFloat(arr[5].toString()));
            bean.setCity(locationList[1].toString());
            bean.setState(locationList[2].toString());
            returnList.add(bean);
			
		}
		/*try {
			//Translate.setKey("trnsl.1.1.20160618T083703Z.0cb72772b74876d9.afe8e9791d153dfd9a27a05222d1145554e399eb");
			Date currentdate = new Date();

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String d1 = format.format(currentdate);
			String d2 = format.format(DateUtils.addDays(currentdate, noOfDays));
			System.out.println("before rounding ----------->" + lat + " : " + lon);

			lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
			lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));
			System.out.println("after rounding ----------->" + lat + " : " + lon);
			System.out.println(d1 + "   &&&& " + d2);

			Session session = SessionFactory.getCurrentSession();
			Criteria minTempCri = session.createCriteria(GridWiseCola.class);
			minTempCri.add(Restrictio4.25ns.between("id.f_date", d1, d2));
			minTempCri.add(Restrictions.eq("id.lat", lat));
			minTempCri.add(Restrictions.eq("id.lon", lon));
			minTempCri.add(Restrictions.ne("min_temp", -999.9f));
			minTempCri.setCacheable(true);
			// minTempCri.add(Restrictions.eq("id.gmt", 3));
			minTempCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.min("min_temp")));

			List minTempDb = minTempCri.list();
			List<Float> minTemp = new ArrayList<Float>();
			Iterator itr = minTempDb.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				System.out.println(obj[0].toString());
				minTemp.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria maxTempCri = session.createCriteria(GridWiseCola.class);
			maxTempCri.add(Restrictions.between("id.f_date", d1, d2));
			maxTempCri.add(Restrictions.eq("id.lat", lat));
			maxTempCri.add(Restrictions.eq("id.lon", lon));
			maxTempCri.add(Restrictions.ne("max_temp", 999.9f));
			maxTempCri.setCacheable(true);
			// maxTempCri.add(Restrictions.eq("id.gmt", 12));
			maxTempCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.max("max_temp")));

			List maxTempDb = maxTempCri.list();
			List<Float> maxTemp = new ArrayList<Float>();
			Iterator itr1 = maxTempDb.iterator();
			while (itr1.hasNext()) {
				Object[] obj = (Object[]) itr1.next();
				System.out.println(obj[0].toString());
				maxTemp.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria maxRHCri = session.createCriteria(GridWiseCola.class);
			maxRHCri.add(Restrictions.between("id.f_date", d1, d2));
			maxRHCri.add(Restrictions.eq("id.lat", lat));
			maxRHCri.add(Restrictions.eq("id.lon", lon));
			maxRHCri.add(Restrictions.le("max_rh", 99f));
			maxRHCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.max("max_rh")));
			maxRHCri.setCacheable(true);

			List maxRhDb = maxRHCri.list();

			List<Float> maxRh = new ArrayList<Float>();
			Iterator itrRh = maxRhDb.iterator();
			while (itrRh.hasNext()) {
				Object[] obj = (Object[]) itrRh.next();
				System.out.println(obj[0].toString());
				maxRh.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria minRHCri = session.createCriteria(GridWiseCola.class);
			minRHCri.add(Restrictions.between("id.f_date", d1, d2));
			minRHCri.add(Restrictions.eq("id.lat", lat));
			minRHCri.add(Restrictions.eq("id.lon", lon));
			minRHCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.min("max_rh")));
			minRHCri.setCacheable(true);
			List minRhDb = minRHCri.list();

			List<Float> minRh = new ArrayList<Float>();
			Iterator itrRh2 = minRhDb.iterator();
			while (itrRh2.hasNext()) {
				Object[] obj = (Object[]) itrRh2.next();
				System.out.println(obj[0].toString());
				minRh.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria windCri = session.createCriteria(GridWiseCola.class);
			windCri.add(Restrictions.between("id.f_date", d1, d2));
			windCri.add(Restrictions.eq("id.lat", lat));
			windCri.add(Restrictions.eq("id.lon", lon));
			windCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.avg("wind_speed")));
			windCri.setCacheable(true);
			List windDb = windCri.list();

			List<Float> wind = new ArrayList<Float>();
			Iterator itrwind = windDb.iterator();
			while (itrwind.hasNext()) {
				Object[] obj = (Object[]) itrwind.next();
				System.out.println(obj[0].toString());
				wind.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria rainfallCri = session.createCriteria(GridWiseCola.class);
			rainfallCri.add(Restrictions.between("id.f_date", d1, d2));
			rainfallCri.add(Restrictions.eq("id.lat", lat));
			rainfallCri.add(Restrictions.eq("id.lon", lon));
			rainfallCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.sum("rainfall")));
			rainfallCri.setCacheable(true);
			List rainfallDb = rainfallCri.list();

			List<Float> rainfall = new ArrayList<Float>();
			Iterator itrrain = rainfallDb.iterator();
			while (itrrain.hasNext()) {
				Object[] obj = (Object[]) itrrain.next();
				System.out.println(obj[0].toString());
				rainfall.add(Float.parseFloat(obj[1].toString()));
			}

			SQLQuery query = session.createSQLQuery(
					" select  td.tehsil,td.village,dd.district,sd.state from tehsilVillageDetails as td , districtDetails as dd,stateDetails as sd where td.id="
							+ villageId
							+ " and td.districtId=dd.districtId and td.stateId=sd.stateId and dd.stateId=sd.stateId");

			List locationList = query.list();
			Object[] array = (Object[]) locationList.get(0);
			System.out.println("size of location lst " + locationList.size());
			for (int i = 0; i < minTemp.size(); i++) {
				System.out.println("size of list :- " + minTemp.size());
				ForecastBean forecastbean = new ForecastBean();
				forecastbean.setCity(array[1].toString());
				forecastbean.setState(array[3].toString());
				forecastbean.setMax_rh(maxRh.get(i));
				forecastbean.setMax_temp(maxTemp.get(i));
				forecastbean.setMin_rh(minRh.get(i));
				forecastbean.setMin_temp(minTemp.get(i));
				forecastbean.setRainfall(rainfall.get(i));
				forecastbean.setWind_speed(3.6f * wind.get(i));
				System.out.println("city :- " + array[1].toString());
				System.out.println("State :- " + array[3].toString());
				System.out.println("Max Rh :- " + maxRh.get(i));
				System.out.println("Min Rh :- " + minRh.get(i));
				System.out.println("Max Temp :- " + maxTemp.get(i));
				System.out.println("Min Temp :- " + minTemp.get(i));
				System.out.println("Wind :- " + wind.get(i));
				System.out.println("Rainfall :- " + rainfall.get(i));
				returnList.add(forecastbean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
*/
		return returnList;
	}

	private Float getLatLonPoint(Float point) {
		// Float pts = Float.valueOf(point.trim()).floatValue();
		Integer intPt = point.intValue();

		String point1 = String.valueOf(point);
		String point2 = point1.substring(point1.indexOf('.') + 1);
		Float aa = Float.parseFloat(point2);
		if (aa >= 0.00f && aa < 30.00f)
			point = intPt + 0.00f;
		else if (aa >= 30.00f && aa < 75.00f)
			point = intPt + 0.50f;
		else if (aa > 75.00f)
			point = intPt + 1.00f;

		return point;
	}

	/**
	 * add days to date in java
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	@Override
	public List<MandiBean> getMandi(float lat, float lon, String cropName, String phoneNo,String state) {
		List<MandiBean> mandiList = new ArrayList<>();
		try {

			// Translate.setKey("trnsl.1.1.20160618T083703Z.0cb72772b74876d9.afe8e9791d153dfd9a27a05222d1145554e399eb");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			Date newDate = addDays(d, -2);
			//System.out.println("Mandi Date In Dao :- " + sdf.format(newDate) + " ****** " + sdf.format(d));
			Session session = webkcSessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(MandiPriceLatLon.class);
			criteria.setCacheable(true);
			criteria.add(Restrictions.eq("crop", getCropName(cropName)));
			criteria.add(Restrictions.between("lat", lat - 1.0f, lat + 1.0f));
			criteria.add(Restrictions.between("lon", lon - 1.0f, lon + 1.0f));
			criteria.add(Restrictions.between("dateOfDownload", newDate, d));
			//criteria.add(Restrictions.eq("state", state));
			criteria.addOrder(Order.desc("dateOfDownload"));
			/* criteria.addOrder(Order.asc("mandi")); */

			/* criteria.addOrder(Order.desc("arrival")); */

			List<MandiPriceLatLon> toSend = criteria.list();

			for (MandiPriceLatLon mpll : toSend) {
				MandiBean mb = new MandiBean();
				mb.setDate(mpll.getDateOfDownload().toString());
				mb.setArrival(mpll.getArrival());
				mb.setMandi(mpll.getMandi());
				mb.setPrice(mpll.getModalPrice());
				mb.setState(mpll.getState());
				mb.setCrop(mpll.getVariety());
				mandiList.add(mb);
			}

			/*Object stateObj = session.createSQLQuery(
					"select distinct sd.state from agroMgnt.stateDetails as sd,agroMgnt.tehsilVillageDetails as tvd,webkc.mandiPriceLatLon as mp,agroMgnt.userProfile as up  where up.phoneNo='"
							+ phoneNo + "' and up.stationId=tvd.id and tvd.stateId=sd.stateId")
					.list().get(0);*/
			//String state = stateObj.toString();
			if (mandiList.size() == 0) {
			//	System.out.println("inside when mandi list is zero");
				Criteria criteria1 = session.createCriteria(MandiPriceLatLon.class);
				criteria1.setCacheable(true);
				criteria1.add(Restrictions.eq("crop", getCropName(cropName)));
				criteria1.add(Restrictions.eq("state", state));
				criteria1.add(Restrictions.between("dateOfDownload", newDate, d));
				criteria1.addOrder(Order.desc("dateOfDownload"));
				toSend = criteria.list();
				for (MandiPriceLatLon mpll : toSend) {
					MandiBean mb = new MandiBean();
					mb.setDate(mpll.getDateOfDownload().toString());
					mb.setArrival(mpll.getArrival());
					mb.setMandi(mpll.getMandi());
					mb.setPrice(mpll.getModalPrice());
					mb.setState(mpll.getState());
					mb.setCrop(mpll.getVariety());
					mandiList.add(mb);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mandiList;

	}

	public String getCropName(String cropname) {

		try {
			if (!environment.getProperty(cropname).isEmpty()) {
				cropname = environment.getProperty(cropname);
				environment.getProperty(cropname);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.err.println("Either file not found or crop not found in file");

		}

		//System.out.println("crop name in mandi from properties file :-" + cropname);
		return cropname;
	}

	@Override
	public CropCategoryBean getMandiCrops(String phoneNo) {
		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			languageId = "hi";
			e.printStackTrace();
		}

		List<MandiCategoryBean> crops = new ArrayList<>();

		Session sessionAgro = SessionFactory.getCurrentSession();
		String sql = "select cd.cropName,cd.cropHindiName from cropDetails as cd,userRegisteredCrop as urc where urc.phoneNo='"
				+ phoneNo + "' and urc.cropId=cd.cropId order by cd.cropName";
		List cList = sessionAgro.createSQLQuery(sql).list();
		CropCategoryBean bean = new CropCategoryBean();
		List<MandiCategoryBean> userCrops = new ArrayList<>();
		if (cList.size() > 0) {
			for (Object obj : cList) {
				Object[] arr = (Object[]) obj;
				MandiCategoryBean b1 = new MandiCategoryBean();
				if (languageId.contains("en")) {
					b1.setEnglishName(arr[0].toString());
					b1.setHindiName(arr[0].toString());
				} else {
					b1.setEnglishName(arr[0].toString());
					b1.setHindiName(arr[1].toString());
				}

				userCrops.add(b1);
			}
		}

		bean.setUserRegistered(userCrops);
		bean.setCereals(beanList("cereals", languageId));
		bean.setFruits(beanList("fruits", languageId));
		bean.setPulses(beanList("pulses", languageId));
		bean.setOil(beanList("Oil Seeds", languageId));
		bean.setOthers(beanList("others", languageId));
		bean.setSpices(beanList("spices", languageId));
		bean.setVegetables(beanList("vegetables", languageId));
		return bean;
	}

	public List<MandiCategoryBean> beanList(String category, String languageId) {

		List<MandiCategoryBean> toReturn = new ArrayList<>();
		Session session = SessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CropCategoryDetails.class);
		criteria.addOrder(Order.asc("cropName"));
		criteria.add(Restrictions.eq("categoryName", category));
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("cropName"));
		pList.add(Projections.property("cropHindiName"));
		criteria.setProjection(pList);
		List toConvert = criteria.list();
		for (Object obj : toConvert) {
			Object arr[] = (Object[]) obj;
			MandiCategoryBean bean = new MandiCategoryBean();
			if (languageId.equals("en")) {
				bean.setEnglishName(arr[0].toString());
				bean.setHindiName(arr[0].toString());
			} else {
				bean.setEnglishName(arr[0].toString());
				bean.setHindiName(arr[1].toString());
			}

			toReturn.add(bean);
		}
		return toReturn;
	}

	@Override
	public List<StateDistrictBean> getStates() {

		Session session = SessionFactory.getCurrentSession();

		List objectList = session.createSQLQuery("select distinct stateId,state from stateDetails order by state")
				.list();
		List<StateDistrictBean> beanList = new ArrayList<>();
		for (Object obj : objectList) {
			StateDistrictBean bean = new StateDistrictBean();
			Object[] arr = (Object[]) obj;
			bean.setId(Integer.parseInt(arr[0].toString()));
			bean.setStateDistrict(arr[1].toString());
			beanList.add(bean);

		}

		return beanList;
	}

	@Override
	public List<StateDistrictBean> getDistrict(int stateId) {
		Session session = SessionFactory.getCurrentSession();
		List objectList = session
				.createSQLQuery("select distinct districtId,district from districtDetails where stateId=" + stateId
						+ " order by district")
				.list();
		List<StateDistrictBean> beanList = new ArrayList<>();
		for (Object obj : objectList) {
			StateDistrictBean bean = new StateDistrictBean();
			Object[] arr = (Object[]) obj;
			bean.setId(Integer.parseInt(arr[0].toString()));
			bean.setStateDistrict(arr[1].toString());
			beanList.add(bean);
		}

		return beanList;
	}

	@Override
	public List<ForecastBean> getDistrictForecast(int districtId, int stateId, String district) {
		Session sessionTemp = SessionFactory.getCurrentSession();

		String sql = "select lat,lon,id from tehsilVillageDetails where districtId=" + districtId + " and stateId="
				+ stateId + " and tehsil='" + district + "'";
		float lat = 0.0f, lon = 0.0f;
		int villageId = 0;
		List latLon = sessionTemp.createSQLQuery(sql).list();
		for (Object obj : latLon) {
			Object[] arr = (Object[]) obj;
			lat = Float.parseFloat(arr[0].toString());
			lon = Float.parseFloat(arr[1].toString());
			villageId = Integer.parseInt(arr[2].toString());

		}
  
		List<ForecastBean> returnList = new ArrayList<ForecastBean>();
		try {
			//Translate.setKey("trnsl.1.1.20160618T083703Z.0cb72772b74876d9.afe8e9791d153dfd9a27a05222d1145554e399eb");
			Date currentdate = new Date();

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String d1 = format.format(currentdate);
			String d2 = format.format(DateUtils.addDays(currentdate, 6));
			//System.out.println("before rounding ----------->" + lat + " : " + lon);

			lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
			lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));
			//System.out.println("after rounding ----------->" + lat + " : " + lon);
			//System.out.println(d1 + "   &&&& " + d2);

			Session session = SessionFactory.getCurrentSession();
			Criteria minTempCri = session.createCriteria(GridWiseCola.class);
			minTempCri.add(Restrictions.between("id.f_date", d1, d2));
			minTempCri.add(Restrictions.eq("id.lat", lat));
			minTempCri.add(Restrictions.eq("id.lon", lon));
			minTempCri.add(Restrictions.ne("min_temp", -999.9f));
			minTempCri.setCacheable(true);
			// minTempCri.add(Restrictions.eq("id.gmt", 3));
			minTempCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.min("min_temp")));

			List minTempDb = minTempCri.list();
			List<Float> minTemp = new ArrayList<Float>();
			Iterator itr = minTempDb.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				//System.out.println(obj[0].toString());
				minTemp.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria maxTempCri = session.createCriteria(GridWiseCola.class);
			maxTempCri.add(Restrictions.between("id.f_date", d1, d2));
			maxTempCri.add(Restrictions.eq("id.lat", lat));
			maxTempCri.add(Restrictions.eq("id.lon", lon));
			maxTempCri.add(Restrictions.ne("max_temp", 999.9f));
			maxTempCri.setCacheable(true);
			// maxTempCri.add(Restrictions.eq("id.gmt", 12));
			maxTempCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.max("max_temp")));

			List maxTempDb = maxTempCri.list();
			List<Float> maxTemp = new ArrayList<Float>();
			Iterator itr1 = maxTempDb.iterator();
			while (itr1.hasNext()) {
				Object[] obj = (Object[]) itr1.next();
				//System.out.println(obj[0].toString());
				maxTemp.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria maxRHCri = session.createCriteria(GridWiseCola.class);
			maxRHCri.add(Restrictions.between("id.f_date", d1, d2));
			maxRHCri.add(Restrictions.eq("id.lat", lat));
			maxRHCri.add(Restrictions.eq("id.lon", lon));
			maxRHCri.add(Restrictions.le("max_rh", 99f));
			maxRHCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.max("max_rh")));
			maxRHCri.setCacheable(true);

			List maxRhDb = maxRHCri.list();

			List<Float> maxRh = new ArrayList<Float>();
			Iterator itrRh = maxRhDb.iterator();
			while (itrRh.hasNext()) {
				Object[] obj = (Object[]) itrRh.next();
			//	System.out.println(obj[0].toString());
				maxRh.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria minRHCri = session.createCriteria(GridWiseCola.class);
			minRHCri.add(Restrictions.between("id.f_date", d1, d2));
			minRHCri.add(Restrictions.eq("id.lat", lat));
			minRHCri.add(Restrictions.eq("id.lon", lon));
			minRHCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.min("max_rh")));
			minRHCri.setCacheable(true);
			List minRhDb = minRHCri.list();

			List<Float> minRh = new ArrayList<Float>();
			Iterator itrRh2 = minRhDb.iterator();
			while (itrRh2.hasNext()) {
				Object[] obj = (Object[]) itrRh2.next();
				//System.out.println(obj[0].toString());
				minRh.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria windCri = session.createCriteria(GridWiseCola.class);
			windCri.add(Restrictions.between("id.f_date", d1, d2));
			windCri.add(Restrictions.eq("id.lat", lat));
			windCri.add(Restrictions.eq("id.lon", lon));
			windCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.avg("wind_speed")));
			windCri.setCacheable(true);
			List windDb = windCri.list();

			List<Float> wind = new ArrayList<Float>();
			Iterator itrwind = windDb.iterator();
			while (itrwind.hasNext()) {
				Object[] obj = (Object[]) itrwind.next();
				//System.out.println(obj[0].toString());
				wind.add(Float.parseFloat(obj[1].toString()));
			}

			Criteria rainfallCri = session.createCriteria(GridWiseCola.class);
			rainfallCri.add(Restrictions.between("id.f_date", d1, d2));
			rainfallCri.add(Restrictions.eq("id.lat", lat));
			rainfallCri.add(Restrictions.eq("id.lon", lon));
			rainfallCri.setProjection(Projections.projectionList().add(Projections.groupProperty("id.f_date"))
					.add(Projections.sum("rainfall")));
			rainfallCri.setCacheable(true);
			List rainfallDb = rainfallCri.list();

			List<Float> rainfall = new ArrayList<Float>();
			Iterator itrrain = rainfallDb.iterator();
			while (itrrain.hasNext()) {
				Object[] obj = (Object[]) itrrain.next();
				//System.out.println(obj[0].toString());
				rainfall.add(Float.parseFloat(obj[1].toString()));
			}

			SQLQuery query = session.createSQLQuery(
					" select  td.tehsil,td.village,dd.district,sd.state from tehsilVillageDetails as td , districtDetails as dd,stateDetails as sd where td.id="+ villageId
							+ " and td.districtId=dd.districtId and td.stateId=sd.stateId and dd.stateId=sd.stateId");

			List locationList = query.list();
			Object[] array = (Object[]) locationList.get(0);
			//System.out.println("size of location lst " + locationList.size());
			for (int i = 0; i < minTemp.size(); i++) {
				//System.out.println("size of list :- " + minTemp.size());
				ForecastBean forecastbean = new ForecastBean();
				forecastbean.setCity(array[2].toString());
				forecastbean.setState(array[3].toString());
				forecastbean.setMax_rh(maxRh.get(i));
				forecastbean.setMax_temp(maxTemp.get(i));
				forecastbean.setMin_rh(minRh.get(i));
				forecastbean.setMin_temp(minTemp.get(i));
				forecastbean.setRainfall(rainfall.get(i));
				forecastbean.setWind_speed(3.6f * wind.get(i));
				//System.out.println("city :- " + array[1].toString());
				//System.out.println("State :- " + array[3].toString());
				//System.out.println("Max Rh :- " + maxRh.get(i));
				//System.out.println("Min Rh :- " + minRh.get(i));
				//System.out.println("Max Temp :- " + maxTemp.get(i));
				//System.out.println("Min Temp :- " + minTemp.get(i));
				//System.out.println("Wind :- " + wind.get(i));
				//System.out.println("Rainfall :- " + rainfall.get(i));
				returnList.add(forecastbean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		
		
		return returnList;
	}
}
