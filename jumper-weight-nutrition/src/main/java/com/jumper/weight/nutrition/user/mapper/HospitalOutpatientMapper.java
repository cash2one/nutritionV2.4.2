package com.jumper.weight.nutrition.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;


public interface HospitalOutpatientMapper extends BaseMapper<HospitalOutpatient> {
	/**
	 * 搜索手机号或姓名分页显示门诊列表
	 * @createTime 2017-4-28,上午11:49:18
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param query
	 * @return
	 */
	List<HospitalOutpatient> listOutpatientByQuery(@Param("hospitalId") int hospitalId, @Param("query") String query, @Param("status") int status);
	
	/**
	 * 查询用户某个医院的初诊记录
	 * @createTime 2017-4-28,下午6:20:15
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @return
	 */
	HospitalOutpatient findUserFirstOutpatient(@Param("hospitalId") int hospitalId, @Param("userId") int userId);
	
	/**
	 * 查询用户某个医院的最新的一条门诊记录
	 * @createTime 2017-4-28,下午6:50:50
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @param isFinish 是否完成诊断 0：否，1：是，null：查询最近一条，不管是否完成诊断
	 * @return
	 */
	HospitalOutpatient findUserLastOutpatient(@Param("hospitalId") int hospitalId, @Param("userId") int userId, @Param("isFinish") Integer isFinish);
	
	/**
	 * 查询医院时间段内的门诊记录
	 * @createTime 2017-5-4,下午6:54:14
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<HospitalOutpatient> listOutPByDuring(@Param("hospitalId") int hospitalId, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	/**
	 * 查询用户已完成的门诊记录
	 * @createTime 2017-5-5,下午3:52:01
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @param isFinish 是否完成诊断 0：否，1：是，null：查询所有
	 * @return
	 */
	List<HospitalOutpatient> listUserOutpatient(@Param("hospitalId") int hospitalId, @Param("userId") int userId, @Param("isFinish") Integer isFinish);
}