/**
 * 
 */
package com.bkc.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Akash
 *
 * @Date 04-Oct-2017
 */

@Table(name="addNearestShop")
@Entity
public class AddShopModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	@Column(name="name")
	String name;
	@Column(name="shopName")
	String shopName;
	@Column(name="phoneNo")
	String phoneNo;
	@Column(name="stationId")
	int stationId;
	@Column(name="shopDesc")
	String description;
	@Column(name="address")
	String address;
	@Column(name="imgName")
	String imageName;
	@Column(name="lat")
	float lat;
	@Column(name="lon")
	float lon;
	@Column(name="verified")
	boolean verified;
	
	@JsonDeserialize(using=MyEnumDeserialize.class)
	@ElementCollection(targetClass=ShopType.class)
	@CollectionTable(name="shop_type",joinColumns=@JoinColumn(name="id"))
	@Column(name="type",nullable=false)
	@Enumerated(EnumType.STRING)
	Set<ShopType> type;
	
	
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public Set<ShopType> getType() {
		return type;
	}
	public void setType(Set<ShopType> type) {
		this.type = type;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
	

}
