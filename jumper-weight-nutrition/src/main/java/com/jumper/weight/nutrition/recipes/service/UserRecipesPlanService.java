package com.jumper.weight.nutrition.recipes.service;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.recipes.entity.UserRecipesPlan;
import com.jumper.weight.nutrition.recipes.vo.VOUserRecipesList;

public interface UserRecipesPlanService extends BaseService<UserRecipesPlan>{

	/**
	 * 获取某次门诊用户食谱列表
	 * @param hospitalId 医院id
	 * @param userId 用户id
	 * @param outpatientId 门诊id
	 * @return
	 */
	List<UserRecipesPlan> findUserRecipesPlans(int hospitalId, int userId,
			int outpatientId);

	/**
	 * 获取或另存用户食谱列表
	 * @param hospitalId 医院id
	 * @param userId 用户id
	 * @param outpatientId 门诊id
	 * @return
	 */
	List<UserRecipesPlan> saveAsOrFindUserRecipesPlans(int hospitalId,
			int userId, int outpatientId);

	/**
	 * 导入已配置好的方案作为用户食谱
	 * @param hospitalId 医院id
	 * @param userId 用户id
	 * @param outpatientId 门诊id
	 * @return
	 */
	List<UserRecipesPlan> importUserRecipesPlans(int hospitalId, int userId,
			int outpatientId, int planId);

	/**
	 * 保存用户食谱信息
	 * @param userRecipesPlan
	 * @return
	 */
	UserRecipesPlan saveUserRecipesPlan(UserRecipesPlan userRecipesPlan);

	/**
	 * 获取某次门诊用户食谱列表,没有就返回空
	 * @param hospitalId
	 * @param userId
	 * @param outpatientId
	 * @return
	 */
	List<UserRecipesPlan> findUserRecipesPlansByOutPatientId(int outpatientId);

	/**
	 * h5页面查看用户最新食谱列表
	 * @param hospitalId 医院id
	 * @param userId 用户id
	 * @return
	 */
	Map<String, Object> findUserRecipesList(int hospitalId, int userId);

	VOUserRecipesList findUserRecipesById(int recipesId);

}
