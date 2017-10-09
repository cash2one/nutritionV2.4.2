/**
 * 诊疗历史js
 */

var userId = (GetQueryString("userId") != null) ? GetQueryString("userId") : undefined;
var outpatientId = GetQueryString("outpatientId");//门诊id
var menuType = GetQueryString("menuType");

//页面所需后台的数据
$(function() {
	isShowAdvice();
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	var data = {hospitalId : hospitalId, userId : userId}
	$.post(basePath + "/outpatient/listUserOutpatient", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		var list = ret.data;
		var html = "";
		for (var int = 0; int < list.length; int++) {
			var obj = list[int];
			var week = dateToWeek(obj.outpatientTime);
			var doctorAdvice = (!$.isEmptyObject(obj.doctorAdvice)) ? obj.doctorAdvice : "暂无";
			html += 
				"<div class='Z-history'>" +                                                                                                  
				"	<div class='Z-history-title'>" +                                                                                         
				"		<span class='Z-history-name'>" + CONST.outpType[obj.status] + "</span>" +
				"		<span>" + obj.outpatientTime + "</span>" +                                                                                          
				"		<span>" + week + "</span>" +
				"	</div>" +
				"	<div class='Z-advise'>" +
				"		<p>医生建议：<span>" + doctorAdvice + "</span></p>" +
				"	</div>" +
				"	<div class='Z-btn'>";
			if (obj.reportId != null) {
				html += "<a target='_blank' class='lianbut showReport' reportId=" + obj.reportId + ">查看报告</a>";
			}
			if (obj.isMakePlan == 1) {
				html += "<a class='lianbut showPlan' outpId=" + obj.id + ">查看方案</a>";
			}
			html +=	
				"	</div>" +                                                                                                                
				"</div>";
		}
		$("#treatHistoryList").html(html);
	});
	
	//点击查看报告
	$(document).on("click", ".showReport", function() {
		var reportId = $(this).attr("reportId");
		var url = "reportView.html?reportId=" + reportId + "&backOutpatient=" + CONST.backOutpatient[0];
		$(this).attr("href",url);
//		window.location.href = "reportView.html?reportId=" + reportId + "&backOutpatient=" + CONST.backOutpatient[0];
	});
	//点击查看方案
	$(document).on("click", ".showPlan", function() {
		var outpId = $(this).attr("outpId");
		window.location.href = "viewPlan.html?userId=" + userId + "&outpatientId=" + outpId + "&menuType=" + menuType;
	});
});