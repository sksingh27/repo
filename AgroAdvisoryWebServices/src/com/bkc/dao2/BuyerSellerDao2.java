/**
 * 
 */
package com.bkc.dao2;

import com.bkc.bean2.AllPostBean2;

/**
 * @author Akash
 *
 * @Date 20-Sep-2017
 */
public interface BuyerSellerDao2 {
	
	public AllPostBean2 getAllPost(String phoneNo,int sellerId,int renterId);

}
