package com.jumper.weight.nutrition.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.user.service.HospitalOutpatientService;
import com.jumper.weight.nutrition.user.service.HospitalUserManageService;
import com.jumper.weight.nutrition.user.vo.VoOutpChartSta;
import com.jumper.weight.nutrition.user.vo.VoOutpStatistics;
import com.jumper.weight.nutrition.user.vo.VoWeightChartSta;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/statistics")
@Api(value = "/statistics", description = "统计")
public class StatisticsController extends BaseController {
	
	@Autowired
	private HospitalOutpatientService hospitalOutpatientService;
	@Autowired
	private HospitalUserManageService hospitalUserManageService;
	
	/**
	 * 获取门诊统计数据
	 * @createTime 2017-5-4,下午7:36:12
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getOutpStatistics")
	@ApiOperation(value = "获取门诊统计数据", httpMethod = "POST", response = ReturnMsg.class, notes = "获取门诊统计数据", position = 1)
	public ReturnMsg getOutpStatistics(@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			VoOutpStatistics data = hospitalOutpatientService.getOutpStatistics(hospitalId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取门诊统计数据成功", data);
		} catch (Exception e) {
			logger.error("获取门诊统计数据异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取门诊统计数据异常");
		}
	}
	
	/**
	 * 获取门诊图表统计数据
	 * @createTime 2017-6-23,下午4:04:39
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getOutpChartSta")
	@ApiOperation(value = "获取门诊图表统计数据", httpMethod = "POST", response = ReturnMsg.class, notes = "获取门诊图表统计数据", position = 2)
	public ReturnMsg getOutpChartSta(@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "开始日期") @RequestParam String startDate,
			@ApiParam(value = "结束日期") @RequestParam String endDate) {
		try {
			List<VoOutpChartSta> data = hospitalOutpatientService.getOutpChartSta(hospitalId, startDate, endDate);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取门诊图表统计数据成功", data);
		} catch (Exception e) {
			logger.error("获取门诊图表统计数据异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取门诊图表统计数据异常");
		}
	}
	
	/**
	 * 获取体重异常统计数据
	 * @createTime 2017-6-26,下午1:48:32
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getWeightStatistics")
	@ApiOperation(value = "获取体重异常统计数据", httpMethod = "POST", response = ReturnMsg.class, notes = "获取体重异常统计数据", position = 3)
	public ReturnMsg getWeightStatistics(@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			VoWeightChartSta data = hospitalUserManageService.getWeightStatistics(hospitalId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取体重异常统计数据成功", data);
		} catch (Exception e) {
			logger.error("获取体重异常统计数据异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取体重异常统计数据异常");
		}
	}
	
	/**
	 * 获取体重异常图表统计数据
	 * @createTime 2017-6-26,下午1:59:15
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getWeightChartSta")
	@ApiOperation(value = "获取体重异常图表统计数据", httpMethod = "POST", response = ReturnMsg.class, notes = "获取体重异常图表统计数据", position = 4)
	public ReturnMsg getWeightChartSta(@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "开始日期") @RequestParam String startDate,
			@ApiParam(value = "结束日期") @RequestParam String endDate) {
		try {
			List<VoWeightChartSta> data = hospitalUserManageService.getWeightChartSta(hospitalId, startDate, endDate);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取体重异常图表统计数据成功", data);
		} catch (Exception e) {
			logger.error("获取体重异常图表统计数据异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取体重异常图表统计数据异常");
		}
	}
}
