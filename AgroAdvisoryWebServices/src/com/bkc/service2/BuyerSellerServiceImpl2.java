/**
 * 
 */
package com.bkc.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.bean2.AllPostBean2;
import com.bkc.dao2.BuyerSellerDao2;

/**
 * @author Akash
 *
 * @Date 20-Sep-2017
 */

@Service
public class BuyerSellerServiceImpl2 implements BuyerSellerService2 {

	@Autowired
	BuyerSellerDao2 dao;

	
	@Override
	public AllPostBean2 getAllPost(String phoneNo, int sellerId, int renterId) {
		// TODO Auto-generated method stub
		return dao.getAllPost(phoneNo, sellerId, renterId);
	}
	
	
}
