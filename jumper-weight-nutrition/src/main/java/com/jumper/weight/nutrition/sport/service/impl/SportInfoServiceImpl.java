package com.jumper.weight.nutrition.sport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.sport.entity.SportInfo;
import com.jumper.weight.nutrition.sport.mapper.SportInfoMapper;
import com.jumper.weight.nutrition.sport.service.SportInfoService;

@Service
public class SportInfoServiceImpl extends BaseServiceImpl<SportInfo> implements	SportInfoService {

	@Autowired
	private SportInfoMapper sportInfoMapper;
	
	@Override
	protected BaseMapper<SportInfo> getDao() {
		return sportInfoMapper;
	}

	@Override
	public List<SportInfo> listSportsByName(String query) {
		return sportInfoMapper.listSportsByName(query);
	}

}
