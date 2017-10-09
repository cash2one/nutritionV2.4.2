package com.jumper.weight.nutrition.sport.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.sport.service.SportSurveyService;
import com.jumper.weight.nutrition.sport.vo.VoDailySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoSportAnalyze;
import com.jumper.weight.nutrition.sport.vo.VoUserSportRecord;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/sportSurvey")
@Api(value = "/sportSurvey", description = "运动调查")
public class SportSurveyController extends BaseController {
	
	@Autowired
	private SportSurveyService sportSurveyService;
	
	/**
	 * 通过门诊id查询运动调查记录
	 * @createTime 2017-5-10,下午4:13:10
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @param type
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listSportSurveyByOutpId")
	@ApiOperation(value = "通过门诊id查询运动调查记录", httpMethod = "POST", response = ReturnMsg.class, notes = "通过门诊id查询运动调查记录", position = 1)
	public ReturnMsg listSportSurveyByOutpId(@ApiParam(value = "门诊id") @RequestParam int outpatientId,
			@ApiParam(value = "查询类型 0：运动调查页面，1：报告页面") @RequestParam int type) {
		try {
			List<VoDailySportRecord> data = sportSurveyService.listSurveyByOutpId(outpatientId, type);
			data = (data != null) ? data : new ArrayList<VoDailySportRecord>();
			return new ReturnMsg(ReturnMsg.SUCCESS, "通过门诊id查询运动调查记录成功", data);
		} catch (Exception e) {
			logger.error("通过门诊id查询运动调查记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "通过门诊id查询运动调查记录异常");
		}
	}
	
	/**
	 * 查询门诊某天的运动调查记录
	 * @createTime 2017-5-2,下午4:00:42
	 * @createAuthor fangxilin
	 * @param userId
	 * @param date
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findSurveyByDateOutp")
	@ApiOperation(value = "查询门诊某天的运动调查记录", httpMethod = "POST", response = ReturnMsg.class, notes = "查询门诊某天的运动调查记录", position = 2)
	public ReturnMsg findSurveyByDateOutp(@ApiParam(value = "门诊id") @RequestParam int outpatientId,
			@ApiParam(value = "运动日期（yyyy-MM-dd）") @RequestParam String date) {
		try {
			VoDailySportRecord data = sportSurveyService.findByDateOutp(outpatientId, date);
			data = (data != null) ? data : new VoDailySportRecord();
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询门诊某天的运动调查记录成功", data);
		} catch (Exception e) {
			logger.error("查询门诊某天的运动调查记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "查询门诊某天的运动调查记录异常");
		}
	}
	
	/**
	 * 添加门诊某天的运动调查
	 * @createTime 2017-5-10,下午4:22:40
	 * @createAuthor fangxilin
	 * @param recordList
	 * @param outpatientId
	 * @param date
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveSurveyByDate")
	@ApiOperation(value = "添加门诊某天的运动调查", httpMethod = "POST", response = ReturnMsg.class, notes = "添加门诊某天的运动调查", position = 3)
	public ReturnMsg saveSurveyByDate(@ApiParam(value = "某天的运动记录集合") @RequestParam String recordList, 
			@ApiParam(value = "门诊id") @RequestParam int outpatientId,
			@ApiParam(value = "运动日期（yyyy-MM-dd）") @RequestParam String date) {
		try {
			List<VoUserSportRecord> voList = JSON.parseArray(recordList, VoUserSportRecord.class);
			if (ArrayUtils.isEmpty(voList)) {
				return new ReturnMsg(ReturnMsg.FAIL, "运动记录不能为空");
			}
			boolean ret = sportSurveyService.saveSurveyByDate(outpatientId, date, voList);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "添加门诊某天的运动调查失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "添加门诊某天的运动调查成功");
		} catch (Exception e) {
			logger.error("添加门诊某天的运动调查异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "添加门诊某天的运动调查异常");
		}
	}
	
	/**
	 * 删除某条运动调查记录
	 * @createTime 2017-5-10,下午4:26:13
	 * @createAuthor fangxilin
	 * @param surveyId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/delteSportSurveyById")
	@ApiOperation(value = "删除某条运动调查记录", httpMethod = "POST", response = ReturnMsg.class, notes = "删除某条运动调查记录", position = 4)
	public ReturnMsg delteSportSurveyById(@ApiParam(value = "运动调查id") @RequestParam int surveyId) {
		try {
			boolean ret = sportSurveyService.delete(surveyId);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "删除某条运动调查记录失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除某条运动调查记录成功");
		} catch (Exception e) {
			logger.error("删除某条运动调查记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "删除某条运动调查记录异常");
		}
	}
	
	/**
	 * 获取用户运动调查分析
	 * @createTime 2017-6-26,下午4:40:18
	 * @createAuthor fangxilin
	 * @param voList
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getSportSurAnalyze")
	@ApiOperation(value = "获取用户运动调查分析", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户运动调查分析", position = 5)
	public ReturnMsg getSportSurAnalyze(@ApiParam(value = "运动调查列表") @RequestParam String voList) {
		try {
			List<VoDailySportRecord> list = JSON.parseArray(voList, VoDailySportRecord.class);
			VoSportAnalyze data = sportSurveyService.getSportSurAnalyze(list);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户运动调查分析成功", data);
		} catch (Exception e) {
			logger.error("获取用户运动调查分析异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户运动调查分析异常");
		}
	}
}
