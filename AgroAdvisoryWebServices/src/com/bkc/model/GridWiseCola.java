package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.cache.annotation.Cacheable;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;

/**
 * @author Akash 
 */



@Entity
@Table(name="gridwiseCola")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "gwc")
public class GridWiseCola implements Serializable {
	
	
	private static final long serialVersionUID = -723583058586873479L;	
	 @EmbeddedId
	 private GridWiseColaId id;
	
       	
	@Column(name = "max_rh")
	private Float max_rh;
	
	@Column(name = "min_rh")
	private String min_rh;
	
	@Column(name = "air_temp")
	private Float air_temp;
	
	@Column(name = "min_temp")
	private Float min_temp;
	
	@Column(name="max_temp")
	private Float max_temp; 
	
	@Column(name = "pressure")
	private String pressure;
	
	@Formula(value="cast(wind_speed as decimal(5, 2))")
	private Float wind_speed;
	
	@Column(name="wind_direction")
	private String wind_direction;
	
	@Column(name="soil_temp")
	private Float soil_temp;
	
	@Column(name="soil_moisture")
	private Float soil_moisture;
	
	
	@Formula(value="cast(clouds as decimal(5, 2))")
	private Float clouds;		
	
	@Column(name="rainfall")
	private Float rainfall;		
	
	@Column(name="ghi")
	private Float ghi;

	
	public GridWiseColaId getId() {
		return id;
	}

	public void setId(GridWiseColaId id) {
		this.id = id;
	}

	public Float getMax_rh() {
		return max_rh;
	}

	public void setMax_rh(Float max_rh) {
		this.max_rh = max_rh;
	}

	public String getMin_rh() {
		return min_rh;
	}

	public void setMin_rh(String min_rh) {
		this.min_rh = min_rh;
	}

	public Float getAir_temp() {
		return air_temp;
	}

	public void setAir_temp(Float air_temp) {
		this.air_temp = air_temp;
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

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	
	public Float getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(Float wind_speed) {
		this.wind_speed = wind_speed;
	}

	public String getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(String wind_direction) {
		this.wind_direction = wind_direction;
	}

	public Float getSoil_temp() {
		return soil_temp;
	}

	public void setSoil_temp(Float soil_temp) {
		this.soil_temp = soil_temp;
	}

	public Float getSoil_moisture() {
		return soil_moisture;
	}

	public void setSoil_moisture(Float soil_moisture) {
		this.soil_moisture = soil_moisture;
	}	
	
	public Float getClouds() {
		return clouds;
	}

	public void setClouds(Float clouds) {
		this.clouds = clouds;
	}

	public Float getRainfall() {
		return rainfall;
	}

	public void setRainfall(Float rainfall) {
		this.rainfall = rainfall;
	}

	public Float getGhi() {
		return ghi;
	}

	public void setGhi(Float ghi) {
		this.ghi = ghi;
	}

	@Override
	public String toString() {
		String temp= String.valueOf(this.getMax_rh());
		return temp;
	}

}
