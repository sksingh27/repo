package com.bkc.bean;

import java.io.Serializable;

public class UserRegisteredCropBean implements Serializable {
   
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String phoneNo;
	int cropId;
	
	String sowingdate;
	
	
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
	public String getSowingdate() {
		return sowingdate;
	}
	public void setSowingdate(String sowingdate) {
		this.sowingdate = sowingdate;
	}
	
	
	
	
}
