package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
@Embeddable
@Table(name="tehsilVillagedetails")
public class TehsilDetailsId implements Serializable {
	
    
    @Column(name="id")
    int id;
    @Column(name="districtId")
	int districtId;
    @Column(name="stateId")
	int stateId;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

}
