package com.jumper.weight.nutrition.food.vo;

import java.io.Serializable;

/**
 * 各类食物摄入量分析entity
 * @author gyx
 * @time 2017年6月22日
 */
public class VOFoodCatagoryAnalyze implements Serializable{
	
	private static final long serialVersionUID = -2569229770144033208L;
	/** 食材类别 */
	private String catagoryName;
	/** 食材重量 */
	private Double foodWeight;
	public String getCatagoryName() {
		return catagoryName;
	}
	public void setCatagoryName(String catagoryName) {
		this.catagoryName = catagoryName;
	}
	public Double getFoodWeight() {
		return foodWeight;
	}
	public void setFoodWeight(Double foodWeight) {
		this.foodWeight = foodWeight;
	}
}
