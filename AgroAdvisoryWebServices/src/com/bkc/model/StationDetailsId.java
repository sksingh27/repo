package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

/**
 * @author Vicky Rajput
 */

@Embeddable
@Table(name="stationDetails")
public class StationDetailsId implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="id")
	private Integer id;    
    @Column(name="lat")
    private Float lat;
    @Column(name="lon")
    private Float lon;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
    
    

}
