/**
 * 查看用户信息js
 */
var userId = GetQueryString("userId");
var menuType = GetQueryString("menuType");
/**
 * 初始化页面数据
 */
$(function(){
	isShowAdvice();
	//获取时间选项卡列表
	getUserMealsInfoDate();
	//获取第一个选项卡的数据
	setTimeout(function(){
		var eatDate = $("#userMealsInfo").find("li:first").text().substring(0,10);
		getUserMealsInfoByDate(eatDate);
	},100);
	
});

//获取用户饮食记录日期信息
function getUserMealsInfoDate(){
	var html = "";
	$.ajax({
		url:basePath+"/diet/findUserLatestSevenDays",
		type:"post",
		dataType:"json",
		data:{"userId":userId},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var list = json.data.dateList;
				var isShow = json.data.isShow;
				var foodCount = json.data.foodCount;
				var days = json.data.days;
				for(var i=0;i<list.length;i++){
					var date = list[i];
					var week = dateToWeek(date);
					html += "<li><a href='#' class='userMealsOption'>"+date+" "+week+"</a></li>";
				}
				if(isShow == 1){
//					html += "<li><a href='#' class='lookAllMealsInfo'>查看全部</a></li>";
					$(".lookAllMealsInfo").css("display","block");
				}
				$("#userMealsInfo").html(html);
				$("#userMealsInfo").find("li:first").addClass("active");
				$("#foodCountDays").text(days);
				$("#foodCount").text(foodCount);
			}else if(json.msg==10){
				$("#userMealsInfo").text("暂无饮食记录");
				$(".record-1").css("display","none");
			}
		}
	});
}

//根据时间查询饮食记录数据
function getUserMealsInfoByDate(eatDate){
	var html = "";
	$.ajax({
		url:basePath+"/diet/findUserMealsInfoList",
		type:"post",
		dataType:"json",
		data:{"userId":userId,"startTime":eatDate,"endTime":eatDate},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var list = json.data;
				var html = "";
				var breakfast = "",breakfastAdd = "", lunch = "", lunchAdd = "",dinner = "", dinnerAdd = "";
		   	    for(var i=0;i<list.length;i++){
		   	    	var info = list[i];
		   	    	var remark = "";
	   	    		if(info.unitRemark != null && info.unitRemark != ""){
		   	 			remark = "【"+info.unitRemark+"】";
		   	 		}else{
		   	 			remark = "";
		   	 		}
	   	    		if(info.mealsType==1){
	   	    			breakfast += "<span>"+info.foodName+info.foodWeight+"克"+remark+"</span>";
	   	    		}
	   	    		if(info.mealsType==2){
	   	    			breakfastAdd += "<span>"+info.foodName+info.foodWeight+"克"+remark+"</span>";
	   	    		}
	   	    		if(info.mealsType==3){
	   	    			lunch += "<span>"+info.foodName+info.foodWeight+"克"+remark+"</span>";
	   	    		}
	   	    		if(info.mealsType==4){
	   	    			lunchAdd += "<span>"+info.foodName+info.foodWeight+"克"+remark+"</span>";
	   	    		}
	   	    		if(info.mealsType==5){
	   	    			dinner += "<span>"+info.foodName+info.foodWeight+"克"+remark+"</span>";
	   	    		}
	   	    		if(info.mealsType==6){
	   	    			dinnerAdd += "<span>"+info.foodName+info.foodWeight+"克"+remark+"</span>";
	   	    		}
		   	    }
			   	if(breakfast != ""){
		   	    	breakfast = "<tr><td style='width:80px;'>早餐</td><td>"+breakfast+"</td></tr>";
		   	    }
		   	    if(breakfastAdd != ""){
		   	    	breakfastAdd = "<tr><td style='width:80px;'>早点</td><td>"+breakfastAdd+"</td></tr>";
		   	    }
		   	    if(lunch != ""){
		   	    	lunch = "<tr><td style='width:80px;'>午餐</td><td>"+lunch+"</td></tr>";
		   	    }	
		   	    if(lunchAdd != ""){
		   	    	lunchAdd = "<tr><td style='width:80px;'>午点</td><td>"+lunchAdd+"</td></tr>";
		   	    }
		   	    if(dinner != ""){
		   	    	dinner = "<tr><td style='width:80px;'>晚餐</td><td>"+dinner+"</td></tr>";
		   	    }
		   	    if(dinnerAdd != ""){
		   	    	dinnerAdd = "<tr><td style='width:80px;'>晚点</td><td>"+dinnerAdd+"</td></tr>";
		   	    }
			   	html += breakfast + breakfastAdd + lunch + lunchAdd + dinner + dinnerAdd;
		   	    $("#styletable").html(html);
			}
		}
	});
}

//切换饮食记录选项卡
$(document).on("click",".userMealsOption",function(){
	$(this).parent("li").addClass("active").siblings().removeClass("active");
	var eatDate = $(this).text().substring(0,10);
	getUserMealsInfoByDate(eatDate);
});

//查看全部跳转
$(document).on("click",".lookAllMealsInfo",function(){
	window.location.href = "findAllMealsInfo.html?userId="+userId + "&menuType=" + menuType;
});
