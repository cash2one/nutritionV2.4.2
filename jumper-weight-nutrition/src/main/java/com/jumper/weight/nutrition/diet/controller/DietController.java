package com.jumper.weight.nutrition.diet.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.HttpClient;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.diet.entity.WeightMealsInfo;
import com.jumper.weight.nutrition.diet.service.WeightMealsInfoService;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;
import com.jumper.weight.nutrition.food.vo.VOWeightFood;
import com.jumper.weight.nutrition.report.service.WeightReportService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("diet")
@Api(value = "/diet", description = "饮食模块")
public class DietController {
	@Autowired
	private WeightMealsInfoService weightMealsInfoService;
	@Autowired
	private WeightReportService weightReportService;
	
	/**
	 * 条件查询用户饮食记录，不按时间分组分页
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value= "findUserMealsInfoList")
	@ResponseBody
	@ApiOperation(value = "获取用户饮食信息", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户饮食信息")
	public ReturnMsg findUserMealsInfoList(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "开始时间") @RequestParam(value="startTime", required=false) String startTime,
			@ApiParam(value = "结束时间") @RequestParam(value="endTime", required=false) String endTime, 
			@ApiParam(value = "餐次") @RequestParam(value="mealsType", required=false) Integer mealsType){
		try {
			if(mealsType == null){
				mealsType = 0;
			}
			List<VOWeightMealsInfo> voMealsInfoList = weightMealsInfoService.findUserMealsInfoList(userId, startTime, endTime, mealsType);
			if(voMealsInfoList != null && voMealsInfoList.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取饮食记录信息成功！", voMealsInfoList);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "无饮食记录信息！", new ArrayList<VOWeightMealsInfo>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取饮食记录信息失败！");
		}
	}
	
	/**
	 * 获取全部饮食记录（分页）
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value= "findAllUserMealsInfoList")
	@ResponseBody
	@ApiOperation(value = "获取用户饮食信息", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户饮食信息")
	public ReturnMsg findAllUserMealsInfoList(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "开始时间") @RequestParam String startTime,
			@ApiParam(value = "结束时间") @RequestParam String endTime,
			@ApiParam(value = "分页索引") @RequestParam int pageIndex,
			@ApiParam(value = "分页大小") @RequestParam int pageSize){
		try {
			//获取总记录数
			PageInfo pageInfo = weightMealsInfoService.findAllUserMealsInfoList(userId, startTime, endTime, pageIndex, pageSize);
			if(pageInfo.getList() != null && pageInfo.getList().size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取饮食记录分页信息成功！", pageInfo);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "无饮食记录信息！", new PageInfo());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取饮食分页记录信息失败！");
		}
	}
	
	/**
	 * 保存用户饮食记录
	 */
	@RequestMapping(method = RequestMethod.POST, value= "saveUserMealsInfo")
	@ResponseBody
	@ApiOperation(value = "保存用户饮食信息", httpMethod = "POST", response = ReturnMsg.class, notes = "保存用户饮食信息")
	public ReturnMsg saveUserMealsInfo(@ApiParam(value = "用户id") @RequestParam int userId,
			@ApiParam(value = "调查的饮食时间") @RequestParam(value="") String eatDate,
			@ApiParam(value = "用户饮食信息json") @RequestParam String mealsMsg){
		try {
//			mealsMsg = new String(mealsMsg.getBytes("ISO-8859-1"),"UTF-8");
			if(StringUtils.isEmpty(mealsMsg)){
				return new ReturnMsg(ReturnMsg.FAIL, "您还没有添加食物！");
			}
			if(StringUtils.isEmpty(eatDate)){
				eatDate = TimeUtils.dateFormatToString(new Date(), Const.YYYYMMDD);
			}
			List<WeightMealsInfo> mealsInfoList = JSON.parseArray(mealsMsg, WeightMealsInfo.class);
			boolean b = weightMealsInfoService.saveUserMealsInfo(mealsInfoList, userId, eatDate);
			if(b){
				return new ReturnMsg(ReturnMsg.SUCCESS, "保存用户饮食记录成功！");
			}else{
				return new ReturnMsg(ReturnMsg.FAIL, "保存用户饮食记录失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除用户某一条饮食记录
	 */
	@RequestMapping(method = RequestMethod.POST, value= "deleteUserMealsInfo")
	@ResponseBody
	@ApiOperation(value = "删除用户饮食信息", httpMethod = "POST", response = ReturnMsg.class, notes = "删除用户饮食信息")
	public ReturnMsg deleteUserMealsInfo(@ApiParam(value = "记录id") @RequestParam int mealsInfoId){
		boolean b = weightMealsInfoService.delete(mealsInfoId);
		if(b){
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除用户饮食记录成功！");
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "删除用户饮食记录失败！");
		}
	}
	
	/**
	 * 自动补全食材名称
	 */
	@RequestMapping("searchFood")
	@ResponseBody
	public void searchFood(String q, HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("foodName", q);
			HttpClient client = new HttpClient(Consts.BASE_PATH+"/food/findFoodByName", map);
			String result = client.post();
			String ret = "";
			if(result != null){
				ReturnMsg returnMsg = JSON.parseObject(result, ReturnMsg.class);
				if(returnMsg.getMsg()==1){
					ret = JSON.toJSONString(returnMsg.getData());
				}
			}
			Writer writer = response.getWriter();
			writer.write(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="searchFoods", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsg searchFoods(@ApiParam(value = "饮食日期") @RequestParam String keywords){
		if(StringUtils.isNotEmpty(keywords)){
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("foodName", keywords);
				HttpClient client = new HttpClient(Consts.BASE_PATH+"/food/findFoodByName", map);
				String result = client.post();
				if(result != null){
					ReturnMsg returnMsg = JSON.parseObject(result, ReturnMsg.class);
					if(returnMsg.getMsg()==1){
						List<VOWeightFood> foodList = JSON.parseArray(JSON.toJSONString(returnMsg.getData()), VOWeightFood.class);
						return new ReturnMsg(ReturnMsg.SUCCESS, "搜索食材成功", foodList);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ReturnMsg(ReturnMsg.FAIL, "无搜索结果");
	}
	
	/**
	 * 删除用户某一天的饮食记录
	 * @param mealsInfoId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value= "deleteUserMealsInfoByDay")
	@ResponseBody
	@ApiOperation(value = "删除用户某一天的饮食记录", httpMethod = "POST", response = ReturnMsg.class, notes = "删除用户某一天的饮食记录")
	public ReturnMsg deleteUserMealsInfoByDay(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "饮食日期") @RequestParam String eatDate){
		boolean b = weightMealsInfoService.deleteUserMealsInfo(userId, eatDate);
		if(b){
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除用户饮食记录成功！");
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "删除用户饮食记录失败！");
		}
	}
	
	/**
	 * 重命名用户饮食记录日期（修改日期）
	 */
	@RequestMapping(method = RequestMethod.POST, value= "reNameUserMealsInfo")
	@ResponseBody
	@ApiOperation(value = "重命名用户饮食记录日期", httpMethod = "POST", response = ReturnMsg.class, notes = "重命名用户饮食记录日期")
	public ReturnMsg reNameUserMealsInfo(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "旧的饮食日期") @RequestParam String oldDate, 
			@ApiParam(value = "新的饮食日期") @RequestParam String newDate){
		boolean b = weightMealsInfoService.reNameUserMealsInfo(userId, oldDate, newDate);
		if(b){
			return new ReturnMsg(ReturnMsg.SUCCESS, "重命名用户饮食记录日期成功！");
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "重命名用户饮食记录日期失败！");
		}
	}
	
	/**
	 * 查询用户近7天的饮食记录日期列表
	 */
	@RequestMapping(method = RequestMethod.POST, value= "findUserLatestSevenDays")
	@ResponseBody
	@ApiOperation(value = "获取用户饮食信息", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户饮食信息")
	public ReturnMsg findUserLatestSevenDays(@ApiParam(value = "用户id") @RequestParam int userId){
		try {
			List<String> dateList = weightMealsInfoService.findUserLatestSevenDays(userId);
			if(dateList != null && dateList.size() > 0){
				//是否显示查看全部按钮
				int isShow = 0;
				int count = weightMealsInfoService.findCount(userId,"","");
				if(count > 7){
					isShow = 1;
				}
				//获取近7天用户饮食记录总食物个数
				int countFood = weightMealsInfoService.findFoodCount(userId, dateList);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dateList", dateList);
				map.put("isShow", isShow);
				map.put("foodCount", countFood);
				map.put("days", dateList.size());
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取饮食记录最近七天日期成功！", map);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "无饮食记录信息！", new HashMap<String, Object>());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取饮食记录日期信息失败！");
		}
	}
	
}
