package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Table(name="tehsilVillageDetails")
@Embeddable
public class TehsilVillageDetailsNewId implements Serializable{
	
	  @Column(name="lat")
			float lat;
		    @Column(name="lon")
			float lon;
		    
		    public float getLat() {
				return lat;
			}
			public void setLat(float lat) {
				this.lat = lat;
			}
			public float getLon() {
				return lon;
			}
			public void setLon(float lon) {
				this.lon = lon;
			}

}
