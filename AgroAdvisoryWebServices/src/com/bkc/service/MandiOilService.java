package com.bkc.service;

import java.util.List;
import java.util.Map;

import com.bkc.bean.MandiChartBean;
import com.bkc.bean.MandiRateBean;

public interface MandiOilService {
   
	public MandiRateBean getmandiOilDetails(String cropname);
	public MandiRateBean getmandiOilDetailsCropSpecific(String cropname,float lat,float lon);
	public Map<String, List<MandiChartBean>> mandiRatesOnChart(String phoneNo, String cropName) throws Exception;
	//---Mandi according to Districts
		public List<String> distinctStateFromAgmark(String cropName);
		public List<String> distinctDistirctFromAgmark(String state);
		public MandiRateBean mandiOnSelectedDistrict(String state,String district,String cropName)throws Exception;
		//
		
	
}
