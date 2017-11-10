package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Table(name="MandiArrival")
@Entity
public class Arrival {
	@EmbeddedId
	ArrivalId id;
	@Column(name="maxArrival")
	String Arrival;
	public ArrivalId getId() {
		return id;
	}
	public void setId(ArrivalId id) {
		this.id = id;
	}
	public String getArrival() {
		return Arrival;
	}
	public void setArrival(String arrival) {
		Arrival = arrival;
	}
	
	
	

}
