package com.jumper.weight.nutrition.user.service;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;

public interface HospitalUserInfoService extends BaseService<HospitalUserInfo> {
	/**
	 * 添加或更新医院用户信息
	 * @createTime 2017-4-27,上午10:27:00
	 * @createAuthor fangxilin
	 * @param hospUser
	 * @return
	 */
	boolean addOrUpdateHospUser(HospitalUserInfo hospUser);

	/**
	 * 通过openID查询医院用户信息
	 * @param hospitalId 医院ID
	 * @param channel 渠道
	 * @param openId
	 * @return
	 */
	HospitalUserInfo findHospitalUserInfoByOpenId(Integer hospitalId,
			String channel, String openId);

	/**
	 * 保存用户openID
	 * @param userId 用户ID
	 * @param channel 渠道
	 * @param openId
	 * @return
	 */
	boolean saveUserOpenId(int userId, int channel, String openId);

	/**
	 * 通过医院ID和用户ID查询医院用户信息
	 * @param hospitalId 医院ID
	 * @param userId 用户ID
	 * @return
	 */
	HospitalUserInfo findHospitalUserInfo(int hospitalId, int userId);
}
