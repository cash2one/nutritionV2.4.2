package com.jumper.weight.nutrition.record.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.record.entity.UserSugarRecord;
import com.jumper.weight.nutrition.record.mapper.UserSugarRecordMapper;
import com.jumper.weight.nutrition.record.service.UserSugarRecordService;

@Service
public class UserSugarRecordServiceImpl extends BaseServiceImpl<UserSugarRecord> implements UserSugarRecordService {

	@Autowired
	private UserSugarRecordMapper userSugarRecordMapper;
	
	@Override
	protected BaseMapper<UserSugarRecord> getDao() {
		return userSugarRecordMapper;
	}
	
	@Override
	public UserSugarRecord findUserLastSugar(int userId) {
		return userSugarRecordMapper.findUserLastSugar(userId);
	}
}
