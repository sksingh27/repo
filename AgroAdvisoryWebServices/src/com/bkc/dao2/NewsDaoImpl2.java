/**
 * 
 */
package com.bkc.dao2;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.UserNews;
import com.bkc.service.UserDetailsService;

/**
 * @author Akash
 *
 * @Date 21-Sep-2017
 */
@Repository
@Transactional
public class NewsDaoImpl2 implements NewsDao2 {
	
	
	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory SessionFactory;
	
	@Autowired
	UserDetailsService userService;
	
	@Override
	public List<UserNews> getNews(String phoneNo,String languageId,int newsId) 
	{
		/*String userLanguage;
		try {

			userLanguage = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			e.printStackTrace();
			userLanguage = "hi";
		}*/
		Session session = SessionFactory.getCurrentSession();
		/*SQLQuery qur = session.createSQLQuery("  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1,lw.videoName from tehsilVillageDetails as tvd ,userProfile as ud ,stateDetails sd,districtDetails dd,latestNews as lw where ud.phoneNo like '"
						+ phoneNo
						+ "' and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and  (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All') and lw.languageId='"
						+ languageId + "' order by lw.newsId desc limit 20");*/
		SQLQuery qur;
		if(newsId==0){
		
			qur = session.createSQLQuery("  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1,lw.videoName from latestNews as lw where  lw.languageId='"
					+ languageId + "' order by lw.newsId desc limit 20");
		}
		else {
			
			qur = session.createSQLQuery("  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1,lw.videoName from latestNews as lw where  lw.languageId='"
					+ languageId + "' and lw.newsId <"+newsId +" order by lw.newsId desc limit 20");
		} 

		List list = qur.list();
		List<UserNews> news = new ArrayList<>();

		if (list.size() > 0) {
			for (Object obj : list) {
				UserNews n = new UserNews();
				Object[] ar = (Object[]) obj;

				n.setNewsId(Integer.parseInt(ar[0].toString()));
				n.setNewsHeadLine(ar[1].toString());
				n.setNews(ar[2].toString());
				n.setNewsType(ar[3].toString());
				n.setNewsDate(ar[4].toString());
				n.setImageName1(ar[5].toString());
				n.setVideoName(ar[6].toString());
				news.add(n);
			}
		}
	//	System.out.println("size of news :-" + news.size());
		return news;
	}


	@Override
	public List<UserNews> getIncomeList(String phoneNo,String languageId,int newsId) {
		
		
		Session session = SessionFactory.getCurrentSession();
		/*
		 * SQLQuery qur = session.createSQLQuery(
		 * "  select distinct lw.newsId,lw.headLine,lw.news,lw.newsType,lw.newsDate,lw.imageName1 from tehsilVillageDetails as tvd ,userRegisteredCrop  as anc,userProfile as ud ,stateDetails sd,districtDetails dd,cropDetails as cd,latestNews as lw where (ud.phoneNo like '"
		 * + phoneNo + "' and anc.phoneNo=ud.phoneNo) or (ud.phoneNo like '"
		 * +phoneNo+
		 * "' )  and tvd.id=ud.stationId  and tvd.stateId=sd.stateId and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and anc.cropId=cd.cropId and (lw.districtName like dd.district or lw.districtName like 'All') and (lw.stateName like sd.state or lw.stateName like 'All') and (cd.cropName like lw.cropName or lw.cropName like 'All')  order by lw.newsId desc limit 10"
		 * );
		 */
		
		SQLQuery qur;
		if(newsId==0){
		
			qur = session.createSQLQuery(
					"select distinct newsId,headLine,news,newsType,newsDate,imageName1,videoName from incomeAdvice where languageId='"
							+ languageId + "' order by newsId desc limit 20");
		}
		else{
			qur = session.createSQLQuery(
					"select distinct newsId,headLine,news,newsType,newsDate,imageName1,videoName from incomeAdvice where languageId='"
							+ languageId + "' and newsId <"+newsId+" order by newsId desc limit 20");
		}
		
		 
		// System.out.println("query:-"+"select distinct
		// newsId,headLine,news,newsType,newsDate,imageName1 from incomeAdvice
		// where languageId='"+ languageId + "' order by newsId desc limit " +
		// limit);
		List list = qur.list();
		List<UserNews> userIncomelist = new ArrayList<>();

		for (Object obj : list) {
			UserNews news = new UserNews();
			Object[] ar = (Object[]) obj;
			news.setNewsId(Integer.parseInt(ar[0].toString()));
			news.setNewsHeadLine(ar[1].toString());
			news.setNews(ar[2].toString());
			news.setNewsType(ar[3].toString());
			news.setNewsDate(ar[4].toString());
			news.setImageName1(ar[5].toString());
			news.setVideoName(ar[6].toString());
			userIncomelist.add(news);
		}
	//	System.out.println("size of income advice list " + userIncomelist.size());

		return userIncomelist;
	}
	
	
}
