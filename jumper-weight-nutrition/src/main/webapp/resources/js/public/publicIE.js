var messageAll=$("#imMessageCenterBox");var reIm=/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/;var userHeadImg="../sfile/chat/img/default_user.gif";var busCode="30101";var mobile="";var ajaxArray=[];var re=/'/g;var userMessageImg="";var tF=true;function addMsg(g){var f=g.sendTime;var d=g.msgId;var e=g.msgContent;var c=/\\\\/g;var b=/\\"/g;var a=e.replace(b,'"');var h=a.replace(c,"&#92;");newMessage(rightMessage({msgId:d,time:f,messageBody:h}));defaultSend(h,f)}function addMsg1(d){var b=d.msgId;var c=d.sendTime;messageBody1=d.msgContent;var a="";if(userMessageImg){a=userMessageImg}else{a=userHeadImg}if(d.sendChatId==selToID){newMessage(leftMessage({headImg:a,msgId:b,time:c,messageBody:messageBody1}));messageHz1(b);defaultLeft(messageBody1,selToID,c)}else{$("#im_"+d.sendChatId).find(".xdd").show();setTimeout(function(){$("#im_"+d.sendChatId).insertBefore($("#userList").children().eq(0))},300);defaultLeft(messageBody1,d.sendChatId,c)}}function addImg(d,l){var i=d.msgId;var e=d.sendTime;var c=(d.msgContent).replace(re,'"');var g=JSON.parse(c);var a=g[1].thumbnail;var h=g[0].original;var j=g[0].width;var m=g[0].height;var b,k;var f="";if(userMessageImg){f=userMessageImg}else{f=userHeadImg}if(parseInt(j)>400){k=m*(400/j);b=400}if(d.sendChatId==selToID){if(l){newMessage(leftImg({headImg:f,msgId:i,time:e,src:a,ySrc:h,width:b,height:k,yWidth:j,yHeight:m}))}else{oldMessage(leftImg({headImg:f,msgId:i,time:e,src:a,ySrc:h,width:b,height:k,yWidth:j,yHeight:m}))}messageHz1(d.msgId);defaultImg(selToID,e)}else{$("#im_"+d.sendChatId).find(".xdd").show();setTimeout(function(){$("#im_"+d.sendChatId).insertBefore($("#userList").children().eq(0))},300);defaultImg(d.sendChatId,e)}}function addMsgOld1(d){var b=d.msgId;var c=d.sendTime;var e=d.msgContent;var a="";if(userMessageImg){a=userMessageImg}else{a=userHeadImg}oldMessage(leftMessage({headImg:a,msgId:b,time:c,messageBody:e}))}function addMsgOld2(c){var a=c.msgId;var b=c.sendTime;var d=c.msgContent;oldMessage(rightMessage({msgId:a,time:b,messageBody:d}))}function rightMessage(a){var b='<div class="rightMessage" id="'+a.msgId+'"><div class="messageTime">'+a.time+'</div><img class="imgR fr" src="'+userHeadDoctor+'" style="width: 36px; height: 36px;margin-right: 18px;"><p class="rightMessageBody arrText fr"><span>'+a.messageBody+'</span><span class="talkB"></span></p><div class="clear"></div></div>';return b}function leftMessage(a){var b='<div class="leftMessage" id="'+a.msgId+'"><div class="messageTime">'+a.time+'</div><img class="imgL fl" src="'+a.headImg+'" style="width: 36px; height: 36px;margin-left: 18px;"><p class="leftMessageBody arrText fl"><span>'+a.messageBody+'</span><span class="talkS"></span></p><div class="clear"></div></div>';return b}function leftImg(b){var a='<div class="leftMessage" id="'+b.msgId+'"><div class="messageTime">'+b.time+'</div><img class="imgL fl" src="'+b.headImg+'" style="width: 36px; height: 36px;margin-left: 18px;"><p class="leftMessageBody arrText fl"><img class="showImgIm" src="'+b.src+'" data-src="'+b.ySrc+'" data-width="'+b.yWidth+'" data-height="'+b.yHeight+'" style="width:'+b.width+';height:"'+b.height+'""></div></div>';return a}function newMessage(a){messageAppend(messageAll,a);scrollBottom()}function oldMessage(a){messageBefore(messageAll,a)}function messageBefore(a,b){a.children().eq(0).before(b)}function messageAppend(a,b){a.append(b)}function leftFriend(b){if(!b.src){b.src="../sfile/chat/img/default_user.gif"}var a='<li id="'+b.id+'"><div class="leftImg fl"><img class="lImg" src="'+b.src+'"><span class="xdd"></span></div><div class="rightTitle fl"><div class="titleTop"><span class="ttName fl" title="'+b.name+'">'+b.name+'</span><span class="ttTime fr">'+b.time+'</span></div><p class="titleBottom">'+b.msgContent+"</p></div></li>";return a}var sendBotton='<a id="sendMessage" href="javascript:;" onclick="onSendMsg()">发送</a>';var noSendBotton='<a class="noSend" href="javascript:;">发送</a>';var register='<a class="register fr">邀请注册</a>';var messageMore='<a id="messageMore" href="javascript:;">查看更多...</a>';function scrollBottom(){setTimeout(function(){var a=messageAll[0].scrollHeight-messageAll.height();messageAll.scrollTop(a)},300)}function onTextareaKeyDown(a){var a=arguments.callee.caller.arguments[0]||window.event;if(a.keyCode==13){setTimeout(function(){onSendMsg()},0)}}function getFriendList(b,c,a){if(!c){c=""}if(!a){a=""}$.ajax({url:url+"/chat/friendGetAll.do",type:"POST",dataType:"json",data:{busCode:"30101",imAccount:b,pageNo:c,pageSize:a},success:function(j){if(j.msg==1){var d=j.data.conts;if(d.length==0){errorQt();errorDefault();$(".userNoMessage,.showUserImg").hide();$(".imLeftBox").append('<p class="noP">无聊天记录</p>');return}var h="";for(var g=0;g<d.length;g++){h+=leftFriend({src:d[g].headUrl,name:d[g].nickName,id:"im_"+d[g].friendAccount,time:d[g].msg.msgTime.substring(5),msgContent:d[g].msg.msgContent})}$("#userList").append(h);var f=webChatUserInfo.chatId;if(f){selToID=f}else{selToID=$("#userList").children().eq(0).attr("id").substring(3)}doctorImg();$("#im_"+selToID).css("background-color","#b8e2fa");$("#im_"+selToID).insertBefore($("#userList").children().eq(0));successQt();getFirendMessageInfo();$("#sendMessage").addClass("noSend");var e=$("#im_"+selToID).find(".lImg").attr("src");var k=$("#im_"+selToID).find(".ttName").text();getUserInfo({chatId:selToID,name:k,img:e})}else{errorTs(j.msgbox);errorQt();errorDefault();$(".userNoMessage,.showUserImg").hide()}},error:function(d,f,e){errorTs("好友列表请求错误!");errorQt();errorDefault();$(".userNoMessage,.showUserImg").hide()}})}function getHistory(a){if(!a.pageNo){a.pageNo=1}if(!a.pageSize){a.pageSize=20}if(!a.msgId){a.msgId=""}$.ajax({url:url+"/chat/selMessage.do",type:"POST",dataType:"json",beforeSend:function(){var b='<img src="../sfile/chat/img/loading.gif"/>';$("#messageMore").empty().append(b)},data:{sender:userName,recevrer:selToID,busCode:busCode,startTime:"",endTime:"",pageNo:a.pageNo,pageSize:a.pageSize,msgId:a.msgId},success:function(d){$("#messageMore").empty().text("查看更多...");if(d.msg==1){var b=d.data.dataList;if(b.length==0){errorTs("没有更多历史消息!");return}else{for(var c=0;c<b.length;c++){if(b[c].msgType=="TIMImageElem"){addImg(b[c],false)}else{showHistory(b[c])}}}setTimeout(function(){var e=$("#imMessageCenterBox").children().not("a");if(e.length==0){$("#im_"+selToID).find(".titleBottom").text("");$("#im_"+selToID).find(".ttTime").text("");return}else{var g=e.eq(e.length-1);var i=g.find(".arrText").children().eq(0).text();var h=g.find(".messageTime").text();var f=h.substring(5,16);if(i==""){i="【图片消息】"}$("#im_"+selToID).find(".titleBottom").text(i);$("#im_"+selToID).find(".ttTime").text(f)}},100)}else{errorTs(d.msgbox)}},error:function(b,d,c){$("#messageMore").empty().text("查看更多...");errorTs("历史消息请求错误!")}})}function showHistory(a){if(a.msgType=="TIMTextElem"){if(a.sendChatId==userName){addMsgOld2(a)}else{addMsgOld1(a)}}}function getUserInfo(a){if(!a.healthNum){a.healthNum=""}if(!a.chatId){a.chatId=""}$.ajax({url:url+"/userBasic/getUserInfo.do",type:"POST",dataType:"json",data:{healthNum:a.healthNum,chatId:a.chatId},success:function(b){if(b){$(".showUserImg img").attr("src",a.img);userMessageImg=a.img;$(".userXx h4").text(a.name);$(".userXx").find(".sex").text(b.sex);$(".userXx").find(".age").text(b.age+"岁");$(".userDqyz").find(".week").text(b.yunWeeks);$(".userDqyz").find(".yzq").text(b.yunDate);$(".userXx").find(".sfzc").text("(已注册)");successDefault();successQt();mobile=b.mobile;if(tF){setTimeout(function(){newOldMessage()},400);tF=!tF}else{newOldMessage()}$("#sendMessage").addClass("noSend")}else{errorTs("获取孕妇基本信息错误");errorQt();errorDefault()}},error:function(b,d,c){errorTs("获取孕妇基本信息错误");errorDefault();errorQt()}})}function getFirendMessageInfo(){$.ajax({url:url+"/chat/selNoreadmsgInfo.do",type:"POST",dataType:"json",timeout:"80000",data:{busCode:busCode,recevrer:userName},success:function(c){if(c.msg==1){var a=c.data;for(var b=0;b<a.length;b++){if(a[b].msgType=="TIMImageElem"){addImg(a[b],true)}else{addMsg1(a[b])}}}else{}setTimeout(function(){getFirendMessageInfo()},5000)},error:function(a,c,b){setTimeout(function(){getFirendMessageInfo()},5000)}})}function onSendMsg(){var a=tool.trimQb(tool.trimStr($("#messageBody").val()));if(a.length==0){$("#sendMessage").addClass("noSend");$("#messageBody").val("");return}$("#sendMessage").addClass("noSend");$.ajax({url:url+"/chat/sendTextMsg.do",type:"POST",dataType:"json",data:{busCode:busCode,sender:userName,recevrer:selToID,text:a},success:function(b){if(b.msg==1){var c=b.data;addMsg(c)}else{errorTs("发送消息失败....")}},error:function(b,d,c){errorTs("发送消息失败....")}})}function messageHz(a){$.ajax({url:url+"/chat/determineReadmsg.do",type:"POST",dataType:"json",timeout:"80000",data:{busCode:busCode,recevrer:userName,msgId:a},success:function(b){if(b.msg==1){}else{}},error:function(b,d,c){}})}function messageHz1(a){$.ajax({url:url+"/chat/callbackMsgReceipt.do",type:"POST",dataType:"json",timeout:"80000",data:{busCode:busCode,recevrer:userName,msgIds:a},success:function(b){if(b.msg==1){}else{}},error:function(b,d,c){}})}function sendRegister(){var a="尊敬的宝妈，为了方便与您沟通，医生邀请你下载《天使医生》app，并绑定医院，下载链接：http://image.jumper-health.com/user.html";$.ajax({url:url+"/sms/pcSendInviteRegister.do",type:"POST",dataType:"json",data:{content:a,phone:mobile},success:function(b){if(b.msg==1){errorTs("发送短信邀请注册成功")}else{errorTs("发送短信邀请注册失败")}},error:function(b,d,c){errorTs("发送短信邀请注册失败")}})}function doctorImg(){$.ajax({url:url+"/chat/getOwnImg.do",type:"POST",dataType:"json",data:{ownChatId:userName,friendChatId:selToID},success:function(a){if(a.data){userHeadDoctor=a.data}else{userHeadDoctor="../sfile/chat/img/default_docter.gif"}},error:function(a,c,b){userHeadDoctor="../sfile/chat/img/default_docter.gif"}})}function newOldMessage(){$.ajax({url:url+"/chat/selectMsgForpage.do",type:"POST",dataType:"json",data:{sender:userName,recevrer:selToID,busCode:busCode,pageNo:"",pageSize:"",msgId:""},success:function(c){if(c.msg==1){var a=c.data.dataList;if(a.length==0){errorTs("没有更多历史消息!");return}else{for(var b=0;b<a.length;b++){if(a[b].msgType=="TIMImageElem"){addImg(a[b],false)}else{showHistory(a[b])}}}setTimeout(function(){var d=$("#imMessageCenterBox").children().not("a");if(d.length==0){$("#im_"+selToID).find(".titleBottom").text("");$("#im_"+selToID).find(".ttTime").text("");return}else{var f=d.eq(d.length-1);var h=f.find(".arrText").children().eq(0).text();var g=f.find(".messageTime").text();var e=g.substring(5,16);if(h==""){h="【图片消息】"}$("#im_"+selToID).find(".titleBottom").text(h);$("#im_"+selToID).find(".ttTime").text(e)}},100)}else{errorTs(c.msgbox)}},error:function(a,c,b){errorTs("查询历史消息错误!")}})}function newGetMessage(){$.ajax({url:url+"/chat/selectNoReadmsg.do",type:"POST",dataType:"json",data:{sender:userName,recevrer:selToID,busCode:busCode,pageNo:"",pageSize:""},success:function(c){if(c.msg==1){var a=c.data;for(var b=0;b<a.length;b++){if(a[b].msgType=="TIMImageElem"){addImg(a[b],true)}else{addMsg1(a[b])}}}else{}},error:function(a,c,b){errorTs("聊天信息消息错误!")}})}function errorDefault(){$(".userMessage").hide();$(".userNoMessage,.showUserImg").show();$("#messageMore").hide();$(".showUserImg img").attr("src","../sfile/chat/img/default_user.gif")}function successDefault(){$(".userMessage").show();$(".userNoMessage").hide();$("#messageMore").show();$(".showUserImg").show()}function errorQt(){$("#messageBody").attr("disabled",true);$("#sendMessage").addClass("noSend");$("#messageMore").hide()}function successQt(){$("#messageBody").attr("disabled",false);$("#sendMessage").addClass("noSend");$("#messageMore").show()}function defaultSend(e,d){var a=d.substring(5,16);var b=/&#92;/g;var c=e.replace(b,"\\");$("#im_"+selToID).find(".titleBottom").text(c);$("#im_"+selToID).find(".ttTime").text(a);$("#messageBody").val("")}function defaultLeft(d,a,c){var b=c.substring(5,16);$("#im_"+a).find(".titleBottom").text(d);$("#im_"+a).find(".ttTime").text(b)}function defaultImg(a,c){var b=c.substring(5,16);$("#im_"+a).find(".titleBottom").text("【图片消息】");$("#im_"+a).find(".ttTime").text(b)}function errorTs(a){art.dialog({content:a,icon:"warning",time:1.5})};