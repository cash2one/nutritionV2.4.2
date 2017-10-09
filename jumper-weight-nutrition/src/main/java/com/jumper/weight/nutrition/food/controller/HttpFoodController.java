package com.jumper.weight.nutrition.food.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.food.entity.WeightFood;
import com.jumper.weight.nutrition.food.service.FoodService;
import com.jumper.weight.nutrition.food.vo.VOWeightFood;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("food")
@Api(value = "/food", description = "食材模块")
public class HttpFoodController {
	@Autowired
	private FoodService foodService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/findFoodByName")
	@ResponseBody
	@ApiOperation(value = "通过食材名称查询食材信息", httpMethod = "POST", response = String.class, notes = "通过食材名称查询食材信息")
	public ReturnMsg findFoodByName(@ApiParam(value = "食材名称") @RequestParam String foodName){
		ReturnMsg returnMsg = null;
		try {
			List<VOWeightFood> voFoodList = foodService.findFoodByName(foodName);
			if(voFoodList != null && voFoodList.size() > 0){
				returnMsg = new ReturnMsg(ReturnMsg.SUCCESS, "获取食材列表成功！", voFoodList);
			}else{
				returnMsg = new ReturnMsg(ReturnMsg.DATA_NULL, "食材列表为空！", new ArrayList<VOWeightFood>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg = new ReturnMsg(ReturnMsg.FAIL, "获取食材列表失败！");
		}
		return returnMsg;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findFoodByIds")
	@ResponseBody
	@ApiOperation(value = "通过食材id集合查询食材信息", httpMethod = "POST", response = String.class, notes = "通过食材id集合查询食材信息")
	public ReturnMsg findFoodByIds(@ApiParam(value = "食材id集合") @RequestParam String ids){
		try {
			String[] foodIds = ids.split(",");
			List<WeightFood> foodList = foodService.findFoodByIds(foodIds);
			if(foodList != null && foodList.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取食材信息列表成功！", foodList);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "查询列表为空！", new ArrayList<WeightFood>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取食材信息失败！");
		}
	}
	
}
