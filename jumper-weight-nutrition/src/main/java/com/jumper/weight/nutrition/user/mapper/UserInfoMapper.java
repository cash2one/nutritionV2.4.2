package com.jumper.weight.nutrition.user.mapper;

import java.util.List;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.user.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
	/**
	 * 连表user_extra_info查询用户信息
	 * @createTime 2017-4-26,下午2:14:47
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	UserInfo findUserByUserId(Integer userId);
	
	/**
	 * 连表user_extra_info查询用户列表信息
	 * @createTime 2017-4-26,下午4:09:11
	 * @createAuthor fangxilin
	 * @param userIds
	 * @return
	 */
	List<UserInfo> listUserByUserId(List<Integer> userIds);
	
	/**
	 * 通过手机号查询
	 * @createTime 2017-4-27,下午2:27:07
	 * @createAuthor fangxilin
	 * @param mobile
	 * @return
	 */
	UserInfo findByMobile(String mobile);
	
	/**
	 * 连体重门诊表查询所有体重用户信息
	 * @createTime 2017-5-6,下午8:17:46
	 * @createAuthor fangxilin
	 * @return
	 */
	List<UserInfo> listOutpUsersInfo();
}