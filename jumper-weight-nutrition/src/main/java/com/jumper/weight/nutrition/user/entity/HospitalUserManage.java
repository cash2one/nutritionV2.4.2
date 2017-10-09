package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

/**
 * 医院孕妇管理表
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-8
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class HospitalUserManage extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
    private Integer hospitalId;

    private Integer userId;

    private Date addTime;

    private Date lastOutpatientTime;
    
    //连表查询新增字段-----start
    private String mobile;
    private String realName;
    private Integer height;
    private Double weight;
    private Integer pWeek;
    private Date expectedDate;
    private Double currentWeight;
    private Integer weightExceptionType;
    private Date birthday;
    private Double currentBmi;
    /**当前体重状态 0：偏低，1：正常，2超重*/
    private Integer weightStatus;
    /**最近一次秤体重时间*/
    private Date lastWeightTime;
    private Double currentSugar;
    //连表查询新增字段-----end
    
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getLastOutpatientTime() {
        return lastOutpatientTime;
    }

    public void setLastOutpatientTime(Date lastOutpatientTime) {
        this.lastOutpatientTime = lastOutpatientTime;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getpWeek() {
		return pWeek;
	}

	public void setpWeek(Integer pWeek) {
		this.pWeek = pWeek;
	}

	public Date getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public Double getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(Double currentWeight) {
		this.currentWeight = currentWeight;
	}

	public Integer getWeightExceptionType() {
		return weightExceptionType;
	}

	public void setWeightExceptionType(Integer weightExceptionType) {
		this.weightExceptionType = weightExceptionType;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getCurrentBmi() {
		return currentBmi;
	}

	public void setCurrentBmi(Double currentBmi) {
		this.currentBmi = currentBmi;
	}

	public Integer getWeightStatus() {
		return weightStatus;
	}

	public void setWeightStatus(Integer weightStatus) {
		this.weightStatus = weightStatus;
	}

	public Date getLastWeightTime() {
		return lastWeightTime;
	}

	public void setLastWeightTime(Date lastWeightTime) {
		this.lastWeightTime = lastWeightTime;
	}

	public Double getCurrentSugar() {
		return currentSugar;
	}

	public void setCurrentSugar(Double currentSugar) {
		this.currentSugar = currentSugar;
	}
}