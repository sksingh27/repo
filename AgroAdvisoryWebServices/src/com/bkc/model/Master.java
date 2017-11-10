package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="master")
@Entity
public class Master {

	@EmbeddedId
	MasterId id;
	@Column(name="state_name")
	String state;
	@Column(name="dist_name")
	String district;
	@Column(name="tehsil_name")
	String tehshil;
	public MasterId getId() {
		return id;
	}
	public void setId(MasterId id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getTehshil() {
		return tehshil;
	}
	public void setTehshil(String tehshil) {
		this.tehshil = tehshil;
	}
	
	
	
}
