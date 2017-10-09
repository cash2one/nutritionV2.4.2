package com.jumper.weight.nutrition.record.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jumper.record.service.dubbo.RecordSugarDubbo;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.record.entity.UserSugarRecord;
import com.jumper.weight.nutrition.record.service.UserSugarRecordService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 血糖模块Controller
 * @Description TODO
 * @author fangxilin
 * @date 2017年9月13日
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@RestController
@RequestMapping("/sugar")
@Api(value = "/sugar", description = "血糖模块")
public class SugarController extends BaseController {
	
	@Autowired
	private RecordSugarDubbo recordSugarDubbo;
	@Autowired
	private UserSugarRecordService userSugarRecordService;
	
	/**
	 * 保存用户目前血糖值
	 * @createTime 2017年9月13日,下午1:39:46
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @param mealType
	 * @param sugarValue
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveSugarRecord")
	@ApiOperation(value = "保存用户目前血糖值", httpMethod = "POST", response = ReturnMsg.class, notes = "保存用户目前血糖值", position = 1)
	public ReturnMsg saveSugarRecord(@ApiParam(value = "用户id") @RequestParam int userId,
			@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "餐次 0：早餐前，1：早餐后，2：午餐前，3：午餐后，4：晚餐前，5：晚餐后，6：睡前") @RequestParam int mealType,
			@ApiParam(value = "血糖值") @RequestParam double sugarValue) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_id", userId);
			param.put("hospital_id", hospitalId);
			param.put("add_time", TimeUtils.dateFormatToString(new Date(), Const.YYYYMMDD_HHMMSS));
			param.put("test_time_state", mealType);
			param.put("average_value", sugarValue);
			String paramStr = JSON.toJSONString(param);
			//添加或更新血糖监测数据
			com.jumper.common.web.ReturnMsg ret = recordSugarDubbo.addBloodSugar(paramStr);
			if (ret.getMsg() != 1) {
				return new ReturnMsg(ReturnMsg.FAIL, "保存用户目前血糖值失败");
			}
			if (ret.getMsg() == 1 && ret.getData() != null) {
				//表示血糖出现重复记录！
				List<Map<String, Object>> retdata = (List<Map<String, Object>>) ret.getData();
				Integer blood_id = (Integer) retdata.get(0).get("blood_id");
				param.put("blood_id", blood_id);//更新
				paramStr = JSON.toJSONString(param);
				ret = recordSugarDubbo.addBloodSugar(paramStr);
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "保存用户目前血糖值成功");
		} catch (Exception e) {
			logger.error("保存用户目前血糖值异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "保存用户目前血糖值异常");
		}
	}
	
	/**
	 * 获取用户最近一条血糖记录
	 * @createTime 2017-5-5,下午1:59:50
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findUserLastSugar")
	@ApiOperation(value = "获取用户最近一条血糖记录", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户最近一条血糖记录", position = 1)
	public ReturnMsg findUserLastSugar(@ApiParam(value = "用户id") @RequestParam int userId) {
		try {
			UserSugarRecord data = userSugarRecordService.findUserLastSugar(userId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户最近一条血糖记录成功", data);
		} catch (Exception e) {
			logger.error("获取用户最近一条血糖记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户最近一条血糖记录异常");
		}
	}
}
