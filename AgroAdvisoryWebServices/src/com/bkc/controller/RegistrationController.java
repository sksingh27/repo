package com.bkc.controller;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bkc.bean.BuyerSellerPojo;
import com.bkc.bean.CropVarietyBean;
import com.bkc.bean.ResultUser;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.UserRegisteredCropBean;
import com.bkc.bean.VillageLevelBean;
import com.bkc.service.CropDetailsService;
import com.bkc.service.StationDetailsService;
import com.bkc.service.UserDetailsService;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

	@Autowired
	StationDetailsService stationDetailsService;

	@Autowired
	UserDetailsService userDetailService;

	@Autowired
	CropDetailsService cropService;
	
	@RequestMapping("villagesGeoLocation/{lat}/{lon}")
	public ResponseEntity<List<VillageLevelBean>> registerUser(@PathVariable("lat") float lat,
			@PathVariable("lon") float lon) {

		//System.out.println(lat);
		//System.out.println(lon);

		List<VillageLevelBean> villageList = stationDetailsService.villagesOnGeoLocation(lat, lon);
		for (VillageLevelBean bean : villageList) {
			//System.out.println(bean.getVillage());
		}

		return new ResponseEntity<>(villageList, HttpStatus.OK);

	}

	@RequestMapping(value = "/saveUser", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<ResultUser> saveUser(@RequestBody UserProfileRegistrationBean bean) {
		//System.out.println("inside service language id is" + bean.getLanguageId());
		//System.out.println("village id is ----------------------" + bean.getVillageId());

		if (bean.getVillageId() == 0)
			bean.setVillageId(484710);
		//System.out.println(bean.getName());
		//System.out.println(bean.getVillageId());
		boolean userSave = userDetailService.saveUser(bean);
		ResultUser user = new ResultUser();

		if (userSave) {
			//System.out.println("user saved succesfully ");
			user.setRes("yes");

		} else {
			user.setRes("no");
			//System.out.println("user not saved");
		}
		return new ResponseEntity<ResultUser>(user, HttpStatus.OK);

	}

/*	@RequestMapping(value = "/saveBuyerSeller", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<String> saveBuyerSeller(@RequestBody BuyerSellerPojo bean) {
		System.out.println("inside service");
		if (bean.getPlace() == 0)
			bean.setPlace(484710);
		boolean userSave = userDetailService.saveBuyerSeller(bean);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PostMapping(value = { "/addCrop", "/editSowingDate" } ,consumes = "application/json",produces = "application/json")
	public ResponseEntity<String> addCrop(@RequestBody UserRegisteredCropBean bean) {
		
		 bean.setCropId(11); bean.setPhoneNo("5555555555");
		 bean.setSowingDate(new Date());
		 
		boolean result = userDetailService.UserCropAdd(bean);
		System.out.println("success"+bean.getCropId());
		
		return new ResponseEntity<>(HttpStatus.OK);

	}*/

	@RequestMapping("/userLanguage/{phoneNo}")
	public ResponseEntity<String> getUserRegisteredLanguage(@PathVariable String phoneNo) {

		String languageId;
		try {

			languageId = userDetailService.getUserLanguage(phoneNo);
			return new ResponseEntity<String>(languageId, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			languageId = "null";
			return new ResponseEntity<String>(languageId, HttpStatus.OK);

		}
	}

	@GetMapping("/saveuserlanguage/{phoneNo}/{languageId}")
	public ResponseEntity<ResultUser> getUserRegisteredLanguage(@PathVariable String phoneNo,
			@PathVariable String languageId) {
		if ("English".equalsIgnoreCase(languageId) || "en".equalsIgnoreCase(languageId)) {
			languageId = "en";
		} else {
			languageId = "hi";
		}
		ResultUser resultUser = new ResultUser();
		try {

			userDetailService.setUserLanguage(phoneNo, languageId);
			resultUser.setRes("saved");
			return new ResponseEntity<ResultUser>(resultUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			languageId = "null";
			resultUser.setRes("not saved");
			return new ResponseEntity<ResultUser>(resultUser, HttpStatus.OK);

		}
	}
	@RequestMapping(value = "/saveBuyerSeller", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<ResultUser> saveBuyerSeller(@RequestBody BuyerSellerPojo bean) {
		//System.out.println("inside service");
		if (bean.getPlace() == 0)
			bean.setPlace(484710);
		boolean userSave = userDetailService.saveBuyerSeller(bean);		
		ResultUser resultUser = new ResultUser();
		if(userSave){
			resultUser.setRes("saved");			
		}
		else {
			resultUser.setRes("not saved");
			
		}
		return new ResponseEntity<ResultUser>(resultUser,HttpStatus.OK);

	}
	
	@PostMapping(value = { "/addCrop", "/editSowingDate" } ,consumes = "application/json",produces = "application/json")
	public ResponseEntity<ResultUser> addCrop(@RequestBody UserRegisteredCropBean bean) {
		
		/* bean.setCropId(11); bean.setPhoneNo("5555555555");
		 bean.setSowingDate(new Date());*/
		 
		boolean result = userDetailService.UserCropAdd(bean);
		ResultUser resultUser = new ResultUser();
		if(result){
			resultUser.setRes("saved");
		}
		else {
			resultUser.setRes("not saved");
		}
		
		return new ResponseEntity<ResultUser>(resultUser,HttpStatus.OK);

	}
	
	@GetMapping("/getvariety/{cropId}/{languageId}")
	public ResponseEntity<List<CropVarietyBean>> getVariety(@PathVariable int cropId,@PathVariable String languageId){
		
		
		return new ResponseEntity<List<CropVarietyBean>>(cropService.getVariety(cropId, languageId),HttpStatus.OK);
		
		
	}
}
