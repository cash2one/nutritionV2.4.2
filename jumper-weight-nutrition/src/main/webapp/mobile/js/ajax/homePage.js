/**
 * 用户首页js
 */
var hospitalId = GetQueryString("hospitalid");
var userId = GetQueryString("userid");
$.cookie("userId",userId);
$.cookie("hospitalId",hospitalId);
var outpatientId = "";
var outpType = "";
var height = "";//孕前身高
//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//判断用户是初诊还是复诊状态
	/*var param = {hospitalId : hospitalId, userId : userId};
	$.post(basePath + "/outpatient/isFirstOutpatient", param, function(ret) {
		if (ret.msg != 1) {
			mui.toast(ret.msgbox);
			return;
		}
		outpType = ret.data.type;
		var outpatientId = ret.data.outpatientId;
		if (outpType == 0 || outpType == 1) {// 初诊
			
		}
	}, "json");*/
	
	
	//通过登记号查询用户首页信息，目标摄入量根据医生所开方案选择最高目标摄入量（如果就诊多次，有多次方案呢）
	$.ajax({
		url:basePath+"/health/getUserHealthInfo",
		type:"POST",
		dataType:"json",
		data:{userId:userId,hospitalId:hospitalId},
		success:function(json){
			if(json.msg == 1){
				var data = json.data;
				height = data.height;
				$("#beforeWeight").text(data.beforeWeight);
				$("#suggestIntake").text(data.suggestIntake);
				suggestIntake = data.suggestIntake;
				$("#beforeBMI").text(data.beforeBMI);
				if(data.needIntake<0){
					$("#prgLogo").attr("src","images/img-kcal/top-img-3.png");
					$("#kcalNum").text(data.needIntake*(-1));
				}else{
					$("#kcalNum").text(data.needIntake);
				}
				$("#eatKcal").text(data.eatKcal);
				eatKcal = data.eatKcal;
				$("#sportTimeLength").text(data.sportTimeLength);
				$("#sportKcal").text(data.sportKcal);
				$("#safeWeight").text(data.safeWeightLow+"kg-"+data.safeWeightHigh+"kg");
				$("#currWeight").text(data.currWeight+"kg");
				var pregnantWeek = data.pregnantWeek;
				var week = pregnantWeek[0]+"周";
				if(pregnantWeek[1] != 0){
					week += pregnantWeek[1]+"天";
				}
				$("title").text(week);
				//今天的体重，有今天的需要给刻度尺赋初值
				var currDate = data.currWeightTime;
				var today = formatDate(new Date());
				$("#today").text(today.substring(5));
				if(currDate != "" && currDate == today){//今天有体重
					$(".del-weight").attr("record-id",data.recordId);
					$(".del-weight").attr("curr-weight",data.currWeight);
				}
			}
		}
	});
	
	
	//点击饮食记录
	$(document).on("click","#dietSurvey",function(){
		window.location.href = "dietSurvey.html?skipType=home";
	});
	
	
	//点击运动记录
	$(document).on("click","#sportSurvey",function(){
		window.location.href = "sportSurvey.html?skipType=home";
	});
	
	
	//点击查看食谱
	$(document).on("click","#viewRecipes",function(){
		window.location.href = "viewRecipes.html?hospitalId="+hospitalId+"&userId="+userId;
	});
	
	//点击体重记录
	$(document).on("click",".btn-weight-ok",function(){
		var weight = $(".number").text();
		//保存用户体重记录
		$.ajax({
			url:basePath+"/physical/saveWeightRecord",
			type:"POST",
			dataType:"JSON",
			data:{userId:userId,hospitalId:hospitalId,value:weight},
			success:function(json){
				if(json.msg == 1){
					//修改最近体重值
//					$("#currWeight").text(weight);
					window.location.reload();
				}
			}
		});
		
	});
	
	//点击删除用户今日体重
	$(document).on("click",".del-weight",function(){
		var recordId = $(this).attr("record-id");
		if(recordId != null && recordId != "" && recordId != 0){
			$.ajax({
				url:basePath+"/physical/deleteWeightRecord",
				type:"POST",
				dataType:"JSON",
				data:{recordId:recordId},
				success:function(json){
					if(json.msg == 1){
						//修改最近体重值
//						$("#currWeight").text(weight);
						window.location.reload();
					}
				}
			});
		}
	});
	
});

//滑动刻度尺，根据体重值计算BMI
function getCurrBMI(weight){
	var BMI = TOOL.getBmi(height, weight);
	$(".bmi").text(BMI);
}
