package com.jumper.weight.nutrition.hospital.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalTemplate;
import com.jumper.weight.nutrition.hospital.mapper.WeightHospitalTemplateMapper;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalTemplateService;

@Service
public class WeightHospitalTemplateServiceImpl extends BaseServiceImpl<WeightHospitalTemplate> implements WeightHospitalTemplateService {

	@Autowired
	private WeightHospitalTemplateMapper weightHospitalTemplateMapper;
	
	@Override
	protected BaseMapper<WeightHospitalTemplate> getDao() {
		return weightHospitalTemplateMapper;
	}

	@Override
	public PageInfo<WeightHospitalTemplate> listTemplateByPage(int hospitalId, int page, int limit) {
		PageHelper.startPage(page, limit);
		List<WeightHospitalTemplate> list = weightHospitalTemplateMapper.listTemplateByHosp(hospitalId);
		PageInfo<WeightHospitalTemplate> data = new PageInfo<WeightHospitalTemplate>(list);
		return data;
	}

}
