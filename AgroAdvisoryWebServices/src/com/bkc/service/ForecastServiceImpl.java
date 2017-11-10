package com.bkc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.CropCategoryBean;
import com.bkc.bean.ForecastBean;
import com.bkc.bean.MandiBean;
import com.bkc.bean.Forecast.StateDistrictBean;
import com.bkc.dao.ForecastDao;


@Service("forecastService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ForecastServiceImpl implements ForecastService {
	
	@Autowired
	ForecastDao forecastDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<ForecastBean> getForecast(float lat, float lon,int villageId,int noOfDays) {		
		return forecastDao.getForecast(lat, lon,villageId,noOfDays);
	}

	@Override
	public List<MandiBean> getMandi(float lat, float lon,String cropName,String phoneNo,String state) {
		// TODO Auto-generated method stub
		return forecastDao.getMandi(lat, lon,cropName,phoneNo,state);
	}

	@Override
	public CropCategoryBean getMandiCrops(String phoneNo) {
		// TODO Auto-generated method stub
		return forecastDao.getMandiCrops(phoneNo);
	}

	@Override
	public List<StateDistrictBean> getStates() {
		// TODO Auto-generated method stub
		return this.forecastDao.getStates();
	}

	@Override
	public List<StateDistrictBean> getDistrict(int stateId) {
		// TODO Auto-generated method stub
		return this.forecastDao.getDistrict(stateId);
	}

	@Override
	public List<ForecastBean> getDistrictForecast(int districtId, int stateId,String district) {
		
		return this.forecastDao.getDistrictForecast(districtId, stateId,district);
	}
	
	
	

}
