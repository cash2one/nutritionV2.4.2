package com.jumper.weight.nutrition.food.vo;

import java.io.Serializable;

/**
 * 餐次功能比
 * @author gyx
 * @time 2017年6月22日
 */
public class VOMealsTypeIntakeAnalyze implements Serializable{

	private static final long serialVersionUID = -7402842399864219764L;
	
	/** 餐次 */
	private String mealsName;
	/** 摄入能量 */
	private Double energyIntake;
	/** 构成比 */
	private Double accountPercent;
	public String getMealsName() {
		return mealsName;
	}
	public void setMealsName(String mealsName) {
		this.mealsName = mealsName;
	}
	public Double getEnergyIntake() {
		return energyIntake;
	}
	public void setEnergyIntake(Double energyIntake) {
		this.energyIntake = energyIntake;
	}
	public Double getAccountPercent() {
		return accountPercent;
	}
	public void setAccountPercent(Double accountPercent) {
		this.accountPercent = accountPercent;
	}
	
	
}
