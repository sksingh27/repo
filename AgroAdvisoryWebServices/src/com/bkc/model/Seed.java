package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "MandiSeed")
@Entity
public class Seed {
    
	@EmbeddedId
	SeedId id;
	@Column(name = "crop")
	String crop;
	@Column(name = "value")
	String value;
	@Column(name = "xlDate")
	String xlDate;

	public SeedId getId() {
		return id;
	}

	public void setId(SeedId id) {
		this.id = id;
	}
	public String getCrop() {
		return crop;
	}

	public void setCrop(String crop) {
		this.crop = crop;
	}

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

}
