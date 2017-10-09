package com.jumper.weight.nutrition.diet.service;

import java.util.List;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.diet.entity.DietSurvey;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfoPage;
/**
 * 膳食调查service
 * @author gyx
 * @time 2017年5月11日
 */
public interface DietSurveyService extends BaseService<DietSurvey>{

	/**
	 * 根据门诊id查询用户膳食调查记录
	 * @param outpatientId 门诊id
	 * @param type 类型
	 * @return
	 */
	List<VOWeightMealsInfoPage> findUserDietSurveyList(int outpatientId, int type);

	/**
	 * 保存用户膳食调查记录
	 * @param dietSurvey
	 * @return
	 */
	DietSurvey saveUserDietSurvey(DietSurvey dietSurvey);

}
