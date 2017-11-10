package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="buyerSellerDetails")
public class BuyerSeller implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;	
	@Column(name="name")
	private String name;
	@Column(name="contact")
    private String contact;
	@Column(name="email")
    private String email;
	@Column(name="place")
    private int place;
	@Column(name="buyer_seller")
    private String buyer_seller;
	@Column(name="description")
    private String desc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public String getBuyer_seller() {
		return buyer_seller;
	}
	public void setBuyer_seller(String buyer_seller) {
		this.buyer_seller = buyer_seller;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
