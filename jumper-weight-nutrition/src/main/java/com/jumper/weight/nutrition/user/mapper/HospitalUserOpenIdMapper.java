package com.jumper.weight.nutrition.user.mapper;

import java.util.Map;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.entity.HospitalUserOpenId;

public interface HospitalUserOpenIdMapper extends BaseMapper<HospitalUserOpenId>{

	/**
	 * 通过openid查询医院用户信息
	 * @param map
	 * @return
	 */
	HospitalUserInfo findHospitalUserInfoByOpenId(Map<String, Object> map);

	/**
	 * 通过用户ID和openID查询用户openID记录是否已存在
	 * @param map
	 * @return
	 */
	HospitalUserOpenId findUserOpenIdByUIdOpenId(Map<String, Object> map);

}
