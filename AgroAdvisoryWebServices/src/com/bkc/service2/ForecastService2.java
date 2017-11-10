package com.bkc.service2;

import java.util.List;

import com.bkc.bean.ForecastBean;

public interface ForecastService2 {

	public List<ForecastBean> getForecast(float lat,float lon,int noOfDays,String village,String state);
	
}
