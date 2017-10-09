package com.jumper.weight.nutrition.record.vo;


public class VoUserWeightRecord {
	
	/** 测量体重值 */
    private Double averageValue;
    /** 基础代谢 0-9999*/
    private Integer basalMetabolism;
    /** 体脂率 */
    private Double bodyFatRate;
    /** 肌肉率 */
    private Double muscle;
    /** 水分率 */
    private Double moistureContent;
    
    //以下参数只用来展示给前端，不作为入参
    private Double bmi;
    private Integer userId;
    /** 体重状态 0：偏瘦，1：标准，2：偏胖，3：肥胖 */
    private Integer weightStatus;
    private String addTime;
    private Integer recordId;

	public Double getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(Double averageValue) {
		this.averageValue = averageValue;
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

	public Double getBmi() {
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getWeightStatus() {
		return weightStatus;
	}

	public void setWeightStatus(Integer weightStatus) {
		this.weightStatus = weightStatus;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
}
