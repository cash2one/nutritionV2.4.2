package com.jumper.weight.nutrition.recipes.service;

import java.util.List;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesPlan;

public interface StandardRecipesPlanService extends BaseService<StandardRecipesPlan>{

	/**
	 * 条件获取食谱方案列表
	 * @param hospitalId 医院id
	 * @param keywords 方案名称关键字
	 * @return
	 */
	List<StandardRecipesPlan> findStandardRecipesPlans(int hospitalId,
			String keywords);

	/**
	 * 删除食谱方案
	 * @param planId 方案id
	 * @return
	 */
	boolean deleteStandardRecipesPlan(int planId);

	/**
	 * 保存方案
	 * @param recipesPlan 方案信息
	 * @return
	 */
	StandardRecipesPlan saveStandardRecipesPlan(StandardRecipesPlan recipesPlan);

	/**
	 * 查询方案名是否已存在
	 * @param recipesPlan
	 * @return
	 */
	StandardRecipesPlan findStandardRecipesPlanByName(
			StandardRecipesPlan recipesPlan);
	
}
