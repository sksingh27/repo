package com.bkc.model;
// default package
// Generated 20 Apr, 2017 10:58:25 AM by Hibernate Tools 5.2.1.Final

import java.beans.Transient;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserProfile generated by hbm2java
 */
@Entity
@Table(name = "userProfile", catalog = "agroMgnt")
public class UserProfile implements java.io.Serializable {
    @Id
    @Column(name="phoneNo")
	private String phoneNo;
	private String name;
	private Date creationTime;
	private Date lastUpdatedTime;
	private int stationId;
	@Column(name="aadharNo")
	private String aadharNo;
	public UserProfile() {
	}






	public String getPhoneNo() {
		return phoneNo;
	}






	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}






	public String getAadharNo() {
		return aadharNo;
	}






	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}






	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	
	@Column(name = "creationTime", nullable = false, length = 19)
	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastUpdatedTime", nullable = false, length = 19)
	public Date getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	@Column(name = "stationId", nullable = false)
	public int getStationId() {
		return this.stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
}
