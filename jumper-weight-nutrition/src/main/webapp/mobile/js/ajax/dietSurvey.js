/**
 * 膳食调查js
 */
var skipType = GetQueryString("skipType");
 $(document).ready(function(){
	 $.ajaxSetup({ //设置ajax为同步提交
	        async: false
	    });
	 
	var param = {userId:userId};
	if(skipType == "home"){
		param.startTime = getDateByDaysLate(new Date(),0);
		param.endTime = getDateByDaysLate(new Date(),0);
	}
	$.ajax({
		url:basePath+"/diet/findUserMealsInfoList",
		type:"POST",
		dataType:"json",
		data:param,
		success:function(json){
			var data = json.data;
			var html = "",breakfast="",breakfastAdd="",lunch="",lunchAdd="",dinner="",dinnerAdd="";
			for(var i=0;i<data.length;i++){
				var unitRemark = data[i].unitRemark;
				if(unitRemark != null && unitRemark != ""){
					unitRemark = "【"+unitRemark+"】";
				}else{
					unitRemark = "";
				}
				html = "<li><span>"+data[i].foodName+"</span><span class='fr'>"+data[i].foodWeight+"克"+unitRemark+"</span></li>";
				if(data[i].mealsType==1){breakfast += html;}
				if(data[i].mealsType==2){breakfastAdd += html;}
				if(data[i].mealsType==3){lunch += html;}
				if(data[i].mealsType==4){lunchAdd += html;}
				if(data[i].mealsType==5){dinner += html;}
				if(data[i].mealsType==6){dinnerAdd += html;}
			}
			if(breakfast!=""){
				$("#breakfast").html(breakfast);
				$("#breakfast").attr("data","1");
			}
			if(breakfastAdd!=""){
				$("#breakfastAdd").html(breakfastAdd);
				$("#breakfastAdd").attr("data","1");
			}
			if(lunch!=""){
				$("#lunch").html(lunch);
				$("#lunch").attr("data","1");
			}
			if(lunchAdd!=""){
				$("#lunchAdd").html(lunchAdd);
				$("#lunchAdd").attr("data","1");
			}
			if(dinner!=""){
				$("#dinner").html(dinner);
				$("#dinner").attr("data","1");
			}
			if(dinnerAdd!=""){
				$("#dinnerAdd").html(dinnerAdd);
				$("#dinnerAdd").attr("data","1");
			}
			
		}
	});
	if(skipType == "home"){//从用户首页点击进来的
		$("#diet_survey_next").text("完成");
	}else{
		var outpReason = $.cookie("outpReason");
		//查询是否跳过运动调查
		$.ajax({
			url:basePath+"/settings/findHospitalOutpatientReasonById",
			type:"POST",
			dataType:"json",
			data:{outpReasonId:outpReason},
			success:function(json){
				if(json.msg == 1){
					var isSkipSport = json.data.isSkipSport;
					if(isSkipSport == 1){
						$("#diet_survey_next").attr("isSkipSport",1).text("完成");
					}else if(isSkipSport == 0){
						$("#diet_survey_next").attr("isSkipSport",0).text("下一步");
					}
				}
			}
		});
	}
});
 /**
  * 选择食物跳转搜索页面
  */
 $(document).on("click",".chooseFood",function(){
	 var mealsType = $(this).attr("mealsType");
	 window.location.href = "searchFood.html?mealsType="+mealsType+"&skipType="+skipType;
 })
 
 /**
  * 点击下一步，跳转运动调查
  */
 $(document).on("click","#diet_survey_next",function(){
	 var isSkipSport = $(this).attr("isSkipSport");
	 var breakfast = $("#breakfast").attr("data");
	 var lunch = $("#lunch").attr("data");
	 var dinner = $("#dinner").attr("data");
	 if(breakfast == "0"){
		 mui.toast("请添加早餐食物");
	 }else if(lunch == "0"){
		 mui.toast("请添加午餐食物");
	 }else if(dinner == "0"){
		 mui.toast("请添加晚餐食物");
	 }else{
		 if(skipType == "home"){
			 //从用户首页点击进来，点击完成返回首页
			 window.location.href = "homePage.html?hospitalid="+hospitalId+"&userid="+userId;
		 }else{
			 if(isSkipSport == 0){
				 //不跳过运动调查，跳转
				 window.location.href = "sportSurvey.html";
			 }else if(isSkipSport == 1){
				//跳过运动调查（直接完成）
				//保存一条门诊记录
				var isSuccess = true;
				var outpatientId = "";
				var outpatientReason = $.cookie('outpReason');
				var opData = {hospitalId : hospitalId, userId : userId, outpatientReason : outpatientReason};
				$.post(basePath + "/outpatient/addOutpatient", opData, function(ret) {
					if (ret.msg != 1) {
						isSuccess = false;
						mui.toast(ret.msgbox);
						return;
					}
					outpatientId = ret.data;
				}, "json");
				if (!isSuccess) {
					return;
				}
				//跳转
				window.location.href = "firstCheck.html?outpatientId="+outpatientId;
			 }
		 }
	 }
 });
 
 /**
  * 点击跳过
  */
 $(document).on("click","#jumperOver",function(){
	 window.location.href = "sportSurvey.html";
 });
 