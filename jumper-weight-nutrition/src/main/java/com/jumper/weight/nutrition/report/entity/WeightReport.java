package com.jumper.weight.nutrition.report.entity;

import java.util.Date;

/**
 * 体重营养报告表
 * @author gyx
 * @time 2017年5月3日
 */
public class WeightReport {
    private Integer id;

    //用户id
    private Integer userId;

    //医院id
    private Integer hospitalId;

    //门诊id
    private Integer outpatientId;

    //医院名称
    private String hospitalName;

    //用户基本信息
    private String userMsg;

    //添加时间
    private Date addTime;
    
    //报告编号
    private String reportNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getOutpatientId() {
        return outpatientId;
    }

    public void setOutpatientId(Integer outpatientId) {
        this.outpatientId = outpatientId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg == null ? null : userMsg.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}
    
}