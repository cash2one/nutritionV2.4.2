
/**
 * 通知模板js
 */

//分页数据
var msgTotalPage = 0;//总页数
var msgCurr = 1;//当前页
var msgList = [];//数据
var msgTotal = 0;//总条数，msg防止命名冲突重叠

//每页显示8条
var msgPageData = {hospitalId : hospitalId, page : msgCurr, limit : 5};

//短信列表类型 0：设置页面通知模板，1：弹框页面通知模板
var msgListType = $("#msgListType").val();

//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//设置页面通知模板每页显示10条
	if (msgListType == 0) {
		msgPageData.limit = 10;
	}
	//初始化分页
	msgInitPage(msgPageData);
	
});

//初始化分页
function msgInitPage(data) {
	//初始分页信息
	function init(data) {
		$.post(basePath + "/settings/listTemplateByPage", data, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time : 1000});
				return;
			}
			msgTotalPage = ret.data.pages;
			msgCurr = ret.data.pageNum;
			msgList = ret.data.list;
			msgTotal = ret.data.total;
		}, "json");
	}
	
	//跳转方法
	function jumpPage(obj) {
		//得到了当前页，用于向服务端请求对应数据
		msgCurr = obj.curr;
		data.page = msgCurr;
		//跳转到某页，并初始化分页数据
		init(data);
		if (msgTotalPage == 0) {
			return;
		}
		var html = "";
		if (msgListType == 0) {//设置页面通知模板列表
			html = '<h5 class="textList">已添加模板：</h5>';
			for (var int = 0; int < msgList.length; int++) {
				var obj = msgList[int];
				html += 
					'<h5 class="textList ov">' +
						'<span class="fl dataContent textSpan">' + obj.content + '</span>' + 
						'<span class="endBtn2 fr"><span class="bianji editMsgTemplate" editType="edit" templateId=' + obj.id + '>编辑</span>|' + 
						'<span class="del delMsgTemplate" templateId=' + obj.id + '>删除</span></span>' + 
					'</h5>';
			}
		} else {//弹框页面通知模板列表
			for (var int = 0; int < msgList.length; int++) {
				var obj = msgList[int];
				html += 
					'<div class="mess-list-r">' + 
	                	'<div class="mess-list-r-box" >' +
			            	'<span class="mess-list-name">通知内容：</span>' +
			            	'<span class="mess-list-main">' + obj.content +'</span>' + 
			            '</div>' + 
		            '</div>';
			}
		}
		
		$(".msgTemplate").html(html);
	}
	init(data);
	
	//展示分页列表
	layui.use(["laypage"], function() {
		layui.laypage({
			cont : "msgPage",
			pages : msgTotalPage,
			curr : data.page,
			skip : true,
			jump : function(obj) {
				jumpPage(obj);
			}
		});
		
		if ($("#msgPage1").length > 0) {
			layui.laypage({
				cont : "msgPage1",
				pages : msgTotalPage,
				curr : data.page,
				skip : true,
				jump : function(obj) {
					jumpPage(obj);
				}
			});
		}
		
	});
}
