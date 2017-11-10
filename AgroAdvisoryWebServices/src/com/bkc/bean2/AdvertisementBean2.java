package com.bkc.bean2;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementBean2 {
	List<String> imageNames;
	String videoName;
	boolean video;
	String version;
	String adText;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAdText() {
		return adText;
	}
	public void setAdText(String adText) {
		this.adText = adText;
	}
	public AdvertisementBean2() {
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
