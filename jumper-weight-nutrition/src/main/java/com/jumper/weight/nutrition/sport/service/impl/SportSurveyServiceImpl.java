package com.jumper.weight.nutrition.sport.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.record.entity.UserWeightRecord;
import com.jumper.weight.nutrition.record.mapper.UserWeightRecordMapper;
import com.jumper.weight.nutrition.sport.entity.SportInfo;
import com.jumper.weight.nutrition.sport.entity.SportSurvey;
import com.jumper.weight.nutrition.sport.mapper.SportInfoMapper;
import com.jumper.weight.nutrition.sport.mapper.SportSurveyMapper;
import com.jumper.weight.nutrition.sport.service.SportSurveyService;
import com.jumper.weight.nutrition.sport.service.UserSportRecordService;
import com.jumper.weight.nutrition.sport.vo.VoDailySportAnalyze;
import com.jumper.weight.nutrition.sport.vo.VoDailySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoSportAnalyze;
import com.jumper.weight.nutrition.sport.vo.VoSportChart;
import com.jumper.weight.nutrition.sport.vo.VoUserSportRecord;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;

@Service
public class SportSurveyServiceImpl extends BaseServiceImpl<SportSurvey> implements SportSurveyService {
	
	@Autowired
	private SportSurveyMapper sportSurveyMapper;
	@Autowired
	private UserSportRecordService userSportRecordService;
	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;
	@Autowired
	private SportInfoMapper sportInfoMapper;
	@Autowired
	private UserWeightRecordMapper userWeightRecordMapper;
	
	@Override
	protected BaseMapper<SportSurvey> getDao() {
		return sportSurveyMapper;
	}

	@Override
	public List<VoDailySportRecord> listSurveyByOutpId(int outpatientId, int type) {
		List<SportSurvey> surveyList = sportSurveyMapper.listSurveyByOutpId(outpatientId);
		List<VoDailySportRecord> returnList = new ArrayList<VoDailySportRecord>();
		if (ArrayUtils.isEmpty(surveyList) && type == 0) {
			//获取昨日的用户运动记录
			HospitalOutpatient outp = hospitalOutpatientMapper.findById(outpatientId);
			String yesterday = TimeUtils.dateFormatToString(TimeUtils.getDateByDaysLate(-1, new Date()), Const.YYYYMMDD);
			VoDailySportRecord vo = userSportRecordService.listSportRecordByDate(outp.getUserId(), yesterday);//不带运动调查id
			vo = (vo != null) ? vo : new VoDailySportRecord();
			vo.setSportDate(yesterday);
			returnList.add(vo);
			return returnList;
		} else if (ArrayUtils.isEmpty(surveyList) && type == 1) {
			return returnList;
		}
		//运动调查记录列表封装
		for (SportSurvey survey : surveyList) {
			VoDailySportRecord vo = getVoDailySportRecord(survey);//带运动调查id
			returnList.add(vo);
		}
		return returnList;
	}

	@Override
	public VoDailySportRecord findByDateOutp(int outpatientId, String surveyDate) {
		SportSurvey survey = sportSurveyMapper.findByDateOutp(outpatientId, surveyDate);
		return getVoDailySportRecord(survey);
	}
	
	/**
	 * 封装VoDailySportRecord
	 * @createTime 2017-5-10,下午2:39:49
	 * @createAuthor fangxilin
	 * @param survey
	 * @return
	 */
	private VoDailySportRecord getVoDailySportRecord(SportSurvey survey) {
		if (survey == null) {
			return null;
		}
		List<VoUserSportRecord> recordList = JSON.parseArray(survey.getSurveyList(), VoUserSportRecord.class);
		VoDailySportRecord vo = new VoDailySportRecord();
		vo.setSurveyId(survey.getId());
		vo.setSportDate(TimeUtils.dateFormatToString(survey.getSurveyDate(), Const.YYYYMMDD));
		if (ArrayUtils.isEmpty(recordList)) {
			return vo;
		}
		
		List<Integer> sportIds = new ArrayList<Integer>();
		for (VoUserSportRecord record : recordList) {
			sportIds.add(record.getSportId());
		}
		//查询运动集合
		List<SportInfo> sportList = sportInfoMapper.listByIds(sportIds);
		//赋值
		Double totalTime = 0D, totalCalorie = 0D;
		SportInfo sport = null;
		for (VoUserSportRecord record : recordList) {
			totalTime += record.getTimeLength();
			totalCalorie += record.getCalories();
			for (SportInfo sp : sportList) {
				if (record.getSportId().intValue() == sp.getId()) {
					sport = sp;
					break;
				}
			}
			record.setCalorie(sport.getCalorie());//计算卡洛里时要用到（废弃）
			record.setMet(sport.getMet());//计算卡洛里时要用到
		}
		vo.setRecordList(recordList);
		vo.setTotalTime(totalTime);
		vo.setTotalCalorie(totalCalorie);
		return vo;
	}

	@Override
	public boolean saveSurveyByDate(int outpatientId, String surveyDate, List<VoUserSportRecord> voList) throws Exception {
		if (ArrayUtils.isEmpty(voList)) {
			return false;
		}
		//保存时，VoUserSportRecord前端必须传sportId, timeLength
		List<Integer> sportIds = new ArrayList<Integer>();
		for (VoUserSportRecord vo : voList) {
			sportIds.add(vo.getSportId());
		}
		//查询运动集合，为VoUserSportRecord赋值
		List<SportInfo> sportList = sportInfoMapper.listByIds(sportIds);
		SportInfo sport = null;
		HospitalOutpatient outp = hospitalOutpatientMapper.findById(outpatientId);//门诊记录
		//用户最近的一条体重记录
		UserWeightRecord weight = userWeightRecordMapper.findUserLastWeight(outp.getUserId());
		double currWeight = weight.getAverageValue();
		for (VoUserSportRecord vo : voList) {
			for (SportInfo sp : sportList) {
				if (vo.getSportId().intValue() == sp.getId()) {
					sport = sp;
					break;
				}
			}
			vo.setSportName(sport.getName());
			//double calories = FunctionUtils.setDecimal(vo.getTimeLength() / 30 * sport.getCalorie(), 1);
			double calories = FunctionUtils.getSpoConsumeCal(currWeight, sport.getMet(), vo.getTimeLength());
			vo.setCalories(calories);
		}
		String surveyList = JSON.toJSONString(voList);
		
		SportSurvey survey = sportSurveyMapper.findByDateOutp(outpatientId, surveyDate);
		SportSurvey bean = new SportSurvey();
		bean.setSurveyList(surveyList);
		int num = 0;
		if (survey == null) {//更新
			bean.setHospitalId(outp.getHospitalId());
			bean.setUserId(outp.getUserId());
			bean.setOutpatientId(outpatientId);
			bean.setSurveyDate(TimeUtils.stringFormatToDate(surveyDate, Const.YYYYMMDD));
			bean.setAddTime(new Date());
			num = sportSurveyMapper.insert(bean);
		} else {
			bean.setId(survey.getId());
			num = sportSurveyMapper.update(bean);
		}
		return (num > 0);
	}

	@Override
	public VoSportAnalyze getSportSurAnalyze(List<VoDailySportRecord> list) {
		VoSportAnalyze analyze = new VoSportAnalyze();
		List<VoDailySportAnalyze> dailyList = new ArrayList<VoDailySportAnalyze>();
		Set<String> sportList = new HashSet<String>();
		for (VoDailySportRecord daily : list) {
			VoDailySportAnalyze voDsa = new VoDailySportAnalyze();
			voDsa.setSportDate(daily.getSportDate());
			List<VoSportChart> recordList = new ArrayList<VoSportChart>();
			for (VoUserSportRecord record : daily.getRecordList()) {
				sportList.add(record.getSportName());
				VoSportChart voSc = new VoSportChart();
				voSc.setSportName(record.getSportName());
				voSc.setTimeLength(record.getTimeLength());
				voSc.setCalories(record.getCalories());
				//计算百分比时，四舍五入可能出现相加不等于100%的情况
				voSc.setTimeLengthPer(FunctionUtils.setDecimal(record.getTimeLength() / daily.getTotalTime() * 100, 1));
				voSc.setCaloriesPer(FunctionUtils.setDecimal(record.getCalorie() / daily.getTotalCalorie() * 100, 1));
				recordList.add(voSc);
			}
			voDsa.setRecordList(recordList);
			dailyList.add(voDsa);
		}
		analyze.setSportList(sportList);
		analyze.setDailyList(dailyList);
		return analyze;
	}
}
