package com.bkc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Vicky Rajput 
 */
@Entity
@Table(name="userCropCalendar")
public class UserCropCalendar implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserCropCalendarId id;
	
	@Column(name="stages")
	private String stages;
	
	@Column(name="subStages")
	private String subStages;
	
	@Column(name="dateOfStage")
	private String dateOfStage;
    @Column(name="minTempEqual")
	private float minTempEqual;
	@Column(name="minTempBetween1")
	private float minTempBetween1;
	@Column(name="minTempBetween2")
	private float minTempBetween2;
	@Column(name="maxTempEqual")
	private float maxTempEqual;

	@Column(name="maxTempBetween1")
	private float maxTempBetween1;
	@Column(name="maxTempBetween2")
	private float maxTempBetween2;
	@Column(name="rainfallEqual")
	private float rainfallEqual;
	@Column(name="rainfallBetween1")
	private float rainfallBetween1;
	@Column(name="rainfallbetween2")
	private float rainfallBetween2;
	@Column(name="windSpeedEqual")
	private float windSpedEqual;
	@Column(name="windSpeedBetween1")
	private float windSpeedBetween1;
	@Column(name="windSpeedBetween2")
	private float windSpeedBetween2;
	@Column(name="radiationEqual")
	private float radiationEqual;
	@Column(name="rHEqual")
	private float rHEqual;
	@Column(name="rHbetween1")
	private float rHBetween1;
	@Column(name="rHBetween2")
	private float rHBetween2;
	@Column(name="radiationBetween1")
    private float radiationBetween1;
	@Column(name="radiationBetween2")
    private float radiationBetween2;
   	
	@Column (name="irrigation")
	private int irrigation;
	
	@Column (name="noOfDays")
	private int noOfDays;
	

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int getIrrigation() {
		return irrigation;
	}

	public void setIrrigation(int irrigation) {
		this.irrigation = irrigation;
	}

	public float getRadiationBetween1() {
		return radiationBetween1;
	}

	public void setRadiationBetween1(float radiationBetween1) {
		this.radiationBetween1 = radiationBetween1;
	}

	public float getRadiationBetween2() {
		return radiationBetween2;
	}

	public void setRadiationBetween2(float radiationBetween2) {
		this.radiationBetween2 = radiationBetween2;
	}


	public float getMinTempEqual() {
		return minTempEqual;
	}

	public void setMinTempEqual(float minTempEqual) {
		this.minTempEqual = minTempEqual;
	}

	public float getMinTempBetween1() {
		return minTempBetween1;
	}

	public void setMinTempBetween1(float minTempBetween1) {
		this.minTempBetween1 = minTempBetween1;
	}

	public float getMinTempBetween2() {
		return minTempBetween2;
	}

	public void setMinTempBetween2(float minTempBetween2) {
		this.minTempBetween2 = minTempBetween2;
	}

	public float getMaxTempEqual() {
		return maxTempEqual;
	}

	public void setMaxTempEqual(float maxTempEqual) {
		this.maxTempEqual = maxTempEqual;
	}

	public float getMaxTempBetween1() {
		return maxTempBetween1;
	}

	public void setMaxTempBetween1(float maxTempBetween1) {
		this.maxTempBetween1 = maxTempBetween1;
	}

	public float getMaxTempBetween2() {
		return maxTempBetween2;
	}

	public void setMaxTempBetween2(float maxTempBetween2) {
		this.maxTempBetween2 = maxTempBetween2;
	}

	public float getRainfallEqual() {
		return rainfallEqual;
	}

	public void setRainfallEqual(float rainfallEqual) {
		this.rainfallEqual = rainfallEqual;
	}

	public float getRainfallBetween1() {
		return rainfallBetween1;
	}

	public void setRainfallBetween1(float rainfallBetween1) {
		this.rainfallBetween1 = rainfallBetween1;
	}

	public float getRainfallBetween2() {
		return rainfallBetween2;
	}

	public void setRainfallBetween2(float rainfallBetween2) {
		this.rainfallBetween2 = rainfallBetween2;
	}

	public float getWindSpedEqual() {
		return windSpedEqual;
	}

	public void setWindSpedEqual(float windSpedEqual) {
		this.windSpedEqual = windSpedEqual;
	}

	public float getWindSpeedBetween1() {
		return windSpeedBetween1;
	}

	public void setWindSpeedBetween1(float windSpeedBetween1) {
		this.windSpeedBetween1 = windSpeedBetween1;
	}

	public float getWindSpeedBetween2() {
		return windSpeedBetween2;
	}

	public void setWindSpeedBetween2(float windSpeedBetween2) {
		this.windSpeedBetween2 = windSpeedBetween2;
	}

	public float getRadiationEqual() {
		return radiationEqual;
	}

	public void setRadiationEqual(float radiationEqual) {
		this.radiationEqual = radiationEqual;
	}

	public float getrHEqual() {
		return rHEqual;
	}

	public void setrHEqual(float rHEqual) {
		this.rHEqual = rHEqual;
	}

	public float getrHBetween1() {
		return rHBetween1;
	}

	public void setrHBetween1(float rHBetween1) {
		this.rHBetween1 = rHBetween1;
	}

	public float getrHBetween2() {
		return rHBetween2;
	}

	public void setrHBetween2(float rHBetween2) {
		this.rHBetween2 = rHBetween2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public UserCropCalendarId getId() {
		return id;
	}

	public void setId(UserCropCalendarId id) {
		this.id = id;
	}

	public String getStages() {
		return stages;
	}

	public void setStages(String stages) {
		this.stages = stages;
	}

	public String getSubStages() {
		return subStages;
	}

	public void setSubStages(String subStages) {
		this.subStages = subStages;
	}

	public String getDateOfStage() {
		return dateOfStage;
	}

	public void setDateOfStage(String dateOfStage) {
		this.dateOfStage = dateOfStage;
	}

	
}