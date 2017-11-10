package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="insectPestHindi")
public class InsectPestHindi {
    @Id
	int id;
    @Column(name="cropId")
	int cropId;
	@Column(name="insectPest")
    String insectPest;
	@Column(name="daysBetween1")
	int daysBetween1;
	@Column(name="daysBetween2")
	int daysBetween2;
	@Column(name="languageId")
	String languageId;
	
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCropId() {
		return cropId;
	}
	public void setCropId(int cropId) {
		this.cropId = cropId;
	}
	public String getInsectPest() {
		return insectPest;
	}
	public void setInsectPest(String insectPest) {
		this.insectPest = insectPest;
	}
	public int getDaysBetween1() {
		return daysBetween1;
	}
	public void setDaysBetween1(int daysBetween1) {
		this.daysBetween1 = daysBetween1;
	}
	public int getDaysBetween2() {
		return daysBetween2;
	}
	public void setDaysBetween2(int daysBetween2) {
		this.daysBetween2 = daysBetween2;
	}
	
}
