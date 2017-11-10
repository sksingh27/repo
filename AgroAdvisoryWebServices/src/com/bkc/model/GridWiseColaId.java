package com.bkc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;


/**
 * @author Vicky Rajput
 */

@Embeddable
@Table(name="gridwiseCola")
public class GridWiseColaId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="f_date")
	private String f_date;
    @Column(name="gmt")
	private Integer gmt;
    @Column(name="lat")
    private Float lat;
    @Column(name="lon")
    private Float lon;
	public String getF_date() {
		return f_date;
	}
	public void setF_date(String f_date) {
		this.f_date = f_date;
	}
	public Integer getGmt() {
		return gmt;
	}
	public void setGmt(Integer gmt) {
		this.gmt = gmt;
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
