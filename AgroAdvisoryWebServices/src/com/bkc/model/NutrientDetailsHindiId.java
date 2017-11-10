package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;


@Table(name="nutrientDetailsHindi")
@Embeddable
public class NutrientDetailsHindiId implements Serializable {
	@Column(name="cropId")
	int cropId;
	@Column(name="nutrientId")
    int nutrientId;
	@Column(name="languageId")
	String languageId;
	
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public int getCropId() {
		return cropId;
	}
	public void setCropId(int cropId) {
		this.cropId = cropId;
	}
	public int getNutrientId() {
		return nutrientId;
	}
	public void setNutrientId(int nutrientId) {
		this.nutrientId = nutrientId;
	}
	
	

}
