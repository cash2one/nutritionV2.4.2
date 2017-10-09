$(function(){
	/*放大图片*/
	$(document).on("click",".showImgIm",function(){ 
			var src = $(this).attr("data-src");
			var width = $(this).attr("data-width");
			var height = $(this).attr("data-height");
			if(parseInt(width)>560){
				height=parseInt(height*(490/width));
				width=490;
			}
			if(parseInt(height)>600){
				width=parseInt(width*(530/height));
				height=530;
			}
			art.dialog({
				    padding: 0,
				    title: '',
				    content: '<img src="'+src+'" width="'+width+'" height="'+height+'" />',
				    lock: true
				});
	});
	/*点击显示出发送图片框*/
	$(document).on("click",".imga",function(){
			$("#uploadWindow").show();
	});

	/*点击好友事件==>查询历史记录改变左侧聊天头像*/
	$(document).on({
		"mouseover":function(){
			if($(this).is(".active")){
				return;
			}else{
				$(this).addClass("hover_active");
			}
		},
		"mouseout":function(){
			$(this).removeClass("hover_active");
		},
		"click":function(){
			if($(this).is(".active")){
				return;
			}else{
				$("#friendList li").removeClass("active");
				$(this).addClass("active");
				$("#imMessageCenterBox").empty().append(messageMore);
				$("#messageBody").val("");
				selToID = $(this).attr("id").substring(3);
				recveNickName = $(this).find(".imUserName").text();
				$(this).find(".xdd").text("0").hide();
				getHistory(true);
			}
		}
	},"#friendList li");
	//播放语音
	var timerNew = null;
	var newNumber = 0;
	var imgSrc = "../resources/img/audio.gif";
	var oldImgSrc = "../resources/img/audio.png";
	$(document).on("click",".audioI",function(){
		var index = $(this).attr("id");
		for(var i = 0;i<$(".aAudio").length;i++){
			var xI = $(".aAudio").eq(i).prev().attr("id");
			if(xI != index){
				var id = $(".aAudio").eq(i).attr("id");
				var playAudio = document.getElementById(id);
				$(".aAudio").eq(i).prev().find("img").attr("src",oldImgSrc);
				playAudio.pause();
			}
		}
		var _this = $(this);
		var id = $(this).next().attr("id");
		var playAudio = document.getElementById(id);
		var number = parseInt($(this).nextAll("span").text());
		var oldNumber = parseInt($(this).nextAll(".dataYl").text());
		if(playAudio.paused){
			clearInterval(timerNew);
			playAudio.play();
			$(this).find("img").attr("src",imgSrc);
			timerNew = setInterval(function(){
				number = number - 1;
				_this.nextAll("span").text(number);
				if(number == -1){
					clearInterval(timerNew);
					_this.find("img").attr("src",oldImgSrc);
					_this.nextAll("span").text(oldNumber);
				}
			},1000);
		}else{
			playAudio.pause();
			$(this).find("img").attr("src",oldImgSrc);
			clearInterval(timerNew);
		}
	})
	
	
	
	$(document).on("click","#moreOldMessage",function(){
		    var msgId = $("#imMessageCenterBox").children().eq(1).attr("id");
			getHistory(false,msgId);
	});
	
})