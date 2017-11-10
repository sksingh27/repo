package com.bkc.dao2;

import java.util.List;

import com.bkc.bean.ForecastBean;

public interface ForecastDao2 {
	public List<ForecastBean> getForecast(float lat,float lon,int noOfDays,String village,String state);

}
