package com.jumper.weight.nutrition.recipes.mapper;

import java.util.List;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesDetail;

public interface StandardRecipesDetailMapper extends BaseMapper<StandardRecipesDetail>{

	/**
	 * 通过方案id查询标准食谱
	 * @param planId 方案id
	 * @return
	 */
	List<StandardRecipesDetail> findStandardRecipesByPlan(int planId);

	/**
	 * 删除方案下的食谱
	 * @param planId 方案id
	 */
	void deleteRecipesDetailByPlan(int planId);
	
}
