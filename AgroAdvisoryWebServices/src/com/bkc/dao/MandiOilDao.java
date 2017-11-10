package com.bkc.dao;

import java.util.List;
import java.util.Map;

import com.bkc.bean.MandiChartBean;
import com.bkc.bean.MandiRateBean;

public interface MandiOilDao {

	public MandiRateBean getmandiOilDetails(String cropName);
	public MandiRateBean getmandiOilDetailsCropSpecific(String cropName,float lat ,float lon);
	public Map<String, List<MandiChartBean>> mandiRatesOnChart(String phoneNo,String cropName);
	//---Mandi according to Districts
	public List<String> distinctStateFromAgmark(String cropName);
	public List<String> distinctDistirctFromAgmark(String state);
	public MandiRateBean mandiOnSelectedDistrict(String state,String district,String cropName);
	//
	
}
