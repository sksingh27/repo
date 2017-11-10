package com.bkc.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFeedbackBean {

	List<String> audioFile;
	List<String> imageFile;
	String date;

	
	public List<String> getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(List<String> audioFile) {
		this.audioFile = audioFile;
	}

	public List<String> getImageFile() {
		return imageFile;
	}

	public void setImageFile(List<String> imageFile) {
		this.imageFile = imageFile;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	

}
