package com.bkc.bean2;

public class ClientRegistrationBean {
	String username;
	String phone;
	float lat;
	float lon;
	int locationid;
	String state;
	String location;
	float unroundedLat;
	float unroundedLon;
	boolean save;
	String aadharNo;
	
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getLocationid() {
		return locationid;
	}
	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public boolean isSave() {
		return save;
	}
	public void setSave(boolean save) {
		this.save = save;
	}
	public float getUnroundedLat() {
		return unroundedLat;
	}
	public void setUnroundedLat(float unroundedLat) {
		this.unroundedLat = unroundedLat;
	}
	public float getUnroundedLon() {
		return unroundedLon;
	}
	public void setUnroundedLon(float unroundedLon) {
		this.unroundedLon = unroundedLon;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
