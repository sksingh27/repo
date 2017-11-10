package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name="diseaseDetailsHindi")
public class DiseaseDetailsHindi implements Serializable{
	
	@EmbeddedId
	private DiseaseDetailsHindiId id;
	
	@Column(name="diseaseName")
	private String dieaseName;
	
	@Column(name="diseaseType")
	private String diseaseType;	
	
	@Column(name = "minTempEqual")
	private Float minTempEqual;
	
	@Column(name="minTempLessThan")
	private Float minTempLessThan;
	
	@Column(name="minTempGreaterThan")
	private Float minTempGreaterThan;
	
	@Column(name = "minTempBetween1")
	private Float minTempBetween1;
	
	@Column(name = "minTempBetween2")
	private Float minTempBetween2;
	
	@Column(name = "maxTempEqual")
	private Float maxTempEqual;
	
	@Column(name="maxTempLessThan")
	private Float maxTempLessThan;
	
	@Column(name="maxTempGreaterThan")
	private Float maxTempGreaterThan;
	
	@Column(name = "maxTempBetween1")
	private Float maxTempBetween1;
	
	@Column(name = "maxTempBetween2")
	private Float maxTempBetween2;
	
	@Column(name = "rHEqual")
	private Float rHEqual;
	
	@Column(name="rHLessThan")
	private Float rHLessThan;
	
	@Column(name="rHGreaterThan")
	private Float rHGreaterThan;
	
	@Column(name = "rHBetween1")
	private Float rHBetween1;
	
	@Column(name = "rHBetween2")
	private Float rHBetween2;
	
	@Column(name = "rainfallEqual")
	private Float rainfallEqual;
	
	@Column(name="rainfallLessThan")
	private Float rainfallLessThan;
	
	@Column(name="rainfallGreaterThan")
	private Float rainfallGreaterThan;
	
	@Column(name = "rainfallBetween1")
	private Float rainfallBetween1;
	
	@Column(name = "rainfallBetween2")
	private Float rainfallBetween2;
	
	/*@Column(name = "windSpeedEqual")
	private Float windSpeedEqual;
	
	@Column(name = "windSpeedLessThan")
	private Float windSpeedLessThan;
	
	@Column(name = "windSpeedGreaterThan")
	private Float windSpeedGreaterThan;
	
	@Column(name = "windSpeedBetween1")
	private Float windSpeedBetween1;
	
	@Column(name = "windSpeedBetween2")
	private Float windSpeedBetween2;*/
	
	/*@Column(name = "radiationEqual")
	private Float radiationEqual;
	
	@Column(name = "radiationLessThan")
	private Float radiationLessThan;
	
	@Column(name = "radiationGreaterThan")
	private Float radiationGreaterThan;
	
	@Column(name = "radiationBetween1")
	private Float radiationBetween1;
	
	@Column(name = "radiationBetween2")
	private Float radiationBetween2;*/
	
/*	@Column(name="eTEqual")
	private Float eTEqual;
	
	@Column(name="eTLessThan")
	private Float eTLessThan;
	
	@Column(name="eTGreaterThan")
	private Float eTGreaterThan;
	
	@Column(name="eTBetween1")
	private Float eTBetween1;*/
	
	/*@Column(name="eTBetween2")
	private Float eTBetween2;*/
	
	@Column(name="otherFactor")
	private String otherFactor;
	
	@Column(name="symptoms")
	private String symptoms;

	@Column(name="days_between1")
	private int days_between1;
	
	@Column(name="days_between2")
	private int days_between2;
	
	@Column(name="controlMeasure")
	private String controlMeasure;

	public DiseaseDetailsHindiId getId() {
		return id;
	}

	public void setId(DiseaseDetailsHindiId id) {
		this.id = id;
	}

	public String getDieaseName() {
		return dieaseName;
	}

	public void setDieaseName(String dieaseName) {
		this.dieaseName = dieaseName;
	}

	public String getDiseaseType() {
		return diseaseType;
	}

	public void setDiseaseType(String diseaseType) {
		this.diseaseType = diseaseType;
	}

	public Float getMinTempEqual() {
		return minTempEqual;
	}

	public void setMinTempEqual(Float minTempEqual) {
		this.minTempEqual = minTempEqual;
	}

	public Float getMinTempLessThan() {
		return minTempLessThan;
	}

	public void setMinTempLessThan(Float minTempLessThan) {
		this.minTempLessThan = minTempLessThan;
	}

	public Float getMinTempGreaterThan() {
		return minTempGreaterThan;
	}

	public void setMinTempGreaterThan(Float minTempGreaterThan) {
		this.minTempGreaterThan = minTempGreaterThan;
	}

	public Float getMinTempBetween1() {
		return minTempBetween1;
	}

	public void setMinTempBetween1(Float minTempBetween1) {
		this.minTempBetween1 = minTempBetween1;
	}

	public Float getMinTempBetween2() {
		return minTempBetween2;
	}

	public void setMinTempBetween2(Float minTempBetween2) {
		this.minTempBetween2 = minTempBetween2;
	}

	public Float getMaxTempEqual() {
		return maxTempEqual;
	}

	public void setMaxTempEqual(Float maxTempEqual) {
		this.maxTempEqual = maxTempEqual;
	}

	public Float getMaxTempLessThan() {
		return maxTempLessThan;
	}

	public void setMaxTempLessThan(Float maxTempLessThan) {
		this.maxTempLessThan = maxTempLessThan;
	}

	public Float getMaxTempGreaterThan() {
		return maxTempGreaterThan;
	}

	public void setMaxTempGreaterThan(Float maxTempGreaterThan) {
		this.maxTempGreaterThan = maxTempGreaterThan;
	}

	public Float getMaxTempBetween1() {
		return maxTempBetween1;
	}

	public void setMaxTempBetween1(Float maxTempBetween1) {
		this.maxTempBetween1 = maxTempBetween1;
	}

	public Float getMaxTempBetween2() {
		return maxTempBetween2;
	}

	public void setMaxTempBetween2(Float maxTempBetween2) {
		this.maxTempBetween2 = maxTempBetween2;
	}

	public Float getrHEqual() {
		return rHEqual;
	}

	public void setrHEqual(Float rHEqual) {
		this.rHEqual = rHEqual;
	}

	public Float getrHLessThan() {
		return rHLessThan;
	}

	public void setrHLessThan(Float rHLessThan) {
		this.rHLessThan = rHLessThan;
	}

	public Float getrHGreaterThan() {
		return rHGreaterThan;
	}

	public void setrHGreaterThan(Float rHGreaterThan) {
		this.rHGreaterThan = rHGreaterThan;
	}

	public Float getrHBetween1() {
		return rHBetween1;
	}

	public void setrHBetween1(Float rHBetween1) {
		this.rHBetween1 = rHBetween1;
	}

	public Float getrHBetween2() {
		return rHBetween2;
	}

	public void setrHBetween2(Float rHBetween2) {
		this.rHBetween2 = rHBetween2;
	}

	public Float getRainfallEqual() {
		return rainfallEqual;
	}

	public void setRainfallEqual(Float rainfallEqual) {
		this.rainfallEqual = rainfallEqual;
	}

	public Float getRainfallLessThan() {
		return rainfallLessThan;
	}

	public void setRainfallLessThan(Float rainfallLessThan) {
		this.rainfallLessThan = rainfallLessThan;
	}

	public Float getRainfallGreaterThan() {
		return rainfallGreaterThan;
	}

	public void setRainfallGreaterThan(Float rainfallGreaterThan) {
		this.rainfallGreaterThan = rainfallGreaterThan;
	}

	public Float getRainfallBetween1() {
		return rainfallBetween1;
	}

	public void setRainfallBetween1(Float rainfallBetween1) {
		this.rainfallBetween1 = rainfallBetween1;
	}

	public Float getRainfallBetween2() {
		return rainfallBetween2;
	}

	public void setRainfallBetween2(Float rainfallBetween2) {
		this.rainfallBetween2 = rainfallBetween2;
	}

/*	public Float getWindSpeedEqual() {
		return windSpeedEqual;
	}

	public void setWindSpeedEqual(Float windSpeedEqual) {
		this.windSpeedEqual = windSpeedEqual;
	}

	public Float getWindSpeedLessThan() {
		return windSpeedLessThan;
	}

	public void setWindSpeedLessThan(Float windSpeedLessThan) {
		this.windSpeedLessThan = windSpeedLessThan;
	}

	public Float getWindSpeedGreaterThan() {
		return windSpeedGreaterThan;
	}

	public void setWindSpeedGreaterThan(Float windSpeedGreaterThan) {
		this.windSpeedGreaterThan = windSpeedGreaterThan;
	}

	public Float getWindSpeedBetween1() {
		return windSpeedBetween1;
	}

	public void setWindSpeedBetween1(Float windSpeedBetween1) {
		this.windSpeedBetween1 = windSpeedBetween1;
	}

	public Float getWindSpeedBetween2() {
		return windSpeedBetween2;
	}

	public void setWindSpeedBetween2(Float windSpeedBetween2) {
		this.windSpeedBetween2 = windSpeedBetween2;
	}

	public Float getRadiationEqual() {
		return radiationEqual;
	}

	public void setRadiationEqual(Float radiationEqual) {
		this.radiationEqual = radiationEqual;
	}

	public Float getRadiationLessThan() {
		return radiationLessThan;
	}

	public void setRadiationLessThan(Float radiationLessThan) {
		this.radiationLessThan = radiationLessThan;
	}

	public Float getRadiationGreaterThan() {
		return radiationGreaterThan;
	}

	public void setRadiationGreaterThan(Float radiationGreaterThan) {
		this.radiationGreaterThan = radiationGreaterThan;
	}

	public Float getRadiationBetween1() {
		return radiationBetween1;
	}

	public void setRadiationBetween1(Float radiationBetween1) {
		this.radiationBetween1 = radiationBetween1;
	}

	public Float getRadiationBetween2() {
		return radiationBetween2;
	}

	public void setRadiationBetween2(Float radiationBetween2) {
		this.radiationBetween2 = radiationBetween2;
	}

	public Float geteTEqual() {
		return eTEqual;
	}

	public void seteTEqual(Float eTEqual) {
		this.eTEqual = eTEqual;
	}

	public Float geteTLessThan() {
		return eTLessThan;
	}

	public void seteTLessThan(Float eTLessThan) {
		this.eTLessThan = eTLessThan;
	}

	public Float geteTGreaterThan() {
		return eTGreaterThan;
	}

	public void seteTGreaterThan(Float eTGreaterThan) {
		this.eTGreaterThan = eTGreaterThan;
	}

	public Float geteTBetween1() {
		return eTBetween1;
	}

	public void seteTBetween1(Float eTBetween1) {
		this.eTBetween1 = eTBetween1;
	}

	public Float geteTBetween2() {
		return eTBetween2;
	}

	public void seteTBetween2(Float eTBetween2) {
		this.eTBetween2 = eTBetween2;
	}*/

	public String getOtherFactor() {
		return otherFactor;
	}

	public void setOtherFactor(String otherFactor) {
		this.otherFactor = otherFactor;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public int getDays_between1() {
		return days_between1;
	}

	public void setDays_between1(int days_between1) {
		this.days_between1 = days_between1;
	}

	public int getDays_between2() {
		return days_between2;
	}

	public void setDays_between2(int days_between2) {
		this.days_between2 = days_between2;
	}

	public String getControlMeasure() {
		return controlMeasure;
	}

	public void setControlMeasure(String controlMeasure) {
		this.controlMeasure = controlMeasure;
	}	
	
	
	

	

}
