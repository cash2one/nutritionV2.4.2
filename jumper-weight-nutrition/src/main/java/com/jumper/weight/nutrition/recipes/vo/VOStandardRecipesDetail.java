package com.jumper.weight.nutrition.recipes.vo;

import java.io.Serializable;

/**
 * 标准食谱详情VO(json格式)
 * @author gyx
 * @time 2017年4月27日
 */
public class VOStandardRecipesDetail implements Serializable{

	private static final long serialVersionUID = -4381026663470949073L;
	
    private Byte mealsType;
    
    private Integer foodId;

    private String foodName;
    
    private String unitRemark = "";

    private Float foodWeight;

	public Byte getMealsType() {
		return mealsType;
	}

	public void setMealsType(Byte mealsType) {
		this.mealsType = mealsType;
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
    
    
    
}
