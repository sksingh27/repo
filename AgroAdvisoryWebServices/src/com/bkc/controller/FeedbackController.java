package com.bkc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.ResultUser;
import com.bkc.bean.UserQueryFeedback;
import com.bkc.service.UserDetailsService;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {

	@Autowired
	UserDetailsService userService;

	/*
	 * @RequestMapping("/savefeedback") public ResponseEntity<String>
	 * saveUserQuery(@RequestBody UserQueryFeedback bean){
	 * 
	 * UserQueryFeedback bean= new UserQueryFeedback();
	 * bean.setPhoneNo("9873468841"); bean.setQuery(
	 * "मध्य प्रदेश में मंडी कारोबारियों से क्या बोले कृषि मंत्री ?");
	 * userService.saveUserQuery(bean);
	 * 
	 * 
	 * return new ResponseEntity<>(HttpStatus.OK); }
	 */

	@RequestMapping("/savefeedback")
	public ResponseEntity<ResultUser> saveUserQuery(@RequestBody UserQueryFeedback bean) {

		// System.out.println("--------saveing feedback-----------");

		boolean result = userService.saveUserQuery(bean);
		String toSend;
		ResultUser user = new ResultUser();
		if (result) {
			user.setRes("saved");
			return new ResponseEntity<ResultUser>(user, HttpStatus.OK);
		} else {
			user.setRes("not saved");
			return new ResponseEntity<ResultUser>(user, HttpStatus.OK);
		}
	}

	@RequestMapping("/savecropfieldtext")
	public ResponseEntity<ResultUser> saveUserCropFieldQueryQuery(@RequestBody UserQueryFeedback bean) {
System.out.println("saving.........!!!!!");
		boolean result = userService.saveUserCropFeildQuery(bean);
		String toSend;
		ResultUser user = new ResultUser();
		if (result) {
			user.setRes("saved");
			System.out.println("saved");
			return new ResponseEntity<ResultUser>(user, HttpStatus.OK);
		} else {
			user.setRes("not saved");
			return new ResponseEntity<ResultUser>(user, HttpStatus.OK);
		}
	}

}
