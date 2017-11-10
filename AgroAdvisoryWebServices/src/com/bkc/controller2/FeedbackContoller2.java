package com.bkc.controller2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.UserQueryFeedback;
import com.bkc.service.UserDetailsService;

@Controller("/feedback")
public class FeedbackContoller2 {

	@Autowired
	UserDetailsService userService;	
	
	@PostMapping("/savefeedback")
	public ResponseEntity<String> saveUserQuery(@RequestBody UserQueryFeedback bean){
		
		/*UserQueryFeedback bean= new UserQueryFeedback();
		bean.setPhoneNo("9873468841");
		bean.setQuery("मध्य प्रदेश में मंडी कारोबारियों से क्या बोले कृषि मंत्री ?");*/
		boolean result =userService.saveUserQuery(bean);
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
