package com.jumper.weight.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公共拦截器，主要拦截参数
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class CommonInterceptor implements HandlerInterceptor {
	
	private final static Logger logger = Logger.getLogger(CommonInterceptor.class);
	/** 不需打印日志的方法 */
	private final String[]  NOLOGPATHS = {"/outpatient/listOutpatientUser"};
	
	/**
	 * 请求到控制器之前执行该方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//请求参数
		String requestParam = "";
		//以拼接形式获取地址参数
		Enumeration<String> keys = request.getParameterNames(); 
		while(keys.hasMoreElements()) { 
		    String k = keys.nextElement(); 
		    requestParam += k + " = " + request.getParameter(k)+",";
		}
		String path = request.getServletPath();
		requestParam = (StringUtils.isNotEmpty(requestParam)) ? requestParam.substring(0, requestParam.lastIndexOf(",")) : "";
		if (!ArrayUtils.contains(NOLOGPATHS, path)) {
			logger.info("请求方式：" + request.getMethod() + ",访问路径：" + path + ",输出参数==>" + requestParam);
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
