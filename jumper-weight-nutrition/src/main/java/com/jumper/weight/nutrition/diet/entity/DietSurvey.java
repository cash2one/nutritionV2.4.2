package com.jumper.weight.nutrition.diet.entity;

import java.io.Serializable;
import java.util.Date;

public class DietSurvey implements Serializable{
	private static final long serialVersionUID = 6255329942328935429L;

	private Integer id;

    private Integer userId;

    private Integer hospitalId;

    private Integer outpatientId;

    private Date eatDate;

    private String dietMsg;

    private Date addTime;
    
    private String eatDateStr;//用于接收前端时间字符串

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getOutpatientId() {
		return outpatientId;
	}

	public void setOutpatientId(Integer outpatientId) {
		this.outpatientId = outpatientId;
	}

	public Date getEatDate() {
        return eatDate;
    }

    public void setEatDate(Date eatDate) {
        this.eatDate = eatDate;
    }

    public String getDietMsg() {
        return dietMsg;
    }

    public void setDietMsg(String dietMsg) {
        this.dietMsg = dietMsg == null ? null : dietMsg.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public String getEatDateStr() {
		return eatDateStr;
	}

	public void setEatDateStr(String eatDateStr) {
		this.eatDateStr = eatDateStr;
	}

    
}