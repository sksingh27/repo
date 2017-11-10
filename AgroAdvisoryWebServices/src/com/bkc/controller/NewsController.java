package com.bkc.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bkc.bean.NewsBean;
import com.bkc.bean.UserNews;
import com.bkc.bean.UserNewsLogin;
import com.bkc.bean.UserNewsOld;
import com.bkc.model.CropDetails;
import com.bkc.model.NewsPojo;
import com.bkc.model.StateDetails;
import com.bkc.service.AdvisoryService;
import com.bkc.service.CropDetailsService;
import com.bkc.service.StateService;

@Controller
public class NewsController {

	
	@Autowired
	AdvisoryService advisoryService;
	@Autowired
	private CropDetailsService cropdService;
	@Autowired
	private StateService stateService;
	
	/*public static String REQUEST_URL = "https://android.googleapis.com/gcm/send";*/
	public static String REQUEST_URL = "https://fcm.googleapis.com/fcm/send";
	
	@RequestMapping("/insertNews")
	public ModelAndView insertNews(){
	 
		
		ModelAndView m= new  ModelAndView("NewsInterface");
		NewsBean bean= new NewsBean();
		List<CropDetails> cropList=cropdService.listdata();
		
		Map<String, String> cList= new LinkedHashMap<>();
		cList.put("All", "All");
		for(CropDetails cd:cropList){
			
			cList.put(cd.getCropname(), cd.getCropname());
		}
		
		Map<String, String> sList= new LinkedHashMap<>();
		sList.put("All", "All");
		List<StateDetails> tempStateList=stateService.getDistinctStates();
		
		for(StateDetails sd:tempStateList){
			sList.put(sd.getState(), sd.getState());
			
		}
		
		
		 		
		m.addObject("cropNameList",cList);
		m.addObject("stateList",sList);
		m.addObject("initBean", bean);
		return m;
	}
	
	
	@RequestMapping("/saveNews")
	public String saveNews(@ModelAttribute NewsBean bean){
	  
		String s=null;
		try {
		 s= new String(bean.getNewsTitle().getBytes("iso-8859-1"), "UTF-8");
		//System.out.println("String is :-"+s);
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		
		//System.out.println(bean.getState()+"111111111111111111111111111");
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		String finalDate=bean.getDate().split("/")[2]+"-"+bean.getDate().split("/")[1]+"-"+bean.getDate().split("/")[0];
		//System.out.println("dasdasdasdasdasda "+bean.getDate());
		NewsPojo news= new NewsPojo();
		try {
			
			Date d=format.parse(finalDate);			
			news.setDate(d);
			news.setDistrictName(bean.getDistrict());
			news.setHeadLine(s);
			news.setNews(new String(bean.getMessage().getBytes("iso-8859-1"),"UTF-8"));
			news.setNewsType(new String(bean.getNewsType().getBytes("iso-8859-1"),"UTF-8"));
			news.setCropName(bean.getCropName());
			news.setLink(bean.getLink());
			news.setStateName(bean.getState());
			advisoryService.saveNews(news);
			/*List tokenList = advisoryService.getUserForNews(bean.getCropName(), bean.getState(), bean.getDistrict());			
			for(Object token : tokenList){
				Object []obj = (Object[]) token;
				sendNews(String.valueOf(obj[1]), s,new String(bean.getMessage().getBytes("iso-8859-1"),"UTF-8"));
			}*/					
			return "redirect:insertNews";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "error";
		
	}
	
	@GetMapping("/getNewsImage/{phoneNo}")
	public ResponseEntity<List<UserNews>> getNewsForUser(@PathVariable String phoneNo){
		
		List<UserNews> news=advisoryService.getNews(phoneNo);
		//System.out.println(news.size());
		return new ResponseEntity<>(news,HttpStatus.OK);		
	}
	
	@GetMapping("/getIncomeImage")
	public ResponseEntity<UserNews> getIncomeForUser(){
		
		UserNews news=advisoryService.getIncome();		
		return new ResponseEntity<>(news,HttpStatus.OK);		
	}
	@GetMapping("/getIncomeImageList/{phoneNo}")
	public ResponseEntity<List<UserNews>> getIncomeListForUser ( @PathVariable String phoneNo){
		//System.out.println("inside income image list ..........!!!");
		
		try{
		List<UserNews> news=advisoryService.getIncomeList(20,phoneNo);		
		return new ResponseEntity<>(news,HttpStatus.OK);		
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<List<UserNews>>(new ArrayList<UserNews>(),HttpStatus.OK);
		}
		}
	
	@RequestMapping("newshomepage/{phoneNo}/{token}")
	public ResponseEntity<UserNewsLogin> newsOnHomePage(@PathVariable String phoneNo,@PathVariable String token){
		
		List<UserNewsLogin> beanList=advisoryService.getNewsForHomepage(phoneNo,token);
		UserNewsLogin bean= new UserNewsLogin();
		if(beanList.size()>0){
			bean=beanList.get(0);
		}
		return new ResponseEntity<UserNewsLogin>(bean,HttpStatus.OK);
	}
	
	/*@GetMapping("/getNews/{phoneNo}")
	public ResponseEntity<List<UserNewsOld>> getNewsForUserOld(@PathVariable String phoneNo){
		
		List<UserNewsOld> news=advisoryService.getNewsOld(phoneNo);
		System.out.println(news.size());
		return new ResponseEntity<>(news,HttpStatus.OK);		
	}*/
	
	/*@SuppressWarnings("unchecked")
	public void sendNews(String token,String headline,String news){
				
		System.out.println("Token no. is :- "+token);
		JSONObject json_obj = new JSONObject();
		JSONObject json_obj_msg = new JSONObject();
		JSONObject json_obj_nft = new JSONObject();
		json_obj_msg.put("message", news);		
		json_obj_nft.put("body", news);
		json_obj_nft.put("title", headline);
		json_obj.put("notification", json_obj_nft);
		json_obj.put("data", json_obj_msg);
		json_obj.put("to", token);
		System.out.println(json_obj.toJSONString());
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(REQUEST_URL);
		httpPost.setHeader("Authorization","key=AIzaSyCvtNJruqO7Q3gjQEVWwWaZ7NTfIOLbE9k");		
		httpPost.setHeader("Content-Type","application/json");
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formparams,"utf-8"));
			httpPost.setEntity(new StringEntity(json_obj.toJSONString(), "UTF8"));
			httpclient.execute(httpPost);
			System.out.println("Data Send Successfully....");
		} 
		catch (UnsupportedEncodingException e) {					
			e.printStackTrace();
		} 
		catch (ClientProtocolException e) {					
			e.printStackTrace();
		} 
		catch (IOException e) {					
			e.printStackTrace();
		}		
	}	*/
}
