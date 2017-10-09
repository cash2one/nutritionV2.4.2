package com.jumper.weight.nutrition.user.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.user.entity.HospitalUserManage;
import com.jumper.weight.nutrition.user.vo.VoHospitalUserManage;
import com.jumper.weight.nutrition.user.vo.VoQueryUserManage;
import com.jumper.weight.nutrition.user.vo.VoWeightChartSta;

public interface HospitalUserManageService extends BaseService<HospitalUserManage> {
	
	/**
	 * 通过各条件筛选孕妇管理分页列表
	 * @createTime 2017-5-8,下午4:59:37
	 * @createAuthor fangxilin
	 * @param voQuery
	 * @return
	 */
	PageInfo<VoHospitalUserManage> listUserManageByPage(VoQueryUserManage voQuery);
	
	/**
	 * 保存一条孕妇管理记录
	 * @createTime 2017-5-16,上午9:29:03
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	boolean saveUserManage(int hospitalId, int userId) throws Exception;
	
	/**
	 * 获取孕妇档案中体重异常统计
	 * @createTime 2017-6-26,上午11:41:48
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	VoWeightChartSta getWeightStatistics(int hospitalId);
	
	/**
	 * 获取孕妇档案中体重异常图表统计
	 * @createTime 2017-6-26,上午11:41:48
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	List<VoWeightChartSta> getWeightChartSta(int hospitalId, String startDate, String endDate);
	
	/**
	 * 删除孕妇档案
	 * @createTime 2017-6-26,下午5:22:37
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	boolean deleteUserManage(int hospitalId, int userId);

	/**
	 * 根据用户ID和医院ID查询用户档案记录
	 * @param hospitalId 医院ID
	 * @param userId 用户ID
	 * @return
	 */
	HospitalUserManage findHospitalUserManage(int hospitalId, int userId);

	/**
	 * 
	 * 查询未读消息条数
	 * @Title: getNoRead
	 * @param: @param hospitalId
	 * @param: @return
	 * @return: Integer
	 */
	Integer getNoRead(int hospitalId);
}
