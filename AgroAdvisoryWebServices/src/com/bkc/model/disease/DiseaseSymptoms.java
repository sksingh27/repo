/**
 * 
 */
package com.bkc.model.disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Akash
 *
 * @Date 16-Oct-2017
 */
@Table(name="Symptoms",catalog="DiseaseDiagnosis")
@Entity
public class DiseaseSymptoms {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	@Column(name="symptom")
	String symptom;
	@Column(name="languageId",nullable=false)
	String languageId;
	
	@ManyToOne
	@JoinColumn(name="diseaseId",nullable=false)
	DiseaseDiagnosis disease;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	public DiseaseDiagnosis getDisease() {
		return disease;
	}
	public void setDisease(DiseaseDiagnosis disease) {
		this.disease = disease;
	}
	
}
