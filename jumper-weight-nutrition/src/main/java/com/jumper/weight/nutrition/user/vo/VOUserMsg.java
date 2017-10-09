package com.jumper.weight.nutrition.user.vo;

import java.io.Serializable;

public class VOUserMsg implements Serializable{
	
	private static final long serialVersionUID = -1142432169595626905L;
	
	/** 真实名称 */
	private String realName;
	/** 孕前体重 */
	private Double weight;
	/** 身高 */
	private Integer height;
	/** 年龄 */
	private Integer age;
	/** 孕前BMI */
	private Double beforeBMI;
	/** 孕期 */
	private String pregnantStage;
	/** 孕周 */
	private String pregnantWeek;
	/** 当前体重 */
	private Double currentWeight;
	/** 当前BMI */
	private Double currentBMI;
	 /** 基础代谢 0-9999*/
    private Integer basalMetabolism;
    /** 体脂率 */
    private Double bodyFatRate;
    /** 肌肉率 */
    private Double muscle;
    /** 水分率 */
    private Double moistureContent;
    /** 推荐孕期体重增长 */
    private String suggestWeightAdd;
    /** 当前体重增长 */
    private Double currentWeightAdd;
    /** 预产期 */
    private String expectedDate;
    /** 最近一次称重时间 */
    private String addWeightTime;
    /**	怀孕类型0：单胎，1：多胎 */
	private Integer pregnantType;
    
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getBeforeBMI() {
		return beforeBMI;
	}
	public void setBeforeBMI(Double beforeBMI) {
		this.beforeBMI = beforeBMI;
	}
	public String getPregnantStage() {
		return pregnantStage;
	}
	public void setPregnantStage(String pregnantStage) {
		this.pregnantStage = pregnantStage;
	}
	public String getPregnantWeek() {
		return pregnantWeek;
	}
	public void setPregnantWeek(String pregnantWeek) {
		this.pregnantWeek = pregnantWeek;
	}
	public Double getCurrentWeight() {
		return currentWeight;
	}
	public void setCurrentWeight(Double currentWeight) {
		this.currentWeight = currentWeight;
	}
	public Double getCurrentBMI() {
		return currentBMI;
	}
	public void setCurrentBMI(Double currentBMI) {
		this.currentBMI = currentBMI;
	}
	public Integer getBasalMetabolism() {
		return basalMetabolism;
	}
	public void setBasalMetabolism(Integer basalMetabolism) {
		this.basalMetabolism = basalMetabolism;
	}
	public Double getBodyFatRate() {
		return bodyFatRate;
	}
	public void setBodyFatRate(Double bodyFatRate) {
		this.bodyFatRate = bodyFatRate;
	}
	public Double getMuscle() {
		return muscle;
	}
	public void setMuscle(Double muscle) {
		this.muscle = muscle;
	}
	public Double getMoistureContent() {
		return moistureContent;
	}
	public void setMoistureContent(Double moistureContent) {
		this.moistureContent = moistureContent;
	}
	public String getSuggestWeightAdd() {
		return suggestWeightAdd;
	}
	public void setSuggestWeightAdd(String suggestWeightAdd) {
		this.suggestWeightAdd = suggestWeightAdd;
	}
	public Double getCurrentWeightAdd() {
		return currentWeightAdd;
	}
	public void setCurrentWeightAdd(Double currentWeightAdd) {
		this.currentWeightAdd = currentWeightAdd;
	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	public String getAddWeightTime() {
		return addWeightTime;
	}
	public void setAddWeightTime(String addWeightTime) {
		this.addWeightTime = addWeightTime;
	}
	public Integer getPregnantType() {
		return pregnantType;
	}
	public void setPregnantType(Integer pregnantType) {
		this.pregnantType = pregnantType;
	}
	
	
}
