/**
 * 
 */
package com.bkc.bean;

import java.util.Set;

/**
 * @author Akash
 *
 * @Date 17-Oct-2017
 */
public class DiseaseDiagnosisControlAndSymptomsBean {
	
	Set<String> symptoms;
	Set<String> controlMeasures;
	public Set<String> getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(Set<String> symptoms) {
		this.symptoms = symptoms;
	}
	public Set<String> getControlMeasures() {
		return controlMeasures;
	}
	public void setControlMeasures(Set<String> controlMeasures) {
		this.controlMeasures = controlMeasures;
	}
	

}
