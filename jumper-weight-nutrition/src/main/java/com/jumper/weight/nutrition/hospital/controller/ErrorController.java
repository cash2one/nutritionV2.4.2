package com.jumper.weight.nutrition.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.ReturnMsg;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
	
	/**
	 * 非法访问提示
	 * @createTime 2017-8-2,上午9:33:07
	 * @createAuthor fangxilin
	 * @return
	 */
	@RequestMapping("/illegality")
	@ResponseBody
	public ReturnMsg illegality() {
		return new ReturnMsg(ReturnMsg.FAIL, "非法访问");
	}
}
