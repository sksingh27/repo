package com.bkc.bean;

public class RainfallBean {

	String date;
	float rainfall;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getRainfall() {
		return rainfall;
	}
	public void setRainfall(float rainfall) {
		this.rainfall = rainfall;
	}
	
	@Override
	public String toString() {
		String temp= String.valueOf(this.getRainfall());
		return temp;
	}
	
}
