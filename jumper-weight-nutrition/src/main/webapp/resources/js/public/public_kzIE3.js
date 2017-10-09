$(function(){
	/*查询历史记录*/
	$(document).on("click","#messageMore",function(){
		var msgID = $("#imMessageCenterBox").children().eq(0).attr("id");
			if(msgID == "messageMore"){
				getHistory({
					pageNo:1,
					pageSize:20
				});
			}else{
				getHistory({
					msgId:msgID
				});
			}
	});
	/*点击好友,查询孕妇信息及历史聊天记录*/
	$(document).on("click","#userList li",function(){
		if($(this).is(".aaa")){
			return;
		}
		$("#sendMessage").addClass("noSend");
		$("#messageBody").val("");
		$("#userList li").css("background-color","#FFFFFF").removeClass("aaa");
		$(this).css("background-color","#b8e2fa").addClass("aaa");
		selToID = $(this).attr("id").substring(3);
		$("#im_"+selToID).find(".xdd").hide();
		$("#imMessageCenterBox").empty().append(messageMore);
		var zyImg = $("#im_"+selToID).find(".lImg").attr("src");
		var zyNc = $("#im_"+selToID).find(".ttName").text();
		getUserInfo({
			"chatId":selToID,
			"name":zyNc,
			"img":zyImg
			});	
	});
	/*放大图片*/
	$(document).on("click",".showImgIm",function(){ 
			var src = $(this).attr("data-src");
			var width = $(this).attr("data-width");
			var height = $(this).attr("data-height");
			if(parseInt(width)>1200){
				height=parseInt(height*(1200/width));
				width=1200;
			}
			if(parseInt(height)>600){
				width=parseInt(width*(600/height));
				height=600;
			}
			art.dialog({
				    padding: 0,
				    title: '',
				    content: '<img src="'+src+'" width="'+width+'" height="'+height+'" />',
				    lock: true
				});
	});
	/*点击发送按钮*/
	$(document).on("click","#sendMessage",function(){
		if($(this).is(".noSend")){
			return;
		}else{
			onSendMsg();
		}
	});
})