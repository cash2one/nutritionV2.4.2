package com.jumper.weight.nutrition.record.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class UserWeightRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer userId;

    private Double averageValue;
	/**
	 * 体重测试状(0：体重过低，1：体重正常，2：超重)    
	 */
    private Integer weightState;

    private Integer testWeek;

    private Integer testDay;

    private Date testTime;

    private Date addTime;

    private Integer basalMetabolism;

    private Double bodyFatRate;

    private Double muscle;

    private Double moistureContent;

    private Double fatMass;

    private Integer hbr;

    private Double boneRate;

    private Integer currentIdentity;

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

    public Integer getWeightState() {
        return weightState;
    }

    public void setWeightState(Integer weightState) {
        this.weightState = weightState;
    }

    public Integer getTestWeek() {
        return testWeek;
    }

    public void setTestWeek(Integer testWeek) {
        this.testWeek = testWeek;
    }

    public Integer getTestDay() {
        return testDay;
    }

    public void setTestDay(Integer testDay) {
        this.testDay = testDay;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getBasalMetabolism() {
        return basalMetabolism;
    }

    public void setBasalMetabolism(Integer basalMetabolism) {
        this.basalMetabolism = basalMetabolism;
    }

    public Double getBodyFatRate() {
        return bodyFatRate;
    }

    public void setBodyFatRate(Double bodyFatRate) {
        this.bodyFatRate = bodyFatRate;
    }

    public Double getMuscle() {
        return muscle;
    }

    public void setMuscle(Double muscle) {
        this.muscle = muscle;
    }

    public Double getMoistureContent() {
        return moistureContent;
    }

    public void setMoistureContent(Double moistureContent) {
        this.moistureContent = moistureContent;
    }

    public Double getFatMass() {
        return fatMass;
    }

    public void setFatMass(Double fatMass) {
        this.fatMass = fatMass;
    }

    public Integer getHbr() {
        return hbr;
    }

    public void setHbr(Integer hbr) {
        this.hbr = hbr;
    }

    public Double getBoneRate() {
        return boneRate;
    }

    public void setBoneRate(Double boneRate) {
        this.boneRate = boneRate;
    }

    public Integer getCurrentIdentity() {
        return currentIdentity;
    }

    public void setCurrentIdentity(Integer currentIdentity) {
        this.currentIdentity = currentIdentity;
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