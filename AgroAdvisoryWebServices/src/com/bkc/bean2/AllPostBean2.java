/**
 * 
 */
package com.bkc.bean2;

import java.util.List;

import com.bkc.bean.AllPostBean;

/**
 * @author Akash
 *
 * @Date 20-Sep-2017
 */
public class AllPostBean2 {

	int sellerId;
	int renterId;
	List<AllPostBean> postList;
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getRenterId() {
		return renterId;
	}
	public void setRenterId(int renterId) {
		this.renterId = renterId;
	}
	public List<AllPostBean> getPostList() {
		return postList;
	}
	public void setPostList(List<AllPostBean> postList) {
		this.postList = postList;
	}
	
	
}
