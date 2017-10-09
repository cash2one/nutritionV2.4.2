package com.junit;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;
/**
 * spring加载log4j默认从src找log4j.properties
 * 所以设置Junit启动时，Spring加载正确的log4j地址
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-14
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Junit4ClassRunner extends SpringJUnit4ClassRunner {
	
	static{
		try {
			Log4jConfigurer.initLogging("classpath:conf/log4j.properties");
		} catch (FileNotFoundException e) {
			System.err.println("Cannot Initialize log4j");
			e.printStackTrace();
		}
	}

	public Junit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}
	
}
