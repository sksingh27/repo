package com.bkc.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.CropBean;
import com.bkc.bean.CropBean2;
import com.bkc.bean.NewsBean;
import com.bkc.bean.RainfallBean;
import com.bkc.bean.UserNews;
import com.bkc.bean.UserNewsLogin;
import com.bkc.bean.UserNewsOld;
import com.bkc.dao.AdvisoryDao;

import com.bkc.model.CropCalendar;
import com.bkc.model.CropDetails;
import com.bkc.model.DiseaseDetails;
import com.bkc.model.DiseaseDetailsHindi;
import com.bkc.model.GridWiseCola;
import com.bkc.model.MgntPracticesHindi;
import com.bkc.model.NewsPojo;
import com.bkc.model.NutrientDetails;
import com.bkc.model.NutrientDetailsHindi;

@Service("avdisoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdvisoryServiceImpl implements AdvisoryService {
    @Autowired
	AdvisoryDao advisoryDao;

	@Override
	public HashMap<String, Float> before7Days(Date d, float lat, float lon) {
		
		return advisoryDao.before7Days(d, lat, lon);
	}

	@Override
	public List<GridWiseCola> beforeRainfall(Date d, float lat, float lon) {
		
		return advisoryDao.beforeRainfall(d, lat, lon);
	}

	@Override
	public List<CropCalendar> cropCalList(int cropId,String stage) {
	
		return advisoryDao.cropCalList(cropId,stage);
	}

	@Override
	public HashMap<String, Float> ahead7Days(Date d, float lat, float lon) {
		
		return advisoryDao.ahead7Days(d, lat, lon);
	}

	/*@Override
	public List<DiseaseDetails> ds(String attackStage, int cropId) {
		
		return advisoryDao.ds(attackStage, cropId);
	}
*/
	@Override
	public List<GridWiseCola> sevenDaysAhead(float lat, float lon, Date d) {
		
		return advisoryDao.sevenDaysAhead(lat, lon, d);
	}

	@Override
	public String getList(int cropId,String phoneNo) {
		
		return advisoryDao.getList(cropId,phoneNo);
	}

	@Override
	public List<NutrientDetailsHindi> nd(String reqStage, int cropId,String languageId) {		
		return advisoryDao.nd(reqStage, cropId,languageId);
	}

	@Override
	public List<CropCalendar> Ideal(String reqStage, int cropId) {
		// TODO Auto-generated method stub
		return null;
	}

/*	@Override
	public List<AdvisoryGujarat> advisoryDetail(Date currentdate, int cropId) {
		// TODO Auto-generated method stub
		return advisoryDao.advisoryDetail(currentdate, cropId);
	}

	@Override
	public List<AdvisoryGujarat> advisoryChartDetail(int cropId) {
		// TODO Auto-generated method stub
		return advisoryDao.advisoryChartDetail(cropId);
	}*/

	@Override
	public List<DiseaseDetailsHindi> ds(String attackStage, int cropId,String languageId) {
		return advisoryDao.ds(attackStage, cropId,languageId);
	}

	@Override
	public List<MgntPracticesHindi> getManagementPracticeHindi(int cropId, String state,String languageId,String phoneNo,int noOfDays) {
		// TODO Auto-generated method stub
		return advisoryDao.getManagementPracticeHindi(cropId, state,languageId,phoneNo,noOfDays);
	}

	@Override
	public HashMap<String, Float> calculateRainfallInStage(int cropId, String stage, float lat, float lon, String phoneNo) {
		// TODO Auto-generated method stub
		return advisoryDao.calculateRainfallInStage(cropId, stage, lat, lon, phoneNo);
	}
    @Override
	public List<String> getInsectPest(int cropId, String phoneNo,String languageId,int noOfDays){
		return advisoryDao.getInsectPest(cropId,  phoneNo,languageId,noOfDays);
	}

	@Override
	public HashMap<String, Float> nextDayForecast(float lat, float lon) {
		// TODO Auto-generated method stub
		return advisoryDao.nextDayForecast(lat, lon);
	}

	@Override
	public List<GridWiseCola> rfandrh4Days(float lat, float lon) {
		// TODO Auto-generated method stub
		return advisoryDao.rfandrh4Days(lat, lon);
	}

	@Override
	public HashMap<String, Float> get4DaysForecast(float lat, float lon) {
		// TODO Auto-generated method stub
		return advisoryDao.get4DaysForecast(lat, lon);
	}

	@Override
	public List<RainfallBean> rh4Days(float lat, float lon) {
		// TODO Auto-generated method stub
		return advisoryDao.rh4Days(lat, lon);
	}

	@Override
	public List<CropDetails> getCrop() {
		// TODO Auto-generated method stub
		return advisoryDao.getCrop();
	}

	@Override
	public List<CropBean> getNewCrop(String phoneNo) {
		// TODO Auto-generated method stub
		return advisoryDao.getNewCrop(phoneNo);
	}

	@Override
	public void saveNews(NewsPojo news) {
	advisoryDao.saveNews(news);
		
	}

	@Override
	public List<UserNews> getNews(String phoneNo) {
		
		return advisoryDao.getNews(phoneNo);
	}
	
	@Override
	public UserNews getIncome() {
		
		return advisoryDao.getIncome();
	}

	@Override
	public List<UserNewsLogin> getNewsForHomepage(String phoneNo,String tokenNo) {
		
		return advisoryDao.getNewsForHomepage(phoneNo,tokenNo);
	}
	
	@Override
	public List getUserForNews(String Crop, String state, String district) {
		// TODO Auto-generated method stub
		return advisoryDao.getUserForNews(Crop, state, district);
	}

	@Override
	public HashMap<String, Float> calculateRainfallInStageWeatherIndia(int cropId, String stage, float lat, float lon) {
		// TODO Auto-generated method stub
		return advisoryDao.calculateRainfallInStageWeatherIndia(cropId, stage, lat, lon);
	}

	@Override
	public String currentStageWeatherIndia(int cropId) {
		// TODO Auto-generated method stub
		return advisoryDao.currentStageWeatherIndia(cropId);
	}

	@Override
	public CropDetails cropIdOnCropName(String cropName) {
		// TODO Auto-generated method stub
		return advisoryDao.cropIdOnCropName(cropName);
	}

	@Override
	public List<CropBean> getUserCrops(String phoneNo) {
		// TODO Auto-generated method stub
		return advisoryDao.getUserCrops(phoneNo);
	}

	@Override
	public List<UserNews> getIncomeList(int limit,String phoneNo) {
		// TODO Auto-generated method stub
		return advisoryDao.getIncomeList(limit,phoneNo);
	}

	@Override
	public List<RainfallBean> rf4Days(float lat, float lon) {
		// TODO Auto-generated method stub
		return this.advisoryDao.rf4Days(lat, lon);
	}

	@Override
	public int getNoOfDaysFromUser(String phoneNo, int cropId) throws ParseException {
		
		return this.advisoryDao.getNoOfDaysFromUser(phoneNo, cropId);
	}

	/* (non-Javadoc)
	 * @see com.bkc.service.AdvisoryService#getUserCrops2(java.lang.String)
	 */
	@Override
	public List<CropBean2> getUserCrops2(String phoneNo) {
		
		return advisoryDao.getUserCrops2(phoneNo);
	}
	
	
	
	
}

