package com.jumper.weight.nutrition.recipes.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesPlan;

/**
 * 食谱方案mapper
 * @author gyx
 * @time 2017年4月27日
 */
public interface StandardRecipesPlanMapper extends BaseMapper<StandardRecipesPlan>{

	/**
	 * 条件获取食谱方案列表
	 * @param param
	 * @return
	 */
	List<StandardRecipesPlan> findStandardRecipesPlans(Map<String, Object> param);

	/**
	 * 通过方案名称查询方案名是否重复
	 * @param recipesPlan
	 * @return
	 */
	StandardRecipesPlan findStandardRecipesPlanByName(
			StandardRecipesPlan recipesPlan);
	
}
