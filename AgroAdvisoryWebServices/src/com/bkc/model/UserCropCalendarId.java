package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Vicky Rajput 
 */

@Embeddable
@Table(name="userCropCalendar")
public class UserCropCalendarId implements Serializable{	
	
	
	private static final long serialVersionUID = 1L;


	
	@Column(name="stageId")
	private Integer stageId;
	
	@Column(name="phoneNo")
	private String phoneNo;
	
	@Column(name = "cropId")
	int cropId;
	
	public Integer getStageId() {
		return stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
