package com.bkc.dao;

import java.util.List;

import com.bkc.bean.CropCategoryBean;
import com.bkc.bean.ForecastBean;
import com.bkc.bean.MandiBean;
import com.bkc.bean.Forecast.StateDistrictBean;

public interface ForecastDao {
	
	public List<ForecastBean> getForecast(float lat,float lon,int villageId,int noOfDays);
	public List<MandiBean> getMandi(float lat,float lon,String cropName,String phoneNo,String state);
	public CropCategoryBean getMandiCrops(String phoneNo);
	public List<StateDistrictBean> getStates();
	public List<StateDistrictBean> getDistrict(int stateId);
	public List<ForecastBean> getDistrictForecast(int districtId,int stateId,String district);
	

}
