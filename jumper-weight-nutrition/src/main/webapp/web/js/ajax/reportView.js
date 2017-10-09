/**
 * 查看报告js
 */
var reportId = GetQueryString("reportId");
var backOutpatient = GetQueryString("backOutpatient");

var outpatientId = "";
var spoList = [];
var userId = "";//体重曲线要用到
//用户基本信息
function listUserMsg(userMsg,hospitalName,dietAdvice,doctorAdvice,addTime,reportNumber){
	$("#hospitalName").text(hospitalName);
	if(dietAdvice == null || dietAdvice == "" || typeof(dietAdvice)=="undefined"){
		dietAdvice = "暂无";
	}
	$("#dietAdvice").text(dietAdvice);
	if(doctorAdvice == null || doctorAdvice == "" || typeof(doctorAdvice)=="undefined"){
		doctorAdvice = "暂无";
	}
	
	$("#doctorAdvice").text(doctorAdvice);
	$("#addTime").text(addTime);
	$("#reportNumber").text("报告编号："+reportNumber);
	
	$("#realName").text(userMsg.realName);
	$("#age").text(userMsg.age);
	$("#weight").text(userMsg.weight);
	$("#height").text(userMsg.height);
	$("#beforeBMI").text(userMsg.beforeBMI);
	$("#pregnantStage").text(userMsg.pregnantStage);
	$("#pregnantWeek").text(userMsg.pregnantWeek);
	$("#currentWeight").text(userMsg.currentWeight);
	$("#currentBMI").text(userMsg.currentBMI);
	$("#bodyFatRate").text(userMsg.bodyFatRate==0?"/":userMsg.bodyFatRate+"%");
	$("#muscle").text(userMsg.muscle==0?"/":userMsg.muscle+"%");
	$("#moistureContent").text(userMsg.moistureContent==0?"/":userMsg.moistureContent+"%");
	$("#basalMetabolism").text(userMsg.basalMetabolism==0?"/":userMsg.basalMetabolism+"kcal");
	$("#suggestWeightAdd").text(userMsg.suggestWeightAdd);
	$("#currentWeightAdd").text(userMsg.currentWeightAdd.toFixed(1));
	
}

//膳食调查
function dietSurvey(outpatientId){
	$.ajax({
		url:basePath+"/dietSurvey/findUserDietSurveyList",
		type:"post",
		dataType:"json",
		data:{outpatientId:outpatientId,type:1},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var list = json.data;
				var html = "";
				for(var i=0;i<list.length;i++){
					html += "<p class='report-foodlist-title'>"+list[i].eatDate+"</p>"+
							"<table class='report-table report-table-diet'>"+
								"<th>餐次</th><th>食物名称</th><th>用量（g）</th>";
					var info = list[i].infoList;
					if(info != null && info != ""){
						for(var j=0;j<info.length;j++){
							var mealsType = info[j].mealsType;
							var mealsName = getMealsName(parseInt(mealsType));
							var unitRemark = info[j].unitRemark;
							if(unitRemark != null && unitRemark != ""){
								unitRemark = "【"+unitRemark+"】";
							}else{
								unitRemark = "";
							}
							html += "<tr><td>"+mealsName+"</td><td>"+info[j].foodName+unitRemark+"</td><td>"+info[j].foodWeight+"</td></tr>";
						}		
					}else{
						html += "<tr><td colspan='3'>暂无记录</td></tr>";
					}
					html += "</table>";
				}
				$("#dietSurveyArea").append(html);
			}else if(json.msg == 10){
				$("#dietSurvey").css("display","none");
			}
		}
	});
}

//方案
function listUserRecipesPlan(outpatientId){
	$.ajax({
		url:basePath+"/userRecipes/findUserRecipesPlansByOutPatientId",
		type:"post",
		dataType:"json",
		data:{outpatientId:outpatientId},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var list = json.data.planList;
				var html = "";
				for(var i=0;i<list.length;i++){
					html += "<p class='report-foodlist-title'>"+list[i].recipesName+"</p>"+
							"<table class='report-table report-table-diet'>"+
								"<th>餐次</th><th>食物名称</th><th>用量（g）</th>";
					var info = list[i].recipesMsg;
					if(info != null && info != ""){
						var recipesMsg = eval("("+info+")");
						recipesMsg.sort(function(a,b){
							return a.mealsType-b.mealsType;
						});
						for(var j=0;j<recipesMsg.length;j++){
							var mealsType = recipesMsg[j].mealsType;
							var mealsName = getMealsName(parseInt(mealsType));
							var unitRemark = recipesMsg[j].unitRemark;
							if(unitRemark != null && unitRemark != ""){
								unitRemark = "【"+unitRemark+"】";
							}else{
								unitRemark = "";
							}
							html += "<tr><td>"+mealsName+"</td><td>"+recipesMsg[j].foodName+unitRemark+"</td><td>"+recipesMsg[j].foodWeight+"</td></tr>";
						}
					}else{
						html += "<tr><td colspan='3'>暂无记录</td></tr>";
					}
					html += "</table>";
				}
				$("#recipesPlan").after(html);
			}else if(json.msg == 10){
				$("#recipesPlan").css("display","none");
			}
		}
	});
}

/**
 * 初始化页面数据
 */
$(function(){
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//是否显示返回门诊按钮
	if(backOutpatient == CONST.backOutpatient[0]){
		$("#report-back-outpatient").css("display","none");
	}
	var param = {
		reportId:reportId
	};
	var userDefinedSport = "";
	//获取用户基本信息
	$.ajax({
		url:basePath+"/report/findUserWeightReport",
		type:"post",
		dataType:"json",
		data:param,
		async:false,
		success:function(json){
			if(json.msg == 1){
				userId = json.data.userId;
				var hospitalId = json.data.hospitalId;
				outpatientId = json.data.outpatientId;
				var userMsg = json.data.voUserMsg;
				var hospitalName = json.data.hospitalName;
				var dietAdvice = json.data.dietAdvice;
				var doctorAdvice = json.data.doctorAdvice;
				var addTime = json.data.addTime;
				var reportNumber = json.data.reportNumber;
				userDefinedSport = json.data.userDefinedSport;
				listUserMsg(userMsg,hospitalName,dietAdvice,doctorAdvice,addTime,reportNumber);
				dietSurvey(outpatientId);
				listUserRecipesPlan(outpatientId);
			}
		}
	});
	
	//加载运动调查记录
	var data = {outpatientId : outpatientId, type : 1};
	$.post(basePath + "/sportSurvey/listSportSurveyByOutpId", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		spoList = ret.data;
		var html = "";
		for (var int = 0; int < spoList.length; int++) {
			var obj = spoList[int];
			html += 
				'<p class="report-foodlist-title">' + obj.sportDate + '</p>' +
				'<table class="report-table report-table-sport">' +
					'<th>活动名称</th>' +
					'<th>小时</th>' +
					'<th>分钟</th>' +
					'<th>能量（kcal）</th>';
			var recordList = obj.recordList;
			var recordHtml = "";
			for (var int2 = 0; int2 < recordList.length; int2++) {
				var obj2 = recordList[int2];
				var mh = minToHour(obj2.timeLength);
				recordHtml += 
					'<tr>' +
						'<td>' + obj2.sportName + '</td>' +
						'<td>' + mh[0] + '</td>' +
						'<td>' + mh[1] + '</td>' +
						'<td>' + obj2.calories + '</td>' +
					'</tr>';
			}
			html +=	recordHtml +	
				'</table>';
		}
		$("#sportSurveyArea").append(html);
	});
	//if(userDefinedSport != null && userDefinedSport != "" && typeof(userDefinedSport) != "undefined") {
	if (!$.isEmptyObject(userDefinedSport) && spoList.length > 0) {
		var userDefinedSportHtml = "<p>自定义运动：" + userDefinedSport + "</p>";
		$("#sportSurveyArea").append(userDefinedSportHtml);
	}
	//如果运动调查为空那么隐藏运动调查标题
	if (spoList.length == 0) {
		$("#sportSurvey").hide();
	}
	
	//调设置接口
	$.ajax({
		url:basePath+"/settings/findSettingByHospId",
		type:"post",
		dataType:"json",
		data:{hospitalId:hospitalId},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var hideDiet = json.data.hideDiet;
				var hideSport = json.data.hideSport;
				var hideFoodtab = json.data.hideFoodtab;
				var hideExchange = json.data.hideExchange;
				if(hideDiet == 1){//隐藏膳食调查清单
					$("#dietSurvey").css("display","none");
					$("#dietSurveyArea").css("display","none");
				}
				if(hideSport == 1){
					$("#sportSurvey").hide();
					$("#sportSurveyArea").hide();
				}
				if(hideFoodtab == 1){
					$("#foodtab").css("display","none");
				}
				if(hideExchange == 1){
					$("#exchange").css("display","none");
				}
			}
		}
	});
	
});

/**
 * 打印
 */
$(document).on("click","#report-print",function(){
	$(".report-head").css("display","none");
	window.print();
	setTimeout(function(){
		$(".report-head").css("display","block");
	},100);
});

/**
 * 返回门诊
 */
$(document).on("click","#report-back-outpatient",function(){
	window.location.href = "outpatient.html?hospitalId=" + hospitalId;
});