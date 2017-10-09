package com.jumper.weight.common.enums;

/**
 * 未称重类型枚举
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-6
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public enum WeightExceptionType {
	neven(0, -1, "从未秤重"),
	normal(1, 3, "3天以内秤过重"),
	three(2, 5, "3天未称重"),
	five(3, 7, "5天未称重"),
	seven(4, 14, "7天未称重"),
	fourteen(5, 14, "14天未称重");
	
	private int type;
	private int day;
	private String comment;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	private WeightExceptionType(int type, int day, String comment) {
		this.type = type;
		this.day = day;
		this.comment = comment;
	}
	
}
