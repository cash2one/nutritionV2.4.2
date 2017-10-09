package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class WeightUserOrder extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
    private Integer userId;

    private Double currentWeight;

    private Integer weightExceptionType;

    /**当前体重状态 0：偏低，1：正常，2超重*/
    //private Integer weightStatus;
    /**最近一次秤体重时间*/
    private Date lastWeightTime;
    
    private Double currentSugar;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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