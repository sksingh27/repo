package com.bkc.bean;

import java.util.Comparator;

public class AllPostBeanComperator implements Comparator<AllPostBean> {

	@Override
	public int compare(AllPostBean o1, AllPostBean o2) {
		int result=((o1.getPostDate().compareTo(o2.getPostDate())*-1)==0 ? 1:(o1.getPostDate().compareTo(o2.getPostDate())*-1));
		System.out.println(result);
		return result;
	}
	
	

}
