package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "cropProduct")
@Entity
public class CropProduct {
	@EmbeddedId
	CropPorductId id;

	@Column(name = "value")
	String value;
	@Column(name = "xlDate")
	String xlDate;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getXlDate() {
		return xlDate;
	}

	public void setXlDate(String xlDate) {
		this.xlDate = xlDate;
	}

	public CropPorductId getId() {
		return id;
	}

	public void setId(CropPorductId id) {
		this.id = id;
	}

}
