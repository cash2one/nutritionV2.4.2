package com.jumper.weight.nutrition.sport.vo;

import java.util.List;

/**
 * 每日运动调查分析vo
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-26
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoDailySportAnalyze {
	private String sportDate;
	List<VoSportChart> recordList;
	public String getSportDate() {
		return sportDate;
	}
	public void setSportDate(String sportDate) {
		this.sportDate = sportDate;
	}
	public List<VoSportChart> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<VoSportChart> recordList) {
		this.recordList = recordList;
	}
}
