package com.jumper.weight.nutrition.user.vo;

/**
 * 门诊用户统计数据
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-4
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoOutpStatistics {
	/** 今日初诊人数 */
	private Integer dayFirstOutpNum;
	/** 本月初诊人数 */
	private Integer monthFirstOutpNum;
	/** 今日门诊人数 */
	private Integer dayOutpNum;
	/** 本月门诊人数 */
	private Integer monthOutpNum;
	/** 今日复诊人数 */
	private Integer dayRepeatNum;
	/** 本月复诊人数 */
	private Integer monthRepeatNum;
	
	public VoOutpStatistics(Integer dayFirstOutpNum, Integer monthFirstOutpNum,
			Integer dayOutpNum, Integer monthOutpNum, Integer dayRepeatNum,
			Integer monthRepeatNum) {
		this.dayFirstOutpNum = dayFirstOutpNum;
		this.monthFirstOutpNum = monthFirstOutpNum;
		this.dayOutpNum = dayOutpNum;
		this.monthOutpNum = monthOutpNum;
		this.dayRepeatNum = dayRepeatNum;
		this.monthRepeatNum = monthRepeatNum;
	}
	public Integer getDayFirstOutpNum() {
		return dayFirstOutpNum;
	}
	public void setDayFirstOutpNum(Integer dayFirstOutpNum) {
		this.dayFirstOutpNum = dayFirstOutpNum;
	}
	public Integer getMonthFirstOutpNum() {
		return monthFirstOutpNum;
	}
	public void setMonthFirstOutpNum(Integer monthFirstOutpNum) {
		this.monthFirstOutpNum = monthFirstOutpNum;
	}
	public Integer getDayOutpNum() {
		return dayOutpNum;
	}
	public void setDayOutpNum(Integer dayOutpNum) {
		this.dayOutpNum = dayOutpNum;
	}
	public Integer getMonthOutpNum() {
		return monthOutpNum;
	}
	public void setMonthOutpNum(Integer monthOutpNum) {
		this.monthOutpNum = monthOutpNum;
	}
	public Integer getDayRepeatNum() {
		return dayRepeatNum;
	}
	public void setDayRepeatNum(Integer dayRepeatNum) {
		this.dayRepeatNum = dayRepeatNum;
	}
	public Integer getMonthRepeatNum() {
		return monthRepeatNum;
	}
	public void setMonthRepeatNum(Integer monthRepeatNum) {
		this.monthRepeatNum = monthRepeatNum;
	}
}
