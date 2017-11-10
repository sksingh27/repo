/**
 * 
 */
package com.bkc.dao;

import java.util.List;

import com.bkc.bean.DiseaseDiagnosisBean;
import com.bkc.bean.DiseaseDiagnosisControlAndSymptomsBean;
import com.bkc.bean.DiseaseDiagnosisCropBean;

/**
 * @author Akash
 *
 * @Date 17-Oct-2017
 */


public interface DiseaseDiagnosisDao {

	//list of all crops 
	public List<DiseaseDiagnosisCropBean> getAllCrops(String languageId);
	//list of all disease diagnosis bean on cropId
	public List<DiseaseDiagnosisBean> getAllDisease(int cropId,String languageId);
    //all control and symptoms bean
	public DiseaseDiagnosisControlAndSymptomsBean getControlAndSymptoms(int diseaseId,String languageId);
}
