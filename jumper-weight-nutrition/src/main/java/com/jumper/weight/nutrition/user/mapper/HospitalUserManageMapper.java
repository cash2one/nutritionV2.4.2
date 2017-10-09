package com.jumper.weight.nutrition.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.user.entity.HospitalUserManage;
import com.jumper.weight.nutrition.user.vo.VoQueryUserManage;

public interface HospitalUserManageMapper extends BaseMapper<HospitalUserManage> {
	
	/**
	 * 连排序表查询孕妇管理列表
	 * @createTime 2017-5-8,下午6:06:00
	 * @createAuthor fangxilin
	 * @param voQuery
	 * @return
	 */
	List<HospitalUserManage> listUserManageByPage(VoQueryUserManage voQuery);
	
	/**
	 * 通过医院id和用户id查询
	 * @createTime 2017-5-16,上午9:35:40
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @return
	 */
	HospitalUserManage findByHospUser(@Param("hospitalId") int hospitalId, @Param("userId") int userId);
	
	/**
	 * 连排序表查询称重时间段内的孕妇管理列表
	 * @createTime 2017-6-26,上午11:54:02
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<HospitalUserManage> listUmByDuring(@Param("hospitalId") int hospitalId, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	/**
	 * 通过医院id和userId删除档案
	 * @createTime 2017-6-26,下午5:40:05
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @return
	 */
	int deleteByHospUId(@Param("hospitalId") int hospitalId, @Param("userId") int userId) throws Exception;
}