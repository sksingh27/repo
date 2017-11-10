package com.bkc.controller2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bkc.bean.AllPostBean;
import com.bkc.bean.buyerseller.RenterBean;
import com.bkc.bean.buyerseller.SellerBean;
import com.bkc.bean2.AllPostBean2;
import com.bkc.service.BuyerSellerService;
import com.bkc.service2.BuyerSellerService2;

@Controller
@RequestMapping("/buyerseller2")
public class BuyerSellerController2 {
	
	@Autowired
	BuyerSellerService buyerSellerService;
	
	@Autowired
	BuyerSellerService2 service2;
	@RequestMapping("/savesellerpost")
	public ResponseEntity<String> saveUserPost(@RequestBody SellerBean bean){
		/*SellerBean bean= new SellerBean();
		bean.setCategoryId(1);
		bean.setDescription("this is test description");
		bean.setImageName("hello.jpg");
		bean.setItemId(1);
		bean.setPhoneNo("9873468844");
		bean.setPackaging("test package");
		bean.setPrice("1000");
		bean.setQuantity("infinite");
		bean.setStationid(41127);*/
		
		try{
			buyerSellerService.saveSellerPost(bean);
			return new ResponseEntity<String>("saved",HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>("not saved",HttpStatus.OK);
		}
		
		
		
	}
	
	@RequestMapping("/saverenterpost")
	public ResponseEntity<String> saveRenterPost(@RequestBody RenterBean bean){
		/*RenterBean bean= new RenterBean();
		bean.setCategoryId(1);
		bean.setDescription("this is test description");
		bean.setImageName("hello.jpg");
		bean.setItemId(1);
		bean.setPhoneNo("9873468844");
		
		bean.setPrice("1000");

		bean.setStationid(41127);*/
		
	//	System.out.println("category id :-"+ bean.getCategoryId());
		
		try{
			buyerSellerService.saveRenterPost(bean);
			return new ResponseEntity<String>("saved",HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>("not saved",HttpStatus.OK);
		}
		
		
	}
	
	@RequestMapping("/getallposts/{phoneNo}/{sellerId}/{renterId}")
	public ResponseEntity<AllPostBean2> getAllPosts(@PathVariable String phoneNo,@PathVariable int sellerId,@PathVariable int renterId){
		
		try {
		return	new ResponseEntity<AllPostBean2>(service2.getAllPost(phoneNo,sellerId,renterId),HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return	new ResponseEntity<AllPostBean2>(new AllPostBean2(),HttpStatus.OK);
		}
	}

}
