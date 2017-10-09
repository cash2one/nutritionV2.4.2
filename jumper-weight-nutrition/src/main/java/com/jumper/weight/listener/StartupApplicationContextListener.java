package com.jumper.weight.listener;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jumper.weight.common.util.Consts;

/**
 * 常量初始化赋值
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class StartupApplicationContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			Properties config = new Properties();
			
			/*ServletContext servletContext = servletContextEvent.getServletContext();
			config.load(servletContext.getResourceAsStream("/WEB-INF/classes/conf/config.properties"));*/
			
			//获取环境变量的路径
			String resource = System.getenv("WEBAPP_APP_NUTRITIONV2_CONF");
			resource = resource.replace("\\", "/");
			config.load(new FileInputStream(new File(resource + "/conf/config.properties")));
			Consts.BASE_FILE_URL = config.getProperty("BASE_FILE_URL");
			Consts.BASE_PATH = config.getProperty("BASE_PATH");
			Consts.WEIGHT_FOOD_IMG_URL = config.getProperty("WEIGHT_FOOD_IMG_URL");
			Consts.USER_PORTAL_URL = config.getProperty("USER_PORTAL_URL");
			Consts.BASE_SERVICE_PATH = config.getProperty("BASE_SERVICE_PATH");
			Consts.NUTRITIONV2_SMS_APPID = config.getProperty("NUTRITIONV2_SMS_APPID");
			Consts.BAFY_INTERFACE_USER = config.getProperty("BAFY_INTERFACE_USER");
			Consts.BAFY_INTERFACE_PWD = config.getProperty("BAFY_INTERFACE_PWD");
			Consts.BUS_CODE = config.getProperty("BUS_CODE");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
