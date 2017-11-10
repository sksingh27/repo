package com.bkc.bean;

import java.util.ArrayList;
import java.util.List;

public class MandiRateBean {
	
	List<MandiOilBean> mandiOilBeanList;
	List<NcdexBean> ncdexBeanList;
	/*ArrivalBean arrivalBean;
	List<MandiOilBean> oilBeanList;*/
	//List<MandiOilBean> cropProductBeanList;
	List<MandiBean> agmarkList;
	public MandiRateBean() {
	this.mandiDescription="No file found";
	this.mandiOilBeanList= new ArrayList<>();
	this.ncdexBeanList= new ArrayList<>();
	this.agmarkList= new ArrayList<>();
	}
	public List<MandiBean> getAgmarkList() {
		return agmarkList;
	}
	public void setAgmarkList(List<MandiBean> agmarkList) {
		this.agmarkList = agmarkList;
	}
	public List<MandiOilBean> getMandiOilBeanList() {
		return mandiOilBeanList;
	}
	public void setMandiOilBeanList(List<MandiOilBean> mandiOilBeanList) {
		this.mandiOilBeanList = mandiOilBeanList;
	}
	public List<NcdexBean> getNcdexBeanList() {
		return ncdexBeanList;
	}
	public void setNcdexBeanList(List<NcdexBean> ncdexBeanList) {
		this.ncdexBeanList = ncdexBeanList;
	}
	
	String mandiDescription;


	public String getMandiDescription() {
		return mandiDescription;
	}
	public void setMandiDescription(String mandiDescription) {
		this.mandiDescription = mandiDescription;
	}
	
	

}
