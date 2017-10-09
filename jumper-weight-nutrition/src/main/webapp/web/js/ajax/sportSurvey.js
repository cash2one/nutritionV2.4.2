/**
 * 运动调查js
 */
var userId = (GetQueryString("userId") != null) ? GetQueryString("userId") : undefined;
var outpatientId = GetQueryString("outpatientId");//门诊id

$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//默认展现所有运动
	var spoData = {query : ""};
	showSportList(spoData);
	$("#search-btn").click(function() {
		spoData.query = $("input[name='query']").val().trim();
		showSportList(spoData);
	});
	
	//展现出运动调查的列表
	var surData = {outpatientId : outpatientId, type : 0};
	$.post(basePath + "/sportSurvey/listSportSurveyByOutpId", surData, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		var list = ret.data;
		var dateHtml = "";
		//运动调查日期list
		for (var int = 0; int < list.length; int++) {
			var obj = list[int];
			var week = dateToWeek(obj.sportDate);
			var str = "<li class='layui-this surveyDate' surveyId=" + obj.surveyId + "><span class='tabelTitleN surveyDateWeek'>" + obj.sportDate + " " + week + "</span>"
			if (int != 0) {
				str += "<i class='layui-icon layui-unselect layui-tab-close deleteSurvey' surveyId=" + obj.surveyId + ">ဆ</i>";
			}
			str += "</li>";
			dateHtml += str;
		}
		//展示运动调查列表
		getRecordListHtml(list[0]);
		//展示日期列表
		$("#dateList").html(dateHtml);
		
		//移除掉除layui-this选中属性
		$(".surveyDate").each(function(i) {
			if (i != 0) {
				$(this).removeClass("layui-this");
			}
		});
	});
	
	//展现门诊中的自定义运动
	$.post(basePath + "/outpatient/findOutpById", {outpatientId : outpatientId}, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		$("input[name='userDefinedSport']").val(ret.data.userDefinedSport);
	});
	
	//绑定回车事件触发搜索运动
	$(".sportsArea").keydown(function(e) {
		e = e||event;
    	var key = e.keyCode;
    	if (key == 13) {
    		$(".enterBtnEvent").click();
		}
	});
	//绑定回车事件下一个输入框聚焦
	$(".sportRecordArea").keydown(function(e) {
		e = e||event;
		var index = 0;
		var target = $(".eventArea").find("input[type='text']");
		target.each(function(i) {
			if ($(this).is(":focus")) {
				index = (i < target.length - 1) ? i+1 : 0;
				return false;
			}
		});
		var key = e.keyCode;
		if (key == 13) {
			target.eq(index).focus();
		}
	});
	
	//切换运动调查日期时，保存当前的运动调查记录
	$(document).on("click", ".surveyDateWeek", function() {
		//保存
		var result = saveSportSurvey();
		if (!result.isSuccess) {
			layer.msg(result.msgbox, {time : 1000});
			return;
		}
		
		//展现
		var dateW = $(this).text();
		var surveyDate = dateW.substring(0, dateW.indexOf("周") - 1);
		var data = {outpatientId : outpatientId, date : surveyDate};
		$.post(basePath + "/sportSurvey/findSurveyByDateOutp", data, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time : 1000});
				return;
			}
			//运动调查列表
			getRecordListHtml(ret.data);
		});
		//菜单栏选中事件
		addTabActive($(this).parents("li"));
	});
	
	//新增调查天数，并显示用户运动记录
	/*--------------------  layui 日历     ---------------------- --*/
    layui.use('laydate', function() {
        var laydate = layui.laydate;
    });
    $('#addDay').click(function () {
        layui.laydate({
			elem : this,
			festival : true,
			isclear : false,
			max : laydate.now()
		});
        $("#laydate_table td, #laydate_today").unbind('click').click(function () {
        	if ($(this).hasClass("laydate_void")) {
				return;
			}
    		//新增调查日期li
    		setTimeout(function() {
    			addTableTitle_date();
			}, 10);
    	});
    });
	
	//删除某天的运动调查记录
    $(document).on("click", ".deleteSurvey", function() {
    	var idx = $(this).parents("li").index();
    	var surveyId = $(this).attr("surveyId");
    	var currDel = $(this);
    	layer.alert('确定要删除该运动调查么？', {
            icon: 0,
            title: '提示',
            btn: ['删除'],
            btnAlign: 'c',
            yes: function (index, layero) {
            	//执行删除
            	if (surveyId != "") {
					$.post(basePath + "/sportSurvey/delteSportSurveyById", {surveyId : surveyId}, function(ret) {
						if (ret.msg != 1) {
							layer.msg(ret.msgbox, {time: 600});
						}
					});
				}
            	layer.close(index);
            	//移除tab选项
            	currDel.parents("li").remove();
            	//移除tab选项后,active自动往前移动
            	$(".layui-tab-title li").eq(idx - 1).addClass("layui-this");
                //展现记录
        		var dateW = $(".layui-tab-title li").eq(idx - 1).find(".surveyDateWeek").text();
        		var surveyDate = dateW.substring(0, dateW.indexOf("周") - 1);
        		var data = {outpatientId : outpatientId, date : surveyDate};
        		$.post(basePath + "/sportSurvey/findSurveyByDateOutp", data, function(ret) {
        			if (ret.msg != 1) {
        				layer.msg(ret.msgbox, {time : 1000});
        				return;
        			}
        			//运动调查列表
        			getRecordListHtml(ret.data);
        		});
            }
        });
	});
    
	//添加运动到列表
	$(document).on("click", ".sport", function() {
		var sportId = $(this).attr("sportId");
		var met = Number($(this).attr("met"));
		//判断是否已有该运动
		var isRepeat = false;
		$("#recordList tr").each(function() {
			if (sportId == $(this).attr("sportId")) {
				isRepeat = true;
				return;
			}
		});
		if (isRepeat) {
			layer.msg("列表中已有该运动", {time: 1000});
			return;
		}
		//判断总时长是否超过24小时
		var isOver = isOverTime();
		if (isOver) {
			layer.msg("总时长不能超过24小时", {time: 1000});
			return;
		}
		//默认30分钟，并计算30分钟消耗的卡洛里
		var currWeight = Number($(".currWeightSpan").text());
		var calories = TOOL.getSpoConsumeCal(currWeight, met, 30);
		var html = 
			"<tr sportId=" + sportId + " met=" + met + ">" +
				"<td>" + $(this).text() + "</td>" +
				"<td><label><input class='inputH' type='text' value='0'></label></td>" +
				"<td><label><input class='inputM' type='text' value='30'></label></td>" +
				"<td class='calories'>" + calories + "</td>" +
				"<td><a class='btn_blue trDel'>删除</a></td>" +
			"</tr>";
		$("#recordList").append(html);
		//更新合计时间和总消耗能量
		showTotalByChange();
	});
	
	//运动时间变化时，对应的消耗卡洛里也变化
	$(document).on("blur", ".inputH, .inputM", function() {//change方法不支持ie6
		var parTr = $(this).parent().parent().parent();
		var hour = Number(parTr.find(".inputH").val());
		var min = Number(parTr.find(".inputM").val());
		var met = Number(parTr.attr("met"));
		var timeLength = hour * 60 + min;
		var currWeight = Number($(".currWeightSpan").text());
		var calories = TOOL.getSpoConsumeCal(currWeight, met, timeLength);
		parTr.find(".calories").text(calories);
		//更新合计时间和总消耗能量
		showTotalByChange();
		//判断总时长是否超过24小时
		var isOver = isOverTime();
		if (isOver) {
			layer.msg("总时长不能超过24小时", {time: 1000});
		}
	});
	
	//点击返回保存当前记录，并返回上一个页面
	$(".survey-go-back").click(function() {
		//保存
		saveSportSurvey();
		window.location.href = "dietSurvey.html?userId=" + userId + "&outpatientId=" + outpatientId;
	});
	//点击下一步时保存当前记录
	$("#nextStep").click(function() {
		//保存
		var result = saveSportSurvey();
		if (!result.isSuccess) {
			layer.msg(result.msgbox, {time : 1000});
			return;
		}
		//保存自定义运动
		var outData = {outpatientId : outpatientId, isFinish : 0, userDefinedSport : $("input[name='userDefinedSport']").val().trim()}
		$.post(basePath + "/outpatient/updateOutpatient", outData, function(ret) {
			if (ret.msg != 1) {
				layer.msg(result.msgbox, {time : 1000});
				return;
			}
		});
		window.location.href = "makePlan.html?userId=" + userId + "&outpatientId=" + outpatientId;
	});
	//点击跳过
	$("#jumperOver").click(function() {
		window.location.href = "makePlan.html?userId=" + userId + "&outpatientId=" + outpatientId;
	});
});

//展现运动列表
function showSportList(spoData) {
	$.post(basePath + "/sport/listSportsByName", spoData, function(ret) {
		if (ret.msg == 1) {
			var list = ret.data;
			var html = "";
			if (list.length == 0) {
				html = '<li style="font-size : 14px;">没有符合关键词的结果</li>';
				$("#sportList").html(html);
				return;
			}
			for (var int = 0; int < list.length; int++) {
				var obj = list[int];
				html += "<li><div class='daoruListFirst sport' sportId=" + obj.id + " met=" + obj.met + ">" + obj.name + "</div></li>";
			}
			$("#sportList").html(html);
		} else {
			layer.msg(ret.msgbox, {time : 1000});
		}
	});
}

//封装展示记录列表
function getRecordListHtml(daily) {
	var recordList = daily.recordList;
	var listHtml = "";
	for (var int = 0; int < recordList.length; int++) {
		var obj = recordList[int];
		var mh = minToHour(obj.timeLength);
		listHtml += 
			"<tr sportId=" + obj.sportId + " met=" + obj.met + ">" +
				"<td>" + obj.sportName + "</td>" +
				"<td><label><input class='inputH' type='text' value=" + mh[0] + "></label></td>" +
				"<td><label><input class='inputM' type='text' value=" + mh[1] + "></label></td>" +
				"<td class='calories'>" + obj.calories + "</td>" +
				"<td><a class='btn_blue trDel'>删除</a></td>" +
			"</tr>";
	}
	//赋值合计时间和总热量
	var minh = minToHour(daily.totalTime);
	//展示
	$("#recordList").html(listHtml);
	$(".totalCalorie").text(daily.totalCalorie.toFixed(1));
	$(".totalTime").text(minh[0] + "小时" + minh[1] + "分钟");
}

//保存当前运动调查记录
function saveSportSurvey() {
	var returnJson = {isSuccess : true, msgbox : "保存成功"};
	//封装调查列表
	var recordList = [];
	if ($("#recordList tr").length == 0) {
		returnJson.isSuccess = false;
		returnJson.msgbox = "运动记录不能为空";
		return returnJson;
	}
	$("#recordList tr").each(function(i) {
		var sportId = $(this).attr("sportId");
		var hour = $(this).find(".inputH").val();
		var min = $(this).find(".inputM").val();
		var timeLength = Number(hour) * 60 + Number(min);
		if (timeLength > 0) {
			var obj = {sportId : sportId, timeLength : timeLength};
			recordList.push(obj);
		}
	});
	if (recordList.length == 0) {
		returnJson.isSuccess = false;
		returnJson.msgbox = "运动记录不能为空";
		return returnJson;
	}
	//判断总时长是否超过24小时
	var isOver = isOverTime();
	if (isOver) {
		returnJson.isSuccess = false;
		returnJson.msgbox = "总时长不能超过24小时";
		return returnJson;
	}
	//保存当前调查记录
	var dateW = $(".layui-this").find(".surveyDateWeek").text();
	var surveyDate = dateW.substring(0, dateW.indexOf("周") - 1);
	var saveData = {outpatientId : outpatientId, date : surveyDate, recordList : JSON.stringify(recordList)};
	$.post(basePath + "/sportSurvey/saveSurveyByDate", saveData, function(ret) {
		if (ret.msg != 1) {
			returnJson.isSuccess = false;
			returnJson.msgbox = ret.msgbox;
		}
	});
	return returnJson;
}

//新增一个运动调查日期
function addTableTitle_date() {
	var date = $("#addDay").val();
	var flag = isRepeatDate(date);
	if (flag) {
		layer.msg("日期重复", {time : 1000});
		return;
	}
	if($(".layui-tab-title li").length >= 7) {
		layer.msg("最多只能调查7天", {time : 1000});
		return;
	}
	//保存当前的记录
	var result = saveSportSurvey();
	if (!result.isSuccess) {
		layer.msg(result.msgbox, {time : 1000});
		return;
	}
	
	var week = dateToWeek(date);
	$(".layui-tab-title").append(
		"<li class='layui-this surveyDate' surveyId=''><span class='tabelTitleN surveyDateWeek'>" + date + " " + week + "</span>" + 
			"<i class='layui-icon layui-unselect layui-tab-close deleteSurvey' surveyId=''>ဆ</i>" + 
		"</li>");
	//添加tab选项 acive自动移到新建的tab上~
	var tabTitle = $(".layui-tab-title li");
	tabTitle.eq(tabTitle.length - 1).addClass("layui-this").siblings().removeClass("layui-this");
	//展现
	var data = {userId : userId, date : date};
	$.post(basePath + "/sport/listSportRecordByDate", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		//运动调查列表
		getRecordListHtml(ret.data);
	});
}

//判断是否有重复的日期
function isRepeatDate(date) {
	var flag = false;
	$(".surveyDateWeek").each(function(i) {
		var dateW = $(this).text();
		var surveyDate = dateW.substring(0, dateW.indexOf("周") - 1);
		if(surveyDate == date) {
			flag = true;
			return false;
		}
	});
	return flag;
}
//判断总时长是否超过24小时
function isOverTime() {
	var totalTime = 0;//总数
	$("#recordList tr").each(function(i) {
		var hour = Number($(this).find(".inputH").val());
		var min = Number($(this).find(".inputM").val());
		totalTime += (hour * 60 + min);
	});
	return (totalTime > 24 * 60);
}

//菜单栏（运动调查列表）选中
function addTabActive(_this) {
    _this.addClass("layui-this").siblings().removeClass("layui-this");
}

//input 数字输入范围限制方法以及提示框;
layui.use('layer', function(){
    var layer = layui.layer;
    function inputFn1(target,num1,num2) {
        $(document).on('keyup afterpaste', target, function () {
            $(this).val($(this).val().replace(/[^0-9]/g, ''));
            if($(this).val()<num1 || $(this).val()>num2){
                layer.msg("请输入"+num1+"-"+num2+"的数字",{time: 1200});
                $(this).val(1);
            }
        });
    }
    inputFn1(".inputH",0,24);
    inputFn1(".inputM",0,60);
});

//计算出当前运动列表的合计时间和总消耗能量
function showTotalByChange() {
	var totalTime = 0, totalCalorie = 0;//总数
	$("#recordList tr").each(function(i) {
		var hour = Number($(this).find(".inputH").val());
		var min = Number($(this).find(".inputM").val());
		var calories = Number($(this).find(".calories").text());
		totalTime += (hour * 60 + min);
		totalCalorie += calories;
	});
	var minh = minToHour(totalTime);
	////赋值合计时间和总热量
	$(".totalTime").text(minh[0] + "小时" + minh[1] + "分钟");
	$(".totalCalorie").text(totalCalorie.toFixed(1));
}