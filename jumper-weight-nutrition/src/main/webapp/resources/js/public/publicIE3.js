/*全局变量*/
 var messageAll=$("#imMessageCenterBox");
 var reIm=/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/;
 var userHeadImg = "../sfile/chat/img/default_user.gif";
 var busCode="30101";
 var mobile = "";
 var ajaxArray=[];
 var re = /'/g;
 var userMessageImg = "";
 var tF=true;
/*增加一条消息*/
function addMsg(msg) {
	var time = msg.sendTime;
	var msgID = msg.msgId;
	var messageBody = msg.msgContent;
	var re=/\\\\/g;
	var re1=/\\"/g;
	var messageBody2 = messageBody.replace(re1,"\"");
	var messageBody1 = messageBody2.replace(re,"&#92;");
	newMessage(rightMessage({
		msgId:msgID,
    	time:time,
    	messageBody:messageBody1
    }));
	defaultSend(messageBody1,time);
}
/*接收到一条消息*/
function addMsg1(msg){
	var msgID = msg.msgId;
    var time=msg.sendTime;
	messageBody1=msg.msgContent;
	var headImg = "";
	if(userMessageImg){
		headImg = userMessageImg;
	}else{
		headImg = userHeadImg;
	}
	if(msg.sendChatId == selToID ){
		newMessage(leftMessage({
			headImg:headImg,
			msgId:msgID,
	    	time:time,
	    	messageBody:messageBody1
	    }));
		messageHz1(msgID);
		defaultLeft(messageBody1,selToID,time);
	}else{
		$("#im_"+msg.sendChatId).find(".xdd").show();
		setTimeout(function(){
			$("#im_"+msg.sendChatId).insertBefore($("#userList").children().eq(0));
		},300);
		defaultLeft(messageBody1,msg.sendChatId,time);
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
	var headImg = "";
	if(userMessageImg){
		headImg = userMessageImg;
	}else{
		headImg = userHeadImg;
	}
	if(parseInt(yWidth)>400){
		height =yHeight*(400/yWidth);
		width = 400;
	}
	if(msg.sendChatId == selToID ){
		if(boo){
			newMessage(leftImg({
				headImg:headImg,
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
				headImg:headImg,
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
		messageHz1(msg.msgId);
		defaultImg(selToID,time);
	}else{
		$("#im_"+msg.sendChatId).find(".xdd").show();
		setTimeout(function(){
			$("#im_"+msg.sendChatId).insertBefore($("#userList").children().eq(0));
		},300);
		defaultImg(msg.sendChatId,time);
	}

	
}

/*接收到一条消息*/
function addMsgOld1(msg) {
	var msgID = msg.msgId;
    var time=msg.sendTime;
	var messageBody1=msg.msgContent;
	var headImg = "";
	if(userMessageImg){
		headImg = userMessageImg;
	}else{
		headImg = userHeadImg;
	}
	oldMessage(leftMessage({
		headImg:headImg,
		msgId:msgID,
    	time:time,
    	messageBody:messageBody1
    }));
	//defaultLeft(messageBody1,selToID,time);
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
	//defaultLeft(messageBody1,selToID,time);
}
/*公用发送消息*/
/*rightMessage*/
function rightMessage(opt){
var message='<div class="rightMessage" id="'+opt.msgId+'"><div class="messageTime">'+opt.time+'</div><img class="imgR fr" src="'+userHeadDoctor+'" style="width: 36px; height: 36px;margin-right: 18px;"><p class="rightMessageBody arrText fr"><span>'+opt.messageBody+'</span><span class="talkB"></span></p><div class="clear"></div></div>'
  return message;
}
function leftMessage(opt){
var message='<div class="leftMessage" id="'+opt.msgId+'"><div class="messageTime">'+opt.time+'</div><img class="imgL fl" src="'+opt.headImg+'" style="width: 36px; height: 36px;margin-left: 18px;"><p class="leftMessageBody arrText fl"><span>'+opt.messageBody+'</span><span class="talkS"></span></p><div class="clear"></div></div>';
  return message;
}

function leftImg(opt){
  var img = '<div class="leftMessage" id="'+opt.msgId+'"><div class="messageTime">'+opt.time+'</div><img class="imgL fl" src="'+opt.headImg+'" style="width: 36px; height: 36px;margin-left: 18px;"><p class="leftMessageBody arrText fl"><img class="showImgIm" src="'+opt.src+'" data-src="'+opt.ySrc+'" data-width="'+opt.yWidth+'" data-height="'+opt.yHeight+'" style="width:'+opt.width+';height:"'+opt.height+'""></div></div>';
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
		opt.src = "../sfile/chat/img/default_user.gif";
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
				if(json.msg == 1){
					var xaw = json.data.conts;
					if(xaw.length == 0){
						errorQt();
						errorDefault();
						$(".userNoMessage,.showUserImg").hide();
						$(".imLeftBox").append('<p class="noP">无聊天记录</p>');
						return;
					}
					var html="";
					for(var i=0;i<xaw.length;i++){
						html+=leftFriend({
							src:xaw[i].headUrl,
							name:xaw[i].nickName,
							id:"im_"+xaw[i].friendAccount,
							time:xaw[i].msg.msgTime.substring(5),
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
					doctorImg();
					$("#im_"+selToID).css("background-color","#b8e2fa");
					$("#im_"+selToID).insertBefore($("#userList").children().eq(0));
					successQt();
					getFirendMessageInfo();
					$("#sendMessage").addClass("noSend");
					var zyImg = $("#im_"+selToID).find(".lImg").attr("src");
					var zyNc = $("#im_"+selToID).find(".ttName").text();
					getUserInfo({
						"chatId":selToID,
						"name":zyNc,
						"img":zyImg
						});
				}else{
					errorTs(json.msgbox);
					errorQt();
					errorDefault();
					$(".userNoMessage,.showUserImg").hide();
				}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			errorTs("好友列表请求错误!");
			errorQt();
			errorDefault();
			$(".userNoMessage,.showUserImg").hide();
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
		success : function(json) {
			$("#messageMore").empty().text("查看更多...");
			if(json.msg == 1){
				var xaw = json.data.dataList;
				if(xaw.length == 0){
					errorTs("没有更多历史消息!");
					return;
				}else{
					for(var i=0;i<xaw.length;i++){
						if(xaw[i].msgType == "TIMImageElem"){
							addImg(xaw[i],false);
						}else{
							showHistory(xaw[i]);
						}
					}
				};
				setTimeout(function(){
					var $Array = $("#imMessageCenterBox").children().not("a");
					if($Array.length == 0){
					    $("#im_"+selToID).find(".titleBottom").text("");
					    $("#im_"+selToID).find(".ttTime").text("");
					    return;
					}else{
						var $body=$Array.eq($Array.length - 1);
						var text =$body.find(".arrText").children().eq(0).text();
						var time =$body.find(".messageTime").text();
						var timeA = time.substring(5,16);
						if(text == ""){
							text = "【图片消息】";
						};
					    $("#im_"+selToID).find(".titleBottom").text(text);
					    $("#im_"+selToID).find(".ttTime").text(timeA);
					};
				},100);
				
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
	$.ajax({
		url : url+"/userBasic/getUserInfo.do",
		type : "POST",
		dataType : "json",
		data:{"healthNum":opt.healthNum,"chatId":opt.chatId},
		success : function(json) {
			if(json){
				/*if(json.img){
					$(".showUserImg img").attr("src",json.img);
				}else{
					$(".showUserImg img").attr("src",userHeadImg);
				}*/
				$(".showUserImg img").attr("src",opt.img);
				userMessageImg = opt.img;
				$(".userXx h4").text(opt.name);
				$(".userXx").find(".sex").text(json.sex);
				$(".userXx").find(".age").text(json.age+'岁');
				$(".userDqyz").find(".week").text(json.yunWeeks);
				$(".userDqyz").find(".yzq").text(json.yunDate);
				$(".userXx").find(".sfzc").text("(已注册)");
				successDefault();
				successQt();
				mobile = json.mobile;
				if(tF){
					setTimeout(function(){
						newOldMessage();
					},400);
					tF=!tF;
				}else{
					newOldMessage();
				}

			/*	getHistory({
					pageNo:1,
					pageSize:20
				});*/
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
	$.ajax({
		url : url+"/chat/selNoreadmsgInfo.do",
		type : "POST",
		dataType : "json",
	    timeout:"80000", 
		data:{"busCode":busCode,"recevrer":userName},
		success : function(json) {
			if(json.msg == 1){
				var xaw =json.data;
				for(var i=0; i<xaw.length;i++){
				if(xaw[i].msgType == "TIMImageElem"){
					addImg(xaw[i],true);
				}else{
					addMsg1(xaw[i]);
				}

				}
			}else{
			}
			setTimeout(function(){
				getFirendMessageInfo();
			},5000);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			setTimeout(function(){
				getFirendMessageInfo();
			},5000);
		}
	});
};
/*发送文字消息*/
function onSendMsg(){
	var text = tool.trimQb(tool.trimStr($("#messageBody").val()));
	if(text.length == 0){
		$("#sendMessage").addClass("noSend");
		$("#messageBody").val("");
		return;
	}
	$("#sendMessage").addClass("noSend");
	$.ajax({
		url : url+"/chat/sendTextMsg.do",
		type : "POST",
		dataType : "json",
		data:{"busCode":busCode,"sender":userName,"recevrer":selToID,"text":text},
		success : function(json) {
			if(json.msg == 1){
			var msg = json.data;
			addMsg(msg);
			}else{
				errorTs("发送消息失败....");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
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
			if(json.msg == 1){
				
			}else{
				
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 

		}
	});
};
/*最新消息回执*/
function messageHz1(msgId){
	$.ajax({
		url : url+"/chat/callbackMsgReceipt.do",
		type : "POST",
		dataType : "json",
	    timeout:"80000", 
		data:{"busCode":busCode,"recevrer":userName,"msgIds":msgId},
		success : function(json) {
			if(json.msg == 1){
				
			}else{

			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			
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
/*获取医生头像*/
;function doctorImg(){
	$.ajax({
		url : url+"/chat/getOwnImg.do",
		type : "POST",
		dataType : "json", 
		data:{"ownChatId":userName,"friendChatId":selToID},
		success : function(json) {
			if(json.data){
				userHeadDoctor = json.data;
			}else{
				userHeadDoctor = "../sfile/chat/img/default_docter.gif";
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			userHeadDoctor = "../sfile/chat/img/default_docter.gif";
		}
	});
}
/*新的查询历史消息*/
;function newOldMessage(){
	$.ajax({
		url : url+"/chat/selectMsgForpage.do",
		type : "POST",
		dataType : "json", 
		data:{"sender":userName,"recevrer":selToID,"busCode":busCode,"pageNo":"","pageSize":"","msgId":""},
		success : function(json) {
			if(json.msg == 1){
				var xaw = json.data.dataList;
				if(xaw.length == 0){
					errorTs("没有更多历史消息!");
					return;
				}else{
					for(var i=0;i<xaw.length;i++){
						if(xaw[i].msgType == "TIMImageElem"){
							addImg(xaw[i],false);
						}else{
							showHistory(xaw[i]);
						}
					}
				};
				setTimeout(function(){
					var $Array = $("#imMessageCenterBox").children().not("a");
					if($Array.length == 0){
					    $("#im_"+selToID).find(".titleBottom").text("");
					    $("#im_"+selToID).find(".ttTime").text("");
					    return;
					}else{
						var $body=$Array.eq($Array.length - 1);
						var text =$body.find(".arrText").children().eq(0).text();
						var time =$body.find(".messageTime").text();
						var timeA = time.substring(5,16);
						if(text == ""){
							text = "【图片消息】";
						};
					    $("#im_"+selToID).find(".titleBottom").text(text);
					    $("#im_"+selToID).find(".ttTime").text(timeA);
					};
				},100);
				
			}else{
				errorTs(json.msgbox);
			};
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			errorTs("查询历史消息错误!");
		}
	});
}
/*根据长轮询状态,发送AJAX请求去后台获取聊天信息*/
;function newGetMessage(){
	$.ajax({
		url : url+"/chat/selectNoReadmsg.do",
		type : "POST",
		dataType : "json", 
		data:{"sender":userName,"recevrer":selToID,"busCode":busCode,"pageNo":"","pageSize":""},
		success : function(json) {
			if(json.msg == 1){
				var xaw =json.data;
				for(var i=0; i<xaw.length;i++){
				if(xaw[i].msgType == "TIMImageElem"){
					addImg(xaw[i],true);
				}else{
					addMsg1(xaw[i]);
				}

				}
			}else{
			}
		
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			errorTs("聊天信息消息错误!");
		}
	});
}
/*获取信息错误*/
function errorDefault(){
	$(".userMessage").hide();
	$(".userNoMessage,.showUserImg").show();
	$("#messageMore").hide();
	$(".showUserImg img").attr("src","../sfile/chat/img/default_user.gif");
}
/*获取信息成功*/
function successDefault(){
	$(".userMessage").show();
	$(".userNoMessage").hide();
	$("#messageMore").show();
	$(".showUserImg").show();
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
function defaultSend(text,time){
	var timeA=time.substring(5,16);
	var re = /&#92;/g;
	var text1 = text.replace(re,"\\");
    $("#im_"+selToID).find(".titleBottom").text(text1);
    $("#im_"+selToID).find(".ttTime").text(timeA);
    $("#messageBody").val('');
}
/*左侧接收默认样式*/
function defaultLeft(text,sId,time){
	var timeA=time.substring(5,16);
    $("#im_"+sId).find(".titleBottom").text(text);
    $("#im_"+sId).find(".ttTime").text(timeA);
}
/*左侧接收默认样式*/
function defaultImg(sId,time){
	var timeA=time.substring(5,16);
    $("#im_"+sId).find(".titleBottom").text("【图片消息】");
    $("#im_"+sId).find(".ttTime").text(timeA);
}

/*错误弹框*/
function errorTs(message){
	art.dialog({
    	content: message,
        icon: 'warning',
        time: 1.5
	}); 
}
