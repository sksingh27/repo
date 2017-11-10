package com.bkc.dao;

import java.util.List;

import com.bkc.bean.CropVarietyBean;
import com.bkc.model.CropDetails;

public interface CropDetailsdao {

	public List<CropDetails> listData();
	public Integer getCropId(String cropname);
	public CropDetails getCropDetails(int cropId);
	public List<CropVarietyBean> getVariety(int cropId,String languageId);
}
