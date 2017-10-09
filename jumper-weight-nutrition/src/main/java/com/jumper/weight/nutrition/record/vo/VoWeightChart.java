package com.jumper.weight.nutrition.record.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 体重曲线图vo
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-5
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoWeightChart {
	/** 安全体重范围的集合Object[0]横坐标怀孕天数，Object[1]体重最小值，Object[2]体重最大值 */
	private List<Object[]> safeWeightList = new ArrayList<Object[]>();
	/** 用户体重列表的集合Object[0]横坐标怀孕天数，Object[1]体重值 */
	private List<Object[]> userWeightList = new ArrayList<Object[]>();
	/** 预产期 */
	private String expectedDate;
	/** 体重增长值 */
	private Double weightIncrease;
	/** 推荐孕期体重增长 */
	private Double[] suggestIncrease;
	/** 近7天对应的起始及结束孕周 */
	private Integer[] sevenPweek;
	public List<Object[]> getSafeWeightList() {
		return safeWeightList;
	}
	public void setSafeWeightList(List<Object[]> safeWeightList) {
		this.safeWeightList = safeWeightList;
	}
	public List<Object[]> getUserWeightList() {
		return userWeightList;
	}
	public void setUserWeightList(List<Object[]> userWeightList) {
		this.userWeightList = userWeightList;
	}
	public String getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}
	public Double getWeightIncrease() {
		return weightIncrease;
	}
	public void setWeightIncrease(Double weightIncrease) {
		this.weightIncrease = weightIncrease;
	}
	public Double[] getSuggestIncrease() {
		return suggestIncrease;
	}
	public void setSuggestIncrease(Double[] suggestIncrease) {
		this.suggestIncrease = suggestIncrease;
	}
	public Integer[] getSevenPweek() {
		return sevenPweek;
	}
	public void setSevenPweek(Integer[] sevenPweek) {
		this.sevenPweek = sevenPweek;
	}
	
}
