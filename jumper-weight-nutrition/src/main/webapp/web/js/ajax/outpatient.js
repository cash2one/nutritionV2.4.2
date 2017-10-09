//手机端h5或web端第一个页面赋初始session值
var hospitalId = GetQueryString("hospitalId");
//$.session.set('hospitalId', hospitalId);
$.cookie('hospitalId', hospitalId);

//分页数据
var totalPage = 0;//总页数
var curr = 1;//当前页
var list = [];//数据

//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	var data = {hospitalId : hospitalId, page : curr, limit : CONST.tabPageSize, status : 0};
	//初始化分页
	initPage(data);
	
	setInterval(function() {
		var query = $("input[name='query']").val().trim();
		if (query != "" || curr != totalPage) {
			return;
		}
		//没有搜索条件，并且当前页为最后一页时执行
		data.page = curr;
		delete data["query"];
		initPage(data);
	}, 3000);
	
	//模糊查询
	$("#search-btn").click(function() {
		var query = $("input[name='query']").val().trim();
		data.page = 1;
		data.query = query;
		//分页查询
		initPage(data);
	});
	
	//点击初诊复诊tab选项卡
	$(".tabnav2 li").click(function() {
		var _index = $(this).index();
		$(this).addClass('active').siblings().removeClass('active');
		$('.tabstyle2 .con-right-cen').eq(_index).show().siblings().hide();
		var status = $(this).attr("status");
		data.page = 1;
		data.query = "";
		data.status = status;
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
	
	//跳转用户详情页面
	$(document).on("click", ".userInfoTr", function(event) {
		event.stopPropagation();
		var userId = $(this).attr("userId");
		var status = $(this).attr("status");
		var outpatientId = $(this).attr("outpId");
		if (status == 0) {//初诊
			window.location.href = "fillInUserInfo.html?userId=" + userId + "&outpatientId=" + outpatientId;
		} else {//复诊
			window.location.href = "viewUserInformation.html?userId=" + userId + "&outpatientId=" + outpatientId + "&menuType=" + CONST.menuType[0];
		}
	});
	
	//新建档
	$("#bookbuild").click(function() {
		window.location.href = "fillInUserInfo.html";
	});
	
	//点击删除
	$(document).on("click", ".delOutp, .delOutpBatch", function(event) {
	//$(".delOutp, .delOutpBatch").click(function() {
		event.stopPropagation();
		var outpIds = [], target = new Object();
		if ($(this).hasClass("delOutp")) {//单个删除
			outpIds.push($(this).attr("outpId"));
			target = $(this);
		} else {//多个删除
			target = $("input[name='record']:checked");
			target.each(function(i) {
				outpIds.push($(this).val());
			});
		}
		if (outpIds.length == 0) {
			layer.msg('请勾选要删除的孕妇', {time: 1000});
			return;
		}
		layer.alert('确定要删除么？', {
            icon: 0,
            title: '提示',
            btn: ['删除'],
            btnAlign: 'c',
            yes: function (index, layero) {
            	checkedDel(outpIds, target);//执行删除
                layer.close(index);
                layer.msg('操作成功', {time: 600});
            }
            //键盘 回车 ECS控制弹窗~
            /*success: function (layero, index) {
                $(document).on('keydown', function (e) {
                    if ($(".layui-layer").hasClass("layui-layer")) {
                        if (e.keyCode == 13) {
                            layer.close(index);
                            checkedDel(outpIds, target);//执行删除
                            layer.msg('操作成功', {time: 600});
                        }
                        if (e.keyCode == 27) {
                            layer.close(index);
                        }
                    }
                });
            }*/
        });
		//确定删除outpIds：门诊id集合
        function checkedDel(outpIds, target) {
            target.each(function() { // 遍历选中的checkbox
                $(this).parents("tr").remove();  // 获取checkbox后删除按钮所在的行
            });
           $.post(basePath + "/outpatient/deleteOutpatient", {idList : outpIds}, function(ret) {
				if (ret.msg != 1) {
					layer.msg(ret.msgbox, {time: 600});
				}
			}, "json");
        }
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
	
	//单击填充短信模板
	$(document).on('click','.mess-list-r-box',function () {
        var content = $(this).find('.mess-list-main').text();
        $('#msgContent').val(content);
        /*countChar("msgContent","counter2");*/
    });
	
	//发送通知
	var mobileList = [];
	$(document).on("click", ".sendMessage, .sendMsgBatch", function(event) {
		event.stopPropagation();
		
		//先查询是否有短信模板，有的话就弹框，没有的话就不让发送短信
		var html = $(".msgTemplate").html();
		if(html.trim() == ""){
			layer.alert('没有短信模版，请添加。', {
	            icon: 0,
	            title: '提示',
	            btn: ['好'],
	            btnAlign: 'c',
	            yes: function (index, layero) {
	                layer.close(index);
	            }
	        });
		}else{
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
		}
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
	
	//查询医院设置，如果咨询关闭则隐藏未读消息数量
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
					$("#noRead").css("display","none");
					$.cookie("noRead","hidden");
				}
			}
		}
	});
	
});


//初始化分页
function initPage(data) {
	//初始分页信息
	function init(data) {
		$.post(basePath + "/outpatient/listOutpatientUser", data, function(ret) {
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
					html = "<tr><td colspan='13' class='noResultTd'>没有符合关键词的结果</td></tr>";
					$("#pageList").html(html);
					return;
				}
				for (var int = 0; int < list.length; int++) {
					var outp = list[int];
					if(outp.voWeightRecord != null){
						var weightStatus = "weight-status"+outp.voWeightRecord.weightStatus;
						var addTime = outp.voWeightRecord.addTime;
						if(getDateByDaysLate(new Date(),0) == addTime){
							addTime = "今天";
						}else if(getDateByDaysLate(new Date(),-1) == addTime){
							addTime = "昨天";
						}
					}
					html += 
						"<tr class='userInfoTr' userId=" + outp.voUserInfo.userId + " status=" + outp.status + " outpId=" + outp.id + ">" +
							"<td><input class='checkIn' type='checkbox' name='record' value=" + outp.id + "><a class='realName'>" + outp.voUserInfo.realName + "</a></td>" +
							"<td>" + outp.voUserInfo.outpatientNum + "</td>" +
							"<td>" + outp.voUserInfo.age + "</td>" +
							"<td class='mobile'>" + outp.voUserInfo.mobile + "</td>" +
							"<td>" + outp.voUserInfo.pregnantWeek[0] + "周" + outp.voUserInfo.pregnantWeek[1] + "天</td>" +
							"<td>" + outp.voUserInfo.expectedDate + "</td>" +
							"<td>" + outp.voUserInfo.height + "</td>" +
							"<td>" + outp.voWeightRecord.averageValue + "</td>" +
							"<td>" + outp.voUserInfo.weight + "</td>" +
							"<td>" + addTime + "</td>" +
							"<td class="+weightStatus+">" + CONST.weightStatus[outp.voWeightRecord.weightStatus] + "</td>" +
							"<td>" + outp.outpatientTime + "</td>" +
							"<td>" + outp.outpatientReason + "</td>" +
							"<td><a class='sendMessage'>发送通知</a><a class='delOutp' outpId=" + outp.id + ">删除</a></td>" +
						"</tr>";
				}
				//记录下选中的栏目
				var checkedArr = [];
				var isMouseoverObj = "";
				$(".userInfoTr").each(function() {
					if ($(this).find("input[name='record']").is(":checked")) {
						checkedArr.push($(this).attr("userId"));
					}
					if ($(this)[0].hasAttribute("ismouseover")) {
						isMouseoverObj = $(this).attr("userId");
					}
				});
				
				$("#pageList").html(html);
				//赋值刷新列表前的样式
				$(".userInfoTr").each(function(i) {
					var trBackColor = (i % 2 == 0) ? "evenTr" : "oddTr";
					$(this).addClass(trBackColor);
					var checked = false;
					for (var i = 0; i < checkedArr.length; i++) {
						if ($(this).attr("userId") == checkedArr[i]) {
							checked = true;
							break;
						}
					}
					if (checked) {
						$(this).find("input[name='record']").attr("checked", true);
						$(this).addClass('activeBg')//设置选中样式
					}
					if (isMouseoverObj != "" && isMouseoverObj == $(this).attr("userId")) {
						$(this).attr("ismouseover", 1);
						$(this).addClass('tr-hover')//设置鼠标覆盖样式
					}
				});
			}
		});
	});
}
