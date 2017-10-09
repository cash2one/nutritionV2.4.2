package com.jumper.weight.nutrition.sport.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.sport.entity.SportInfo;
import com.jumper.weight.nutrition.sport.service.SportInfoService;
import com.jumper.weight.nutrition.sport.service.UserSportRecordService;
import com.jumper.weight.nutrition.sport.vo.VoDailySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoQuerySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoUserSportRecord;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 运动模块
 * @Description TODO
 * @author fangxilin
 * @date 2017-4-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@RestController
@RequestMapping("/sport")
@Api(value = "/sport", description = "运动模块")
public class SportController extends BaseController {
	
	@Autowired
	private SportInfoService sportInfoService;
	@Autowired
	private UserSportRecordService userSportRecordService;
	
	/**
	 * 通过运动名模糊查询
	 * @version 1.0
	 * @createTime 2017-4-28,下午1:48:42
	 * @createAuthor fangxilin
	 * @param query
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listSportsByName")
	@ApiOperation(value = "通过运动名模糊查询", httpMethod = "POST", response = ReturnMsg.class, notes = "通过运动名模糊查询", position = 1)
	public ReturnMsg listSportsByName(@ApiParam(value = "运动名（如果要查询所有就传空）") @RequestParam(required = false) String query) {
		try {
			if (StringUtils.isEmpty(query)) {
				query = null;
			}
			List<SportInfo> data = sportInfoService.listSportsByName(query);
			//Object data = (user != null) ? user : new ArrayList<>();
			return new ReturnMsg(ReturnMsg.SUCCESS, "通过运动名模糊查询成功", data);
		} catch (Exception e) {
			logger.error("通过运动名模糊查询异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "通过运动名模糊查询异常");
		}
	}
	
	/**
	 * 添加用户某天的运动记录
	 * @createTime 2017-5-2,下午2:09:10
	 * @createAuthor fangxilin
	 * @param recordList
	 * @param userId
	 * @param date
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addSportRecordByDate")
	@ApiOperation(value = "添加用户某天的运动记录", httpMethod = "POST", response = ReturnMsg.class, notes = "添加用户某天的运动记录", position = 2)
	public ReturnMsg addSportRecordByDate(@ApiParam(value = "某天的运动记录集合") @RequestParam String recordList, 
			@ApiParam(value = "用户id") @RequestParam int userId,
			@ApiParam(value = "运动日期（yyyy-MM-dd）") @RequestParam String date) {
		try {
			List<VoUserSportRecord> voList = JSON.parseArray(recordList, VoUserSportRecord.class);
			if (ArrayUtils.isEmpty(voList)) {
				return new ReturnMsg(ReturnMsg.FAIL, "运动记录不能为空");
			}
			boolean ret = userSportRecordService.addSportRecordByDate(userId, date, voList);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "添加用户某天的运动记录失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "添加用户某天的运动记录成功");
		} catch (Exception e) {
			logger.error("添加用户某天的运动记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "添加用户某天的运动记录异常");
		}
	}
	
	/**
	 * 删除某条运动记录
	 * @createTime 2017-5-2,下午3:05:00
	 * @createAuthor fangxilin
	 * @param recordId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/delteSportRecordById")
	@ApiOperation(value = "删除某条运动记录", httpMethod = "POST", response = ReturnMsg.class, notes = "删除某条运动记录", position = 3)
	public ReturnMsg delteSportRecordById(@ApiParam(value = "运动记录id") @RequestParam int recordId) {
		try {
			boolean ret = userSportRecordService.delete(recordId);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "删除某条运动记录失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除某条运动记录成功");
		} catch (Exception e) {
			logger.error("删除某条运动记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "删除某条运动记录异常");
		}
	}
	
	/**
	 * 查询用户某天的运动记录
	 * @createTime 2017-5-2,下午4:00:42
	 * @createAuthor fangxilin
	 * @param userId
	 * @param date
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listSportRecordByDate")
	@ApiOperation(value = "查询用户某天的运动记录", httpMethod = "POST", response = ReturnMsg.class, notes = "查询用户某天的运动记录", position = 4)
	public ReturnMsg listSportRecordByDate(@ApiParam(value = "用户id") @RequestParam int userId,
			@ApiParam(value = "运动日期（yyyy-MM-dd）") @RequestParam String date) {
		try {
			VoDailySportRecord data = userSportRecordService.listSportRecordByDate(userId, date);
			data = (data != null) ? data : new VoDailySportRecord();
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询用户某天的运动记录成功", data);
		} catch (Exception e) {
			logger.error("查询用户某天的运动记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "查询用户某天的运动记录异常");
		}
	}
	
	/**
	 * 分页查询用户时间段内的运动记录
	 * @createTime 2017-5-4,下午5:20:00
	 * @createAuthor fangxilin
	 * @param voQuery
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listSportRecordByDuring")
	@ApiOperation(value = "分页查询用户时间段内的运动记录", httpMethod = "POST", response = ReturnMsg.class, notes = "分页查询用户时间段内的运动记录", position = 5)
	public ReturnMsg listSportRecordByDuring(@ApiParam(value = "分页查询参数") VoQuerySportRecord voQuery) {
		try {
			if (voQuery.getUserId() == null || voQuery.getPage() == null || voQuery.getLimit() == null) {
				return new ReturnMsg(ReturnMsg.FAIL, "分页查询参数不全");
			}
			PageInfo<VoDailySportRecord> data = userSportRecordService.listSportRecordByDuring(voQuery);
			return new ReturnMsg(ReturnMsg.SUCCESS, "分页查询用户时间段内的运动记录成功", data);
		} catch (Exception e) {
			logger.error("分页查询用户时间段内的运动记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "分页查询用户时间段内的运动记录异常");
		}
	}
	
}
