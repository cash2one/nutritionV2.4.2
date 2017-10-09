var mobile = GetQueryString("mobile");

var userId = null;
//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	var data = {hospitalId : hospitalId, mobile : mobile};
	//加载用户信息
	$.post(basePath + "/user/findUserByIdMobile", data, function(ret) {
		if (ret.msg == 1) {
			userId = ret.data.userId;
			//alert(JSON.stringify(ret));
			$("#realName").val(ret.data.realName);
			$(".birthday").text(ret.data.birthday);
			birthday = ret.data.birthday;//赋值，生日弹框默认值要用到
			$("input[name='weight']").val(ret.data.weight);
			$("input[name='height']").val(ret.data.height);
			$(".pregnantType").text(CONST.pregnantType[ret.data.pregnantType]);
			$("#expectedDate").text(ret.data.expectedDate);
			$("input[name='outpatientNum']").val(ret.data.outpatientNum);
		} else {
			mui.toast(ret.msgbox);
		}
	}, "json");

	//下一步，数据验证并且保存跳转
	$(".nextStep").click(function() {
		if ($("#realName").val() == "") {
			mui.toast("请输入姓名");
			return;
		}
		if ($(".birthday").text() == "") {
			mui.toast("请输入生日");
			return;
		}
		var weight = $("input[name='weight']").val();
		if (weight == "") {
			mui.toast("请输入正确的孕前体重");
			return;
		}
		if (weight < 20 || weight > 150) {
			mui.toast("孕前体重不在20~150kg");
			return;
		}
		var currWeight = $("input[name='averageValue']").val();
		if (currWeight == "") {
			mui.toast("请输入正确的目前体重");
			return;
		}
		if (currWeight < 20 || currWeight > 150) {
			mui.toast("目前体重不在20~150kg");
			return;
		}
		var height = $("input[name='height']").val();
		if (height == "") {
			mui.toast("请输入身高");
			return;
		}
		if (height < 50 || height > 200) {
			mui.toast("身高不在50~200cm");
			return;
		}
		if ($(".pregnantType").text() == "") {
			mui.toast("请输入怀孕类型");
			return;
		}
		if ($("#expectedDate").text() == "") {
			mui.toast("请输入预产期");
			return;
		}
		/*if ($("input[name='outpatientNum']").val() == "") {
			mui.toast("请输入就诊卡号");
			return;
		}*/
		/*if (!/^[0-9a-zA-Z]+$/.test($("input[name='outpatientNum']").val())) {
			mui.toast("请输入正确的就诊卡号");
			return;
		}*/
		if($("#reason").attr("value") == ""){
			mui.toast("请输入就诊原因");
			return;
		}
		//怀孕类型
		var pregnantType = 0;
		for (var i = 0; i < CONST.pregnantType.length; i++) {
			if ($(".pregnantType").text() == CONST.pregnantType[i]) {
				pregnantType = i;
			}
		}
		var userParams = {
			userId : userId,
			hospitalId : hospitalId,
			birthday : $(".birthday").text(),
			weight : $("input[name='weight']").val(),
			height : $("input[name='height']").val(),
			pregnantType : pregnantType,
			outpatientNum : $("input[name='outpatientNum']").val().trim(),
			isAddOutp : 0,
			mobile:mobile,
			expectedDate:$("#expectedDate").text(),
			realName:$("#realName").val()
		}
		var physicalParams = {averageValue : $("input[name='averageValue']").val()};
		var postData = {userParams : JSON.stringify(userParams), physicalParams : JSON.stringify(physicalParams)};
		//保存用户数据
		$.post(basePath + "/user/addOrUpdateUserInfo", postData, function(ret) {
			if (ret.msg != 1) {
				mui.toast(ret.msgbox);
				return;
			}
			userId = ret.data.userId;
			$.cookie('userId', userId);
		}, "json");
		
		if (userId == null) {
			return;
		}
		
		//获取用户openID
		var channel = $.cookie("channel");
		var openId = $.cookie("openId");
		if(channel != null && channel != "null" && openId != null && openId != "null") {
			var param = {
				userId:userId,
				channel:channel,
				openId:openId
			};
			//保存openId记录
			$.post(basePath + "/user/saveUserOpenId", param, function(data) {
				if(data.msg != 1) {
					mui.toast(ret.msgbox);
					return;
				}
			});
		}
		
		//保存openId记录成功与否都需跳转
		var isSkipDiet = ($("#reason").attr("isSkipDiet") == "true");
		var isSkipSport = ($("#reason").attr("isSkipSport") == "true");
		var reasonId = $("#reason").attr("value");
		if (!isSkipDiet) {
			window.location.href = "dietSurvey.html";
		} else if (!isSkipSport) {
			window.location.href = "sportSurvey.html";
		} else {
			//否则都跳过的话就保存一条门诊记录
			var outpatientId = "";
			var opData = {hospitalId : hospitalId, userId : userId, outpatientReason : reasonId};
			//保存一条门诊记录
			$.post(basePath + "/outpatient/addOutpatient", opData, function(ret) {
				if (ret.msg != 1) {
					mui.toast(ret.msgbox);
					return;
				}
				outpatientId = ret.data;
				//跳转
				window.location.href = "firstCheck.html?outpatientId=" + outpatientId;
			}, "json");
		}
		//将就诊原因暂时先放到cookie中
		$.cookie('outpReason', reasonId);
	});
});
