package com.bkc.dao;

import java.util.List;

import com.bkc.model.Master;
import com.bkc.model.StationDetails;

public interface StationDataDao {
	public List<StationDetails> getStation(String state);
    
}
