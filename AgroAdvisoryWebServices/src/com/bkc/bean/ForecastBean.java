package com.bkc.bean;



public class ForecastBean {
	
		
	
	private String state;
	private String city;
	private Float max_rh;	
	private Float min_rh;	
	private Float min_temp;	
	private Float max_temp;	
	private Float wind_speed;	
	private Float rainfall;
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Float getMax_rh() {
		return max_rh;
	}
	public void setMax_rh(Float max_rh) {
		this.max_rh = max_rh;
	}
	
	public Float getMin_rh() {
		return min_rh;
	}
	public void setMin_rh(Float min_rh) {
		this.min_rh = min_rh;
	}
	public Float getMin_temp() {
		return min_temp;
	}
	public void setMin_temp(Float min_temp) {
		this.min_temp = min_temp;
	}
	public Float getMax_temp() {
		return max_temp;
	}
	public void setMax_temp(Float max_temp) {
		this.max_temp = max_temp;
	}
	public Float getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(Float wind_speed) {
		this.wind_speed = wind_speed;
	}
	public Float getRainfall() {
		return rainfall;
	}
	public void setRainfall(Float rainfall) {
		this.rainfall = rainfall;
	}
	
	

}
