package com.jumper.weight.nutrition.food.service;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;
import com.jumper.weight.nutrition.food.entity.WeightFood;
import com.jumper.weight.nutrition.food.vo.VOFoodCatagoryAnalyze;
import com.jumper.weight.nutrition.food.vo.VOMealsTypeIntakeAnalyze;
import com.jumper.weight.nutrition.food.vo.VONutritionAnalyze;
import com.jumper.weight.nutrition.food.vo.VOTotalAnalyze;
import com.jumper.weight.nutrition.food.vo.VOWeightFood;
import com.jumper.weight.nutrition.food.vo.VoFoodWeight;

/**
 * 食材信息Service
 * @author gyx
 * @time 2017年4月26日
 */
public interface FoodService extends BaseService<WeightFood>{

	/**
	 * 通过食材名称快捷模糊搜索食材信息
	 * @param foodName 食材名称
	 * @return
	 */
	List<VOWeightFood> findFoodByName(String foodName);

	/**
	 * 通过食材id数组查询食材列表
	 * @param foodIds id数组
	 * @return
	 */
	List<WeightFood> findFoodByIds(String[] foodIds);
	
	/**
	 * 获取食物营养素分析
	 * @createTime 2017-5-17,下午3:06:47
	 * @createAuthor fangxilin
	 * @param foodWeightList
	 * @param day 天数（用于取平均值）
	 * @param userId
	 * @return
	 */
	VONutritionAnalyze getVONutritionAnalyze(List<VoFoodWeight> foodWeightList, int day, Integer userId, Integer hospitalId);
	
	/**
	 * 获取用户推荐卡洛里摄入量
	 * @createTime 2017-5-17,下午3:26:56
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	Integer calculateSugIntake(int userId, int hospitalId);

	/**
	 * 各类食物摄入量分析
	 * @param outpatientId 门诊id
	 * @return
	 */
	Map<String, Object> getFoodCatagoryAnalyzeList(Integer outpatientId);

	/**
	 * 餐次功能比分析
	 * @param outpatientId 门诊id
	 * @return
	 */
	List<VOMealsTypeIntakeAnalyze> getMealsTypeIntakeAnalyzeList(
			Integer outpatientId);

	/**
	 * 获取营养素分析数据
	 * @param outpatientId 门诊id
	 * @return
	 */
	Map<String, Object> getVOMealsInfoList(Integer outpatientId);

	/**
	 * 来源分析
	 * @param voNutritionAnalyze
	 * @return
	 */
	VOTotalAnalyze getVOTotalAnalyze(VONutritionAnalyze voNutritionAnalyze);
}
