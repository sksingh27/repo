package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="tehsilVillageDetails")
public class TehsilDetails {

	
	@EmbeddedId
	TehsilDetailsId id;
	
	
    @Column(name="tehsil")
    String tehsil;
    @Column(name="village")
	String village;
    @Column(name="lat")
	float lat;
    @Column(name="lon")
	float lon;
    
	

	public TehsilDetailsId getId() {
		return id;
	}
	public void setId(TehsilDetailsId id) {
		this.id = id;
	}
	public String getTehsil() {
		return tehsil;
	}
	public void setTehsil(String tehsil) {
		this.tehsil = tehsil;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
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
    
}
