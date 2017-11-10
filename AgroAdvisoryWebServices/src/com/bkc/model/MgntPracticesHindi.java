package com.bkc.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="mgntPracticesHindi")
public class MgntPracticesHindi {
    @EmbeddedId
	MgntPractcesHindiId id;
	@Column(name="advisoryText")
	String advisoryTest;
	@Column(name="startDate")
    int daysBetween1;
	@Column(name="endDate")
    int daysBetween2;
	@Column(name="state")
	String state;
	@Column(name="mgntTarger")
	String mgntTarget;
	public String getMgntTarget() {
		return mgntTarget;
	}
	public void setMgntTarget(String mgntTarget) {
		this.mgntTarget = mgntTarget;
	}
	public MgntPractcesHindiId getId() {
		return id;
	}
	public void setId(MgntPractcesHindiId id) {
		this.id = id;
	}
	public String getAdvisoryTest() {
		return advisoryTest;
	}
	public void setAdvisoryTest(String advisoryTest) {
		this.advisoryTest = advisoryTest;
	}
	public int getDaysBetween1() {
		return daysBetween1;
	}
	public void setDaysBetween1(int daysBetween1) {
		this.daysBetween1 = daysBetween1;
	}
	public int getDaysBetween2() {
		return daysBetween2;
	}
	public void setDaysBetween2(int daysBetween2) {
		this.daysBetween2 = daysBetween2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
