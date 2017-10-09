package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class HospitalOutpatient extends BaseEntity {

	private static final long serialVersionUID = 1L;

    private Integer hospitalId;

    private Integer userId;

    private Date outpatientTime;

    private Integer status;

    private Integer isFinish;

    private String dietAdvice;

    private String doctorAdvice;

    private String userDefinedSport;
    
    private Date finishTime;
    
    private Integer outpatientReason;

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOutpatientTime() {
        return outpatientTime;
    }

    public void setOutpatientTime(Date outpatientTime) {
        this.outpatientTime = outpatientTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public String getDietAdvice() {
        return dietAdvice;
    }

    public void setDietAdvice(String dietAdvice) {
        this.dietAdvice = dietAdvice == null ? null : dietAdvice.trim();
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice == null ? null : doctorAdvice.trim();
    }

    public String getUserDefinedSport() {
        return userDefinedSport;
    }

    public void setUserDefinedSport(String userDefinedSport) {
        this.userDefinedSport = userDefinedSport == null ? null : userDefinedSport.trim();
    }

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getOutpatientReason() {
		return outpatientReason;
	}

	public void setOutpatientReason(Integer outpatientReason) {
		this.outpatientReason = outpatientReason;
	}
    
}