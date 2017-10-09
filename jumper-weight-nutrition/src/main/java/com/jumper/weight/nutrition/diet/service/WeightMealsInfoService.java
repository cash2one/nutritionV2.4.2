package com.jumper.weight.nutrition.diet.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.diet.entity.WeightMealsInfo;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfoPage;
import com.jumper.weight.nutrition.report.vo.VOWeightReport;

/**
 * 用户饮食记录service
 * @author gyx
 * @time 2017年4月26日
 */
public interface WeightMealsInfoService extends BaseService<WeightMealsInfo>{

	/**
	 * 条件查询用户饮食记录列表
	 * @param userId 用户id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	List<VOWeightMealsInfo> findUserMealsInfoList(int userId, String startTime,
			String endTime, int mealsType);

	/**
	 * 获取用户分页饮食记录信息
	 * @param userId 用户id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param pageIndex 分页索引
	 * @param pageSize 分页大小
	 * @return
	 */
	PageInfo findAllUserMealsInfoList(int userId,
			String startTime, String endTime, int pageIndex, int pageSize);

	/**
	 * 保存用户饮食信息
	 * @param mealsInfoList 饮食信息
	 * @param userId 用户id
	 * @param eatDate 调查的饮食日期
	 * @return
	 */
	boolean saveUserMealsInfo(List<WeightMealsInfo> mealsInfoList, int userId,
			String eatDate);


	/**
	 * 删除用户某一天的饮食记录
	 * @param userId 用户id
	 * @param eatDate 饮食日期
	 * @return
	 */
	boolean deleteUserMealsInfo(int userId, String eatDate);

	/**
	 * 重命名用户饮食记录日期
	 * @param userId 用户id
	 * @param oldDate 旧的日期
	 * @param newDate 新的日期
	 * @return
	 */
	boolean reNameUserMealsInfo(int userId, String oldDate, String newDate);

	/**
	 * 查询用户近7天的饮食记录日期列表
	 * @param userId 用户id
	 * @return
	 */
	List<String> findUserLatestSevenDays(int userId);

	/**
	 * 获取用户饮食记录条数
	 * @param userId 用户id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	int findCount(int userId, String startTime, String endTime);

	/**
	 * 获取近7天用户饮食记录总食物个数
	 * @param userId
	 * @param dateList
	 * @return
	 */
	int findFoodCount(int userId, List<String> dateList);

}
