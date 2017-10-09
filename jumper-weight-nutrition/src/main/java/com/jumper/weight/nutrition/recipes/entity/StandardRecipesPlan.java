package com.jumper.weight.nutrition.recipes.entity;

import java.util.Date;

/**
 * 标准食谱方案entity
 * @author gyx
 * @time 2017年4月27日
 */
public class StandardRecipesPlan {
    private Integer id;

    private String name;

    private Integer hospitalId;

    private String dietAdvice;

    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDietAdvice() {
        return dietAdvice;
    }

    public void setDietAdvice(String dietAdvice) {
        this.dietAdvice = dietAdvice == null ? null : dietAdvice.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}