package com.bkc.controller2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.advisory.DiseaseAdvisoryCallable;
import com.bkc.advisory.FourDaysForecastCallable;
import com.bkc.advisory.MgntPracticesAdvisoryCallable;
import com.bkc.advisory.WeatherAdvisoryCallable;
import com.bkc.bean.AgroAdvisoryBean;
import com.bkc.bean.RainfallBean;
import com.bkc.model.CropCalendar;
import com.bkc.model.CropDetails;
import com.bkc.model.DiseaseDetailsHindi;
import com.bkc.model.GridWiseCola;
import com.bkc.model.MgntPracticesHindi;
import com.bkc.model.NutrientDetailsHindi;
import com.bkc.service.AdvisoryService;
import com.bkc.service.CropDetailsService;
import com.bkc.service.ForecastService;
import com.bkc.service.StationDetailsService;
import com.bkc.service.UserDetailsService;

@Controller
@RequestMapping("/advisory2")
public class AdvisoryController2 {

	@Autowired(required = true)
	UserDetailsService userDetailsService;
	@Autowired
	AdvisoryService advisoryService;

	@Autowired
    ForecastService forecastService;

	@Autowired
	private StationDetailsService stationdService;

	@Autowired
	private CropDetailsService cropdService;

	
	
	/*
	 * @Autowired Callable<Map<String, Object>> diseaseMapCallable;
	 */
	@SuppressWarnings("unused")
	@GetMapping("/getAdvisory/{phoneNo}/{cropId}/{lat}/{lon}/{village}/{languageId}")
	public ResponseEntity<AgroAdvisoryBean> genAdvisoryApp(@PathVariable String phoneNo, @PathVariable int cropId,@PathVariable float lat,@PathVariable float lon,@PathVariable String village,@PathVariable String languageId,	HttpSession httpSession) {
        System.out.println("generating advisory for :-"+ phoneNo+"lat "+lat+" lon "+lon+" village "+village);
		
		
		AgroAdvisoryBean bean = new AgroAdvisoryBean();
		//System.out.println("generating advisory..................!!!!");
		try {
		//	String languageId = userDetailsService.getUserLanguage(phoneNo);
			bean = generateAdvisory(phoneNo, cropId, languageId,lat ,lon,village);
		} catch (Exception e) {
			bean.setNotFound("Not found");
			e.printStackTrace();
		}
		return new ResponseEntity<AgroAdvisoryBean>(bean, HttpStatus.OK);

	}

	public AgroAdvisoryBean generateAdvisory(String phoneNo, int cropId, String languageId,float lat,float lon,String village) throws Exception {

		//UserProfileRegistrationBean userProfile = userDetailsService.getUserProfileOnPhoneNo(phoneNo);
		//VillageBean villageBean = stationdService.getVillageOnId(userProfile.getVillageId());
		CropDetails cc = cropdService.getCropDetails(cropId);
		AgroAdvisoryBean adviosry = new AgroAdvisoryBean();
		adviosry.setCity(village);
		adviosry.setState("Not Found");
		//float lat = villageBean.getLat();
		//float lon = villageBean.getLon();
		Date dc = new Date();
		String stage = advisoryService.getList(cropId, phoneNo);
		//System.out.println("stage is --------->"+stage);
		//String cropStage = "";
		String cName;
		if (languageId.equals("en")) {
			cName = cc.getCropname();
		} else {
			cName = cc.getCropHindiName();
		}
		//System.out.println("after setting crop Name=------------!!" + cName);
		try {
			if (!stage.equals(":") && !stage.contains("Harvesting") && !stage.isEmpty()) {
				int noOfDays;
				try {
					noOfDays = advisoryService.getNoOfDaysFromUser(phoneNo, cropId);
				} catch (Exception e) {
					e.printStackTrace();
					noOfDays = 0;
				}
				System.out.println("no of days are"+noOfDays);
				ExecutorService executorService = Executors.newFixedThreadPool(2);

				String[] stageArray = stage.split(":");
				stage = stageArray[0];
				System.out.println("Stage is "+stage);
				//cropStage = stageArray[1];
				StringBuilder nutst = new StringBuilder();
				// Map<String, Object> diseaseMap = generateDiseaseAdvisory(lat,
				// lon, dc, stage, cropId, languageId);
				Map<String, Object> diseaseMap;
				String weatherAdvisory;
				String fourDaysForecast;
				StringBuffer mgntPractices;

				try {
					
			//		System.out.println("-----------inside executor try catch-----------------");
					Future<String> weatherAdvisoryFuture = executorService.submit(new WeatherAdvisoryCallable(dc, lat,lon, cropId, phoneNo, stage, cc, languageId, advisoryService));
					Callable<Map<String, Object>> diseaseMapCallable = new DiseaseAdvisoryCallable(lat, lon, dc, stage,
							cropId, languageId, advisoryService);
					Future<StringBuffer> mgntPracticesFuture = executorService.submit(new MgntPracticesAdvisoryCallable(cropId, languageId, phoneNo, noOfDays, advisoryService));
					Future<Map<String, Object>> diseaseFuture = executorService.submit(diseaseMapCallable);
					
					Future<String> fourDaysForecastFuture = executorService.submit(new FourDaysForecastCallable(advisoryService, lat, lon, languageId));
					
					diseaseMap = diseaseFuture.get();
					weatherAdvisory = weatherAdvisoryFuture.get();
					fourDaysForecast = fourDaysForecastFuture.get();
					mgntPractices = mgntPracticesFuture.get();
				} catch (Exception e) {
					e.printStackTrace();
					diseaseMap = new HashMap<>();
					weatherAdvisory = "";
					fourDaysForecast = "";
					mgntPractices = new StringBuffer("");
					throw new Exception();
				} finally {
					executorService.shutdown();
					executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
					System.out.println("-------------inside executor finally---------");
				}
				List<String> diseaseList = (List<String>) diseaseMap.get("diseaseList");
				String dName = (String) diseaseMap.get("dName");
				nutst = generateNutrientAdvisory(stage, cropId, languageId);
				adviosry.setNutrient(nutst.toString());
				StringBuilder agroAdvisory = new StringBuilder();
				agroAdvisory.append(weatherAdvisory);
				String insectPestAvd = generateInsectPestAdvisory(cropId, phoneNo, languageId, noOfDays);
				// StringBuffer mgntPractices =
				// generateMgntAdvisory(cropId,languageId,phoneNo,noOfDays);
				// String fourDaysForecast = generateFourDaysForecast(lat, lon,
				// languageId);

				adviosry.setCropName(cName);
				adviosry.setCropNameEnglish(cc.getCropname());
				adviosry.setDisease(diseaseList);
				adviosry.setHeading(agroAdvisory.toString() + "\n" + dName);
				adviosry.setInsectPest(insectPestAvd);
				adviosry.setMgntPracHindi(mgntPractices.toString());
				adviosry.setForecastAdvisory(fourDaysForecast);
				adviosry.setNotFound("");

			} else if (stage.contains("Harvesting")) {
				String prefix;
				String suffix;
				if (languageId.equals("en")) {
					prefix = "Crop cycle of your ";
					suffix = " crop is complete.Please add new crops to get advisories";
				} else {
					prefix = "आपकी ";
					suffix = " की फसल का चक्र पूर्ण हो चूका है। कृपया दूसरी फसल के लिए वर्तमान समय की नई फसल जोड़े।";

				}
				System.out.println("inside else when harvesting comes or stage not found !!!!!!!!!!!!");
				adviosry.setCropName(cName);
				adviosry.setCropNameEnglish(cc.getCropname());
				adviosry.setDisease(new ArrayList<String>());
				adviosry.setHeading("");
				adviosry.setInsectPest("");
				adviosry.setMgntPracHindi("");
				adviosry.setForecastAdvisory("");
				adviosry.setNutrient("");
				adviosry.setNotFound(prefix + cName + suffix);
				stage = "no Stage foung";
				//cropStage = "no Stage found";
			} else {

				String prefix;
				String suffix;
				if (languageId.equals("en")) {
					prefix = "Crop cycle of your  ";
					suffix = " crop is has not started yet,Please wait until sowing date.";
				} else {
					prefix = "आपकी ";
					suffix = " की  फसल का चक्र क्रम अभी शुरू नहीं हुआ है। कृषि सलाह के लिए फसल की बुवाई तक प्रतीक्षा करे।";

				}
				System.out.println("sowing date cannot be greater than todays date");
				adviosry.setCropName(cName);
				adviosry.setCropNameEnglish(cc.getCropname());
				adviosry.setDisease(new ArrayList<String>());
				adviosry.setHeading("");
				adviosry.setInsectPest("");
				adviosry.setMgntPracHindi("");
				adviosry.setForecastAdvisory("");
				adviosry.setNutrient("");
				adviosry.setNotFound(prefix + cName + suffix);
				stage = "no Stage foung";
				//cropStage = "no Stage found";
			}
		} catch (

		ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			String prefix;
			String suffix;
			if (languageId.equals("en")) {
				prefix = "Crop cycle of your  ";
				suffix = " crop is has not started yet,Please wait until sowing date.";
			} else {
				prefix = "आपकी ";
				suffix = " की  फसल का चक्र क्रम अभी शुरू नहीं हुआ है। कृषि सलाह के लिए फसल की बुवाई तक प्रतीक्षा करे।";

			}
			//System.out.println("sowing date cannot be greater than todays date");
			adviosry.setCropName(cName);
			adviosry.setCropNameEnglish(cc.getCropname());
			adviosry.setDisease(new ArrayList<String>());
			adviosry.setHeading("");
			adviosry.setInsectPest("");
			adviosry.setMgntPracHindi("");
			adviosry.setForecastAdvisory("");
			adviosry.setNutrient("");
			adviosry.setNotFound(prefix + cName + suffix);
			stage = "no Stage foung";
			//cropStage = "no Stage found";

		} catch (Exception e) {
			String message;
			if (languageId.equals("en")) {
				message = "Due to some technical problem we are unable to provide advisory";
			} else {
				message = "कुछ तकनीकी असुविधा के कारण सलाह जारी नहीं हो सकी।";
			}
			e.printStackTrace();
			adviosry.setCropName(cName);
			adviosry.setCropNameEnglish(cc.getCropname());
			adviosry.setDisease(new ArrayList<String>());
			adviosry.setHeading("");
			adviosry.setInsectPest("");
			adviosry.setMgntPracHindi("");
			adviosry.setForecastAdvisory("");
			adviosry.setNutrient("");
			adviosry.setNotFound(message);
		}
		return adviosry;
	}

	public String generateWeatherAdvisory(Date dc, float lat, float lon, int cropId, String phoneNo, String stage,
			CropDetails cc, String languageId) throws Exception {
		HashMap<String, Float> aheadList = advisoryService.ahead7Days(dc, lat, lon);
		List<CropCalendar> cList = advisoryService.cropCalList(cropId, stage);
		HashMap<String, Float> li = advisoryService.calculateRainfallInStage(cropId, stage, lat, lon, phoneNo);
		CropCalendar tempObj = new CropCalendar();

		Float beforeRainfall = li.get("beforeRainfall");
		Float forecastRainfall = li.get("forecastRainfall");
		Float maxRh = aheadList.get("maxRh");
		float maxTemp = aheadList.get("maxTemp");
		float minTemp = aheadList.get("minTemp");
		for (CropCalendar cd : cList) {
			//System.out.println("temp obj -----------------------------------------" + cd.getId().getSubStage());
			tempObj = cd;
		}
		//System.out.println(" maxt temp :-" + maxTemp + " required is" + tempObj.getMaxTempBetween2());
		//System.out.println(" max rh :-" + maxRh + " required is" + tempObj.getrHBetween2());
		//System.out.println(" rainfall ahead :-" + forecastRainfall + " rainfall between 1 "	+ tempObj.getRainfallBetween1() + " rainfall between 2 :-" + tempObj.getRadiationBetween2());

		StringBuilder agroAdvisory = new StringBuilder();
		if (languageId.equals("en")) {

			if (cc.getCropSeason().equalsIgnoreCase("Kharif")) {
				// rainfall required
				if (tempObj.getRainfallBetween1() != 0 & tempObj.getRainfallBetween2() != 0) {

					//System.out.println("rainfall is not zero");
					// if there is rainfall in forecast
					if (forecastRainfall > 5) {
						//System.out.println("forecast rainfall is not zero............!!");
						if (beforeRainfall <= (tempObj.getRainfallBetween1() - 10)) {
							//System.out.println(		"inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days.In case of water logging, pump out water from the field.Do not spray insecticides. ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days.Do not spray insecticides.");
							} else if ((beforeRainfall + forecastRainfall) <= tempObj.getRainfallBetween1()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days. In case of dryness in field irrigation is required.");
							}
						} else if (beforeRainfall >= (tempObj.getRainfallBetween1() - 10)) {
							//System.out.println(	"inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days.In case of water logging, pump out water from the field.Do not spray insecticides. ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"There is possibility of rain in coming days. Do not spray insecticides.");
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
					//System.out.println("ideal rainfall is zero ......!! forecast rainfall is :-" + forecastRainfall+ " : before rainfall is " + beforeRainfall);

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
							agroAdvisory.append("Forecast says weather is ideal from better crop growth.");
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
				boolean rainFlag = true;

				if ((forecastRainfall) > 2.5 && (forecastRainfall) <= 7.5) {
					agroAdvisory.append("Very light rainfall in forecast.");
				} else if ((forecastRainfall) > 7.6 && (forecastRainfall) <= 15.5) {
					agroAdvisory.append("Moderate rainfall in forecast.");
				} else if ((forecastRainfall) > 15.6 && (forecastRainfall) <= 35.5) {
					rainFlag = false;
					agroAdvisory.append("There is rainfall in forecast.");
				} else if ((forecastRainfall) > 35.6 && (forecastRainfall) <= 64.4) {
					rainFlag = false;
					agroAdvisory.append(
							"There is heavy rainfall in forecast. In case of water logging pump out water from the field else crop might get affected.");
				} else if ((forecastRainfall) > 64.5 && (forecastRainfall) <= 124.4) {
					rainFlag = false;
					agroAdvisory.append(
							"Heavy rainfall in forecast. In case of water logging pump out water from the field else crop might get affected.");
				} else if ((forecastRainfall) > 124.5 && (forecastRainfall) <= 244.4) {
					rainFlag = false;
					agroAdvisory.append(
							"Extreme rainfall in forecast. In case of water logging pump out water from the field else crop might get affected.");
				}

				if (maxTemp > tempObj.getMinTempBetween1() && maxTemp <= tempObj.getMaxTempBetween2()
						&& maxRh > tempObj.getrHBetween1() && maxRh < tempObj.getrHBetween2() && rainFlag) {
					agroAdvisory.append("The forecast says weather is ideal for crop.");
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

					agroAdvisory.append(
							"With increase in temperature , relative humidity may also increase. Weeding must be continued . ");
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

		} else {
			// hindi section.....!!
			if (cc.getCropSeason().equalsIgnoreCase("Kharif")) {
				// rainfall required
				if (tempObj.getRainfallBetween1() != 0 & tempObj.getRainfallBetween2() != 0) {

					//System.out.println("rainfall is not zero");
					// if there is rainfall in forecast
					if (forecastRainfall > 5) {
					//	System.out.println("forecast rainfall is not zero............!!");
						if (beforeRainfall <= (tempObj.getRainfallBetween1() - 10)) {
						//	System.out.println("inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"आगामी दिनों में बारिश की संभावना है। जल भराव की स्थिति पर जल निकासी का प्रबन्ध करे। उर्वरको और कीटनाशको का उपयोग स्थगित करे। ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory
										.append("आगे बारिश की संभावना है।  कीटनाशको का और उर्वरको का छिड़काव न करे।");
							} else if ((beforeRainfall + forecastRainfall) <= tempObj.getRainfallBetween1()) {
								agroAdvisory.append(
										"आगामी दिनों में बारिश की संभावना है। आवश्यकता पड़ने पर हल्की सिंचाई कर सकते है।");
							}
						} else if (beforeRainfall >= (tempObj.getRainfallBetween1() - 10)) {
							//System.out.println("inside when before rainfall is less tahn between 1------------------------------------------->>>>>>>");
							if ((forecastRainfall + beforeRainfall) >= tempObj.getRainfallBetween2()) {
								agroAdvisory.append(
										"आगामी दिनों में बारिश की संभावना है। जल भराव की स्थिति पर जल निकासी का प्रबन्ध करे। उर्वरको और कीटनाशको का उपयोग स्थगित करे। ");
							} else if ((beforeRainfall + forecastRainfall) >= tempObj.getRainfallBetween1()
									&& (beforeRainfall + forecastRainfall) < tempObj.getRainfallBetween2()) {
								agroAdvisory
										.append("आगे बारिश की संभावना है।  कीटनाशको का और उर्वरको का छिड़काव न करे।");
							}
						}
					}
					// if there is no rainfall in forecast
					if (forecastRainfall < 5) {
						if (beforeRainfall < tempObj.getRainfallBetween1()) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है, खेत में मृदा के नमी के अनुसार सिंचाई करे व आवश्यकता पड़ने पर खेत के अन्य काम भी कर सकते है। ");
						}

					}

				}
				// no rainfall required
				else {
					//System.out.println("ideal rainfall is zero ......!! forecast rainfall is :-" + forecastRainfall	+ " : before rainfall is " + beforeRainfall);

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
						} else if ((maxTemp > tempObj.getMinTempBetween1() & maxTemp <= tempObj.getMaxTempBetween2())
								&& (maxRh < tempObj.getrHBetween1())) {
							agroAdvisory.append(
									"आगामी दिनों में बारिश की संभावना नहीं है, हवा में नमी कम हो सकती है। बीमारी और कीटो का प्रकोप हो सकता है, नियंत्रण हेतु उचित प्रबंध करे।");
						} else if ((maxTemp > tempObj.getMinTempBetween1() & maxTemp <= tempObj.getMaxTempBetween2())
								&& (maxRh > tempObj.getrHBetween2())) {
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

				boolean rainFlag = true;

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
					rainFlag = false;
					agroAdvisory.append(
							"आने वाले दिनों में तेज बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे, नहीं तो फसल ख़राब हो सकती है।");
				} else if ((forecastRainfall) > 64.5 && (forecastRainfall) <= 124.4) {
					rainFlag = false;
					agroAdvisory.append(
							"आने वाले दिनों में बहुत भारी बारिश की संभावना है। जल जमाव होने पर जल निकासी की उचित व्यवस्था करे, नहीं तो फसल ख़राब हो सकती है।");
				} else if ((forecastRainfall) > 124.5 && (forecastRainfall) <= 244.4) {

					rainFlag = false;
					agroAdvisory.append(
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

				//System.out.println("rainfall-------------------in 4 days check..........!!" + bean.getRainfall()+ ":date is -->" + bean.getDate());

				rfDates = rfDates + "," + bean.getDate().substring(8, 10);
				//System.out.println(rfDates);
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
			//System.out.println("rh is not empty");
			rHdates = rHdates.substring(1);
			if (languageId.equals("en")) {
				rHForecast = "Relative humidity will be high on " + month + rHdates + ".";
			} else {
				rHForecast = rHdates + " तारीख को उमस  ज्यादा  रहेगी।";
			}

		}
		return rHForecast;
	}

	public Map<String, Object> generateDiseaseAdvisory(float lat, float lon, Date dc, String stage, int cropId,
			String languageId) throws Exception {

		//System.out.println(	"....................................inside generate disease advisory...........................!!!!");
	//	System.out.println("stage is " + stage);
		String symptomPrefix;
		String symptomPostfisx;
		List<String> diseaseList;
		List<GridWiseCola> ahdList;
		List<DiseaseDetailsHindi> ds;
		diseaseList = new ArrayList<String>();
		ahdList = advisoryService.sevenDaysAhead(lat, lon, dc);
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

		ds = advisoryService.ds(stage, cropId, languageId);
		String dName = "";
		StringBuilder st = new StringBuilder("");

		StringBuilder discontrol = new StringBuilder();
		Map<String, Object> disease = new HashMap<>();
		for (DiseaseDetailsHindi dt : ds) {
			System.out.println(dt.getDieaseName() + "-------------!!!!!");
			if (dt.getDays_between1() != dt.getDays_between2()) {
				System.out.println("when disease days are not same !!");
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
							System.out
									.println("Inside if when only temperature is between min and max and counter is :-"
											+ c + "maximum temp= " + gcd.getMax_temp());

						} else if (gcd.getMax_temp() > dt.getMaxTempGreaterThan()) {
							c = c + 1;
							System.out.println("when temperature greater than occurs");
						} else if (gcd.getMax_temp() < dt.getMaxTempLessThan()) {
							c = c + 1;
							System.out.println("when temperature lessthan occurs");
						}

					}

					// when only rh is given.
					if (dt.getrHGreaterThan() != 0.0
							|| dt.getrHBetween1() != 0.0 && dt.getrHBetween2() != 0.0 && dt.getMinTempBetween1() == 0) {

						if (dt.getrHGreaterThan() != 0 && gcd.getMax_rh() >= dt.getrHGreaterThan() + 2) {
							c = c + 1;
							System.out.println("when only rh greater than occurs with counter:-" + c);
						} else if (dt.getrHBetween1() != 0 && gcd.getMax_rh() >= dt.getrHBetween1()
								&& gcd.getMax_rh() <= dt.getrHBetween2() + 3) {

							c = c + 1;
							System.out.println("when rh bt1 and bt2 occurs with counter :-" + c);
						} else if (dt.getrHBetween1() == 0 & dt.getrHGreaterThan() == 0 & dt.getrHLessThan() != 0) {
							System.out.println("when only rh less than is given ");
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
							System.out.println("when both are present  with counter value :-" + c + "and max temp is :"
									+ gcd.getMax_temp());
						}
					}

				}

				if (c >= dt.getDays_between1()) {

					System.out.println("days counter = " + c);
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

				System.out.println("days are same");
			}

		}
		if (dName != "") {
			dName = dName.substring(1) + symptomPostfisx;
		}

		disease.put("diseaseList", diseaseList);
		disease.put("dName", dName);
		System.out.println(
				"....................................end generate disease advisory...........................!!!!");
		return disease;

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

	public StringBuilder generateNutrientAdvisory(String stage, int cropId, String languageId) throws Exception {
		String parameter;
		String postfix;
		if (languageId.equals("en")) {

			parameter = " Kg ";
			postfix = "per hectatre is required.";
		} else {
			parameter = " किलोग्राम ";
			postfix = " प्रति हेक्टेयर की आवश्यकता है ";
		}
		List<NutrientDetailsHindi> nd = advisoryService.nd(stage, cropId, languageId);

		StringBuilder nutst = new StringBuilder();
		System.out.println(".........-------------------");
		for (NutrientDetailsHindi nds : nd) {
			nutst.append(nds.getQuantity() + parameter + nds.getNutrientName() + ", ");
		}

		if (!nutst.toString().equals("")) {
			nutst.append(postfix);
		}

		return nutst;

	}

	public StringBuffer generateMgntAdvisory(int cropId, String languageId, String phoneNo, int noOfDays)
			throws Exception {
		List<MgntPracticesHindi> mgntPracticeList = advisoryService.getManagementPracticeHindi(cropId, "find state",
				languageId, phoneNo, noOfDays);
		StringBuffer mgntPractices = new StringBuffer();
		for (MgntPracticesHindi obj : mgntPracticeList) {
			mgntPractices.append(obj.getAdvisoryTest());
			mgntPractices.append(" ");

		}
		return mgntPractices;
	}

	public String generateInsectPestAdvisory(int cropId, String phoneNo, String languageId, int noOfDays)
			throws Exception {

		List<String> insectPest = advisoryService.getInsectPest(cropId, phoneNo, languageId, noOfDays);

		if (!(insectPest.get(insectPest.size() - 1)).isEmpty() && (insectPest.get(insectPest.size() - 1)) != " ") {

			System.out.println(insectPest.get(insectPest.size() - 1));
		}
		insectPest.remove(insectPest.size() - 1);

		String insectPestAvd = insectPest.toString().trim();
		insectPestAvd = insectPestAvd.replaceAll("\\[", "").replaceAll("\\]", "");
		return insectPestAvd;
	}

	public static void main(String[] args) {

		try {
			System.out.println(
					new SimpleDateFormat("MMM").format(new SimpleDateFormat("yyyy-MM-dd").parse("2017-07-29")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
