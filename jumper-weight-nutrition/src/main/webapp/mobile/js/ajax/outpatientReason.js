/**
 **	复诊就诊原因js
 */
var outpatientId = "";
$(function(){
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//查询目前体重
	$.ajax({
		url:basePath+"/physical/findUserLastphysical",
		type:"POST",
		dataType:"json",
		data:{userId:userId, hospitalId:hospitalId},
		success:function(json){
			if(json.msg == 1){
				var data = json.data;
				var currentWeight = data.averageValue;
				$("#averageValue").val(currentWeight);
			}
		}
	});
	
	//加载医院设置的异常原因列表
	var param = {
			hospitalId:hospitalId,
			type:1//复诊
		};
	$.ajax({
		url:basePath+"/settings/findHospitalOutpatientReason",
		type:"POST",
		dataType:"json",
		data:param,
		async:false,
		success:function(json){
			if(json.msg == 1){
				var data = json.data;
				var html = "";
				for(var i=0; i<data.length; i++){
					html += '<li class="mui-table-view-cell mui-radio mui-left">'+
						'<input name="outpReason" type="radio" value="'+data[i].id+'">'+data[i].outpatientReason+'</li>';
				}
				$("#outpReason").html(html);
			}
		}
	});
	
});

//提交信息
$("#submitOutpReason").click(function() {
	//添加就诊原因
	var outpReason = $("input[type='radio']:checked").val();
	if(outpReason == null || outpReason == ""){
		mui.toast("请选择就诊原因");
		return;
	}
	
	//添加一条复诊记录
	var opData = {hospitalId : hospitalId, userId : userId, outpatientReason : Number(outpReason)};
	var isSuccess = true;
	$.post(basePath + "/outpatient/addOutpatient", opData, function(ret) {
		if (ret.msg != 1) {
			isSuccess = false;
			mui.toast(ret.msgbox);
			return;
		}
		outpatientId = ret.data;
	});
	if (!isSuccess) {
		return;
	}
	
	//添加体重记录
	var currentWeight = $("#averageValue").val();
	if(currentWeight != null && currentWeight != ""){
		$.ajax({
			url:basePath+"/physical/saveWeightRecord",
			type:"POST",
			dataType:"json",
			async:false,
			data:{userId:userId,value:currentWeight,hospitalId:hospitalId},
			success:function(json){
				if(json.msg == 0){
					mui.toast(json.msgbox);
					return;
				}
			}
		});
	}
	
	//跳转
	window.location.href = "repeatCheck.html?outpatientId="+outpatientId;
});