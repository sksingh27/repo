package com.bkc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="latestNews")
public class NewsPojo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
    @Column(name="headLine")
	String headLine;
	
    @Column(name="newsType")
	String newsType;
    
    @Column(name="cropName")
	String cropName;
	
    @Column(name="stateName")
	String stateName;
    
    @Column(name="districtName")
	String districtName;
	
    @Column(name="news")
	String news;
	
    @Column(name="link")
	String link;
    
    @Column(name="newsDate")
    @Temporal(TemporalType.DATE)
	Date date;

  

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeadLine() {
		return headLine;
	}

	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getNews() {
		return news;
	}

	public void setNews(String news) {
		this.news = news;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
    
	
	
	
	
}
