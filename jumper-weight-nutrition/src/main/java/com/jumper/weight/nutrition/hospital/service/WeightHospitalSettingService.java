package com.jumper.weight.nutrition.hospital.service;

import java.util.List;
import java.util.Map;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.hospital.entity.HospitalOutpatientReason;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalTemplate;

public interface WeightHospitalSettingService extends BaseService<WeightHospitalSetting> {
	/**
	 * 通过医院id查询
	 * @createTime 2017-6-22,上午10:06:23
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	WeightHospitalSetting findSettingByHospId(int hospitalId);
	
	/**
	 * 保存医院设置
	 * @createTime 2017-6-22,上午10:17:42
	 * @createAuthor fangxilin
	 * @param setting
	 * @return
	 */
	boolean saveSetting(WeightHospitalSetting setting);

	/**
	 * 查询医院模板设置列表
	 * @param hospitalId 医院id
	 * @param type 类型
	 * @return
	 */
	List<WeightHospitalTemplate> findHospitalTemplate(int hospitalId, int type);

	/**
	 * 保存医院模板信息
	 * @param weightHospitalTemplate 医院模板信息
	 * @return
	 */
	WeightHospitalTemplate saveHospitalTemplate(WeightHospitalTemplate weightHospitalTemplate);

	/**
	 * 删除模板信息
	 * @param templateId 模板id
	 * @return
	 */
	boolean deleteHospitalTemplate(int templateId);
	
	/**
	 * 查询医院安全体重范围计算公式列表
	 * @createTime 2017-7-18,下午5:37:28
	 * @createAuthor fangxilin
	 * @param hospIds
	 * @return map <hospitalId, SafeFormula>
	 */
	Map<Integer, Integer> listHospSafeFormula(List<Integer> hospIds);

	/**
	 * 获取医院就诊原因
	 * @param hospitalId 医院id
	 * @param type 门诊类型
	 * @return
	 */
	List<HospitalOutpatientReason> addOrFindHospitalOutpatientReason(int hospitalId,
			int type);

	/**
	 * 保存医院就诊原因
	 * @param hospitalOutpatientReason
	 * @return
	 */
	HospitalOutpatientReason saveHospitalOutpatientReason(
			HospitalOutpatientReason hospitalOutpatientReason);

	/**
	 * 通过id查询就诊原因信息
	 * @param id
	 * @return
	 */
	HospitalOutpatientReason findHospitalOutpatientReasonByOutpId(int outpatientId);

	/**
	 * 通过就诊原因ID获取就诊原因信息
	 * @param outpReasonId 就诊原因ID
	 * @return
	 */
	HospitalOutpatientReason findHospitalOutpatientReasonById(int outpReasonId);
}
