package com.jumper.weight.nutrition.hospital.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.hospital.entity.HospitalOutpatientReason;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalTemplate;
import com.jumper.weight.nutrition.hospital.mapper.HospitalOutpatientReasonMapper;
import com.jumper.weight.nutrition.hospital.mapper.WeightHospitalSettingMapper;
import com.jumper.weight.nutrition.hospital.mapper.WeightHospitalTemplateMapper;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalSettingService;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;

@Service
public class WeightHospitalSettingServiceImpl extends BaseServiceImpl<WeightHospitalSetting> implements WeightHospitalSettingService {

	@Autowired
	private WeightHospitalSettingMapper weightHospitalSettingMapper;
	@Autowired
	private WeightHospitalTemplateMapper weightHospitalTemplateMapper;
	@Autowired
	private HospitalOutpatientReasonMapper hospitalOutpatientReasonMapper;
	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;
	
	@Override
	protected BaseMapper<WeightHospitalSetting> getDao() {
		return weightHospitalSettingMapper;
	}

	@Override
	public WeightHospitalSetting findSettingByHospId(int hospitalId) {
		WeightHospitalSetting setting = weightHospitalSettingMapper.findSettingByHospId(hospitalId);
		if (setting == null) {//为空的话默认不隐藏
			setting = new WeightHospitalSetting(hospitalId, 0, 0, 0, 0, 0, 0);
		}
		return setting;
	}

	@Override
	public boolean saveSetting(WeightHospitalSetting setting) {
		WeightHospitalSetting info = weightHospitalSettingMapper.findSettingByHospId(setting.getHospitalId());
		try {
			if (info == null) {//添加
				weightHospitalSettingMapper.insert(setting);
			} else {
				setting.setId(info.getId());
				weightHospitalSettingMapper.update(setting);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<WeightHospitalTemplate> findHospitalTemplate(int hospitalId,
			int type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hospitalId", hospitalId);
		param.put("type", type);
		List<WeightHospitalTemplate> templateList = weightHospitalTemplateMapper.findHospitalTemplate(param);
		if(templateList != null && templateList.size() > 0){
			return templateList;
		}
		return null;
	}

	@Override
	public WeightHospitalTemplate saveHospitalTemplate(
		WeightHospitalTemplate weightHospitalTemplate) {
		boolean b = false;
		try {
			if(weightHospitalTemplate.getId() != null && weightHospitalTemplate.getId() != 0){
				//修改
				weightHospitalTemplateMapper.update(weightHospitalTemplate);
				weightHospitalTemplate = weightHospitalTemplateMapper.findById(weightHospitalTemplate.getId());
			}else{
				//添加
				weightHospitalTemplate.setAddTime(new Date());
				weightHospitalTemplateMapper.insert(weightHospitalTemplate);
			}
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		if(b){
			return weightHospitalTemplate;
		}
		return null;
	}

	@Override
	public boolean deleteHospitalTemplate(int templateId) {
		boolean b = false;
		try {
			weightHospitalTemplateMapper.delete(templateId);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public Map<Integer, Integer> listHospSafeFormula(List<Integer> hospIds) {
		List<WeightHospitalSetting> list = weightHospitalSettingMapper.listHospSafeFormula(hospIds);
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		for (WeightHospitalSetting setting : list) {
			ret.put(setting.getHospitalId(), setting.getSafeFormula());
		}
		return ret;
	}

	/*@Override
	public List<HospitalOutpatientReason> addOrFindHospitalOutpatientReason(
			int hospitalId, int type, int pageType) {
		boolean b = false;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hospitalId", hospitalId);
		param.put("type", type);
		List<HospitalOutpatientReason> reasonList = new ArrayList<HospitalOutpatientReason>();
		reasonList = hospitalOutpatientReasonMapper.findHospitalOutpatientReason(param);
		if(reasonList != null && reasonList.size() > 0){
			return reasonList;
		}else{
			if(pageType == 0){
				//列表为空且为h5页面请求
				HospitalOutpatientReason reason = new HospitalOutpatientReason();
				reason.setHospitalId(hospitalId);
				reason.setType(type);
				reason.setOutpatientReason("孕期体重营养管理");
				reason.setTips(type==0?"您已成功完成调查，请耐心等待候诊。":"您已成功确认信息，请耐心等待候诊。");
				reason.setAddTime(new Date());
				reason.setCanDelete(false);//默认的不能被删除
				try {
					hospitalOutpatientReasonMapper.insert(reason);
					b = true;
				} catch (Exception e) {
					b = false;
					e.printStackTrace();
				}
				if(b){
					reasonList.add(reason);
					return reasonList;
				}
			}
		}
		return null;
	}*/
	
	@Override
	public List<HospitalOutpatientReason> addOrFindHospitalOutpatientReason(
			int hospitalId, int type) {
		boolean b = false;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hospitalId", hospitalId);
		param.put("type", type);
		List<HospitalOutpatientReason> reasonList = new ArrayList<HospitalOutpatientReason>();
		reasonList = hospitalOutpatientReasonMapper.findHospitalOutpatientReason(param);
		if(reasonList != null && reasonList.size() > 0){
			return reasonList;
		}else{
			//列表为空
			HospitalOutpatientReason reason = new HospitalOutpatientReason();
			reason.setHospitalId(hospitalId);
			reason.setType(type);
			reason.setOutpatientReason("孕期体重营养管理");
			reason.setTips(type==0?"您已成功完成调查，请耐心等待候诊。":"您已成功确认信息，请耐心等待候诊。");
			reason.setAddTime(new Date());
			reason.setCanDelete(false);//默认的不能被删除
			try {
				hospitalOutpatientReasonMapper.insert(reason);
				b = true;
			} catch (Exception e) {
				b = false;
				e.printStackTrace();
			}
			if(b){
				reasonList.add(reason);
				return reasonList;
			}
		}
		return null;
	}

	@Override
	public HospitalOutpatientReason saveHospitalOutpatientReason(
			HospitalOutpatientReason hospitalOutpatientReason) {
		boolean b = false;
		try {
			if(hospitalOutpatientReason.getId() != null && hospitalOutpatientReason.getId() != 0){
				//修改
				hospitalOutpatientReasonMapper.update(hospitalOutpatientReason);
				hospitalOutpatientReason = hospitalOutpatientReasonMapper.findById(hospitalOutpatientReason.getId());
			}else{
				//添加
				hospitalOutpatientReason.setAddTime(new Date());
				hospitalOutpatientReasonMapper.insert(hospitalOutpatientReason);
			}
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		if(b){
			return hospitalOutpatientReason;
		}
		return null;
	}

	@Override
	public HospitalOutpatientReason findHospitalOutpatientReasonByOutpId(int outpatientId) {
		HospitalOutpatient outpatient = hospitalOutpatientMapper.findById(outpatientId);
		if(outpatient != null && outpatient.getOutpatientReason() > 0){
			HospitalOutpatientReason reason = hospitalOutpatientReasonMapper.findById(outpatient.getOutpatientReason());
			if(reason != null){
				return reason;
			}
		}
		return null;
	}

	@Override
	public HospitalOutpatientReason findHospitalOutpatientReasonById(
			int outpReasonId) {
		HospitalOutpatientReason reason = hospitalOutpatientReasonMapper.findById(outpReasonId);
		if(reason != null){
			return reason;
		}
		return null;
	}
	
}
