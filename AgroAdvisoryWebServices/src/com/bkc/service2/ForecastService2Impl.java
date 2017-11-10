package com.bkc.service2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.ForecastBean;
import com.bkc.dao2.ForecastDao2;

@Service("forecastService2")
@Transactional
public class ForecastService2Impl implements ForecastService2{

	@Autowired
	ForecastDao2 dao;
	
	@Override
	public List<ForecastBean> getForecast(float lat, float lon, int noOfDays, String village, String state) {
		
		return this.dao.getForecast(lat, lon, noOfDays, village, state);
	}

}
