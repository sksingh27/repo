package com.bkc.controller2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.VillageBean;
import com.bkc.bean2.ClientRegistrationBean;
import com.bkc.service.StationDetailsService;
import com.bkc.service.UserDetailsService;
import com.bkc.staticclasses.GetRoundedLatLon;

@Controller
@RequestMapping("/edit2")
public class EditController2 {
	
	@Autowired
	UserDetailsService userService;

	@Autowired
	StationDetailsService stationDetailsService;
	
	@PostMapping(value="/updateUserLocation")
	public ResponseEntity<ClientRegistrationBean> updateLocation(@RequestBody UserProfileRegistrationBean bean/*@RequestParam UserProfileRegistrationBean bean*/){
		
		//bean.setAadharNo("111");
		//bean.setName("test");
		
		boolean userSave=userService.updateUserLocation(bean);

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
	
	@GetMapping(value="/updateAadharNo/{phoneNo}/{aadharNo}")
	public ResponseEntity<String> addAAdharCard(@PathVariable String phoneNo,@PathVariable String aadharNo){
	//	System.out.println("PN "+phoneNo+"       Adhar"+aadharNo);
		boolean result=userService.updateAadharNo(phoneNo, aadharNo);
		if(result){
			return new ResponseEntity<String>("saved",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("not saved",HttpStatus.OK);
		
		}
	}
	
	@GetMapping(value="/updateUserName/{phoneNo}/{Name}")
	public ResponseEntity<String> changeUserName(@PathVariable String phoneNo,@PathVariable String Name){
		//System.out.println("PN "+phoneNo+"       Adhar"+Name);
		boolean result=userService.updateUserName(phoneNo, Name);
		if(result){
			return new ResponseEntity("saved",HttpStatus.OK);
		}
		else {
			return new ResponseEntity("not saved",HttpStatus.OK);
		
		}
	}
}
