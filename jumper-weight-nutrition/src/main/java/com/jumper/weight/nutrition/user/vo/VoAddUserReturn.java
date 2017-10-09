package com.jumper.weight.nutrition.user.vo;

/**
 * 新增一个用户时，返回的参数
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-11
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoAddUserReturn {
	private Integer userId;
	private Integer outpatientId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOutpatientId() {
		return outpatientId;
	}
	public void setOutpatientId(Integer outpatientId) {
		this.outpatientId = outpatientId;
	}
	public VoAddUserReturn(Integer userId, Integer outpatientId) {
		this.userId = userId;
		this.outpatientId = outpatientId;
	}
}
