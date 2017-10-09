package com.jumper.weight.nutrition.diet.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 饮食记录VO
 * @author gyx
 * @time 2017年4月26日
 */
public class VOWeightMealsInfo implements Serializable{

	private static final long serialVersionUID = -4381026663470949073L;
	private Long id;
	
	private Integer userId;

    private Byte mealsType;
    
    private Date addTime;

    private Date eatDate;
    
    private Integer foodId;

    private String foodName;
    
    private String unitRemark = "";

    private Float foodWeight;
    
    private Double calorie;
    
    private Integer categoryId;
    
    private String categoryName;
    
    private Double totalCalorie;

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
		this.foodName = foodName;
	}

	public String getUnitRemark() {
		return unitRemark;
	}

	public void setUnitRemark(String unitRemark) {
		this.unitRemark = unitRemark;
	}

	public Float getFoodWeight() {
		return foodWeight;
	}

	public void setFoodWeight(Float foodWeight) {
		this.foodWeight = foodWeight;
	}

	public Double getCalorie() {
		return calorie;
	}

	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getTotalCalorie() {
		return totalCalorie;
	}

	public void setTotalCalorie(Double totalCalorie) {
		this.totalCalorie = totalCalorie;
	}
    
    
}
