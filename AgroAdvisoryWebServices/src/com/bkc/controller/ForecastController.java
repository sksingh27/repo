package com.bkc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.ForecastBean;
import com.bkc.bean.Forecast.StateDistrictBean;
import com.bkc.service.ForecastService;

@Controller
public class ForecastController {

	
	@Autowired
	ForecastService service;
   @RequestMapping("/getstates")
	public ResponseEntity<List<StateDistrictBean>> getStates(){
		//System.out.println("inside controller");
		List<StateDistrictBean> beanList=service.getStates();
		return new ResponseEntity<List<StateDistrictBean>>(beanList,HttpStatus.OK); 
				
	}

   @RequestMapping("/getdistricts/{stateId}")
public ResponseEntity<List<StateDistrictBean>> getDistricts(@PathVariable Integer stateId){
		
		List<StateDistrictBean> beanList=service.getDistrict(stateId);
		return new ResponseEntity<List<StateDistrictBean>>(beanList,HttpStatus.OK); 
				
	}
	
	public List<ForecastBean> getDistrictForecast(){
		
		
		return null;
	}
	@RequestMapping("/getdistrictforecast/{stateId}/{districtId}/{district}")
	public ResponseEntity<List<ForecastBean>> getDistrictForecast(@PathVariable int stateId,@PathVariable int districtId,@PathVariable String district){
		
		try{
		
			List<ForecastBean> forecastBeanList=service.getDistrictForecast(districtId, stateId,district);
			
			return new ResponseEntity<List<ForecastBean>>(forecastBeanList,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			List<ForecastBean> forecastBeanList=new ArrayList<>();
			
			return new ResponseEntity<List<ForecastBean>>(forecastBeanList,HttpStatus.OK);
		}
		
	}
}
