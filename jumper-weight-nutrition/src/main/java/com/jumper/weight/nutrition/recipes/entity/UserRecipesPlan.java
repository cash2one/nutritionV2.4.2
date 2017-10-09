package com.jumper.weight.nutrition.recipes.entity;

import java.util.Date;
/**
 * 用户食谱方案entity
 * @author gyx
 * @time 2017年4月28日
 */
public class UserRecipesPlan {
    private Integer id;

    private Integer userId;

    private Integer hospitalId;

    private Integer outpatientId;

    private String recipesName;

    private String recipesMsg;

    private Integer intakeCalorie;

    private Date addTime;

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

    public String getRecipesName() {
        return recipesName;
    }

    public void setRecipesName(String recipesName) {
        this.recipesName = recipesName == null ? null : recipesName.trim();
    }

    public String getRecipesMsg() {
        return recipesMsg;
    }

    public void setRecipesMsg(String recipesMsg) {
        this.recipesMsg = recipesMsg == null ? null : recipesMsg.trim();
    }

    public Integer getIntakeCalorie() {
        return intakeCalorie;
    }

    public void setIntakeCalorie(Integer intakeCalorie) {
        this.intakeCalorie = intakeCalorie;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}