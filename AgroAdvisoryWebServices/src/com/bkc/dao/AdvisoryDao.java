package com.bkc.dao;

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
import com.bkc.model.DiseaseDetailsHindi;
import com.bkc.model.GridWiseCola;
import com.bkc.model.MgntPracticesHindi;
import com.bkc.model.NewsPojo;
import com.bkc.model.NutrientDetailsHindi;

public interface AdvisoryDao {

	public HashMap<String,Float > before7Days(Date d, float lat,float lon);
    public List<GridWiseCola> beforeRainfall(Date d, float lat, float lon);	 
	public List<CropCalendar> cropCalList(int cropId,String stage);
	public HashMap<String, Float> ahead7Days(Date d, float lat,float lon);
	/*public List<DiseaseDetails> ds(String attackStage,int cropId);*/
	public List<DiseaseDetailsHindi> ds(String attackStage,int cropId,String languageId);
	public List<GridWiseCola> sevenDaysAhead(float lat,float lon,Date d);
	public String getList(int cropId,String phoneNo);
	public List<NutrientDetailsHindi> nd(String reqStage,int cropId,String languageId);
	public List<CropCalendar> Ideal(String reqStage,int cropId);
/*	public List<AdvisoryGujarat> advisoryDetail(Date currentdate,int cropId);
	public List<AdvisoryGujarat> advisoryChartDetail(int cropId);*/
	public List<MgntPracticesHindi> getManagementPracticeHindi(int cropId,String state,String languageId,String phoneNo,int noOfDays);
	public HashMap<String, Float> calculateRainfallInStage(int cropId,String stage,float lat,float lon,String phoneNo);
	public List<String> getInsectPest(int cropId,String phoneNo,String languageId,int noOfDays);                         
	public HashMap<String, Float> nextDayForecast(float lat,float lon);                             
     public List<GridWiseCola> rfandrh4Days(float lat,float lon);
     public HashMap<String, Float> get4DaysForecast(float lat, float lon);
     public List<RainfallBean> rh4Days(float lat, float lon);
     public List<CropDetails> getCrop();
     public List<CropBean> getNewCrop(String phoneNo);
     public void saveNews(NewsPojo news);     
     public List getUserForNews(String Crop,String state,String district);
     public List<UserNews> getNews(String phoneNo);
     public List<UserNewsLogin> getNewsForHomepage(String phoneNo,String tokenNo);
     //weather india section starts here
     public HashMap<String, Float> calculateRainfallInStageWeatherIndia(int cropId,String stage, float lat, float lon);
     public String currentStageWeatherIndia(int cropId);
     public CropDetails cropIdOnCropName(String cropName);
     public List<CropBean> getUserCrops(String phoneNo);
     
     public UserNews getIncome();
     public List<UserNews> getIncomeList(int limit,String phoneNo);
     public List<RainfallBean> rf4Days(float lat, float lon);
     public int getNoOfDaysFromUser(String phoneNo, int cropId) throws ParseException;// returns no of days for ex. currently crop is on 55th day from start
     public List<CropBean2> getUserCrops2(String phoneNo);  //to send sowing date
     
}