package com.jumper.weight.nutrition.hospital.mapper;

import java.util.List;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;


public interface WeightHospitalSettingMapper extends BaseMapper<WeightHospitalSetting> {
	/**
	 * 通过医院id查询
	 * @createTime 2017-6-22,上午10:06:23
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	WeightHospitalSetting findSettingByHospId(int hospitalId);
	
	/**
	 * 根据hospIds列表查询医院安全体重范围计算公式列表
	 * @createTime 2017-7-18,下午5:47:12
	 * @createAuthor fangxilin
	 * @param hospIds
	 * @return
	 */
	List<WeightHospitalSetting> listHospSafeFormula(List<Integer> hospIds);
}