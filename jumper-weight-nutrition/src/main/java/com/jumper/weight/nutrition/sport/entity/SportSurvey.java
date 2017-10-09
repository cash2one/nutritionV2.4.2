package com.jumper.weight.nutrition.sport.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class SportSurvey extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    private Integer hospitalId;

    private Integer userId;

    private Integer outpatientId;

    private Date surveyDate;

    private String surveyList;

    private Date addTime;

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOutpatientId() {
        return outpatientId;
    }

    public void setOutpatientId(Integer outpatientId) {
        this.outpatientId = outpatientId;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getSurveyList() {
        return surveyList;
    }

    public void setSurveyList(String surveyList) {
        this.surveyList = surveyList == null ? null : surveyList.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}