package com.jumper.weight.nutrition.hospital.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalTemplate;

public interface WeightHospitalTemplateMapper extends BaseMapper<WeightHospitalTemplate>{

	/**
	 * 查询医院模板设置列表
	 * @param param
	 * @return
	 */
	List<WeightHospitalTemplate> findHospitalTemplate(Map<String, Object> param);
	
	/**
	 * 查询医院通知模板
	 * @createTime 2017年9月22日,下午4:51:16
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	List<WeightHospitalTemplate> listTemplateByHosp(int hospitalId);
	
}
