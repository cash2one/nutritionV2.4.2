package com.jumper.weight.nutrition.user.vo;

import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;

public class VoHospitalUserManage {
	
	private Integer id;
	/** 更新时间（最近一次完成门诊的时间） */
	private String lastOutpatientTime;
	private VoUserWeightRecord voWeightRecord;
	private VoUserInfo voUserInfo;
	private Double currentSugar;
	/**血糖状态0：偏低，1：正常，2：偏高*/
	private Integer sugarStatus;
	//未读消息数,默认为0
	private Integer unReadMsgs = 0;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLastOutpatientTime() {
		return lastOutpatientTime;
	}
	public void setLastOutpatientTime(String lastOutpatientTime) {
		this.lastOutpatientTime = lastOutpatientTime;
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
	public Double getCurrentSugar() {
		return currentSugar;
	}
	public void setCurrentSugar(Double currentSugar) {
		this.currentSugar = currentSugar;
	}
	public Integer getSugarStatus() {
		return sugarStatus;
	}
	public void setSugarStatus(Integer sugarStatus) {
		this.sugarStatus = sugarStatus;
	}
	public Integer getUnReadMsgs() {
		return unReadMsgs;
	}
	public void setUnReadMsgs(Integer unReadMsgs) {
		this.unReadMsgs = unReadMsgs;
	}
	
}
