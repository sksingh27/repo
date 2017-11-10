package com.bkc.bean;


/**
 * Created by dmdd on 5/9/17.
 */

public class BuyerSellerPojo {
    private String name;
    private String contact;
    private String email;
    private int place;
    private String buyer_seller;
    private String desc;


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
