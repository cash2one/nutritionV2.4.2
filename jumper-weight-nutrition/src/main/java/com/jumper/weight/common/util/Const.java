package com.jumper.weight.common.util;

/**
 * final常量
 * @Description TODO
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Const {
	/** 版本号 v+日期+版本 **/
	public final static String VERSION = "V1704013.1.0.0";
	
	/** yyyy-MM-dd时间格式 **/
	public static final String YYYYMMDD = "yyyy-MM-dd";
	
	/** yyyyMMdd时间格式 **/
	public static final String YYYYMMDD1 = "yyyyMMdd";
	
	/** yyyy-MM-dd HH:mm:ss时间格式 **/
	public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	/** 新建档时发送给用户短信 **/
	public static final String NEW_BOOKBUILD = "【天使医生】您已注册天使医生用户，账号：%s，初始密码：%s，点击http://dwz.cn/3xHpI1或在应用商店搜索“天使医生”即可下载天使医生APP";
	
	/** 短信接口访问授权码 */
	public static final String SMS_SESSION_KEY = "ByqGSM49AgEGBSuBht*FrUYPl6GMO4ptxJTgCAv48kwHQAwF0CocT*Z4txgK7JcavBfxkQpnKz85hS+Ci4dmhRANCAARL6YljBDHyDVhkx0CAB2YKAAATdjtx";
	
	/** 短信接口签名密钥 */
	public static final String SMS_SESSION_SECRET = "503302f84d85a7fc2e263e35";
	
	/** 正则：只能输入英文数字 */
	public static final String NUMEN = "[A-Za-z0-9_]*";
	
	/** sessin的有效期设为24小时 */
	public static final Integer SESSIONMAXACTIVETIME = 24 * 60 * 60;
	
	/** 天使医院 */
	public static final Integer ANGELSOUND_HOSPITAL_ID = 49;
	
	/** 食材种类 */
	public static String FOOD_CATEGORY[] = {"谷薯类","豆制品类","蔬菜类","水果类","肉蛋类","乳制品类","油脂类","坚果类"}; //食品种类名必须与数据库数据一致
	
	/** 餐次类型 */
	public static String MEALS_NAME[] = {"早餐","早点","午餐","午点","晚餐","晚点"};
	
}
