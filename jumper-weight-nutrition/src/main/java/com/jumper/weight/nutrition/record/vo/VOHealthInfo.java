package com.jumper.weight.nutrition.record.vo;

import java.io.Serializable;

public class VOHealthInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//推荐摄入量
	private Double suggestIntake;
	//还需摄入量
	private Double needIntake;
	//已摄入量（饮食记录）
	private Double eatKcal;
	//已运动时长（分钟）
	private Double sportTimeLength;
	//运动消耗热量
	private Double sportKcal;
	//目标体重最低界
	private Double safeWeightLow;
	//目标体重最高界
	private Double safeWeightHigh;
	//最近体重
	private Double currWeight;
	//最近体重时间
	private String currWeightTime;
	//最近体重记录ID
	private Integer recordId;
	//孕前身高
	private Integer height;
	//孕前体重
	private Double beforeWeight;
	//孕前BMI
	private Double beforeBMI;
	//孕周
	private int[] pregnantWeek;
	public Double getSuggestIntake() {
		return suggestIntake;
	}
	public void setSuggestIntake(Double suggestIntake) {
		this.suggestIntake = suggestIntake;
	}
	public Double getNeedIntake() {
		return needIntake;
	}
	public void setNeedIntake(Double needIntake) {
		this.needIntake = needIntake;
	}
	public Double getEatKcal() {
		return eatKcal;
	}
	public void setEatKcal(Double eatKcal) {
		this.eatKcal = eatKcal;
	}
	public Double getSportTimeLength() {
		return sportTimeLength;
	}
	public void setSportTimeLength(Double sportTimeLength) {
		this.sportTimeLength = sportTimeLength;
	}
	public Double getSportKcal() {
		return sportKcal;
	}
	public void setSportKcal(Double sportKcal) {
		this.sportKcal = sportKcal;
	}
	public Double getSafeWeightLow() {
		return safeWeightLow;
	}
	public void setSafeWeightLow(Double safeWeightLow) {
		this.safeWeightLow = safeWeightLow;
	}
	public Double getSafeWeightHigh() {
		return safeWeightHigh;
	}
	public void setSafeWeightHigh(Double safeWeightHigh) {
		this.safeWeightHigh = safeWeightHigh;
	}
	public Double getCurrWeight() {
		return currWeight;
	}
	public void setCurrWeight(Double currWeight) {
		this.currWeight = currWeight;
	}
	public String getCurrWeightTime() {
		return currWeightTime;
	}
	public void setCurrWeightTime(String currWeightTime) {
		this.currWeightTime = currWeightTime;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Double getBeforeWeight() {
		return beforeWeight;
	}
	public void setBeforeWeight(Double beforeWeight) {
		this.beforeWeight = beforeWeight;
	}
	public Double getBeforeBMI() {
		return beforeBMI;
	}
	public void setBeforeBMI(Double beforeBMI) {
		this.beforeBMI = beforeBMI;
	}
	public int[] getPregnantWeek() {
		return pregnantWeek;
	}
	public void setPregnantWeek(int[] pregnantWeek) {
		this.pregnantWeek = pregnantWeek;
	}
	
	
	
	
}
