package com.jumper.weight.nutrition.user.vo;


/**
 * 门诊图表统计
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-23
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoOutpChartSta {
	/**对应的日期*/
	private String date;
	/**门诊人数*/
	private Integer outpNum = 0;
	/**初诊人数*/
	private Integer firstOutpNum = 0;
	/**复诊人数*/
	private Integer repeatOutpNum = 0;
	/**累计门诊人数*/
	private Integer addUpOutpNum = 0;
	/**累计门诊人次*/
	private Integer addUpOutpTimes = 0;
	
	/**门诊人次，计算累计门诊人次时要用到*/
	private Integer outpTimes = 0;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getOutpNum() {
		return outpNum;
	}
	public void setOutpNum(Integer outpNum) {
		this.outpNum = outpNum;
	}
	public Integer getFirstOutpNum() {
		return firstOutpNum;
	}
	public void setFirstOutpNum(Integer firstOutpNum) {
		this.firstOutpNum = firstOutpNum;
	}
	public Integer getRepeatOutpNum() {
		return repeatOutpNum;
	}
	public void setRepeatOutpNum(Integer repeatOutpNum) {
		this.repeatOutpNum = repeatOutpNum;
	}
	public Integer getAddUpOutpNum() {
		return addUpOutpNum;
	}
	public void setAddUpOutpNum(Integer addUpOutpNum) {
		this.addUpOutpNum = addUpOutpNum;
	}
	public Integer getAddUpOutpTimes() {
		return addUpOutpTimes;
	}
	public void setAddUpOutpTimes(Integer addUpOutpTimes) {
		this.addUpOutpTimes = addUpOutpTimes;
	}
	public Integer getOutpTimes() {
		return outpTimes;
	}
	public void setOutpTimes(Integer outpTimes) {
		this.outpTimes = outpTimes;
	}
	
}
