package com.jumper.weight.nutrition.diet.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ComparatorList;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.diet.entity.DietSurvey;
import com.jumper.weight.nutrition.diet.mapper.DietSurveyMapper;
import com.jumper.weight.nutrition.diet.mapper.WeightMealsInfoMapper;
import com.jumper.weight.nutrition.diet.service.DietSurveyService;
import com.jumper.weight.nutrition.diet.service.WeightMealsInfoService;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfoPage;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;

/**
 * 膳食调查serviceImpl
 * @author gyx
 * @time 2017年5月11日
 */
@Service
public class DietSurveyServiceImpl extends BaseServiceImpl<DietSurvey> implements DietSurveyService{
	@Autowired
	private DietSurveyMapper dietSurveyMapper;
	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;
	@Autowired
	private WeightMealsInfoMapper weightMealsInfoMapper;
	@Autowired
	private WeightMealsInfoService weightMealsInfoService;

	@Override
	protected BaseMapper<DietSurvey> getDao() {
		return dietSurveyMapper;
	}

	/**
	 * 根据门诊id查询用户膳食调查记录
	 */
	@Override
	public List<VOWeightMealsInfoPage> findUserDietSurveyList(int outpatientId,
			int type) {
		List<VOWeightMealsInfoPage> voWeightMealsInfoPageList = new ArrayList<VOWeightMealsInfoPage>();
		List<DietSurvey> dietSurveyList = dietSurveyMapper.findUserDietSurveyList(outpatientId);
		if(dietSurveyList != null && dietSurveyList.size() > 0){
			voWeightMealsInfoPageList = getVOWeightMealsInfoList(dietSurveyList);
			return voWeightMealsInfoPageList;
		}else{
			//为空
			if(type==0){
				//膳食调查页面，查询昨日记录，【id赋值0】
				HospitalOutpatient outp = hospitalOutpatientMapper.findById(outpatientId);
				String yesterday = TimeUtils.dateFormatToString(TimeUtils.getDateByDaysLate(-1, new Date()), Const.YYYYMMDD);
				List<VOWeightMealsInfo> voWeightMealsInfoList = weightMealsInfoService.findUserMealsInfoList(outp.getUserId(), "", "", 0);
				if(voWeightMealsInfoList != null && voWeightMealsInfoList.size() > 0){
					VOWeightMealsInfoPage page = new VOWeightMealsInfoPage();
					page.setDietSurveyId(0);
					page.setEatDate(yesterday);
					page.setInfoList(voWeightMealsInfoList);
					voWeightMealsInfoPageList.add(page);
					return voWeightMealsInfoPageList;
				}else{
					return voWeightMealsInfoPageList;
				}
				
			}else if(type==1){
				//报告页，直接返回空
				return voWeightMealsInfoPageList;
			}
		}
		return null;
	}

	/**
	 * 组装VOWeightMealsInfo
	 * @param dietSurveyList 膳食调查记录
	 * @return
	 */
	private List<VOWeightMealsInfoPage> getVOWeightMealsInfoList(
			List<DietSurvey> dietSurveyList) {
		List<VOWeightMealsInfoPage> pageList = new ArrayList<VOWeightMealsInfoPage>();
		for (DietSurvey dietSurvey : dietSurveyList) {
			VOWeightMealsInfoPage page = new VOWeightMealsInfoPage();
			page.setDietSurveyId(dietSurvey.getId());
			page.setEatDate(TimeUtils.dateFormatToString(dietSurvey.getEatDate(), Const.YYYYMMDD));
			List<VOWeightMealsInfo> voMealsInfoList = new ArrayList<VOWeightMealsInfo>();
			if(StringUtils.isNotBlank(dietSurvey.getDietMsg())){
				voMealsInfoList = JSON.parseArray(dietSurvey.getDietMsg(), VOWeightMealsInfo.class);
			}
			Collections.sort(voMealsInfoList, new ComparatorList());
			page.setInfoList(voMealsInfoList);
			pageList.add(page);
		}
		return pageList;
	}

	/**
	 * 保存用户膳食调查记录
	 */
	@Override
	public DietSurvey saveUserDietSurvey(DietSurvey dietSurvey) {
		boolean b = false;
		try {
			if(dietSurvey.getId() == 0 || dietSurvey.getId() == null){
				//添加
				dietSurvey.setAddTime(new Date());
				b = dietSurveyMapper.insert(dietSurvey)>0;
			}else{
				//修改
				b = dietSurveyMapper.update(dietSurvey)>0;
				dietSurvey = dietSurveyMapper.findById(dietSurvey.getId());
			}
			if(b){
				return dietSurvey;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
