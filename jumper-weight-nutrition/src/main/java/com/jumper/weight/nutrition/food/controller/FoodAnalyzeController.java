package com.jumper.weight.nutrition.food.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.food.service.FoodService;
import com.jumper.weight.nutrition.food.vo.VOFoodCatagoryAnalyze;
import com.jumper.weight.nutrition.food.vo.VOMealsTypeIntakeAnalyze;
import com.jumper.weight.nutrition.food.vo.VONutritionAnalyze;
import com.jumper.weight.nutrition.food.vo.VOTotalAnalyze;
import com.jumper.weight.nutrition.food.vo.VoFoodWeight;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/foodAnalyze")
@Api(value = "/foodAnalyze", description = "食物分析")
public class FoodAnalyzeController extends BaseController {
	
	@Autowired
	private FoodService foodService;
	
	/**
	 * 食物营养素分析
	 * @createTime 2017-5-18,下午1:43:22
	 * @createAuthor fangxilin
	 * @param userId
	 * @param voList
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/nutritionAnalyze")
	@ApiOperation(value = "食物营养素分析", httpMethod = "POST", response = ReturnMsg.class, notes = "食物营养素分析", position = 1)
	public ReturnMsg nutritionAnalyze(@ApiParam(value = "用户id") @RequestParam(required = false) Integer userId,
			@ApiParam(value = "食物分析列表") @RequestParam String voList, 
			@ApiParam(value = "医院id") @RequestParam(required = false) Integer hospitalId) {
		try {
			List<VoFoodWeight> foodWeightList = JSON.parseArray(voList, VoFoodWeight.class);
			VONutritionAnalyze data = foodService.getVONutritionAnalyze(foodWeightList, 1, userId, hospitalId);//默认调查的是1天
			return new ReturnMsg(ReturnMsg.SUCCESS, "食物营养素分析成功", data);
		} catch (Exception e) {
			logger.error("食物营养素分析异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "食物营养素分析异常");
		}
	}
	
	/**
	 * 各类食物摄入量分析
	 * @param outpatientId 门诊id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/foodCatagoryAnalyze")
	@ApiOperation(value = "各类食物摄入量分析", httpMethod = "POST", response = ReturnMsg.class, notes = "各类食物摄入量分析", position = 1)
	public ReturnMsg foodCatagoryAnalyze(@ApiParam(value = "门诊id") @RequestParam(required = false) Integer outpatientId) {
		try {
			Map<String, Object> map = foodService.getFoodCatagoryAnalyzeList(outpatientId);
			if(map != null){
				List<VOFoodCatagoryAnalyze> analyzeList = (List<VOFoodCatagoryAnalyze>) map.get("analyzeList");
				if(analyzeList != null && analyzeList.size() > 0){
					return new ReturnMsg(ReturnMsg.SUCCESS, "各类食物摄入量分析成功", map);
				}
			}
			return new ReturnMsg(ReturnMsg.DATA_NULL, "膳食调查数据为空");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "各类食物摄入量分析异常");
		}
	}
	
	/**
	 * 餐次功能比分析
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/mealsTypeIntakeAnalyze")
	@ApiOperation(value = "餐次功能比分析", httpMethod = "POST", response = ReturnMsg.class, notes = "餐次功能比分析", position = 1)
	public ReturnMsg mealsTypeIntakeAnalyze(@ApiParam(value = "门诊id") @RequestParam(required = false) Integer outpatientId) {
		try {
			List<VOMealsTypeIntakeAnalyze> analyzeList = foodService.getMealsTypeIntakeAnalyzeList(outpatientId);
			if(analyzeList != null && analyzeList.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "餐次功能比分析成功", analyzeList);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "膳食调查数据为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "餐次功能比分析异常");
		}
	}
	
	/**
	 * 三大能量物质来源分析
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/threeSourceAnalyze")
	@ApiOperation(value = "三大能量物质来源分析", httpMethod = "POST", response = ReturnMsg.class, notes = "三大能量物质来源分析", position = 1)
	public ReturnMsg threeSourceAnalyze(@ApiParam(value = "门诊id") @RequestParam(required = false) Integer outpatientId, 
			@ApiParam(value = "医院id") @RequestParam(required = false) Integer hospitalId) {
		try {
			Map<String, Object> map = foodService.getVOMealsInfoList(outpatientId);
			if(map != null){
				VONutritionAnalyze voNutritionAnalyze = foodService.getVONutritionAnalyze((List<VoFoodWeight>) map.get("foodWeightList"), (int)map.get("days"), (int)map.get("userId"), hospitalId);//默认调查的是1天
				VOTotalAnalyze voTotalAnalyze = foodService.getVOTotalAnalyze(voNutritionAnalyze);
				if(voTotalAnalyze != null){
					return new ReturnMsg(ReturnMsg.SUCCESS, "总体来源分析成功", voTotalAnalyze);
				}
			}
			return new ReturnMsg(ReturnMsg.DATA_NULL, "膳食调查数据为空");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "总体来源分析异常");
		}
	}
	
	
}
