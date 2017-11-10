package com.bkc.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.bean.MandiChartBean;
import com.bkc.bean.MandiRateBean;
import com.bkc.dao.MandiOilDao;

@Service
@Transactional
public class MandiOilServiceImpl implements MandiOilService{
    @Autowired
	MandiOilDao mandiOilDao;
	@Override
	public MandiRateBean getmandiOilDetails(String cropName) {
		return mandiOilDao.getmandiOilDetails(cropName);
		
	}

	public MandiRateBean getmandiOilDetailsCropSpecific(String cropname,float lat,float lon) {
		return mandiOilDao.getmandiOilDetailsCropSpecific( cropname,lat,lon);
		
	}

	@Override
	public Map<String, List<MandiChartBean>> mandiRatesOnChart(String phoneNo, String cropName) {
		return mandiOilDao.mandiRatesOnChart(phoneNo, cropName);
		
	}

	@Override
	public List<String> distinctStateFromAgmark(String cropName) {
		// TODO Auto-generated method stub
		return this.mandiOilDao.distinctStateFromAgmark(cropName);
	}

	@Override
	public List<String> distinctDistirctFromAgmark(String state) {
		// TODO Auto-generated method stub
		return this.mandiOilDao.distinctDistirctFromAgmark(state);
	}

	@Override
	public MandiRateBean mandiOnSelectedDistrict(String state, String district,String cropName) {
		// TODO Auto-generated method stub
		return this.mandiOilDao.mandiOnSelectedDistrict(state, district, cropName);
	}

	

	
	
}
