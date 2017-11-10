package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
@Table(name="districtDetails")
public class DistrictDetailsId implements Serializable{
	  
	
	  @Column(name="id") 
	  int id;    
	
	    @Column(name="sId")
	    int sId;
	    
	    @Column(name="districtId")
	    int districtId;
	    
	    public int getsId() {
			return sId;
		}
		public void setsId(int sId) {
			this.sId = sId;
		}
		
		public int getDistrictId() {
			return districtId;
		}
		public void setDistrictId(int districtId) {
			this.districtId = districtId;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
}
