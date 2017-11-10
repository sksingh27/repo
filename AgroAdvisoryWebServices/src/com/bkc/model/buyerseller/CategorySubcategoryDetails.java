package com.bkc.model.buyerseller;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="catSubCatDetails")
public class CategorySubcategoryDetails {

	@EmbeddedId
	CategorySubcategoryDetailsId id;
	@Column(name="categoryName")
	String categoryName;
	@Column(name="subCatName")
	String subCategoryName;
	@Column(name="categoryHindiName")
	String catHindiName;
	@Column(name="subCatHindiName")
	String subCatHindiName;
	@Column(name="nextLink")
	boolean nextLink;
	
	
	
	
	public boolean isNextLink() {
		return nextLink;
	}
	public void setNextLink(boolean nextLink) {
		this.nextLink = nextLink;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	public String getCatHindiName() {
		return catHindiName;
	}
	public void setCatHindiName(String catHindiName) {
		this.catHindiName = catHindiName;
	}
	public String getSubCatHindiName() {
		return subCatHindiName;
	}
	public void setSubCatHindiName(String subCatHindiName) {
		this.subCatHindiName = subCatHindiName;
	}
	public CategorySubcategoryDetailsId getId() {
		return id;
	}
	public void setId(CategorySubcategoryDetailsId id) {
		this.id = id;
	}
	
	
	
}
