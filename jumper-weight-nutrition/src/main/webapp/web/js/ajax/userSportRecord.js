/**
 * 运动记录js
 */
var userId = (GetQueryString("userId") != null) ? GetQueryString("userId") : undefined;
var outpatientId = GetQueryString("outpatientId");//门诊id
var menuType = GetQueryString("menuType");

var list = [];//运动记录列表

$(function() {
	isShowAdvice();
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//默认查近7条
	var data = {userId : userId, page : 1, limit : 7};
	$.post(basePath + "/sport/listSportRecordByDuring", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		if (ret.data.list.length == 0) {
			$("#sportDateList").html("暂无运动记录");
			$(".totalCalorie").text("无");
			$(".totalCalorie").css("color", "#000000");
			return;
		}
		list = ret.data.list;
		var dateHtml = "";
		for (var int = 0; int < list.length; int++) {
			var obj = list[int];
			var week = dateToWeek(obj.sportDate);
			dateHtml += "<li class='sportDate-li'><a>" + obj.sportDate + " " + week + "</a></li>";
		}
		if (ret.data.total > 7) {
			//dateHtml += '<li class="showAllRecord"><a>查看全部</a></li>';
			dateHtml += 
				'<div class="tab-cheakall fr">' + 
					'<a class="bigbtn-blue showAllRecord">查看全部</a>' +
				'</div>';
		}
		$("#sportDateList").prepend(dateHtml);
		//设置第一个默认选中
		$("#sportDateList li:first").addClass("active");
		var daylyRecord = list[0];
		//展示用户运动记录
		showRecordList(daylyRecord);
	});
	
	//切换日期
	$(document).on("click", ".sportDate-li", function() {
		$(this).addClass("active").siblings().removeClass("active");
		var date = $(this).find("a").text();
		date = date.substring(0, 10);
		for (var int = 0; int < list.length; int++) {
			var obj = list[int];
			if (obj.sportDate == date) {
				//展示用户运动记录
				showRecordList(obj);
				break;
			}
		}
	});
	
	//查看全部
	$(document).on("click", ".showAllRecord", function() {
		window.location.href = "allSportRecord.html?userId=" + userId + "&outpatientId=" + outpatientId + "&menuType=" + menuType;
	});
});

//封装展示用户运动记录
function showRecordList(daylyRecord) {
	var daySportList = daylyRecord.recordList;
	var recordHtml = "";
	for (var i = 0; i < daySportList.length; i++) {
		var obj = daySportList[i];
		recordHtml += "<tr><td>" + obj.sportName + "</td><td>" + obj.timeLength + "分钟</td><td>消耗能量" + obj.calories + "kcal</td></tr>";
	}
	$(".recordList").html(recordHtml);
	$(".totalCalorie").text(daylyRecord.totalCalorie + "kcal");
}
