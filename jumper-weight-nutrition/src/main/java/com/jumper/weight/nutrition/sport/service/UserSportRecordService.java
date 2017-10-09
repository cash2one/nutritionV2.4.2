package com.jumper.weight.nutrition.sport.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.sport.entity.UserSportRecord;
import com.jumper.weight.nutrition.sport.vo.VoDailySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoQuerySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoUserSportRecord;

public interface UserSportRecordService extends BaseService<UserSportRecord> {
	/**
	 * 添加或更新用户某天的运动记录
	 * @createTime 2017-5-2,上午9:59:21
	 * @createAuthor fangxilin
	 * @param userId
	 * @param date 运动的日期
	 * @param voList
	 * @return
	 */
	boolean addSportRecordByDate(int userId, String date, List<VoUserSportRecord> voList);
	
	/**
	 * 查询用户某天的运动记录
	 * @createTime 2017-5-2,下午3:35:32
	 * @createAuthor fangxilin
	 * @param userId
	 * @param date
	 * @return
	 */
	VoDailySportRecord listSportRecordByDate(int userId, String date);
	
	/**
	 * 分页查询用户时间段内的运动记录
	 * @createTime 2017-5-4,下午4:40:47
	 * @createAuthor fangxilin
	 * @param voQuery
	 * @return
	 */
	PageInfo<VoDailySportRecord> listSportRecordByDuring(VoQuerySportRecord voQuery);
	
	/**
	 * 通过日期集合查询运动记录
	 * @createTime 2017-5-4,下午5:57:07
	 * @createAuthor fangxilin
	 * @param userId
	 * @param dates 日期集合
	 * @return
	 */
	List<VoDailySportRecord> listSportRecordByDates(int userId, List<String> dates);
}
