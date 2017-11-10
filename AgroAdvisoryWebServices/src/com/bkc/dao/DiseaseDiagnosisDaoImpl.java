/**
 * 
 */
package com.bkc.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bkc.bean.CropBean;
import com.bkc.bean.DiseaseDiagnosisBean;
import com.bkc.bean.DiseaseDiagnosisControlAndSymptomsBean;
import com.bkc.bean.DiseaseDiagnosisCropBean;

/**
 * @author Akash
 *
 * @Date 17-Oct-2017
 */
@Repository
public class DiseaseDiagnosisDaoImpl implements DiseaseDiagnosisDao{

    @Autowired
    @Qualifier("diseaseDiagnosisSessionFactory")     	
	SessionFactory diseaseDiagnosisSessionFactory;
	
	@Override
	public List<DiseaseDiagnosisCropBean> getAllCrops(String languageId) {
		
		Session session=diseaseDiagnosisSessionFactory.getCurrentSession();
		List tempList=session.createQuery("select distinct cropId,cropName from DiseaseCrops where languageId =: languageId").setString("languageId", languageId).list();
		List<DiseaseDiagnosisCropBean> result= new ArrayList<>();
		for(Object obj:tempList){
			DiseaseDiagnosisCropBean bean= new DiseaseDiagnosisCropBean();
			Object[] arr=(Object[])obj;
			bean.setCropId(Integer.parseInt(arr[0].toString()));
			bean.setCropName(arr[1].toString());
			result.add(bean);
			
		}
		return result;
	}

	@Override
	public List<DiseaseDiagnosisBean> getAllDisease(int cropId, String languageId) {
		Session session=diseaseDiagnosisSessionFactory.getCurrentSession();
		List tempList=session.createQuery("select distinct diseaseId,diseaseName,imageName from DiseaseDiagnosis where languageId =: languageId and cropId =: cropId").setInteger("cropId", cropId).setString("languageId", languageId).list();
	    List<DiseaseDiagnosisBean> result= new ArrayList<>();
	    for(Object obj :tempList){
	    	Object[] arr= (Object[])obj;
	    	DiseaseDiagnosisBean bean= new DiseaseDiagnosisBean();
	    	bean.setDiseaseId(Integer.parseInt(String.valueOf(arr[0])));
	    	bean.setDiseaseName(String.valueOf(arr[1]));
	    	bean.setImageName(String.valueOf(arr[2]));
	    	result.add(bean);
	    }
		
		return result;
	}


	@Override
	public DiseaseDiagnosisControlAndSymptomsBean getControlAndSymptoms(int diseaseId, String languageId) {
		
		String hql ="select s.symptom,c.controlMeasure from DiseaseSymtoms as s,DiseaseControlMeasure as c where c.disease.diseaseId=:diseaseId and s.disease.diseaseId=:diseaseid and c.languageId=:languageId and s.languageId=:languageId";
		List tempList= diseaseDiagnosisSessionFactory.getCurrentSession().createQuery(hql).setInteger("diseaseId", diseaseId).setString("languageId", languageId).list();
		DiseaseDiagnosisControlAndSymptomsBean bean= new DiseaseDiagnosisControlAndSymptomsBean();
		Set<String> symptoms= new HashSet<>();
		Set<String> control= new HashSet<>();
		for(Object obj:tempList){
			Object[] arr=(Object[])obj;
			symptoms.add(String.valueOf(arr[0]));
			control.add(String.valueOf(arr[1]));
		}
		bean.setControlMeasures(control);
		bean.setSymptoms(symptoms);
		return bean;
	}

}
