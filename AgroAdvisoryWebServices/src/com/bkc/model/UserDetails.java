package com.bkc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "userDetails")

public class UserDetails {

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer userId;
	@Column(name = "emailId")
	String emailId;

	@Column(name = "userName")
	String userName;
	@Column(name = "creationTime")
	Date creationTime;
	@Column(name = "name", length = 200)
	String name;

	@Column(name = "phoneNo", length = 10)
	String phoneNo;
	
	@Column(name="password", length = 200)
	String password;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "addNewCrop", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "cropId") })
	List<CropDetails> cropDetails;

	public List<CropDetails> getCropDetails() {
		return cropDetails;
	}

	public void setCropDetails(List<CropDetails> cropDetails) {
		this.cropDetails = cropDetails;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

}
