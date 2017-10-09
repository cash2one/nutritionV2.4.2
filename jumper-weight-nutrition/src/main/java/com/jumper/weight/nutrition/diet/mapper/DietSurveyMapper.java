package com.jumper.weight.nutrition.diet.mapper;

import java.util.List;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.diet.entity.DietSurvey;
/**
 * 膳食调查mapper
 * @author gyx
 * @time 2017年5月11日
 */
public interface DietSurveyMapper extends BaseMapper<DietSurvey>{

	/**
	 * 根据门诊id查询用户膳食调查记录
	 * @param outpatientId 门诊id
	 * @return
	 */
	List<DietSurvey> findUserDietSurveyList(int outpatientId);
	
	/**
	 * 通过门诊id删除用户膳食调查记录
	 * @param outpatientId 门诊id
	 */
	void deleteUserDietSurveyByOutpId(int outpatientId);
	
}
