package com.bkc.service;

import java.util.List;

import com.bkc.model.CropVarietyDetail;

public interface CropVarityDetailService {


	public List<CropVarietyDetail> listData();

	public List<String> getState();
	public List<CropVarietyDetail> getVariety(String state,int cropId);
	
	
}
