package com.jumper.weight.nutrition.food.vo;

import java.io.Serializable;

/**
 * 食材简要信息VO
 * @author gyx
 * @time 2017年4月26日
 */
public class VOWeightFood implements Serializable{
	
	private static final long serialVersionUID = -1207621633137368837L;
	//食材id
	private int id;
	//食材名称
	private String name; 
	//单位备注
	private String unitRemark;
	//每100g食材所含热量
	private double calorie;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnitRemark() {
		return unitRemark;
	}
	public void setUnitRemark(String unitRemark) {
		this.unitRemark = unitRemark;
	}
	public double getCalorie() {
		return calorie;
	}
	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}
	
}
