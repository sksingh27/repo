/**
 * 
 */
package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Akash
 *
 * @Date 30-Oct-2017
 */
@Entity
@Table(name="Crop_Variety")
public class CropVariety {
	@EmbeddedId
	CropVarietyId id;
	@Column(name="varietyName")
    String varietyName;
	public CropVarietyId getId() {
		return id;
	}
	public void setId(CropVarietyId id) {
		this.id = id;
	}
	public String getVarietyName() {
		return varietyName;
	}
	public void setVarietyName(String varietyName) {
		this.varietyName = varietyName;
	}
	
	
}
