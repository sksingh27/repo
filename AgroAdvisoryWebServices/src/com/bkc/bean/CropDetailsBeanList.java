package com.bkc.bean;

import java.io.Serializable;
import java.util.List;

import com.bkc.model.CropDetails;

public class CropDetailsBeanList implements Serializable {

	List<CropDetails> cropList;

	public List<CropDetails> getCropList() {
		return cropList;
	}

	public void setCropList(List<CropDetails> cropList) {
		this.cropList = cropList;
	}
	
	
	
}
