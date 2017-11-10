package com.bkc.bean.buyerseller;

public class CategoryBean {
	
	String categoryName;
	int categoryId;
	int color;
	boolean nextLink;
	
	public boolean isNextLink() {
		return nextLink;
	}
	public void setNextLink(boolean nextLink) {
		this.nextLink = nextLink;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	

}
