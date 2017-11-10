package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;


@Embeddable
@Table(name="addNewCrop")
public class AddNewCropId implements Serializable {

	
	@Column(name="newCropId")
	int newCropId;
	
	@Column(name="cropId")
	int cropId;
	
	@Column(name="statonId")
	int stationId;
	
	
	public int getNewCropId() {
		return newCropId;
	}
	public void setNewCropId(int newCropId) {
		this.newCropId = newCropId;
	}
	public int getCropId() {
		return cropId;
	}
	public void setCropId(int cropId) {
		this.cropId = cropId;
	}
	public int getStationId() {
		return stationId;
	}
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	
}
