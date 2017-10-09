/**
 * 配置方案js
 */
$(function(){
//	myBrowser();
	//获取已有方案列表
	$.ajax({
		url:basePath+"/plan/findStandardRecipesPlans",
		type:"POST",
		dataType:"json",
		data:{hospitalId:hospitalId},
		success:function(json){
			if(json.msg==1){
				var data = json.data;
				var html = "";
				for(var i=0;i<data.length;i++){
					html += "<li>"+
                                "<div class='daoruListFirst' title='"+data[i].name+"'>"+data[i].name+"</div>"+
                                "<div class='layui-icon editPlan' plan-id='"+data[i].id+"' title='编辑'>&#xe63c;</div>"+
                                "<div class='layui-icon renamePlan' plan-id='"+data[i].id+"' plan-name='"+data[i].name+"' title='重命名'>&#xe642;</div>"+
                                "<div class='layui-icon deletePlan' plan-id='"+data[i].id+"' title='删除'>&#xe640;</div>"+
                            "</li>";
				}
				$("#planList").html(html);
			}
		}
	});
});

/**
 * 搜索方案
 */
$(document).on("click","#searchPlan",function(){
	var keywords = $("#keywords").val();
	var param = {
			hospitalId:hospitalId,
			keywords:keywords
	};
	$.ajax({
		url:basePath+"/plan/findStandardRecipesPlans",
		type:"POST",
		dataType:"json",
		data:param,
		success:function(json){
			if(json.msg==1){
				var data = json.data;
				var html = "";
				for(var i=0;i<data.length;i++){
					html += "<li>"+
                                "<div class='daoruListFirst' title='"+data[i].name+"'>"+data[i].name+"</div>"+
                                "<div class='layui-icon editPlan' plan-id='"+data[i].id+"' title='编辑'>&#xe63c;</div>"+
                                "<div class='layui-icon renamePlan' plan-id='"+data[i].id+"' plan-name='"+data[i].name+"' title='重命名'>&#xe642;</div>"+
                                "<div class='layui-icon deletePlan' plan-id='"+data[i].id+"' title='删除'>&#xe640;</div>"+
                            "</li>";
				}
				$("#planList").html(html);
			}else if(json.msg==10){
				$("#planList").html('<li style="font-size : 14px;">没有符合关键词的结果</li>');
			}
		}
	});
});

var remark = "";
//查找食物自动补全
$("#search-food-input").autocomplete(basePath+"/diet/searchFood",{
	dataType:"json",
	minChars:1,
	max:15,
	autoFill:false,
	matchContains:true,
	width:110,
	scrollHeight:330,
	parse:function(data){
		return $.map(data,function(row){
			return {
				data:row,
				value:row.name,
				result:row.name
			};
		});
	},
	formatItem:function(row,i,max){
		if(row.unitRemark != null && row.unitRemark != ""){
			remark = "【"+row.unitRemark+"】";
		}else{
			remark = "";
		}
		return row.name+remark;
	}
}).result(function(e,row){
	$("#foodWeight").removeAttr("id");
	if(row.unitRemark != null && row.unitRemark != ""){
		remark = "【"+row.unitRemark+"】";
	}else{
		remark = "";
	}
	var meals_type = $(".select-meals-type").find("input:checked").val();
	var meals_name = $(".select-meals-type").find("input:checked").attr("meals-name");
	var html = "<tr><td>"+meals_name+"</td><td>"+row.name+remark+"</td>" +
			   "<td><label><input id='foodWeight' type='text' maxlength='5' class='food-weight' food-id='"+row.id+"' onkeyup='checkNumber(this);'></label>" +
			   "</td><td><a class='btn_blue delete-meals-food' del-type='no-save' meals-type='"+meals_type+"' food-id='"+row.id+"' food-name='"+row.name+"' unit-remark='"+row.unitRemark+"'>删除</a></td></tr>";
	var flag = 0;
	$("a.delete-meals-food").each(function(){
		if($(this).attr("meals-type")==meals_type && $(this).attr("food-id")==row.id){
			flag += 1;
		}
	});
	if(flag == 0){
		/*$("#recipesList").append(html);
		$("#search-food-input").val("");
		setTimeout(function(){
			$(".food-weight:last").focus();
		},10);*/
		if($(".delete-meals-food").length == 0){
			$("#recipesList").append(html);
		}else{
			$(".delete-meals-food").each(function(){
				var mealsType = $(this).attr("meals-type");
				var next = $(this).parent().parent().next("tr").find(".delete-meals-food").attr("meals-type");
				if(meals_type<mealsType){
					$(this).parent().parent().before(html);
					return false;
				}else if(meals_type>=mealsType && meals_type<next){
					$(this).parent().parent().after(html);
					return false;
				}else if(meals_type>=mealsType && next==null){
					$("#recipesList").append(html);
					return false;
				}
			});
		}
		$("#search-food-input").val("");
		setTimeout(function(){
			$("#foodWeight").focus();
		},10);
		//实时统计食材个数
		var count = countFood();
		$("#countFood").text(count);
	}else{
		layer.msg(row.name+"已存在于"+meals_name+"食谱列表中！");
	}
});

//根据食谱json数据展示每个食谱的记录列表
function listRecipesMsg(recipesMsg){
	var html = "";
	var remark = "";
	if(recipesMsg != null && recipesMsg != "undefined" && recipesMsg.length>0){
		var msg = eval("("+recipesMsg+")");
		msg.sort(function(a,b){
			return a.mealsType-b.mealsType;
		});
		for(var i=0;i<msg.length;i++){
			var meals_type = msg[i].mealsType;
			var meals_name = getMealsName(parseInt(meals_type));
			if(msg[i].unitRemark != null && msg[i].unitRemark != ""){
				remark = "【"+msg[i].unitRemark+"】";
			}else{
				remark = "";
			}
			html += "<tr><td>"+meals_name+"</td><td>"+msg[i].foodName+remark+"</td>" +
			   "<td><label><input type='text' maxlength='5' class='food-weight' value='"+msg[i].foodWeight+"' food-id='"+msg[i].foodId+"' onkeyup='checkNumber(this);'></label>" +
			   "</td><td><a class='btn_blue delete-meals-food' del-type='save' meals-type='"+meals_type+"' food-id='"+msg[i].foodId+"' food-name='"+msg[i].foodName+"' unit-remark='"+msg[i].unitRemark+"'>删除</a></td></tr>";
		}
	}
	$("#recipesList").html(html);
	setTimeout(function(){
		//实时统计食材个数
		var count = countFood();
		$("#countFood").text(count);
	},100);
	foodAnalyze();
}
//食谱列表置空，食谱列表选项卡列表给默认食谱一
function defaultRecipes(){
	$("#recipesList").html("");
	var defaultOption = "<li class='layui-this lookRecipesOption' recipes-id='0'>"+
                            "<span class='tabelTitleN' title='食谱一'>食谱一</span>"+
                            "<i class='layui-icon pen changeRecipesName' recipes-id='0'>&#xe642;</i>"+
                        "</li>";
	$("#recipesOptions").html(defaultOption);
	$("#recipesId").val(0);//方案中没有食谱时，将隐藏域食谱id置空
}

/**
 * 编辑方案(导入对应方案的食谱列表)
 */
 $(document).on("click",".editPlan",function(){
	 $("#daoTarget").each(function(){$(this).removeClass("daoruStyle").removeAttr("id");});
	 $(this).siblings(".daoruListFirst").addClass("daoruStyle").attr("id","daoTarget");
 	 $(".btnBottom").css("display","block");
	 $(".rImg").css("display","none");
	 $(".rBox").css("display","block");
	 var planId = $(this).attr("plan-id");
	 $("#planId").val(planId);
	 $.ajax({
			url:basePath+"/plan/findStandardRecipesByPlan",
			type:"POST",
			dataType:"json",
			data:{planId:planId},
			success:function(json){
				if(json.msg==1){
					var data = json.data.recipesList;
					var recipesMsg = data[0].recipesMsg;//默认显示第一套食谱
					listRecipesMsg(recipesMsg);
					$("#recipesId").val(data[0].id);
					var dietAdvice = json.data.dietAdvice;
					$("#dietAdvice").val(dietAdvice);
					if(dietAdvice != null && dietAdvice != "" && dietAdvice != "undefined"){
						var length = dietAdvice.length;
						$("#counter1").text(length);
					}
					//显示食谱选项卡列表
					var recipesOptions = "";
					var changeName = "";
					var deleteRecipes = "";
					for(var i=0;i<data.length;i++){
						if(i==0){//第一个有重命名
							changeName = '<i class="layui-icon pen changeRecipesName" recipes-id='+data[i].id+'>&#xe642;</i>';
							deleteRecipes = "";
						}else{
							changeName = "";
							deleteRecipes = '<i class="layui-icon layui-unselect layui-tab-close removeRecipes" recipes-id="'+data[i].id+'">ဆ</i>';
						}
						var recipesName = data[i].name;
						if(recipesName.length>5){
							recipesName = recipesName.substring(0,5)+"...";
						}
						recipesOptions += "<li class='lookRecipesOption' recipes-id='"+data[i].id+"' recipes-data='"+data[i].recipesMsg+"'>"+
                                           "<span class='tabelTitleN' title='"+data[i].name+"'>"+recipesName+"</span>"+
                                           changeName+deleteRecipes+
                                           "</li>";
					}
					$("#recipesOptions").html(recipesOptions);
					//默认选中第一个
					setTimeout(function(){
						$("#recipesOptions").find("li:first").addClass("layui-this");
					},50);
					setTimeout(function(){
	 					//表格hover样式
		 			     tableHover($(".layui-table tbody").find("tr"),'#fff','#f4f4f4','#DCEFFA');
	 				},500);
				}else if(json.msg == 10){
//					alert("方案中没有食谱");
					defaultRecipes();
				}
			}
		});
 });
 
 /**
  * 删除方案中的某一个食谱记录（页面上移除）
  */
 $(document).on("click",".delete-meals-food",function(){
	 var $this = $(this);
	 $this.parent().parent().remove();
	 setTimeout(function(){
		//实时统计食材个数
		var count = countFood();
		$("#countFood").text(count);
	 },100);
 });
 
 /**
  * 重命名方案
  */
 $(document).on("click",".renamePlan",function(){
	var planId = $(this).attr("plan-id");
	var planName = $(this).attr("plan-name");
	var $this = $(this);
	layer.alert('内容', {
        title: '修改方案名称',
        skin: 'layer-ext-moon',
        content:'<input type="text" placeholder="输入名称" value="'+planName+'" class="laNameTabel"/><br><span class="errorMsg"></span>',
        success:function(layero, index){
        	planName = $(".laNameTabel").val();
        	$(".laNameTabel").focus().val(planName);
        },
        yes: function(index, layero){
        	planName = $(".laNameTabel").val();
        	var reg = /[\^\:\?\*"\>\<\|]+/g;
        	if(planName == ""){
        		$(".errorMsg").text("请输入方案名称");
        		$(".laNameTabel").focus();
        	}else if(reg.test(planName)){
        		$(".errorMsg").text("方案名称不能包含特殊字符");
        		$(".laNameTabel").focus();
        	}else if(planName.length>30){
        		$(".errorMsg").text("方案名称不超过30个字符");
        		$(".laNameTabel").focus();
        	}else{
        		var param = {
            		id:planId,
            		name:planName
            	};
            	param = JSON.stringify(param);
            	$.ajax({
            		url:basePath+"/plan/saveStandardRecipesPlan",
            		type:"POST",
            		contentType: "application/json;charset=utf-8",
            		dataType:"json",
            		data: param,
            		async:false,
            		success:function(json){
            			if(json.msg==1){
            				layer.close(index);
            				$this.siblings('.daoruListFirst').text(planName);
            				$this.siblings('.daoruListFirst').attr("title",planName);
            				$this.attr("plan-name",planName);
            				layer.msg("重命名方案成功！", {time:1000});
            			}else if(json.msg==10){
            				$(".errorMsg").text(json.msgbox);
	    	        		$(".laNameTabel").focus();
            			}else{
            				layer.close(index);
            				layer.msg(json.msgbox, {time:1000});
            			}
            			
            		}
            	});
        	}
        }
    });
 });
 
 
 /**
  * 删除方案
  */
 $(document).on("click",".deletePlan",function(){
	 var planId = $(this).attr("plan-id");
	 var $this = $(this);
	 layer.alert('要删除这个方案么？', {
         icon: 0,
         title: '提示',
         btn:['删除'],
         btnAlign: 'c',
         yes: function(index, layero){
        	 $.ajax({
        			url:basePath+"/plan/deleteStandardRecipesPlan",
        			type:"POST",
        			dataType:"json",
        			data:{planId:planId},
        			success:function(json){
        				if(json.msg==1){
        		             layer.close(index);
        		             layer.msg('操作成功', {time: 1000});
        		             setTimeout(function(){
        		            	 window.location.reload();
        		             },1000);
        				}
        			}
        		});
         }
         
     });
 });
 
 /**
  * 新增方案
  */
 $(document).on("click","#addPlan",function(){
	 layer.alert('内容', {
	        title: '添加方案',
	        skin: 'layer-ext-moon',
	        content:'<input type="text" placeholder="输入名称" value="" class="laNameTabel"/><br><span class="errorMsg"></span>',
	        success:function(layero, index){
	        	$(".laNameTabel").focus();
	        },
	        yes: function(index, layero){
	        	var planName = $(".laNameTabel").val();
	        	var reg = /[\^\:\?\*"\>\<\|]+/g;
	        	if(planName == ""){
	        		$(".errorMsg").text("请输入方案名称");
	        		$(".laNameTabel").focus();
	        	}else if(reg.test(planName)){
	        		$(".errorMsg").text("方案名称不能包含特殊字符");
	        		$(".laNameTabel").focus();
	        	}else if(planName.length>30){
	        		$(".errorMsg").text("方案名称不超过30个字符");
	        		$(".laNameTabel").focus();
	        	}else{
	        		var param = {
    	        		id:0,
    	        		name:planName,
    	        		hospitalId:hospitalId
    	        	};
    	        	param = JSON.stringify(param);
    	        	$.ajax({
    	        		url:basePath+"/plan/saveStandardRecipesPlan",
    	        		type:"POST",
    	        		contentType: "application/json;charset=utf-8",
    	        		dataType:"json",
    	        		data: param,
    	        		success:function(json){
    	        			if(json.msg==1){
    	        				layer.close(index);
    	        				layer.msg("新增方案成功！", {time:1000});
    	        				var plan = json.data.plan;
    	        				$("#daoTarget").each(function(){$(this).removeClass("daoruStyle").removeAttr("id");});
    	        				var html = "<li>"+
    			                            "<div class='daoruListFirst daoruStyle' id='daoTarget' title='"+plan.name+"'>"+plan.name+"</div>"+
    			                            "<div class='layui-icon editPlan' plan-id='"+plan.id+"' title='编辑'>&#xe63c;</div>"+
    			                            "<div class='layui-icon renamePlan' plan-id='"+plan.id+"' plan-name='"+plan.name+"' title='重命名'>&#xe642;</div>"+
    			                            "<div class='layui-icon deletePlan' plan-id='"+plan.id+"' title='删除'>&#xe640;</div>"+
    		                            "</li>";
    	        				$("#planList").append(html);
    	        				$("#planId").val(plan.id);
    	        				var recipes = json.data.recipes;
    	        				$("#recipesList").html("");
    	        				$("#dietAdvice").val("");
    	        				$("#counter1").text("0");
    	        				var recipesName = recipes.name;
    	    					if(recipesName.length>5){
    	    						recipesName = recipesName.substring(0,5)+"...";
    	    					}
    	        				var defaultOption = "<li class='layui-this lookRecipesOption' recipes-id='"+recipes.id+"'>"+
    	        			                            "<span class='tabelTitleN' title='"+recipes.name+"'>"+recipesName+"</span>"+
    	        			                            "<i class='layui-icon pen changeRecipesName' recipes-id='"+recipes.id+"'>&#xe642;</i>"+
    	        			                        "</li>";
    	        				$("#recipesOptions").html(defaultOption);
    	        				$("#recipesId").val(recipes.id);
    	        				$(".rImg").css("display","none");
    	        				$(".rBox").css("display","block");
    	        				$(".btnBottom").css("display","block");
    	        			}else if(json.msg==10){
    	        				$(".errorMsg").text(json.msgbox);
    	    	        		$(".laNameTabel").focus();
    	        			}else{
    	        				layer.close(index);
    	        				layer.msg(json.msgbox, {time:1000});
    	        			}
    	        		}
    	        	});
	        	}
	        }
	    });
 });
 
 //重命名食谱
 $(document).on("click",".changeRecipesName",function(){
	 var $this = $(this);
//	 var oldName = $this.siblings('.tabelTitleN').text();
	 var oldName = $this.siblings('.tabelTitleN').attr("title");
	 var newName = "";
	 layer.alert('内容', {
	        title: '修改食谱名称',
	        skin: 'layer-ext-moon',
	        content:'<input type="text" placeholder="输入名称" value="'+oldName+'" class="laNameTabel"/><br><span class="errorMsg"></span>',
	        success:function(layero, index){
	        	newName = $(".laNameTabel").val();
	        	$(".laNameTabel").focus().val(newName);
	        },
	        yes: function(index, layero){
	        	newName = $(".laNameTabel").val();
	        	var reg = /[\^\:\?\*"\>\<\|]+/g;
	        	if(newName == ""){
	        		$(".errorMsg").text("请输入食谱名称");
	        		$(".laNameTabel").focus();
	        	}else if(reg.test(newName)){
	        		$(".errorMsg").text("食谱名称不能包含特殊字符");
	        		$(".laNameTabel").focus();
	        	}else if(newName.length>15){
	        		$(".errorMsg").text("食谱名称不超过15个字符");
	        		$(".laNameTabel").focus();
	        	}else{
	        		var recipesName = $(".laNameTabel").val();
					if(recipesName.length>5){
						recipesName = recipesName.substring(0,5)+"...";
					}
	        		$this.siblings('.tabelTitleN').text(recipesName);
	        		$this.siblings('.tabelTitleN').attr("title",$(".laNameTabel").val());
		            layer.close(index);
		            //保存进数据库
		            var recipesId = $this.attr("recipes-id"); 
		            var planId = $("#planId").val();
		        	var param = {
		        			id:recipesId,
		        			name:newName,
		        			recipesPlanId:planId
		        	};
		        	$.ajax({
		        		url: basePath+"/plan/saveStandardRecipesDetail",
		        		type: "post",
		        		contentType: "application/json;charset=utf-8",
		        		dataType: "json",
		        		data: JSON.stringify(param),
		        		success:function(json){
		        			if(json.msg == 1){
		        				$this.attr("recipes-id",json.data.id); 
		        				$("#recipesId").val(json.data.id);
		        				layer.msg("重命名成功！", {time:1000});
		        			}
		        		}
		        	});
	        	}
	        }
	    });
 });
 var flag = "";
 //保存当前页食谱信息
 function saveStandardRecipes(){
	 var planId = $("#planId").val();
	 var recipesId = $("#recipesId").val();
	 var dietMsg = new Array();
	 $("#recipesList").find(".delete-meals-food").each(function(){
			var mealsType = $(this).attr("meals-type");
			var foodId = $(this).attr("food-id");
			var foodName = $(this).attr("food-name");
			var foodWeight = $(this).parent().siblings("td").find(".food-weight").val();
			var unitRemark = $(this).attr("unit-remark");
			if(unitRemark == "" || unitRemark == "undefined"){
				unitRemark = "";
			}
			var msg = {
					foodId:foodId,
					foodName:foodName,
					foodWeight:foodWeight,
					mealsType:mealsType,
					unitRemark:unitRemark
				};
			if(foodWeight != null && foodWeight != "" && foodWeight != 0){
				dietMsg.push(msg);
			}
		});
	 	var intakeCalorie = $("#energyIntake").text();
	 	intakeCalorie = intakeCalorie==""?0:parseInt(intakeCalorie);
		 if(JSON.stringify(dietMsg) != "[]"){
	 	var recipesMsg = "";
		 if(JSON.stringify(dietMsg) != "[]"){
			 recipesMsg = JSON.stringify(dietMsg);
		 }
			 var param = {
		 			id:recipesId,
		 			recipesPlanId:planId,
		 			recipesMsg:recipesMsg,
		 			intakeCalorie:intakeCalorie
		 	};
			$.ajax({
		 		url: basePath+"/plan/saveStandardRecipesDetail",
		 		type: "post",
		 		contentType: "application/json;charset=utf-8",
		 		dataType: "json",
		 		data: JSON.stringify(param),
		 		async:false,
		 		success:function(json){
		 			if(json.msg == 1){
		 				flag = "success";
		 			}else{
		 				flag = "fail";
		 				layer.msg("保存失败，请稍后重试！",{time:1000});
		 			}
		 		}
			});
		 }else{
			 flag = "fail";
			 layer.msg("方案不能为空",{time:1000});
		 }
 }
 
//新增食谱（先保存当前页，再新增一个（弹出框）空的，保存进数据库）
 $(document).on("click","#addRecipes",function(){
	 //先保存
	 saveStandardRecipes();
	 setTimeout(function(){
		 if(flag == "success" || flag == ""){
			 if($(".layui-tab-title li").length < 7){
				 layer.alert('内容', {
				        title: '新增食谱',
				        skin: 'layer-ext-moon',
				        content:'<input type="text" placeholder="输入名称" value="" class="laNameTabel"/><br><span class="errorMsg"></span>',
				        success:function(layero, index){
				        	$(".laNameTabel").focus();
				        },
				        yes: function(index, layero){
				        	var recipesName = $(".laNameTabel").val();
				        	var reg = /[\^\:\?\*"\>\<\|]+/g;
				        	if(recipesName == ""){
				        		$(".errorMsg").text("请输入食谱名称");
				        		$(".laNameTabel").focus();
				        	}else if(reg.test(recipesName)){
				        		$(".errorMsg").text("食谱名称不能包含特殊字符");
				        		$(".laNameTabel").focus();
				        	}else if(recipesName.length>15){
				        		$(".errorMsg").text("食谱名称不超过15个字符");
				        		$(".laNameTabel").focus();
				        	}else{
				        		layer.close(index);
					            //保存进数据库
					            var planId = $("#planId").val();
					        	var param = {
					        			id:0,
					        			name:recipesName,
					        			recipesPlanId:planId
					        	};
					        	$.ajax({
					        		url: basePath+"/plan/saveStandardRecipesDetail",
					        		type: "post",
					        		contentType: "application/json;charset=utf-8",
					        		dataType: "json",
					        		data: JSON.stringify(param),
					        		success:function(json){
					        			if(json.msg == 1){
					        				 //添加选项卡
					        				var recipesName = json.data.name;
					    					if(recipesName.length>5){
					    						recipesName = recipesName.substring(0,5)+"...";
					    					}
			        				        $(".layui-tab-title").append(
			        				            '<li class="lookRecipesOption" recipes-id="'+json.data.id+'"><span class="tabelTitleN" title="'+json.data.name+'">'+recipesName+'</span>'
			        				            +'<i class="layui-icon layui-unselect layui-tab-close removeRecipes" recipes-id="'+json.data.id+'">ဆ</i></li>'
			        				        );
			        				        //添加tab选项 acive自动移到新建的tab上~
			        				        var tabTitle=$(".layui-tab-title li");
			        				        tabTitle.eq(tabTitle.length-1).addClass("layui-this").siblings().removeClass("layui-this");
					        				$("#recipesId").val(json.data.id);
					        				$("#recipesList").html("");
					        				$("#countFood").text(0);
					        				foodAnalyze();
					        			}
					        		}
					        	});
				        	}
				        }
				    });
			 }else{
				 layer.msg("最多只能添加7个");
			 }
		 }
	 },100);
 });
 
 
 /**
  * 切换食谱选项卡
  */
 $(document).on("click",".lookRecipesOption",function(){
	 var $this = $(this);
	 //先保存当前页食谱，再展示对应食谱的数据
	 saveStandardRecipes();
	 setTimeout(function(){
		 if(flag == "success" || flag == ""){
			 $this.addClass("layui-this").siblings().removeClass("layui-this");
			 var recipesId = $this.attr("recipes-id");
			 $("#recipesId").val(recipesId);
			 $.ajax({
		 		url: basePath+"/plan/findStandardRecipesById",
		 		type: "post",
		 		dataType: "json",
		 		data: {recipesId:recipesId},
		 		success:function(json){
		 			if(json.msg == 1){
		 				var recipesMsg = json.data.recipesMsg;
		 				listRecipesMsg(recipesMsg);
		 				setTimeout(function(){
		 					//表格hover样式
			 			     tableHover($(".layui-table tbody").find("tr"),'#fff','#f4f4f4','#DCEFFA');
		 				},500);
		 			}
		 		}
		 	});
		 }
	 },100);
 });
 //删除食谱
 $(document).on("click",".removeRecipes",function(event){
	 event.stopPropagation();
	 var $this = $(this);
	 var recipesId = $this.attr("recipes-id");
	 layer.alert('确认删除此食谱记录？', {
         icon: 0,
         title: '提示',
         btn:['删除'],
         btnAlign: 'c',
         yes: function(index, layero){
             layer.close(index);
           //删除数据库中内容
         	$.ajax({
         		url: basePath+"/plan/deleteStandardRecipesDetail",
         		type: "POST",
         		dataType: "json",
         		data: {recipesId:recipesId},
         		async:false,
         		success:function(json){
         			if(json.msg==1){
         				//删除成功以后切换选项卡
         				var _index = $this.parents("li").index();
         			    //移除tab选项
         				$this.parents("li").remove();
         			    //移除tab选项后,active自动往前移动
         			    if($this.parents("li").hasClass("layui-this")){
         			        $(".layui-tab-title li").eq(_index-1).addClass("layui-this");
         			        //需要查询新的时候，查询新的内容进行填充（当前本来就不是高亮显示就不需要重新加载数据了）
         			        var recipesId = $(".layui-this").attr("recipes-id");
         			        $("#recipesId").val(recipesId);
         					$.ajax({
         				 		url: basePath+"/plan/findStandardRecipesById",
         				 		type: "post",
         				 		dataType: "json",
         				 		data: {recipesId:recipesId},
         				 		success:function(json){
         				 			if(json.msg == 1){
         				 				var recipesMsg = json.data.recipesMsg;
         				 				listRecipesMsg(recipesMsg);
         				 			}
         				 		}
         				 	});
         			    }
         			}else{
         				layer.msg("删除失败，请稍后重试！",{time:1000});
         			}
         		}
         	});
         }
     });
 });

 /**
  * 实时统计食物个数
  */
 function countFood(){
 	var foodIdArray = new Array();
 	$("a.delete-meals-food").each(function(){
 		var foodId = $(this).attr("food-id");
 		foodIdArray.push(foodId);
 	});
 	if(foodIdArray.length==0){
 		return 0;
 	}else{
 		foodIdArray.sort(); //先排序
 		var res = [foodIdArray[0]];
 		for(var i = 1; i < foodIdArray.length; i++){
 			if(foodIdArray[i] !== res[res.length - 1]){
 		    res.push(foodIdArray[i]);
 		  }
 		 }
 		 return res.length;
 	}
 }
 
 //保存食谱（点击保存，包括饮食建议）(保存食谱，保存方案)
 $(document).on("click","#savePlanAndRecipes",function(){
	 var planId = $("#planId").val();
	 var recipesId = $("#recipesId").val();
	 var dietAdvice = $("#dietAdvice").val();
	 //保存食谱
	 saveStandardRecipes();
	 setTimeout(function(){
		 if(flag == "success" || flag == ""){
			 var param = {
				id:planId,
				dietAdvice:dietAdvice
			 };
			 param = JSON.stringify(param);
			//保存方案
			 $.ajax({
	        		url:basePath+"/plan/saveStandardRecipesPlan",
	        		type:"POST",
	        		contentType: "application/json;charset=utf-8",
	        		dataType:"json",
	        		data: param,
	        		async:false,
	        		success:function(json){
	        			if(json.msg==1){
	        				layer.msg("保存成功！", {time:1000});
	        			}else{
	        				layer.msg("保存失败，请稍后重试！", {time:1000});
	        			}
	        		}
	        	});
		 }
	 },100);
 });

 
