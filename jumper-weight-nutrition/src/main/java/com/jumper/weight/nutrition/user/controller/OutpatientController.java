package com.jumper.weight.nutrition.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.service.HospitalOutpatientService;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoHospitalOutpatient;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 门诊模块
 * @Description TODO
 * @author fangxilin
 * @date 2017-4-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@RestController
@RequestMapping("/outpatient")
@Api(value = "/outpatient", description = "门诊模块")
public class OutpatientController extends BaseController {
	
	@Autowired
	private HospitalOutpatientService hospitalOutpatientService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 按条件显示医院门诊用户列表（实时查询，没有延迟）
	 * @createTime 2017-4-28,下午3:26:58
	 * @createAuthor fangxilin
	 * @param query
	 * @param hospitalId
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listOutpatientUser")
	@ApiOperation(value = "按条件显示医院门诊用户列表", httpMethod = "POST", response = ReturnMsg.class, notes = "按条件显示医院门诊用户列表", position = 1)
	public ReturnMsg listOutpatientUser(@ApiParam(value = "姓名或手机（如果为空表示查询所有）") @RequestParam(required = false) String query, 
			@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "当前页") @RequestParam int page,
			@ApiParam(value = "每页大小") @RequestParam int limit,
			@ApiParam(value = "初复诊状态") @RequestParam int status) {
		try {
			if (StringUtils.isEmpty(query)) {
				query = null;
			}
			PageInfo<VoHospitalOutpatient> data = hospitalOutpatientService.listOutpatientUser(hospitalId, query, page, limit, status);
			return new ReturnMsg(ReturnMsg.SUCCESS, "按条件显示医院门诊用户列表成功", data);
		} catch (Exception e) {
			logger.error("按条件显示医院门诊用户列表异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "按条件显示医院门诊用户列表异常");
		}
	}
	
	/**
	 * 通过id删除或批量删除门诊记录
	 * @createTime 2017-4-28,下午6:06:48
	 * @createAuthor fangxilin
	 * @param idList
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deleteOutpatient")
	@ApiOperation(value = "通过id删除或批量删除门诊记录", httpMethod = "POST", response = ReturnMsg.class, notes = "通过id删除或批量删除门诊记录", position = 2)
	public ReturnMsg deleteOutpatient(@ApiParam(value = "门诊id集合") @RequestParam("idList[]") List<Integer> idList) {
		try {
			if (ArrayUtils.isEmpty(idList)) {
				return new ReturnMsg(ReturnMsg.FAIL, "请选择要删除的门诊记录");
			}
			boolean ret = hospitalOutpatientService.deleteOutpatient(idList);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "删除失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "删除成功");
		} catch (Exception e) {
			logger.error("通过id删除或批量删除门诊记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "通过id删除或批量删除门诊记录异常");
		}
	}
	
	/**
	 * 判断用户是否是初诊
	 * @createTime 2017-4-28,下午6:46:22
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/isFirstOutpatient")
	@ApiOperation(value = "判断用户是否是初诊", httpMethod = "POST", response = ReturnMsg.class, notes = "判断用户是否是初诊", position = 3)
	public ReturnMsg isFirstOutpatient(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			HospitalOutpatient outp = hospitalOutpatientService.findUserLastOutpatient(hospitalId, userId, null);
			//type 0：初诊，1：初诊未完成诊断，2：复诊，3：复诊未完成诊断
			Integer type = 2;
			if (outp == null) {
				type = 0;
			} else if (outp.getStatus() == 0 && outp.getIsFinish() == 0) {
				type = 1;
			} else if (outp.getStatus() == 1 && outp.getIsFinish() == 0) {
				type = 3;
			}
			Map<String, Integer> data = new HashMap<String, Integer>();
			data.put("type", type);
			if (outp != null) {
				data.put("outpatientId", outp.getId());
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "判断用户是否是初诊成功", data);
		} catch (Exception e) {
			logger.error("判断用户是否是初诊异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "判断用户是否是初诊异常");
		}
	}
	
	/**
	 * 添加一条门诊记录
	 * @createTime 2017-5-4,下午7:36:01
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @param userDefinedSport
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addOutpatient")
	@ApiOperation(value = "添加一条门诊记录", httpMethod = "POST", response = ReturnMsg.class, notes = "添加一条门诊记录", position = 4)
	public ReturnMsg addOutpatient(@ApiParam(value = "用户id") @RequestParam int userId, 
			@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "用户自定义运动") @RequestParam(required = false) String userDefinedSport, 
			@ApiParam(value = "就诊原因id") @RequestParam(required = false) Integer outpatientReason) {
		try {
			outpatientReason = (outpatientReason != null) ? outpatientReason : 0;
			int ret = hospitalOutpatientService.addOutpatient(userId, hospitalId, userDefinedSport, outpatientReason);
			if (ret == 0) {
				return new ReturnMsg(ReturnMsg.FAIL, "添加一条门诊记录失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "添加一条门诊记录成功", ret);
		} catch (Exception e) {
			logger.error("添加一条门诊记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "添加一条门诊记录异常");
		}
	}
	
	/**
	 * 查询用户门诊记录（诊疗历史）
	 * @createTime 2017-5-5,下午4:27:42
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param userId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listUserOutpatient")
	@ApiOperation(value = "查询用户门诊记录（诊疗历史）", httpMethod = "POST", response = ReturnMsg.class, notes = "查询用户门诊记录（诊疗历史）", position = 5)
	public ReturnMsg listUserOutpatient(@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "用户id") @RequestParam int userId) {
		try {
			List<VoHospitalOutpatient> data = hospitalOutpatientService.listUserOutpatient(hospitalId, userId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询用户门诊记录（诊疗历史）成功", data);
		} catch (Exception e) {
			logger.error("查询用户门诊记录（诊疗历史）异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "查询用户门诊记录（诊疗历史）异常");
		}
	}
	
	/**
	 * 更新门诊记录
	 * @createTime 2017-5-12,下午2:12:52
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @param isFinish
	 * @param userDefinedSport
	 * @param dietAdvice
	 * @param doctorAdvice
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/updateOutpatient")
	@ApiOperation(value = "更新门诊记录", httpMethod = "POST", response = ReturnMsg.class, notes = "更新门诊记录", position = 6)
	public ReturnMsg updateOutpatient(@ApiParam(value = "门诊id") @RequestParam int outpatientId, 
			@ApiParam(value = "是否完成诊断（0：否，1：是）") @RequestParam int isFinish,
			@ApiParam(value = "用户自定义运动") @RequestParam(required = false) String userDefinedSport,
			@ApiParam(value = "饮食建议") @RequestParam(required = false) String dietAdvice,
			@ApiParam(value = "医生建议") @RequestParam(required = false) String doctorAdvice,
			@ApiParam(value = "就诊原因id") @RequestParam(required = false) Integer outpatientReason) {
		try {
			HospitalOutpatient bean = new HospitalOutpatient();
			bean.setId(outpatientId);
			if (isFinish == 1) {
				bean.setFinishTime(new Date());
			}
			bean.setIsFinish(isFinish);
			bean.setUserDefinedSport(userDefinedSport);
			bean.setDietAdvice(dietAdvice);
			bean.setDoctorAdvice(doctorAdvice);
			bean.setOutpatientReason(outpatientReason);
			boolean ret = hospitalOutpatientService.updateOutp(bean);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "更新门诊记录失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "更新门诊记录成功");
		} catch (Exception e) {
			logger.error("更新门诊记录异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "更新门诊记录异常");
		}
	}
	
	/**
	 * 通过门诊id获取门诊信息
	 * @createTime 2017-5-15,上午9:29:29
	 * @createAuthor fangxilin
	 * @param outpatientId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findOutpById")
	@ApiOperation(value = "通过门诊id获取门诊信息", httpMethod = "POST", response = ReturnMsg.class, notes = "通过门诊id获取门诊信息", position = 7)
	public ReturnMsg findOutpById(@ApiParam(value = "门诊id") @RequestParam int outpatientId) {
		try {
			VoHospitalOutpatient data = hospitalOutpatientService.findOutpById(outpatientId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "通过门诊id获取门诊信息成功", data);
		} catch (Exception e) {
			logger.error("通过门诊id获取门诊信息异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "通过门诊id获取门诊信息异常");
		}
	}
	
	/**
	 * 给门诊用户发送短信通知
	 * @createTime 2017-7-12,下午3:53:10
	 * @createAuthor fangxilin
	 * @param mobileList
	 * @param hospitalId
	 * @param content
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sendMsgToOutp")
	@ApiOperation(value = "给门诊用户发送短信通知", httpMethod = "POST", response = ReturnMsg.class, notes = "给门诊用户发送短信通知", position = 8)
	public ReturnMsg sendMsgToOutp(@ApiParam(value = "手机号码列表集合") @RequestParam("mobileList[]") List<String> mobileList,
			@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "短信内容") @RequestParam String content) {
		try {
			if (ArrayUtils.isEmpty(mobileList) || StringUtils.isEmpty(content)) {
				return new ReturnMsg(ReturnMsg.FAIL, "短信内容或者不能号码为空");
			}
			boolean ret = userInfoService.sendMsgBatch(mobileList, hospitalId, content);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "给门诊用户发送短信通知失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "给门诊用户发送短信通知成功");
		} catch (Exception e) {
			logger.error("给门诊用户发送短信通知异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "给门诊用户发送短信通知异常");
		}
	}
	
}
