/**
 * 天使医生登录
 */
//timer变量，控制时间
var InterValObj; 
//间隔函数，1秒执行 
var count = 60;  
//当前剩余秒数
var curCount;
var hospitalId = GetQueryString("hospitalId");
var channel = GetQueryString("channel");
var openId = GetQueryString("openId");
$.cookie("channel",channel);
$.cookie("openId",openId);
$(document).ready(function() {
	//获取验证码
	$(".yzbutton").on("click", function() {
		curCount = count;
		//手机号码
		var mobile = $("#mobile").val();
		if(mobile == "") {
			$("#mobileTis").html("请输入手机号码");
			return;
		} else {
			$("#mobileTis").html("");
		}
		var code = 0;
		var msgbox = "";
		//发送验证码
		$.ajax({
			url: basePath + "/barcode/getSmsCode",
			type: "post",
			data:{mobile:mobile,hospitalId:hospitalId},
			dataType: "json",//从服务器端返回的数据类型
			async: false,//同步请求
			success: function(json) {
				code = json.msg;
				msgbox = json.msgbox;
			}
		});
		if(code == 1) {
			//禁用按钮
			$(".yzbutton").attr("disabled", "true");
			$(".yzbutton").val(curCount + "S");
			//启动计时器，1秒执行一次
			InterValObj = window.setInterval(SetRemainTime, 1000);
		} else {
			//启用按钮
			$(".yzbutton").removeAttr("disabled");
			$("#mobileTis").html(msgbox);
		}
	});
	
	//登录
	mui(document.body).on("tap", "#login", function() {
		//收起虚拟键盘
		document.activeElement.blur();
	    //手机号码
		var mobile = $("#mobile").val();
		//验证码
		var verificationCode = $("#code").val();
		if(mobile == "") {
			$("#mobileTis").html("请输入手机号码");
			return;
		} else {
			$("#mobileTis").html("");
		}
		if(verificationCode == "") {
			$("#codeTis").html("请输入验证码");
			return;
		} else {
			$("#codeTis").html("");
		}
		//验证码校验
		$.ajax({
			url: basePath + "/barcode/verifedLogin",
			type: "post",
			dataType: "json",//从服务器端返回的数据类型
			data: {mobile:mobile, code:verificationCode, hospitalId:hospitalId},
			beforeSend:function() { //请求中
				$("#login").attr("disabled", true);
			},
			success: function(json) {
				//验证成功
				if(json.msg == 1) {
					//用户信息已存在
					var mobile = json.data;
					window.location.href = "checkPage.html?hospitalId="+hospitalId+"&mobile="+mobile;
				}else { //失败
					mui.toast(json.msgbox);
				}
			},
			complete: function() { //请求完成
				$("#login").attr("disabled", false);
			}
		});
	});
});

//timer处理函数  
function SetRemainTime() {
    if (curCount == 0) {
    	//停止计时器
    	window.clearInterval(InterValObj);
        //启用按钮 
        $(".yzbutton").removeAttr("disabled"); 
        $(".yzbutton").val("获取验证码");  
    } else  {  
        curCount--;  
        $(".yzbutton").val(curCount + "S");  
    }  
}

//手机号码 /^[1][35789][0-9]{9}$/或者这个格式判断的
function isMobile(str) {
	var patrn=/^[1][35789][0-9]{9}$/;
	return patrn.test(str);
};