package com.bkc.model.buyerseller;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="rentItemDetails")
public class RentItemDetails {
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	int id;
    @Column(name="phoneNo")
    String phoneNo;
    @Column(name="stationId")
    int stationId;
    @Column(name="itemId")
    int itemId;
    @Column(name="categoryId")
    int categoryId;
    @Column(name="expectedPrice")
    String expectedPrice;
    @Column(name="itemDescribe")
    String itemDescription;
    @Column(name="imageName")
    String imageName;
    @Column(name="lat")
    float lat;
    @Column(name="lon")
    float lon;
    @Column(name="updatedTime")
    Date date;
    
    @Column(name="isRented")
    boolean rentStatus;
    @Column(name="approval")
    boolean approval;
    
    
    
    public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	@Column(name="subCatId")
    int subCatId;
    
	public int getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public int getStationId() {
		return stationId;
	}
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getExpectedPrice() {
		return expectedPrice;
	}
	public void setExpectedPrice(String expectedPrice) {
		this.expectedPrice = expectedPrice;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean isRentStatus() {
		return rentStatus;
	}
	public void setRentStatus(boolean rentStatus) {
		this.rentStatus = rentStatus;
	}
    
    

}
