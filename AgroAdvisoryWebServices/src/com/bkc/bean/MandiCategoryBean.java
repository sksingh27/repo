package com.bkc.bean;

public class MandiCategoryBean {
	
	String hindiName;
	String englishName;
	public String getHindiName() {
		return hindiName;
	}
	public void setHindiName(String hindiName) {
		this.hindiName = hindiName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	@Override
	public boolean equals(Object obj) {
		System.out.println("calling overriden equals");
		MandiCategoryBean bean=(MandiCategoryBean)obj;
		if(this.getEnglishName().equals(bean.getEnglishName())){
			return true;
		}
		return false;
	}

}
