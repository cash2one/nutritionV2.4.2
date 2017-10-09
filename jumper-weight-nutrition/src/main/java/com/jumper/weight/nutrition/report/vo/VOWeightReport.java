package com.jumper.weight.nutrition.report.vo;

import java.io.Serializable;
import java.util.List;

import com.jumper.weight.nutrition.user.vo.VOUserMsg;

/**
 * 体重营养报告vo
 * @author gyx
 * @time 2017年5月3日
 */
public class VOWeightReport implements Serializable{
    
	private static final long serialVersionUID = 125816534045117232L;

	private int id;
	
	//用户id
	private int userId;
	
	//门诊id
	private int outpatientId;
	
	//医院id
	private int hospitalId;

    //医院名称
    private String hospitalName;

    //用户基本信息
    private VOUserMsg voUserMsg;
    
    //饮食建议
    private String dietAdvice = "";
    
    //医生建议
    private String doctorAdvice = "";
    
    //自定义运动
    private String userDefinedSport = "";
    
    //添加时间
    private String addTime;
    
    //报告编号
    private String reportNumber = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOutpatientId() {
		return outpatientId;
	}

	public void setOutpatientId(int outpatientId) {
		this.outpatientId = outpatientId;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public VOUserMsg getVoUserMsg() {
		return voUserMsg;
	}

	public void setVoUserMsg(VOUserMsg voUserMsg) {
		this.voUserMsg = voUserMsg;
	}

	public String getDietAdvice() {
		return dietAdvice;
	}

	public void setDietAdvice(String dietAdvice) {
		this.dietAdvice = dietAdvice;
	}

	public String getDoctorAdvice() {
		return doctorAdvice;
	}

	public void setDoctorAdvice(String doctorAdvice) {
		this.doctorAdvice = doctorAdvice;
	}
	
	public String getUserDefinedSport() {
		return userDefinedSport;
	}

	public void setUserDefinedSport(String userDefinedSport) {
		this.userDefinedSport = userDefinedSport;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}
	
	

}