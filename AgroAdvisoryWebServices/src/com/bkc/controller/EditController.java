package com.bkc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bkc.bean.CropBean;
import com.bkc.bean.ResultUser;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.model.CropDetails;
import com.bkc.service.AdvisoryService;
import com.bkc.service.UserDetailsService;

@Controller
@RequestMapping("/edit")
public class EditController {

	@Autowired
	UserDetailsService userService;
	
	@Autowired
	AdvisoryService advisoryService;
	
	@GetMapping("/deleteCrop/{phoneNo}/{cropId}")
	public ResponseEntity<List<CropBean>> deleteCrop(@PathVariable String phoneNo,@PathVariable int cropId){
	
		boolean result=userService.deleteUserRegisteredCrop(phoneNo, cropId);
		
		List<CropBean> beanList=advisoryService.getUserCrops(phoneNo);
		   
		return new ResponseEntity<List<CropBean>>(beanList,HttpStatus.OK);
		
	}
	
/*	@GetMapping(value="/updateUserLocation/{phoneNo}/{villageId}")
	public ResponseEntity updateLocation(@PathVariable String phoneNo,@PathVariable int villageId@RequestParam UserProfileRegistrationBean bean){
		UserProfileRegistrationBean bean= new UserProfileRegistrationBean();
		//bean.setAadharNo("111");
		//bean.setName("test");
		bean.setPhoneNo(phoneNo);
		bean.setVillageId(villageId);
		boolean userSave=userService.updateUserLocation(bean);
		if(userSave){
			System.out.println("saved");
			return new ResponseEntity(HttpStatus.OK);
		}
		else {
			return new ResponseEntity(HttpStatus.OK);
		}
	}
	@GetMapping(value="/updateAadharNo/{phoneNo}/{aadharNo}")
	public ResponseEntity addAAdharCard(@PathVariable String phoneNo,@PathVariable String aadharNo){
		System.out.println("PN "+phoneNo+"       Adhar"+aadharNo);
		boolean result=userService.updateAadharNo(phoneNo, aadharNo);
		if(result){
			return new ResponseEntity(HttpStatus.OK);
		}
		else {
			return new ResponseEntity(HttpStatus.OK);
		
		}
	}
	
	@GetMapping(value="/updateUserName/{phoneNo}/{Name}")
	public ResponseEntity<String> changeUserName(@PathVariable String phoneNo,@PathVariable String Name){
		System.out.println("PN "+phoneNo+"       Adhar"+Name);
		boolean result=userService.updateUserName(phoneNo, Name);
		if(result){
			return new ResponseEntity("yes",HttpStatus.OK);
		}
		else {
			return new ResponseEntity("no",HttpStatus.OK);
		
		}
	}*/
	
	@GetMapping(value="/updateUserLocation/{phoneNo}/{villageId}")
	public ResponseEntity<String> updateLocation(@PathVariable String phoneNo,@PathVariable int villageId/*@RequestParam UserProfileRegistrationBean bean*/){
		UserProfileRegistrationBean bean= new UserProfileRegistrationBean();
		//bean.setAadharNo("111");
		//bean.setName("test");
		bean.setPhoneNo(phoneNo);
		bean.setVillageId(villageId);
		boolean userSave=userService.updateUserLocation(bean);
		if(userSave){
			//System.out.println("saved");
			return new ResponseEntity<String>("saved",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("not saved",HttpStatus.OK);
		}
	}
	
	@GetMapping(value="/updateAadharNo/{phoneNo}/{aadharNo}")
	public ResponseEntity<ResultUser> addAAdharCard(@PathVariable String phoneNo,@PathVariable String aadharNo){
		//System.out.println("PN "+phoneNo+"       Adhar"+aadharNo);
		boolean result=userService.updateAadharNo(phoneNo, aadharNo);
		ResultUser user= new ResultUser();
		if(result){
			user.setRes("saved");
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		}
		else {
			user.setRes("not saved");
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		
		}
	}
	
	@GetMapping(value="/updateUserName/{phoneNo}/{Name}")
	public ResponseEntity<ResultUser> changeUserName(@PathVariable String phoneNo,@PathVariable String Name){
		//System.out.println("PN "+phoneNo+"       Adhar"+Name);
		boolean result=userService.updateUserName(phoneNo, Name);
		ResultUser user= new ResultUser();
		if(result){
			user.setRes("saved");
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		}
		else {
			user.setRes("not saved");
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		
		}
	}
}
