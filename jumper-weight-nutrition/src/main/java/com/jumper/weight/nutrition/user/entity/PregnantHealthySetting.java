package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class PregnantHealthySetting extends BaseEntity {

	private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer project;

    private Integer state;

    private Date addTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}