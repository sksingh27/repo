/**
 * 
 */
package com.bkc.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Akash
 *
 * @Date 31-Oct-2017
 */
public class MandiRateHashMapBean {
	List<NcdexBean> ncdexBeanList;
	HashMap<String,List<MandiOilBean>> mandiOilBeanList;
	HashMap<String,List<MandiBean>> agmarkList;

	String mandiDescription;

	public MandiRateHashMapBean() {
		this.mandiDescription="No file found";
		this.mandiOilBeanList= new HashMap<>();
		this.ncdexBeanList= new ArrayList<>();
		this.agmarkList= new HashMap<>();
		}
	

	public String getMandiDescription() {
		return mandiDescription;
	}
	public void setMandiDescription(String mandiDescription) {
		this.mandiDescription = mandiDescription;
	}


	public List<NcdexBean> getNcdexBeanList() {
		return ncdexBeanList;
	}


	public void setNcdexBeanList(List<NcdexBean> ncdexBeanList) {
		this.ncdexBeanList = ncdexBeanList;
	}


	public HashMap<String, List<MandiOilBean>> getMandiOilBeanList() {
		return mandiOilBeanList;
	}


	public void setMandiOilBeanList(HashMap<String, List<MandiOilBean>> mandiOilBeanList) {
		this.mandiOilBeanList = mandiOilBeanList;
	}


	public HashMap<String, List<MandiBean>> getAgmarkList() {
		return agmarkList;
	}


	public void setAgmarkList(HashMap<String, List<MandiBean>> agmarkList) {
		this.agmarkList = agmarkList;
	}
	
	
}
