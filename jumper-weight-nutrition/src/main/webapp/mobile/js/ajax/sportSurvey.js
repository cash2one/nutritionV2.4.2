//页面所需后台的数据
var skipType = GetQueryString("skipType");
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//运动列表赋值
	$.post(basePath + "/sport/listSportsByName", function(ret) {
		if (ret.msg == 1) {
			var html = "";
			for (var int = 0; int < ret.data.length; int++) {
				var sport = ret.data[int];
				html += "<li class='mui-table-view-cell'>" + 
							"<span class='s-n add-icon "+JudgeIcon()+"'>" + sport.name + "</span><span class='mui-badge mui-badge-primary timeLength' sportId='" + sport.id + "'></span>" +
		                "</li>";
			}
			$(".sportList").prepend(html);
			function JudgeIcon(){
				if(sport.name=='步行'){
					return 'walk';
				}else if(sport.name=='看书'){
					return 'book';
				}else if(sport.name=='静坐'){
					return 'sit';
				}else if(sport.name=='轻家务'){
					return 'duties';
				}else if(sport.name=='上下楼梯'){
					return 'stairs';
				}else if(sport.name=='瑜伽'){
					return 'yoga';
				}else if(sport.name=='骑自行车'){
					return 'bike';
				}else if(sport.name=='孕妇体操'){
					return 'gymnastics';
				}else if(sport.name=='一般慢跑'){
					return 'run';
				}else if(sport.name=='游泳'){
					return 'swim';
				}else if(sport.name=='爬山'){
					return 'hiking';
				}
			}
		} else {
			mui.toast(ret.msgbox);
		}
	}, "json");
	
	//查询出昨日的运动信息,并赋初始值
	var yestDay = getDateByDaysLate(new Date(), -1);
	if(skipType == "home"){//如果是从首页进来，查询的是今天的运动记录
		yestDay = getDateByDaysLate(new Date(),0);
	}
	var data = {userId : userId, date : yestDay};
	$.post(basePath + "/sport/listSportRecordByDate", data, function(ret) {
		if (ret.msg != 1) {
			mui.toast(ret.msgbox);
			return;
		}
		for (var int = 0; int < ret.data.recordList.length; int++) {
			var record = ret.data.recordList[int];
			$(".timeLength").each(function(i) {
				if (record.sportId == $(this).attr("sportId")) {
					var minHour = minToHour(record.timeLength);
					var text = (minHour[0] == 0) ? minHour[1] + "分钟" : minHour[0] + "小时" + minHour[1] + "分钟";
					$(this).text(text);
				}
			});
		}
	}, "json");
	
	
	//点击完成
	$(".nextStep").click(function() {
		var recordList = new Array();
		$(".timeLength").each(function(i) {
			var timeLength = hourToMin($(this).text());
			if (timeLength > 0) {
				var record = {sportId : $(this).attr("sportId"), timeLength : timeLength};
				recordList.push(record);
			}
		});
		/*if (recordList.length == 0) {
			mui.toast("请添加运动记录");
			return;
		}*/
		//判断总时长是否超过24小时
		var totalTime = 0;
		for (var i in recordList) {
			totalTime += recordList[i].timeLength;
		}
		if (totalTime >= 24 * 60) {
			mui.toast("总时长不能超过24小时");
			return;
		}
		
		var userDefinedSport = $("input[name='userDefinedSport']").val().trim();
		if (userDefinedSport.length > 30) {
			mui.toast("自定义运动不能超过30个字符");
			return;
		}
		
		//保存运动记录
		var isSuccess = true;
		if (recordList.length > 0) {
			var data = {userId : userId, date : yestDay, recordList : JSON.stringify(recordList)};
			$.post(basePath + "/sport/addSportRecordByDate", data, function(ret) {
				if (ret.msg != 1) {
					isSuccess = false;
					mui.toast(ret.msgbox);
				}
			}, "json");
		}
		if (!isSuccess) {
			return;
		}
		if(skipType == "home"){
			//从首页进来保存成功以后返回首页
			window.location.href = "homePage.html?hospitalid="+hospitalId+"&userid="+userId;
		}else{
			//保存一条门诊记录
			var outpatientReason = $.cookie('outpReason');
			var opData = {hospitalId : hospitalId, userId : userId, userDefinedSport : userDefinedSport, outpatientReason : outpatientReason};
			$.post(basePath + "/outpatient/addOutpatient", opData, function(ret) {
				if (ret.msg != 1) {
					mui.toast(ret.msgbox);
					return;
				}
				var outpatientId = ret.data;
				//跳转
				window.location.href = "firstCheck.html?outpatientId=" + outpatientId;
			}, "json");
		}
		
	});
	
});