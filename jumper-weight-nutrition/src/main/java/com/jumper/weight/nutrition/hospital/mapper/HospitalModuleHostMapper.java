package com.jumper.weight.nutrition.hospital.mapper;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.hospital.entity.HospitalModuleHost;

public interface HospitalModuleHostMapper extends BaseMapper<HospitalModuleHost> {
	/**
	 * 通过类型查询
	 * @createTime 2017-7-27,下午2:33:49
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param moduleNum
	 * @return
	 */
	HospitalModuleHost findHostByHospType(@Param("hospitalId") int hospitalId, @Param("moduleNum") int moduleNum);
}