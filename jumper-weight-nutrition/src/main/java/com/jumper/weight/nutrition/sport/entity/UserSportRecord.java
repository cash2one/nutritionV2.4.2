package com.jumper.weight.nutrition.sport.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class UserSportRecord extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer sportId;

    private String sportName;

    private Double calories;

    private Date addDate;

    private Date startTime;

    private Double timeLength;

    private Date endTime;

    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName == null ? null : sportName.trim();
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Double getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Double timeLength) {
        this.timeLength = timeLength;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}