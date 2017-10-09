package com.jumper.weight.nutrition.sport.vo;

/**
 * 运动记录分页查询参数
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-4
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoQuerySportRecord {
	private Integer userId;
	/** 筛选：开始日期 */
	private String startDate;
	/** 筛选：开始日期 */
	private String endDate;
	/** 当前页 */
	private Integer page;
	/** 每页大小 */
	private Integer limit;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
