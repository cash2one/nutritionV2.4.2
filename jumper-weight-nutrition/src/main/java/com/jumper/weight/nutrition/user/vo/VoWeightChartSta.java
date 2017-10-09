package com.jumper.weight.nutrition.user.vo;

/**
 * 体重异常图表统计vo
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-26
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoWeightChartSta {
	/**对应的日期*/
	private String date;
	/**体重偏低人数*/
	private Integer lowWeightNum;
	/**体重超重人数*/
	private Integer highWeightNum;
	/**体重异常人数*/
	private Integer excWeightNum;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getLowWeightNum() {
		return lowWeightNum;
	}
	public void setLowWeightNum(Integer lowWeightNum) {
		this.lowWeightNum = lowWeightNum;
	}
	public Integer getHighWeightNum() {
		return highWeightNum;
	}
	public void setHighWeightNum(Integer highWeightNum) {
		this.highWeightNum = highWeightNum;
	}
	public Integer getExcWeightNum() {
		return excWeightNum;
	}
	public void setExcWeightNum(Integer excWeightNum) {
		this.excWeightNum = excWeightNum;
	}
}
