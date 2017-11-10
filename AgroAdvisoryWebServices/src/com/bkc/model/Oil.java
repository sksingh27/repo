package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="oil")
@Entity
public class Oil {
    @EmbeddedId
	OilId id;
	@Column(name="xlDate")
    String xlDate;
	@Column(name="value")
    String value;
	public OilId getId() {
		return id;
	}
	public void setId(OilId id) {
		this.id = id;
	}
	public String getXlDate() {
		return xlDate;
	}
	public void setXlDate(String xlDate) {
		this.xlDate = xlDate;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
