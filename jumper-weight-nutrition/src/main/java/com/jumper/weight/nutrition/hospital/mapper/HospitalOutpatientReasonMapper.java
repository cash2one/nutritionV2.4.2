package com.jumper.weight.nutrition.hospital.mapper;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.hospital.entity.HospitalOutpatientReason;
/**
 * 医院就诊原因mapper
 * @author gyx
 * @time 2017年7月18日
 */
public interface HospitalOutpatientReasonMapper extends
		BaseMapper<HospitalOutpatientReason> {

	/**
	 * 查询医院就诊原因
	 * @param param
	 * @return
	 */
	List<HospitalOutpatientReason> findHospitalOutpatientReason(
			Map<String, Object> param);

}
