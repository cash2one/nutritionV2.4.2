package com.jumper.weight.nutrition.sport.vo;

import java.util.List;
import java.util.Set;

/**
 * 运动分析返回值vo
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-26
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoSportAnalyze {
	public Set<String> sportList;
	public List<VoDailySportAnalyze> dailyList;
	public Set<String> getSportList() {
		return sportList;
	}
	public void setSportList(Set<String> sportList) {
		this.sportList = sportList;
	}
	public List<VoDailySportAnalyze> getDailyList() {
		return dailyList;
	}
	public void setDailyList(List<VoDailySportAnalyze> dailyList) {
		this.dailyList = dailyList;
	}
}
