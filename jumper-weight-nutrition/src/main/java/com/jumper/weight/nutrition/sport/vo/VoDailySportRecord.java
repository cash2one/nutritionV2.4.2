package com.jumper.weight.nutrition.sport.vo;

import java.util.ArrayList;
import java.util.List;

public class VoDailySportRecord {
	/** 运动调查记录id */
	private Integer surveyId;
	private String sportDate;
	/** 运动的总时长（min） */
	private Double totalTime = 0D;
	/** 运动的消耗的总热量（卡洛里） */
	private Double totalCalorie= 0D;
	/** 运动记录 */
	private List<VoUserSportRecord> recordList = new ArrayList<VoUserSportRecord>();
	
	public Integer getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}
	public String getSportDate() {
		return sportDate;
	}
	public void setSportDate(String sportDate) {
		this.sportDate = sportDate;
	}
	public Double getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Double totalTime) {
		this.totalTime = totalTime;
	}
	public Double getTotalCalorie() {
		return totalCalorie;
	}
	public void setTotalCalorie(Double totalCalorie) {
		this.totalCalorie = totalCalorie;
	}
	public List<VoUserSportRecord> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<VoUserSportRecord> recordList) {
		this.recordList = recordList;
	}
}
