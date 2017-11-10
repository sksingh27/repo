/**
 * 
 */
package com.bkc.controller2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.UserNews;
import com.bkc.service2.NewsService2;

/**
 * @author Akash
 *
 * @Date 21-Sep-2017
 */

@Controller
@RequestMapping("/news2")
public class NewsController2 {
	
	@Autowired
	NewsService2 service;
	
	@GetMapping("/getNewsImage/{phoneNo}/{languageId}/{newsId}")
	public ResponseEntity<List<UserNews>> getNewsForUser(@PathVariable String phoneNo,@PathVariable String languageId,@PathVariable int newsId){
		
		List<UserNews> news=service.getNews(phoneNo, languageId, newsId);
		//System.out.println(news.size());
		return new ResponseEntity<>(news,HttpStatus.OK);		
	}
	

	@GetMapping("/getIncomeImageList/{phoneNo}/{languageId}/{newsId}")
	public ResponseEntity<List<UserNews>> getIncomeListForUser ( @PathVariable String phoneNo,@PathVariable String languageId,@PathVariable int newsId){
		//System.out.println("inside income image list ..........!!!");
		
		try{
		List<UserNews> news=service.getIncomeList(phoneNo, languageId, newsId);		
		return new ResponseEntity<>(news,HttpStatus.OK);		
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<List<UserNews>>(new ArrayList<UserNews>(),HttpStatus.OK);
		}
		}
	
}
