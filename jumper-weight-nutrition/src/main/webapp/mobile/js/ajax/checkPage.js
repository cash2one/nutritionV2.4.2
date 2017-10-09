//手机端h5或web端第一个页面赋初始session值
var hospitalId = GetQueryString("hospitalId");
var userId = GetQueryString("userId");
var mobile = GetQueryString("mobile");
$.cookie('hospitalId', hospitalId);
$.cookie('userId', userId);

var outpatientId = "";
//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	document.activeElement.blur();//收回软键盘
	var data = {hospitalId : hospitalId, mobile : mobile};
	//加载用户信息
	$.post(basePath + "/user/findUserByIdMobile", data, function(ret) {
		if (ret.msg == 1) {
			$(".hospitalName").text(ret.data.hospitalName);
			$(".mobile").text(mobile);
			if(ret.data.mobile != null){
				$(".realName").text(ret.data.realName);
			}else{
				$(".realNameDD").css("display","none");
			}
			if(ret.data.userId != null){
				userId = ret.data.userId;
				$.cookie('userId', userId);
			}
		} else {
			mui.toast(ret.msgbox);
		}
	}, "json");
	
	if(userId == null){
		$(".checkInfo").attr("outpType", 0);
		$(".liucheng").show();
	}else{
		var param = {hospitalId : hospitalId, userId : userId};
		//判断是否是初诊，并返回basePath
		$.post(basePath + "/outpatient/isFirstOutpatient", param, function(ret) {
			if (ret.msg != 1) {
				mui.toast(ret.msgbox);
				return;
			}
			//$.session.set('basePath', ret.data.basePath);
			$(".checkInfo").attr("outpType", ret.data.type);
			if (ret.data.type == 0) {//初诊
				$(".liucheng").show();
			} else if (ret.data.type == 2) {//复诊
				$(".h4Title").show();
				$(".pText2").show();
			} else {//初诊未完成诊断,复诊未完成诊断 不做样式任何处理
				outpatientId = ret.data.outpatientId;
			}
			
		}, "json");
	}
	
	$(".checkInfo").click(function() {
		var outpType = $(this).attr("outpType");
		if (outpType == 0) {// 初诊
			window.location.href = "fillInUserInfo.html?mobile="+mobile;
		} else if (outpType == 1) {// 初诊未完成诊断
			window.location.href = "firstCheck.html?outpatientId=" + outpatientId;
		} else if (outpType == 2) {// 复诊
			window.location.href = "outpatientReason.html";
		} else {// 复诊未完成诊断
			window.location.href = "repeatCheck.html?outpatientId=" + outpatientId;
		}
	});
});
