/**
 * 用户查看食谱js
 */
 var userId = GetQueryString("userId");
 var hospitalId = GetQueryString("hospitalId");
 function listRecipes(list){
	 	var html = "";
		var breakfast = "",breakfastAdd = "", lunch = "", lunchAdd = "",dinner = "", dinnerAdd = "";
		if(list.length>0){
			for(var i=0;i<list.length;i++){
	   	    	var info = list[i];
	   	    	var remark = "";
	    		if(info.unitRemark != null && info.unitRemark != ""){
	   	 			remark = "【"+info.unitRemark+"】";
	   	 		}else{
	   	 			remark = "";
	   	 		}
	    		if(info.mealsType==1){
	    			breakfast += "<li class='mui-table-view-cell mui-media'><img class='mui-media-object mui-pull-left' src='"+info.img+"'><div class='mui-media-body'>"+info.foodName+"<p class='mui-ellipsis'>"+info.foodWeight+"克 <span>"+remark+"</span></p></div></li>";
	    		}
	    		if(info.mealsType==2){
	    			breakfastAdd += "<li class='mui-table-view-cell mui-media'><img class='mui-media-object mui-pull-left' src='"+info.img+"'><div class='mui-media-body'>"+info.foodName+"<p class='mui-ellipsis'>"+info.foodWeight+"克 <span>"+remark+"</span></p></div></li>";
	    		}
	    		if(info.mealsType==3){
	    			lunch += "<li class='mui-table-view-cell mui-media'><img class='mui-media-object mui-pull-left' src='"+info.img+"'><div class='mui-media-body'>"+info.foodName+"<p class='mui-ellipsis'>"+info.foodWeight+"克 <span>"+remark+"</span></p></div></li>";
	    		}
	    		if(info.mealsType==4){
	    			lunchAdd += "<li class='mui-table-view-cell mui-media'><img class='mui-media-object mui-pull-left' src='"+info.img+"'><div class='mui-media-body'>"+info.foodName+"<p class='mui-ellipsis'>"+info.foodWeight+"克 <span>"+remark+"</span></p></div></li>";
	    		}
	    		if(info.mealsType==5){
	    			dinner += "<li class='mui-table-view-cell mui-media'><img class='mui-media-object mui-pull-left' src='"+info.img+"'><div class='mui-media-body'>"+info.foodName+"<p class='mui-ellipsis'>"+info.foodWeight+"克 <span>"+remark+"</span></p></div></li>";
	    		}
	    		if(info.mealsType==6){
	    			dinnerAdd += "<li class='mui-table-view-cell mui-media'><img class='mui-media-object mui-pull-left' src='"+info.img+"'><div class='mui-media-body'>"+info.foodName+"<p class='mui-ellipsis'>"+info.foodWeight+"克 <span>"+remark+"</span></p></div></li>";
	    		}
	   	    }
		   	if(breakfast != ""){
	   	    	breakfast = "<div class='pTitle'>早餐</div><ul class='mui-table-view'>"+breakfast+"</ul>";
	   	    }
	   	    if(breakfastAdd != ""){
	   	    	breakfastAdd = "<div class='pTitle'>早点</div><ul class='mui-table-view'>"+breakfastAdd+"</ul>";
	   	    }
	   	    if(lunch != ""){
	   	    	lunch = "<div class='pTitle'>午餐</div><ul class='mui-table-view'>"+lunch+"</ul>";
	   	    }	
	   	    if(lunchAdd != ""){
	   	    	lunchAdd = "<div class='pTitle'>午点</div><ul class='mui-table-view'>"+lunchAdd+"</ul>";
	   	    }
	   	    if(dinner != ""){
	   	    	dinner = "<div class='pTitle'>晚餐</div><ul class='mui-table-view'>"+dinner+"</ul>";
	   	    }
	   	    if(dinnerAdd != ""){
	   	    	dinnerAdd = "<div class='pTitle'>晚点</div><ul class='mui-table-view'>"+dinnerAdd+"</ul>";
	   	    }
		   	html += breakfast + breakfastAdd + lunch + lunchAdd + dinner + dinnerAdd;
	   	    $("#contentBox").html(html);
		}else{
			$("#contentBox").css("display","none");
			$("#contentBox_recipes_null").css("display","block");
			$(".dietAdvice").css("display","none");
		}
 }
 
 $(document).ready(function(){
    	var param = {userId:userId,hospitalId:hospitalId};
    	$.ajax({
    		url:basePath+"/userRecipes/findUserRecipesList",
    		type:"POST",
    		dataType:"json",
    		data:param,
    		success:function(json){
    			if(json.msg == 1){
    				var data = json.data.recipesList;
    				//食谱名称列表
    				var nameHTML = "";
    				for(var i=0;i<data.length;i++){
    					nameHTML += "<li recipes-id="+data[i].recipesId+"><a href='javascript:void(0);'>"+data[i].recipesName+"</a></li>";
    				}
    				$("#inner").html(nameHTML);
    				
    				//食谱内容列表(展示第一个食谱)
    				var list = json.data.recipesList[0].detailList;
    				var dietAdvice = json.data.dietAdvice;
    				if(dietAdvice != null && dietAdvice != ""){
    					$(".dietAdvice").css("display","block");
    					$("#dietAdvice").text(dietAdvice);
    					$("#advice").val(dietAdvice);
    				}
    				listRecipes(list);
    				$("#inner li:first").addClass("active");
    			}else if(json.msg == 10){
    				$("#outer").css("display","none");
    				$("#contentBox").css("display","none");
    				$("#contentBox_null").css("display","block");
    			}
    		}
    	});
    });

 /**
  * 切换食谱选项卡
  */
 mui("#outer").on('tap', '#inner li', function () {
	 $("#contentBox").css("display","block");
	 $("#contentBox_recipes_null").css("display","none");
	 if($("#advice").val()!=""){
		 $(".dietAdvice").css("display","block");
	 }
     $(this).addClass('active').siblings().removeClass('active');
     var recipesId = $(this).attr("recipes-id");
     $.ajax({
 		url:basePath+"/userRecipes/findUserRecipesById",
 		type:"POST",
 		dataType:"json",
 		data:{recipesId:recipesId},
 		success:function(json){
 			if(json.msg == 1){
 				var list = json.data.detailList;
 				listRecipes(list);
 			}
 		}
 	});
 });
 