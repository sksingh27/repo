package com.bkc.advisory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import com.bkc.model.CropCalendar;
import com.bkc.model.CropDetails;
import com.bkc.service.AdvisoryService;

public class WeatherAdvisoryCallable implements Callable<String> {

	Date dc;
	float lat;
	float lon;
	int cropId;
	String phoneNo;
	String stage;
	CropDetails cc;
	String languageId;
	
	AdvisoryService advisoryService;
	
	
	public WeatherAdvisoryCallable(Date dc, float lat, float lon, int cropId, String phoneNo, String stage,CropDetails cc, String languageId, AdvisoryService advisoryService) {
		super();
		this.dc = dc;
		this.lat = lat;
		this.lon = lon;
		this.cropId = cropId;
		this.phoneNo = phoneNo;
		this.stage = stage;
		this.cc = cc;
		this.languageId = languageId;
		this.advisoryService = advisoryService;
	}

	@Override
	public String call() throws Exception {
		Thread.currentThread().setName("weather1-thread");
		//System.out.println("-----------------Weather advisory thread name--------------"+Thread.currentThread().getName());
		return generateWeatherAdvisory(dc, lat, lon, cropId, phoneNo, stage, cc, languageId);
	}

	public String generateWeatherAdvisory(Date dc, float lat, float lon, int cropId, String phoneNo, String stage,
			CropDetails cc,String languageId) throws Exception {
		HashMap<String, Float> aheadList = advisoryService.ahead7Days(dc, lat, lon);
		List<CropCalendar> cList = advisoryService.cropCalList(cropId, stage);
		HashMap<String, Float> li = advisoryService.calculateRainfallInStage(cropId, stage, lat, lon, phoneNo);
		CropCalendar tempObj = new CropCalendar();

		Float beforeRainfall = li.get("beforeRainfall");
		Float forecastRainfall = li.get("forecastRainfall");
		Float maxRh = aheadList.get("maxRh");
		float maxTemp = aheadList.get("maxTemp");
		//float minTemp = aheadList.get("minTemp");
		for (CropCalendar cd : cList) {
			//System.out.println("temp obj -----------------------------------------"+cd.getId().getSubStage());
			tempObj = cd;
		}
		//System.out.println(" maxt temp :-"+ maxTemp+ " required is"+tempObj.getMaxTempBetween2());
		//System.out.println(" max rh :-"+ maxRh+ " required is"+tempObj.getrHBetween2());
		//System.out.println(" rainfall ahead :-"+ forecastRainfall+ " rainfall between 1 "+tempObj.getRainfallBetween1() + " rainfall between 2 :-"+tempObj.getRadiationBetween2());
		

		StringBuilder agroAdvisory = new StringBuilder();
		if(languageId.equals("en")){
			
			if (cc.getCropSeason().equalsIgnoreCase("Kharif")) {
				// rainfall required
				if (tempObj.getRainfallBetween1() != 0 & tempObj.getRainfallBetween2() != 0) {

				//	System.out.println("rainfall is not zero");
					// if there is rainfall in forecast
					if (forecastRainfall > 5) {
				//		System.out.println("forecast rainfall is not zero............!!");
						if (beforeRainfall <= (tempObj.getRainfallBetween1() - 10)) {
						//	System.out.println(	"inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days.In case of water logging, pump out water from the field.Do not spray insecticides. ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory.append("There is possibility of rain in coming days.Do not spray insecticides.");
							} else if ((beforeRainfall + forecastRainfall) <= tempObj.getRainfallBetween1()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days. In case of dryness in field irrigation is required.");
							}
						} else if (beforeRainfall >= (tempObj.getRainfallBetween1() - 10)) {
						//	System.out.println("inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days.In case of water logging, pump out water from the field.Do not spray insecticides. ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory.append("There is possibility of rain in coming days. Do not spray insecticides.");
							}
						}
					}
					// if there is no rainfall in forecast
					if (forecastRainfall < 5) {
						if (beforeRainfall < tempObj.getRainfallBetween1()) {
							agroAdvisory.append(
									"There is no rainfall in forecast, irrigation and other field practices can pe done according to moisture in soil. ");
						}

					}

				}
				// no rainfall required
				else {
					System.out.println("ideal rainfall is zero ......!! forecast rainfall is :-" + forecastRainfall
							+ " : before rainfall is " + beforeRainfall);

					if (forecastRainfall >= 5) {
						if (forecastRainfall >= 5 && forecastRainfall < 15) {
							agroAdvisory.append(
									"Rainfall in forecast. Insects and diseases may attack, Proper arrangement must be done.");
						} else if (forecastRainfall >= 15 && forecastRainfall < 35) {
							agroAdvisory.append(
									"Slight rainfall in forecast. In case of water logging, pump out water from the field, this might affect the crop.");
						} else if (forecastRainfall >= 35 && forecastRainfall < 65) {
							agroAdvisory.append(
									"Moderate rainfall in forecast, In case of water logging, pump out water from the field, this might affect the crop.");
						} else if (forecastRainfall >= 65 && forecastRainfall < 125) {
							agroAdvisory.append(
									"Heavy rainfall in forecast,In case of water logging, pump out water from the field, this might affect the crop.");
						} else if (forecastRainfall >= 125) {
							agroAdvisory.append(
									"There is heavy rainfall in forecast, In case of water logging, pump out water from the field, this might affect the crop.");
						}

					} else if (forecastRainfall < 5) {
						if ((maxTemp > tempObj.getMinTempBetween1() & maxTemp <= tempObj.getMaxTempBetween2())
								&& (maxRh > tempObj.getrHBetween1() & maxRh <= tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"Forecast says weather is ideal from better crop growth.");
						} else if ((maxTemp > tempObj.getMinTempBetween1() & maxTemp <= tempObj.getMaxTempBetween2())
								&& (maxRh < tempObj.getrHBetween1())) {
							agroAdvisory.append(
									"No rainfall in forecast, relative humidity may decrease। Dieseases and insects may attack.");
						} else if ((maxTemp > tempObj.getMinTempBetween1() & maxTemp <= tempObj.getMaxTempBetween2())
								&& (maxRh > tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"No rainfall in forecast, relative humidity may increase, diseases may attack .Proper weeding must be done.");
						} else if ((maxTemp < tempObj.getMinTempBetween1())
								&& (maxRh > tempObj.getrHBetween1() & maxRh <= tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"No rainfall in forecast. There might be decrease in temperature. Weeding,irrigation and spraying of insecticides and pesticides can also be done.");
						} else if ((maxTemp < tempObj.getMinTempBetween1()) && (maxRh < tempObj.getrHBetween1())) {
							agroAdvisory.append(
									"No rainfall in forecast. Relative humidity with temperature may decrease. Weeding,irrigation and spraying of insecticides and pesticides can also be done.");
						} else if ((maxTemp < tempObj.getMinTempBetween1()) && (maxRh > tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"No rainfall in forecast. There can be decrease in temperature and increase in relative humidity. Diseases and insects may attack, proper arrangement must be done");
						} else if ((maxTemp > tempObj.getMinTempBetween2())
								&& (maxRh > tempObj.getrHBetween1() & maxRh <= tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"No rainfall in forecast. There is possibility of increase in temperature,moisture in soil must be controlled.");
						} else if ((maxTemp > tempObj.getMinTempBetween2()) && (maxRh < tempObj.getrHBetween1())) {
							agroAdvisory.append(
									"No rainfall in forecast. There may be increase in temperature and decrease in relative humidity, slight irrigation may be required.");
						} else if ((maxTemp > tempObj.getMinTempBetween2()) && (maxRh > tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"No rainfall in forecast. With increase in relative humidity ,temperature may also increase. Moisture in soil must be controlled.");
						}

					}

				}
			} else {
				/* rabi season */
				boolean rainFlag=true;
				
				if ((forecastRainfall) > 2.5 && (forecastRainfall) <= 7.5) {
					agroAdvisory.append(
							"Very light rainfall in forecast.");
				} else if ((forecastRainfall) > 7.6 && (forecastRainfall) <= 15.5) {
					agroAdvisory.append(
							"Moderate rainfall in forecast.");
				} else if ((forecastRainfall) > 15.6 && (forecastRainfall) <= 35.5) {
					rainFlag=false;
					agroAdvisory.append(
							"There is rainfall in forecast.");
				} else if ((forecastRainfall) > 35.6 && (forecastRainfall) <= 64.4) {
					rainFlag=false;
					agroAdvisory.append(
							"There is heavy rainfall in forecast. In case of water logging pump out water from the field else crop might get affected.");
				} else if ((forecastRainfall) > 64.5 && (forecastRainfall) <= 124.4) {
					rainFlag=false;
					agroAdvisory.append(
							"Heavy rainfall in forecast. In case of water logging pump out water from the field else crop might get affected.");
				} else if ((forecastRainfall) > 124.5 && (forecastRainfall) <= 244.4) {
					rainFlag=false;
					agroAdvisory.append(
							"Extreme rainfall in forecast. In case of water logging pump out water from the field else crop might get affected.");
				}

				
				if (maxTemp > tempObj.getMinTempBetween1() && maxTemp <= tempObj.getMaxTempBetween2()
						&& maxRh > tempObj.getrHBetween1() && maxRh < tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"The forecast says weather is ideal for crop.");
				} else if (maxTemp > tempObj.getMinTempBetween1() && maxTemp <= tempObj.getMaxTempBetween2()
						&& maxRh <= tempObj.getrHBetween1()) {

					agroAdvisory.append(
							"In coming days,relative humidity may decrease ,Weeding and other practices must be continued.");
				} else if (maxTemp > tempObj.getMinTempBetween1() && maxTemp <= tempObj.getMaxTempBetween2()
						&& maxRh >= tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"In coming days,relative humidity may increase. To avoid loss, slight irrigation  can be done. ");
				} else if (maxTemp < tempObj.getMinTempBetween1() && maxRh > tempObj.getrHBetween1()
						&& maxRh < tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"Temperature may decrease drastically , to protect crop from frost little irrigation must be done. ");
				} else if (maxTemp <= tempObj.getMinTempBetween1() && maxRh <= tempObj.getrHBetween1()) {
					agroAdvisory.append(
							"With decrease in temperature ,relative humidity may also decrease. Proper weeding and irrigation must be done before incests and diseases attack.");
				} else if (maxTemp < tempObj.getMinTempBetween1() && maxRh > tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"There can be decrease in temperature and increase in relative humidity. Diseases and insects may attack, to avoid loss spraying on insecticides and pesticides can be done. ");
				} else if (maxTemp > tempObj.getMaxTempBetween2() && maxRh > tempObj.getMaxTempBetween1()
						&& maxRh < tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"Temperature may increase in coming days. In case of dryness in soil ,irrigation can be done.");
				} else if (maxTemp > tempObj.getMaxTempBetween2() && maxRh < tempObj.getrHBetween1() && rainFlag) {
					
					
					agroAdvisory.append(
							"In coming days with increase in temperature there may be decrease in relative humidity. If the temperature is too high avoid spraying of insecticides and pesticides. ");

				} else if (maxTemp > tempObj.getMaxTempBetween2() && maxRh > tempObj.getrHBetween2() && rainFlag) {
					
					
					agroAdvisory.append("With increase in temperature , relative humidity may also increase. Weeding must be continued . ");
				}

				if (tempObj.getIrrigation() > 0 && rainFlag) {
					// agroAdvisory.append("फसल में सिंचाई की आवश्यकता है।
					// खेत में
					// पानी "+ tempObj.getIrrigation()+" मिलीमीटर प्रति
					// हेक्टेयर के
					// हिसाब से करे। ");
					agroAdvisory.append("Irrigation may be required.");
				}

				
			}
			
		}
		else {
			//hindi section.....!!
			if (cc.getCropSeason().equalsIgnoreCase("Kharif")) {
				//rainfall required
				if (tempObj.getRainfallBetween1() != 0 & tempObj.getRainfallBetween2() != 0) {

					System.out.println("rainfall is not zero");
					//if there is rainfall in forecast
					if (forecastRainfall > 5) {
						System.out.println("forecast rainfall is not zero............!!");
						if (beforeRainfall <= (tempObj.getRainfallBetween1() - 10)) {
							System.out.println(
									"inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"आगामी दिनों में बारिश की संभावना है। जल भराव की स्थिति पर जल निकासी का प्रबन्ध करे। उर्वरको और कीटनाशको का उपयोग स्थगित करे। ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"आगे बारिश की संभावना है।  कीटनाशको का और उर्वरको का छिड़काव न करे।");
							} else if ((beforeRainfall + forecastRainfall) <= tempObj.getRainfallBetween1()) {
								agroAdvisory.append(
										"आगामी दिनों में बारिश की संभावना है। आवश्यकता पड़ने पर हल्की सिंचाई कर सकते है।");
							}
						} else if (beforeRainfall >= (tempObj.getRainfallBetween1() - 10) ) {
						//	System.out.println("inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"आगामी दिनों में बारिश की संभावना है। जल भराव की स्थिति पर जल निकासी का प्रबन्ध करे। उर्वरको और कीटनाशको का उपयोग स्थगित करे। ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"आगे बारिश की संभावना है।  कीटनाशको का और उर्वरको का छिड़काव न करे।");
							} 
						}
					}
					//if there is no rainfall in forecast
					if (forecastRainfall < 5) {
						if (beforeRainfall < tempObj.getRainfallBetween1()) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है, खेत में मृदा के नमी के अनुसार सिंचाई करे व आवश्यकता पड़ने पर खेत के अन्य काम भी कर सकते है। ");
						}

					}

				}
                //no rainfall required
				else {
				//	System.out.println("ideal rainfall is zero ......!! forecast rainfall is :-" + forecastRainfall		+ " : before rainfall is " + beforeRainfall);
                    
					if (forecastRainfall >= 5) {
						if (forecastRainfall >= 5 && forecastRainfall < 15) {
							agroAdvisory.append(
									"आने वाले दिनों में बारिश की संभावना है। किट व बीमारी का आक्रमण हो सकता है, समय पर उचित प्रबंध करे।");
						} else if (forecastRainfall >= 15 && forecastRainfall < 35) {
							agroAdvisory.append(
									"आने वाले दिनों में मध्यम बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे। नहीं तो फसल ख़राब हो सकती है।");
						} else if (forecastRainfall >= 35 && forecastRainfall < 65) {
							agroAdvisory.append(
									"आने वाले दिनों में तेज बारिश की संभावना है।	जल जमाव होने पर जल निकासी की उचित व्यवस्था करे। नहीं तो फसल ख़राब हो सकती है।");
						} else if (forecastRainfall >= 65 && forecastRainfall < 125) {
							agroAdvisory.append(
									"आने वाले दिनों में बहुत भारी बारिश की संभावना है।	जल जमाव होने पर जल निकासी की उचित व्यवस्था करे। नहीं तो फसल ख़राब हो सकती है।");
						} else if (forecastRainfall >= 125) {
							agroAdvisory.append(
									"आने वाले दिनों में मूसलाधार बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे। नहीं तो फसल ख़राब हो सकती है।");
						}

					} else if (forecastRainfall < 5) {
						if ((maxTemp > tempObj.getMinTempBetween1() & maxTemp <= tempObj.getMaxTempBetween2())
								&& (maxRh > tempObj.getrHBetween1() & maxRh <= tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"आगामी दिनों का मौसम फसल के अनुकूल रहने की संभावना है, जो की अच्छे उत्पादन के अनुकूल है।");
						} else if ((maxTemp > tempObj.getMinTempBetween1()
								& maxTemp <= tempObj.getMaxTempBetween2()) && (maxRh < tempObj.getrHBetween1())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है, हवा में नमी कम हो सकती है। बीमारी और कीटो का प्रकोप हो सकता है, नियंत्रण हेतु उचित प्रबंध करे।");
						} else if ((maxTemp > tempObj.getMinTempBetween1()
								& maxTemp <= tempObj.getMaxTempBetween2()) && (maxRh > tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है, हवा में नमी सामान्य से अधिक होने की संभावना है, बीमारी का प्रकोप हो सकता है, नियंत्रण हेतु उचित प्रबंध करे। खेत में निराई - गुड़ाई करे और डोरा या कुल्पा चलाये।");
						} else if ((maxTemp < tempObj.getMinTempBetween1())
								&& (maxRh > tempObj.getrHBetween1() & maxRh <= tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है। तापमान सामान्य से कम हो सकता है, मौसम के अनुसार खेतो में अपना काम कर सकते है, जैसे सिंचाई, निराई गुड़ाई या डोरा कुल्पा या फिर उर्वरको व कीटनाशको का उपयोग कर सकते है।");
						} else if ((maxTemp < tempObj.getMinTempBetween1()) && (maxRh < tempObj.getrHBetween1())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है। तापमान के साथ हवा में नमी भी सामान्य से कम होने की संभावना है, मौसम के अनुसार खेतो में अपना काम कर सकते है, जैसे सिंचाई, निराई गुड़ाई या डोरा कुल्पा या फिर उर्वरको व कीटनाशको का उपयोग कर सकते है।");
						} else if ((maxTemp < tempObj.getMinTempBetween1()) && (maxRh > tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है। तापमान सामान्य से कम व हवा में नमी अधिक होने की संभावना है, बीमारी और कीटो का प्रकोप हो सकता है, उचित प्रबंध करे।");
						} else if ((maxTemp > tempObj.getMinTempBetween2())
								&& (maxRh > tempObj.getrHBetween1() & maxRh <= tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है। तापमान सामान्य से अधिक रहेगा, मृदा में नमी बनाये रखे।");
						} else if ((maxTemp > tempObj.getMinTempBetween2()) && (maxRh < tempObj.getrHBetween1())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है। तापमान सामान्य से अधिक व हवा में नमी कम होने की संभावना है, आवश्यकता पड़ने पर हल्की सिंचाई करे।");
						} else if ((maxTemp > tempObj.getMinTempBetween2()) && (maxRh > tempObj.getrHBetween2())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है। तापमान के साथ हवा में नमी भी सामान्य से अधिक होने की संभावना है, मृदा में नमी बनाये रखे व बीमारी और कीटो का प्रकोप हो सकता है, नियंत्रण हेतु उचित प्रबंध करे।");
						}

					}
					

				}
			} else {

				/* rabi season */
				
				boolean rainFlag=true;
				
				if ((forecastRainfall) > 2.5 && (forecastRainfall) <= 7.5) {
					agroAdvisory.append(
							"आने वाले दिनों में हल्की बौछारो के साथ बारिश की संभावना है। किट व बीमारी का आक्रमण हो सकता है, समय पर उचित प्रबंध करे।");
				} else if ((forecastRainfall) > 7.6 && (forecastRainfall) <= 15.5) {
					agroAdvisory.append(
							"आने वाले दिनों में मध्यम बारिश की संभावना है। किट व बीमारी का आक्रमण हो सकता है, समय पर उचित प्रबंध करे।");
				} else if ((forecastRainfall) > 15.6 && (forecastRainfall) <= 35.5) {
					agroAdvisory.append(
							"आने वाले दिनों में बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे। नहीं तो फसल ख़राब हो सकती है।");
				} else if ((forecastRainfall) > 35.6 && (forecastRainfall) <= 64.4) {
					rainFlag=false;
					agroAdvisory.append(
							"आने वाले दिनों में तेज बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे, नहीं तो फसल ख़राब हो सकती है।");
				} else if ((forecastRainfall) > 64.5 && (forecastRainfall) <= 124.4) {
					rainFlag=false;
					agroAdvisory.append(
							"आने वाले दिनों में बहुत भारी बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे, नहीं तो फसल ख़राब हो सकती है।");
				} else if ((forecastRainfall) > 124.5 && (forecastRainfall) <= 244.4) {
					
					rainFlag=false;agroAdvisory.append(
							"आने वाले दिनों में मूसलाधार बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे। नहीं तो फसल ख़राब हो सकती है।");
				}
				
				if (maxTemp > tempObj.getMinTempBetween1() && maxTemp <= tempObj.getMaxTempBetween2()
						&& maxRh > tempObj.getrHBetween1() && maxRh < tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में मौसम फसल के लिए अनुकूल रहने की सम्भावना है। यह मौसम अच्छे पैदावार के अनुकूल है। ");
				} else if (maxTemp > tempObj.getMinTempBetween1() && maxTemp <= tempObj.getMaxTempBetween2()
						&& maxRh <= tempObj.getrHBetween1() && rainFlag) {

					agroAdvisory.append(
							"आने वाले दिनों में हवा में नमी कम होने की सम्भावना है। खरपतवार नियंत्रण हेतु खेतो में निराई-गुड़ाई जारी रखे। ");
				} else if (maxTemp > tempObj.getMinTempBetween1() && maxTemp <= tempObj.getMaxTempBetween2()
						&& maxRh >= tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में हवा में नमी ज्यादा होने की सम्भावना है। नुकसान से बचने के लिए खेत में हल्की सिंचाई करे। ");
				} else if (maxTemp < tempObj.getMinTempBetween1() && maxRh > tempObj.getrHBetween1()
						&& maxRh < tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में तापमान कम होने की सम्भवना। फसल को पाले (अधिक ठण्ड) से बचाने के लिए खेत में हल्की सिंचाई करे। ");
				} else if (maxTemp <= tempObj.getMinTempBetween1() && maxRh <= tempObj.getrHBetween1() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में तापमान के साथ हवा में नमी भी कम होने की संभावना है। आक्रमण से पहले खेतो में निराई-गुड़ाई कर, हल्की सिंचाई करे। ");
				} else if (maxTemp < tempObj.getMinTempBetween1() && maxRh > tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में तापमान कम होने के साथ हवा में नमी बढ़ने की सम्भवना है। फसल में बीमारी के आक्रमण की संभावना है। नुकसान से बचने के लिए बीमारी लगने पर खेत में दवाई का छिड़काव करे फिर हल्की सिंचाई भी करे। ");
				} else if (maxTemp > tempObj.getMaxTempBetween2() && maxRh > tempObj.getMaxTempBetween1()
						&& maxRh < tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में मौसम का तापमान सामान्य से ज्यादा होने की सम्भावना है। मिट्टी में सूखे की स्थिति पर हल्की सिंचाई करे। तापमान ज्यादा हो तो बीमारी व कीटो का उचित प्रबंध करे। ");
				} else if (maxTemp > tempObj.getMaxTempBetween2() && maxRh < tempObj.getrHBetween1() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में मौसम का तापमान सामान्य से ज्यादा होने के साथ हवा में नमी के कम होने की सम्भावना है। यदि तापमान में सामान्य से ज्यादा का अंतर हो तो सिचाई करें व किसी भी दवाई का छिड़काव न करें। ");

				} else if (maxTemp > tempObj.getMaxTempBetween2() && maxRh > tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append(
							"आने वाले दिनों में मौसम में तापमान के साथ हवा में नमी भी ज्यादा होने की संभावना। ऐसी स्थिति में खरपतवार पर नियंत्रण करे और साथ ही मृदा में नमी बनाए रखने के लिए हल्की सिंचाई करे। ");
				}

				if (tempObj.getIrrigation() > 0 && rainFlag) {
					// agroAdvisory.append("फसल में सिंचाई की आवश्यकता है।
					// खेत में
					// पानी "+ tempObj.getIrrigation()+" मिलीमीटर प्रति
					// हेक्टेयर के
					// हिसाब से करे। ");
					agroAdvisory.append("फसल में सिंचाई की आवश्यकता है। ");
				}

				
			}
		}
		
		return agroAdvisory.toString();
	}
	
}
