/**
 * 
 */
package com.bkc.model.disease;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Akash
 *
 * @Date 16-Oct-2017
 */
@Table(name="Disease_Crops",catalog="DiseaseDiagnosis")
@Entity
public class DiseaseCrops {

	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	int cropId;
	@Column(name="cropName")
	String cropName;
	@Column(name="languageId",nullable=false)
	String languageId;
	@OneToMany(mappedBy="crop")
	Set<DiseaseDiagnosis> diseases;
	
	public int getCropId() {
		return cropId;
	}
	public void setCropId(int cropId) {
		this.cropId = cropId;
	}
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public Set<DiseaseDiagnosis> getDiseases() {
		return diseases;
	}
	public void setDiseases(Set<DiseaseDiagnosis> diseases) {
		this.diseases = diseases;
	}
	
	
	
}
