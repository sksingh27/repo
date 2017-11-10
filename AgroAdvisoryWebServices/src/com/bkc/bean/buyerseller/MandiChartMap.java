package com.bkc.bean.buyerseller;

import java.util.List;
import java.util.Map;

import com.bkc.bean.MandiChartBean;

public class MandiChartMap {
	
	Map<String, List<MandiChartBean>> mandiMap;
	List<String> dates;
	

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public Map<String, List<MandiChartBean>> getMandiMap() {
		return mandiMap;
	}

	public void setMandiMap(Map<String, List<MandiChartBean>> mandiMap) {
		this.mandiMap = mandiMap;
	}
	

}
