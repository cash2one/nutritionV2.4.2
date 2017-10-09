/**
 * 目前方案js
 */
var outpatientId = GetQueryString("outpatientId");
var userId = GetQueryString("userId");
var menuType = GetQueryString("menuType");
if(outpatientId == null || outpatientId == "" || outpatientId == "null"){
	outpatientId = 0;
}
/**
 * 初始化页面数据
 */
$(function(){
	isShowAdvice();
	var param = {
		hospitalId:hospitalId,
		userId:userId,
		outpatientId:outpatientId	
	};
	//获取用户食谱列表
	$.ajax({
		url:basePath+"/userRecipes/findUserRecipesPlans",
		type:"post",
		dataType:"json",
		data:param,
		async:false,
		success:function(json){
			if(json.msg == 1){
				var list = json.data.planList;
				var html = "";
				for(var i=0;i<list.length;i++){
					html += "<li><a class='userRecipes' recipes-id='"+list[i].id+"'>"+list[i].recipesName+"</a></li>";
				}
				$("#userRecipesPlan").html(html);
				$("#userRecipesPlan").find("li:first").addClass("active");
				//获取第一份食谱的数据并展示
				getUserRecipesPlanById(list[0].id);
				//给饮食建议和医生建议赋值
				var dietAdvice = json.data.dietAdvice;
				$("#dietAdvice").text(dietAdvice);
			}else if(json.msg==10){
//				$("#modifyPlan").css("display","none");
				$("#userRecipesPlan").text("暂无方案");
			}
		}
	});
	
});

//根据时间查询饮食记录数据
function getUserRecipesPlanById(id){
	var html = "";
	$.ajax({
		url:basePath+"/userRecipes/findUserRecipesPlansById",
		type:"post",
		dataType:"json",
		data:{id:id},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var recipesMsg = json.data.recipesMsg;
				var list = "";
				if(recipesMsg != null && recipesMsg != "" && recipesMsg != "undefined"){
					list = eval("("+recipesMsg+")");
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
				}else{
					$("#styletable").html("<tr><td colspan='2'>暂无记录</td></tr>");
				}
			}else if(json.msg == 10){
				$("#styletable").html("<tr><td colspan='2'>暂无记录</td></tr>");
			}
			$("#styletable").find("tr:even").addClass("oddrowcolor");
	   	    $("#styletable").find("tr:odd").addClass("evenrowcolor");
		}
	});
}

//切换方案食谱选项卡
$(document).on("click",".userRecipes",function(){
	$(this).parent("li").addClass("active").siblings().removeClass("active");
	var id = $(this).attr("recipes-id");
	getUserRecipesPlanById(id);
});

/**
 * 点击修改方案，如果本次门诊有方案，直接导入，没有方案就另存一份
 */
$(document).on("click","#modifyPlan",function(){
	var param = {
			hospitalId:hospitalId,
			userId:userId,
			outpatientId:outpatientId	
		};
	$.ajax({
		url:basePath+"/userRecipes/saveAsOrFindUserRecipesPlans",
		type:"post",
		dataType:"json",
		data:param,
		async:false,
		success:function(json){
			if(json.msg == 1 || json.msg == 10){
				//另存成功以后跳转
				window.location.href="modifyPlan.html?userId="+userId+"&outpatientId="+outpatientId;
			}
		}
	});
});

