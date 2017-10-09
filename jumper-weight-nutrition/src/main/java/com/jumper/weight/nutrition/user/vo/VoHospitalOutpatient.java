package com.jumper.weight.nutrition.user.vo;

import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;

public class VoHospitalOutpatient {
	private Integer id;
	private String outpatientTime;
	/** 门诊状态0：初诊，1：复诊 */
	private Integer status;
	/** 医生建议 */
	private String doctorAdvice;
	/** 初诊对应的报告id */
	private Integer reportId;
	/** 该门诊是否制定了方案 0：否，1：是 */
	private Integer isMakePlan;
	private String outpatientReason;
	/**自定义运动*/
	private String userDefinedSport;
	
	private VoUserWeightRecord voWeightRecord;
	private VoUserInfo voUserInfo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOutpatientTime() {
		return outpatientTime;
	}
	public void setOutpatientTime(String outpatientTime) {
		this.outpatientTime = outpatientTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public VoUserWeightRecord getVoWeightRecord() {
		return voWeightRecord;
	}
	public void setVoWeightRecord(VoUserWeightRecord voWeightRecord) {
		this.voWeightRecord = voWeightRecord;
	}
	public VoUserInfo getVoUserInfo() {
		return voUserInfo;
	}
	public void setVoUserInfo(VoUserInfo voUserInfo) {
		this.voUserInfo = voUserInfo;
	}
	public String getDoctorAdvice() {
		return doctorAdvice;
	}
	public void setDoctorAdvice(String doctorAdvice) {
		this.doctorAdvice = doctorAdvice;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Integer getIsMakePlan() {
		return isMakePlan;
	}
	public void setIsMakePlan(Integer isMakePlan) {
		this.isMakePlan = isMakePlan;
	}
	public String getOutpatientReason() {
		return outpatientReason;
	}
	public void setOutpatientReason(String outpatientReason) {
		this.outpatientReason = outpatientReason;
	}
	public String getUserDefinedSport() {
		return userDefinedSport;
	}
	public void setUserDefinedSport(String userDefinedSport) {
		this.userDefinedSport = userDefinedSport;
	}
	
}
