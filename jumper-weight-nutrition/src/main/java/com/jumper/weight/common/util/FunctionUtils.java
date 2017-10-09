package com.jumper.weight.common.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 计算公式，公共方法工具类
 * ClassName: FunctionUtils 
 * @Description: TODO
 * @author fangxilin
 * @date 2016-5-14
 */

public class FunctionUtils {
    
	/**计算bmi*/
    public static Double getBMI(Integer height, Double weight) { //静态函数
    	Double bmi = 0D;
    	if (height == null || weight == null || height == 0) {
			return bmi;
		}
		double hei = height.doubleValue();
		bmi = weight / ((hei / 100) * (hei / 100));
		bmi = setDecimal(bmi, 1);
		return bmi;
    }
    
    /**
	 * 计算孕周
	 * @param test_date 当前时间  
	 * @param expected_date 预产期
	 * @return pregnant_week[0] 孕周   pregnant_week[1] 零几天  pregnant_week[2] 总天数
	 */
	public static int[] calPregnantWeek(Date test_date, Date expected_date){
		int[] pregnant_week = new int[3];
    	if(test_date == null || expected_date == null){
    		return pregnant_week;
    	}
    	long pregnant = expected_date.getTime()/1000 - 3600*24*280;
    	Long interval_t = (test_date.getTime()/1000 - pregnant)/(3600*24);
    	int interval = interval_t.intValue();
    	if(interval >= 301){
    		pregnant_week[0] = 43;
    		pregnant_week[1] = 0;
    		pregnant_week[2] = 301;
    	}else if(interval<0){
			pregnant_week[0] = 0;
			pregnant_week[1] = 0;
			pregnant_week[2] = 0;
		}else{
    		pregnant_week[0] = interval/7;
	    	pregnant_week[1] = interval%7;
	    	pregnant_week[2] = interval;
    	}
    	return pregnant_week;
    }
	
	/**
	 * 根据怀孕天数计算预产期
	 * @createTime 2017-6-30,下午2:45:53
	 * @createAuthor fangxilin
	 * @param nowDate 当前时间
	 * @param pday 怀孕天数
	 * @return
	 */
	public static Date getExpDateByPday(Date nowDate, int pday) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(nowDate);
		todayStart.add(Calendar.DATE, 280 - pday);
		return todayStart.getTime();
	}
	
	/**
	 * 根据末次月经时间计算预产期
	 * @param lastPeriod 末次月经时间
	 * @return 
	 */
	public static Date getPregancyDay(String lastPeriod){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = sdf.parse(lastPeriod);
			Calendar Calendarborn = Calendar.getInstance();
			Calendarborn.setTime(d1);
			Calendarborn.add(Calendar.DAY_OF_MONTH, 279);
			return Calendarborn.getTime();
			/*int bornyear = Calendarborn.get(Calendar.YEAR);
			int bornmonth = 1 + Calendarborn.get(Calendar.MONTH);
			int bornday = Calendarborn.get(Calendar.DAY_OF_MONTH);
			String borndayString = (String.valueOf(bornyear) + "-" + String.valueOf(bornmonth) + "-" + String.valueOf(bornday));
			return TimeUtils.convertDate(borndayString, "yyyy-MM-dd");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据预产期计算末次月经
	 * @version 1.0
	 * @createAuthor fangxilin
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param expectedDate 预产期
	 * @return
	 */
	public static Date getLastPeriodByExp(Date expectedDate){
		if(expectedDate == null){
			return null;
		}
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(expectedDate);
		todayStart.add(Calendar.DATE, -279);
		return todayStart.getTime();
	}
	
	/**
	 * 自定义的除法运算
	 * @param 
	 * @return 
	 */
	public static double division(Double num1, Double num2){
		if(num2 == 0 || num2 == null){
			return 0;
		}
		return num1/num2;
	}
	
	/**
	 * 保留n位小数位
	 * @param num 要保留的小数位的数
	 * @param n 要保留n位有效数字
	 * @return 
	 */
	public static double setDecimal(Double num, int n){
		BigDecimal b = new BigDecimal(num.floatValue());
		double result = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();	
		return result;
	}
	
    public static float round(double num,int status) {
    	java.text.DecimalFormat df=new java.text.DecimalFormat("#.#");
    	if(status ==2){
    		df=new java.text.DecimalFormat("#.##"); 
    	}
    	float sourceF = new Float(df.format(num));
    	return sourceF;
    }
    
    /**
	 * 获得推荐摄入的卡洛里
	 * @param height 身高
	 * @param bmi 孕前bmi
	 * @param type 计算公式 0：公式一   1：公式二（默认）
	 * @return 
	 */
    public static int getSuggestIntake(Double height, Double bmi, int type) {
    	Double suggestIntake = 1700D;
		if (type == 0) {
			Double amount = (bmi <= 18.5) ? 200 : ((18.5 < bmi && bmi <= 24.9) ? 100D : 0D);
			suggestIntake = (height - 105.00) * 30 + amount;
		} else if (type == 1) {
			if (bmi <= 18.5) {
				suggestIntake = (height - 105.00) * 35;
			} else if (bmi > 18.5 && bmi <= 24.9) {
				suggestIntake = (height - 105.00) * 30;
			} else { // 其实就是大于25
				suggestIntake = (height - 105.00) * 25;
			}
		}
		return (int) setDecimal(suggestIntake, 0);
    }
    
    /**
	 * 将手机号转换成123*****456的形式
	 * @param mobile 手机号码
	 */
    public static String getPrivateMobile(String mobile){
    	if(StringUtils.isEmpty(mobile)){
    		return null;
    	}
    	mobile = mobile.substring(0, 3)+"*****"+mobile.substring(8, 11);
    	return mobile;
    }
    
    /**
     * 根据怀孕天数计算孕期阶段
     * @createTime 2017-5-3,下午7:40:45
     * @createAuthor fangxilin
     * @param day 怀孕天数
     * @param currIden 怀孕状态 0：怀孕中 1：已有宝宝
     * @return stage 孕期阶段
     */
	public static String calPregnantStage(int day, int currIden) {
		if (currIden == 1) {
			return "已有宝宝";
		}
		String stage = "孕早期";
		if (day >= 0 && day <= 90) { // 0到12周6天为孕早期
			stage = "孕早期";
		} else if (day >= 91 && day <= 195) { // 13周0天到27周6天为孕中期
			stage = "孕中期";
		} else if (day >= 196) {
			stage = "孕晚期";
		}
		return stage;
	}
	
	/**
	 * 根据bmi判断体重状态
	 * @createTime 2017-5-8,下午6:48:52
	 * @createAuthor fangxilin
	 * @param bim
	 * @return 体重状态 0：偏瘦，1：标准，2：偏胖，3：肥胖
	 */
	public static int getStatusByBmi(Double bmi) {
		int status = 3;
		if (bmi < 18.5) {
			status = 0;
		} else if (bmi < 25) {
			status = 1;
		} else if (bmi < 30) {
			status = 2;
		}
		return status;
	}
	
	/**
	 * 根据安全体重范围判断体重状态
	 * @createTime 2017-6-29,上午10:32:29
	 * @createAuthor fangxilin
	 * @param safeWeight
	 * @param weight
	 * @return 体重状态 0：偏低，1：正常，2：超重
	 */
	public static int getStatusByWeight(double[] safeWeight, Double weight) {
		if (weight == null) {
			return 0;
		}
		int status = 1;
		if (weight < safeWeight[0]) {
			status = 0;
		} else if (weight > safeWeight[1]) {
			status = 2;
		}
		return status;
	}
	
	/**
	 * 获取血糖状态
	 * @createTime 2017年9月14日,下午3:14:36
	 * @createAuthor fangxilin
	 * @param sugar
	 * @return
	 */
	public static int getSugarStatus(Double sugar) {
		int status = 1;
		if (sugar < 4) {
			status = 0;
		} else if (sugar > 6) {
			status = 2;
		}
		return status;
	}
	
	/**
	 * 计算运动消耗的卡洛里量
	 * @createTime 2017-6-22,下午4:08:49
	 * @createAuthor fangxilin
	 * @param currWeight 用户当前体重
	 * @param met 运动代谢当量
	 * @param timeLength 运动时长
	 * @return
	 */
	public static double getSpoConsumeCal(Double currWeight, Double met, Double timeLength) {
		double calorie = setDecimal(currWeight * met * timeLength / 60 * 1.05, 1);
		return calorie;
	}
}
