package com.bkc.controller2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bkc.bean.AdvertisementBean;
import com.bkc.bean.UserNewsLogin;
import com.bkc.bean2.AdvertisementBean2;
import com.bkc.service.AdvisoryService;
import com.bkc.service.UserDetailsService;

@Controller
@RequestMapping("/home")
public class HomeController2 {
	
	@Autowired
	AdvisoryService advisoryService;

	@Autowired(required = true)
	UserDetailsService userDetailsService;
	
	@RequestMapping("/advertisement/{phoneNo}/{token}")
	public @ResponseBody ResponseEntity<AdvertisementBean2> returnAdvertisementNames(@PathVariable String phoneNo,
			@PathVariable String token) {
		try {

			// return new ResponseEntity<List<String>>(new
			// ArrayList<String>(),HttpStatus.OK);
			AdvertisementBean temp = userDetailsService.getAdvertisementOnUserLocation(phoneNo);
			AdvertisementBean2 bean= new AdvertisementBean2();
			bean.setImageNames(temp.getImageNames());
			bean.setVideoName(temp.getVideoName());
			bean.setVideo(temp.isVideo());
			List<UserNewsLogin> newsList = advisoryService.getNewsForHomepage(phoneNo, token);

			if (newsList.size() > 0) {
				bean.setAdText(newsList.get(0).getNewsHeadLine());
			} else {
				bean.setAdText("");
			}
            bean.setVersion("1.15");
			return new ResponseEntity<AdvertisementBean2>(bean, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<AdvertisementBean2>(new AdvertisementBean2(), HttpStatus.OK);
		}
	}
}