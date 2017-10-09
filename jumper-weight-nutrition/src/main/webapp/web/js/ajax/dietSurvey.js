/**
 * 膳食调查js
 */
var userId = GetQueryString("userId");
var outpatientId = GetQueryString("outpatientId");
/**
 * 新增调查天数
 */
$('#addDay').click(function () {
	var maxDate = getDateByDaysLate(new Date(),-1);
    layui.laydate({elem: this, festival: true,isclear: false, max:maxDate});
    $("#laydate_table td,#laydate_today").unbind('click').click(function () {
    	setTimeout(function(){
    		addTableTitle_date();
    	},10);
    });
  });
function  addTableTitle_date(){
	var newDate = $("#addDay").val();
    if($(".layui-tab-title li").length < 7 && newDate != ""){
    	$("#addDay").val("");
    	var week = dateToWeek(newDate);
    	var count = checkName(newDate);
        if(count>0){
        	layer.msg("该日期已存在，请重新选择！",{time:1000});
        }else{
        	saveDietSurvey();
        	setTimeout(function(){
        		if(flag == "success"){
        			$("#eatDate").val(newDate);
        			var newWeek = dateToWeek(newDate);
    		        $(".layui-tab-title").append(
    		            '<li class="dietSurvey"><span class="tabelTitleN">'+newDate+' '+newWeek+'</span>'
    		            + '<i class="layui-icon layui-unselect layui-tab-close removeUserDietSurvey" dietSurveyId="0">ဆ</i></li>'
    		        );
    		        //添加tab选项 acive自动移到新建的tab上~
    		        var tabTitle=$(".layui-tab-title li");
    		        tabTitle.eq(tabTitle.length-1).addClass("layui-this").siblings().removeClass("layui-this");
        			setTimeout(function(){
        				//先查询该日期是否有用户饮食记录，有就展示，没有就置空
        				getUserMealsInfo(newDate);
        				//getUserDietSurvey(newDate);
        				
        			},100);
        		}
        	},50);
        }
    }else{
    	layer.msg("最多只能调查7天");
    }
}
/**
 * 获取用户某天的饮食记录
 */
function getUserDietSurvey(id){
	var param = {
			id:id
	};
	//获取昨日膳食调查记录
	$.ajax({
		url:basePath+"/dietSurvey/findUserDietSurveyById",
		type:"POST",
		dataType:"json",
		data:param,
		success:function(json){
			if(json.msg==1){
				var data = json.data;
				var html = "";
				var remark = "";
				var dietMsg = data.dietMsg;
				if(dietMsg != null && dietMsg != ""){
					data = eval("("+dietMsg+")");
					for(var i=0;i<data.length;i++){
						if(data[i].unitRemark != null && data[i].unitRemark != ""){
							remark = "【"+data[i].unitRemark+"】";
						}else{
							remark = "";
						}
						var mealsName = getMealsName(parseInt(data[i].mealsType));
						html += "<tr><td>"+mealsName+"</td><td>"+data[i].foodName+remark+"</td>" +
						   "<td><label><input type='text' maxlength='5' class='food-weight' value='"+data[i].foodWeight+"' food-id='"+data[i].foodId+"' onkeyup='checkNumber(this);'></label>" +
						   "</td><td><a class='btn_blue delete-meals-food' del-type='save' meals-id='"+data[i].id+"' meals-type='"+data[i].mealsType+"' food-id='"+data[i].foodId+"' food-name='"+data[i].foodName+"' unit-remark='"+data[i].unitRemark+"'>删除</a></td></tr>";
					}
				}
				$("#mealsInfoList").html(html);
			}else if(json.msg==10){
				$("#mealsInfoList").html("");
			}
			setTimeout(function(){
				//实时统计食材个数
				var count = countFood();
				$("#countFood").text(count);
			},100);
			foodAnalyze();
		}
	});
}

/**
 * 获取用户某天的饮食记录
 */
function getUserMealsInfo(eatDate){
	var param = {
			userId: userId,
			startTime: eatDate,
			endTime: eatDate
	};
	//获取昨日膳食调查记录
	$.ajax({
		url:basePath+"/diet/findUserMealsInfoList",
		type:"POST",
		dataType:"json",
		data:param,
		success:function(json){
			if(json.msg==1){
				var data = json.data;
				var html = "";
				var remark = "";
				for(var i=0;i<data.length;i++){
					if(data[i].unitRemark != null && data[i].unitRemark != ""){
						remark = "【"+data[i].unitRemark+"】";
					}else{
						remark = "";
					}
					var mealsName = getMealsName(data[i].mealsType);
					html += "<tr><td>"+mealsName+"</td><td>"+data[i].foodName+remark+"</td>" +
					   "<td><label><input type='text' maxlength='5' class='food-weight' value='"+data[i].foodWeight+"' food-id='"+data[i].foodId+"' onkeyup='checkNumber(this);'></label>" +
					   "</td><td><a class='btn_blue delete-meals-food' del-type='save' meals-id='"+data[i].id+"' meals-type='"+data[i].mealsType+"' food-id='"+data[i].foodId+"' food-name='"+data[i].foodName+"' calorie='"+data[i].calorie+"'>删除</a></td></tr>";
				}
				$("#mealsInfoList").html(html);
			}else if(json.msg==10){
				$("#mealsInfoList").html("");
			}
			setTimeout(function(){
				//实时统计食材个数
				var count = countFood();
				$("#countFood").text(count);
			},100);
			foodAnalyze();
		}
	});
}
/**
 * 初始化膳食调查页面
 */
$(function(){
//	myBrowser();
	var param = {
			outpatientId:outpatientId,
			type:0
	};
	$.ajax({
		url:basePath+"/dietSurvey/findUserDietSurveyList",
		type:"POST",
		dataType:"json",
		data:param,
		success:function(json){
			if(json.msg==1){
				var data = json.data;
				var html = "";
				var remark = "";
				//显示调查记录选项卡列表
				var recipesOptions = "";
				var deleteUserDietSurvey = "";
				var mealsInfo = data[0].infoList;
				var eatDate = data[0].eatDate;
				$("#eatDate").val(eatDate);
				for(var i=0;i<data.length;i++){
					if(i>0){//第一个有重命名
						deleteUserDietSurvey = '<i class="layui-icon layui-unselect layui-tab-close removeUserDietSurvey" dietSurveyId="'+data[i].dietSurveyId+'">ဆ</i>';
					}
					var eatDate = data[i].eatDate;
					var week = dateToWeek(eatDate);
					recipesOptions += "<li class='dietSurvey' dietSurveyId='"+data[i].dietSurveyId+"'>"+
                                       "<span class='tabelTitleN'>"+eatDate+" "+week+"</span>"+
                                       deleteUserDietSurvey+
                                       "</li>";
				}
				$("#dietSurveyOptions").html(recipesOptions);
				//默认选中第一个
				setTimeout(function(){
					$("#dietSurveyOptions").find("li:first").addClass("layui-this");
				},50);
				
				for(var j=0;j<mealsInfo.length;j++){
					//显示第一条数据
					if(mealsInfo[j].unitRemark != null && mealsInfo[j].unitRemark != ""){
						remark = "【"+mealsInfo[j].unitRemark+"】";
					}else{
						remark = "";
					}
					var mealsName = getMealsName(mealsInfo[j].mealsType);
					html += "<tr><td>"+mealsName+"</td><td>"+mealsInfo[j].foodName+remark+"</td>" +
					   "<td><label><input type='text' maxlength='5' class='food-weight' value='"+mealsInfo[j].foodWeight+"' food-id='"+mealsInfo[j].foodId+"' onkeyup='checkNumber(this);'></label>" +
					   "</td><td><a class='btn_blue delete-meals-food' del-type='save' meals-id='"+mealsInfo[j].id+"' meals-type='"+mealsInfo[j].mealsType+"' food-id='"+mealsInfo[j].foodId+"' food-name='"+mealsInfo[j].foodName+"' unit-remark='"+mealsInfo[j].unitRemark+"'>删除</a></td></tr>";
				}
				$("#mealsInfoList").html(html);
			}else if(json.msg==10){
				//先给出昨天的日期
				var yesterday = getDateByDaysLate(new Date(),-1);
				var week = dateToWeek(yesterday);
				$("#yesterday").html(yesterday+" "+week);
				$("#eatDate").val(yesterday);
				//默认给一个昨天的日期和空的面板
				$("#mealsInfoList").html("");
			}
			setTimeout(function(){
				//实时统计食材个数
				var count = countFood();
				$("#countFood").text(count);
			},100);
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
		/*$("#mealsInfoList").append(html);
		$("#search-food-input").val("");
		setTimeout(function(){
			$(".food-weight:last").focus();
		},10);*/
		if($(".delete-meals-food").length == 0){
			$("#mealsInfoList").append(html);
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
					$("#mealsInfoList").append(html);
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
		layer.msg(row.name+"已存在于"+meals_name+"饮食记录中！");
	}
});

var flag = "";
/**
 * 保存用户膳食记录
 */
function saveDietSurvey(){
	var dietMsg = new Array();
	$("#mealsInfoList").find(".delete-meals-food").each(function(){
		var mealsType = $(this).attr("meals-type");
		var foodId = $(this).attr("food-id");
		var foodName = $(this).attr("food-name");
		var foodWeight = $(this).parent().siblings("td").find(".food-weight").val();
		var unitRemark = $(this).attr("unit-remark");
		if(unitRemark == null || unitRemark == "" || unitRemark == "undefined"){
			unitRemark = "";
		}
		var msg = {
				mealsType:mealsType,
				foodId:foodId,
				foodName:foodName,
				foodWeight:foodWeight,
				unitRemark:unitRemark
			};
		if(foodWeight != null && foodWeight != "" && foodWeight != 0){
			dietMsg.push(msg);
		}
	});
	if(JSON.stringify(dietMsg) != "[]"){
		var dietMsgStr = "";
		if(JSON.stringify(dietMsg) != "[]"){
			dietMsgStr = JSON.stringify(dietMsg);
		}
		var dietSurveyId = $(".layui-this").attr("dietSurveyId");
		if(dietSurveyId != null && dietSurveyId != 0){
			var param = {
					id:dietSurveyId,
					dietMsg: dietMsgStr
			};
		}else{
			var param = {
					id:0,
					userId:	userId,
					hospitalId:hospitalId,
					outpatientId:outpatientId,
					eatDateStr: $("#eatDate").val(),
					dietMsg: dietMsgStr
				};
		}
		$.ajax({
			url:basePath+"/dietSurvey/saveUserDietSurvey",
			type:"POST",
			dataType:"json",
			contentType: "application/json;charset=utf-8",
			data:JSON.stringify(param),
			success:function(json){
				if(json.msg==1){
					//保存成功
					flag = "success";
					if(dietSurveyId == null || dietSurveyId == 0)
					$(".layui-this").attr("dietSurveyId",json.data.id);
				}else{
					flag = "fail";
					layer.msg("保存失败，请稍后重试！",{time:1000});
				}
			}
		});
	}else{
		flag = "fail";
		layer.msg("膳食调查记录不能为空",{time:1000});
	}
}

/**
 * 切换调查日期
 */
$(document).on("click",".dietSurvey",function(){
	var $this = $(this);
	saveDietSurvey();
	setTimeout(function(){
		if(flag == "success"){
			$this.addClass("layui-this").siblings().removeClass("layui-this");
			var date = $this.find(".tabelTitleN").text().substring(0,10);
			$("#eatDate").val(date);
			var dietSurveyId = $this.attr("dietSurveyId");
			setTimeout(function(){
				getUserDietSurvey(dietSurveyId);
			},100);
			setTimeout(function(){
				//表格hover样式
		     tableHover($(".layui-table tbody").find("tr"),'#fff','#f4f4f4','#DCEFFA');
			},500);
		}
	},50);
});

/**
 * 删除某一条饮食记录
 */
$(document).on("click",".delete-meals-food",function(){
	var $this = $(this);
	$this.parent().parent().remove();
	//实时统计食材个数
	var count = countFood();
	$("#countFood").text(count);
});
 
/**
 * 删除用户某一天的饮食记录
 */
$(document).on("click",".removeUserDietSurvey",function(event){
	event.stopPropagation();
	var $this = $(this);
	 layer.alert('确认删除此调查记录？', {
         icon: 0,
         title: '提示',
         btn:['删除'],
         btnAlign: 'c',
         yes: function(index, layero){
             layer.close(index);
           //删除数据库中内容
         	var eatDate = $this.parent("li").find(".tabelTitleN").text().substring(0,10);
         	var id = $this.attr("dietSurveyId");
         	var param = {
         			id: id
         	}
         	$.ajax({
         		url: basePath+"/dietSurvey/deleteUserDietSurvey",
         		type: "POST",
         		dataType: "json",
         		data: param,
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
         			        var date = $(".layui-this").find(".tabelTitleN").text().substring(0,10);
         			        var id = $(".layui-this").attr("dietSurveyId");
         			        $("#eatDate").val(date);
         			        getUserDietSurvey(id);
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

function checkName(date){
	var count = 0;
	$(".dietSurvey").each(function(){
		var newDate = $(this).find(".tabelTitleN").text().substring(0,10);
		if(newDate == date){
			count ++;
		}
	});
	return count;
}

/**
 * 点击返回
 */
$(document).on("click","#goBack",function(){
	saveDietSurvey();
	setTimeout(function(){
		if(flag == "success"){
//			window.history.go(-1);
			window.location.href="fillInUserInfo.html?userId="+userId+"&outpatientId="+outpatientId+"&menuType="+CONST.menuType[0];
		}
	},50);
});

/**
 * 点击下一步
 */
$(document).on("click","#goNext",function(){
	saveDietSurvey();
	setTimeout(function(){
		if(flag == "success"){
			window.location.href="sportSurvey.html?userId="+userId+"&outpatientId="+outpatientId;
		}
	},50);
});

/**
 * 点击跳过
 */
$(document).on("click","#jumperOver",function(){
	window.location.href="sportSurvey.html?userId="+userId+"&outpatientId="+outpatientId;
});

/**
 * 查看营养分析
 */
$(document).on("click","#lookNutritionAnalyze",function(){
	var dietMsg = new Array();
	$("#mealsInfoList").find(".delete-meals-food").each(function(){
		var mealsType = $(this).attr("meals-type");
		var foodId = $(this).attr("food-id");
		var foodName = $(this).attr("food-name");
		var foodWeight = $(this).parent().siblings("td").find(".food-weight").val();
		var unitRemark = $(this).attr("unit-remark");
		if(unitRemark == null || unitRemark == "" || unitRemark == "undefined"){
			unitRemark = "";
		}
		var msg = {
				mealsType:mealsType,
				foodId:foodId,
				foodName:foodName,
				foodWeight:foodWeight,
				unitRemark:unitRemark
			};
		if(foodWeight != null && foodWeight != "" && foodWeight != 0){
			dietMsg.push(msg);
		}
	});
	if(JSON.stringify(dietMsg) == "[]"){
		layer.alert("请先添加食物后再进行营养分析");
	}else{
		saveDietSurvey();
		setTimeout(function(){
			if(flag == "success"){
				window.location.href="nutritionAnalyze.html?outpatientId="+outpatientId;
			}
		},50);
	}
	
});


