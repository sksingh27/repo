package com.bkc.service;

import java.util.List;

import com.bkc.bean.VillageBean;
import com.bkc.bean.VillageLevelBean;
import com.bkc.model.StationDetails;

public interface StationDetailsService {

	public List<StationDetails> getList();
	public List<StationDetails> getStation(String state);
	public Integer getStationID(String station,String state);
	public List<String> getDistrictonState(String state);
	public List<String> getStationsOnDist(String state,String district);
	public List<VillageLevelBean> getTehsil(String state,String district,String tehsil);
	public List<VillageLevelBean> villagesOnGeoLocation(float lat,float lon);
	public VillageBean getVillageOnId(int id);
}
