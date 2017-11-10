package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author Vicky Rajput 
 */

@Entity
@Table(name="stationDetails")
public class StationDetails implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private StationDetailsId id;

	@Column(name = "cityName")
	private String cityName;
	
	@Column(name="state")
	private String state;
	
	@Column(name="elevationHeight")
	private Float elevationHeight;
	
	@Column(name="region")
	private String region;
      

	/*@Column(name = "taluk")
	private String taluk;*/

/*	public String getTaluk() {
		return taluk;
	}

	public void setTaluk(String taluk) {
		this.taluk = taluk;
	}
*/
	public StationDetailsId getId() {
		return id;
	}

	public void setId(StationDetailsId id) {
		this.id = id;
	}
		

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Float getElevationHeight() {
		return elevationHeight;
	}

	public void setElevationHeight(Float elevationHeight) {
		this.elevationHeight = elevationHeight;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	

}
