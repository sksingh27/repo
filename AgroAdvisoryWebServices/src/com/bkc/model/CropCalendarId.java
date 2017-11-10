package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

/**
 * @author Vicky 
 */
@Embeddable
@Table(name="cropCalendar")
public class CropCalendarId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "cropId")
	Integer cropId;
	
	@Column(name = "diseaseId")
	Integer diseaseId;
	
	@Column(name = "stage")
	String stage;
	
	@Column(name = "subStage")
	String subStage;
	

	public Integer getCropId() {
		return cropId;
	}

	public void setCropId(Integer cropId) {
		this.cropId = cropId;
	}

	public Integer getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Integer diseaseId) {
		this.diseaseId = diseaseId;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSubStage() {
		return subStage;
	}

	public void setSubStage(String subStage) {
		this.subStage = subStage;
	}
	
	
	

}
