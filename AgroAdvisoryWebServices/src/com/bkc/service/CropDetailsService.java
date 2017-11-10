package com.bkc.service;

import java.util.List;

import com.bkc.bean.CropVarietyBean;
import com.bkc.model.CropDetails;

public interface CropDetailsService {

	public List<CropDetails> listdata();
	public Integer getCropId(String cropName);
	public CropDetails getCropDetails(int cropId);
	public List<CropVarietyBean> getVariety(int cropId,String languageId);
	
	
}
