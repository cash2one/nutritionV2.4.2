package com.jumper.weight.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * @Description TODO
 * @author qinxiaowei
 * @date 2017-1-12
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Util {
	
	/**
	 * 验证手机号码是否合法
	 * 合法格式：13、14、15、17、18开头
	 * @version 1.0
	 * @createTime 2017-1-12,上午10:20:18
	 * @updateTime 2017-1-12,上午10:20:18
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param telNum
	 * @return
	 */
	public static boolean isMobiPhoneNum(String telNum) {
		String regex = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(telNum);  
        return m.matches();  
	} 
	
	/**
	 * 计算年龄
	 * @version v.1.0
	 * @createTime 2017年5月25日,下午6:07:24
	 * @updateTime 2017年5月25日,下午6:07:24
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param brithday
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static int getCurrentAgeByBirthdate(String brithday) throws ParseException, Exception {
		  try {
			   Calendar calendar = Calendar.getInstance();
			   SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			   String currentTime = formatDate.format(calendar.getTime());
			   Date today = formatDate.parse(currentTime);
			   Date brithDay = formatDate.parse(brithday);
			   return today.getYear() - brithDay.getYear();
		  } catch (Exception e) {
			  return 0;
		  }
	}
}
