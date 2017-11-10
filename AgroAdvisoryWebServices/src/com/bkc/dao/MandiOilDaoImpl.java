package com.bkc.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.bkc.bean.MandiBean;
import com.bkc.bean.MandiChartBean;
import com.bkc.bean.MandiOilBean;
import com.bkc.bean.MandiRateBean;
import com.bkc.bean.NcdexBean;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.model.CropProduct;
import com.bkc.model.MandiDetails;
import com.bkc.model.NCDEXnHapur;
import com.bkc.model.UserDetails;
import com.bkc.model.UserProfile;
import com.bkc.model.webkc.MandiPriceLatLon;
import com.bkc.service.UserDetailsService;

@Repository
@Transactional
@PropertySource(value = { "file:/home/dmdd/Desktop/fasalsalah/property/mandi.properties" })
public class MandiOilDaoImpl implements MandiOilDao {
	@Autowired
	@Qualifier("mandiOilSessionFactory")
	SessionFactory factory;

	
	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory sessionFactory;

	@Autowired
	@Qualifier("webkcSessionFactory")
	SessionFactory webkcSessionfactory;
	@Autowired
	Environment environment;

	@Autowired
	UserDetailsService userService;
	
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public MandiRateBean getmandiOilDetails(String cropname) {

		/*
		 * cropname="Mustard";
		 * 
		 * MandiRateBean finalBean = new MandiRateBean(); Session session =
		 * factory.getCurrentSession();
		 * 
		 * DetachedCriteria innerCriteria =
		 * DetachedCriteria.forClass(Seed.class, "inner")
		 * .setProjection(Projections.projectionList().add(Projections.max(
		 * "inner.id.date"))); Criteria crit =
		 * session.createCriteria(Seed.class, "outer");
		 * crit.add(Subqueries.propertyEq("outer.id.date", innerCriteria));
		 * crit.add(Restrictions.eq("crop", cropname)); List<Seed> list =
		 * crit.list(); List<MandiOilBean> seedBeanList = new ArrayList<>(); for
		 * (Seed temp : list) { MandiOilBean bean = new MandiOilBean();
		 * bean.setCity(temp.getId().getCity()); bean.setDate(temp.getXlDate());
		 * bean.setValue(temp.getValue());
		 * 
		 * seedBeanList.add(bean);
		 * 
		 * } finalBean.setMandiOilBeanList(seedBeanList);
		 * 
		 * DetachedCriteria arrivalInnerCriteria =
		 * DetachedCriteria.forClass(Arrival.class, "inner")
		 * .setProjection(Projections.projectionList().add(Projections.max(
		 * "inner.id.date"))); Criteria arrivalCriteria =
		 * session.createCriteria(Arrival.class, "outer");
		 * arrivalCriteria.add(Subqueries.propertyEq("outer.id.date",
		 * arrivalInnerCriteria));
		 * arrivalCriteria.add(Restrictions.eq("id.crop",
		 * cropname).ignoreCase()); List<Arrival> arrivalList =
		 * arrivalCriteria.list(); ArrivalBean bean = new ArrivalBean(); for
		 * (Arrival temp : arrivalList) {
		 * 
		 * bean.setCity(temp.getId().getCity());
		 * bean.setValue(temp.getArrival()); } finalBean.setArrivalBean(bean);
		 * 
		 * DetachedCriteria ncdexInnerCriteria =
		 * DetachedCriteria.forClass(NCDEXnHapur.class, "inner")
		 * .setProjection(Projections.projectionList().add(Projections.max(
		 * "inner.id.date"))); Criteria ncdexCriteria =
		 * session.createCriteria(NCDEXnHapur.class, "outer");
		 * ncdexCriteria.add(Subqueries.propertyEq("outer.id.date",
		 * ncdexInnerCriteria)); ncdexCriteria.add(Restrictions.eq("id.crop",
		 * cropname).ignoreCase()); List<NCDEXnHapur> ncdexList =
		 * ncdexCriteria.list(); List<NcdexBean> ncdexBeanList = new
		 * ArrayList<>(); for (NCDEXnHapur temp : ncdexList) { NcdexBean
		 * tempNcdexBean= new NcdexBean();
		 * tempNcdexBean.setMonth(temp.getId().getMonth());
		 * tempNcdexBean.setSource(temp.getId().getSource());
		 * tempNcdexBean.setChange(temp.getChange());
		 * tempNcdexBean.setValue(temp.getValue());
		 * ncdexBeanList.add(tempNcdexBean);
		 * 
		 * } finalBean.setNcdexBeanList(ncdexBeanList);
		 * 
		 * 
		 * DetachedCriteria cropPorductInnerCriteria =
		 * DetachedCriteria.forClass(CropProduct.class, "inner")
		 * .setProjection(Projections.projectionList().add(Projections.max(
		 * "inner.id.date"))); Criteria cropProductCriteria =
		 * session.createCriteria(CropProduct.class, "outer");
		 * cropProductCriteria.add(Subqueries.propertyEq("outer.id.date",
		 * cropPorductInnerCriteria));
		 * cropProductCriteria.add(Restrictions.eq("id.crop",
		 * cropname).ignoreCase()); List<CropProduct> cropProductList =
		 * cropProductCriteria.list(); List<MandiOilBean> cropProductBeanList =
		 * new ArrayList<>(); for (CropProduct temp : cropProductList) {
		 * MandiOilBean cpBean = new MandiOilBean();
		 * cpBean.setCity(temp.getId().getCity());
		 * cpBean.setDate(temp.getXlDate()); cpBean.setValue(temp.getValue());
		 * cropProductBeanList.add(cpBean);
		 * 
		 * } finalBean.setCropProductBeanList(cropProductBeanList);
		 * 
		 * DetachedCriteria oilInnerCriteria =
		 * DetachedCriteria.forClass(Oil.class, "inner")
		 * .setProjection(Projections.projectionList().add(Projections.max(
		 * "inner.id.date"))); Criteria oilCriteria =
		 * session.createCriteria(Oil.class, "outer");
		 * oilCriteria.add(Subqueries.propertyEq("outer.id.date",
		 * oilInnerCriteria)); oilCriteria.add(Restrictions.eq("id.crop",
		 * cropname).ignoreCase()); List<Oil> oilList = oilCriteria.list();
		 * List<MandiOilBean> oilBeanList = new ArrayList<>(); for (Oil temp :
		 * oilList) { MandiOilBean cpBean = new MandiOilBean();
		 * cpBean.setCity(temp.getId().getCity());
		 * cpBean.setDate(temp.getXlDate()); cpBean.setValue(temp.getValue());
		 * oilBeanList.add(cpBean);
		 * 
		 * } finalBean.setOilBeanList(oilBeanList);
		 */

		return null;
	}

	@Override
	public MandiRateBean getmandiOilDetailsCropSpecific(String cropname, float lat, float lon) {

		MandiRateBean finalBean = new MandiRateBean();
		Session session = factory.getCurrentSession();
	//	System.out.println("lat lon in dao :-" + lat + " : " + lon);
		Criteria mandiDetailsCriteria = session.createCriteria(MandiDetails.class);
		mandiDetailsCriteria.add(Restrictions.between("lat", lat - 1.5f, lat + 1.5f));
		mandiDetailsCriteria.add(Restrictions.between("lon", lon - 1.5f, lon + 1.5f));
		mandiDetailsCriteria.setProjection(Projections.property("mandi"));
		List nearMandiList = mandiDetailsCriteria.list();
		List<String> listOfMandiString = new ArrayList<>();
		List<MandiPriceLatLon> finalPramukhMandiList = new ArrayList<MandiPriceLatLon>();
		List<MandiOilBean> tempParamukhMandi= new ArrayList<>();
		String cityNamesInQuery="";
		
		for (Object obj : nearMandiList) {
			cityNamesInQuery=cityNamesInQuery+"'"+obj.toString()+"',";
			
			listOfMandiString.add(obj.toString().trim());
		}
		if(cityNamesInQuery.length()>0){
           cityNamesInQuery=cityNamesInQuery.substring(0,cityNamesInQuery.length()-1);
		}
	//	System.out.println(cityNamesInQuery);
			Session session2 = sessionFactory.getCurrentSession();
			Criteria criteria = session2.createCriteria(MandiPriceLatLon.class);
			criteria.add(Restrictions.eq("crop", getCropName(cropname)).ignoreCase());

		
			criteria.addOrder(Order.desc("dateOfDownload"));
			criteria.addOrder(Order.desc("arrival"));
			if(cropname.contains("Paddy")){
				criteria.setMaxResults(10);
			}
			else{
				criteria.setMaxResults(4);	
			}
			
		
			finalPramukhMandiList = criteria.list();
		//	System.out.println(finalPramukhMandiList.size() + "----------------------------->>>>>");
			for (MandiPriceLatLon temp : finalPramukhMandiList) {
				//System.out.println("mandi is " + temp.getMandi());
				//System.out.println("date is " + temp.getDateOfDownload());
				//System.out.println("value is " + temp.getModalPrice());
			}
			
			
			 for(MandiPriceLatLon temp:finalPramukhMandiList){
				MandiOilBean bean= new MandiOilBean();
				bean.setCity(temp.getMandi());
				bean.setDate(temp.getDateOfDownload().toString());
				bean.setValue(temp.getModalPrice().toString());
				bean.setCrop(temp.getCrop());
			    tempParamukhMandi.add(bean);
			   }
		

		DetachedCriteria ncdexInnerCriteria = DetachedCriteria.forClass(NCDEXnHapur.class, "inner")
				.setProjection(Projections.projectionList().add(Projections.max("inner.id.date")));
		Criteria ncdexCriteria = session.createCriteria(NCDEXnHapur.class, "outer");
		ncdexCriteria.add(Subqueries.propertyEq("outer.id.date", ncdexInnerCriteria));
		ncdexCriteria.add(Restrictions.eq("id.crop", cropname).ignoreCase());
		// ncdexCriteria.add(Restrictions.in("mandi", listOfMandiString));
		List<NCDEXnHapur> ncdexList = ncdexCriteria.list();
		List<NcdexBean> ncdexBeanList = new ArrayList<>();
		for (NCDEXnHapur temp : ncdexList) {
		//	System.out.println("----------------------->"+temp.getChange()+" crop "+temp.getId().getCrop()+" :"+temp.getId().getSource()+" "+temp.getValue());
			NcdexBean tempNcdexBean = new NcdexBean();
			tempNcdexBean.setMonth(temp.getId().getMonth());
			tempNcdexBean.setSource(temp.getId().getSource());
			tempNcdexBean.setChange(temp.getChange());
			tempNcdexBean.setValue(temp.getValue());
			ncdexBeanList.add(tempNcdexBean);

		}
		finalBean.setNcdexBeanList(ncdexBeanList);


        String queryString="select city,xlDate,value,productName from cropProduct where crop regexp '"+cropname+"' and date=(select max(date) from cropProduct where crop='"+cropname+"') and city in ("+cityNamesInQuery+")";  		
      //  System.out.println(queryString);
        List cropProductList= new ArrayList<>();
        	try{
        		cropProductList=session.createSQLQuery(queryString).list();		
        	}
        catch(Exception e){
        	System.err.println("when eror in mand detail in mandioildetails query");
        }
	
		//System.out.println("crop name inside crop product --------------> :-" + cropname);
		List<MandiOilBean> cropProductBeanList = new ArrayList<>();
		for (Object temp : cropProductList) {
			MandiOilBean cpBean = new MandiOilBean();
			Object[] arr=(Object[]) temp;
			cpBean.setCity(arr[0].toString());
			cpBean.setDate(arr[1].toString());
			cpBean.setValue(arr[2].toString());
			cpBean.setCrop(arr[3].toString());
			cropProductBeanList.add(cpBean);
			tempParamukhMandi.add(cpBean);
			//System.out.println(arr[0]);
			//System.out.println(arr[1]);
			//System.out.println(arr[2]);
			
			// seedBeanList.add(cpBean);

		}
		
		String[] listOfMandiFromPropertiesFile=getListOfPramukhMandisFromAgmark(cropname);
		String pramukCities="";
		for(String temp:listOfMandiFromPropertiesFile){
			pramukCities=pramukCities+",'"+temp.split(":")[0]+"'";
			
		}
		pramukCities="' '"+pramukCities;
		String pramukhQuery="Select city,date,value,productname from cropProduct where date between '"+yyyy_mm_dd.format(DateUtils.addDays(new Date(), -2))+"' and '"+yyyy_mm_dd.format(new Date())+ "' and city in("+pramukCities+") and crop regexp '"+cropname+"'";
      try{
		List pramukhListFromCropProduct =factory.getCurrentSession().createSQLQuery(pramukhQuery).list();
        for (Object temp : pramukhListFromCropProduct) {
			MandiOilBean cpBean = new MandiOilBean();
			Object[] arr=(Object[]) temp;
			cpBean.setCity(arr[0].toString());
			cpBean.setDate(arr[1].toString());
			cpBean.setValue(arr[2].toString());
			cpBean.setCrop(arr[3].toString());
			//cropProductBeanList.add(cpBean);
			tempParamukhMandi.add(cpBean);
			//System.out.println(arr[0]);
			//System.out.println(arr[1]);
			//System.out.println(arr[2]);
			
			// seedBeanList.add(cpBean);

		}
      }
      catch(Exception e){
    	  System.err.println("Error in pramukh mandi when fetching from crop Product");
      }
		//System.out.println("pramukh mandi query :-"+pramukhQuery);
		//System.out.println("-------------------------crop product bean --------------------------------");
		
		finalBean.setMandiOilBeanList(tempParamukhMandi); 
	

		return finalBean;
	}

	public String[] getListOfPramukhMandisFromAgmark(String cropName) {

		try {
			String mandiString = environment.getProperty(cropName + ".pramukh");
			String[] mandiList = mandiString.split(",");
			return mandiList;
		} catch (Exception e) {
			//System.out.println("exception when not found in property file");
			return new String[] {};

		}

	}
	
	public String getCropName(String cropname) {
		
		
		try{
			if(!environment.getProperty(cropname).isEmpty()){
			cropname=environment.getProperty(cropname);
			environment.getProperty(cropname);
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
			//e.getMessage();
			//System.out.println("Either file not found or crop not found in file");
			
		}
		
		//System.out.println("crop name in mandi from properties file :-"+cropname);
		return cropname;
	}

	@Override
	public Map<String, List<MandiChartBean>> mandiRatesOnChart(String phoneNo,String cropName) {
		
		
		UserProfileRegistrationBean profile=userService.getUserProfileOnPhoneNo(phoneNo);
		int villageId=profile.getVillageId();
		System.out.println("village id is --------->"+villageId);
		float lat,lon;
		Session session=sessionFactory.getCurrentSession();
		Map<String, List<MandiChartBean>> finalMap= new HashMap<>();
		String latLonQuery="select distinct tvd.id.lat,tvd.id.lon from TehsilVillageDetailsNew as tvd where tvd.iid="+villageId;
	           Object[] o=(Object[])session.createQuery(latLonQuery).list().get(0);
		      lat=Float.parseFloat(o[0].toString());
		      lon=Float.parseFloat(o[1].toString());
		      String currentDate=yyyy_mm_dd.format(new Date());
		      String PreviousDate=yyyy_mm_dd.format(DateUtils.addDays(new Date(), -15));
		    //  System.out.println("lat---->"+lat);
		     // System.out.println("lon---->"+lon);
		      String mandiAvgSqlQuery="select distinct m.mandi,avg(m.modal_price) as avg,m.lat,m.lon from webkc.mandiPriceLatLon as m where m.crop='"+cropName+"' and m.date_of_download between '"+PreviousDate+"' and '"+currentDate+"' and m.lat between "+ (lat - 1.0f) +" and "+(lat+1.0f)+ " and m.lon between "+(lon-1.0f)+ " and "+(lon+1.0f)+ "  group by m.mandi order by avg desc limit 3";
             // System.out.println(mandiAvgSqlQuery);	 
		      String mandi="";
              List avgMandiList=session.createSQLQuery(mandiAvgSqlQuery).list();          
	             for(Object obj:avgMandiList){
	            	 Object[] arr=(Object[])obj;
	            	 mandi="'"+arr[0].toString()+"',"+mandi;
	            	 
	            	 
	             }if(!mandi.isEmpty()){
	            	 mandi=mandi.substring(0,mandi.length()-1);	 
	             }
	            // System.out.println(mandi);
	             String mandiQuery="select m.mandi,m.date_of_download,m.modal_price from webkc.mandiPriceLatLon as m where m.mandi in("+mandi+") and m.date_of_download between '"+PreviousDate+"' and '"+currentDate+"' and crop='"+cropName+"' order by m.mandi,m.date_of_download";
	           //  System.out.println(mandiQuery);
	             List finalMandiList=session.createSQLQuery(mandiQuery).list();
	             int i=-15;
	             String tempDate=yyyy_mm_dd.format(DateUtils.addDays(new Date(), -15));
	             String tempVal="0";
	             for(Object obj:finalMandiList){
	            	 Object[] arr=(Object[])obj;
	            	 String m=arr[0].toString();
	            	 
	            	 if(finalMap.containsKey(m)){
	            		
	            		 
	            		 if(tempDate.equals(arr[1].toString())){
	            		
	            			
		            		 try {
		            			// System.out.println("date found temp date"+ tempDate+" mandi date "+arr[1].toString()+"for -----"+m);
		            			 MandiChartBean bean= new MandiChartBean();
			            		 bean.setDate(arr[1].toString());
			            		 bean.setPrice(arr[2].toString());
			            		 tempVal=bean.getPrice();
			            		 finalMap.get(m).add(bean);
			            		 finalMap.put(m, finalMap.get(m));
								tempDate=yyyy_mm_dd.format(DateUtils.addDays(yyyy_mm_dd.parse(tempDate), 1));
								tempVal=bean.getPrice();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            		 i=i+1;
	            		 }
	            		 else {
	            			// System.out.println("date not found  date"+ tempDate+" mandi date "+arr[1].toString()+"for -----"+m);
	            			 while (!tempDate.equals(arr[1].toString())) {
								// System.out.println("------------->filling up date from "+tempDate+" to "+arr[1].toString()+"for -----"+m );
	            				 int j=1;
	            				 try {
									
									 MandiChartBean bean= new MandiChartBean();
			            			 bean.setDate(tempDate);
				            		 bean.setPrice(tempVal);
				            		 finalMap.get(m).add(bean);
				            		 finalMap.put(m, finalMap.get(m));
				            		 tempDate=yyyy_mm_dd.format(DateUtils.addDays(yyyy_mm_dd.parse(tempDate), j));
				            		 i=i+1;
				            		 
									
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
	            			
	            		
		            		 try {
		            			 MandiChartBean bean= new MandiChartBean();
		            			 bean.setDate(arr[1].toString());
			            		 bean.setPrice(arr[2].toString());
			            		 finalMap.get(m).add(bean);
			            		 finalMap.put(m, finalMap.get(m));
			            		 tempVal=bean.getPrice();
								tempDate=yyyy_mm_dd.format(DateUtils.addDays(yyyy_mm_dd.parse(tempDate), 1));
								
								//System.out.println("temp date after exiting while ------->"+tempDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
	            		 
	            		 
	            	 }
	            	 else {
						
	            		
	            	
	            	//	System.out.println("inserting value for when key not found"+yyyy_mm_dd.format(DateUtils.addDays(new Date(), -15)));
							tempDate=yyyy_mm_dd.format(DateUtils.addDays(new Date(), -14));
							 MandiChartBean bean= new MandiChartBean();
		            		 
		            		 bean.setDate(yyyy_mm_dd.format(DateUtils.addDays(new Date(), -15)));
		            		 bean.setPrice(arr[2].toString());
		            		 tempVal=arr[2].toString();
		            		 List<MandiChartBean> beanList= new ArrayList<>();
		            		 beanList.add(bean);
		            		 finalMap.put(m, beanList);
				
					}
	            	 
	            	 
	             }
	           
	            return finalMap; 
	}

	@Override
	public List<String> distinctStateFromAgmark(String cropName) {
		Session session= webkcSessionfactory.getCurrentSession();
		List tempList=session.createSQLQuery("select distinct state from mandiPriceLatLon where crop='"+cropName+"' order by state").list();
		List<String> finalList= new ArrayList<>();
		for(Object obj : tempList){
			finalList.add(String.valueOf(obj));
		}
		tempList=null;
		return finalList;
	}
	
	@Override
	public List<String> distinctDistirctFromAgmark(String state) {
		Session session= webkcSessionfactory.getCurrentSession();
		List tempList=session.createSQLQuery("select distinct mandi from mandiPriceLatLon where state='"+state+"' order by state").list();
		List<String> finalList= new ArrayList<>();
		for(Object obj : tempList){
			finalList.add(String.valueOf(obj));
		}
		tempList=null;
		return finalList;
	}

	@Override
	public MandiRateBean mandiOnSelectedDistrict(String state, String district,String cropName) {
		MandiRateBean bean= new MandiRateBean();
		//getting agmark list
		String  previousDate=yyyy_mm_dd.format(DateUtils.addDays(new Date(), -5));
		String  todayDate=yyyy_mm_dd.format(new Date());
		String agmarkQuery=" select date_of_download,mandi,state,arrival,modal_price,crop from mandiPriceLatLon where date_of_download between '"+previousDate+"' and  '"+todayDate+"' and state regexp '"+state+"' and mandi regexp '"+district+"' and crop='"+cropName+"' order by date_of_download desc";
		//System.out.println(agmarkQuery);
		Session session= webkcSessionfactory.getCurrentSession();
		List tempAgmarkList=session.createSQLQuery(agmarkQuery).list();
		List<MandiBean> agmarkList=new ArrayList<>();
		List<MandiBean> agmarkBean= new ArrayList<>();
		for(Object obj:tempAgmarkList){
			Object[] objArr=(Object[])obj;
			MandiBean b= new MandiBean();
			b.setDate(String.valueOf(objArr[0]));
			b.setCrop(cropName);
			b.setMandi(district);
			b.setState(state);
			b.setPrice(Float.parseFloat(objArr[4].toString()));
			b.setArrival(Float.parseFloat(objArr[3].toString()));
			agmarkList.add(b);
		}
		bean.setAgmarkList(agmarkList);
		tempAgmarkList=null;
		String cropProductQuery="select cp.city,cp.productName,cp.value,cp.date from cropProduct as cp, mandiDetails as m where m.state='"+state+"' and m.mandi=cp.city and cp.crop='"+cropName+"' and cp.date between '"+previousDate+"' and '"+todayDate+"' order by cp.date desc";
	//	System.out.println(cropProductQuery);
		session=factory.getCurrentSession();
		List tempList;
		try{
			 tempList=session.createSQLQuery(cropProductQuery).list();	
		}
		catch(Exception e){
			e.printStackTrace();
			tempList= new ArrayList<>();
		}
		
		List<MandiOilBean> majorMandiList= new ArrayList<>();
		for(Object obj:tempList){
			Object[] arr=(Object[])obj;
			MandiOilBean b1= new MandiOilBean();
			b1.setCity(arr[0].toString());
			b1.setCrop(arr[1].toString());
			b1.setDate(arr[3].toString());
			b1.setValue(arr[2].toString());
            majorMandiList.add(b1);			
		}
		bean.setMandiOilBeanList(majorMandiList);
		tempList=null;
		return bean;
	}
}
