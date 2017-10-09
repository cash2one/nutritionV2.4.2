package com.jumper.weight.nutrition.hospital.service;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalTemplate;

public interface WeightHospitalTemplateService extends BaseService<WeightHospitalTemplate> {
	
	/**
	 * 分页查询通知模板
	 * @createTime 2017年9月22日,下午4:57:16
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param page
	 * @param limit
	 * @return
	 */
	PageInfo<WeightHospitalTemplate> listTemplateByPage(int hospitalId, int page, int limit);
}
