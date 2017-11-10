package com.bkc.model.buyerseller;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CategorySubcategoryDetailsId implements Serializable {

	@Column(name="categoryId")
	int catId;
	@Column(name="subCatId")
	int subCatId;
	@Column(name="languageId")
	String languageId;
	
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}
	
	
}
