package com.bkc.advisory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import com.bkc.bean.RainfallBean;
import com.bkc.service.AdvisoryService;

public class FourDaysForecastCallable implements Callable<String> {
	AdvisoryService advisoryService;
	float lat;
	float lon;
	String languageId;

	public FourDaysForecastCallable(AdvisoryService advisoryService, float lat, float lon, String languageId) {
		super();
		this.advisoryService = advisoryService;
		this.lat = lat;
		this.lon = lon;
		this.languageId = languageId;
	}

	@Override
	public String call() throws Exception {
      Thread.currentThread().setName("forecast-thread");
		//System.out.println("----------- four days forecast thread------------- "+Thread.currentThread().getName());
		return generateFourDaysForecast(lat, lon, languageId);
	}

	public String generateFourDaysForecast(float lat, float lon, String languageId) throws Exception {
		String fourDaysForecast;
		String maxTempPrefix;
		String maxTempSuffix;
		String maxTempAuxilary;
		String minTempPrefix;
		String minTempSuffix;
		if (languageId.equals("en")) {
			maxTempPrefix = "In coming 4 days maximum temperature will range from ";
			maxTempAuxilary = " to ";
			maxTempSuffix = " degree celcius ";
			minTempPrefix = "and minimum temperature will approximately be ";
			minTempSuffix = " degree celcius.";
		} else {
			maxTempPrefix = " आने वाले 4 दिनों में दिन का तापमान  ";
			maxTempAuxilary = " से ";
			maxTempSuffix = " डिग्री रहने कि संभावना है, ";
			minTempPrefix = " और  रात का तापमान  ";
			minTempSuffix = " डिग्री रहने  कि संभावना है। ";
		}
		List<RainfallBean> finalRflist = advisoryService.rf4Days(lat, lon);
		String rfFocreast = getRainfallAdvisory(finalRflist, languageId); // generating
		// rainfall
		// advisory
		HashMap<String, Float> forecastList = advisoryService.get4DaysForecast(lat, lon);
		List<RainfallBean> rhList = advisoryService.rh4Days(lat, lon);
		String rHForecast = getRhAdvisory(rhList, languageId);
		String maxTempForcast = maxTempPrefix + Math.round(forecastList.get("maxTempMin")) + maxTempAuxilary
				+ Math.round(forecastList.get("maxTempMax")) + maxTempSuffix;
		String minTempForecast = minTempPrefix + Math.round(forecastList.get("minTempMin")) + minTempSuffix;

		fourDaysForecast = rfFocreast + "  " + rHForecast + " " + maxTempForcast + " " + minTempForecast;
		return fourDaysForecast;
	}

	public String getRainfallAdvisory(List<RainfallBean> finalRfList, String languageId) {

		String rfDates = "";
		String rfFocreast = "";
		String month;
		try {
			month = finalRfList.size() > 0 ? new SimpleDateFormat("MMM")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(finalRfList.get(0).getDate())) + " " : "";
		} catch (ParseException e) {

			e.printStackTrace();
			month = "";
		}
		for (RainfallBean bean : finalRfList) {

			if (bean.getRainfall() > 10) {

			//	System.out.println("rainfall-------------------in 4 days check..........!!" + bean.getRainfall()		+ ":date is -->" + bean.getDate());

				rfDates = rfDates + "," + bean.getDate().substring(8, 10);
				System.out.println(rfDates);
			}

		}

		if (!rfDates.isEmpty()) {

			rfDates = rfDates.substring(1);
			if (languageId.equals("en")) {
				rfFocreast = "There is possibility of rain on " + month + rfDates + ".";
			} else {
				rfFocreast = rfDates + " तारीख को बारिश होने कि संभावना है।";
			}
		}

		return rfFocreast;
	}

	public String getRhAdvisory(List<RainfallBean> rhList, String languageId) {
		String rHdates = "";
		String rHForecast = "";
		String month;
		try {
			month = rhList.size() > 0 ? new SimpleDateFormat("MMM")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(rhList.get(0).getDate())) + " " : "";
		} catch (ParseException e) {

			e.printStackTrace();
			month = "";
		}
		for (RainfallBean bean : rhList) {
			if (bean.getRainfall() >= 70) {
				rHdates = "," + bean.getDate().substring(8, 10);

			}
		}

		if (!rHdates.isEmpty()) {
		//	System.out.println("rh is not empty");
			rHdates = rHdates.substring(1);
			if (languageId.equals("en")) {
				rHForecast = "Relative humidity will be high on " + month + rHdates + ".";
			} else {
				rHForecast = rHdates + " तारीख को उमस  ज्यादा  रहेगी।";
			}

		}
		return rHForecast;
	}

}
