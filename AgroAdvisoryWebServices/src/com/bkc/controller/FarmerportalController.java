package com.bkc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bkc.bean.AddShopBean;
import com.bkc.bean.AddShopBeanJsp;
import com.bkc.bean.CorrectedShopBean;
import com.bkc.bean.FarmersportalBean;
import com.bkc.bean.OrganicFarmingBean;
import com.bkc.bean.ResultUser;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.VillageBean;
import com.bkc.model.UserProfile;
import com.bkc.model.UserTokenRegistration;
import com.bkc.service.FarmersportalService;
import com.bkc.service.StationDetailsService;
import com.bkc.service.UserDetailsService;

@SuppressWarnings({ "deprecation", "unused" })
@Controller
@RequestMapping("/farmerportal")
public class FarmerportalController {

	@Autowired
	FarmersportalService service;
	
	@Autowired
	UserDetailsService userService;
	
	@Autowired
	StationDetailsService stationService;
	
	public static String REQUEST_URL = "https://fcm.googleapis.com/fcm/send";
	
	@RequestMapping(value={"/fertilizerDealers/{phoneNo}","/seedDealers/{phoneNo}","/pesticideDealers/{phoneNo}","machinery/{phoneNo}"})
	public ResponseEntity<List<FarmersportalBean>> getDealers(@PathVariable String phoneNo,HttpServletRequest req){
		UserProfileRegistrationBean userBean=userService.getUserProfileOnPhoneNo(phoneNo);
		VillageBean villageBean=stationService.getVillageOnId(userBean.getVillageId());
		List<FarmersportalBean> beanList= new ArrayList<>();
		String url=req.getServletPath();
		//System.out.println("lat in farmers protal :- "+villageBean.getLat());
		//System.out.println("lot in farmers protal :- "+villageBean.getLon());
		//System.out.println(url);
		try{
		if(url.contains("fertilizer")){
		
			beanList=service.getFertilizersDealer(villageBean.getLat(), villageBean.getLon());
			
		}
		else if(url.contains("seed")){
			beanList=service.getSeedDealer(villageBean.getLat(), villageBean.getLon());
			
		}
		else if(url.contains("pesticide")){
			beanList=service.getPesticideDealer(villageBean.getLat(), villageBean.getLon());
			
		}
		else if (url.contains("machinery")) {
			beanList=service.getMachineryDetails(userBean.getVillageId());
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<List<FarmersportalBean>>(beanList,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/organicfaming")
	public ResponseEntity<List<OrganicFarmingBean>> organicFarmingList(@PathVariable String phonneNo){
		
		
		return new ResponseEntity<List<OrganicFarmingBean>>(service.getOrganicFarmingList(),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/addshop") 
	public ResponseEntity<ResultUser> saveShop(@RequestBody AddShopBean bean){
		
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		
		ResultUser result= new ResultUser();
		
        try {
			service.saveShop(bean);
			result.setRes("saved");
		} catch (Exception e) {
			e.printStackTrace();
			result.setRes("not saved");
		}  		
		
		return new ResponseEntity<ResultUser>(result,HttpStatus.OK);
	}
	@RequestMapping(value="/verifyshop")
	public @ResponseBody String verifyAndAddShop(@RequestParam int id, @RequestParam String shopName,
			@RequestParam String address, @RequestParam String desc, @RequestParam String phoneNo) throws Exception{
		CorrectedShopBean bean= new CorrectedShopBean();
		System.out.println(address);
		System.out.println(desc);
		bean.setId(id);
	    bean.setDesc(desc);
	    bean.setShopName(shopName);
	    bean.setAddress(address);
		System.out.println(bean.getId());
		System.out.println("phone no is "+phoneNo);
		
		
		
		
		boolean result=service.verifyShop(bean);
		if(result){
			UserTokenRegistration tokenModel=userService.getUserTokenRegistration(phoneNo);
			String languageId=userService.getUserLanguage(phoneNo);
			String headLine;
			String text;
				if(languageId.equals("en")){
					headLine=" CONGRATULATIONS!!! Your shop is Successfully Registered now";
					text=" Congratulations your shop has been added to our app successfully. Now you can see your shop in the 'Nearest Shop' section.Kindly add more people on Fasal Salah to promote their shop for free. For more information, call 9810449569 and WhatsApp on 9599735432";
				}
				else {
					headLine=" बधाई!!! आपकी दुकान अब दर्ज हो गायी है";
					text=" बधाई!!! आपकी दुकान अब दर्ज हो गायी है आपकी दुकान को सफलतापूर्वक हमारे ऐप में जोड़ दिया गया है ।अब आप नजदीकी दुकान खंड में अपनी दुकान देख सकते हैं।कृपया अपनी दुकान को मुफ्त में बढ़ावा देने के लिए फसल सालह पर अधिक लोगों को जोड़ें। अधिक जानकारी के लिए, 9810449569 पर कॉल करें और व्हाट्सएप 9599735432 पर";
				}
			sendNews(tokenModel.getTokenNo(), headLine, text, 10, "default", "");
			return "approved";
		}
		return "there was some error in saving";
	
	}
	@RequestMapping("/displayshop")
	public ModelAndView displayShop(){
		
		ModelAndView m= new ModelAndView("addshop");
		List<AddShopBeanJsp> list=service.getShopList();
		m.addObject("beanList",list);
		return m;
	}
	
	@SuppressWarnings({ "unchecked" })
	public void sendNews(String token, String headline, String news, int newsId,String image,String video) {

		System.out.println("Token no. is :- " + token);
		JSONObject json_obj = new JSONObject();
		JSONObject json_obj_msg = new JSONObject();
		JSONObject json_obj_nft = new JSONObject();
		json_obj_msg.put("message", news);
		json_obj_msg.put("mtitle", headline);
		json_obj_msg.put("newsId", newsId);
		json_obj_msg.put("imageName1", image);
		json_obj_msg.put("videoName", video);
		json_obj_nft.put("body", news);
		json_obj_nft.put("title", headline);
		/* json_obj.put("notification", json_obj_nft); */
		json_obj.put("data", json_obj_msg);
		json_obj.put("to", token);
		System.out.println(json_obj.toJSONString());
		@SuppressWarnings({ "resource" })
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(REQUEST_URL);
		httpPost.setHeader("Authorization", "key=AIzaSyCCLIeCTQX1LBUf5nrMML_vaA_6e112VRY");
		httpPost.setHeader("Content-Type", "application/json");
		try {
			// httpPost.setEntity(new UrlEncodedFormEntity(formparams,"utf-8"));
			httpPost.setEntity(new StringEntity(json_obj.toJSONString(), "UTF8"));
			httpclient.execute(httpPost);
			System.out.println("Data Send Successfully....");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
