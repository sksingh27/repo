package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="NCDEX")
@Entity
public class NCDEXnHapur {
    @EmbeddedId
	NCDEXnHapurId id;
    @Column(name="maxval")
	String value;
    @Column(name="changeval")
	String change;
   
	public NCDEXnHapurId getId() {
		return id;
	}
	public void setId(NCDEXnHapurId id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
    
	
	
}
