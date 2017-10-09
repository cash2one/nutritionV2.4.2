package com.jumper.weight.nutrition.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.report.entity.WeightReport;
import com.jumper.weight.nutrition.report.service.WeightReportService;
import com.jumper.weight.nutrition.report.vo.VOWeightReport;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("report")
@Api(value = "/report", description = "报告模块")
public class WeightReportController {
	@Autowired
	private WeightReportService weightReportService;
	
	
	/**
	 * 通过报告id查询报告记录
	 */
	@RequestMapping("findUserWeightReport")
	@ResponseBody
	@ApiOperation(value = "查询用户报告记录", httpMethod = "POST", response = ReturnMsg.class, notes = "查询用户报告记录")
	public ReturnMsg findUserWeightReport(@ApiParam(value = "报告id")@RequestParam("reportId") int reportId){
		try {
			VOWeightReport voWeightReport = weightReportService.findUserWeightReport(reportId);
			if(voWeightReport != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户报告信息成功！", voWeightReport);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户报告信息为空！", new VOWeightReport());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户报告信息失败！");
		}
	}
	
	/**
	 * 保存用户体重报告WeightReport
	 */
	@RequestMapping("saveUserWeightReport")
	@ResponseBody
	@ApiOperation(value = "保存用户报告记录", httpMethod = "POST", response = ReturnMsg.class, notes = "保存用户报告记录")
	public ReturnMsg saveUserWeightReport(@ApiParam(value = "报告信息")@RequestBody WeightReport weightReport){
		try {
			Integer reportId = weightReportService.saveUserWeightReport(weightReport);
			if(reportId != null && reportId != 0){
				VOWeightReport voWeightReport = weightReportService.findUserWeightReport(reportId);
				if(voWeightReport != null){
					return new ReturnMsg(ReturnMsg.SUCCESS, "保存用户报告信息成功！", voWeightReport);
				}else{
					return new ReturnMsg(ReturnMsg.DATA_NULL, "获取用户报告信息为空！", new VOWeightReport());
				}
			}else{
				return new ReturnMsg(ReturnMsg.FAIL, "保存用户报告信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "保存用户报告信息失败！");
		}
	}
	
	/**
	 * 通过门诊id查询报告记录
	 */
	@RequestMapping("findUserWeightReportByOutpId")
	@ResponseBody
	@ApiOperation(value = "根据门诊id查询用户报告记录", httpMethod = "POST", response = ReturnMsg.class, notes = "根据门诊id查询用户报告记录")
	public ReturnMsg findUserWeightReportByOutpId(@ApiParam(value = "门诊id")@RequestParam("outpatientId") int outpatientId){
		try {
			WeightReport weightReport = weightReportService.findUserWeightReportByOutpId(outpatientId);
			if(weightReport != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户报告信息成功！", weightReport);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户报告信息为空！", new WeightReport());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户报告信息失败！");
		}
	}
	
}
