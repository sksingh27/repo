package com.bkc.bean;

import java.util.List;

public class AgroAdvisoryBean {

	private String cropName;
	private String cropNameEnglish;
	private String city;
	private String state;
	private String heading;
	private List<String> disease;
	private String insectPest;
	 private String forecastAdvisory;
	private String mgntPracHindi;
	private String nutrient;
	private String notFound;
	
	public String getNotFound() {
		return notFound;
	}
	public void setNotFound(String notFound) {
		this.notFound = notFound;
	}
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	
	public String getCropNameEnglish() {
		return cropNameEnglish;
	}
	public void setCropNameEnglish(String cropNameEnglish) {
		this.cropNameEnglish = cropNameEnglish;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}

	public List<String> getDisease() {
		return disease;
	}
	public void setDisease(List<String> disease) {
		this.disease = disease;
	}
	public String getInsectPest() {
		return insectPest;
	}
	public void setInsectPest(String insectPest) {
		this.insectPest = insectPest;
	}
	public String getForecastAdvisory() {
		return forecastAdvisory;
	}
	public void setForecastAdvisory(String forecastAdvisory) {
		this.forecastAdvisory = forecastAdvisory;
	}
	public String getMgntPracHindi() {
		return mgntPracHindi;
	}
	public void setMgntPracHindi(String mgntPracHindi) {
		this.mgntPracHindi = mgntPracHindi;
	}
	public String getNutrient() {
		return nutrient;
	}
	public void setNutrient(String nutrient) {
		this.nutrient = nutrient;
	}
	
	

}
