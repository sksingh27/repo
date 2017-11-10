package com.bkc.controller2;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.ForecastBean;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.VillageBean;
import com.bkc.service.ForecastService;
import com.bkc.service2.ForecastService2;

@Controller
@RequestMapping("/forecast2")
public class ForecastController2 {
	
	@Autowired
	ForecastService2 forecastService;
	
	@SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("/getForecast/{phoneNo}/{noOfDays}/{lat}/{lon}/{village}/{state}")
	public ResponseEntity<List<ForecastBean>> weatherForecast(@PathVariable String phoneNo,
			@PathVariable String noOfDays,@PathVariable String lat,@PathVariable String lon,@PathVariable String village,@PathVariable String state) {

		
		List<ForecastBean> forecastfinallist = new ArrayList<>();
		
			try {
			    
				forecastfinallist = forecastService.getForecast(Float.parseFloat(lat), Float.parseFloat(lon), Integer.parseInt(noOfDays),village,state);
				return new ResponseEntity<List<ForecastBean>>(forecastfinallist, HttpStatus.OK);
			} catch (Exception e) {
				//System.out.println("-------------------------->>error in generating forecast<<--------------------------");
				e.printStackTrace();
			}
			
			
		
		return new ResponseEntity<List<ForecastBean>>(forecastfinallist, HttpStatus.OK);

	}

}
