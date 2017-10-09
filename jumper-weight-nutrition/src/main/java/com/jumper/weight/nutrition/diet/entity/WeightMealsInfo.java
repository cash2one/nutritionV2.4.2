package com.jumper.weight.nutrition.diet.entity;

import java.util.Date;

public class WeightMealsInfo {
    private Long id;

    private Integer userId;

    private Byte mealsType;

    private Date addTime;

    private Date eatDate;

    private Byte recordType;

    private Integer foodId;

    private String foodName;

    private Float foodWeight;

    private Float calorie;

    private Float sugarPoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getMealsType() {
        return mealsType;
    }

    public void setMealsType(Byte mealsType) {
        this.mealsType = mealsType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getEatDate() {
        return eatDate;
    }

    public void setEatDate(Date eatDate) {
        this.eatDate = eatDate;
    }

    public Byte getRecordType() {
        return recordType;
    }

    public void setRecordType(Byte recordType) {
        this.recordType = recordType;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName == null ? null : foodName.trim();
    }

    public Float getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(Float foodWeight) {
        this.foodWeight = foodWeight;
    }

    public Float getCalorie() {
        return calorie;
    }

    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }

    public Float getSugarPoint() {
        return sugarPoint;
    }

    public void setSugarPoint(Float sugarPoint) {
        this.sugarPoint = sugarPoint;
    }
}