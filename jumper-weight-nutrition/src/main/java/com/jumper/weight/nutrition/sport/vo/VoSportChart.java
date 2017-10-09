package com.jumper.weight.nutrition.sport.vo;

/**
 * 运动分析图表基类vo
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-26
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoSportChart {
	private String sportName;
	private Double timeLength;
	private Double calories;
	/**运动时长占比*/
	private Double timeLengthPer;
	/**运动卡洛里占比*/
	private Double caloriesPer;
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public Double getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Double timeLength) {
		this.timeLength = timeLength;
	}
	public Double getCalories() {
		return calories;
	}
	public void setCalories(Double calories) {
		this.calories = calories;
	}
	public Double getTimeLengthPer() {
		return timeLengthPer;
	}
	public void setTimeLengthPer(Double timeLengthPer) {
		this.timeLengthPer = timeLengthPer;
	}
	public Double getCaloriesPer() {
		return caloriesPer;
	}
	public void setCaloriesPer(Double caloriesPer) {
		this.caloriesPer = caloriesPer;
	}
}
