package com.jumper.weight.nutrition.report.service;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.report.entity.WeightReport;
import com.jumper.weight.nutrition.report.vo.VOWeightReport;
/**
 * 报告service
 * @author gyx
 * @time 2017年5月3日
 */
public interface WeightReportService extends BaseService<WeightReport>{

	/**
	 * 通过报告id查询报告信息
	 * @param reportId
	 * @return
	 */
	VOWeightReport findUserWeightReport(int reportId);

	/**
	 * 保存用户报告信息
	 * @param weightReport
	 * @return
	 */
	Integer saveUserWeightReport(WeightReport weightReport);
	
	/**
	 * 根据门诊id查询用户报告信息
	 * @param outpatientId 门诊id
	 * @return
	 */
	WeightReport findUserWeightReportByOutpId(int outpatientId);
	
}
