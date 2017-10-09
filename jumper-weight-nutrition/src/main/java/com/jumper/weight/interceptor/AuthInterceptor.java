package com.jumper.weight.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jumper.weight.common.util.Const;

/**
 * 身份验证拦截器
 * @Description TODO
 * @author fangxilin
 * @date 2017-8-2
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class AuthInterceptor implements HandlerInterceptor {
	
	private final static Logger logger = Logger.getLogger(AuthInterceptor.class);
	
	/** 需要设置session的方法 */
	private final String[]  ACCESSPATHS = {
		"/mobile/login.html", "/mobile/checkPage.html",
		"/web/outpatient.html", "/swagger/index.html",
		"/barcode/wxaliWeightNutritionV2", "/barcode/appWeightNutritionV2",
		"/web/angelsounds.html","/mobile/viewRecipes.html",
		"/mobile/homePage.html","/outpatient/listOutpatientUser",
		"/userManage/checkNutritionConsult","/userManage/getNoRead"
	};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		HttpSession session = request.getSession();
		if (ArrayUtils.contains(ACCESSPATHS, path)) {
			session.setMaxInactiveInterval(Const.SESSIONMAXACTIVETIME); //设置 session 24小时失效
			String access = UUID.randomUUID().toString().replace("-", "");
			session.setAttribute("ACCESS_TOKEN", access);
			return true;
		}
		Object accessSess = session.getAttribute("ACCESS_TOKEN");
		if (accessSess == null || "".equals(accessSess)) {
			request.getRequestDispatcher("/error/illegality").forward(request, response);
			return false;
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
