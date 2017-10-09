package com.jumper.weight.nutrition.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalSettingService;
import com.jumper.weight.nutrition.user.entity.HospitalUserManage;
import com.jumper.weight.nutrition.user.service.HospitalUserManageService;
import com.jumper.weight.nutrition.user.vo.VoHospitalUserManage;
import com.jumper.weight.nutrition.user.vo.VoQueryUserManage;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/userManage")
@Api(value = "/userManage", description = "孕妇管理模块")
public class UserManageController extends BaseController {
	
	@Autowired
	private HospitalUserManageService hospitalUserManageService;
	@Autowired
	private WeightHospitalSettingService weightHospitalSettingService;
	
	/**
	 * 通过各条件筛选孕妇管理分页列表（有延迟3分钟定时时间）
	 * @createTime 2017-5-8,下午7:22:02
	 * @createAuthor fangxilin
	 * @param voQuery
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listUserManageByPage")
	@ApiOperation(value = "通过各条件筛选孕妇管理分页列表", httpMethod = "POST", response = ReturnMsg.class, notes = "通过各条件筛选孕妇管理分页列表", position = 1)
	public ReturnMsg listUserManageByPage(@ApiParam(value = "分页查询参数") VoQueryUserManage voQuery) {
		try {
			if (voQuery.getHospitalId() == null) {
				return new ReturnMsg(ReturnMsg.FAIL, "医院id不能为空");
			}
			String query = (StringUtils.isNotEmpty(voQuery.getQuery())) ? voQuery.getQuery() : null;
			String startExpDate = (StringUtils.isNotEmpty(voQuery.getStartExpDate())) ? voQuery.getStartExpDate() : null;
			String endExpDate = (StringUtils.isNotEmpty(voQuery.getEndExpDate())) ? voQuery.getEndExpDate() : null;
			voQuery.setQuery(query);
			voQuery.setStartExpDate(startExpDate);
			voQuery.setEndExpDate(endExpDate);
			PageInfo<VoHospitalUserManage> data = hospitalUserManageService.listUserManageByPage(voQuery);
			return new ReturnMsg(ReturnMsg.SUCCESS, "通过各条件筛选孕妇管理分页列表成功", data);
		} catch (Exception e) {
			logger.error("通过各条件筛选孕妇管理分页列表异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "通过各条件筛选孕妇管理分页列表异常");
		}
	}
	
	/**
	 * 删除孕妇档案
	 * @createTime 2017-6-26,下午5:52:04
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deleteUserManage")
	@ApiOperation(value = "删除孕妇档案", httpMethod = "POST", response = ReturnMsg.class, notes = "删除孕妇档案", position = 2)
	public ReturnMsg deleteUserManage(@ApiParam(value = "医院id") int hospitalId,
			@ApiParam(value = "用户id") int userId) {
		try {
			boolean ret = hospitalUserManageService.deleteUserManage(hospitalId, userId);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "删除孕妇档案失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除孕妇档案成功");
		} catch (Exception e) {
			logger.error("删除孕妇档案异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "删除孕妇档案异常");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/checkNutritionConsult")
	@ApiOperation(value = "查询用户是否可以进行营养咨询", httpMethod = "POST", response = ReturnMsg.class, notes = "查询用户是否可以进行营养咨询")
	public ReturnMsg checkNutritionConsult(@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "用户ID") @RequestParam int userId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("imNumber", "101_yy_"+hospitalId);
			map.put("consultId", userId);
			WeightHospitalSetting setting = weightHospitalSettingService.findSettingByHospId(hospitalId);
			if(setting != null && setting.getNutritionConsult()==1){
				HospitalUserManage userManage = hospitalUserManageService.findHospitalUserManage(hospitalId, userId);
				if(userManage != null){
					map.put("canChat", 1);
					return new ReturnMsg(ReturnMsg.SUCCESS, "查询用户营养咨询状态成功", map);
				}
			}
			map.put("canChat", 0);
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询用户营养咨询状态成功", map);
		} catch (Exception e) {
			logger.error("查询用户营养咨询状态异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "查询用户营养咨询状态异常");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getNoRead")
	@ApiOperation(value = "查询未读消息条数", httpMethod = "POST", response = ReturnMsg.class, notes = "查询未读消息条数")
	public ReturnMsg getNoRead(@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			Integer noread = hospitalUserManageService.getNoRead(hospitalId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询未读消息条数成功", noread);
		} catch (Exception e) {
			logger.error("查询未读消息条数异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "查询未读消息条数异常");
		}
	}
}
