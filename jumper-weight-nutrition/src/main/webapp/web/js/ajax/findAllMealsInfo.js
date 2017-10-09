/**
 * 获取全部饮食记录js
 */
var userId = GetQueryString("userId");
var menuType = GetQueryString("menuType");

/**
 * 初始化页面数据
 */
//请求总页数
var totalPage = 0;
var curr = 1;
$(function(){
	isShowAdvice();
	var startTime = "";
	var endTime = "";
	getPage(startTime, endTime, 1);
});

//显示分页
function getPage(startTime,endTime,currIndex){
	$.ajax({
		url:basePath+"/diet/findAllUserMealsInfoList",
		type:"post",
		dataType:"json",
		data:{"userId":userId, "pageIndex":1, "pageSize":CONST.pageSize, "startTime":startTime, "endTime":endTime},
		async:false,
		success:function(json){
			totalPage = json.data.pages;
		}
	});
	layui.use(["laypage"], function() {
		layui.laypage({
		    cont: "page",
		    pages: totalPage,
		    curr: currIndex,
		   	skip: true,
		   	jump: function(obj) {
		   	 	//得到了当前页，用于向服务端请求对应数据
		   	    curr = obj.curr;
			   	var param = {
		    		"userId":userId, 
		    		"pageIndex":curr, 
		    		"pageSize":CONST.pageSize, 
		    		"startTime":startTime, 
		    		"endTime":endTime	
				   };
			   	 $.ajax({
			 		url:basePath+"/diet/findAllUserMealsInfoList",
			 		type:"post",
			 		dataType:"json",
			 		data: param,
			 		async:false,
			 		success:function(json){
			 			if(json.msg == 1){
			 				var list = json.data.list;
				 			var html = "";
					   	    for(var i=0;i<list.length;i++){
					   	    	var breakfast = "",breakfastAdd = "", lunch = "", lunchAdd = "",dinner = "", dinnerAdd = "";
					   	    	var eatDate = list[i].eatDate.substring(0,10);
					   	    	var week= dateToWeek(eatDate);
					   	    	var info = list[i].infoList;
					   	    	var remark = "";
					   	    	for(var j=0;j<info.length;j++){
					   	    		if(info[j].unitRemark != null && info[j].unitRemark != ""){
						   	 			remark = "【"+info[j].unitRemark+"】";
						   	 		}else{
						   	 			remark = "";
						   	 		}
					   	    		if(info[j].mealsType==1){
					   	    			breakfast += "<span>"+info[j].foodName+info[j].foodWeight+"克"+remark+"</span>";
					   	    		}
					   	    		if(info[j].mealsType==2){
					   	    			breakfastAdd += "<span>"+info[j].foodName+info[j].foodWeight+"克"+remark+"</span>";
					   	    		}
					   	    		if(info[j].mealsType==3){
					   	    			lunch += "<span>"+info[j].foodName+info[j].foodWeight+"克"+remark+"</span>";
					   	    		}
					   	    		if(info[j].mealsType==4){
					   	    			lunchAdd += "<span>"+info[j].foodName+info[j].foodWeight+"克"+remark+"</span>";
					   	    		}
					   	    		if(info[j].mealsType==5){
					   	    			dinner += "<span>"+info[j].foodName+info[j].foodWeight+"克"+remark+"</span>";
					   	    		}
					   	    		if(info[j].mealsType==6){
					   	    			dinnerAdd += "<span>"+info[j].foodName+info[j].foodWeight+"克"+remark+"</span>";
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
							   	html += "<div class='panel'>"+
							   			"<ul class='tabnav2'>"+
							   				"<li class='active'><a href='#'>"+eatDate+" "+week+"</a></li>"+
							   			"</ul>"+
							   			"<div class='tabstyle2'>"+
							   				"<table id='styletable1' class='tablerec' style='margin-top: 0;'>"+
							   					breakfast + breakfastAdd + lunch + lunchAdd + dinner + dinnerAdd +
											"</table>"+
										"</div>"+
										"</div>";
					   	    }
					   	    $("#mealsInfoList").html(html);
			 			}else if(json.msg == 10){
			 				$("#mealsInfoList").html("");
			 			}
			 		}
			 	});
	   		}
	  	});
	});
}


//点击查询按钮，按时间搜索
$(document).on("click","#searchBtn",function(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	$("#allMealsInfoBtn").removeClass("active");
	$("#thirtyDaysBtn").removeClass("active");
	getPage(startTime, endTime, 1);
});

//全部 
$(document).on("click","#allMealsInfoBtn",function(){
	var startTime = "";
	var endTime = "";
	$("#startTime").val("");
	$("#endTime").val("");
	$("#allMealsInfoBtn").addClass("active");
	$("#thirtyDaysBtn").removeClass("active");
	getPage(startTime, endTime, 1);
});

//30天内 
$(document).on("click","#thirtyDaysBtn",function(){
	var startTime = getDateByDaysLate(new Date(),-30);
	var endTime = formatDate(new Date());
	$("#startTime").val("");
	$("#endTime").val("");
	$("#thirtyDaysBtn").addClass("active");
	$("#allMealsInfoBtn").removeClass("active");
	getPage(startTime, endTime, 1);
});