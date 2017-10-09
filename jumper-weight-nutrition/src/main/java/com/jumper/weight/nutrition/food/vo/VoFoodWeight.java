package com.jumper.weight.nutrition.food.vo;

/**
 * 食物对应的重量
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-17
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoFoodWeight {
	
	private Integer foodId;
	/** 重量 */
	private Double weight;
	public Integer getFoodId() {
		return foodId;
	}
	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
}
