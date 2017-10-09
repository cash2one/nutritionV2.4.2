package com.jumper.weight.nutrition.hospital.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;
/**
 * 就诊原因entity
 * @author gyx
 * @time 2017年7月18日
 */
public class HospitalOutpatientReason extends BaseEntity{
	private static final long serialVersionUID = 1L;

	//医院id
	private Integer hospitalId;

	//门诊类型：0表示初诊，1表示复诊
    private Integer type;

    //就诊原因
    private String outpatientReason;

    //提示文案
    private String tips;

    //添加时间
    private Date addTime;

    //是否删除：0表示未删除，1表示已删除
    private Boolean isDelete;

    //是否可删除：0表示不能删除，1表示可以删除
    private Boolean canDelete;
    
    //是否可跳过饮食记录：0表示不能跳过，1表示跳过
    private Boolean isSkipDiet;
    
    //是否可跳过运动记录：0表示不能跳过，1表示跳过
    private Boolean isSkipSport;

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

    public String getOutpatientReason() {
        return outpatientReason;
    }

    public void setOutpatientReason(String outpatientReason) {
        this.outpatientReason = outpatientReason == null ? null : outpatientReason.trim();
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

	public Boolean getIsSkipDiet() {
		return isSkipDiet;
	}

	public void setIsSkipDiet(Boolean isSkipDiet) {
		this.isSkipDiet = isSkipDiet;
	}

	public Boolean getIsSkipSport() {
		return isSkipSport;
	}

	public void setIsSkipSport(Boolean isSkipSport) {
		this.isSkipSport = isSkipSport;
	}
    
    
}