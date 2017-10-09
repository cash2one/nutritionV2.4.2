package com.jumper.weight.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 时间工具类
 * @Description TODO
 * @author fangxilin
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class TimeUtils {
	
	/**
	 * 快捷string转Date
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param data
	 * @return
	 */
	public static Date convertToDate(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(Const.YYYYMMDD);// 可以方便地修改日期格式
		Date ret;
		try {
			ret = dateFormat.parse(date);
			return ret;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * string格式化Date
	 * @param data
	 * @param format
	 * @return
	 */
	public static Date stringFormatToDate(String date, String format) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date langDate;
		try {
			langDate = sdf.parse(date);
			return langDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String格式化String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String stringFormatString(String date, String format) {
		String time = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			time = sdf.format(sdf.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * Date格式化String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateFormatToString(Date date, String format) {
		if(date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String转为Timestamp
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static Timestamp stringToTimestamp(String date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		format.setLenient(false);
		try {
			Timestamp ts = new Timestamp(format.parse(date).getTime());
			return ts;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取当前时间往后多少天
	 * @param day
	 * @param date
	 * @return
	 */
	public static Date getDateByDaysLate(int day, Date date) {
		Calendar todayStart = Calendar.getInstance();
		if (date != null) {
			todayStart.setTime(date);
		}
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		todayStart.add(Calendar.DATE, day);
		return todayStart.getTime();
	}
	
	/**************************************************************************/
	
	
	/**
	 * 日期1减日期2相差多少年(计算年龄)
	 * @createTime 2017-4-26,下午3:11:45
	 * @createAuthor fangxilin
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static Integer getTowDateMinusYear(Date dt1, Date dt2) {
		Integer year = null;
		if (dt1 == null || dt2 == null) {
			return year;
		}
		/*long minus = dt1.getTime() - dt2.getTime();
		long miles = 365L * 24 * 60 * 60 * 1000;
		year = (int) (minus / miles);*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt1);
		int year1 = cal.get(Calendar.YEAR);
		cal.setTime(dt2);
		int year2 = cal.get(Calendar.YEAR);
		return year1 - year2;
	}
	
	/**
	 * 得到某年某月的第一天
	 * @createTime 2017-5-4,下午7:00:45
	 * @createAuthor fangxilin
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 00);
		return cal.getTime();
	}

	/**
	 * 得到某年某月的最后一天
	 * @createTime 2017-5-4,下午7:00:59
	 * @createAuthor fangxilin
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);

		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}
	
	/**
	 * 日期1减日期2相差多少天
	 * @createTime 2017-5-6,下午10:09:25
	 * @createAuthor fangxilin
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static int getTowDateMinusDay(Date dt1, Date dt2) {
		if (dt1 == null || dt2 == null) {
			return -1;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt1);
		int day1 = cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(dt2);
		int day2 = cal.get(Calendar.DAY_OF_YEAR);
		return day1 - day2;
	}
	
	/**
	 * 判断两个日期是否是同一天
	 * @createTime 2017-5-6,下午10:09:25
	 * @createAuthor fangxilin
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static boolean isSameDay(Date dt1, Date dt2) {
		if (dt1 == null && dt2 == null) {
			return true;
		} else if (dt1 == null || dt2 == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt1);
		int year1 = cal.get(Calendar.YEAR);
		int day1 = cal.get(Calendar.DAY_OF_YEAR);
		cal.setTime(dt2);
		int year2 = cal.get(Calendar.YEAR);
		int day2 = cal.get(Calendar.DAY_OF_YEAR);
		return (year1 == year2 && day1 == day2);
	}
	
	/**
	 * 获得起始时间和结束时间内的所有时间集合
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);
        // 测试结束日期是否在该日期之前  
        while (endDate.after(cal.getTime())) {
        	lDate.add(cal.getTime());
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        int ret = beginDate.compareTo(endDate);
        if(ret < 1){
        	lDate.add(endDate);// 把结束时间加入集合  
        }
        return lDate;  
    }	
}