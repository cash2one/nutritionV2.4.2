/** 全局变量-----------------start */
var basePath = window.location.href;
basePath = basePath.substring(0, basePath.indexOf("nutritionV2")) + "nutritionV2";
//var hospitalId = $.session.get('hospitalId');
//var userId = $.session.get('userId');
var hospitalId = $.cookie('hospitalId');
var userId = $.cookie('userId');
//var hospitalId = 42;

/** 全局变量------------------end */

/** 常量-----------------start */
var CONST = {
	pregnantType : ["单胎", "多胎"],//0：单胎，1：多胎
	pageSize: 2,
	tabPageSize : 21,//表格式分页的大小
	outpType : ["初诊", "复诊"],//0：初诊，1：复诊
	weightStatus : ["偏慢", "正常", "偏快"],
	menuType : ["outpatient","userManage"],
	backOutpatient : [0,1],//0：不显示返回门诊按钮，1：显示返回门诊按钮（报告页）
	evaluation:["↓不足","√达标","↑偏高"],
	templateType:[1,2,3,4],//1:通知模板，2：就诊原因，3：初诊扫码文案，4：复诊扫码文案
	busCode:70101
}
/** 常量------------------end */

/** 计算公式-----------------start */
var TOOL = {
	/**
	 * 计算孕周
	 * @param testDate 当前日期 格式 2017-02-17
	 * @param expectedDate 预产期 格式 2017-02-17
	 */
    getPregnantWeek : function(testDate, expectedDate) {
    	var days = 280 - getTowDateMinusDay(expectedDate,testDate);
    	var pweek = new Array(3);
    	if(days >= 301){
    		pweek[0] = 43;
    		pweek[1] = 0;
    		pweek[2] = 301;
    	}else if(days < 0){
    		pweek[0] = 0;
    		pweek[1] = 0;
    		pweek[2] = 0;
    	}else{
    		pweek[0] = parseInt(days/7);
    		pweek[1] = days%7;
    		pweek[2] = days;
    	}
    	return pweek;
    },
    /**
	 * 根据末次月经计算预产期
	 * @param lastPeriod 末次月经 格式 2017-02-17
	 */
    getPregancyDay : function(lastPeriod) {
    	var expectedDate = getDateByDays(lastPeriod, 279);
    	return expectedDate;
    },
    /**
	 * 根据预产期计算末次月经
	 * @param lastPeriod 末次月经 格式 2017-02-17
	 */
    getLastPeriodByExp : function(expectedDate) {
    	var lastPeriod = getDateByDays(expectedDate, -279);
    	return lastPeriod;
	},
	/**
	 * 计算年龄
	 * @param birthday 出生日期 格式 2017-02-17
	 */
	getAge : function(birthday) {
		var birth = stringToDate(birthday);
		return new Date().getFullYear() - birth.getFullYear();
	},
	/**
	 * 计算bmi
	 */
	getBmi : function(height, weight) {
		var bmi = weight / ((height/100) * (height / 100));
		bmi = (!isNaN(bmi) && isFinite(bmi)) ? bmi : 0;
		return bmi.toFixed(1);
	},
	/**
	 * 计算运动消耗的卡洛里量
	 * @param currWeight 用户当前体重
	 * @param met 运动代谢当量
	 * @param timeLength 运动时长
	 */
	getSpoConsumeCal : function(currWeight, met, timeLength) {
		var calorie = (currWeight * met * timeLength / 60 * 1.05).toFixed(1);
		return calorie;
	}
}
/** 计算公式------------------end */

// 获取页面跳转参数
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 获取多少天后的日期
 * @param date 日期类型
 */
function getDateByDaysLate(date, AddDayCount) {
	date.setDate(date.getDate() + AddDayCount);//获取AddDayCount天后的日期
	var y = date.getFullYear();
	var m = date.getMonth() + 1;//获取当前月份的日期
	var d = date.getDate();
	return y + "-" + paddNum(m) + "-" + paddNum(d);
}
/**
 * 获取多少天后的日期
 * @param dateStr 日期字符串类型
 */
function getDateByDays(dateStr, day) {
	//var date = new Date(dateStr);//ie6不支持
	var date = stringToDate(dateStr);
	date.setDate(date.getDate() + day);
	var y = date.getFullYear();
	var m = date.getMonth() + 1;//获取当前月份的日期
	var d = date.getDate();
	return y + "-" + paddNum(m) + "-" + paddNum(d);
}
/**
 * 格式化日期为yyyy-MM-dd
 * @param date 日期
 * @returns date
 */
function formatDate(date) {
	return date.getFullYear() + "-" + paddNum(date.getMonth() + 1) + "-" + paddNum(date.getDate());
}
/**
 * 格式化日期为yyyy-MM-dd HH:mm:ss
 * @param date 日期
 * @returns date
 */
function formatDatetime(date) {
	date = new Date(date);
	return date.getFullYear() + "-" + paddNum(date.getMonth() + 1) + "-" + paddNum(date.getDate()) + " " 
	+ paddNum(date.getHours()) + ":" + paddNum(date.getMinutes()) + ":" + paddNum(date.getSeconds());
}
/**
 * 日期字符串转换成日期
 * @param str 日期字符串
 * @returns date
 */
function stringToDate(str) {
	str = str.split('-');
	var date = new Date();
	date.setFullYear(str[0], str[1] - 1, str[2]);
	date.setHours(0, 0, 0, 0);
	return date;
}
/**
 * 日期1减日期2相差多少天
 * @param dt1 日期字符串
 * @param dt2 日期字符串
 * @return 相差天数
 */
function getTowDateMinusDay(sdt1, sdt2) {
    var date1 = stringToDate(sdt1);
    var date2 = stringToDate(sdt2);
    //把相差的毫秒数转换为天数  
    var days = parseInt((date1 - date2) / 1000 / 60 / 60 /24);
    return  days;  
}
//如果1位的时候补0 用于日期月和日为一位时自动补0
function paddNum(num){
    num += "";
    return num.replace(/^(\d)$/,"0$1");
}
/**
 * xx分钟转换为xx小时xx分钟
 * @param mins 分钟数
 * @returns [hour, min]
 */
function minToHour(mins) {
	mins = (mins != null) ? mins : 0;
	var hour = parseInt(mins / 60);
	var min = mins - hour * 60;
	return [hour, min];
}
/**
 * xx小时xx分钟转换为分钟数
 * @param hours xx小时xx分钟 字符串
 * @returns mins 分钟数
 */
function hourToMin(hours) {
	if (hours == "") {
		return 0;
	}
	var hour = "0";
	var min = hours.substring(0, hours.indexOf("分钟"));
	if (hours.indexOf("小时") > 0) {
		hour = hours.substring(0, hours.indexOf("小时"));
		min = hours.substring(hours.indexOf("小时") + 2, hours.indexOf("分钟"));
	}
	var mins = Number(hour) * 60 + Number(min);
	return mins;
}

/**
 * 根据日期计算周几
 */
function dateToWeek(date){
	var day = stringToDate(date).getDay();
	var week = "周";
	switch (day) {
	  case 0 :
	    week += "日";
	    break;
	  case 1 :
	    week += "一";
	    break;
	  case 2 :
	    week += "二";
	    break;
	  case 3 :
	    week += "三";
	    break;
	  case 4 :
	    week += "四";
	    break;
	  case 5 :
	    week += "五";
	    break;
	  case 6 :
	    week += "六";
	    break;
	}
	return week;
}

/**
 * 通过餐次计算餐次名字
 */
function getMealsName(mealsType){
	var mealsName = "";
	switch (mealsType) {
	  case 1 :
	    mealsName = "早餐";
	    break;
	  case 2 :
	    mealsName = "早点";
	    break;
	  case 3 :
	    mealsName = "午餐";
	    break;
	  case 4 :
	    mealsName = "午点";
	    break;
	  case 5 :
	    mealsName = "晚餐";
	    break;
	  case 6 :
	    mealsName = "晚点";
	    break;
	}
	return mealsName;
}

/**
 * 根据bmi计算体重状态
 * @param bim
 * @return 体重状态 0：偏瘦，1：标准，2：偏胖，3：肥胖
 */
function getStatusByBmi(bmi) {
	var status = 3;
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
 * 膳食调查配置方案相关输入分量正则
 */
function checkNumber(obj){
	if(obj.value.indexOf(".")==0){
		if(isNaN(obj.value.substring(1))){
			 layer.msg("请输入数字！");
			 obj.value="";
		 }else{
			 obj.value=obj.value.replace(/^\.(\d).*$/,'0.$1');
		 }
	 }else{
		 if(isNaN(obj.value)){
			 layer.msg("请输入数字！");
			 obj.value="";
		 }else if(obj.value<0){
			 layer.msg("请输入正确的分量！");
			 obj.value="";
		 }else{
			 obj.value=obj.value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');
		 }
	 }
}

function checkNumbers(obj){
	if(obj.value.indexOf(".")==0){
		if(isNaN(obj.value.substring(1))){
			 obj.value="";
		 }else{
			 obj.value=obj.value.replace(/^\.(\d).*$/,'0.$1');
		 }
	 }else{
		 if(isNaN(obj.value)){
			 obj.value="";
		 }else{
			 obj.value=obj.value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');
		 }
	 }
}

/**兼容ie浏览器trim()方法*/
String.prototype.trim = function () {
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
}