package com.bkc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Table(name="districtDetails")
@Entity
public class DistrictDetails {
	
	
	/*    @Id
	    @Column(name="id")
	    @GeneratedValue(strategy=GenerationType.AUTO)
		int id;*/
	   
	    
	    
	    
		
/*		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}*/
	     @EmbeddedId
	     DistrictDetailsId id;
	   
	     @Column(name="district")
	     String district;
	     
	    
	     
		public DistrictDetailsId getId() {
			return id;
		}
		public void setId(DistrictDetailsId id) {
			this.id = id;
		}
	
		public String getDistrict() {
			return district;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		
}
