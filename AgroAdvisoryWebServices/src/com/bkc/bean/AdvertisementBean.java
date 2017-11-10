package com.bkc.bean;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementBean {
	
	List<String> imageNames;
	String videoName;
	boolean video;
	public AdvertisementBean() {
		this.imageNames=new ArrayList<>();
		
	}
	public List<String> getImageNames() {
		return imageNames;
	}
	public void setImageNames(List<String> imageNames) {
		this.imageNames = imageNames;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public boolean isVideo() {
		return video;
	}
	public void setVideo(boolean video) {
		this.video = video;
	}
	

}
