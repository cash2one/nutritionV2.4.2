package com.jumper.weight.nutrition.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.hospital.entity.HospitalInfo;
import com.jumper.weight.nutrition.hospital.mapper.HospitalInfoMapper;
import com.jumper.weight.nutrition.hospital.service.HospitalInfoService;

@Service
public class HospitalInfoServiceImpl extends BaseServiceImpl<HospitalInfo> implements HospitalInfoService{
	@Autowired
	private HospitalInfoMapper hospitalInfoMapper;
	
	@Override
	protected BaseMapper<HospitalInfo> getDao() {
		return hospitalInfoMapper;
	}
	
}
