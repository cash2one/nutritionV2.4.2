package com.jumper.weight.nutrition.recipes.vo;

import java.io.Serializable;

/**
 * 用户食谱详情VO(用于h5页面展示)
 * @author gyx
 * @time 2017年5月11日
 */
public class VOUserRecipesDetail implements Serializable{

	private static final long serialVersionUID = -4381026663470949073L;
	
    private Byte mealsType;
    
    private Integer foodId;

    private String foodName;
    
    private String unitRemark = "";

    private Float foodWeight;
    
    private String img;

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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
    
    
    
}
