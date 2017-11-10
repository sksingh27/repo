/**
 * 
 */
package com.bkc.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.bkc.model.ShopType;

/**
 * @author Akash
 *
 * @Date 04-Oct-2017
 */
public class AddShopBean {

	String name;
	String shopName;
	String phoneNo;
	int stationId;
	String description;
	String address;
	String imageName;
    List<String> shopType;
  

	public List<String> getShopType() {
	return shopType;
}

public void setShopType(List<String> shopType) {
	this.shopType = shopType;
}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
