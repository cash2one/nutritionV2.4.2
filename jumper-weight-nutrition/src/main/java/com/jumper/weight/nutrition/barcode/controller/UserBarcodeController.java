package com.jumper.weight.nutrition.barcode.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jumper.dubbo.bean.bo.UserBasicInfo;
import com.jumper.dubbo.bean.po.UserVerifiedCode;
import com.jumper.dubbo.service.UserService;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.DesEncryptUtils;
import com.jumper.weight.common.util.HttpClient;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.SignatureUtils;
import com.jumper.weight.common.util.Util;
import com.jumper.weight.nutrition.hospital.entity.HospitalInfo;
import com.jumper.weight.nutrition.hospital.service.HospitalInfoService;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.service.HospitalUserInfoService;

/**
 * 用户扫码入口
 * @author gyx
 * @time 2017年7月28日
 */
@Controller
@RequestMapping("barcode")
public class UserBarcodeController {
	private final static Logger logger = Logger.getLogger(UserBarcodeController.class);
	@Autowired
	private HospitalInfoService hospitalInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private HospitalUserInfoService hospitalUserInfoService; 
	
	
	/**
	 * 获取登录验证码
	 * @param hospitalId 医院ID
	 * @param mobile 手机号
	 * @return
	 */
	@RequestMapping("getSmsCode")
	@ResponseBody
	public ReturnMsg getSmsCode(@RequestParam("hospitalId") Integer hospitalId, @RequestParam("mobile") String mobile){
		try {
			if(StringUtils.isEmpty(mobile)) {
				return new ReturnMsg(ReturnMsg.FAIL, "手机号码不能为空！");
			}
			if(!Util.isMobiPhoneNum(mobile)) {
				return new ReturnMsg(ReturnMsg.FAIL, "您输入的手机号码格式不正确，请重新输入！");
			}
			//生成6位数的随机验证码
			String code = RandomStringUtils.random(6, false, true);
			String content = code+"是你申请注册天使医生(用户端)的验证码。(15分钟内有效，如非本人操作请忽略)";
			HospitalInfo hospitalInfo = hospitalInfoService.findById(hospitalId);
			//生成签名
			Map<String, String> params = SignatureUtils.getHttpSignatureParams(hospitalId, hospitalInfo.getName(), mobile, content);
			String sign = SignatureUtils.getSignature(params);
			//调用http请求
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hospId", hospitalId.longValue());
			map.put("hospName", hospitalInfo.getName());
			map.put("content", content);
			map.put("mobile", mobile);
			map.put("req_sign", sign);
			map.put("appid", Consts.NUTRITIONV2_SMS_APPID);
			HttpClient client = new HttpClient(Consts.BASE_SERVICE_PATH+"/sms/send_smsbyhb", map, HttpClient.TYPE_JSON);
//			client.addHeader("Content-Type", "application/json;charset=UTF-8");
			String result = client.post();
			if(result != null){
				ReturnMsg returnMsg = JSON.parseObject(result, ReturnMsg.class);
				if(returnMsg.getMsg()==1){
					//保存验证码到数据库
					UserVerifiedCode uv = new UserVerifiedCode();
					uv.setMobile(mobile);
					uv.setCode(code);
					//新增验证码
					userService.saveUserVerifiedCode(uv);
					return new ReturnMsg(ReturnMsg.SUCCESS, "验证码发送成功！");
				}
			}
			return new ReturnMsg(ReturnMsg.FAIL, "验证码发送失败！");
		} catch (IOException e) {
			logger.error("getSmsCode()", e);
			return new ReturnMsg(ReturnMsg.FAIL, "验证码发送失败！");
		}
	}
	
	/**
	 * 验证登录
	 */
	@RequestMapping("verifedLogin")
	@ResponseBody
	public ReturnMsg verifedLogin(@RequestParam("code") String code, @RequestParam("mobile") String mobile){
		try {
			if(StringUtils.isEmpty(mobile)) {
				return new ReturnMsg(ReturnMsg.FAIL, "手机号码不能为空！");
			}
			if(StringUtils.isEmpty(code)) {
				return new ReturnMsg(ReturnMsg.FAIL, "请先输入验证码！");
			}
			//通过手机号码查询验证码
			UserVerifiedCode userVerifiedCode = userService.findVerifiedCodeByMobileSQL(mobile);
			if(userVerifiedCode == null) {
				return new ReturnMsg(ReturnMsg.FAIL, "验证码已过期，请重新获取！");
			}
			if(!userVerifiedCode.getCode().equals(code)) {
				return new ReturnMsg(ReturnMsg.FAIL, "验证码错误，请重新输入！");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "成功", mobile);
		} catch (Exception e) {
			logger.error("verifedLogin()", e);
			return new ReturnMsg(ReturnMsg.FAIL, "失败");
		}
	}
	
	/**
	 * 微信公众号、支付宝生活号体重营养菜单请求入口
	 */
	@RequestMapping("wxaliWeightNutritionV2")
	public void wxaliWeightNutritionV2(HttpServletRequest request, HttpServletResponse response){
		try {
			//医院Id
			String hospitalId = request.getParameter("hospitalId");
			//渠道
			String channel = request.getParameter("channel");
			//openId
			String openId = request.getParameter("openid");
			Integer hospitalid = Integer.parseInt(DesEncryptUtils.decrypt(hospitalId));
			//查询用户业务表记录，有的话直接跳转checkPage页面，没有记录跳转到验证手机号码页面（注意保存openID）
			HospitalUserInfo hospitalUserInfo = hospitalUserInfoService.findHospitalUserInfoByOpenId(hospitalid, channel, openId);
			String redirectUrl = request.getContextPath();
			if(hospitalUserInfo != null){
				String mobile = hospitalUserInfo.getMobile();
				Integer userId = hospitalUserInfo.getUserId();
				redirectUrl += "/mobile/checkPage.html?hospitalId="+hospitalid+"&userId="+userId+"&mobile="+mobile;
			}else{
				redirectUrl += "/mobile/login.html?hospitalId="+hospitalid+"&channel="+channel+"&openId="+openId;
			}
			logger.info("redirectUrl:"+redirectUrl);
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * app体重营养菜单请求入口
	 */
	@RequestMapping("appWeightNutritionV2")
	public String appWeightNutritionV2(@RequestParam("hospitalid") int hospitalid, @RequestParam("userid") String userid){
		Integer userId = null;
		if(hospitalid == 42){
			userId = Integer.parseInt(DesEncryptUtils.decrypt(String.valueOf(userid)));
		}else{
			userId = Integer.parseInt(userid);
		}
		//查询用户业务表记录，有的话直接跳转checkPage页面，没有记录跳转到验证手机号码页面
		HospitalUserInfo hospitalUserInfo = hospitalUserInfoService.findHospitalUserInfo(hospitalid, userId);
		String redirectUrl = "";
		String mobile = "";
		if(hospitalUserInfo == null){
			//调his或者天使的接口查询用户手机号
			UserBasicInfo basicInfo = userService.getBasicInfoByUserId(userId);
			if(basicInfo != null){
				mobile = basicInfo.getMobile();
			}
		}else{
			mobile = hospitalUserInfo.getMobile();
		}
		redirectUrl += "/mobile/checkPage.html?hospitalId="+hospitalid+"&userId="+userId+"&mobile="+mobile;
		logger.info("redirectUrl:"+redirectUrl);
		return "redirect:"+redirectUrl;
	}
	
}
