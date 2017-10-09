package com.jumper.weight.nutrition.user.vo;

/**
 * 孕妇管理分页查询参数
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-8
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class VoQueryUserManage {
	private Integer hospitalId;
	/** 搜索关键字：姓名或手机号 */
	private String query;
	/** 体重状态 0：偏瘦，1：标准，2：偏胖，3：肥胖*/
	private Integer weightStatus;
	/** 开始筛选的预产期 */
	private String startExpDate;
	/** 结束筛选的预产期 */
	private String endExpDate;
	/** 多少天未秤体重类型  0：从未称重，1：正常小于3天，2：3天，3：5天，4：7天，5：14天 */
	private Integer weightExceptionType;
	/**是否是妊娠期糖尿病 0：否，1：是，null：查询所有*/
	private Integer isDiabetes;
	/**需要排序的字段（列）和数据库字段名一样*/
	private String orderRow;
	/**需要排序的字段（列）0：asc升序，1：desc降序*/
	private Integer orderType;
	/** 当前页 */
	private Integer page;
	/** 每页大小 */
	private Integer limit;
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Integer getWeightStatus() {
		return weightStatus;
	}
	public void setWeightStatus(Integer weightStatus) {
		this.weightStatus = weightStatus;
	}
	public String getStartExpDate() {
		return startExpDate;
	}
	public void setStartExpDate(String startExpDate) {
		this.startExpDate = startExpDate;
	}
	public String getEndExpDate() {
		return endExpDate;
	}
	public void setEndExpDate(String endExpDate) {
		this.endExpDate = endExpDate;
	}
	public Integer getWeightExceptionType() {
		return weightExceptionType;
	}
	public void setWeightExceptionType(Integer weightExceptionType) {
		this.weightExceptionType = weightExceptionType;
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
	public String getOrderRow() {
		return orderRow;
	}
	public void setOrderRow(String orderRow) {
		this.orderRow = orderRow;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getIsDiabetes() {
		return isDiabetes;
	}
	public void setIsDiabetes(Integer isDiabetes) {
		this.isDiabetes = isDiabetes;
	}
	
}
