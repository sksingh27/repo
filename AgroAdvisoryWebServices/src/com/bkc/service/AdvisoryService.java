package com.bkc.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bkc.bean.CropBean;
import com.bkc.bean.CropBean2;
import com.bkc.bean.RainfallBean;
import com.bkc.bean.UserNews;
import com.bkc.bean.UserNewsLogin;
import com.bkc.bean.UserNewsOld;
import com.bkc.model.CropCalendar;
import com.bkc.model.CropDetails;
import com.bkc.model.DiseaseDetails;
import com.bkc.model.DiseaseDetailsHindi;
import com.bkc.model.GridWiseCola;
import com.bkc.model.MgntPracticesHindi;
import com.bkc.model.NewsPojo;
import com.bkc.model.NutrientDetails;
import com.bkc.model.NutrientDetailsHindi;

public interface AdvisoryService {
	public HashMap<String, Float> before7Days(Date d, float lat, float lon)throws Exception;
	public List<GridWiseCola> beforeRainfall(Date d, float lat, float lon)throws Exception;
	public List<CropCalendar> cropCalList(int cropId,String stage)throws Exception;
	public HashMap<String, Float> ahead7Days(Date d, float lat,float lon)throws Exception;
/*	public List<DiseaseDetails> ds(String attackStage,int cropId);*/
	public List<DiseaseDetailsHindi> ds(String attackStage,int cropId,String languageId)throws Exception;
	public List<GridWiseCola> sevenDaysAhead(float lat, float lon, Date d)throws Exception;
	public String getList(int cropId,String phoneNo)throws Exception;
	public List<NutrientDetailsHindi> nd(String reqStage,int cropId,String languageId)throws Exception;
	public List<CropCalendar> Ideal(String reqStage,int cropId)throws Exception;
    public List<MgntPracticesHindi> getManagementPracticeHindi(int cropId, String state,String languageId,String phoneNo,int noOfDays)throws Exception;
	public HashMap<String, Float> calculateRainfallInStage(int cropId,String stage,float lat,float lon,String phoneNo)throws Exception;
	public List<String> getInsectPest(int cropId, String phoneNo,String languageId,int noOfDays)throws Exception;
	public HashMap<String, Float> nextDayForecast(float lat,float lon)throws Exception;
    public List<GridWiseCola> rfandrh4Days(float lat,float lon)throws Exception;
    public HashMap<String, Float> get4DaysForecast(float lat, float lon)throws Exception;
    public List<RainfallBean> rh4Days(float lat, float lon)throws Exception;
    public List<CropDetails> getCrop();
    public List<CropBean> getNewCrop(String phoneNo);
    public void saveNews(NewsPojo news)throws Exception;    
    public List getUserForNews(String Crop,String state,String district);
    public List<UserNews> getNews(String phoneNo);
    public List<UserNewsLogin> getNewsForHomepage(String phoneNo,String tokenNo);
    public HashMap<String, Float> calculateRainfallInStageWeatherIndia(int cropId,String stage, float lat, float lon)throws Exception;
    public String currentStageWeatherIndia(int cropId)throws Exception;
    public CropDetails cropIdOnCropName(String cropName) throws Exception;
    public List<CropBean> getUserCrops(String phoneNo);
    
    public UserNews getIncome();
    public List<UserNews> getIncomeList(int limit,String phoneNo);
    public List<RainfallBean> rf4Days(float lat, float lon)throws Exception;
    public int getNoOfDaysFromUser(String phoneNo, int cropId) throws ParseException;
    public List<CropBean2> getUserCrops2(String phoneNo);  //to send sowing date
}

