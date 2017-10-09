package com.jumper.weight.nutrition.recipes.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.recipes.entity.UserRecipesPlan;

public interface UserRecipesPlanMapper extends BaseMapper<UserRecipesPlan>{

	/**
	 * 查询本次门诊用户食谱列表
	 * @param param
	 * @return
	 */
	List<UserRecipesPlan> findUserRecipesPlans(int outpatientId);

	/**
	 * 查询用户最近正在使用使用
	 * @param param
	 * @return
	 */
	List<UserRecipesPlan> findUserLeastRecipesPlans(Map<String, Object> param);

	/**
	 * 删除用户某次门诊食谱记录
	 * @param param
	 */
	void deleteUserRecipesPlans(int outpatientId);

}
