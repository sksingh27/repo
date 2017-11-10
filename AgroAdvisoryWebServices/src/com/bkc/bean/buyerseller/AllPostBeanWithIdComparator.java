package com.bkc.bean.buyerseller;

import java.util.Comparator;

import com.bkc.bean.AllPostBean;

public class AllPostBeanWithIdComparator implements Comparator<AllPostBeanWithId>{

	@Override
	public int compare(AllPostBeanWithId o1, AllPostBeanWithId o2) {
	
		int result=((o1.getPostDate().compareTo(o2.getPostDate())*-1)==0 ? 1:(o1.getPostDate().compareTo(o2.getPostDate())*-1));
		System.out.println(result);
		return result;
	}
	

}
