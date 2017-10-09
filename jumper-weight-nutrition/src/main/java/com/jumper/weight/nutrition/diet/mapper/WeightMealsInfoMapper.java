package com.jumper.weight.nutrition.diet.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.diet.entity.WeightMealsInfo;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;

public interface WeightMealsInfoMapper extends BaseMapper<WeightMealsInfo>{

	/**
	 * 条件查询用户饮食记录
	 * @param map
	 * @return
	 */
	List<WeightMealsInfo> findUserMealsInfoList(Map<String, Object> map);

	/**
	 * 获取用户饮食记录分页日期
	 * @param map
	 * @return
	 */
	List<String> findUserPageEatDates(Map<String, Object> map);

	/**
	 * 获取用户饮食记录分页信息
	 * @param map
	 * @return
	 */
	List<WeightMealsInfo> findAllUserMealsInfoList(Map<String, Object> map);

	/**
	 * 条件查询用户饮食记录是否存在
	 * @param weightMealsInfo
	 * @return
	 */
	WeightMealsInfo findUserMealsInfoByConds(WeightMealsInfo weightMealsInfo);

	/**
	 * 查询报告页用户饮食记录
	 * @param dietMsg
	 * @return
	 */
	List<VOWeightMealsInfo> findUserMealsInfoByDietDate(Map<String, Object> map);

	/**
	 * 删除用户一整天的饮食记录
	 * @param param
	 */
	void deleteUserMealsInfo(Map<String, Object> param);

	/**
	 * 重命名用户饮食记录日期
	 * @param param
	 */
	void reNameUserMealsInfo(Map<String, Object> param);

	/**
	 * 条件获取用户饮食记录条数
	 * @param param
	 * @return
	 */
	int findCount(Map<String, Object> param);

	/**
	 * 查询用户近7天的饮食记录日期列表
	 * @param userId 用户id
	 * @return
	 */
	List<String> findUserLatestSevenDays(int userId);

	/**
	 * 获取近7天用户饮食记录总食物个数
	 * @param param
	 * @return
	 */
	int findFoodCount(Map<String, Object> param);

}
