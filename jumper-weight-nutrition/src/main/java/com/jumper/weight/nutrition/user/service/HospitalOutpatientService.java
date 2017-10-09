package com.jumper.weight.nutrition.user.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.vo.VoHospitalOutpatient;
import com.jumper.weight.nutrition.user.vo.VoOutpChartSta;
import com.jumper.weight.nutrition.user.vo.VoOutpStatistics;

public interface HospitalOutpatientService extends BaseService<HospitalOutpatient> {
	/**
	 * 搜索手机号或姓名分页显示门诊列表 
	 * @createTime 2017-4-28,下午2:17:15
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param query
	 * @return
	 */
	PageInfo<VoHospitalOutpatient> listOutpatientUser(int hospitalId, String query, int page, int limit, int status);
	
	/**
	 * 通过门诊id批量删除
	 * @createTime 2017-4-28,下午4:53:00
	 * @createAuthor fangxilin
	 * @param idList
	 * @return
	 */
	boolean deleteOutpatient(List<Integer> idList);
	
	/**
	 * 查询用户某个医院的初诊记录
	 * @createTime 2017-4-28,下午6:41:55
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	HospitalOutpatient findUserFirstOutpatient(int userId, int hospitalId);
	
	/**
	 * 添加一条门诊记录
	 * @createTime 2017-4-28,下午6:13:33
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @param userDefinedSport 用户自定义运动
	 * @return 门诊id，失败返回0
	 */
	int addOutpatient(int userId, int hospitalId, String userDefinedSport, int outpatientReason);
	
	/**
	 * 获取医院当月的门诊统计数据
	 * @createTime 2017-5-4,下午6:56:39
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	VoOutpStatistics getOutpStatistics(int hospitalId);
	
	/**
	 * 查询用户门诊记录（诊疗历史）
	 * @createTime 2017-5-5,下午3:40:30
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @return
	 */
	List<VoHospitalOutpatient> listUserOutpatient(int hospitalId, int userId);
	
	/**
	 * 更新门诊记录（用于诊断）
	 * @createTime 2017-5-16,上午9:25:38
	 * @createAuthor fangxilin
	 * @param bean
	 * @return
	 */
	boolean updateOutp(HospitalOutpatient bean);
	
	/**
	 * 查询用户某个医院的最新的一条门诊记录
	 * @createTime 2017-6-2,下午3:07:45
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @param isFinish 是否完成诊断 0：否，1：是，null：查询最近一条，不管是否完成诊断
	 * @return
	 */
	HospitalOutpatient findUserLastOutpatient(int hospitalId, int userId, Integer isFinish);
	
	/**
	 * 获取门诊图表统计数据
	 * @createTime 2017-6-23,下午4:24:11
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<VoOutpChartSta> getOutpChartSta(int hospitalId, String startDate, String endDate);
	
	/**
	 * 通过门诊id查询门诊信息
	 * @createTime 2017年9月22日,上午10:59:31
	 * @createAuthor fangxilin
	 * @return
	 */
	VoHospitalOutpatient findOutpById(int outpatientId);
}
