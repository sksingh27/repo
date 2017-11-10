package com.bkc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;


/**
 * @author Vicky 
 */
@Embeddable
@Table(name="diseaseDetails")
public class DiseaseDetailsId implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;

	@Column(name="diseaseId")
	private Integer diseaseId;
	
	@Column(name="cropId")
	private Integer cropId;
	
	@Column(name="attackStage")
	private String attackStage;
	

	public String getAttackStage() {
		return attackStage;
	}

	public void setAttackStage(String attackStage) {
		this.attackStage = attackStage;
	}

	public Integer getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Integer diseaseId) {
		this.diseaseId = diseaseId;
	}

	public Integer getCropId() {
		return cropId;
	}

	public void setCropId(Integer cropId) {
		this.cropId = cropId;
	}
	
	

}
