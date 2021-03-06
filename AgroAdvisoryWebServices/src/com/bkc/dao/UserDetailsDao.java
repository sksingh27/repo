package com.bkc.dao;

import java.util.Date;
import java.util.List;

import com.bkc.bean.AddNewCropPojo;
import com.bkc.bean.AdvertisementBean;
import com.bkc.bean.BuyerSellerPojo;
import com.bkc.bean.RegistrationPojo;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.UserQueryFeedback;
import com.bkc.bean.UserRegisteredCropBean;
import com.bkc.model.AddNewCrop;
import com.bkc.model.StationDetails;
import com.bkc.model.UserDetails;
import com.bkc.model.UserProfile;
import com.bkc.model.UserTokenRegistration;


public interface UserDetailsDao {
	
	public List<UserDetails> getUserDetailsOnPhoneNumber(String phoneNo);
	public List getLatLonOnUserIdandCropId(int cropId,int userId);
	public void tokenRegistration(String phoneNo,String tokenNo);
	public int addUser(RegistrationPojo user);
	public int addCrop(AddNewCropPojo crop);
	public int addUserFromPage(RegistrationPojo userbean);
	public List<StationDetails> getLatLonOnUserIdandCropIdBeforeDate(int cropId, int userId) ;
    public Date getCreationTImeofCrop(int userId,int cropId);
    public boolean saveUser(UserProfileRegistrationBean bean);
    public UserProfileRegistrationBean getUserProfileOnPhoneNo(String phoneNo);
    public boolean UserCropAdd(UserRegisteredCropBean cropBean);
    public boolean deleteUserRegisteredCrop(String phoneNo,int cropId);
    public boolean updateUserLocation(UserProfileRegistrationBean bean);
    public boolean updateAadharNo(String phoneNo,String aadharNo);    
    public boolean saveBuyerSeller(BuyerSellerPojo bean);
    public boolean saveUserQuery(UserQueryFeedback bean);
    public boolean updateUserName(String phoneNo,String name);
    public AdvertisementBean getAdvertisementOnUserLocation(String phoneNo);
    public String getUserLanguage(String phoneNo);
    public UserProfile getUserOnPhoneNo(String phoneNo);
    public void setUserLanguage(String phoneNo,String languageId);
    public UserTokenRegistration getUserTokenRegistration(String phoneNo);
    public boolean saveUserCropFeildQuery(UserQueryFeedback bean);
   
    
}
