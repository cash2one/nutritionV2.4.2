package com.jumper.weight.nutrition.user.vo;

public class VoUserInfo {
	private Integer userId;
	private String mobile;
	private String realName;
	private String birthday;
	private Double weight;
	private Integer height;
	private String expectedDate;
	private Integer hospitalId;
	/**	怀孕类型0：单胎，1：多胎 */
	private Integer pregnantType;
	/**	就诊卡号 */
	private String outpatientNum;
	/**	是否需要新增门诊记录 */
	private Integer isAddOutp;
	/**保健号*/
	private String healthNum;
	/**是否是妊娠期糖尿病 0：否，1：是*/
	private Integer isDiabetes;
	
	//以下几个参数前端非必传，只是用来展现的
	private Integer age;
	private String lastPeriod;
	/**	怀孕状态0：怀孕中，1：已有宝宝 */
	private Integer currentIdentity;
	private int[] pregnantWeek;
	private Double bmi;
	/** 体重状态 0：偏瘦，1：标准，2：偏胖，3：肥胖 */
    private Integer weightStatus;
	private String hospitalName;
	/**	怀孕阶段：孕早期，孕中期，孕晚期，已有宝宝 */
	private String pregnantStage;
	/** 最近一次完成诊断的时间 */
	private String finishTime;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
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
	public Integer getPregnantType() {
		return pregnantType;
	}
	public void setPregnantType(Integer pregnantType) {
		this.pregnantType = pregnantType;
	}
	public String getLastPeriod() {
		return lastPeriod;
	}
	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod = lastPeriod;
	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	public Integer getCurrentIdentity() {
		return currentIdentity;
	}
	public void setCurrentIdentity(Integer currentIdentity) {
		this.currentIdentity = currentIdentity;
	}
	public int[] getPregnantWeek() {
		return pregnantWeek;
	}
	public void setPregnantWeek(int[] pregnantWeek) {
		this.pregnantWeek = pregnantWeek;
	}
	public Double getBmi() {
		return bmi;
	}
	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}
	public Integer getWeightStatus() {
		return weightStatus;
	}
	public void setWeightStatus(Integer weightStatus) {
		this.weightStatus = weightStatus;
	}
	public String getOutpatientNum() {
		return outpatientNum;
	}
	public void setOutpatientNum(String outpatientNum) {
		this.outpatientNum = outpatientNum;
	}
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getPregnantStage() {
		return pregnantStage;
	}
	public void setPregnantStage(String pregnantStage) {
		this.pregnantStage = pregnantStage;
	}
	public Integer getIsAddOutp() {
		return isAddOutp;
	}
	public void setIsAddOutp(Integer isAddOutp) {
		this.isAddOutp = isAddOutp;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getHealthNum() {
		return healthNum;
	}
	public void setHealthNum(String healthNum) {
		this.healthNum = healthNum;
	}
	public Integer getIsDiabetes() {
		return isDiabetes;
	}
	public void setIsDiabetes(Integer isDiabetes) {
		this.isDiabetes = isDiabetes;
	}
	
}
