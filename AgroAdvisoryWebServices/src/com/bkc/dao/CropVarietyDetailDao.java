package com.bkc.dao;

import java.util.List;

import com.bkc.model.CropVarietyDetail;

public interface CropVarietyDetailDao {

	
	public List<CropVarietyDetail> listData();

	public List<String> getState();
	public List<CropVarietyDetail> getVariety(String state,int cropId);
}
