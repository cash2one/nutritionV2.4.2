package com.jumper.weight.nutrition.sport.vo;

public class VoUserSportRecord {
	private Integer sportId;
	private Double timeLength;
	
	//以下参数不做入参，只用来返回给前端展现
	private Integer id;
	private String sportName;
	/** 消耗的总卡洛里 */
	private Double calories;
	/** 运动30分钟消耗的卡洛里 */
	private Double calorie;
	/**运动代谢当量（消耗量=体重*MET*运动分钟/60）*/
    private Double met;
	public Integer getSportId() {
		return sportId;
	}
	public void setSportId(Integer sportId) {
		this.sportId = sportId;
	}
	public Double getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Double timeLength) {
		this.timeLength = timeLength;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public Double getCalories() {
		return calories;
	}
	public void setCalories(Double calories) {
		this.calories = calories;
	}
	public Double getCalorie() {
		return calorie;
	}
	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}
	public Double getMet() {
		return met;
	}
	public void setMet(Double met) {
		this.met = met;
	}
	
}
