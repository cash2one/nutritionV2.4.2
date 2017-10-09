package com.jumper.weight.nutrition.recipes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesPlan;
import com.jumper.weight.nutrition.recipes.entity.UserRecipesPlan;
import com.jumper.weight.nutrition.recipes.service.StandardRecipesPlanService;
import com.jumper.weight.nutrition.recipes.service.UserRecipesPlanService;
import com.jumper.weight.nutrition.recipes.vo.VOUserRecipesDetail;
import com.jumper.weight.nutrition.recipes.vo.VOUserRecipesList;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.service.HospitalOutpatientService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("userRecipes")
@Api(value = "/userRecipes", description = "用户食谱制定模块")
public class UserRecipesPlanController {
	
	@Autowired
	private UserRecipesPlanService userRecipesPlanService;
	@Autowired
	private StandardRecipesPlanService standardRecipesPlanService;
	@Autowired
	private HospitalOutpatientService hospitalOutpatientService;
	
	/**
	 * 查询某次门诊用户的食谱列表，没有就返回空
	 */
	@RequestMapping(value = "findUserRecipesPlansByOutPatientId", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取用户食谱列表", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户食谱列表")
	public ReturnMsg findUserRecipesPlansByOutPatientId(@ApiParam(value = "门诊id")@RequestParam("outpatientId") int outpatientId){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<UserRecipesPlan> planList = userRecipesPlanService.findUserRecipesPlansByOutPatientId(outpatientId);
			if(planList != null && planList.size() > 0){
				HospitalOutpatient hospitalOutpatient = hospitalOutpatientService.findById(outpatientId);
				if(hospitalOutpatient != null){
					if(StringUtils.isNotEmpty(hospitalOutpatient.getDietAdvice())){
						map.put("dietAdvice", hospitalOutpatient.getDietAdvice());
					}
					if(StringUtils.isNotEmpty(hospitalOutpatient.getDoctorAdvice())){
						map.put("doctorAdvice", hospitalOutpatient.getDoctorAdvice());
					}
				}
				map.put("planList", planList);
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户食谱列表成功！", map);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户食谱列表为空！", map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户食谱列表失败！");
		}
	}
	
	/**
	 * 查询某次门诊用户的食谱列表,没有就查询最近的一次方案
	 */
	@RequestMapping(value = "findUserRecipesPlans", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取用户食谱列表", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户食谱列表")
	public ReturnMsg findUserRecipesPlans(@ApiParam(value = "医院id")@RequestParam("hospitalId") int hospitalId,
			@ApiParam(value = "用户id")@RequestParam("userId") int userId, 
			@ApiParam(value = "门诊id")@RequestParam("outpatientId") int outpatientId){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<UserRecipesPlan> planList = userRecipesPlanService.findUserRecipesPlans(hospitalId, userId, outpatientId);
			if(planList != null && planList.size() > 0){
				int realOutpatientId = planList.get(0).getOutpatientId();
				HospitalOutpatient hospitalOutpatient = hospitalOutpatientService.findById(realOutpatientId);
				if(hospitalOutpatient != null && StringUtils.isNotEmpty(hospitalOutpatient.getDietAdvice())){
					map.put("dietAdvice", hospitalOutpatient.getDietAdvice());
				}
				map.put("planList", planList);
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户食谱列表成功！", map);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户食谱列表为空！", map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户食谱列表失败！");
		}
	}
	
	/**
	 * 点击修改方案（先查询本次门诊有没有记录，没有就另存一份最新的，然后查询出本次门诊的食谱记录）
	 */
	@RequestMapping(value = "saveAsOrFindUserRecipesPlans", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取或另存用户食谱列表", httpMethod = "POST", response = ReturnMsg.class, notes = "获取或另存用户食谱列表")
	public ReturnMsg saveAsOrFindUserRecipesPlans(@ApiParam(value = "医院id")@RequestParam("hospitalId") int hospitalId,
			@ApiParam(value = "用户id")@RequestParam("userId") int userId, 
			@ApiParam(value = "门诊id")@RequestParam("outpatientId") int outpatientId){
		try {
			List<UserRecipesPlan> planList = userRecipesPlanService.saveAsOrFindUserRecipesPlans(hospitalId, userId, outpatientId);
			if(planList != null && planList.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户食谱列表成功！", planList);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户食谱列表为空！", new ArrayList<StandardRecipesPlan>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户食谱列表失败！");
		}
	}
	
	/**
	 * 导入方案（某一个方案的食谱覆盖之前门诊的食谱记录）
	 */
	@RequestMapping(value = "importUserRecipesPlans", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "导入方案作为用户食谱", httpMethod = "POST", response = ReturnMsg.class, notes = "导入方案作为用户食谱")
	public ReturnMsg importUserRecipesPlans(@ApiParam(value = "医院id")@RequestParam("hospitalId") int hospitalId,
			@ApiParam(value = "用户id")@RequestParam("userId") int userId, 
			@ApiParam(value = "门诊id")@RequestParam("outpatientId") int outpatientId, 
			@ApiParam(value = "方案id")@RequestParam("planId") int planId){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			//获取食谱列表
			List<UserRecipesPlan> planList = userRecipesPlanService.importUserRecipesPlans(hospitalId, userId, outpatientId, planId);
			//获取方案对应的饮食建议
			StandardRecipesPlan standardPlan =  standardRecipesPlanService.findById(planId);
			if(standardPlan != null && StringUtils.isNotEmpty(standardPlan.getDietAdvice())){
				map.put("dietAdvice", standardPlan.getDietAdvice());
			}
			if(planList != null && planList.size() > 0){
				map.put("planList", planList);
				return new ReturnMsg(ReturnMsg.SUCCESS, "导入方案成功！", map);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "导入方案为空！", new HashMap<String, Object>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "导入方案失败！");
		}
	}
	
	/**
	 * 保存用户食谱（添加和修改）
	 */
	@RequestMapping(value = "saveUserRecipesPlan", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存用户食谱信息", httpMethod = "POST", response = ReturnMsg.class, notes = "保存用户食谱信息")
	public ReturnMsg saveUserRecipesPlan(@ApiParam(name = "param",value = "用户食谱信息", required=true)@RequestBody UserRecipesPlan userRecipesPlan){
		UserRecipesPlan recipesPlan = userRecipesPlanService.saveUserRecipesPlan(userRecipesPlan);
		if(recipesPlan != null){
			return new ReturnMsg(ReturnMsg.SUCCESS, "保存用户食谱成功", recipesPlan);
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "保存用户食谱失败，请稍后重试！");
		}
	}
	
	/**
	 * 删除用户食谱
	 */
	@RequestMapping(value = "deleteUserRecipesPlan", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "删除用户食谱", httpMethod = "POST", response = ReturnMsg.class, notes = "删除用户食谱")
	public ReturnMsg deleteUserRecipesPlan(@ApiParam(value = "用户食谱id")@RequestParam("recipesId") int recipesId){
		boolean b = userRecipesPlanService.delete(recipesId);
		if(b){
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除用户食谱成功！");
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "删除用户食谱失败！");
		}
	}
	
	/**
	 * 通过食谱方案id查询食谱记录
	 */
	@RequestMapping(value = "findUserRecipesPlansById", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取用户食谱列表", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户食谱列表")
	public ReturnMsg findUserRecipesPlansById(@ApiParam(value = "用户食谱记录id")@RequestParam("id") int id){
		try {
			UserRecipesPlan plan = userRecipesPlanService.findById(id);
			if(plan != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户食谱记录成功！", plan);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户食谱记录为空！", new UserRecipesPlan());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户食谱记录失败！");
		}
	}
	
	/**
	 * 查询用户最新一份食谱
	 */
	@RequestMapping(value = "findUserRecipesList", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "h5页面查看用户最新食谱列表", httpMethod = "POST", response = ReturnMsg.class, notes = "h5页面查看用户最新食谱列表")
	public ReturnMsg findUserRecipesList(@ApiParam(value = "医院id")@RequestParam("hospitalId") int hospitalId,
			@ApiParam(value = "用户id")@RequestParam("userId") int userId){
		try {
			Map<String, Object> map = userRecipesPlanService.findUserRecipesList(hospitalId, userId);
//			List<VOUserRecipesList> planList = userRecipesPlanService.findUserRecipesList(hospitalId, userId);
			List<VOUserRecipesList> planList = (List<VOUserRecipesList>) map.get("recipesList");
			if(planList != null && planList.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户食谱列表成功！", map);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户食谱列表为空！", new HashMap<String, Object>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户食谱列表失败！");
		}
	}
	
	/**
	 * 通过食谱id查询用户食谱记录
	 */
	@RequestMapping(value = "findUserRecipesById", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "通过食谱id查询用户食谱记录", httpMethod = "POST", response = ReturnMsg.class, notes = "通过食谱id查询用户食谱记录")
	public ReturnMsg findUserRecipesById(@ApiParam(value = "食谱id")@RequestParam("recipesId") int recipesId){
		try {
			VOUserRecipesList detailList = userRecipesPlanService.findUserRecipesById(recipesId);
			if(detailList != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "查询用户食谱记录成功！", detailList);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "用户食谱记录为空！", new VOUserRecipesList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "查询用户食谱记录失败！");
		}
	}
}
