package com.jumper.weight.nutrition.recipes.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesDetail;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesPlan;
import com.jumper.weight.nutrition.recipes.service.StandardRecipesDetailService;
import com.jumper.weight.nutrition.recipes.service.StandardRecipesPlanService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("plan")
@Api(value = "/plan", description = "配置食谱方案模块")
public class StandardRecipesController {
	@Autowired
	private StandardRecipesPlanService standardRecipesPlanService;
	@Autowired
	private StandardRecipesDetailService standardRecipesDetailService;
	
	/**
	 * 获取方案列表
	 */
	@RequestMapping(value = "findStandardRecipesPlans", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取医院食谱方案列表", httpMethod = "POST", response = ReturnMsg.class, notes = "获取医院食谱方案列表")
	public ReturnMsg findStandardRecipesPlans(@ApiParam(value = "医院id")@RequestParam("hospitalId") int hospitalId,
			@ApiParam(value = "方案名称")@RequestParam(value="keywords", required=false) String keywords){
		try {
			/*if(StringUtils.isNotEmpty(keywords)){
				keywords = new String(keywords.getBytes("ISO-8859-1"),"UTF-8");
			}*/
			List<StandardRecipesPlan> planList = standardRecipesPlanService.findStandardRecipesPlans(hospitalId, keywords);
			if(planList != null && planList.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取食谱方案列表成功！", planList);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "食谱方案列表为空！", new ArrayList<StandardRecipesPlan>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取食谱方案列表失败！");
		}
	}
	
	/**
	 * 获取方案下的食谱列表
	 */
	@RequestMapping(value = "findStandardRecipesByPlan", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取医院某方案的食谱列表", httpMethod = "POST", response = ReturnMsg.class, notes = "获取医院某方案的食谱列表")
	public ReturnMsg findStandardRecipesByPlan(@ApiParam(value = "方案id")@RequestParam("planId") int planId){
		try {
			List<StandardRecipesDetail> recipesList = standardRecipesDetailService.findStandardRecipesByPlan(planId);
			Map<String, Object> map = new HashMap<String, Object>();
			//获取方案对应的饮食建议
			StandardRecipesPlan standardPlan =  standardRecipesPlanService.findById(planId);
			if(standardPlan != null && StringUtils.isNotEmpty(standardPlan.getDietAdvice())){
				map.put("dietAdvice", standardPlan.getDietAdvice());
			}
			if(recipesList != null && recipesList.size() >0){
				map.put("recipesList", recipesList);
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取标准食谱列表成功！", map);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "标准食谱列表为空！", new HashMap<String, Object>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取标准食谱列表失败！");
		}
	}
	
	//删除方案（同时删除食谱）
	@RequestMapping(value = "deleteStandardRecipesPlan", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "删除方案", httpMethod = "POST", response = ReturnMsg.class, notes = "删除方案")
	public ReturnMsg deleteStandardRecipesPlan(@ApiParam(value = "方案id")@RequestParam("planId") int planId){
		boolean b = standardRecipesPlanService.deleteStandardRecipesPlan(planId);
		if(b){
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除方案成功！");
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "删除方案失败！");
		}
	}
	
	//删除食谱
	@RequestMapping(value = "deleteStandardRecipesDetail", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "删除标准食谱", httpMethod = "POST", response = ReturnMsg.class, notes = "删除标准食谱")
	public ReturnMsg deleteStandardRecipesDetail(@ApiParam(value = "食谱id")@RequestParam("recipesId") int recipesId){
		boolean b = standardRecipesDetailService.delete(recipesId);
		if(b){
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除食谱成功！");
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "删除食谱失败！");
		}
	}
	
	//保存方案（新增以后返回方案信息到前端用于展示和添加食谱）
	@RequestMapping(value = "saveStandardRecipesPlan", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存方案信息", httpMethod = "POST", response = ReturnMsg.class, notes = "保存方案信息")
	public ReturnMsg saveStandardRecipesPlan(@ApiParam(name = "param",value = "方案信息", required=true)@RequestBody StandardRecipesPlan recipesPlan){
		int planId = recipesPlan.getId();
		StandardRecipesPlan existPlan = standardRecipesPlanService.findStandardRecipesPlanByName(recipesPlan);
		//给新增的方案默认新增一个食谱一
		if(existPlan != null){
			return new ReturnMsg(ReturnMsg.DATA_NULL, "该方案名已存在！");
		}else{
			StandardRecipesPlan plan = standardRecipesPlanService.saveStandardRecipesPlan(recipesPlan);
			if(plan != null){
				Map<String, Object> map = new HashMap<String, Object>();
				if(planId == 0){
					StandardRecipesDetail recipes = new StandardRecipesDetail();
					recipes.setAddTime(new Date());
					recipes.setName("食谱一");
					recipes.setRecipesPlanId(recipesPlan.getId());
					standardRecipesDetailService.insert(recipes);
					map.put("recipes", recipes);
				}
				map.put("plan", plan);
				return new ReturnMsg(ReturnMsg.SUCCESS, "保存方案成功", map);
			}else{
				return new ReturnMsg(ReturnMsg.FAIL, "保存方案失败，请稍后重试！");
			}
		}
	}
	
	//保存食谱
	@RequestMapping(value = "saveStandardRecipesDetail", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存食谱信息", httpMethod = "POST", response = ReturnMsg.class, notes = "保存食谱信息")
	public ReturnMsg saveStandardRecipesDetail(@ApiParam(value = "食谱信息")@RequestBody StandardRecipesDetail recipesDetail){
		StandardRecipesDetail recipes = standardRecipesDetailService.saveStandardRecipesDetail(recipesDetail);
		if(recipes != null){
			return new ReturnMsg(ReturnMsg.SUCCESS, "保存食谱成功", recipes);
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "保存食谱失败，请稍后重试！");
		}
	}
	
	//根据食谱id查询食谱数据
	@RequestMapping(value = "findStandardRecipesById", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据食谱id获取食谱数据", httpMethod = "POST", response = ReturnMsg.class, notes = "根据食谱id获取食谱数据")
	public ReturnMsg findStandardRecipesById(@ApiParam(value = "食谱id")@RequestParam("recipesId") int recipesId){
		StandardRecipesDetail recipes = standardRecipesDetailService.findById(recipesId);
		if(recipes != null){
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询食谱数据成功", recipes);
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "查询食谱数据失败，请稍后重试！");
		}
	}
	
	
}
