/**
 * 方案制定js
 */
var outpatientId = GetQueryString("outpatientId");
var userId = GetQueryString("userId");
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

var flag = "";
//保存当前页食谱信息
function saveStandardRecipes(){
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
		 if(JSON.stringify(dietMsg) != "[]"){
			 var recipesMsg = "";
			 if(JSON.stringify(dietMsg) != "[]"){
				 recipesMsg = JSON.stringify(dietMsg);
			 }
			 var intakeCalorie = $("#energyIntake").text();
			 intakeCalorie = intakeCalorie==""?0:parseInt(intakeCalorie);
			 if(recipesId == null || recipesId=="" || recipesId == 0){
//				 var recipesName = $(".layui-this").find(".tabelTitleN").text();
				 var recipesName = $(".layui-this").find(".tabelTitleN").attr("title");
				 var param = {
						id:recipesId,
						userId:userId,
						hospitalId:hospitalId,
						outpatientId:outpatientId,
						recipesName:recipesName,
				 		recipesMsg:recipesMsg,
				 		intakeCalorie:intakeCalorie
				 }
			 }else{
				 var param = {
			 			id:recipesId,
			 			recipesMsg:recipesMsg,
			 			intakeCalorie:intakeCalorie
				 	};
			 }
			$.ajax({
		 		url: basePath+"/userRecipes/saveUserRecipesPlan",
		 		type: "post",
		 		contentType: "application/json;charset=utf-8",
		 		dataType: "json",
		 		data: JSON.stringify(param),
		 		async:false,
		 		success:function(json){
		 			if(json.msg == 1){
		 				flag = "success";
		 				if(recipesId == null || recipesId=="" || recipesId == 0){
		 					$("#recipesOptions").find("li:first").attr("recipes-id",json.data.id);
		 					$("#recipesOptions").find("li:first").find(".changeRecipesName").attr("recipes-id",json.data.id);
		 				}
		 			}else{
		 				flag = "fail";
		 				layer.msg("保存失败，请稍后重试！",{time:1000});
		 			}
		 		}
			});
		 }else{
			 flag = "fail";
			 layer.msg("食谱不能为空",{time:1000});
		 }
		
}

//初始化页面数据
$(function(){
//	myBrowser();
	//获取已有方案列表
	$.ajax({
		url:basePath+"/plan/findStandardRecipesPlans",
		type:"POST",
		dataType:"json",
		data:{hospitalId:hospitalId},
		async:false,
		success:function(json){
			if(json.msg==1){
				var data = json.data;
				var html = "";
				for(var i=0;i<data.length;i++){
					html += "<li>"+
                                "<div class='daoruListFirst' title='"+data[i].name+"'>"+data[i].name+"</div>"+
                                "<div class='daoruBtn importPlan' plan-id='"+data[i].id+"'>导入</div>"+
                            "</li>";
				}
				$("#planList").html(html);
			}
		}
	});
	//获取用户食谱列表，没有则默认给一个食谱一
	$.ajax({
		url:basePath+"/userRecipes/findUserRecipesPlansByOutPatientId",
		type:"POST",
		dataType:"json",
		data:{outpatientId:outpatientId},
		async:false,
		success:function(json){
			if(json.msg==1){
				var data = json.data.planList;
				var recipesMsg = data[0].recipesMsg;//默认显示第一套食谱
				listRecipesMsg(recipesMsg);
				$("#recipesId").val(data[0].id);
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
					var recipesName = data[i].recipesName;
					if(recipesName.length>5){
						recipesName = recipesName.substring(0,5)+"...";
					}
					recipesOptions += "<li class='lookRecipesOption' recipes-id='"+data[i].id+"'>"+
                                       "<span class='tabelTitleN' title='"+data[i].recipesName+"'>"+recipesName+"</span>"+
                                       changeName+deleteRecipes+
                                       "</li>";
				}
				$("#recipesOptions").html(recipesOptions);
				//默认选中第一个
				setTimeout(function(){
					$("#recipesOptions").find("li:first").addClass("layui-this");
				},50);
				//给饮食建议和医生建议赋值
				var dietAdvice = json.data.dietAdvice;
				var doctorAdvice = json.data.doctorAdvice;
				$("#dietAdvice").val(dietAdvice);
				$("#doctorAdvice").val(doctorAdvice);
				if(dietAdvice != null && dietAdvice != "" && dietAdvice != "undefined"){
					var length1 = dietAdvice.length;
					$("#counter1").text(length1);
				}
				if(doctorAdvice != null && doctorAdvice != "" && doctorAdvice != "undefined"){
					var length2 = doctorAdvice.length;
					$("#counter2").text(length2);
				}
			}else if(json.msg==10){
				//默认给食谱一
				defaultRecipes();
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
		                    "<div class='daoruListFirst'>"+data[i].name+"</div>"+
		                    "<div class='daoruBtn importPlan' plan-id='"+data[i].id+"'>导入</div>"+
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
//		$("#recipesList").append(html);
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

//导入方案方法
function importPlan($this){
	var planId = $this.attr("plan-id");
  	 var param = {
  			hospitalId:hospitalId,
			userId:userId,
			outpatientId:outpatientId,
			planId:planId
	};
  	$.ajax({
		url:basePath+"/userRecipes/importUserRecipesPlans",
		type:"POST",
		dataType:"json",
		data:param,
		success:function(json){
			if(json.msg==1){
				var data = json.data.planList;
				var recipesMsg = data[0].recipesMsg;//默认显示第一套食谱
				listRecipesMsg(recipesMsg);
				$("#recipesId").val(data[0].id);
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
					var recipesName = data[i].recipesName;
					if(recipesName.length>5){
						recipesName = recipesName.substring(0,5)+"...";
					}
					recipesOptions += "<li class='lookRecipesOption' recipes-id='"+data[i].id+"'>"+
                                      "<span class='tabelTitleN' title='"+data[i].recipesName+"'>"+recipesName+"</span>"+
                                      changeName+deleteRecipes+
                                      "</li>";
				}
				$("#recipesOptions").html(recipesOptions);
				//默认选中第一个
				setTimeout(function(){
					$("#recipesOptions").find("li:first").addClass("layui-this");
				},50);
				var dietAdvice = json.data.dietAdvice;
				if(dietAdvice == null || dietAdvice == "" || typeof(dietAdvice) == "undefined"){
					dietAdvice = "";
				}
				$("#dietAdvice").val(dietAdvice);
				var length = dietAdvice.length;
				$("#counter1").text(length);
			}else if(json.msg==10){
				//默认给食谱一
				defaultRecipes();
			}
		}
	});
}

/**
 * 导入方案(如果初次进来一个食谱都没有，点击导入不提示)
 */
$(document).on("click",".importPlan",function(){
	var $this = $(this);
	var recipesId = $("#recipesOptions").find("li:first").attr("recipes-id");
	if(recipesId == 0){
		importPlan($this);
	}else{
		layer.alert('<span style="margin-left:30px;">确认导入方案？</span><br>已经添加好的食物将被替换', {
	        icon: 0,
	        title: '提示',
	        btn:['确认'],
	        btnAlign: 'c',
	        yes: function(index, layero){
	        	layer.close(index);
	        	importPlan($this);
	        }
	    });
	}
});

 /**
  * 删除食谱中的某一个食谱记录（页面上移除）
  */
 $(document).on("click",".delete-meals-food",function(){
	 var $this = $(this);
	 $this.parent().parent().remove();
	 //实时统计食材个数
	 var count = countFood();
	 $("#countFood").text(count);
 });
 
 //重命名食谱
 $(document).on("click",".changeRecipesName",function(){
	 var $this = $(this);
//	 var oldName = $this.siblings('.tabelTitleN').text();
	 var oldName = $this.siblings('.tabelTitleN').attr("title");
	 var newName = "";
	 layer.alert('内容', {
	        title: '修改名称',
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
	        		$this.siblings('.tabelTitleN').attr("title", $(".laNameTabel").val());
		            layer.close(index);
		            //保存进数据库
		            var recipesId = $this.attr("recipes-id"); 
		        	var param = {
		        			id:recipesId,
		        			recipesName:newName
		        	};
		        	$.ajax({
		        		url: basePath+"/userRecipes/saveUserRecipesPlan",
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
					        			userId:userId,
					        			hospitalId:hospitalId,
					        			outpatientId:outpatientId,
					        			recipesName:recipesName
					        	};
					        	$.ajax({
					        		url: basePath+"/userRecipes/saveUserRecipesPlan",
					        		type: "post",
					        		contentType: "application/json;charset=utf-8",
					        		dataType: "json",
					        		data: JSON.stringify(param),
					        		success:function(json){
					        			if(json.msg == 1){
					        				var recipesName = json.data.recipesName;
					    					if(recipesName.length>5){
					    						recipesName = recipesName.substring(0,5)+"...";
					    					}
					        				 //添加选项卡
			        				        $(".layui-tab-title").append(
			        				            '<li class="lookRecipesOption" recipes-id="'+json.data.id+'"><span class="tabelTitleN" title="'+json.data.recipesName+'">'+recipesName+'</span>'
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
		 		url: basePath+"/userRecipes/findUserRecipesPlansById",
		 		type: "post",
		 		dataType: "json",
		 		data: {id:recipesId},
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
         		url: basePath+"/userRecipes/deleteUserRecipesPlan",
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
         				 		url: basePath+"/userRecipes/findUserRecipesPlansById",
         				 		type: "post",
         				 		dataType: "json",
         				 		data: {id:recipesId},
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
 
 //点击返回（保存数据，返回上一页）
 $(document).on("click","#goBack",function(){
	 saveStandardRecipes();
	 setTimeout(function(){
		 if(flag == "success" || flag == ""){
			 //保存饮食建议和医生建议
			 //饮食建议
			 var dietAdvice = $("#dietAdvice").val();
			 //医生建议
			 var doctorAdvice = $("#doctorAdvice").val();
			 $.ajax({
	         		url: basePath+"/outpatient/updateOutpatient",
	         		type: "POST",
	         		dataType: "json",
	         		data: {outpatientId:outpatientId,isFinish:0,dietAdvice:dietAdvice,doctorAdvice:doctorAdvice},
	         		async:false,
	         		success:function(json){
	         			if(json.msg == 1){
	         				//返回上一页
	         				 window.history.go(-1);
	         			}else{
	         				layer.msg(json.msgbox,{time:1000});
	         			}
	         		}
	         	});
		 }
	 });
 });
 //点击完成诊断
 $(document).on("click","#completeProfile",function(){
	 saveStandardRecipes();
	 setTimeout(function(){
		 if(flag == "success" || flag == ""){
			//保存饮食建议和医生建议
			 //饮食建议
			 var dietAdvice = $("#dietAdvice").val();
			 //医生建议
			 var doctorAdvice = $("#doctorAdvice").val();
			 $.ajax({
	         		url: basePath+"/outpatient/updateOutpatient",
	         		type: "POST",
	         		dataType: "json",
	         		data: {outpatientId:outpatientId,isFinish:1,dietAdvice:dietAdvice,doctorAdvice:doctorAdvice},
	         		async:false,
	         		success:function(json){
	         			if(json.msg == 1){
	         				//查询该门诊id是否已经生成报告，已经生成报告就直接跳转报告页面，否则的话生成报告
	         				$.ajax({
	        	         		url: basePath+"/report/findUserWeightReportByOutpId",
	        	         		type: "POST",
	        	         		dataType: "json",
	        	         		data: {outpatientId:outpatientId},
	        	         		async:false,
	        	         		success:function(json){
	        	         			if(json.msg == 1){
	        	         				var reportId = json.data.id;
	        	         				window.location.href = "reportView.html?reportId="+reportId + "&backOutpatient=" + CONST.backOutpatient[1];
	        	         			}else{
	        	         				//调用生成报告接口，成功以后跳转到报告页面
	        	         				var param = {
	        	         					userId:userId,
	        	         					hospitalId:hospitalId,
	        	         					outpatientId:outpatientId	
	        	         				}
	        	         				$.ajax({
	        	        	         		url: basePath+"/report/saveUserWeightReport",
	        	        	         		type: "POST",
	        	        	         		dataType: "json",
	        	        	         		contentType: "application/json;charset=utf-8",
	        	        	         		data: JSON.stringify(param),
	        	        	         		async:false,
	        	        	         		success:function(json){
	        	        	         			if(json.msg == 1){
	        	        	         				var reportId = json.data.id;
	        	        	         				window.location.href = "reportView.html?reportId="+reportId + "&backOutpatient=" + CONST.backOutpatient[1];
	        	        	         			}else{
	        	        	         				layer.msg(json.msgbox,{time:1000});
	        	        	         			}
	        	        	         		}
	        	        	         	});
	        	         			}
	        	         		}
	        	         	});
	         			}else{
	         				layer.msg(json.msgbox,{time:1000});
	         			}
	         		}
	         	});
		 }
	 });
 });
 
 
