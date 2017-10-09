package com.jumper.weight.nutrition.report.mapper;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.report.entity.WeightReport;
/**
 * 体重营养报告mapper
 * @author gyx
 * @time 2017年5月4日
 */
public interface WeightReportMapper extends BaseMapper<WeightReport>{

	/**
	 * 根据门诊id查询用户报告信息
	 * @param outpatientId 门诊id
	 * @return
	 */
	WeightReport findWeightReportByOutpatientId(Integer outpatientId);
	
	/**
	 * 根据门诊id删除报告信息
	 * @param outpatientId 门诊id
	 */
	void deleteWeightReportByOutpatientId(Integer outpatientId);
	
}
