package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="cropVarietyDetail")
public class CropVarietyDetail {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="cropId")
	private Integer cropId;
	
	@Column(name="state")
	private String state;
	
	@Column(name="cropVariety")
	private String cropVariety;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCropId() {
		return cropId;
	}
	public void setCropId(Integer cropId) {
		this.cropId = cropId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCropVariety() {
		return cropVariety;
	}
	public void setCropVariety(String cropVariety) {
		this.cropVariety = cropVariety;
	}
	
	
	
	
}
