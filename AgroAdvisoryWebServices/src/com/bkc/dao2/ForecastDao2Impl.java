package com.bkc.dao2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.ForecastBean;
import com.bkc.staticclasses.GetRoundedLatLon;


@Repository("forecastDao2")
@Transactional
public class ForecastDao2Impl implements ForecastDao2 {

	
	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory SessionFactory;
	
	@Override
	public List<ForecastBean> getForecast(float lat, float lon, int noOfDays, String village,String state) {
		List<ForecastBean> returnList = new ArrayList<ForecastBean>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currentdate = new Date();

		String d1 = format.format(currentdate);
		String d2 = format.format(DateUtils.addDays(currentdate, noOfDays));

		//lat = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lat));
		//lon = GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(lon));
		String forecastHQL="select max(max_temp) ,min(min_temp),max(max_rh),min(max_rh),round(avg(wind_speed),2),sum(rainfall),id.f_date from GridWiseCola  where max_temp <> 999.90 and min_temp  <> 999.90 and max_rh <> 999.90 and id.lat='"+lat+"' and id.lon='"+lon+"' and id.f_date between '"+d1+"' and '"+d2+"' group by id.f_date order by id.f_date";
		//System.out.println(forecastHQL);
		Session session = SessionFactory.getCurrentSession();
		List tempList=session.createQuery(forecastHQL).list();
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
            bean.setCity(village);
            bean.setState(state);
            returnList.add(bean);
			
		}
		
		return returnList;
	}
	

}
