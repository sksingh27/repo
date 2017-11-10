package com.bkc.advisory;

import java.util.List;
import java.util.concurrent.Callable;

import com.bkc.model.MgntPracticesHindi;
import com.bkc.service.AdvisoryService;

public class MgntPracticesAdvisoryCallable implements Callable<StringBuffer> {
	
	int cropId;
	String languageId;
	String phoneNo;
	int noOfDays;
	AdvisoryService advisoryService;
	
	
	public MgntPracticesAdvisoryCallable(int cropId, String languageId, String phoneNo, int noOfDays,
			AdvisoryService advisoryService) {
		super();
		this.cropId = cropId;
		this.languageId = languageId;
		this.phoneNo = phoneNo;
		this.noOfDays = noOfDays;
		this.advisoryService = advisoryService;
	}

	@Override
	public StringBuffer call() throws Exception {
		Thread.currentThread().setName("Mgnt-thread");
		//System.out.println("---------------Mgnt Practices advisory callable-------------- "+Thread.currentThread().getName());
		return generateMgntAdvisory(cropId, languageId, phoneNo, noOfDays);
	}
	
	public StringBuffer generateMgntAdvisory(int cropId, String languageId,String phoneNo,int noOfDays) throws Exception {
		List<MgntPracticesHindi> mgntPracticeList = advisoryService.getManagementPracticeHindi(cropId, "Common",languageId,phoneNo,noOfDays);
		StringBuffer mgntPractices = new StringBuffer();
		for (MgntPracticesHindi obj : mgntPracticeList) {
			mgntPractices.append(obj.getAdvisoryTest());
			mgntPractices.append(" ");

		}
       return mgntPractices;
	}


	

}
