package com.jumper.weight.nutrition.sport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.sport.entity.SportSurvey;


public interface SportSurveyMapper extends BaseMapper<SportSurvey> {
	/**
	 * 通过门诊id查询出运动调查记录
	 * @createTime 2017-5-10,下午2:03:03
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @return
	 */
	List<SportSurvey> listSurveyByOutpId(int outpatientId);
	
	/**
	 * 通过日期和门诊id查询出唯一一条 
	 * @createTime 2017-5-10,下午2:04:44
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @param surveyDate
	 * @return
	 */
	SportSurvey findByDateOutp(@Param("outpatientId") int outpatientId, @Param("surveyDate") String surveyDate);
	
	/**
	 * 通过门诊id删除运动调查
	 * @createTime 2017-5-16,下午4:53:36
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @return
	 */
	int deleteByOutpId(int outpatientId);
}