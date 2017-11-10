/**
 * 
 */
package com.bkc.service2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.bean.UserNews;
import com.bkc.dao2.NewsDao2;

/**
 * @author Akash
 *
 * @Date 21-Sep-2017
 */
@Service
public class NewsServiceImpl2 implements NewsService2 {

	
	@Autowired
	NewsDao2 dao;

	@Override
	public List<UserNews> getNews(String phoneNo, String languageId, int newsId) {
	
		return dao.getNews(phoneNo, languageId, newsId);
	}

	/* (non-Javadoc)
	 * @see com.bkc.service2.NewsService2#getIncomeList(java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<UserNews> getIncomeList(String phoneNo, String languageId, int newsId) {
		// TODO Auto-generated method stub
		return dao.getIncomeList(phoneNo, languageId, newsId);
	}
	
	
	
	
	
	
}
