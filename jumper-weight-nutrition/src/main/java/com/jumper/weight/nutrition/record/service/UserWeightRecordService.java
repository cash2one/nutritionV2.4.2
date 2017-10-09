package com.jumper.weight.nutrition.record.service;

import java.util.List;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.record.entity.UserWeightRecord;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.record.vo.VoWeightChart;

public interface UserWeightRecordService extends BaseService<UserWeightRecord> {
	/**
	 * 添加或更新用户体重记录
	 * @createTime 2017-4-27,上午11:30:21
	 * @createAuthor fangxilin
	 * @param vo
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	boolean addOrUpdateWeightRecord(VoUserWeightRecord vo, int userId, int hospitalId);
	
	/**
	 * 查询用户最近的一条体重记录 
	 * @createTime 2017-4-27,下午4:51:06
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	VoUserWeightRecord findUserLastWeight(int userId, int hospitalId);
	
	/**
	 * 通过userIds集合查询最近的体重记录
	 * @createTime 2017-4-28,下午2:03:30
	 * @createAuthor fangxilin
	 * @param userIds
	 * @return
	 */
	List<VoUserWeightRecord> listUserLastWeight(List<Integer> userIds);
	
	/**
	 * 获取用户体重曲线图数据
	 * @createTime 2017-5-5,下午2:04:37
	 * @createAuthor fangxilin
	 * @param userId
	 * @param type 获取类型（0：近7天，1：所有）
	 * @return
	 */
	VoWeightChart getWeightChartData(int userId, int type, int hospitalId);
	
	/**
	 * 获取报告中用户体重曲线图数据
	 * @createTime 2017-6-30,下午2:25:48
	 * @createAuthor fangxilin
	 * @param reportId 报告id
	 * @return
	 */
	VoWeightChart getRepWeiChartData(int reportId);

	
}
