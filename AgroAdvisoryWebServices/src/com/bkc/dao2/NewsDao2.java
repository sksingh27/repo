/**
 * 
 */
package com.bkc.dao2;

import java.util.List;

import com.bkc.bean.UserNews;

/**
 * @author Akash
 *
 * @Date 21-Sep-2017
 */
public interface NewsDao2 {
	public List<UserNews> getNews(String phoneNo,String languageId,int newsId) ;
	public List<UserNews> getIncomeList(String phoneNo,String languageId,int newsId);

}
