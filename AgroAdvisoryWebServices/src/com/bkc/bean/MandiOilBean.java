package com.bkc.bean;

public class MandiOilBean {

	String date;
	String city;
	String value;
	String crop;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
	
	public String getCrop() {
		return crop;
	}
	public void setCrop(String crop) {
		this.crop = crop;
	}
	@Override
	public String toString() {
	     System.out.println("date is :- "+date);
	     System.out.println("city is :- "+city);
	     System.out.println("value is :- "+ value);
	     System.out.println("crop is :- "+ crop);
	    
		return super.toString();
	}
}
