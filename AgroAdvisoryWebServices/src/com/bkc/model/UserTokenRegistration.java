package com.bkc.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userTokenRegistration")
public class UserTokenRegistration implements Serializable{
		
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "phoneNo", length = 10)
	String phoneNo;
	
	@Column(name = "tokenNo", length = 200)
	String tokenNo;
	
	@Column(name = "date", length = 200)
	String date;
	

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getTokenNo() {
		return tokenNo;
	}

	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

	
	

}
