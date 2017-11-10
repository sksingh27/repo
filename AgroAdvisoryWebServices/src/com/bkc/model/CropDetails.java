package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="cropDetails")
public class CropDetails implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name = "cropId")
	private Integer cropId;
	
	@Column(name="cropSeason")
	private String cropSeason;
	
	@Column(name="cropHindiName")
	private String	cropHindiName;
	
	public String getCropHindiName() {
		return cropHindiName;
	}
	public void setCropHindiName(String cropHindiName) {
		this.cropHindiName = cropHindiName;
	}
	public String getCropSeason() {
		return cropSeason;
	}
	public void setCropSeason(String cropSeason) {
		this.cropSeason = cropSeason;
	}

	
    @Column(name = "cropName")
	private String cropname;
	
		public Integer getCropId() {
		return cropId;
	}
	public void setCropId(Integer cropId) {
		this.cropId = cropId;
	}
	public String getCropname() {
		return cropname;
	}
	public void setCropname(String cropname) {
		this.cropname = cropname;
	}
	

}
