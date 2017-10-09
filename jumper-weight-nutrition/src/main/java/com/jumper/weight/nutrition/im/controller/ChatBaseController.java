package com.jumper.weight.nutrition.im.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.HttpClient;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.hospital.entity.HospitalInfo;
import com.jumper.weight.nutrition.hospital.service.HospitalInfoService;
import com.jumper.weight.nutrition.im.vo.InitChatDataVo;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;


/**
 * 内部系统嵌套聊天页面Controller
 * @author gyx
 * @time 2017年9月22日
 */
@Controller
public class ChatBaseController {
	protected Logger logger = Logger.getLogger(ChatBaseController.class);
	
	@Autowired
	private HospitalInfoService hospitalInfoService;
	
	/**
	 * 聊天页面初始化
	 * @author huangzg 2017年1月4日 下午6:01:31  
	 * @param vo 参数集
	 * @return        
	 * @throws
	 */
	@RequestMapping(value="/chat/init",method=RequestMethod.GET)
	public ModelAndView init(InitChatDataVo vo){
		ModelAndView mav = new ModelAndView();
		try {
			String hospitalId = vo.getFromUserId();
			HospitalInfo hospitalInfo = hospitalInfoService.findById(Integer.parseInt(hospitalId));
			if(hospitalInfo != null){
				vo.setFromNickName(hospitalInfo.getName());
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("param", JSON.toJSONString(vo));
			HttpClient client = new HttpClient(Consts.BASE_SERVICE_PATH+"/chat/init_data", param);
			String result = client.post();
			String data = "";
			if(result != null){
				Map<String, Object> map = JSON.parseObject(result, Map.class);
				data = (String) map.get("data");
			}
			mav.addObject("data", data);
			if (vo.getBusCode() == 0){
				mav.setViewName("404");
			} else if (StringUtils.isEmpty(vo.getFromUserId())){
				mav.setViewName("404");
			} else if (vo.getFromUserType() == 0){
				mav.setViewName("404");
			} else {
				mav.setViewName("chat");
			}
		} catch (Exception e) {
			logger.info("聊天页面初始化 msg "+e.getMessage(), e);
		}
		mav.addObject("url", Consts.BASE_SERVICE_PATH);
		return mav;
	}
	
	/**
	 * WEB查看聊天记录 
	 * @param consultantId 问题/服务ID
	 * @param startTime 查询开始时间
	 * @param endTime 查询结束时间
	 * @param pageNo 页码
	 * @param pageSize 每页条数
	 * @return        
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/im/sel_message")
	@ResponseBody
	@ApiOperation(value = "查看聊天记录", httpMethod = "POST", response = ReturnMsg.class, notes = "查看聊天记录", position = 5)
	public ReturnMsg selMessage(
			@ApiParam(required = true, name = "busCode", value = "业务代码") @RequestParam String busCode,
			@ApiParam(required = true, name = "sender", value = "发送者IM帐号") @RequestParam String sender,
			@ApiParam(required = true, name = "recevrer", value = "接收者IM帐号") @RequestParam String recevrer,
			@ApiParam(required = false, name = "consultantId", value = "问题ID/服务ID") @RequestParam(required = false) String consultantId,
			
			@ApiParam(required = false, name = "startTime", value = "开始时间") @RequestParam(required = false) String startTime,
			@ApiParam(required = false, name = "endTime", value = "结束时间") @RequestParam(required = false) String endTime,
			@ApiParam(required = false, name = "pageNo", value = "页码") @RequestParam(required = false) Integer pageNo,
			@ApiParam(required = false, name = "pageSize", value = "每页条数") @RequestParam(required = false) Integer pageSize,
			@ApiParam(required = false, name = "msgId", value = "最后一条消息ID") @RequestParam(required = false) Long msgId) {
		
		if (StringUtils.isEmpty(busCode)){
			return new ReturnMsg(ReturnMsg.FAIL, "业务业务不许为空!", new ArrayList<Object>());
		}
		if (StringUtils.isEmpty(sender)){
			return new ReturnMsg(ReturnMsg.FAIL, "发送者ID不许为空!", new ArrayList<Object>());
		}
		if (StringUtils.isEmpty(recevrer)){
			return new ReturnMsg(ReturnMsg.FAIL, "接收者ID不许为空!", new ArrayList<Object>());
		}
		
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("consultantId", consultantId);
			param.put("busCode", busCode);
			param.put("sender", sender);
			param.put("recevrer", recevrer);
			param.put("startTime", startTime);
			param.put("endTime", endTime);
			param.put("pageNo", pageNo);
			param.put("pageSize", pageSize);
			param.put("msgId", msgId);
			HttpClient client = new HttpClient(Consts.BASE_SERVICE_PATH+"/im/sel_message", param);
			String result = client.post();
			if(result != null){
				ReturnMsg ret = JSON.parseObject(result, ReturnMsg.class);
				return ret;
			}
//			return imHistoryMsgService.selMsg(consultantId, busCode, sender, recevrer,startTime, endTime, pageNo, pageSize, msgId);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("WEB查看聊天记录/im/sel_message 发生异常" + e);
		}
		return new ReturnMsg(ReturnMsg.FAIL, "查询失败！", new ArrayList<Object>());
	}
	
	/**
	 * app 发送消息统一接口
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/im/send_msg")
	@ResponseBody
	@ApiOperation(value = "发送消息统一接口", httpMethod = "POST", response = ReturnMsg.class, notes = "发送消息统一接口", position = 5)
	public ReturnMsg sendMsg(
			@ApiParam(required = true, name = "sender", value = "发送者IM帐号") @RequestParam String sender,
			@ApiParam(required = true, name = "receiver", value = "接收者IM帐号") @RequestParam String receiver,
			@ApiParam(required = true, name = "content", value = "消息内容") @RequestParam String content,
			@ApiParam(required = true, name = "type", value = "消息类型") @RequestParam Integer type,
			@ApiParam(required = true, name = "busCode", value = "业务代码") @RequestParam String busCode,
			@ApiParam(required = false, name = "groupId", value = "群ID") @RequestParam(required = false) String groupId,
			@ApiParam(required = false, name = "consultantId", value = "服务ID/问题ID") @RequestParam(required = false) String consultantId){
		if (StringUtils.isEmpty(sender)){
			return new ReturnMsg(ReturnMsg.FAIL, "发送者IM帐号不许为空！", new HashMap<String,Object>());
		}
		/*if (StringUtils.isEmpty(receiver)){
			return new ReturnMsg(ReturnMsg.FAIL, "接收者IM帐号不许为空！", new HashMap<String,Object>());
		}*/
		if (StringUtils.isEmpty(content)){
			return new ReturnMsg(ReturnMsg.FAIL, "消息内容不许为空！", new HashMap<String,Object>());
		}
		if (StringUtils.isEmpty(busCode)){
			return new ReturnMsg(ReturnMsg.FAIL, "业务代码不许为空！", new HashMap<String,Object>());
		}
		if (null == type || type == 0){
			return new ReturnMsg(ReturnMsg.FAIL, "消息类型不许为空！", new HashMap<String,Object>());
		}
		if (StringUtils.isEmpty(groupId) && StringUtils.isEmpty(consultantId) && StringUtils.isEmpty(receiver)){
			return new ReturnMsg(ReturnMsg.FAIL, "群ID，服务ID/问题ID不许同时为空！", new HashMap<String,Object>());
		}
		try{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sender", sender);
			param.put("receiver", receiver);
			param.put("content", content);
			param.put("type", type);
			param.put("busCode", busCode);
			param.put("consultantId", consultantId);
			param.put("groupId", groupId);
			HttpClient client = new HttpClient(Consts.BASE_SERVICE_PATH+"/im/send_msg", param);
			String result = client.post();
			if(result != null){
				ReturnMsg ret = JSON.parseObject(result, ReturnMsg.class);
				return ret;
			}
//			return imSendMsgService.sendMsg(sender, receiver, content, type, busCode, consultantId, groupId);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("发送消息接口sendTextMsg发生异常");
		}
		
		return new ReturnMsg(ReturnMsg.FAIL, "发送失败！", new HashMap<String,Object>());
	}
	
	/*@RequestMapping(method = RequestMethod.POST, value = "/file/upload_file")
	@ResponseBody
	@ApiOperation(value = "单上传文件", httpMethod = "POST", response = ReturnMsg.class, notes = "单上传文件", position = 5)
	public ReturnMsg uploadFile(@RequestParam(value = "file", required = false) MultipartFile file){
		try{
			Map<String, MultipartFile> param = new HashMap<String, MultipartFile>();
			param.put("file", file);
			HttpClient client = new HttpClient(Consts.BASE_SERVICE_PATH+"/file/upload_file", param);
			String result = client.post();
			if(result != null){
				ReturnMsg ret = JSON.parseObject(result, ReturnMsg.class);
				return ret;
			}
			HashMap<String, MultipartFile> files = new HashMap<String, MultipartFile>();
			files.put("file", file);
			HashMap<String, String> params = new HashMap<String, String>();
			String response = NetUtil.sendMultyPartRequest(Consts.BASE_SERVICE_PATH + "/file/upload_file", params, files);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("上传文件异常" + e);
		}
		return new ReturnMsg(ReturnMsg.FAIL, "上传文件失败", new ArrayList<Object>());
	}*/
}
