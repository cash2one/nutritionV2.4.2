package com.jumper.weight.nutrition.hospital.entity;

import com.jumper.weight.common.base.BaseEntity;

/**
 * 设置表
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-22
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class WeightHospitalSetting extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    private Integer hospitalId;

    //报告中是否隐藏膳食调查食物清单 0：否，1：是
    private Integer hideDiet;

    //报告中是否隐藏运动调查清单 0：否，1：是
    private Integer hideSport;

    //报告中是否隐藏营养食物表 0：否，1：是
    private Integer hideFoodtab;

    //报告中是否隐藏食物交换份 0：否，1：是
    private Integer hideExchange;
    
    /**安全体重范围计算公式  0：默认，1：宝安妇幼版公式*/
    private Integer safeFormula;
    
    //是否开启营养咨询 0：否，1：是
    private Integer nutritionConsult;
    
    public WeightHospitalSetting() {
    	
    }
    
    /**
     * 不传safeFormula，插入新记录时会有默认值
     */
    public WeightHospitalSetting(Integer hospitalId, Integer hideDiet,
			Integer hideSport, Integer hideFoodtab, Integer hideExchange, Integer nutritionConsult) {
		this.hospitalId = hospitalId;
		this.hideDiet = hideDiet;
		this.hideSport = hideSport;
		this.hideFoodtab = hideFoodtab;
		this.hideExchange = hideExchange;
		this.nutritionConsult = nutritionConsult;
	}
    
	public WeightHospitalSetting(Integer hospitalId, Integer hideDiet,
			Integer hideSport, Integer hideFoodtab, Integer hideExchange, 
			Integer safeFormula, Integer nutritionConsult) {
		this.hospitalId = hospitalId;
		this.hideDiet = hideDiet;
		this.hideSport = hideSport;
		this.hideFoodtab = hideFoodtab;
		this.hideExchange = hideExchange;
		this.safeFormula = safeFormula;
		this.nutritionConsult = nutritionConsult;
	}

	public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getHideDiet() {
        return hideDiet;
    }

    public void setHideDiet(Integer hideDiet) {
        this.hideDiet = hideDiet;
    }

    public Integer getHideSport() {
        return hideSport;
    }

    public void setHideSport(Integer hideSport) {
        this.hideSport = hideSport;
    }

    public Integer getHideFoodtab() {
        return hideFoodtab;
    }

    public void setHideFoodtab(Integer hideFoodtab) {
        this.hideFoodtab = hideFoodtab;
    }

    public Integer getHideExchange() {
        return hideExchange;
    }

    public void setHideExchange(Integer hideExchange) {
        this.hideExchange = hideExchange;
    }
	public Integer getSafeFormula() {
		return safeFormula;
	}
	public void setSafeFormula(Integer safeFormula) {
		this.safeFormula = safeFormula;
	}
	public Integer getNutritionConsult() {
		return nutritionConsult;
	}
	public void setNutritionConsult(Integer nutritionConsult) {
		this.nutritionConsult = nutritionConsult;
	}
	
    
}