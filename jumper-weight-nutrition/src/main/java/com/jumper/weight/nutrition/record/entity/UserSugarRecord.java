package com.jumper.weight.nutrition.record.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class UserSugarRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
    private Integer userId;

    private Double averageValue;

    private Integer sugarState;

    private Integer testTimeState;

    private Date addTime;

    private Integer businessType;

    private Integer token;

    private Long serverAddTime;

    private Long jid;

    private Integer hospitalId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Integer getSugarState() {
        return sugarState;
    }

    public void setSugarState(Integer sugarState) {
        this.sugarState = sugarState;
    }

    public Integer getTestTimeState() {
        return testTimeState;
    }

    public void setTestTimeState(Integer testTimeState) {
        this.testTimeState = testTimeState;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Long getServerAddTime() {
        return serverAddTime;
    }

    public void setServerAddTime(Long serverAddTime) {
        this.serverAddTime = serverAddTime;
    }

    public Long getJid() {
        return jid;
    }

    public void setJid(Long jid) {
        this.jid = jid;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
}