package com.bkc.controller2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.MandiBean;
import com.bkc.bean.MandiOilBean;
import com.bkc.bean.MandiRateBean;
import com.bkc.bean.MandiRateHashMapBean;
import com.bkc.bean.NcdexBean;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.VillageBean;
import com.bkc.service.ForecastService;
import com.bkc.service.MandiOilService;
import com.bkc.staticclasses.GetRoundedLatLon;

@Controller
@RequestMapping("/mandi2")
public class MandiController2 {
	
	@Autowired
	private MandiOilService mandiOilService;
	
	@Autowired
	ForecastService forecastService;

	@SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("/getMandi/{phoneNo}/{cropName}/{lat}/{lon}") //unrounded lat and long here
	public ResponseEntity<MandiRateBean> displayMandi(@PathVariable String phoneNo, @PathVariable String cropName,@PathVariable float lat,@PathVariable float lon,
			HttpSession httpSession) {

		String url = "getMandi : Phone No :- " + phoneNo;
//		foo.writingFile(url);
		//List<MandiBean> mandifinallist = forecastService.getMandi(lat, lon, cropName, phoneNo);
		MandiRateBean xlBean = mandiOilService.getmandiOilDetailsCropSpecific(cropName, lat, lon);
		//xlBean.setAgmarkList(mandifinallist);
		xlBean.setMandiDescription(GetRoundedLatLon.priceDescriptionOfCrop(cropName));

		return new ResponseEntity<MandiRateBean>(xlBean, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("/getMandi2/{phoneNo}/{cropName}/{lat}/{lon}/{state}")
	public ResponseEntity<MandiRateHashMapBean> displayMandi(@PathVariable String phoneNo, @PathVariable String cropName,@PathVariable float lat,@PathVariable float lon,@PathVariable String state) {
		
		
		/*float lat = 0, lon = 0;
		UserProfileRegistrationBean bean = userDetailsService.getUserProfileOnPhoneNo(phoneNo);
		VillageBean villageBean = stationdService.getVillageOnId(bean.getVillageId());
		lat = villageBean.getLat();
		lon = villageBean.getLon();*/
	//	System.out.println("state is --------->"+villageBean.getState());
		//System.out.println("lat in mandi :-" + lat);
	//	System.out.println("l0t in mandi :-" + lon);
		String url = "getMandi : Phone No :- " + phoneNo;
	//foo.writingFile(url);
		List<MandiBean> mandifinallist = forecastService.getMandi(lat, lon, cropName, phoneNo,state);
	HashMap<String, List<MandiBean>> agmarkHashMap = new LinkedHashMap();
	for(MandiBean b:mandifinallist){
		System.out.println("agmark date "+b.getDate());
		if(agmarkHashMap.containsKey(b.getDate())){
			agmarkHashMap.get(b.getDate()).add(b);
		}
		else {
			List<MandiBean> r= new ArrayList<>();
			r.add(b);
			agmarkHashMap.put(b.getDate(), r);
		}
		
	}
		
		
		
		MandiRateBean xlBean = mandiOilService.getmandiOilDetailsCropSpecific(cropName, lat, lon);
		
		List<MandiOilBean> oilList=xlBean.getMandiOilBeanList();
		HashMap<String, List<MandiOilBean>> oilHashMap=new LinkedHashMap<>();
		for(MandiOilBean b:oilList)
		{
			if(oilHashMap.containsKey(b.getDate())){
				oilHashMap.get(b.getDate()).add(b);
			}
			else {
				List<MandiOilBean> tempList=  new ArrayList<>();
				tempList.add(b);
				oilHashMap.put(b.getDate(), tempList);
			}
		}
		//xlBean.setAgmarkList(mandifinallist);
		//xlBean.setMandiDescription(GetRoundedLatLon.priceDescriptionOfCrop(cropName));

		MandiRateHashMapBean hashBean = new MandiRateHashMapBean();
		hashBean.setAgmarkList(agmarkHashMap);
		hashBean.setMandiDescription(xlBean.getMandiDescription());
		hashBean.setMandiOilBeanList(oilHashMap);
		hashBean.setNcdexBeanList(xlBean.getNcdexBeanList());
		return new ResponseEntity<MandiRateHashMapBean>(hashBean, HttpStatus.OK);
	}
}
