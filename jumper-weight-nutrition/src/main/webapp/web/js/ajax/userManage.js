/**
 * 孕妇管理js
 */
//分页数据
var totalPage = 0;//总页数
var curr = 1;//当前页
var list = [];//数据

//体重异常统计中跳转过来的传参
var weightStatus = GetQueryString("weightStatus");

//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//默认按照last_outpatient_time倒序排
	var data = {
		hospitalId : hospitalId, 
		page : curr, 
		limit : CONST.tabPageSize, 
		orderRow : "last_outpatient_time", 
		orderType : 1
	};
	//体重异常统计中跳转过来的
	if (weightStatus != null) {
		data.weightStatus = weightStatus;
		$("#weightStatus").val(weightStatus);
	}
	//初始化分页
	initPage(data);
	
	//模糊查询
	$("#search-btn1").click(function() {
		var query = $("input[name='query']").val().trim();
		//清空其他赛选条件
		$("#weightStatus").val("");
		$("#weightExceptionType").val("");
		$("#startExpDate").val("");
		$("#endExpDate").val("");
		data.weightStatus = $("#weightStatus").val();
		data.weightExceptionType = $("#weightExceptionType").val();
		data.startExpDate = $("#startExpDate").val();
		data.endExpDate = $("#endExpDate").val();
		data.page = 1;
		data.query = query;
		//分页查询
		initPage(data);
	});
	//回车事件触发查询
	$(document).keydown(function(e) {
		e = e||event;
    	var key = e.keyCode;
    	switch (key) {
		case 13:// 回车按钮
			$(".enterBtnEvent").click();
			break;
		default:
			break;
		}
	});
	//多条件赛选
	$("#search-btn2").click(function() {
		var query = $("input[name='query']").val().trim();
		data.page = 1;
		data.query = query;
		data.weightStatus = $("#weightStatus").val();
		data.weightExceptionType = $("#weightExceptionType").val();
		data.startExpDate = $("#startExpDate").val();
		data.endExpDate = $("#endExpDate").val();
		//分页查询
		initPage(data);
	});
	//切换全部和妊娠糖料病
	$('.tabnav2 li').click(function() {
		var _index = $(this).index();
		$(this).addClass('active').siblings().removeClass('active');
		$('.tabstyle2 .con-right-cen').eq(_index).show().siblings().hide();
		
		var isDiabetes = $(this).attr("isDiabetes");
		if (isDiabetes == 1) {
			$(".currentSugar").show();
		} else {
			$(".currentSugar").hide();
		}
		data.isDiabetes = isDiabetes;
		//默认的赛选条件
		$("#weightStatus").val("");
		$("#weightExceptionType").val("");
		$("#startExpDate").val("");
		$("#endExpDate").val("");
		data.weightStatus = $("#weightStatus").val();
		data.weightExceptionType = $("#weightExceptionType").val();
		data.startExpDate = $("#startExpDate").val();
		data.endExpDate = $("#endExpDate").val();
		data.page = 1;
		data.query = "";
		//分页查询
		initPage(data);
	});
	
	//升序降序
	var isDesc = true;//当前的排序
	$('.orderRowTr th').click(function() {
		var orderRow = $(this).attr("orderRow");
		if (orderRow == null) {
			return;
		}
		if (isDesc) {
			$(this).css('color', '#1990ec').children('.tableSort').css('background-position', '-8px 0');
			$(this).siblings().css('color', '#333').children('.tableSort').css('background-position', '0 0');
			isDesc = false;
		} else {
			$(this).css('color', '#1990ec').children('.tableSort').css('background-position', '-17px 0');
			$(this).siblings().css('color', '#333').children('.tableSort').css('background-position', '0 0');
			isDesc = true;
		}
		data.orderRow = orderRow;
		data.orderType = (isDesc) ? 1 : 0;
		//分页查询
		initPage(data);
	});
	
	//点击姓名跳转到用户信息页面
	$(document).on("click", ".userInfoTr", function() {
		var userId = $(this).attr("userId");
		window.location.href = "viewUserInformation.html?userId=" + userId + "&menuType="+CONST.menuType[1];
	});
	
	//删除孕妇档案
	$(document).on("click", ".delUserManage", function(event){
		event.stopPropagation();
		var $this = $(this);
		var userId = $this.attr("userId");
		layer.alert('档案删除后不可恢复，是否确认删除？', {
            icon: 0,
            title: '提示',
            btn: ['删除','取消'],
            btnAlign: 'c',
            yes: function (index, layero) {
            	layer.close(index);
            	$.post(
            			basePath + "/userManage/deleteUserManage", 
            			{
            				hospitalId : hospitalId,
            				userId:userId
            			},function(ret) {
		    				if (ret.msg == 1) {
		    					$this.parents("tr").remove();
		    				}
		    				layer.msg(ret.msgbox, {time: 600});
            	}, "json");
            }
        });
	});
	
	//加载短信模板
	/*$.post(basePath + "/settings/findHospitalTemplate", {hospitalId : hospitalId, type : 1}, function(ret) {
		if (ret.msg != 1) {
			return;
		}
		var html = "";
		for (var i = 0; i < ret.data.length; i++) {
			var obj = ret.data[i];
			html += 
				'<div class="mess-list-r">' + 
                	'<div class="mess-list-r-box" >' +
		            	'<span class="mess-list-name">通知内容：</span>' +
		            	'<span class="mess-list-main">' + obj.content +'</span>' + 
		            '</div>' + 
	            '</div>';
		}
		$(".msgTemplate").html(html);
	});*/
	
	//通知单击填充短信模板
	$(document).on('click','#messBox .mess-list-r-box',function () {
        var content = $(this).find('.mess-list-main').text();
        $('#msgContent').val(content);
        /*countChar("msgContent","counter2");*/
    });
	
	//消息单击填充短信模板
	$(document).on('click','#chatBox .mess-list-r-box',function () {
        var content = $(this).find('.mess-list-main').text();
        document.getElementById("chatTemplate").contentWindow.document.getElementById("messageBody").value = content;
		//console.log($("#iframe-class").contents().find("#messageBody").val());
        //$('#messageBody').val(content);
    });
	
	//发送通知
	var mobileList = [];
	$(document).on("click", ".sendMessage, .sendMsgBatch", function(event) {
		event.stopPropagation();
		
		//先查询是否有短信模板，有的话就弹框，没有的话就不让发送短信
		var html = $(".msgTemplate").html();
		if (html.trim() == "") {
			layer.alert('没有短信模版，请添加。', {
	            icon: 0,
	            title: '提示',
	            btn: ['好'],
	            btnAlign: 'c',
	            yes: function (index, layero) {
	                layer.close(index);
	            }
	        });
			return;
		}
		//清空手机号列表
		mobileList.splice(0, mobileList.length);
		var nameList = [];
		$("#msgContent").val("");
		if ($(this).hasClass("sendMessage")) {//单个发送
			var currTr = $(this).parent().parent(); 
			nameList.push(currTr.find(".realName").text());
			mobileList.push(currTr.find(".mobile").text());
		} else {//批量发送
			$("input[name='record']:checked").each(function(i) {
				var currTr = $(this).parent().parent();
				nameList.push(currTr.find(".realName").text());
				mobileList.push(currTr.find(".mobile").text());
			});
		}
		if (mobileList.length == 0) {
			layer.msg('请勾选要发送通知的孕妇', {time: 1000});
			return;
		}
		
		//加载收信人，以及手机号码list赋值
		var recHtml = "";
		for (var i = 0; i < nameList.length; i++) {
			var obj = nameList[i];
			recHtml += '<li>' + obj + '</li>'; 
		}
		$(".recipient").html(recHtml);
		
		layer.open({
            title:'发送通知',
            type: 1,
            area: ['900px', '660px'], //宽高
            content:$('#messBox')
        });
		//更改弹框头部样式
		$(".layui-layer").addClass("layer-message");
	});
	
	//发送短信
	$(document).on("click", ".sendMsgBtn", function() {
		var content = $("#msgContent").val().trim();
		if (content == "") {
			layer.msg("短信内容不能为空！", {time: 600});
			return;
		}
		//发送短信接口
		var data = {mobileList : mobileList, hospitalId : hospitalId, content : content};
		$.post(basePath + "/outpatient/sendMsgToOutp", data, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time: 600});
				return;
			}
			$(".layui-layer-close").click();//成功关闭弹框
			$("#msgContent").val("");
		});
	});
	
});


//初始化分页
function initPage(data) {
	//初始分页信息
	function init(data) {
		$.post(basePath + "/userManage/listUserManageByPage", data, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time : 1000});
				return;
			}
			totalPage = ret.data.pages;
			curr = ret.data.pageNum;
			list = ret.data.list;
		}, "json");
	}
	init(data);
	//展示分页列表
	layui.use(["laypage"], function() {
		layui.laypage({
			cont : "page",
			pages : totalPage,
			curr : data.page,
			skip : true,
			jump : function(obj) {
				//得到了当前页，用于向服务端请求对应数据
				curr = obj.curr;
				data.page = curr;
				//跳转到某页，并初始化分页数据
				init(data);
				var html = "";
				if (totalPage == 0 && !$.isEmptyObject(data.query)) {
					html = "<tr><td colspan='9' class='noResultTd'>没有符合关键词的结果</td></tr>";
					$("#pageList").html(html);
					return;
				}
				for (var int = 0; int < list.length; int++) {
					var obj = list[int];
					var weightStatus = "weight-status" + obj.voWeightRecord.weightStatus;
					var sugarStatus = "weight-status" + obj.sugarStatus;
					var addTime = obj.voWeightRecord.addTime;
					var unReadMsgs = "";
					if(obj.unReadMsgs > 0){
						unReadMsgs = "("+obj.unReadMsgs+")";
					}
					if (getDateByDaysLate(new Date(), 0) == addTime) {
						addTime = "今天";
					} else if (getDateByDaysLate(new Date(), -1) == addTime) {
						addTime = "昨天";
					}
					html += 
						"<tr class='userInfoTr' userId=" + obj.voUserInfo.userId + ">" +
							"<td><input class='checkIn' type='checkbox' name='record'><a class='realName'>" + obj.voUserInfo.realName + "</a></td>" +
							"<td>" + obj.voUserInfo.age + "</td>" +
							"<td class='mobile'>" + obj.voUserInfo.mobile + "</td>" +
							"<td>" + obj.voUserInfo.pregnantWeek[0] + "周" + obj.voUserInfo.pregnantWeek[1] + "天</td>" +
							"<td>" + obj.voUserInfo.expectedDate + "</td>" +
							"<td>" + obj.voUserInfo.height + "</td>" +
							"<td>" + obj.voWeightRecord.averageValue + "</td>" +
							"<td>" + obj.voUserInfo.weight + "</td>" +
							"<td>" + addTime + "</td>";
					if (data.isDiabetes == 1) {
						html += "<td class=" + sugarStatus + ">" + obj.currentSugar + "</td>";
					}
					html += 		
							"<td class=" + weightStatus + ">" + CONST.weightStatus[obj.voWeightRecord.weightStatus] + "</td>" +
							//"<td>" + obj.voWeightRecord.bmi + "(" + CONST.weightStatus[obj.voWeightRecord.weightStatus] + ")" + "</td>" +
							"<td><a class='sendMessage'>发送通知</a><a userId=" + obj.voUserInfo.userId + " userName="+obj.voUserInfo.realName+" class='chatWithUser'>发送消息"+unReadMsgs+"</a></td>" +
							//<a class='delUserManage' userId=" + obj.voUserInfo.userId + ">删除</a>
						"</tr>";
				}
				$("#pageList").html(html);
				$.ajax({
					url:basePath+"/settings/findSettingByHospId",
					type:"post",
					dataType:"json",
					data:{hospitalId:hospitalId},
					async:false,
					success:function(json){
						if(json.msg == 1){
							var nutritionConsult = json.data.nutritionConsult;
							if(nutritionConsult == 0){
								$(".chatWithUser").css("display","none");
							}
						}
					}
				});
				$(".userInfoTr:even").addClass("evenTr");
				$(".userInfoTr:odd").addClass("oddTr");
			}
		});
	});
}

$(document).on("click",".chatWithUser",function(event){
	event.stopPropagation();
	//清空未读消息条数
	$(this).text("发送消息");
	var userId = $(this).attr("userId");
	var userName = $(this).attr("userName");
	//var hospitalName =  $(this).attr("hospitalName");
	var busCode =CONST.busCode;//业务代码
	var color = 1;
	var fromUserType=3;//  1：医生 2：患者 用户 3：医院 
	if(userName==null){
		userName = "该用户暂无昵称";
	}
	var src = basePath+"/chat/init?"+"fromUserId="+ hospitalId +"&fromNickName=&fromHeadUrl&fromUserType="+fromUserType
	+"&toUserId="+userId+"&toNickName="+userName+"&toHeadUrl&toUserType=2&userType=1&busCode="+busCode+"&color="+color+"&consultantId="+userId;
	$("#chatTemplate").attr("src",src);
	layer.open({
        title:'发送消息',
        type: 1,
        area: ['934px', '660px'], //宽高
        content:$('#chatBox')
    });
	
	//更改弹框头部样式
	$(".layui-layer").addClass("layer-message");
	setTimeout(function(){
		$('html body .pright').css('background-color','#fff');
	},200);
});



