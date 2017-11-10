package com.bkc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bkc.bean.MandiRateBean;
import com.bkc.service.MandiOilService;

@Controller
public class MandiController {
	
	@Autowired
	private MandiOilService mandiOilService;
	
	
	@GetMapping("/othermandi/{state}/{district}/{cropName}")
	public ResponseEntity<MandiRateBean> getOtherMandi(@PathVariable String state,@PathVariable String district,@PathVariable String cropName){
		
		MandiRateBean bean;
		try {
			bean = mandiOilService.mandiOnSelectedDistrict(state, district, cropName);
			return new ResponseEntity<MandiRateBean>(bean,HttpStatus.OK);
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<MandiRateBean>(new MandiRateBean(),HttpStatus.OK);
		}
		
	}
	
	
	@GetMapping("/othermandidistricts/{state}")
	public ResponseEntity<List<String>> getDistricts(@PathVariable String state){
		
		return new ResponseEntity<List<String>>(mandiOilService.distinctDistirctFromAgmark(state),HttpStatus.OK);
		
	}

	@GetMapping("/othermandistates/{cropName}")
	public ResponseEntity<List<String>> getStates(@PathVariable String cropName){
		
		return new ResponseEntity<List<String>>(mandiOilService.distinctStateFromAgmark(cropName),HttpStatus.OK);
		
	}
}
