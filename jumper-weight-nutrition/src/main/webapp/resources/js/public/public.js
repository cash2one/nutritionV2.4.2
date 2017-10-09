/*全局变量*/
var messageAll = $("#imMessageCenterBox");
var reIm = /\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/;

// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);
// 获取带"/"的项目名，如：/uimcardprj
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
/* var url ="http://localhost:8081/common"; */
var url = localhostPaht + projectName;
//var url = $("#commonUrl").val();
console.log("===================================================>>>>>>");
console.log(url);
console.log(localhostPaht);
console.log(projectName);
/* 登录 */
function independentModeLogin1(name, pas) {
	loginInfo.identifier = name;
	loginInfo.userSig = pas;
	webimLogin();
}
function webimLogin() {
	webim.login(loginInfo, listeners, options, function(resp) {
		loginInfo.identifierNick = resp.identifierNick;// 设置当前用户昵称
	}, function(err) {
		alert(err.ErrorInfo);
	});
};
// 发送消息(文本或者表情)
function onSendMsg() {
	if (!selToID) {
		art.dialog({
			content : '未找到用户信息,数据错误！',
			icon : 'warning',
			time : 2
		});
		$("#messageBody").val('');
		return;
	}
	// 获取消息内容
	var msgtosend = tool.trimStr(document.getElementById("messageBody").value);
	var msgLen = webim.Tool.getStrBytes(msgtosend);
	if (msgtosend.length < 1) {
		art.dialog({
			content : '消息不能为空！',
			icon : 'warning',
			time : 2
		});
		$("#messageBody").val('');
		return;
	}
	var maxLen, errInfo;
	if (selType == webim.SESSION_TYPE.C2C) {
		maxLen = webim.MSG_MAX_LENGTH.C2C;
		errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
	} else {
		maxLen = webim.MSG_MAX_LENGTH.GROUP;
		errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
	}
	if (msgLen > maxLen) {
		alert(errInfo);
		return;
	}
	if (!selSess) {
		var selSess = new webim.Session(selType, selToID, selToID, Math
				.round(new Date().getTime() / 1000));
	}
	var isSend = true;// 是否为自己发送
	var seq = -1;// 消息序列，-1表示sdk自动生成，用于去重
	var random = Math.round(Math.random() * 4294967296);// 消息随机数，用于去重
	var msgTime = Math.round(new Date().getTime() / 1000);// 消息时间戳
	var subType;// 消息子类型
	if (selType == webim.SESSION_TYPE.C2C) {
		subType = webim.C2C_MSG_SUB_TYPE.COMMON;
	} else {
		subType = webim.GROUP_MSG_SUB_TYPE.COMMON;
	}
	var msg = new webim.Msg(selSess, isSend, seq, random, msgTime,
			loginInfo.identifier, subType, loginInfo.identifierNick);
	// huangzg 2017-01-10 add 添加了consultantId
	var y = '{"busCode":' + busCode + ',"consultantId":' + consultantId + '}';

	var text_obj, face_obj, tmsg, emotionIndex, emotion, restMsgIndex;
	// 解析文本和表情
	var expr = /\[[^[\]]{1,3}\]/mg;
	var emotions = msgtosend.match(expr);
	if (!emotions || emotions.length < 1) {
		text_obj = new webim.Msg.Elem.Text(msgtosend);
		msg.addText(text_obj);
	} else {

		for (var i = 0; i < emotions.length; i++) {
			tmsg = msgtosend.substring(0, msgtosend.indexOf(emotions[i]));
			if (tmsg) {
				text_obj = new webim.Msg.Elem.Text(tmsg);
				msg.addText(text_obj);
			}
			emotionIndex = webim.EmotionDataIndexs[emotions[i]];
			emotion = webim.Emotions[emotionIndex];

			if (emotion) {
				face_obj = new webim.Msg.Elem.Face(emotionIndex, emotions[i]);
				msg.addFace(face_obj);
			} else {
				text_obj = new webim.Msg.Elem.Text(emotions[i]);
				msg.addText(text_obj);
			}
			restMsgIndex = msgtosend.indexOf(emotions[i]) + emotions[i].length;
			msgtosend = msgtosend.substring(restMsgIndex);
		}
		if (msgtosend) {
			text_obj = new webim.Msg.Elem.Text(msgtosend);
			msg.addText(text_obj);
		}
	}
	var z = new webim.Msg.Elem.Custom(y);
	var i1 = "busCode=" + busCode + "";
	var i2 = "consultantId=" + consultantId + "";
	var i3 = "sender=" + tName + "";
	var iii = i1 + "&" + i2 + "&" + i3;
	var of = {
		"PushFlag" : 0,
		"Ext" : iii
	};
	msg.addCustom(z);
	msg.SyncOtherMachine = 1;
	msg.OfflinePushInfo = of;
	/*
	 * webim.sendMsg(msg, function (resp) { if (selType ==
	 * webim.SESSION_TYPE.C2C) {//私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
	 * addMsg(msg); } webim.Tool.setCookie("tmpmsg_" + selToID, '', 0);
	 * $("#messageBody").val(''); }, function (err) { alert(err.ErrorInfo);
	 * $("#messageBody").val(''); });
	 */

	$.ajax({
		url : url + "/im/send_msg",
		type : "POST",
		dataType : "json",
		data : {
			"sender" : tName,
			"receiver" : selToID,
			"busCode" : busCode,
			"consultantId" : consultantId,
			"content" : msgtosend,
			"type" : 1
		},
		success : function(json) {
			console.info(json.data.msgId+"=============");
			console.log("发送返回=======================================================>");
			console.log(json);
			var msgId = json.data.msgId;
			addMsg(msg,msgId);
		}
	});
}
/* 增加一条消息 */
function addMsg(msg,msgId) {
//	alert(msgId+"*******************");
	console.info(msgId);
	var isSelfSend, fromAccount, sessType, subType, time, messageBody1;
	time = webim.Tool.formatTimeStamp(msg.getTime());
	messageBody1 = $("#messageBody").val();
	fromAccount = msg.getFromAccount();
	if (!fromAccount) {
		fromAccount = '';
	}
	sessType = msg.getSession().type();
	subType = msg.getSubType();
//	alert(msgId);
	newMessage(rightMessage({
		msgId : msgId,
		time : time,
		name : fromAccount,
		messageBody : messageBody1
	}));
	$("#messageBody").val('');
}
/* 接收到一条消息 */
function addMsg1(msg) {
	var isSelfSend, fromAccount, sessType, subType, time, messageBody1;
	time = webim.Tool.formatTimeStamp(msg.getTime());
	messageBody1 = msg.elems[0].content.text;
	/* fromAccount = msg.fromAccountNick; */
	fromAccount = recveNickName;
	var datas = msg.elems[1].content.data;
	var msgId = JSON.parse(datas).sendMsgId;
	if (!fromAccount) {
		fromAccount = '';
	}
	sessType = msg.getSession().type();
	subType = msg.getSubType();
	newMessage(leftMessage({
		msgId :msgId,
		time : time,
		niceName : fromAccount,
		messageBody : messageBody1
	}));

}
/* 接收到一条消息 */
function addMsgOld1(msg) {
	var time = msg.sendTime;
	var niceName = recveNickName;
	var messageBody1 = msg.msgContent;
	var msgId = msg.msgId;
	oldMessage(leftMessage({
		time : time,
		niceName : niceName,
		msgId : msgId,
		messageBody : messageBody1
	}));

}
function addMsg2(msg) {
	var isSelfSend, fromAccount, sessType, subType, time, messageBody1;
	time = msg.sendTime;
	messageBody1 = msg.msgContent;
	fromAccount = msg.sendChatId;
	if (!fromAccount) {
		fromAccount = '';
	}
	sessType = msg.getSession().type();
	subType = msg.getSubType();
	newMessage(rightMessage({
		nickName:niceName,
		time : time,
		name : fromAccount,
		messageBody : messageBody1
	}));

}

function addMsgOld2(msg,json,msgId) {
	
	console.info(msg.msgType+"**************"+msg.msgContent);
//	alert(msg.msgType+"**************"+msg.msgContent.backMsgId);
	var a = msg.msgContent;
//	JSON.parse(a);
//	alert(msg.msgType+"**************"+msg.msgContent.backMsgId+"**************"+a.backMsgId); 
	var tempStr = msg.msgContent ;
	temp = tempStr.slice(57,70);
	if(msg.msgType){
		if(msg.msgType == 15){
			oldMessage(checkHistoryMsg(tempStr,temp,msgId));
			return;
		}
	}
	var time = msg.sendTime;
	var niceName = recveNickName;
	var messageBody1 = msg.msgContent;
	var msgId = msg.msgId;
	oldMessage(rightMessage({
		nickName:niceName,
		time : time,
		msgId : msgId,
		messageBody : messageBody1
	}));

}
//显示历史消息
function checkHistoryMsg(tempStr,temp,msgId){
	/*var message = '<div class="rightM" id="'
		+ opt.msgId
		+ '"><p id="optTime" class="timeM">'
		+ opt.time
		+ '</p><div class="centerM"><span class="nameM" title="'
		+ userNickName
		+ '">'
		+ userNickName
		+ '</span><p class="pointM"></p><i class="fr"><img src="'
		+ userHeadImg
		+ '"></i><a href="javascript:void(0);" onclick=recallMsg($(this))>撤回消息</a><p id="optMessageBody" class="messageB fr">'
		+ opt.messageBody + '</p></div></div>';*/
		var message = '<div class="rightM" id="'+ msgId+ '"><p id="optTime" class="timeM">'+"您撤回了一条消息"+'</p><div class="centerM"></div></div>';
//		$("#" + temp + "").replaceWith('<p id="optTime" class="timeM">'+"您撤回了一条消息");
		console.info(tempStr+"==="+temp);
		return message;
}

/* 接收到一条图片消息 */
function addImg(msg, boo) {
	var isSelfSend, fromAccount, sessType, subType, time, messageBody1;
	time = webim.Tool.formatTimeStamp(msg.getTime());
	messageBody1 = msg.elems[0].content.text;
	fromAccount = msg.getFromAccount();
	if (!fromAccount) {
		fromAccount = '';
	}
	try{
		var msgIdList = JSON.parse(msg.elems[1].content.data);
	}catch(err){
		throw "服务端返回的参数格式有误";
	}
	var msgId = msgIdList.sendMsgId;
	var imgArray = msg.elems[0].content.ImageInfoArray;
	var src = imgArray[2].url;
	var width = imgArray[2].width;
	var height = imgArray[2].height;
	var src1 = imgArray[0].url;
	var width1 = imgArray[0].width;
	var height1 = imgArray[0].height;
	if (parseInt(width) > 380) {
		height = height * (380 / parseInt(width));
		width = 380;
	}
	if (boo) {
		newMessage(leftImg({
			msgId:msgId,
			time : time,
			name : fromAccount,
			src : src,
			width : width,
			height : height,
			src1 : src1,
			width1 : width1,
			height1 : height1
		}));
	} else {
		newMessage(rightImg({
			time : time,
			name : fromAccount,
			src : src,
			width : width,
			height : height,
			src1 : src1,
			width1 : width1,
			height1 : height1
		}));
	}

}

/* 接收到一条图片消息 */
function addImgOld(msg, boo) {
	var time = msg.sendTime;
	var imgArray = JSON.parse(msg.msgContent);
	var src = imgArray[1].thumbnail;
	var width = imgArray[1].width;
	var height = imgArray[1].height;
	var src1 = imgArray[0].original;
	var width1 = imgArray[0].width;
	var height1 = imgArray[0].height;
	var msgId = msg.msgId;
	if (parseInt(width) > 380) {
		height = height * (380 / parseInt(width));
		width = 380;
	}
	if (boo) {
		oldMessage(leftImg({
			time : time,
			src : src,
			msgId : msgId,
			width : width,
			height : height,
			src1 : src1,
			width1 : width1,
			height1 : height1
		}));
	} else {
		oldMessage(rightImg({
			time : time,
			src : src,
			msgId : msgId,
			width : width,
			height : height,
			src1 : src1,
			width1 : width1,
			height1 : height1
		}));
	}

}

// 监听新消息事件
// newMsgList 为新消息数组，结构为[Msg]
function onMsgNotify(newMsgList) {
	var sess, newMsg;
	// 获取所有聊天会话
	var sessMap = webim.MsgStore.sessMap();
	for ( var j in newMsgList) {// 遍历新消息
		newMsg = newMsgList[j];
		// var reN = newMsg.getSession().id();
		var reN = newMsg.fromAccount;
		if (reN == selToID) {// 为当前聊天对象的消息
			selSess = newMsg.getSession();
			// 在聊天窗体中新增一条消息
			if (newMsgList[0].elems[0].type == "TIMImageElem") {
				addImg(newMsg, true);
			} else {
				if (newMsgList[0].elems[0].type == "TIMTextElem") {
					addMsg1(newMsg);
				} else {
					if (newMsgList[0].elems[0].type == "TIMCustomElem") {
						var iNowList = newMsgList[0].elems[0].content.data;
						try{
							var testList = JSON.parse(iNowList);
						}catch(err){
							throw "客户端返回的参数格式有误";
						}
						console.log("----------------------------------------------------------------");
						console.log(testList);
						console.log(typeof testList);
						//var id = JSON.parse(newMsgList[0].elems[1].content.data).sendMsgId;"aAu_"+
						
						var f = testList.type;
	//					alert(f+"=============================================");
						if(f == 15){
							var idList = testList.data.datas.backMsgId;
							$("#"+idList).replaceWith('<div class="rightM" id="'+ idList + '"><p id="optTime" class="timeM">对方撤回了一条消息</p><div class="centerM"></div></div>');
						}
						if(f == 14){
							convertSoundMsgToHtml(newMsg);
						}
					/*else {
						addMsg1(newMsg);
					}*/
					}
				}
			}
		} else {
			// $("#im_"+reN).before();
			var number = parseInt($("#im_" + reN).find(".xdd").text());
			$("#im_" + reN).find(".xdd").text(number + 1).show();
			$("#im_" + reN).insertBefore($("#friendList").children().eq(0));

		}
	}
	// 消息已读上报，以及设置会话自动已读标记
	webim.setAutoRead(selSess, true, true);
}

// 解析语音消息元素
function convertSoundMsgToHtml(content) {
	var yyArray = JSON.parse(content.elems[0].content.data);
	var yyArray1 = JSON.parse(content.elems[1].content.data);
	var msgid = yyArray1.sendMsgId;
	var audioId = content.time;
	var time = webim.Tool.formatTimeStamp(content.getTime());
	var url = yyArray.data.content;
	var size = yyArray.data.size;
	var fromAccount = recveNickName;
	newMessage(leftAudio({
		audioId : audioId,
		msgId : msgid,
		time : time,
		niceName : fromAccount,
		src : url,
		size : size
	}));
};

//语音历史记录
function oldConvertSoundMsgToHtml(content) {
	var contentData = content.msgContent;
	var id ="aAu_"+content.msgId;
	var url = content.msgContent;
	var msgId = content.msgId;
	var time = content.sendTime;
	var size = content.size;
	var fromAccount = recveNickName;
	if(contentData.indexOf("{")>-1){//如果是撤回消息结构 则进以下逻辑
		contentData = JSON.parse(content.msgContent)
		url = contentData.data.content;
		size = contentData.data.size;
	}else{
		 url = content.msgContent;
		 msgId = content.msgId;
		 id = "aAu_"+content.msgId;
		 time = content.sendTime;
		 size = content.size;
		 fromAccount = recveNickName;
	}
	
	oldMessage(leftAudio({
		audioId : id,
		msgId : msgId,
		time : time,
		niceName : fromAccount,
		src : url,
		size : size
	}));
}

// 上传图片进度条回调函数
// loadedSize-已上传字节数
// totalSize-图片总字节数
function onProgressCallBack(loadedSize, totalSize) {
	var progress = document.getElementById('upd_progress');// 上传图片进度条
	// progress.value = (loadedSize / totalSize) * 100;
}

// 上传图片
function uploadPic() {
	var uploadFiles = document.getElementById('upload3');
	var file = uploadFiles.files[0];

	var businessType;// 业务类型，1-发群图片，2-向好友发图片
	if (selType == webim.SESSION_TYPE.C2C) {// 向好友发图片
		businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.C2C_MSG;
	} else if (selType == webim.SESSION_TYPE.GROUP) {// 发群图片
		businessType = webim.UPLOAD_PIC_BUSSINESS_TYPE.GROUP_MSG;
	}
	// 封装上传图片请求
	var opt = {
		'file' : file, // 图片对象
		'onProgressCallBack' : onProgressCallBack, // 上传图片进度条回调函数
		// 'abortButton': document.getElementById('upd_abort'), //停止上传图片按钮
		'From_Account' : loginInfo.identifier, // 发送者帐号
		'To_Account' : selToID, // 接收者
		'businessType' : businessType
	// 业务类型
	};
	// 上传图片
	/*
	 * webim.uploadPic(opt, function (resp) { //上传成功发送图片 sendPic(resp);
	 * closeUpload(); }, function (err) { alert(err.ErrorInfo); } );
	 */
	var data = {
		"minWidth" : 80,
		"minHeight" : 80
	};
	$.ajaxFileUpload({
		url : url + '/file/upload_file', // 需要链接到服务器地址
		secureuri : false,
		data : data,// 携带参数
		async : false,
		fileElementId : 'upload3', // 文件选择框的id属性
		dataType : 'json', // 服务器返回的格式
		success : function(data, status) // 相当于java中try语句块的用法
		{
			var json = JSON.parse(data);
			sendPic(json.data.fileUrl);
		},
		error : function(data, status, e) // 相当于java中catch语句块的用法
		{
			var json = JSON.parse(data);
			sendPic(json.data.fileUrl);
			console.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					+ json.data);
		}
	});

}
// 发送图片消息
function sendPic(images) {
	if (!selToID) {
		art.dialog({
			content : '未找到用户信息,数据错误！',
			icon : 'warning',
			time : 2
		});
		return;
	}

	if (!selSess) {
		selSess = new webim.Session(selType, selToID, selToID, Math
				.round(new Date().getTime() / 1000));
	}
	var msg = new webim.Msg(selSess, true, -1, -1, -1, loginInfo.identifier, 0,
			loginInfo.identifierNick);
	var images_obj = new webim.Msg.Elem.Images(images.File_UUID);
	for ( var i in images.URL_INFO) {
		var img = images.URL_INFO[i];
		var newImg;
		var type;
		switch (img.PIC_TYPE) {
		case 1:// 原图
			type = 1;// 原图
			break;
		case 2:// 小图（缩略图）
			type = 3;// 小图
			break;
		case 4:// 大图
			type = 2;// 大图
			break;
		}
		newImg = new webim.Msg.Elem.Images.Image(type, img.PIC_Size,
				img.PIC_Width, img.PIC_Height, img.DownUrl);
		images_obj.addImage(newImg);
	}
	msg.addImage(images_obj);
	// huangzg 2017-01-10 add 添加了consultantId
	var y = '{"busCode":' + busCode + ',"consultantId":' + consultantId + '}';
	var z = new webim.Msg.Elem.Custom(y);
	msg.addCustom(z);

	$.ajax({
		url : url + "/im/send_msg",
		type : "POST",
		dataType : "json",
		data : {
			"sender" : tName,
			"receiver" : selToID,
			"busCode" : busCode,
			"consultantId" : consultantId,
			"content" : images,
			"type" : 2
		},
		success : function(json) {
			try {
				var sObj = json;
			} catch (err) {
				throw "获取数据格式不对";
			}
			if (sObj.msg == 1) {
				var test = JSON.parse(sObj.data.msgContent);
				var str = sObj.data.sendTime + "";
				var time = webim.Tool.formatTimeStamp(str.substring(0, 10));
				var src = test[1].thumbnail;
				var width = test[1].width + "px";
				var height = test[1].height + "px";
				var src1 = test[0].original;
				var width1 = test[0].width;
				var height1 = test[0].height;
				var fromAccount = userNickName;
				var msgId = json.data.msgId;
				if (parseInt(width) > 380) {
					height = height * (380 / parseInt(width));
					width = 380;
				}
				newMessage(rightImg({
					msgId:msgId,
					time : time,
					userNickName : fromAccount,
					src : src,
					width : width,
					height : height,
					src1 : src1,
					width1 : width1,
					height1 : height1
				}));
			}

		}
	});

	// 调用发送图片消息接口
	/*
	 * webim.sendMsg(msg, function (resp) {
	 * if(selType==webim.SESSION_TYPE.C2C){//私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
	 * var fromAccount = msg.getFromAccount(); if (!fromAccount) { fromAccount =
	 * ''; } var time=webim.Tool.formatTimeStamp(msg.getTime()); var
	 * imgArray=msg.elems[0].content.ImageInfoArray; var src=imgArray[0].url;
	 * var width=imgArray[0].width+"px"; var height=imgArray[0].height+"px"; var
	 * src1=imgArray[2].url; var width1=imgArray[2].width; var
	 * height1=imgArray[2].height; if(parseInt(width)>380){ width = 380; }
	 * newMessage(rightImg({ time:time, name:fromAccount, src:src, width:width,
	 * height:height, src1:src1, width1:width1, height1:height1 })); } },
	 * function (err) { alert(err.ErrorInfo); });
	 */
}
// 检查文件类型和大小
function checkFile(obj, fileSize) {
	var picExts = 'jpg|jpeg|png|bmp|gif|webp';
	var photoExt = obj.value.substr(obj.value.lastIndexOf(".") + 1)
			.toLowerCase();// 获得文件后缀名
	var pos = picExts.indexOf(photoExt);
	if (pos < 0) {
		alert("您选中的文件不是图片，请重新选择");
		return false;
	}
	fileSize = Math.round(fileSize / 1024 * 100) / 100; // 单位为KB
	if (fileSize > 30 * 1024) {
		alert("您选择的图片大小超过限制(最大为30M)，请重新选择");
		return false;
	}
	return true;
}

function onGroupInfoChangeNotify() {
};

/* 公用发送消息 */
/* rightMessage */
function rightMessage(opt) {
//	alert(JSON.stringify(opt));
	var sendNick = opt.nickName;
//	alert(sendNick);
	var message = '<div class="rightM" id="' + opt.msgId
			+ '"><p class="timeM">' + opt.time
			+ '</p><div class="centerM"><span class="nameM" title="'
			+ userNickName + '">' + userNickName
			+ '</span><p class="pointM"></p><i class="fr"><img src="'
			+ userHeadImg + '"></i><p class="messageB optMessageBody1 fr">' 
			+ opt.messageBody
			+ '</p><div class="menuMessageCh" style="display: none;" >'+'<span class="photoMessage"></span>'+
			'<a href="javascript:void(0);" style=" margin-right: 10px;" onclick=recallMsg($(this),"'+sendNick+'")>撤回</a></div></div></div>';
//	alert(JSON.stringify(opt))
	return message;
}

//撤回消息
function recallMsg(_this,sendNick) {
	// alert(tName+" "+selToID+" "+busCode+" "+consultantId+" ");
	var optMsgId = _this.parent().parent().parent().attr("id");
	var optMsgTime = _this.parent().parent().val(".class");
//	alert(opt.niceName+"==========");
//	alert(optMsgId + ">>>>>>>>>>>>>>>>>>>>>>>>");
//	alert(optMsgTime+"=======");
//	alert(sendNick);
	console.info("撤回消息的ID："+optMsgId+">>>>>>>>>>>>>>>>>>>>>>>");
	$.ajax({
		url : url + "/im/send_msg",
		type : "POST",
		dataType : "json",
		data : {
//			"content" : "{\"type\":15,\"data\":{\"content\":\"回撤消息\",\"datas\":{\"backMsgId\":+'"+msgContent+"'}}}",// 消息id			
			"content" : '{"type":'+15+',"data":{"content":"回撤消息","datas":{"backMsgId":"'+optMsgId+'","sendNick":"对方"}}}', // 消息id
			"sender" : tName,// 发送者IM账号
			"receiver" : selToID,// 接受者IM账号
			"busCode" : busCode,// 业务代码
			"consultantId" : consultantId,// 服务ID/问题ID
			"type" : 15// 撤回消息类型
		},
		success : function(data) {
			console.info(data.msgbox+"/***/*/*/*/*/*/*/*/*"+data.data.backMsgId);
//			alert(opt.nickName +"==="+userNickName);
			if(data.msg == 1){
				//表示该条消息可以撤回
				var recallMsgId = data.data.backMsgId;
				var currentTime = new Date(); 
				var hours = currentTime.getHours();
				var minutes = currentTime.getMinutes(); 
				var seconds = currentTime.getSeconds();
				var recallTime = hours +":"+ minutes +":"+ seconds;//消息撤回的时间
				$("#" + recallMsgId + "").replaceWith('<p id="optTime" class="timeM">'+recallTime+'</br>'+'<p id="optTime" class="timeM">'+"您撤回了一条消息");
//				alert("撤回消息的ID>>>>>>>" + recallMsgId+ ">>>>>>>>");
				console.info("撤回消息的ID>>>>>>>" + recallMsgId+ ">>>>>>>>");
				return;
			}
			if (data.msg == 0) {
		        art.dialog({
				        content: data.msgbox,
				        icon: 'warning',
				        time: 2			
					});
		        $("#messageBody").val('');
//		        alert(">>>>>>>>>>>>>" + window.JSON.parse(data).msgbox + ">>>>>>>>>>>>>>>>");
		        console.info(">>>>>>>>>>>>>" + data.msgbox + ">>>>>>>>>>>>>>>>");
		        return;
		    }
		}
	});
}
//鼠标右击文字拦截
$(document).on("contextmenu",".rightM .optMessageBody1",function(ev){
/*	var oUl=document.getElementById('menu');*/
	var oEvent=ev||event;	
//	oUl.style.top='50px';
//	oUl.style.left='50px';
	/*oUl.style.display='block';
	$("#menu").show();*/
	$(this).parents(".rightM").siblings().find(".menuMessageCh").hide();
	var b = $(this).nextAll(".menuMessageCh").show();
	oEvent.preventDefault();
	
	//点击屏幕其他地方隐藏div
	$(document).bind('click', function(e) {  
        var e = e || window.event; //浏览器兼容性   
        var elem = e.target || e.srcElement;  
        while (elem) { //循环判断至跟节点，防止点击的是div子元素   
            if (elem.class && elem.class == 'menuMessageCh') {  
                return;  
            }  
            elem = elem.parentNode;  
        }  
        b.hide();
      }); 
    return false;
})
//图片拦截
$(document).on("contextmenu",".rightM .imgMessageBody1",function(ev){
/*	var oUl=document.getElementById('menu');*/
	var oEvent=ev||event;	
//	oUl.style.top='50px';
//	oUl.style.left='50px';
	/*oUl.style.display='block';
	$("#menu").show();*/
	$(this).parents(".rightM").siblings().find(".menuMessageCh").hide();
	var b = $(this).nextAll(".menuMessageCh").show();
	oEvent.preventDefault();
	
	//点击屏幕其他地方隐藏div
	$(document).bind('click', function(e) {  
        var e = e || window.event; //浏览器兼容性   
        var elem = e.target || e.srcElement;  
        while (elem) { //循环判断至跟节点，防止点击的是div子元素   
            if (elem.class && elem.class == 'menuMessageCh') {  
                return;  
            }  
            elem = elem.parentNode;  
        }  
       b.hide();
      }); 
    return false;
})



function leftMessage(opt) {
	//显示对方撤回的消息
//	var a =opt.messageBody;
//	var optMsgType = JSON.parse(a).type;
//	if(optMsgType == 15 && opt.niceName !== userNickName){
//		var message = '<div class="rightM" id="'+ opt.msgId+ '"><p id="optTime" class="timeM">'+"\""+opt.niceName+"\""+"撤回了一条消息"+'</p><div class="centerM"></div></div>';
//		return message;
//	}
	var a = opt.messageBody;
	if(a.indexOf("{")>-1){
		var jsonObj=JSON.parse(a);
		var optMsgType = jsonObj.type;
		if(optMsgType == 15 && opt.niceName !== userNickName){
//			var message = '<div class="rightM" id="'+ opt.msgId+ '"><p id="optTime" class="timeM">'+"\""+opt.niceName+"\""+"撤回了一条消息"+'</p><div class="centerM"></div></div>';
			var message = '<div class="rightM" id="'+ jsonObj.data.datas.backMsgId+ '"><p id="optTime" class="timeM">'+"对方撤回了一条消息"+'</p><div class="centerM"></div></div>';
			return message;
		}
	}
	var message = '<div class="leftM" id="' + opt.msgId + '"><p class="timeM">'
			+ opt.time + '</p><div class="centerM"><span class="nameM" title="'
			+ opt.niceName + '">' + opt.niceName
			+ '</span><p class="pointM"></p><i class="fl"><img src="'
			+ recveImg + '"></i><p class="messageB fl">' + opt.messageBody
			+ '</p></div></div>';
	return message;
}

function rightImg(opt) {
	var message = '<div class="rightM" id="' + opt.msgId
			+ '"><p class="timeM">' + opt.time
			+ '</p><div class="centerM"><span class="nameM" title="'
			+ userNickName + '">' + userNickName
			+ '</span><p class="pointM"></p><i class="fr"><img src="'
			+ userHeadImg
			+ '"></i><p class="messageB fr imgMessageBody1"><img class="showImgIm" data-src="'
			+ opt.src1 + '" data-width="' + opt.width1 + '" data-height="'
			+ opt.height1 + '" src="' + opt.src + '" style="width: '
			+ opt.width + 'px;height: ' + opt.height + 'px;"></p><div class="menuMessageCh" style="display: none" ><span class="photoMessage"></span><a href="javascript:void(0);" onclick=recallMsg($(this))>撤回</a></div></div></div>';
	return message;
}
function leftImg(opt) {
	var message = '<div class="leftM" id="' + opt.msgId + '"><p class="timeM">'
			+ opt.time + '</p><div class="centerM"><span class="nameM" title="'
			+ recveNickName + '">' + recveNickName
			+ '</span><p class="pointM"></p><i class="fl"><img src="'
			+ recveImg
			+ '"></i><p class="messageB fl"><img class="showImgIm" data-src="'
			+ opt.src1 + '" data-width="' + opt.width1 + '" data-height="'
			+ opt.height1 + '" src="' + opt.src + '" style="width: '
			+ opt.width + 'px;height: ' + opt.height + 'px;"></p></div></div>';
	return message;
}

function leftAudio(opt) {
	var message = '<div class="leftM" id="'
			+ opt.msgId
			+ '"><p class="timeM">'
			+ opt.time
			+ '</p><div class="centerM"><span class="nameM" title="'
			+ opt.niceName
			+ '">'
			+ opt.niceName
			+ '</span><p class="pointM"></p><i class="fl"><img src="'
			+ recveImg
			+ '"></i><p class="messageB fl"><i id="audio_'
			+ opt.audioId
			+ '" class="fl audioI"><img src="'
			+ audioIcon
			+ '"/></i><audio class="aAudio" style="display:none;" id="'
			+ opt.audioId
			+ '" src="'
			+ opt.src
			+ '" controls="controls"></audio><span class="fl" style="margin:2px 0 0 8px;">'
			+ opt.size + '</span><i class="dataYl" style="display:none;">'
			+ opt.size + '</i></p></div></div>';
	return message;
}

function newMessage(message) {
	messageAppend(messageAll, message);
	scrollBottom();
};
function oldMessage(message) {
	messageBefore(messageAll, message);
};
function messageBefore(messageAll, message) {
	messageAll.children().eq(0).before(message);
};

function messageAppend(messageAll, message) {
	messageAll.append(message);
};

/* 左侧加载好友 */
function leftFriend(opt) {
	var html = '<li id="' + opt.id + '"><img class="fl" src="' + opt.src
			+ '" alt=""><span class="imUserName fl">' + opt.userName
			+ '</span><p class="xdd fl">' + opt.wdMessage + '</p></li>';
	return html;
}
/* 发送按钮 */
var sendBotton = '<a id="sendMessage" href="javascript:;"  class="sendMessageA noSend" onclick="onSendMsg()">发送</a>';
/* 不能发送按钮 */
var noSendBotton = '<a class="sendMessageBotton nosend" href="javascript:;">发送</a>';
/* 没有好友文字 */
var noFriend = '<p class="noFriend">您暂时没有好友不能聊天</p>';
/* 查看更多按钮 */
var messageMore = '<div class="chag" style="text-align: center;text-decoration: underline;"><a id="moreOldMessage" href="javascript:;">查看记录</a></div>'
/* 滚动到底部 */
function scrollBottom() {
	setTimeout(function() {
		var num = messageAll[0].scrollHeight - messageAll.height();
		messageAll.scrollTop(num);
	}, 300);
};

/* 回车发送消息 */
function onTextareaKeyDown(event) {
	var event = arguments.callee.caller.arguments[0] || window.event;// 消除浏览器差异
	if (event.keyCode == 13) {
		setTimeout(function() {
			onSendMsg();
		}, 0);
	}
};
/* 关闭图片发送框 */
function closeUpload() {
	$("#uploadWindow").hide();
	setTimeout(function() {
		$("#upload3").val("");
	}, 1500);
};
/* 上传图片验证 */
function uploadImg() {
	var text = $("#upload3").val();
	if (text.length == 0) {
		art.dialog({
			content : '请上传图片！',
			icon : 'warning',
			time : 1.5
		});
		return;
	} else {
		if (!reIm.test(text)) {
			art.dialog({
				content : '上传文件必须是图片！',
				icon : 'warning',
				time : 1.5
			});
			return;
		} else {
			uploadPic();
			closeUpload();
		}
	}
};
/* 获取好友列表 */
function getFriendList(name, pageNo, pageSize) {
	if (!pageNo) {
		pageNo = "";
	}
	if (!pageSize) {
		pageSize = "";
	}
	$.ajax({
		url : url + "/base/friend_get_all",
		type : "POST",
		dataType : "json",
		data : {
			"busCode" : busCode,
			"imAccount" : name,
			"pageNo" : pageNo,
			"pageSize" : pageSize
		},
		success : function(json) {
			if (json.msg == 1) {
				var aIow = json.data.conts;
				if (aIow.length == 0) {
					$(".sendMessageInput").find(".sendMessageBotton").remove();
					$(".sendMessageInput").append(noSendBotton);
					$(".imga").remove();
					$("#pleft").append(noFriend);
					$("#messageBody").attr("disabled", "true");
					return;
				}
				var html = "";
				for (var i = 0; i < aIow.length; i++) {
					var src = aIow[i].headUrl;
					var id = "im_" + aIow[i].friendAccount;
					var userName = aIow[i].nickName;
					if (!userName) {
						userName = aIow[i].friendAccount;
					}
					if (!src) {
						src = "../resources/img/2017.jpg";
					}
					var wdMessage = aIow[i].noReadCount;
					html += leftFriend({
						id : id,
						src : src,
						userName : userName,
						wdMessage : wdMessage
					});
				}
				;
				$("#friendList").append(html);
				selToID = $("#friendList").children().eq(0).attr("id")
						.substring(3);
				$("#im_" + selToID).addClass("active");
				getHistory(true);
			} else {
				errorTs("获取好友列表失败");
				/*
				 * errorQt(); errorDefault();
				 */
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			errorTs("获取好友列表失败");
			/*
			 * errorQt(); errorDefault();
			 */
		}
	});
};

/* 查询历史消息,查询更多历史消息 */
function getHistory(oldBoolean, msgId, pageNo, pageSize) {
	if (!msgId) {
		msgId = "";
	}
	if (!pageNo) {
		pageNo = 1;
	}
	if (!pageSize) {
		pageSize = 20;
	}
	// huangzg 2017-01-10 add 添加了consultantId
	$.ajax({
		url : url + "/im/sel_message",
		type : "POST",
		dataType : "json",
		data : {
			"sender" : tName,
			"recevrer" : selToID,
			"busCode" : busCode,
			"startTime" : "",
			"endTime" : "",
			"pageNo" : pageNo,
			"pageSize" : pageSize,
			"msgId" : msgId,
			"consultantId" : consultantId
		},
		success : function(json) {
			console.info(JSON.stringify(json));
//			alert(JSON.stringify(json));
			if (json.msg == 1) {
				var xaw = json.data.dataList;
				if (xaw.length == 0) {
					if (oldBoolean) {
						$("#imMessageCenterBox").empty();
					} else {
						errorTs("没有更多历史消息!");
					}
					return;
				} else {
					$("#imMessageCenterBox").append(messageMore);
					for (var i = 0; i < xaw.length; i++) {
						showHistory(xaw[i],json,msgId);
						$("#imMessageCenterBox").find(".chag").remove();
						$("#imMessageCenterBox").children().eq(0).before(
								messageMore);
					}
				}

			} else {
				errorTs(json.msgbox);
			}
			;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			errorTs("历史消息请求错误!")
		}
	});
}

/* 查询历史消息,查询更多历史消息 */
function showHistory(msg,json,msgId) {
//	alert(">>>>>>>>>>"+json.recevrer); 	
	if (msg.msgType == "TIMImageElem") {
		if (msg.sendChatId == tName) {
			addImgOld(msg, false);
		} else {
			addImgOld(msg, true);
		}

	} else {
		if (msg.msgType == "TIMSoundElem") {
			oldConvertSoundMsgToHtml(msg);

		} else {

			if (msg.sendChatId == tName) {
				addMsgOld2(msg,json,msgId);
			} else {
				addMsgOld1(msg);
			}

		}

	}
}
/* 获取信息错误 */
function errorDefault() {
	$(".userMessage").hide();
	$(".userNoMessage").show();
	$("#messageMore").hide();
	$(".imga").hide();
}
/* 获取信息成功 */
function successDefault() {
	$(".userMessage").show();
	$(".userNoMessage").hide();
	$("#messageMore").show();
	$(".imga").show();
}
/* 其它情况错误 */
function errorQt() {
	$("#messageBody").attr("disabled", true);
	$("#sendMessage").addClass("noSend");
	$(".sendMessage").css("background-color", "#ebebe4");
	$("#messageMore").hide();
	$(".imga").hide();
}
/* 其它情况成功 */
function successQt() {
	$("#messageBody").attr("disabled", false);
	$("#sendMessage").removeClass("noSend");
	$(".sendMessage").css("background-color", "#ffffff");
	$("#messageMore").show();
	$(".imga").show();
}

/* 发送消息之后变为默认样式 */
function defaultSend(text) {
	var timeA = tool.time().substring(10, 16);
	$("#im_" + selToID).find(".titleBottom").text(text);
	$("#im_" + selToID).find(".ttTime").text(timeA);
	$("#messageBody").val('');
}
/* 弹框模型 */
function errorTs(message) {
	art.dialog({
		content : message,
		icon : 'warning',
		time : 1.5
	});
};

;
function clog(obj) {
	console.log(obj);
};

