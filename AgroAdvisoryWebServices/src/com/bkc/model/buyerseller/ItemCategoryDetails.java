package com.bkc.model.buyerseller;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="itemCategoryDetails")
public class ItemCategoryDetails implements Serializable{

	@EmbeddedId
	ItemCategoryDetailsId id;
	@Column(name="itemName")
	String itemName;
	@Column(name="itemHindiName")
	String itemNameHindi;
	@Column(name="categorName")
	String categoryName;
	@Column(name="categoryHindiName")
	String categoryNameHindi;
	@Column(name="color")
	int color;
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public ItemCategoryDetailsId getId() {
		return id;
	}
	public void setId(ItemCategoryDetailsId id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNameHindi() {
		return itemNameHindi;
	}
	public void setItemNameHindi(String itemNameHindi) {
		this.itemNameHindi = itemNameHindi;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryNameHindi() {
		return categoryNameHindi;
	}
	public void setCategoryNameHindi(String categoryNameHindi) {
		this.categoryNameHindi = categoryNameHindi;
	}
	
	
}
