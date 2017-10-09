package com.jumper.weight.nutrition.recipes.service;

import java.util.List;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesDetail;

public interface StandardRecipesDetailService extends  BaseService<StandardRecipesDetail>{

	/**
	 * 查询方案下的食谱信息
	 * @param planId
	 * @return
	 */
	List<StandardRecipesDetail> findStandardRecipesByPlan(int planId);

	/**
	 * 保存食谱信息
	 * @param recipesDetail 食谱信息
	 * @return
	 */
	StandardRecipesDetail saveStandardRecipesDetail(
			StandardRecipesDetail recipesDetail);
	
}
