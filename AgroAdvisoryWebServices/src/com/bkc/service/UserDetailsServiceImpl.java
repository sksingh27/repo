package com.bkc.service;

import java.util.Date;
import java.util.List;

import org.hibernate.NonUniqueObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.AddNewCropPojo;
import com.bkc.bean.AdvertisementBean;
import com.bkc.bean.BuyerSellerPojo;
import com.bkc.bean.RegistrationPojo;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.UserQueryFeedback;
import com.bkc.bean.UserRegisteredCropBean;
import com.bkc.bean.UserRegistrationBean;
import com.bkc.dao.UserDetailsDao;
import com.bkc.model.StationDetails;
import com.bkc.model.TehsilDetails;
import com.bkc.model.UserDetails;
import com.bkc.model.UserProfile;
import com.bkc.model.UserTokenRegistration;
@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	UserDetailsDao userDao;
	
	@Override
	public List<UserDetails> getUserDetailsOnPhoneNumber(String phoneNo) {
		
		return this.userDao.getUserDetailsOnPhoneNumber(phoneNo);
	}

	@Override
	public List getLatLonOnUserIdandCropId(int cropId, int userId) {
		
		return this.userDao.getLatLonOnUserIdandCropId(cropId, userId);
	}

	@Override
	public void tokenRegistration(String phoneNo, String tokenNo) {
		this.userDao.tokenRegistration(phoneNo, tokenNo);
		
	}
    
	@Override
	public int addUser(RegistrationPojo user) {
		// TODO Auto-generated method stub
		return userDao.addUser(user);
	}

	@Override
	public int addUserCrop(AddNewCropPojo crop) {
		// TODO Auto-generated method stub
		return userDao.addCrop(crop);
	}

	@Override
	public int addUserFromPage(RegistrationPojo userbean) {
		// TODO Auto-generated method stub
		return userDao.addUserFromPage(userbean);
	}

	@Override
	public List<StationDetails> getLatLonOnUserIdandCropIdBeforeDate(int cropId, int userId) {
		// TODO Auto-generated method stub
		return userDao.getLatLonOnUserIdandCropIdBeforeDate(cropId, userId);
	}

	@Override
	public Date getCreationTImeofCrop(int userId, int cropId) {
		// TODO Auto-generated method stub
		return userDao.getCreationTImeofCrop(userId, cropId);
	}

	@Override
	public boolean saveUser(UserProfileRegistrationBean bean) {
		// TODO Auto-generated method stub
		return userDao.saveUser(bean);
	}

	@Override
	public boolean saveBuyerSeller(BuyerSellerPojo bean) {
		// TODO Auto-generated method stub
		return userDao.saveBuyerSeller(bean);
	}
	
	@Override
	public UserProfileRegistrationBean getUserProfileOnPhoneNo(String phoneNo) {
		// TODO Auto-generated method stub
		return userDao.getUserProfileOnPhoneNo(phoneNo);
	}

	@Override
	public boolean UserCropAdd(UserRegisteredCropBean cropBean) {
		// TODO Auto-generated method stub
		return userDao.UserCropAdd(cropBean);
	}

	@Override
	public boolean deleteUserRegisteredCrop(String phoneNo, int cropId) {
		// TODO Auto-generated method stub
		return userDao.deleteUserRegisteredCrop(phoneNo, cropId);
	}

	@Override
	public boolean updateUserLocation(UserProfileRegistrationBean bean) {
		// TODO Auto-generated method stub
		return userDao.updateUserLocation(bean);
	}

	@Override
	public boolean updateAadharNo(String phoneNo, String aadharNo) {
		// TODO Auto-generated method stub
		return userDao.updateAadharNo(phoneNo, aadharNo);
	}
	
	@Override
	public boolean updateUserName(String phoneNo, String name) {		
		return userDao.updateUserName(phoneNo,name);
	}

	@Override
	public boolean saveUserQuery(UserQueryFeedback bean) {
		// TODO Auto-generated method stub
		return userDao.saveUserQuery(bean);
	}

	@Override
	public AdvertisementBean getAdvertisementOnUserLocation(String phoneNo)throws Exception {
		
		return userDao.getAdvertisementOnUserLocation(phoneNo);
	}

	@Override
	public String getUserLanguage(String phoneNo) throws Exception {
		
		return userDao.getUserLanguage(phoneNo);
	}

	@Override
	public UserProfile getUserOnPhoneNo(String phoneNo) {
		// TODO Auto-generated method stub
		return this.userDao.getUserOnPhoneNo(phoneNo);
	}

	@Override
	public void setUserLanguage(String phoneNo, String languageId) throws Exception {
		this.userDao.setUserLanguage(phoneNo, languageId);
		
	}

    
	@Override
	public UserTokenRegistration getUserTokenRegistration(String phoneNo) {
		
		return this.userDao.getUserTokenRegistration(phoneNo);
	}

	/* (non-Javadoc)
	 * @see com.bkc.service.UserDetailsService#saveUserCropFeildQuery(com.bkc.bean.UserQueryFeedback)
	 */
	@Override
	public boolean saveUserCropFeildQuery(UserQueryFeedback bean) {
		
		return this.userDao.saveUserCropFeildQuery(bean);
	}

}
