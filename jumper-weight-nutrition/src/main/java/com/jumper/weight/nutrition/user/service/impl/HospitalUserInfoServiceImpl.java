package com.jumper.weight.nutrition.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.entity.HospitalUserOpenId;
import com.jumper.weight.nutrition.user.mapper.HospitalUserInfoMapper;
import com.jumper.weight.nutrition.user.mapper.HospitalUserOpenIdMapper;
import com.jumper.weight.nutrition.user.service.HospitalUserInfoService;

@Service
public class HospitalUserInfoServiceImpl extends BaseServiceImpl<HospitalUserInfo> implements HospitalUserInfoService {
	
	@Autowired
	private HospitalUserInfoMapper hospitalUserInfoMapper;
	@Autowired
	private HospitalUserOpenIdMapper hospitalUserOpenIdMapper;

	@Override
	protected BaseMapper<HospitalUserInfo> getDao() {
		return hospitalUserInfoMapper;
	}

	@Override
	public boolean addOrUpdateHospUser(HospitalUserInfo hospUser) {
		HospitalUserInfo info = hospitalUserInfoMapper.findHospUserByUIdHospId(hospUser.getUserId(), hospUser.getHospitalId());
		int num = 0;
		try {
			if (info != null) {
				//更新
				hospUser.setId(info.getId());
				num = hospitalUserInfoMapper.update(hospUser);
			} else {
				//添加
				num = hospitalUserInfoMapper.insert(hospUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (num > 0);
	}

	@Override
	public HospitalUserInfo findHospitalUserInfoByOpenId(Integer hospitalId,
			String channel, String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hospitalId", hospitalId);
		map.put("channel", channel);
		map.put("openId", openId);
		HospitalUserInfo hospitalUserInfo = hospitalUserOpenIdMapper.findHospitalUserInfoByOpenId(map);
		if(hospitalUserInfo != null){
			return hospitalUserInfo;
		}
		return null;
	}

	@Override
	public boolean saveUserOpenId(int userId, int channel, String openId) {
		boolean b = false;
		try {
			//先查询该openID是否已存在
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("channel", channel);
			map.put("openId", openId);
			HospitalUserOpenId userOpenId = hospitalUserOpenIdMapper.findUserOpenIdByUIdOpenId(map);
			if(userOpenId != null){
				b = true;
			}else{
				HospitalUserOpenId hospitalUserOpenId = new HospitalUserOpenId();
				hospitalUserOpenId.setUserId(userId);
				hospitalUserOpenId.setAddTime(new Date());
				if(channel == 1){
					//支付宝
					hospitalUserOpenId.setAlipayOpenId(openId);
				}else if(channel == 2){
					//微信
					hospitalUserOpenId.setWeixinOpenId(openId);
				}
				b = hospitalUserOpenIdMapper.insert(hospitalUserOpenId)>0;
			}
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public HospitalUserInfo findHospitalUserInfo(int hospitalId, int userId) {
		HospitalUserInfo hospitalUserInfo = hospitalUserInfoMapper.findHospUserByUIdHospId(userId,hospitalId);
		if(hospitalUserInfo != null){
			return hospitalUserInfo;
		}
		return null;
	}


}
