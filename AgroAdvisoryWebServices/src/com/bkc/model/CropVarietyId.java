/**
 * 
 */
package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Akash
 *
 * @Date 30-Oct-2017
 */
@Embeddable
public class CropVarietyId implements Serializable{

	@Column(name="cropId")
	int cropId;
	@Column(name="languageId")
	String languageId;
	@Column(name="varietyId")
	int varietyId;
	
	
	public int getCropId() {
		return cropId;
	}
	public void setCropId(int cropId) {
		this.cropId = cropId;
	}
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public int getVarietyId() {
		return varietyId;
	}
	public void setVarietyId(int varietyId) {
		this.varietyId = varietyId;
	}
	
	
	
	
}
