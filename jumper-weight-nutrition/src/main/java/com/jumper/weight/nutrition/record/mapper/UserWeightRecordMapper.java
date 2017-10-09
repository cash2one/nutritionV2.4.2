package com.jumper.weight.nutrition.record.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.record.entity.UserWeightRecord;

public interface UserWeightRecordMapper extends BaseMapper<UserWeightRecord> {
	
	/**
	 * 按日期查询是否有某日的体重记录
	 * @createTime 2017-4-27,上午11:43:19
	 * @createAuthor fangxilin
	 * @param nowDate
	 * @param userId
	 * @return
	 */
	UserWeightRecord findWeightRecordByDate(@Param("nowDate") String nowDate, @Param("userId") int userId);
	
	/**
	 * 查询用户最近的一条体重记录 
	 * @createTime 2017-4-27,下午4:48:57
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	UserWeightRecord findUserLastWeight(int userId);
	
	/**
	 * 通过userIds集合查询最近的体重记录
	 * @createTime 2017-4-28,下午2:03:04
	 * @createAuthor fangxilin
	 * @param userIds
	 * @return
	 */
	List<UserWeightRecord> listUserLastWeight(List<Integer> userIds);
	
	/**
	 * 获取日期范围内的体重列表
	 * @createTime 2017-5-5,下午2:08:45
	 * @createAuthor fangxilin
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<UserWeightRecord> listWeightByDuring(@Param("userId") int userId, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	/**
	 * 连医院用户表查询所有医院用户的最新体重记录
	 * @createTime 2017-7-31,下午1:57:17
	 * @createAuthor fangxilin
	 * @return
	 */
	List<UserWeightRecord> listHospUserWeights();
}