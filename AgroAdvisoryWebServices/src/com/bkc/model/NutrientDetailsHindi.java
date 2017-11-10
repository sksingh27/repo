package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Table(name="nutrientDetailsHindi")
@Entity
public class NutrientDetailsHindi {
	
	@Column(name="nutrientName")
	String nutrientName;
	@Column(name="requireStage")
	String requiredStage;
	@Column(name="noOfDays")
	int noOfDays;
	@Column(name="quantity")
	@EmbeddedId
	NutrientDetailsHindiId id;
	
	public NutrientDetailsHindiId getId() {
		return id;
	}
	public void setId(NutrientDetailsHindiId id) {
		this.id = id;
	}
	int quantity;
	public String getNutrientName() {
		return nutrientName;
	}
	public void setNutrientName(String nutrientName) {
		this.nutrientName = nutrientName;
	}
	public String getRequiredStage() {
		return requiredStage;
	}
	public void setRequiredStage(String requiredStage) {
		this.requiredStage = requiredStage;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
