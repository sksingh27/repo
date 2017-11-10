package com.bkc.advisory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import com.bkc.model.DiseaseDetailsHindi;
import com.bkc.model.GridWiseCola;
import com.bkc.service.AdvisoryService;


public class DiseaseAdvisoryCallable implements Callable<Map<String, Object>> {

	AdvisoryService advisoryService;
	float lat;
	float lon;
	Date dc;
	String stage;
	int cropId;
	String languageId;

	public DiseaseAdvisoryCallable(float lat, float lon, Date dc, String stage, int cropId, String languageId,
			AdvisoryService advisoryService) {
		this.lat = lat;
		this.lon = lon;
		this.dc = dc;
		this.stage = stage;
		this.cropId = cropId;
		this.languageId = languageId;
		this.advisoryService = advisoryService;

	}

	@Override
	public Map<String, Object> call() throws Exception {
		Thread.currentThread().setName("disease-thread");
		//System.out.println("-----------------disease advisory thread name--------------"+Thread.currentThread().getName());     
		return generateDiseaseAdvisory();
		//return new HashMap<>();
	}

	public Map<String, Object> generateDiseaseAdvisory() {

	//	System.out.println(	"....................................inside generate disease advisory...........................!!!!");
	//	System.out.println("stage is " + stage);
		String symptomPrefix;
		String symptomPostfisx;
		List<String> diseaseList;
		List<GridWiseCola> ahdList;
		List<DiseaseDetailsHindi> ds;
		diseaseList = new ArrayList<String>();
		try {
			ahdList = advisoryService.sevenDaysAhead(lat, lon, dc);

		} catch (Exception e) {
			ahdList = new ArrayList<>();
			e.printStackTrace();
		}

		if (languageId.equals("en")) {
			symptomPrefix = "- it's symptoms are,";
			symptomPostfisx = " might occur.";

		} else if (languageId.equals("hi")) {
			symptomPrefix = "- इसके लक्षण है, ";
			symptomPostfisx = "होने की संभावना है।";

		} else {
			symptomPrefix = "- इसके लक्षण है, ";
			symptomPostfisx = "होने की संभावना है।";
		}

		try {
			ds = advisoryService.ds(stage, cropId, languageId);
		} catch (Exception e) {
			ds = new ArrayList<>();
			e.printStackTrace();
		}
		String dName = "";
		StringBuilder st = new StringBuilder("");

		StringBuilder discontrol = new StringBuilder();
		Map<String, Object> disease = new HashMap<>();
		for (DiseaseDetailsHindi dt : ds) {
			System.out.println(dt.getDieaseName() + "-------------!!!!!");
			if (dt.getDays_between1() != dt.getDays_between2()) {
				//System.out.println("when disease days are not same !!");
				int c = 0;

				for (GridWiseCola gcd : ahdList) {

					System.out.println("forDate:-" + gcd.getId().getF_date());

					if (((dt.getMinTempBetween1() != 0.0 && dt.getMinTempBetween2() != 0.0)
							|| dt.getMaxTempGreaterThan() != 0 || dt.getMaxTempLessThan() != 0)
							&& dt.getrHGreaterThan() == 0.0 && dt.getrHBetween1() == 0.0) {
						System.out.println("rh is :" + dt.getrHBetween1() + ".. " + dt.getrHBetween2());
						System.out.println("rh is greaterthan -:" + dt.getrHGreaterThan());
						if (gcd.getMax_temp() >= dt.getMinTempBetween1()
								&& gcd.getMax_temp() <= dt.getMinTempBetween2() + 1.0) // check
																						// has
																						// been
																						// reduced
																						// to
																						// 1
																						// degree
						{
							c = c + 1;
						//	System.out	.println("Inside if when only temperature is between min and max and counter is :-"	+ c + "maximum temp= " + gcd.getMax_temp());

						} else if (gcd.getMax_temp() > dt.getMaxTempGreaterThan()) {
							c = c + 1;
							//System.out.println("when temperature greater than occurs");
						} else if (gcd.getMax_temp() < dt.getMaxTempLessThan()) {
							c = c + 1;
						//	System.out.println("when temperature lessthan occurs");
						}

					}

					// when only rh is given.
					if (dt.getrHGreaterThan() != 0.0
							|| dt.getrHBetween1() != 0.0 && dt.getrHBetween2() != 0.0 && dt.getMinTempBetween1() == 0) {

						if (dt.getrHGreaterThan() != 0 && gcd.getMax_rh() >= dt.getrHGreaterThan() + 2) {
							c = c + 1;
						//	System.out.println("when only rh greater than occurs with counter:-" + c);
						} else if (dt.getrHBetween1() != 0 && gcd.getMax_rh() >= dt.getrHBetween1()
								&& gcd.getMax_rh() <= dt.getrHBetween2() + 3) {

							c = c + 1;
							//System.out.println("when rh bt1 and bt2 occurs with counter :-" + c);
						} else if (dt.getrHBetween1() == 0 & dt.getrHGreaterThan() == 0 & dt.getrHLessThan() != 0) {
							//System.out.println("when only rh less than is given ");
							if (gcd.getMax_rh() < dt.getrHLessThan()) {
								c = c + 1;
							}
						}
					}
					if (dt.getMinTempBetween1() != 0 && dt.getrHBetween1() != 0
							|| dt.getrHGreaterThan() != 0 && dt.getMinTempBetween1() != 0.0
							|| dt.getMaxTempGreaterThan() != 0 || dt.getMaxTempLessThan() != 0) {

						if (gcd.getMax_temp() >= dt.getMinTempBetween1()
								&& gcd.getMax_temp() <= dt.getMinTempBetween2() + 1
								|| gcd.getMax_rh() >= dt.getrHGreaterThan()
								|| gcd.getMax_rh() >= dt.getrHBetween1() && gcd.getMax_rh() <= dt.getrHBetween2() + 2
								|| gcd.getMax_rh() <= dt.getrHLessThan() + 2
								|| gcd.getMax_temp() > dt.getMaxTempGreaterThan() + 1
								|| gcd.getMax_temp() < dt.getMaxTempLessThan()) {
							c = c + 1;
						//	System.out.println("when both are present  with counter value :-" + c + "and max temp is :"		+ gcd.getMax_temp());
						}
					}

				}

				if (c >= dt.getDays_between1()) {

				//	System.out.println("days counter = " + c);
					st.append(dt.getDieaseName());
					dName = dName + "," + dt.getDieaseName();
					if (!dt.getSymptoms().equals("")) {
						String sympt = symptomPrefix;
						st.append(sympt + dt.getSymptoms() + " ");
						st.append(dt.getControlMeasure());
						// String name =
						// String.valueOf(dt.getId().getDiseaseId());

					}
					diseaseList.add(st.toString());

					st = new StringBuilder();
					discontrol.append(dt.getDieaseName() + " " + dt.getControlMeasure());
				}

				st.append("\n");
			}

			else {

				//System.out.println("days are same");
			}

		}
		if (dName != "") {
			dName = dName.substring(1) + symptomPostfisx;
		}

		disease.put("diseaseList", diseaseList);
		disease.put("dName", dName);
		//System.out.println(
			//	"....................................end generate disease advisory...........................!!!!");
		return disease;

	}
}
