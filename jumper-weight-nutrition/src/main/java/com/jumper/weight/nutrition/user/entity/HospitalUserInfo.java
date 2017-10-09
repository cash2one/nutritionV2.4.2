package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class HospitalUserInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer hospitalId;

    private Integer userId;

    private String outpatientNum;

    private Integer pregnantType;

    private Date addTime;
    
    /**根据安全体重范围公式判断当前体重状态 0：偏低，1：正常，2超重*/
    private Integer weightStatus;
    
    private String mobile;

    private String realName;

    private Integer height;

    private Double weight;

    private Date expectedDate;
    
    private Date birthday;
    /**保健号*/
	private String healthNum;
	/**是否是妊娠期糖尿病 0：否，1：是*/
	private Integer isDiabetes;

	//连表查询新增字段-----start
    private Double currentWeight;
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

    public String getOutpatientNum() {
        return outpatientNum;
    }

    public void setOutpatientNum(String outpatientNum) {
        this.outpatientNum = outpatientNum == null ? null : outpatientNum.trim();
    }

    public Integer getPregnantType() {
        return pregnantType;
    }

    public void setPregnantType(Integer pregnantType) {
        this.pregnantType = pregnantType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public Integer getWeightStatus() {
		return weightStatus;
	}

	public void setWeightStatus(Integer weightStatus) {
		this.weightStatus = weightStatus;
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

	public Date getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHealthNum() {
		return healthNum;
	}

	public void setHealthNum(String healthNum) {
		this.healthNum = healthNum;
	}

	public Integer getIsDiabetes() {
		return isDiabetes;
	}

	public void setIsDiabetes(Integer isDiabetes) {
		this.isDiabetes = isDiabetes;
	}

	public Double getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(Double currentWeight) {
		this.currentWeight = currentWeight;
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