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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Akash
 *
 * @Date 16-Oct-2017
 */
@Table(name="Disease",catalog="DiseaseDiagnosis")
@Entity
public class DiseaseDiagnosis {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int diseaseId;
	@Column(name="diseaseName")
	String diseaseName;
	@Column(name="languageId",nullable=false)
	String laguageId;
	@Column(name="imageName")
	String imageName;
	@ManyToOne
	@JoinColumn(name="cropId",nullable=false)
	DiseaseCrops crop;
	
	
	@OneToMany(mappedBy="disease")
    Set<DiseaseSymptoms> symptoms;
	@OneToMany(mappedBy="disease")
	Set<DiseaseControlMeasure> controlMeasures;
	
	
	public int getDiseaseId() {
		return diseaseId;
	}
	public void setDiseaseId(int diseaseId) {
		this.diseaseId = diseaseId;
	}
	public String getDiseaseName() {
		return diseaseName;
	}
	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	public String getLaguageId() {
		return laguageId;
	}
	public void setLaguageId(String laguageId) {
		this.laguageId = laguageId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public DiseaseCrops getCrop() {
		return crop;
	}
	public void setCrop(DiseaseCrops crop) {
		this.crop = crop;
	}
	public Set<DiseaseSymptoms> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(Set<DiseaseSymptoms> symptoms) {
		this.symptoms = symptoms;
	}
	public Set<DiseaseControlMeasure> getControlMeasures() {
		return controlMeasures;
	}
	public void setControlMeasures(Set<DiseaseControlMeasure> controlMeasures) {
		this.controlMeasures = controlMeasures;
	}
	
}
