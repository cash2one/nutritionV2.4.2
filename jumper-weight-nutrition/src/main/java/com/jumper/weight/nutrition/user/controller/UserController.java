package com.jumper.weight.nutrition.user.controller;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.hospital.entity.HospitalInfo;
import com.jumper.weight.nutrition.hospital.service.HospitalInfoService;
import com.jumper.weight.nutrition.record.service.UserWeightRecordService;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.service.HospitalOutpatientService;
import com.jumper.weight.nutrition.user.service.HospitalUserInfoService;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoAddUserReturn;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "用户模块")
public class UserController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserWeightRecordService userWeightRecordService;
	@Autowired
	private HospitalOutpatientService hospitalOutpatientService;
	@Autowired
	private HospitalUserInfoService hospitalUserInfoService;
	@Autowired
	private HospitalInfoService hospitalInfoService;
	
	/**
	 * 非建档页面获取用户信息
	 * @createTime 2017-4-26,下午6:05:15
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findUserByUserId")
	@ApiOperation(value = "获取用户信息", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户信息", position = 1)
	public ReturnMsg findUserByUserId(@ApiParam(value = "用户id(userId和mobile不能都为空)") @RequestParam int userId,
			@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			VoUserInfo data = new VoUserInfo();
			data = userInfoService.findVoUserByUId(userId, hospitalId);
			if (data == null) {
				return new ReturnMsg(ReturnMsg.FAIL, "不存在该用户");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户信息成功", data);
		} catch (Exception e) {
			logger.error("获取用户信息异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户信息异常");
		}
	}
	
	/**
	 * 建档页面获取用户信息
	 * @createTime 2017-7-27,下午4:18:31
	 * @createAuthor fangxilin
	 * @param userId
	 * @param mobile
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findUserByIdMobile")
	@ApiOperation(value = "获取用户信息", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户信息", position = 2)
	public ReturnMsg findUserByIdMobile(@ApiParam(value = "用户id(userId和mobile不能都为空)") @RequestParam(required = false) Integer userId,
			@ApiParam(value = "手机号") @RequestParam(required = false) String mobile,
			@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			if (userId == null && StringUtils.isEmpty(mobile)) {
				return new ReturnMsg(ReturnMsg.FAIL, "用户id和手机号不能都为空");
			}
			VoUserInfo data = new VoUserInfo();
			if (userId != null) {
				data = userInfoService.findVoUserByUId(userId, hospitalId);
			} else {
				//data = userInfoService.findVoUserByMobile(mobile, hospitalId);
				data = userInfoService.findWeiTsUserByMob(mobile, hospitalId);
			}
			data = (data != null) ? data : new VoUserInfo(); 
			//查询用户最近的一条完成诊断的门诊记录
			if (data.getUserId() != null) {//孕妇管理中设置上次门诊日期
				HospitalOutpatient outp = hospitalOutpatientService.findUserLastOutpatient(hospitalId, data.getUserId(), 1);
				String finishTime = (outp != null) ? TimeUtils.dateFormatToString(outp.getFinishTime(), Const.YYYYMMDD) : null;
				data.setFinishTime(finishTime);
			}
			HospitalInfo hosp = hospitalInfoService.findById(hospitalId);
			String hospitalName = (hosp != null) ? hosp.getName() : ""; 
			data.setHospitalName(hospitalName);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户信息成功", data);
		} catch (Exception e) {
			logger.error("获取用户信息异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户信息异常");
		}
	}
	
	/**
	 * 添加或修改用户信息
	 * @createTime 2017-4-26,下午6:04:45
	 * @createAuthor fangxilin
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addOrUpdateUserInfo")
	@ApiOperation(value = "添加或修改用户信息", httpMethod = "POST", response = ReturnMsg.class, notes = "添加或修改用户信息", position = 3)
	public ReturnMsg addOrUpdateUserInfo(@ApiParam(value = "用户信息参数(如果userId为空代表新建档，否则为修改)") @RequestParam String userParams, 
			@ApiParam(value = "体检信息参数") @RequestParam String physicalParams) {
		try {
			//用户信息的校验
			VoUserInfo voUserInfo = JSON.parseObject(userParams, VoUserInfo.class);
			if (voUserInfo.getHospitalId() == null) {
				return new ReturnMsg(ReturnMsg.FAIL, "医院id不能为空");
			}
			if (voUserInfo.getUserId() == null) {//表示新建档
				if (StringUtils.isEmpty(voUserInfo.getMobile())) {
					return new ReturnMsg(ReturnMsg.FAIL, "手机号不能为空");
				}
				VoUserInfo info = userInfoService.findVoUserByMobile(voUserInfo.getMobile(), voUserInfo.getHospitalId());
				if (info != null) {
					return new ReturnMsg(ReturnMsg.FAIL, "该手机号已经注册过了");
				}
			}
			
			//体检数据的校验
			VoUserWeightRecord voRecord = JSON.parseObject(physicalParams, VoUserWeightRecord.class);
			if (voRecord.getAverageValue() == null || voRecord.getAverageValue() < 20 || voRecord.getAverageValue() > 150) {//20~150
				return new ReturnMsg(ReturnMsg.FAIL, "当前体重应该在20-150之间");
			}
			//校验就诊卡号
			if (!voUserInfo.getOutpatientNum().matches(Const.NUMEN)) {
				return new ReturnMsg(ReturnMsg.FAIL, "请输入正确的就诊卡号");
			}
			voUserInfo.setWeight(FunctionUtils.setDecimal(voUserInfo.getWeight(), 1));//保留一位小数
			voRecord.setAverageValue(FunctionUtils.setDecimal(voRecord.getAverageValue(), 1));
			//添加或更新用户信息
			int userId = userInfoService.addOrUpdteUser(voUserInfo);
			if(userId == 0) {
				return new ReturnMsg(ReturnMsg.FAIL, "添加或修改用户信息失败");
			}
			//如果为新建档时，应当新增一条门诊记录
			Integer outpatientId = null;
			if(voUserInfo.getIsAddOutp() == 1) {
				outpatientId = hospitalOutpatientService.addOutpatient(userId, voUserInfo.getHospitalId(), null, 0);
				if (outpatientId == 0) {
					return new ReturnMsg(ReturnMsg.SUCCESS, "新增门诊记录失败");
				}
			}
			//添加或更新体重监测数据
			boolean ret = userWeightRecordService.addOrUpdateWeightRecord(voRecord, userId, voUserInfo.getHospitalId());
			if (!ret) {
				return new ReturnMsg(ReturnMsg.SUCCESS, "添加体检信息失败");
			}
			VoAddUserReturn data = new VoAddUserReturn(userId, outpatientId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "添加或修改用户信息成功", data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加或修改用户信息异常");
			return new ReturnMsg(ReturnMsg.FAIL, "添加或修改用户信息异常", new ArrayList<>());
		}
	}
	
	/**
	 * 保存用户openID
	 */
	@RequestMapping("saveUserOpenId")
	public ReturnMsg saveUserOpenId(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "渠道（1：支付宝，2：微信）") @RequestParam int channel,
			@ApiParam(value = "openid") @RequestParam String openId){
		boolean b = hospitalUserInfoService.saveUserOpenId(userId, channel, openId);
		if(b){
			return new ReturnMsg(ReturnMsg.SUCCESS, "保存用户openID成功");
		}else{
			return new ReturnMsg(ReturnMsg.FAIL, "保存用户openID失败");
		}
	}
}
