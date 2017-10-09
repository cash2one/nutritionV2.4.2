/**
 * 全部运动记录js
 */

var userId = (GetQueryString("userId") != null) ? GetQueryString("userId") : undefined;
var outpatientId = GetQueryString("outpatientId");//门诊id
var menuType = GetQueryString("menuType");

//分页数据
var totalPage = 0;//总页数
var curr = 1;//当前页
var list = [];//数据

//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	$(".showAllRecord").addClass("active");
	var data = {userId : userId, page : curr, limit : CONST.pageSize};
	//初始化分页
	initPage(data);
	
	//查询按钮
	$("#search-btn").click(function() {
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		data.page = 1;
		data.startDate = startDate;
		data.endDate = endDate;
		//分页查询
		initPage(data);
	});
	//快捷全部查询和30天查询
	$(".showAllRecord, .showMonthRecord").click(function() {
		$(this).addClass("active");
		$(this).parent().siblings().find("a").removeClass("active");
		data.page = 1;
		if ($(this).hasClass("showMonthRecord")) {
			var startDate = getDateByDaysLate(new Date(), -29);
			var endDate = formatDate(new Date());
			$("#startDate").val(startDate);
			$("#endDate").val(endDate);
			data.startDate = startDate;
			data.endDate = endDate;
		} else {
			$("#startDate").val("");
			$("#endDate").val("");
			delete data["startDate"];
			delete data["endDate"];
		}
		//分页查询
		initPage(data);
	});
});


//初始化分页
function initPage(data) {
	//初始分页信息
	function init(data) {
		$.post(basePath + "/sport/listSportRecordByDuring", data, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time : 1000});
				return;
			}
			totalPage = ret.data.pages;
			curr = ret.data.pageNum;
			list = ret.data.list;
		});
	}
	init(data);
	//展示分页列表
	layui.use(["laypage"], function() {
		layui.laypage({
			cont : "page",
			pages : totalPage,
			curr : data.page,
			skip : true,
			jump : function(p) {
				//得到了当前页，用于向服务端请求对应数据
				curr = p.curr;
				data.page = curr;
				//跳转到某页，并初始化分页数据
				init(data);
				var html = ""
				for (var int = 0; int < list.length; int++) {
					var obj = list[int];
					var week = dateToWeek(obj.sportDate);
					html += 
						"<div class='panel'>" + 
							"<ul class='tabnav2'>" + 
								"<li class='active'><a>" + obj.sportDate + " " + week + "</a></li>" + 
							"</ul>" + 
							"<div class='tabstyle2'>" + 
								"<table class='tablerec'>";
					var recordList = obj.recordList;
					var recordHtml = "";
					for (var int2 = 0; int2 < recordList.length; int2++) {
						var obj2 = recordList[int2];
						recordHtml += 
							"<tr>" + 
								"<td>" + obj2.sportName + "</td>" + 
								"<td>" + obj2.timeLength + "分钟</td>" + 
								"<td>消耗能量" + obj2.calories + "kcal</td>" + 
							"</tr>";
					}
					html += recordHtml + 
								"</table>" + 
								"<p class='record'>本日共消耗能量：<span class='fontbule'>" + obj.totalCalorie + "kcal</span></p>" + 	
			    			"</div>" + 
						"</div>";
				}
				$("#pageList").html(html);
			}
		});
	});
}