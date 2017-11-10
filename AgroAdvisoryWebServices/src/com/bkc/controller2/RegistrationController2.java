package com.bkc.controller2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bkc.bean.BuyerSellerPojo;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.UserRegisteredCropBean;
import com.bkc.bean.VillageBean;
import com.bkc.bean2.ClientRegistrationBean;
import com.bkc.service.StationDetailsService;
import com.bkc.service.UserDetailsService;
import com.bkc.staticclasses.GetRoundedLatLon;

@Controller()
@RequestMapping("/registration2")
public class RegistrationController2 {

	@Autowired
	UserDetailsService userDetailService;
	
	@Autowired
	StationDetailsService stationDetailsService;
	
	
	@PostMapping(value = "/saveUser")
	public ResponseEntity<ClientRegistrationBean> saveUser(@RequestBody UserProfileRegistrationBean bean) {

		if (bean.getVillageId() == 0)
			bean.setVillageId(484710);
		//System.out.println(bean.getName());
		//System.out.println(bean.getVillageId());
		boolean userSave = userDetailService.saveUser(bean);


		if (userSave) {
			//System.out.println("user saved succesfully ");
			VillageBean b=stationDetailsService.getVillageOnId(bean.getVillageId());
			ClientRegistrationBean regBean= new ClientRegistrationBean();
			regBean.setUnroundedLat(b.getLat());
			regBean.setUnroundedLon(b.getLon());
			regBean.setLat(GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(b.getLat())));
			regBean.setLon(GetRoundedLatLon.getLatLonPoint25Grid(String.valueOf(b.getLon())));
			regBean.setLocation(b.getVillageName());
			regBean.setState(b.getState());
			regBean.setUsername(bean.getName());
			regBean.setPhone(bean.getPhoneNo());
			regBean.setAadharNo(bean.getAadharNo());
			regBean.setSave(true);
			regBean.setLocationid(bean.getVillageId());
			return new ResponseEntity<ClientRegistrationBean>(regBean, HttpStatus.OK);

		} else {
			
			
			return new ResponseEntity<ClientRegistrationBean>(new ClientRegistrationBean(), HttpStatus.FORBIDDEN);
		}
	
	}
	
	@RequestMapping(value = "/saveBuyerSeller", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<String> saveBuyerSeller(@RequestBody BuyerSellerPojo bean) {
		//System.out.println("inside service");
		if (bean.getPlace() == 0)
			bean.setPlace(484710);
		boolean userSave = userDetailService.saveBuyerSeller(bean);
		String toSend;
		if(userSave){
			toSend="saved";
		}
		else {
			toSend="not saved";
		}
		return new ResponseEntity<String>(toSend,HttpStatus.OK);

	}
	
	@PostMapping(value = { "/addCrop", "/editSowingDate" } ,consumes = "application/json",produces = "application/json")
	public ResponseEntity<String> addCrop(@RequestBody UserRegisteredCropBean bean) {
		
		/* bean.setCropId(11); bean.setPhoneNo("5555555555");
		 bean.setSowingDate(new Date());*/
		 
		boolean result = userDetailService.UserCropAdd(bean);
		String toSend;
		if(result){
			toSend="saved";
		}
		else {
			toSend="not saved";
		}
		
		return new ResponseEntity<String>(toSend,HttpStatus.OK);

	}
	
}
