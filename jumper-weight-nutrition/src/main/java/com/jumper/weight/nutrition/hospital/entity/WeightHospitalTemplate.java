package com.jumper.weight.nutrition.hospital.entity;

import java.util.Date;

public class WeightHospitalTemplate {
    private Integer id;

    //医院id
    private Integer hospitalId;

    //模板类型：1表示短信模板；2表示用户复诊就诊原因；3表示初诊用户扫码文案；4表示复诊用户扫码文案
    private Integer type;

    //模板内容
    private String content;
    
    //添加时间
    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
    
    
}