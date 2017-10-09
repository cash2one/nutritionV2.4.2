/**
 * Created by Administrator on 2017/5/9 0009.
 */

var userId = (GetQueryString("userId") != null) ? GetQueryString("userId") : undefined;
var outpatientId = GetQueryString("outpatientId");//门诊id
var menuType = GetQueryString("menuType");

var today = formatDate(new Date());
var jumpType = $("input[name='jumpType']").val();
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	$("#userInfoHtml").load("common/userInfoForm.html");
	if (jumpType == 1) {//建档页面，无弹框
		$("#submitForm").show();
	} else {//用户信息页面，有弹框
		$("#headUserInfo").load("common/headUserInfo.html");
	}
	
	//展示出用户信息来
	var data = {hospitalId : hospitalId};
	if (userId != null) {
		data.userId = userId;
		ininUserInfo(data);
		$("input[name='realName']").focus();//设置姓名框默认聚焦
	} else {
		$("input[name='mobile']").attr("readonly", false);
		$("input[name='mobile']").focus();//设置手机输入框默认聚焦
	}
	//门诊日期默认设置为今天
	$("input[name='outpatientDate']").val(today);
	
	//为复诊时，需要查询出就诊原因
	if (menuType == CONST.menuType[0] && outpatientId != null) {//门诊
		$(".outpatientReason-li").show();
		$.post(basePath + "/outpatient/findOutpById", {outpatientId : outpatientId}, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time : 1000});
				return;
			}
			$(".outpatientReason").text(ret.data.outpatientReason);
		});
	}
	
	//孕妇管理中，信息为只读
	if (menuType == CONST.menuType[1]) {
		$("#submitForm input").attr("readonly", true);
	} else {//门诊
		//日历启动
		layui.use('laydate', function() {
			var laydate = layui.laydate;
		});
		//生日自动算出年龄，且年龄的选择范围是16-45岁
		var minDate = new Date().getFullYear() - 45 + "-01-01";
		var maxDate = new Date().getFullYear() - 16 + "-12-31";
		$("#birthday").click(function() {
			layui.laydate({
				elem : this,
				festival : true,
				istoday: false,
				min : minDate,
				max : maxDate
			});
			$("#laydate_table td, #laydate_today, #laydate_ok").unbind("click").click(function() {
				setTimeout(function() {
					var age = TOOL.getAge($("#birthday").val());
					$(".age").text(age);
				}, 100);
				$("#birthday-error").text("");
			});
		});
		//预产期和末次月经的换算，并算出孕周
		$("#lastPeriod").click(function() {
			layui.laydate({
				elem : this,
				festival : true,
				min : laydate.now(-280),
				max : laydate.now()
			});
			setBylastPeriod();
			$("#laydate_table td, #laydate_today, #laydate_ok").unbind("click").click(function() {
				setBylastPeriod();
			});
		});
		$("#expectedDate").click(function() {
			layui.laydate({
				elem : this,
				festival : true,
				min : laydate.now(),
				max : laydate.now(280)
			});
			setByExpectedDate();
			$("#laydate_table td, #laydate_today, #laydate_ok").unbind("click").click(function() {
				setByExpectedDate();
			});
		});
	}
	
	//手机号变动时，加载出对应的用户信息
	$("#mobile").change(function() {
		var mobile = $(this).val().trim();
		if (mobile == "" || mobile.length != 11) {
			return;
		}
		delete data["userId"];
		data.mobile = $(this).val();
		ininUserInfo(data);
	});
	
	//孕前身高体重变化时，计算出孕前bmi
	$("#weight, #height").change(function() {
		var bmi = TOOL.getBmi($("#height").val(), $("#weight").val());
		$(".pregBmi").text(bmi);
		//$(".pregWeightStatus").text("(" + CONST.weightStatus[getStatusByBmi(bmi)] + ")");
	});
	//当前体重变化时，计算当前bmi
	$("#averageValue").change(function() {
		var bmi = TOOL.getBmi($("#height").val(), $("#averageValue").val());
		$(".currBmi").text(bmi);
		//$(".currWeightStatus").text("(" + CONST.weightStatus[getStatusByBmi(bmi)] + ")");
	});
	
	//点击提交表单
	$("#submit-btn").click(function() {
		$("#submitForm").submit();
	});
	
	//表单验证
	if (menuType != CONST.menuType[1]) {//孕妇管理只读，不需验证
		var validator = $("#submitForm").validate({
	        errorPlacement : function(error, element) {
	            // Append error within linked label
	            $(element)
	                .closest("form")
	                .find("label[for='" + element.attr( "id" ) + "']")
	                .siblings('.errorP')
	                .append( error );
	        },
	        errorElement : "span",
	        debug : true,
	        rules : {
	            mobile:{required:true, digits:true, isMobile:true},
	            realName:{required:true, isChinese:true, maxlength:6},
	            birthday:{required:true, dateISO:true},
	            weight:{required:true, number:true, range:[20,150]},
	            height:{required:true, digits:true, range:[50,200]},
	            pregnantType:{required:true},
	            lastPeriod:{required:true, dateISO:true},
	            expectedDate:{required:true, dateISO:true},
	            outpatientNum:{maxlength:30, numEn:true},
	            averageValue:{required:true, number:true, range:[20,150]},
	            muscle:{number:true, range:[1,100]},
	            bodyFatRate:{number:true, range:[1,100]},
	            moistureContent:{number:true, range:[1,100]},
	            basalMetabolism:{digits:true, maxlength:4},
	            healthNum:{maxlength:30, numEn:true},
	            sugarValue:{number:true, range:[1,20]}
	        },
	        messages : {
	        	mobile:{required:"请输入手机号", digits:"手机号只能为数字"},
	            realName:{required:"请输入姓名", maxlength:"不能超过6个字符"},
	            birthday:{required:"请输入出生日期", dateISO:"请输入正确的日期"},
	            weight:{required:"请输入孕前体重", number:"请输入正确值",range:"体重不在:20~150"},
	            height:{required:"请输入身高", digits:"请输入正整数", range:"身高不在:50~200"},
	            pregnantType:{required:"请选择怀孕类型"},
	            lastPeriod:{required:"请输入末次月经", dateISO:"请输入正确的日期"},
	            expectedDate:{required:"请输入预产期", dateISO:"请输入正确的日期"},
	            outpatientNum:{maxlength:"最多可输入30个字符"},
	            averageValue:{required:"请输入目前体重", number:"请输入正确值",range:"体重不在:20~150"},
	            muscle:{number:"输入1-100的数字", range:"输入1-100的数字"},
	            bodyFatRate:{number:"输入1-100的数字", range:"输入1-100的数字"},
	            moistureContent:{number:"输入1-100的数字", range:"输入1-100的数字"},
	            basalMetabolism:{digits:"请输入正整数", maxlength:"不能超过4位数"},
	            healthNum:{maxlength:"最多可输入30个字符"},
	            sugarValue:{number:"输入1-20的数字", range:"输入1-20的数字"}
	        },
	        submitHandler : function() {
	        	if (outpatientId == null && userId != null) {//新建档，但是所填号码已经注册了就不能走下一步
	        		layer.alert('该孕妇已建立档案，请先扫码完成报到', {
	                    icon: 0,
	                    title: '提示',
	                    btn: ['知道了'],
	                    btnAlign: 'c',
	                    yes: function (index, layero) {
	                    	window.location.href = "outpatient.html?hospitalId=" + hospitalId;
	                    }
	                });
	        		return;
				}
	        	
	        	var userParams = {
	    			userId : userId,
	    			hospitalId : hospitalId,
	    			mobile : $("input[name='mobile']").val(),
	    			realName : $("input[name='realName']").val().trim(),
	    			birthday : $("input[name='birthday']").val(),
	    			weight : $("input[name='weight']").val(),
	    			height : $("input[name='height']").val(),
	    			pregnantType : $("#pregnantType").val(),
	    			lastPeriod : $("input[name='lastPeriod']").val(),
	    			expectedDate : $("input[name='expectedDate']").val(),
	    			outpatientNum : $("input[name='outpatientNum']").val().trim(),
	    			healthNum : $("input[name='healthNum']").val().trim(),
	    			isDiabetes : $("#isDiabetes").val()
	    		}
	        	if (outpatientId != null) {
	        		userParams.isAddOutp = 0;//不新增门诊记录
				} else {
					userParams.isAddOutp = 1;//新增门诊记录
				}
	    		var physicalParams = {
	        		averageValue : $("input[name='averageValue']").val(),
	        		muscle : $("input[name='muscle']").val(),
	        		bodyFatRate : $("input[name='bodyFatRate']").val(),
	        		moistureContent : $("input[name='moistureContent']").val(),
	        		basalMetabolism : $("input[name='basalMetabolism']").val()
				};
	    		var postData = {userParams : JSON.stringify(userParams), physicalParams : JSON.stringify(physicalParams)};
	    		//保存用户和体重数据
	    		$.post(basePath + "/user/addOrUpdateUserInfo", postData, function(ret) {
	    			if (ret.msg != 1) {
	    				layer.msg(ret.msgbox, {time : 1000});
	    				return;
					}
    				userId = ret.data.userId;//如果为新建档，需要返回正确的userId
    				if (jumpType == 0) {
						//隐藏掉弹出框
    		        	$("input[name='isSuccess']").val(1);
					} else {
						//跳转到膳食调查页面
						outpatientId = (outpatientId != null) ? outpatientId : ret.data.outpatientId;
						window.location.href = "dietSurvey.html?userId=" + ret.data.userId + "&outpatientId=" + outpatientId;
					}
	    		}, "json");
	    		//保存血糖数据，不为空时才保存
	    		var sugarValue = $("input[name='sugarValue']").val();
	    		if (sugarValue != "") {
	    			var sugarData = {
        				userId : userId,
    	    			hospitalId : hospitalId,
    	    			sugarValue : sugarValue,
    	    			mealType : $("#mealType").val()
    	    		};
	    			$.post(basePath + "/sugar/saveSugarRecord", sugarData, function(ret) {
		    			if (ret.msg != 1) {
		    				layer.msg(ret.msgbox, {time : 1000});
						}
		    		});
				}
			}
	    });
	}
    
    jQuery.validator.addMethod('num1_100', function(value, element) {
        var length = value.length;
        var mobile =/^(?:\d?\d|100)$/;
        return this.optional(element) || (length > 0 && mobile.test(value));
    },'输入1-100的数字');
    jQuery.validator.addMethod('numEn', function(value, element) {
    	// /(?=^.*?\d)(?=^.*?[a-zA-Z])^[0-9a-zA-Z]{4,23}$/ 只能输入纯英文或纯数字
        var length = value.length;
        var mobile =/^[0-9a-zA-Z]+$/;
        return this.optional(element) || (length > 0 && mobile.test(value));
    },'只能输入数字和英文字母');
    jQuery.validator.addMethod('mameV', function(value, element) {
    	// /^([\u4e00-\u9fa5]+|[a-zA-Z0-9]+)$/ 只能输入纯汉字或纯英文
    	// 不能输入特殊 /^[\w\u4e00-\u9fa5]+$/gi
        var length = value.length;
        var mobile = /[\^\:\?\*"\>\<\|]+/g;
        return this.optional(element) || (length > 0 && !mobile.test(value));
    },'不能输入特殊字符');
    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请填写正确的手机号码");
    jQuery.validator.addMethod("isChinese", function(value, element){
    	var obj = /^[\u4E00-\u9FA5]+$/;
    	return this.optional(element) || (obj.test(value));
	}, "请输入中文");
});
	
//加载用户信息和体检信息
function ininUserInfo(postData) {
	//加载用户信息
	$.post(basePath + "/user/findUserByIdMobile", postData, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		if (ret.data == null || ret.data.mobile == null) {//没有返回手机号表示没有信息
			var mobile = $("input[name='mobile']").val();
			$("#submitForm")[0].reset();
			$("input[name='mobile']").val(mobile);
			//门诊日期默认设置为今天
			$("input[name='outpatientDate']").val(today);
			//非输入框的值也需重置
			$(".age").text("");
			$(".pregnantWeek").text("");
			$(".pregBmi").text("");
			$(".pregWeightStatus").text("");
			$(".currBmi").text("");
			$(".currWeightStatus").text("");
			userId = undefined;
			return;
		}
		//情况错误信息
		$(".error").text("");
		userId = ret.data.userId;
		//孕妇管理中设置上次门诊日期
		if (menuType == CONST.menuType[1]) {//孕妇管理
			$(".outpatientDate-lab").text("上次门诊日期");
			//上次门诊日期设置为最近一次完成诊断的时间
			$("input[name='outpatientDate']").val(ret.data.finishTime);
		}
		$(".realNameSpan").text(ret.data.realName);
		$(".outpNumSpan").text(ret.data.outpatientNum);
		$(".ageSpan").text(ret.data.age);
		$(".pweekSpan").text(ret.data.pregnantWeek[0]);
		$(".pregTypeSpan").text(CONST.pregnantType[ret.data.pregnantType]);
		$("input[name='mobile']").val(ret.data.mobile);
		$("input[name='mobile']").val(ret.data.mobile);
		$("input[name='realName']").val(ret.data.realName);
		$("input[name='birthday']").val(ret.data.birthday);
		$(".age").text(ret.data.age);
		$("input[name='weight']").val(ret.data.weight);
		$("input[name='height']").val(ret.data.height);
		$("#pregnantType").val(ret.data.pregnantType);
		$("input[name='lastPeriod']").val(ret.data.lastPeriod);
		$("input[name='expectedDate']").val(ret.data.expectedDate);
		$(".pregnantWeek").text(ret.data.pregnantWeek[0] + "周" + ret.data.pregnantWeek[1] + "天");
		$(".pregBmi").text(ret.data.bmi);
		//$(".pregWeightStatus").text("(" + CONST.weightStatus[ret.data.weightStatus] + ")");
		$("input[name='outpatientNum']").val(ret.data.outpatientNum);
		$("input[name='healthNum']").val(ret.data.healthNum);
		$("#isDiabetes").val(ret.data.isDiabetes);
	});
	//加载体检信息
	if (userId == null) {
		return;
	}
	postData.userId = userId;
	$.post(basePath + "/physical/findUserLastphysical", postData, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		$(".currWeightSpan").text(ret.data.averageValue);
		$("input[name='averageValue']").val(ret.data.averageValue);
		$(".currBmi").text(ret.data.bmi);
		//$(".currWeightStatus").text("(" + CONST.weightStatus[ret.data.weightStatus] + ")");
		var muscle = "", bodyFatRate = "", moistureContent = "", basalMetabolism = "";
		if (menuType == CONST.menuType[1]) {//孕妇管理
			muscle = (ret.data.muscle == 0) ? "/" : ret.data.muscle;
			bodyFatRate = (ret.data.bodyFatRate == 0) ? "/" : ret.data.bodyFatRate;
			moistureContent = (ret.data.moistureContent == 0) ? "/" : ret.data.moistureContent;
			basalMetabolism = (ret.data.basalMetabolism == 0) ? "/" : ret.data.basalMetabolism;
		} else {
			muscle = (ret.data.muscle == 0) ? "" : ret.data.muscle;
			bodyFatRate = (ret.data.bodyFatRate == 0) ? "" : ret.data.bodyFatRate;
			moistureContent = (ret.data.moistureContent == 0) ? "" : ret.data.moistureContent;
			basalMetabolism = (ret.data.basalMetabolism == 0) ? "" : ret.data.basalMetabolism;
		}
		$("input[name='muscle']").val(muscle);
		$("input[name='bodyFatRate']").val(bodyFatRate);
		$("input[name='moistureContent']").val(moistureContent);
		$("input[name='basalMetabolism']").val(basalMetabolism);
	});
	//加载血糖信息
	$.post(basePath + "/sugar/findUserLastSugar", postData, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		if (ret.data != null) {
			$("input[name='sugarValue']").val(ret.data.averageValue);
			$("#mealType").val(ret.data.testTimeState);
		}
	});
	
}
//末次月经变化时，设置对应的信息也变化
function setBylastPeriod() {
	setTimeout(function() {
		var expDate = TOOL.getPregancyDay($("#lastPeriod").val());
		var pweek = TOOL.getPregnantWeek(today, expDate);
		$("#expectedDate").val(expDate);
		$(".pregnantWeek").text(pweek[0] + "周" + pweek[1] + "天");
	}, 100);
	$("#expectedDate-error").text("");
}
//预产期变化时，设置对应的信息也变化
function setByExpectedDate() {
	setTimeout(function() {
		var lastPeriod = TOOL.getLastPeriodByExp($("#expectedDate").val());
		var pweek = TOOL.getPregnantWeek(today, $("#expectedDate").val());
		$("#lastPeriod").val(lastPeriod);
		$(".pregnantWeek").text(pweek[0] + "周" + pweek[1] + "天");
	}, 100);
	$("#lastPeriod-error").text("");
}
/*--------------------  layui 弹 框     ---------------------- --*/	
function ShowDiv() {
	layui.use(['layer'], function() {
		var layer = layui.layer;
		//孕妇管理页面没有保存按钮
		var btn = (menuType == CONST.menuType[1]) ? [] : ['保存','取消'];
		layer.open({
			title:'孕妇信息',
  			type: 1,
  			area: ['1120px'], //宽高
  			content: $('#submitForm'),
  			btn: btn,
			yes: function(index, layero) {
				$('#submitForm').submit();
				if ($("input[name='isSuccess']").val() == 1) {
					layer.close(index);
					layer.msg("保存成功", {time : 800});
					/*setTimeout(function() {
						window.location.reload();
					}, 600);*/
					var data = {hospitalId : hospitalId, userId : userId};
					ininUserInfo(data);
				}
			}
		});
	});
}
