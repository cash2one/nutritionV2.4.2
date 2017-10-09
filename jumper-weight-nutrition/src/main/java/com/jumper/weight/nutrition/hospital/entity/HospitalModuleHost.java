package com.jumper.weight.nutrition.hospital.entity;

import com.jumper.weight.common.base.BaseEntity;

/**
 * 医院模块对应的host,只有合作医院才有
 * @author Administrator
 */
public class HospitalModuleHost extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private Integer hospitalId;				//医院id
	private String host;					//地址
	private Integer moduleNum;			//模块编号  1表示建册  类别2表示高危
	private String remark;				//备注
	
	public Integer getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getModuleNum() {
		return moduleNum;
	}
	public void setModuleNum(Integer moduleNum) {
		this.moduleNum = moduleNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
