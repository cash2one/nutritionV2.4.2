package com.jumper.weight.nutrition.user.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;


public interface HospitalUserInfoMapper extends BaseMapper<HospitalUserInfo> {
	
	/**
	 * 通过医院id用户id查询医院用户
	 * @createTime 2017-4-26,下午2:44:07
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	HospitalUserInfo findHospUserByUIdHospId(@Param("userId") Integer userId, @Param("hospitalId") Integer hospitalId);
	
	/**
	 * 通过医院id用户id集合查询医院用户列表
	 * @createTime 2017-4-26,下午4:28:13
	 * @createAuthor fangxilin
	 * @param userIds
	 * @param hospitalId
	 * @return
	 */
	List<HospitalUserInfo> listHospUserByUIdHospId(@Param("userIds") List<Integer> userIds, @Param("hospitalId") Integer hospitalId);
	
	 /**
	  * 通过医院id和用户id删除
	  * @createTime 2017-7-18,下午6:23:30
	  * @createAuthor fangxilin
	  * @param userId
	  * @param hospitalId
	  * @return
	  */
	int deleteByHospUId(@Param("userId") Integer userId, @Param("hospitalId") Integer hospitalId);
	
	/**
	 * 通过医院id手机号查询医院用户
	 * @createTime 2017-7-28,下午3:32:03
	 * @createAuthor fangxilin
	 * @param mobile
	 * @param hospitalId
	 * @return
	 */
	HospitalUserInfo findHospUserByMobHosp(@Param("mobile") String mobile, @Param("hospitalId") Integer hospitalId);
	
	/**
	 * 连体重，血糖表查询所有医院用户的最新体重，血糖记录 用作定时器维
	 * @createTime 2017年9月14日,下午1:41:31
	 * @createAuthor fangxilin
	 * @return
	 */
	List<HospitalUserInfo> listHospUserPhysical();
}