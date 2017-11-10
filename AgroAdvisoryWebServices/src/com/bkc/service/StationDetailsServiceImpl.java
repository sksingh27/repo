package com.bkc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.VillageBean;
import com.bkc.bean.VillageLevelBean;
import com.bkc.dao.StationDetailsDao;
import com.bkc.model.Master;
import com.bkc.model.StationDetails;

@Service("stationdService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StationDetailsServiceImpl implements StationDetailsService {

	@Autowired
	StationDetailsDao sddao;
	
	@Override
	public List<StationDetails> getList() {
		// TODO Auto-generated method stub
		return sddao.listData();
	}

	@Override
	public List<StationDetails> getStation(String state) {
		// TODO Auto-generated method stub
		System.out.println(" state in serviceimpl "+ state);
		return sddao.getStation(state);
	}

	@Override
	public Integer getStationID(String station, String state) {
		// TODO Auto-generated method stub
		return sddao.getStationID(station, state);
	}

	@Override
	public List<String> getDistrictonState(String state) {
		// TODO Auto-generated method stub
		return sddao.getDistrictonState(state);
	}

	@Override
	public List<String> getStationsOnDist(String state, String district ) {
		// TODO Auto-generated method stub
		return sddao.getStationsOnDist(state, district);
	}

	@Override
	public List<VillageLevelBean> getTehsil(String state, String district,String tehsil) {
		// TODO Auto-generated method stub
		return sddao.getTehsil(state, district,tehsil) ;
	}

	@Override
	public List<VillageLevelBean> villagesOnGeoLocation(float lat, float lon) {
		// TODO Auto-generated method stub
		return sddao.villagesOnGeoLocation(lat, lon);
	}

	@Override
	public VillageBean getVillageOnId(int id) {
		// TODO Auto-generated method stub
		return sddao.getVillageOnId(id);
	}

	

}
