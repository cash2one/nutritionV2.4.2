package com.jumper.weight.nutrition.sport.service;

import java.util.List;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.sport.entity.SportSurvey;
import com.jumper.weight.nutrition.sport.vo.VoDailySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoSportAnalyze;
import com.jumper.weight.nutrition.sport.vo.VoUserSportRecord;

public interface SportSurveyService extends BaseService<SportSurvey> {
	
	/**
	 * 通过门诊id查询出运动调查记录
	 * @createTime 2017-5-10,下午2:12:19
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @param type 查询类型 0：运动调查页面，1：报告页面
	 * @return
	 */
	List<VoDailySportRecord> listSurveyByOutpId(int outpatientId, int type);
	
	/**
	 * 通过日期和门诊id查询出唯一一条 
	 * @createTime 2017-5-10,下午2:17:23
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @param surveyDate
	 * @return
	 */
	VoDailySportRecord findByDateOutp(int outpatientId, String surveyDate);
	
	/**
	 * 保存运动调查记录
	 * @createTime 2017-5-10,下午2:22:55
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @param voList
	 * @return
	 * @throws Exception 
	 */
	boolean saveSurveyByDate(int outpatientId, String surveyDate, List<VoUserSportRecord> voList) throws Exception;
	
	/**
	 * 获取用户运动调查分析
	 * @createTime 2017-6-26,下午3:43:33
	 * @createAuthor fangxilin
	 * @param list 运动调查列表
	 * @return
	 */
	VoSportAnalyze getSportSurAnalyze(List<VoDailySportRecord>  list);
}
