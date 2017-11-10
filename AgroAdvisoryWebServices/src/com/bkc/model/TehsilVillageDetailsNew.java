package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="tehsilVillageDetails")
public class TehsilVillageDetailsNew {
	
	@EmbeddedId
	TehsilVillageDetailsNewId id;
	
	
    @Column(name="tehsil")
    String tehsil;
    @Column(name="village")
	String village;
    @Column(name="districtId")
    int districtId;
    @Column(name="id")
	int iid;
    @Column(name="stateid")
    int stateId;
    
    
    
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}

	public TehsilVillageDetailsNewId getId() {
		return id;
	}
	public void setId(TehsilVillageDetailsNewId id) {
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

}
