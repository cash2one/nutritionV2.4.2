package com.jumper.weight.nutrition.diet.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户饮食记录分页VO
 * @author gyx
 * @time 2017年4月27日
 */
public class VOWeightMealsInfoPage implements Serializable{

	private static final long serialVersionUID = -4381026663470949073L;

	private int dietSurveyId;//膳食调查记录id
    private String eatDate;
    
    private List<VOWeightMealsInfo> infoList = new ArrayList<VOWeightMealsInfo>();
    
	public int getDietSurveyId() {
		return dietSurveyId;
	}

	public void setDietSurveyId(int dietSurveyId) {
		this.dietSurveyId = dietSurveyId;
	}

	public String getEatDate() {
		return eatDate;
	}

	public void setEatDate(String eatDate) {
		this.eatDate = eatDate;
	}

	public List<VOWeightMealsInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<VOWeightMealsInfo> infoList) {
		this.infoList = infoList;
	}
    
    
}
