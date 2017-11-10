package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Table(name="mgntPracticesHindi")
@Embeddable
public class MgntPractcesHindiId implements Serializable{
	@Column(name="cropId")
	int cropId;
	@Column(name="id")
	int id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
