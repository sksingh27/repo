package com.bkc.bean;

public class NcdexBean {
	
	String month;
	String value;
	String change;
	String source;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value+" : "+String.valueOf(change);
	}
	
}
