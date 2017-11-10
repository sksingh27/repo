/**
 * 
 */
package com.bkc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.bean.DiseaseDiagnosisBean;
import com.bkc.bean.DiseaseDiagnosisControlAndSymptomsBean;
import com.bkc.bean.DiseaseDiagnosisCropBean;
import com.bkc.dao.DiseaseDiagnosisDao;

/**
 * @author Akash
 *
 * @Date 18-Oct-2017
 */
@Service
public class DiseaseDiagnosisServiceImpl implements DiseaseDiagnosisService 
{

	@Autowired
	DiseaseDiagnosisDao dao;
	
	@Override
	public List<DiseaseDiagnosisCropBean> getAllCrops(String languageId) {
		
		return dao.getAllCrops(languageId);
	}

	
	@Override
	public List<DiseaseDiagnosisBean> getAllDisease(int cropId, String languageId) {
		
		return dao.getAllDisease(cropId, languageId);
	}

	
	@Override
	public DiseaseDiagnosisControlAndSymptomsBean getControlAndSymptoms(int diseaseId, String languageId) {
		
		return dao.getControlAndSymptoms(diseaseId, languageId);
	}

	
	
}
