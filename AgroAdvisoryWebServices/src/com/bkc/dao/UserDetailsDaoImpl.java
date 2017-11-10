package com.bkc.dao;

import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.query.spi.HQLQueryPlan;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.AddNewCropPojo;
import com.bkc.bean.AdvertisementBean;
import com.bkc.bean.BuyerSellerPojo;
import com.bkc.bean.RegistrationPojo;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.UserQueryFeedback;
import com.bkc.bean.UserRegisteredCropBean;
import com.bkc.controller.Password;
import com.bkc.model.AddNewCrop;
import com.bkc.model.AddNewCropId;
import com.bkc.model.BuyerSeller;
import com.bkc.model.CropCalendar;
import com.bkc.model.StationDetails;
import com.bkc.model.UserCropCalendar;
import com.bkc.model.UserCropCalendarId;
import com.bkc.model.UserDetails;
import com.bkc.model.UserFeedback;
import com.bkc.model.UserLanguage;
import com.bkc.model.UserProfile;
import com.bkc.model.UserRegisteredCrop;
import com.bkc.model.UserRegisteredCropId;
import com.bkc.model.UserTokenRegistration;
import com.bkc.service.CropDetailsService;
import com.bkc.service.StationDetailsService;
import com.bkc.service.UserDetailsService;

@Repository("userDao")
public class UserDetailsDaoImpl implements UserDetailsDao {

	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory sessionFactory;

	@Autowired
	private CropDetailsService cropService;
	@Autowired
	private StationDetailsService stationdService;

	@Autowired
	private UserDetailsService userService;

	SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public List<UserDetails> getUserDetailsOnPhoneNumber(String phoneNo) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("phoneNo", phoneNo));
		List<UserDetails> userList = criteria.list();

		return userList;
	}

	@Override
	public List getLatLonOnUserIdandCropId(int cropId, int userId) {
		List finaList = new ArrayList<>();

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddNewCrop.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.add(Restrictions.eq("userId", userId));
		List<AddNewCrop> userCropList = criteria.list();

		for (AddNewCrop adn : userCropList) {

			SQLQuery criteria2 = session.createSQLQuery(
					"Select th.id,th.village,th.tehsil,th.lat,th.lon,dd.district,sd.state from tehsilVillageDetails as th ,stateDetails sd,districtDetails dd where th.id="
							+ adn.getId().getStationId()
							+ " and th.districtId=dd.districtId and th.stateId=dd.stateId and sd.stateId=th.stateId");

			finaList.addAll(criteria2.list());

		}

		return finaList;
	}

	@Override
	public List<StationDetails> getLatLonOnUserIdandCropIdBeforeDate(int cropId, int userId) {
		List<StationDetails> finaList = new ArrayList<>();

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddNewCrop.class);
		criteria.add(Restrictions.eq("id.cropId", cropId));
		criteria.add(Restrictions.eq("userId", userId));
		List<AddNewCrop> userCropList = criteria.list();

		for (AddNewCrop adn : userCropList) {
			Criteria criteria2 = session.createCriteria(StationDetails.class);
			criteria2.add(Restrictions.eq("id.id", adn.getId().getStationId()));
			List<StationDetails> tempList = criteria2.list();
			finaList.addAll(tempList);

		}

		return finaList;
	}

	@Override
	public void tokenRegistration(String phoneNo, String tokenNo) {

		UserTokenRegistration utr = new UserTokenRegistration();
		utr.setPhoneNo(phoneNo);
		utr.setTokenNo(tokenNo);
		utr.setDate(new Date().toGMTString());
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(utr);
	}

	@SuppressWarnings("unchecked")
	@Override
	// @Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public int addUser(RegistrationPojo userbean) {
		int flag = 0;

		List<UserDetails> list = sessionFactory.getCurrentSession().createCriteria(UserDetails.class)
				.add(Restrictions.eq("phoneNo", userbean.getPhone())).list();
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			if (list.isEmpty()) {

				AddNewCrop cropsave = new AddNewCrop();
				AddNewCropId cropid = new AddNewCropId();
				UserDetails user = new UserDetails();
				int crop = cropService.getCropId(userbean.getCrop().trim());
				String username = userbean.getName();
				String password = userbean.getName();
				/* String email = userbean.getEmailid(); */
				String state = userbean.getState();
				String district = userbean.getDistrict();
				int villageId = userbean.getVillageId();
				String name = userbean.getName();
				String phoneNo = userbean.getPhone();

				/*
				 * user.setCropId(cropService.getCropId(crop.toString().trim()))
				 * ;
				 */
				/* user.setEmailId(email.trim()); */
				user.setPassword(Password.getPassword(password));
				/*
				 * user.setStationId(stationdService.getStationID(station.trim()
				 * , state.trim()));
				 */
				user.setUserName(username);
				user.setName(name);
				user.setPhoneNo(phoneNo);
				user.setCreationTime(new Date());
				session.save(user);
			//	System.out.println("Userid :- " + user.getUserId());
				cropid.setCropId(crop);
				cropid.setNewCropId(user.getUserId());

				cropid.setStationId(villageId);
				cropsave.setUserId(user.getUserId());
				cropsave.setId(cropid);
				cropsave.setCropCreationTIme(new Date());
				session.save(cropsave);

				String[] date = userbean.getSowingdate().split("/");
				String sowingdate = date[2] + "-" + date[1] + "-" + date[0];

				// generateCropCalendar(crop, user.getUserId(), sowingdate);
				//System.out.println("transaction commited!!!");

				transaction.commit();
				session.close();

				flag = 0;
			} else {
				flag = 1;
			}
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
	//	System.out.println("Return Flag Value :- " + flag);

		return flag;
		/* sessionFactory.getCurrentSession().saveOrUpdate(user); */
	}

	@SuppressWarnings("unchecked")
	@Override
	// @Transactional(propagation=Propagation.REQUIRED)
	@Rollback(true)
	public int addUserFromPage(RegistrationPojo userbean) {
		int flag = 0;

		List<UserDetails> list = sessionFactory.getCurrentSession().createCriteria(UserDetails.class)
				.add(Restrictions.eq("phoneNo", userbean.getPhone())).list();
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			if (list.isEmpty()) {

				AddNewCrop cropsave = new AddNewCrop();
				AddNewCropId cropid = new AddNewCropId();
				UserDetails user = new UserDetails();
				int crop = Integer.parseInt(userbean.getCrop().trim());
				String username = userbean.getName();
				String password = userbean.getName();
				/* String email = userbean.getEmailid(); */
				String state = userbean.getState();
				String district = userbean.getDistrict();
				int villageId = userbean.getVillageId();
				String name = userbean.getName();
				String phoneNo = userbean.getPhone();

				/*
				 * user.setCropId(cropService.getCropId(crop.toString().trim()))
				 * ;
				 */
				/* user.setEmailId(email.trim()); */
				user.setPassword(Password.getPassword(password));
				/*
				 * user.setStationId(stationdService.getStationID(station.trim()
				 * , state.trim()));
				 */
				user.setUserName(username);
				user.setName(name);
				user.setPhoneNo(phoneNo);
				user.setCreationTime(new Date());
				session.save(user);
			//	System.out.println("Userid :- " + user.getUserId());
				cropid.setCropId(crop);
				cropid.setNewCropId(user.getUserId());

				cropid.setStationId(villageId);
				cropsave.setUserId(user.getUserId());
				cropsave.setId(cropid);
				session.save(cropsave);

				String[] date = userbean.getSowingdate().split("/");
				String sowingdate = date[2] + "-" + date[1] + "-" + date[0];

				// generateCropCalendar(crop, user.getUserId(), sowingdate);
			//	System.out.println("transaction commited!!!");
				/*
				 * for(UserCropCalendar usercrplist : userlist){
				 * session.save(usercrplist); }
				 */

				transaction.commit();
				session.close();

				flag = 0;
			} else {
				flag = 1;
			}
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
	//	System.out.println("Return Flag Value :- " + flag);

		return flag;
		/* sessionFactory.getCurrentSession().saveOrUpdate(user); */
	}

	@SuppressWarnings("unchecked")
	public void generateCropCalendar(int cropId, String phoneNo, String sowDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<UserCropCalendar> usercroplist = new ArrayList<UserCropCalendar>();
		List<CropCalendar> croplist = sessionFactory.getCurrentSession().createCriteria(CropCalendar.class)
				.add(Restrictions.eq("id.cropId", cropId)).addOrder(Order.asc("noOfDays")).list();
		UserCropCalendar ucrp = null;
		UserCropCalendarId ucrpId = null;

		try {
			Date sw = sdf.parse(sowDate);
			sw = DateUtils.addDays(sw, -15);
			sowDate = sdf.format(sw);
		//	System.out.println("after reducing 15 from days.........................................!!!" + sowDate);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e);
		}
		String sowingDate = sowDate;
		Session session1 = this.sessionFactory.openSession();
		Transaction transaction1 = session1.beginTransaction();
		int i = 1;

		for (CropCalendar crplist : croplist) {

			try {

				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(sowDate));
				c.add(Calendar.DATE, crplist.getNoOfDays()); // number of days
																// to add
				sowingDate = sdf.format(c.getTime()); // dt is now the new date
			//	System.out.println(sowingDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// throw e;
			}
			//System.out.println("setting date of " + crplist.getId().getStage() + " to" + sowingDate);
			ucrp = new UserCropCalendar();
			ucrpId = new UserCropCalendarId();
			ucrpId.setCropId(cropId);
			// ucrpId.setStationId(stationId);
			ucrpId.setPhoneNo(phoneNo);
			ucrpId.setStageId(++i);

			ucrp.setId(ucrpId);
			ucrp.setDateOfStage(sowingDate);
			ucrp.setIrrigation(crplist.getIrrigation());
			ucrp.setMaxTempBetween1(crplist.getMaxTempBetween1());
			ucrp.setMaxTempBetween2(crplist.getMaxTempBetween2());
			ucrp.setMaxTempEqual(crplist.getMaxTempEqual());
			ucrp.setMinTempBetween1(crplist.getMinTempBetween1());
			ucrp.setMinTempBetween2(crplist.getMinTempBetween2());
			ucrp.setMinTempEqual(crplist.getMinTempEqual());
			ucrp.setNoOfDays(crplist.getNoOfDays());
			ucrp.setRadiationBetween1(crplist.getRadiationBetween1());
			ucrp.setRadiationBetween2(crplist.getRadiationBetween2());
			ucrp.setRadiationEqual(crplist.getRadiationEqual());
			ucrp.setRainfallBetween1(crplist.getRainfallBetween1());
			ucrp.setRainfallBetween2(crplist.getRainfallBetween2());
			ucrp.setRainfallEqual(crplist.getRainfallEqual());
			ucrp.setrHBetween1(crplist.getrHBetween1());
			ucrp.setrHBetween2(crplist.getrHBetween2());
			ucrp.setrHEqual(crplist.getrHEqual());
			ucrp.setStages(crplist.getId().getStage());
			ucrp.setSubStages(crplist.getId().getSubStage());
			ucrp.setWindSpedEqual(crplist.getWindSpeedEqual());
			ucrp.setWindSpeedBetween1(crplist.getWindSpeedBetween1());
			ucrp.setWindSpeedBetween2(crplist.getWindSpeedBetween2());
			session1.saveOrUpdate(ucrp);

			usercroplist.add(ucrp);

		}
		transaction1.commit();
		session1.close();

		// return usercroplist;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Rollback(true)
	public int addCrop(AddNewCropPojo crop) {
		int flag = 0;
		List<UserDetails> list = sessionFactory.getCurrentSession().createCriteria(UserDetails.class)
				.add(Restrictions.eq("phoneNo", crop.getPhone())).list();

		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {

			AddNewCrop cropsave = new AddNewCrop();
			AddNewCropId cropid = new AddNewCropId();

			int cropId = cropService.getCropId(crop.getCrop().trim());
			String state = crop.getState();
			String district = crop.getDistrict();
			int villageId = crop.getVillageId();
			String phoneNo = crop.getPhone();

			//System.out.println(crop.getVillageId());
			//System.out.println("Userid :- " + list.get(0).getUserId());
			//System.out.println("cropid :- " + cropId);
			//System.out.println("Stationid :- " + villageId);

			cropid.setCropId(cropId);
			cropid.setNewCropId(list.get(0).getUserId());
			cropid.setStationId(villageId);
			cropsave.setUserId(list.get(0).getUserId());
			cropsave.setId(cropid);
			cropsave.setCropCreationTIme(new Date());
			session.save(cropsave);

			String[] date = crop.getSowingdate().split("/");
			String sowingdate = date[2] + "-" + date[1] + "-" + date[0];

			// generateCropCalendar(cropId, list.get(0).getUserId(),
			// sowingdate);
			//System.out.println("transaction commited!!!");

			transaction.commit();
			session.close();

			flag = 0;
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			throw e;
		}
	//	System.out.println("Return Flag Value :- " + flag);
		return flag;
	}

	@Override
	public Date getCreationTImeofCrop(int userId, int cropId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(AddNewCrop.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("id.cropId", cropId));
		AddNewCrop anc = (AddNewCrop) criteria.list().get(0);
		return anc.getCropCreationTIme();
	}

	@SuppressWarnings("unchecked")
	@Rollback(true)
	@Override
	public boolean saveUser(UserProfileRegistrationBean bean) {
		// boolean result = false;
		Session session = sessionFactory.openSession();
		/*
		 * Criteria criteria = session.createCriteria(UserProfile.class);
		 * criteria.add(Restrictions.eq("phoneNo", bean.getPhoneNo().trim()));
		 */
		Transaction transaction = session.beginTransaction();

		try {

			UserLanguage language = new UserLanguage();
			UserProfile profile = new UserProfile();
			language.setPhoneNo(bean.getPhoneNo());
			language.setLanguageId(bean.getLanguageId());
			UserTokenRegistration token = new UserTokenRegistration();
			token.setPhoneNo(bean.getPhoneNo());
			token.setTokenNo(bean.getToken());
			token.setDate(new Date().toString());

			profile.setCreationTime(new Date());
			profile.setName(bean.getName());
			profile.setLastUpdatedTime(new Date());
			profile.setAadharNo(bean.getAadharNo());
			profile.setPhoneNo(bean.getPhoneNo());
			profile.setStationId(bean.getVillageId());
			session.save(profile);
			session.saveOrUpdate(language);
			session.saveOrUpdate(token);
			transaction.commit();

			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			//System.out.println("local timeout :-" + transaction.getTimeout() + " transaction status "+ transaction.getLocalStatus() + " active" + transaction.isActive());
			Session session2 = sessionFactory.openSession();

			//System.out.println("user already registrered ...!!");
			Query userProfileQuery = session2.createQuery(
					"update UserProfile set name= :name,aadharNo= :aadhar,stationId=:stationId where phoneNo= :phoneNo");

			UserTokenRegistration token = new UserTokenRegistration();
			token.setPhoneNo(bean.getPhoneNo());
			token.setTokenNo(bean.getToken());
			token.setDate(new Date().toString());
			userProfileQuery.setString("name", bean.getName());
			userProfileQuery.setString("aadhar", bean.getAadharNo());
			userProfileQuery.setInteger("stationId", bean.getVillageId());
			userProfileQuery.setString("phoneNo", bean.getPhoneNo());

			Query userLanguageQuery = session2
					.createQuery("update UserLanguage set languageId= :languageId where phoneNo= :phoneNo");
			userLanguageQuery.setString("languageId", bean.getLanguageId());
			userLanguageQuery.setString("phoneNo", bean.getPhoneNo());

			userProfileQuery.executeUpdate();
			userLanguageQuery.executeUpdate();
			session2.saveOrUpdate(token);
			// transaction.commit();
			// session.flush();
			session2.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// transaction.commit();
			session.close();
			//System.out.println("error in saving user");
			return false;
		} finally {
			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Rollback(true)
	@Override
	public boolean saveBuyerSeller(BuyerSellerPojo bean) {
		// boolean result = false;
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			//System.out.println(bean.getBuyer_seller() + "---" + bean.getContact() + "---" + bean.getDesc() + "---"		+ bean.getEmail() + "---" + bean.getName() + "---" + bean.getPlace());
			BuyerSeller buyerSeller = new BuyerSeller();
			buyerSeller.setName(bean.getName());
			buyerSeller.setContact(bean.getContact());
			buyerSeller.setBuyer_seller(bean.getBuyer_seller());
			buyerSeller.setDesc(bean.getDesc());
			buyerSeller.setEmail(bean.getEmail());
			buyerSeller.setPlace(bean.getPlace());
			session.saveOrUpdate(buyerSeller);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			session.flush();
			session.close();
		//	System.out.println("error in saving user");
			// e.printStackTrace();
			return false;
		}
	}

	@Override
	public UserProfileRegistrationBean getUserProfileOnPhoneNo(String phoneNo) {
	//	System.out.println(phoneNo);
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserProfile.class);
		UserProfile profile = (UserProfile)criteria.add(Restrictions.eq("phoneNo", phoneNo)).uniqueResult();
		UserProfileRegistrationBean bean = new UserProfileRegistrationBean();

		try {
			bean.setPhoneNo(profile.getPhoneNo());
			bean.setVillageId(profile.getStationId());
			bean.setName(profile.getName());
			bean.setAadharNo(profile.getAadharNo());
			
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			//System.out.println("No user found on this number");
		}
		return bean;
	}

	@Rollback(true)
	@Override
	public boolean UserCropAdd(UserRegisteredCropBean cropBean) {
		boolean result = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Session session = sessionFactory.getCurrentSession();
		// Transaction transaction=session.beginTransaction();
		UserRegisteredCrop urc = new UserRegisteredCrop();

		UserRegisteredCropId urcid = new UserRegisteredCropId();
		try {
			Date sw = sdf.parse(cropBean.getSowingdate());
			urcid.setCropId(cropBean.getCropId());
			urcid.setPhoneNo(cropBean.getPhoneNo());
			urc.setId(urcid);
			urc.setSowingDate(sw);
			urc.setLastUpdatedTime(new Date());
			session.saveOrUpdate(urc);
			generateCropCalendar(cropBean.getCropId(), cropBean.getPhoneNo(), cropBean.getSowingdate());
			result = true;
		} catch (Exception e) {
			 e.printStackTrace();
			//System.out.println("error in adding crop");
		}
		return result;
	}

	@Override
	@Rollback(true)
	public boolean deleteUserRegisteredCrop(String phoneNo, int cropId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String deleteFormUserRegisteredCrop = "delete from UserRegisteredCrop where id.phoneNo= :phoneNo and id.cropId= :cropId";
			String deleteFromUserCropCalendar = "delete from UserCropCalendar where id.phoneNo= :phoneNo and id.cropId= :cropId";
			session.createQuery(deleteFormUserRegisteredCrop).setString("phoneNo", phoneNo).setInteger("cropId", cropId)
					.executeUpdate();
			session.createQuery(deleteFromUserCropCalendar).setString("phoneNo", phoneNo).setInteger("cropId", cropId)
					.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean updateUserLocation(UserProfileRegistrationBean bean) {
		boolean result = false;
		Session session = sessionFactory.getCurrentSession();
		/*
		 * Criteria criteria = session.createCriteria(UserProfile.class);
		 * criteria.add(Restrictions.eq("id.phoneNo",
		 * bean.getPhoneNo().trim()));
		 */
		String hql = "update UserProfile set stationId= :stationId where phoneNo= :phoneNo";
		try {
			/*
			 * UserProfile profile = new UserProfile(); UserProfileId profileId
			 * = new UserProfileId();
			 * 
			 * profile.setCreationTime(new Date());
			 * profile.setName(bean.getName()); profile.setLastUpdatedTime(new
			 * Date()); profileId.setAadharNo(bean.getAadharNo());
			 * profileId.setPhoneNo(bean.getPhoneNo());
			 * profile.setStationId(bean.getVillageId());
			 * profile.setId(profileId); session.update(profile);
			 */
			session.createQuery(hql).setString("phoneNo", bean.getPhoneNo())
					.setInteger("stationId", bean.getVillageId()).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false; // TODO: handle exception
		}

	}

	@Override
	public boolean updateUserName(String phoneNo, String name) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "update UserProfile set name= :name where phoneNo= :phoneNo";
			session.createQuery(hql).setString("phoneNo", phoneNo).setString("name", name).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}

	}

	@Override
	public boolean updateAadharNo(String phoneNo, String aadharNo) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "update UserProfile set aadharNo= :aadharNo where phoneNo= :phoneNo";
			session.createQuery(hql).setString("phoneNo", phoneNo).setString("aadharNo", aadharNo).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}

	}

	@Override
	public boolean saveUserQuery(UserQueryFeedback bean) {
		try {
			Session session = sessionFactory.getCurrentSession();
			UserFeedback feedback = new UserFeedback();
			feedback.setFeedbackDate(yyyymmdd.parse(yyyymmdd.format(new Date())));
			feedback.setUserQuery(bean.getQuery());
			feedback.setPhoneNo(bean.getPhoneNo());
            feedback.setQueryType("feedback");
			session.save(feedback);

		} catch (Exception e) {
			//System.out.println("cannot save user query for phone no." + bean.getPhoneNo());
			return false;
		}
		return true;
	}

	
	@Override
	public boolean saveUserCropFeildQuery(UserQueryFeedback bean) {
		try {
			Session session = sessionFactory.getCurrentSession();
			UserFeedback feedback = new UserFeedback();
			feedback.setFeedbackDate(yyyymmdd.parse(yyyymmdd.format(new Date())));
			feedback.setUserQuery(bean.getQuery());
			feedback.setPhoneNo(bean.getPhoneNo());
            feedback.setQueryType("feild");
			session.save(feedback);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public AdvertisementBean getAdvertisementOnUserLocation(String phoneNo) {
		AdvertisementBean bean = new AdvertisementBean();
		bean.setVideo(false);
		List<String> fileNames = new ArrayList<>();
		String stateId = "null";
		String districtId = "null";
		Session session = sessionFactory.getCurrentSession();
		String stateHQL = "select distinct tvd.stateId from UserProfile as up,TehsilVillageDetailsNew as tvd where up.stationId=tvd.iid and up.phoneNo='"
				+ phoneNo + "'";
		String districtHQL = "select distinct tvd.districtId from UserProfile as up,TehsilVillageDetailsNew as tvd where up.stationId=tvd.iid and up.phoneNo='"
				+ phoneNo + "'";
		List stateIdList = session.createQuery(stateHQL).list();
		for (Object obj : stateIdList) {
			stateId = obj.toString();
			//System.out.println("state id is " + stateId);
		}
		List districtIdList = session.createQuery(districtHQL).list();
		for (Object obj : districtIdList) {
			districtId = obj.toString();
			//System.out.println("district id is " + districtId);
		}
		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			languageId = "hi";
			e.printStackTrace();
		}
		File f = new File("/home/dmdd/Desktop/fasalsalah/advertisement/videoad/");
		File[] files = f.listFiles();
		/*
		 * for (File temp : files) {
		 * 
		 * if (temp.getName().contains("district-" + districtId + "-state" +
		 * stateId + "-")) { bean.setVideoName(temp.getName());
		 * bean.setVideo(true); } else if (temp.getName().contains("state-" +
		 * stateId)) { bean.setVideo(true); bean.setVideoName(temp.getName()); }
		 * 
		 * }
		 */
		// System.out.println("bean . is video" + bean.isVideo());
		// if (!bean.isVideo()) {
		//languageId="en";
		//System.out.println("language id is"+languageId);
		List<String> imageNameList = new ArrayList<>();
		f = new File("/home/dmdd/Desktop/fasalsalah/advertisement/");
		files = f.listFiles();
		boolean defaultflag = true;
		boolean stateFlag = true;
		for (File temp : files) {
			if (temp.getName().contains(languageId)) {
			//	System.out.println(temp.getName());
				if (temp.getName().contains("district-" + districtId + "-state" + stateId)) {
				//	System.out.println("setting district images images........!!!");
					imageNameList.add(temp.getName());
					defaultflag = false;
					stateFlag = false;
					bean.setVideo(false);
				} else if (temp.getName().contains("state-" + stateId) && stateFlag) {
					defaultflag = false;
					//System.out.println("setting state images........!!!");
					bean.setVideo(false);
					imageNameList.add(temp.getName());
				} else if (temp.getName().contains("default") && defaultflag) {
					bean.setVideo(false);
					imageNameList.add(temp.getName());
				}
			
			// }
			bean.setImageNames(imageNameList);
			}
		}

		/*
		 * if (bean.getImageNames().size() == 0) { bean.setVideo(true);
		 * bean.setVideoName("video.mp4");
		 * 
		 * }
		 */
		return bean;
	}

	@Override
	public String getUserLanguage(String phoneNo) {
		//System.out.println("found phone no " + phoneNo);
		String userLanguageId = "";
		Session session = sessionFactory.getCurrentSession();
		String hql = "select languageId from UserLanguage as ul where ul.phoneNo='" + phoneNo + "'";
		List temp = session.createQuery(hql).list();
		for (Object obj : temp) {
			userLanguageId = obj.toString();
		}
		return userLanguageId;
	}

	@Override
	public UserProfile getUserOnPhoneNo(String phoneNo) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserProfile.class);
		criteria.add(Restrictions.eq("phoneNo", phoneNo));
		UserProfile profile;
		try {
			profile = (UserProfile) criteria.list().get(0);
		} catch (IndexOutOfBoundsException e) {
			profile = new UserProfile();
		}

		return profile;
	}

	@Override
	public void setUserLanguage(String phoneNo, String languageId) {
	//	System.out.println(	"-------------------------->>inside user daoipl while updating userLanguage <<----------------------------------------- phone:-"+ phoneNo + " --------language---------------" + languageId + "-----------------------------");
		Session session = sessionFactory.getCurrentSession();
		String hql = "update UserLanguage set languageId= :languageId where phoneNo= :phoneNo";
		session.createQuery(hql).setString("phoneNo", phoneNo).setString("languageId", languageId.trim())
				.executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.bkc.dao.UserDetailsDao#getUserTokenRegistration(java.lang.String)
	 */
	@Override
	public UserTokenRegistration getUserTokenRegistration(String phoneNo) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(UserTokenRegistration.class);
		criteria.add(Restrictions.eq("phoneNo", phoneNo));
		UserTokenRegistration res=(UserTokenRegistration)criteria.uniqueResult();
		return res;
	}

}
