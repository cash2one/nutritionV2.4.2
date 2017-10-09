/*全局变量*/
 var messageAll=$("#imMessageCenterBox");
 var reIm=/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/;
 var userHeadImg = "../sfile/chat/img/default_user.png";
 var busCode="30101";
 var mobile = "";
 var ajaxArray=[];
 var re = /'/g;
 alert("加载....publicIE");
/*增加一条消息*/
function addMsg(msg) {
	alert("增加一条消息");
	var time = tool.time();
	var msgID = msg.msgId;
	var messageBody1 = msg.msgContent;
	//var messageBody1=tool.trimStr($("#messageBody").val());
	alert(messageBody1);
	newMessage(rightMessage({
		msgId:msgID,
    	time:time,
    	messageBody:messageBody1
    }));

}
/*接收到一条消息*/
function addMsg1(msg){
	var msgID = msg.msgId;
    var time=msg.sendTime;
	messageBody1=msg.msgContent;
	if(msg.sendChatId == selToID ){
		newMessage(leftMessage({
			msgId:msgID,
	    	time:time,
	    	messageBody:messageBody1
	    }));
		messageHz(msgID);
	}else{
		$("#im_"+msg.sendChatId).find(".xdd").show();
		setTimeout(function(){
			$("#im_"+msg.sendChatId).insertBefore($("#userList").children().eq(0));
		},300)
	}


}
/*接收并显示图片消息*/
function addImg(msg,boo){
	var msgID = msg.msgId;
	var time=msg.sendTime;
	var reImg=(msg.msgContent).replace(re,'"');
	var imgArray =JSON.parse(reImg);
	var src = imgArray[1].thumbnail;
	var ySrc = imgArray[0].original;
	var yWidth = imgArray[0].width;
	var yHeight = imgArray[0].height;
	var width,height;
	if(parseInt(yWidth)>400){
		height =yHeight*(400/yWidth);
		width = 400;
	}
	if(msg.sendChatId == selToID ){
		if(boo){
			newMessage(leftImg({
				msgId:msgID,
				time:time,
		    	src:src,
		    	ySrc:ySrc,
		    	width:width,
		    	height:height,
		    	yWidth:yWidth,
		    	yHeight:yHeight
		    }));
		}else{
			oldMessage(leftImg({
				msgId:msgID,
				time:time,
		    	src:src,
		    	ySrc:ySrc,
		    	width:width,
		    	height:height,
		    	yWidth:yWidth,
		    	yHeight:yHeight
		    }));
		}

		messageHz(msg.msgId);
	}else{
		$("#im_"+msg.sendChatId).find(".xdd").show();
		setTimeout(function(){
			$("#im_"+msg.sendChatId).insertBefore($("#userList").children().eq(0));
		},300)
	}

	
}

/*接收到一条消息*/
function addMsgOld1(msg) {
	var msgID = msg.msgId;
    var time=msg.sendTime;
	var messageBody1=msg.msgContent;
	oldMessage(leftMessage({
		msgId:msgID,
    	time:time,
    	messageBody:messageBody1
    }));

}

function addMsgOld2(msg) {
	var msgID = msg.msgId;
	 var time=msg.sendTime;
	 var messageBody1=msg.msgContent;
	oldMessage(rightMessage({
		msgId:msgID,
    	time:time,
    	messageBody:messageBody1
    }));

}
/*公用发送消息*/
/*rightMessage*/
function rightMessage(opt){
var message='<div class="rightMessage" id="'+opt.msgId+'"><div class="messageTime">'+opt.time+'</div><img class="imgR fr" src="'+userHeadImg+'" style="width: 36px; height: 36px;margin-right: 18px;"><p class="rightMessageBody fr"><span>'+opt.messageBody+'</span><span class="talkB"></span></p><div class="clear"></div></div>'
  return message;
}
function leftMessage(opt){
var message='<div class="leftMessage" id="'+opt.msgId+'"><div class="messageTime">'+opt.time+'</div><img class="imgL fl" src="'+userHeadImg+'" style="width: 36px; height: 36px;margin-left: 18px;"><p class="leftMessageBody fl"><span>'+opt.messageBody+'</span><span class="talkS"></span></p><div class="clear"></div></div>';
  return message;
}

function leftImg(opt){
  var img = '<div class="leftMessage" id="'+opt.msgId+'"><div class="messageTime">'+opt.time+'</div><img class="imgL fl" src="'+userHeadImg+'" style="width: 36px; height: 36px;margin-left: 18px;"><p class="leftMessageBody fl"><img class="showImgIm" src="'+opt.src+'" data-src="'+opt.ySrc+'" data-width="'+opt.yWidth+'" data-height="'+opt.yHeight+'" style="width:'+opt.width+';height:"'+opt.height+'""></div></div>';
  return img;
}

function newMessage(message){
	messageAppend(messageAll,message);
	scrollBottom();
};
function oldMessage(message){
	messageBefore(messageAll,message);
};
function messageBefore(messageAll,message){
		messageAll.children().eq(0).before(message);
};

function messageAppend(messageAll,message){
	messageAll.append(message);
};

/*左侧加载好友*/
function leftFriend(opt){
	if(!opt.src){
		opt.src = userHeadImg;
	}
	var html='<li id="'+opt.id+'"><div class="leftImg fl"><img class="lImg" src="'+opt.src+'"><span class="xdd"></span></div><div class="rightTitle fl"><div class="titleTop"><span class="ttName fl" title="'+opt.name+'">'+opt.name+'</span><span class="ttTime fr">'+opt.time+'</span></div><p class="titleBottom">'+opt.msgContent+'</p></div></li>';
	return html;
}
/*发送按钮*/
var sendBotton = '<a id="sendMessage" href="javascript:;" onclick="onSendMsg()">发送</a>';
/*不能发送按钮*/
var noSendBotton = '<a class="noSend" href="javascript:;">发送</a>';
/*邀请注册按钮*/
var register='<a class="register fr">邀请注册</a>';
/*查看更多按钮*/
var messageMore = '<a id="messageMore" href="javascript:;">查看更多...</a>'
/*滚动到底部*/
function scrollBottom(){
	  setTimeout(function () {
       var num=messageAll[0].scrollHeight-messageAll.height();
       messageAll.scrollTop(num);
      }, 300);
};

/*回车发送消息*/
function onTextareaKeyDown(event) {
	var event=arguments.callee.caller.arguments[0]||window.event;//消除浏览器差异
	  if (event.keyCode == 13) {
      setTimeout(function(){
    	  onSendMsg();
   		},0);
  	}
};
/*获取好友列表*/
function getFriendList(name,pageNo,pageSize){
	//console.log(name);
	if(!pageNo){
		pageNo = "";
	}
	if(!pageSize){
		pageSize = "";
	}
	$.ajax({
		url : url+"/chat/friendGetAll.do",
		type : "POST",
		dataType : "json",
		data:{"busCode":"30101","imAccount":name,"pageNo":pageNo,"pageSize":pageSize},
		success : function(json) {
			alert("加载好友...");
			//console.log("查询好友列表");
			//console.log(json);
				if(json.msg == 1){
					var xaw = json.data.conts;
					if(xaw.length == 0){
						errorTs("未找到好友信息");
						return;
					}
					var html="";
					for(var i=0;i<xaw.length;i++){
						html+=leftFriend({
							src:xaw[i].headUrl,
							name:xaw[i].nickName,
							id:"im_"+xaw[i].friendAccount,
							time:xaw[i].msg.msgTime,
							msgContent:xaw[i].msg.msgContent
						});
					};
					$("#userList").append(html);
					var chatID=webChatUserInfo.chatId;
					if(chatID){
						selToID = chatID;
					}else{
						selToID = $("#userList").children().eq(0).attr("id").substring(3);
					}
					$("#im_"+selToID).css("background-color","#b8e2fa");
					$("#im_"+selToID).insertBefore($("#userList").children().eq(0));
					successQt();
					getFirendMessageInfo();
					$("#sendMessage").addClass("noSend");
					getUserInfo({"chatId":selToID});
					//getHistory();
				}else{
					errorTs(json.msgbox);
					errorQt();
					errorDefault();
				}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			alert("加载好友失败...");
			errorTs("好友列表请求错误!");
			errorQt();
			errorDefault();
		}
	});
};
/*查询历史消息,查询更多历史消息*/
function getHistory(opt){
	if(!opt.pageNo){
		opt.pageNo = 1;
	}
	if(!opt.pageSize){
		opt.pageSize = 20;
	}
	if(!opt.msgId){
		opt.msgId = "";
	}
	$.ajax({
		url : url+"/chat/selMessage.do",
		type : "POST",
		dataType : "json",
	    beforeSend:function(){
	    	var imgLoad = '<img src="../sfile/chat/img/loading.gif"/>';
	    	$("#messageMore").empty().append(imgLoad);
	    },
		data:{"sender":userName,"recevrer":selToID,"busCode":busCode,"startTime":"","endTime":"","pageNo":opt.pageNo,"pageSize":opt.pageSize,"msgId":opt.msgId},
		//data:{"sender":"ys_42","recevrer":"ys_888","busCode":"30101","startTime":"","endTime":"","pageNo":pageNo,"pageSize":pageSize},
		success : function(json) {
			$("#messageMore").empty().text("查看更多...");
						//console.log("查询聊天记录");
							//console.log(json);
			if(json.msg == 1){
				var xaw = json.data.dataList;
				if(xaw.length == 0){
					errorTs("没有更多历史消息!");
				}
				for(var i=0;i<xaw.length;i++){
					if(xaw[i].msgType == "TIMImageElem"){
						addImg(xaw[i],false);
					}else{
						showHistory(xaw[i]);
					}
				}
			}else{
				errorTs(json.msgbox);
			};
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			$("#messageMore").empty().text("查看更多...");
			errorTs("历史消息请求错误!");
		}
	});
};
/*查询历史消息,查询更多历史消息*/
function showHistory(msg){
	//console.log("展示历史消息");
if(msg.msgType == "TIMTextElem"){
      	if(msg.sendChatId == userName){
      		addMsgOld2(msg);
      	}else{
      		addMsgOld1(msg);
      	}
    }
};
/*获取孕妇信息*/
function getUserInfo(opt){
	if(!opt.healthNum){
		opt.healthNum = "";
	}
	if(!opt.chatId){
		opt.chatId = "";
	}
/*	console.log(url+"/userBasic/getUserInfo.do");
	console.info(opt.chatId);*/
	$.ajax({
		url : url+"/userBasic/getUserInfo.do",
		type : "POST",
		dataType : "json",
		data:{"healthNum":opt.healthNum,"chatId":opt.chatId},
		success : function(json) {
			/*console.log("通过保健号或用户id获取孕妇基本信息(我的消息)------天使(前端)");
			console.log(json);*/
			if(json){
				if(json.img){
					$(".showUserImg img").attr("src",json.img);
				}else{
					$(".showUserImg img").attr("src",userHeadImg);
				}
				$(".userXx h4").text(json.name);
				$(".userXx").find(".sex").text(json.sex);
				$(".userXx").find(".age").text(json.age+'岁');
				$(".userDqyz").find(".week").text(json.yunWeeks);
				$(".userDqyz").find(".yzq").text(json.yunDate);
			/*	if(json.isRegist){
					$(".userXx").find(".sfzc").text("(已注册)");
				}else{
					$(".userXx").find(".sfzc").text("(未注册)");
					$(".imRigthTitle").append(register);
				}*/
				$(".userXx").find(".sfzc").text("(已注册)");
				successDefault();
				successQt();
				mobile = json.mobile;
				getHistory({
					pageNo:1,
					pageSize:20
				});
				$("#sendMessage").addClass("noSend");
			}else{
				errorTs("获取孕妇基本信息错误");
				errorQt();
				errorDefault();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			errorTs("获取孕妇基本信息错误");
			errorDefault();
			errorQt();
		}
	});
};
/*长轮询*/
function getFirendMessageInfo(){
	//console.log("长轮询开始....");
	$.ajax({
		url : url+"/chat/selNoreadmsgInfo.do",
		type : "POST",
		dataType : "json",
	    timeout:"80000", 
		data:{"busCode":busCode,"recevrer":userName},
		success : function(json) {
			//console.log(json);
			if(json.msg == 1){
				//console.log("调用数据成功");
				var xaw =json.data;
				for(var i=0; i<xaw.length;i++){
				if(xaw[i].msgType == "TIMImageElem"){
					//console.log(typeof xaw[i].msgContent);
					addImg(xaw[i],true);
				}else{
					addMsg1(xaw[i]);
				}

				}
			}else{
				//console.log("没有数据");
			}
			setTimeout(function(){
				//console.log("长轮询重新开始....");
				getFirendMessageInfo();
			},5000);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			//console.log("长轮询错误,重新开始....");
			setTimeout(function(){
				getFirendMessageInfo();
			},5000);
		}
	});
};
/*发送文字消息*/
function onSendMsg(){
	var text =tool.trimStr($("#messageBody").val());
	alert(text);
	if(text.length == 0){
		$("#sendMessage").addClass("noSend");
		$("#messageBody").val("");
		alert("字段为零,发消息错误");
		return;
	}
	addMsg(text);
	$("#sendMessage").addClass("noSend");
	defaultSend(text);
	$.ajax({
		url : url+"/chat/sendTextMsg.do",
		type : "POST",
		dataType : "json",
		data:{"busCode":busCode,"sender":userName,"recevrer":selToID,"text":text},
		success : function(json) {
			//console.log(json);
			if(json.msg == 1){
			var msg = json.data;
			//addMsg(msg);
			}else{
				alert("发送文字消息失败else");
				errorTs("发送消息失败....");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			alert("发送文字消息失败");
			errorTs("发送消息失败....");
		}
	});
};
/*消息回执*/
function messageHz(msgId){
	$.ajax({
		url : url+"/chat/determineReadmsg.do",
		type : "POST",
		dataType : "json",
	    timeout:"80000", 
		data:{"busCode":busCode,"recevrer":userName,"msgId":msgId},
		success : function(json) {
			//console.log(json);
			if(json.msg == 1){
				//console.log("发送回执成功");
			}else{
				//console.log("发送回执错误");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			//console.log("发送回执错误,重新开始....");
		}
	});
};
/*发送短信邀请注册*/
function sendRegister(){
	var context='尊敬的宝妈，为了方便与您沟通，医生邀请你下载《天使医生》app，并绑定医院，下载链接：http://image.jumper-health.com/user.html';
	$.ajax({
		url : url+"/sms/pcSendInviteRegister.do",
		type : "POST",
		dataType : "json", 
		data:{"content":context,"phone":mobile},
		success : function(json) {
			//console.log(json);
			if(json.msg == 1){
				errorTs("发送短信邀请注册成功")
			}else{
				errorTs("发送短信邀请注册失败");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			errorTs("发送短信邀请注册失败");
		}
	});
};
/*获取信息错误*/
function errorDefault(){
	$(".userMessage").hide();
	$(".userNoMessage").show();
	$("#messageMore").hide();
}
/*获取信息成功*/
function successDefault(){
	$(".userMessage").show();
	$(".userNoMessage").hide();
	$("#messageMore").show();
}
/*其它情况错误*/
function errorQt(){
	$("#messageBody").attr("disabled",true);
	$("#sendMessage").addClass("noSend");
	$("#messageMore").hide();
}
/*其它情况成功*/
function successQt(){
	$("#messageBody").attr("disabled",false);
	$("#sendMessage").addClass("noSend");
	$("#messageMore").show();
}

/*发送消息之后变为默认样式*/
function defaultSend(text){
	var timeA=tool.time().substring(10,16);
    $("#im_"+selToID).find(".titleBottom").text(text);
    $("#im_"+selToID).find(".ttTime").text(timeA);
    $("#messageBody").val('');
}
/*错误弹框*/
function errorTs(message){
	art.dialog({
    	content: message,
        icon: 'warning',
        time: 1.5
	});
}
