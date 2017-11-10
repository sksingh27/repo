package com.bkc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="stateDetails")
public class StateDetails {
    @Id
    @Column(name="stateId")
    @GeneratedValue(strategy=GenerationType.AUTO)
	int id;
    
/*    @OneToMany(mappedBy="id.sId",targetEntity=DistrictDetails.class,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    List<DistrictDetails> districts;
    */
    
    @Column(name="state")
	String state;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
/*	public List<DistrictDetails> getDistricts() {
		return districts;
	}
	public void setDistricts(List<DistrictDetails> districts) {
		this.districts = districts;
	}
	*/

	
	
	
	
}
