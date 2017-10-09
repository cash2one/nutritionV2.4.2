package com.jumper.weight.nutrition.diet.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.diet.entity.DietSurvey;
import com.jumper.weight.nutrition.diet.service.DietSurveyService;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfoPage;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesDetail;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("dietSurvey")
@Api(value = "/dietSurvey", description = "膳食调查模块")
public class DietSurveyController {
	
	@Autowired
	private DietSurveyService dietSurveyService;
	
	//根据门诊id查询用户膳食调查记录，没有就查询用户昨日饮食记录
	@RequestMapping(value="findUserDietSurveyList", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据门诊id查询用户膳食调查记录", httpMethod = "POST", response = ReturnMsg.class, notes = "根据门诊id查询用户膳食调查记录")
	public ReturnMsg findUserDietSurveyList(@ApiParam(value = "门诊id") @RequestParam int outpatientId, 
											@ApiParam(value = "查询类型：0表示膳食调查页面，1表示报告页") @RequestParam int type){
		List<VOWeightMealsInfoPage> voMealsInfoList = dietSurveyService.findUserDietSurveyList(outpatientId, type);
		if(voMealsInfoList != null && voMealsInfoList.size() > 0){
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户膳食调查记录信息成功！", voMealsInfoList);
		}else{
			return new ReturnMsg(ReturnMsg.DATA_NULL, "用户膳食调查记录信息为空！", new ArrayList<VOWeightMealsInfoPage>());
		}
	}
	
	/**
	 * 保存膳食调查记录
	 * @param DietSurvey
	 * @return
	 */
	@RequestMapping(value = "saveUserDietSurvey", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存膳食调查记录", httpMethod = "POST", response = ReturnMsg.class, notes = "保存膳食调查记录")
	public ReturnMsg saveStandardRecipesDetail(@ApiParam(value = "食谱信息")@RequestBody DietSurvey dietSurvey){
		if(dietSurvey != null && StringUtils.isNotEmpty(dietSurvey.getEatDateStr())){
			dietSurvey.setEatDate(TimeUtils.convertToDate(dietSurvey.getEatDateStr()));
		}
		DietSurvey survey = dietSurveyService.saveUserDietSurvey(dietSurvey);
		if(survey != null){
			return new ReturnMsg(ReturnMsg.SUCCESS, "保存膳食调查记录成功", survey);
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "保存膳食调查记录失败，请稍后重试！");
		}
	}
	
	/**
	 * 删除用户某一天的膳食调查记录
	 */
	@RequestMapping(value = "deleteUserDietSurvey", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "删除用户膳食调查记录", httpMethod = "POST", response = ReturnMsg.class, notes = "删除用户膳食调查记录")
	public ReturnMsg deleteUserDietSurvey(@ApiParam(value = "膳食调查记录id")@RequestParam("id") int id){
		if(id == 0){
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除用户膳食调查记录成功！");
		}else{
			boolean b = dietSurveyService.delete(id);
			if(b){
				return new ReturnMsg(ReturnMsg.SUCCESS, "删除用户膳食调查记录成功！");
			}else{
				return new ReturnMsg(ReturnMsg.FAIL, "删除用户膳食调查记录失败！");
			}
		}
		
	}
	
	/**
	 * 根据id查询用户膳食调查记录
	 * @param recipesId
	 * @return
	 */
	@RequestMapping(value = "findUserDietSurveyById", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据id查询用户膳食调查记录", httpMethod = "POST", response = ReturnMsg.class, notes = "根据id查询用户膳食调查记录")
	public ReturnMsg findUserDietSurveyById(@ApiParam(value = "膳食调查记录id")@RequestParam("id") int id){
		DietSurvey survey = dietSurveyService.findById(id);
		if(survey != null){
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询用户膳食调查记录成功", survey);
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "查询用户膳食调查记录失败，请稍后重试！");
		}
	}
	
	
}
