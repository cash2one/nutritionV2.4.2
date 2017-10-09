package com.jumper.weight.nutrition.food.mapper;

import java.util.List;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.food.entity.WeightFood;
import com.jumper.weight.nutrition.food.vo.VOWeightFood;

public interface WeightFoodMapper extends BaseMapper<WeightFood>{

	/**
	 * 通过食材名称模糊查询食材信息
	 * @param foodName 食材名称
	 * @return
	 */
	List<VOWeightFood> findFoodByName(String foodName);

	/**
	 * 通过食材id数组查询食材信息
	 * @param foodIds 食材id数组
	 * @return
	 */
	List<WeightFood> findFoodByIds(String[] foodIds);
	
}
