/*发送消息*/
function send(headSrc,str,label,name){
	var html=
		"<div class='send'>" +
        "<div class='msg'>" +
            "<img src="+headSrc+" />"+
            "<div class='fl'>"+
                "<h1 class='theName'>"+name+"<i class='mylabel' data-"+label+"></i></h1>"+
                "<p><i class='msg_input'></i>"+str+"</p>"+
            "</div>"+
        "</div>" +
        "</div>";
		upView(html);
}
/*接受消息*/
function show(headSrc,str,label,name){
	var html=
        "<div class='show'>" +
            "<div class='msg'>" +
                "<img src="+headSrc+" />"+
                "<div class='fl'>"+
                    "<h1 class='theName'>"+name+"<i class='mylabel' data-"+label+"></i></h1>"+
                    "<p><i class='msg_input'></i>"+str+"</p>"+
                "</div>"+
            "</div>" +
        "</div>";
	upView(html);
}
/*更新视图*/
function upView(html){
	$('.message').append(html);
	$('body').animate({scrollTop:$('.message').outerHeight()-window.innerHeight},200)
}
function sj(){
	return parseInt(Math.random()*10)
}
$(function(){
	$('.footer').on('keyup','input',function(){
		if($(this).val().length>0){
			$(this).next().css('background','#ff6e7f').prop('disabled',true);
		
		}else{
			$(this).next().css('background','#ddd').prop('disabled',false);
		}
	});
	$('.footer p').click(function(){
		show("./images/touxiangm.png",$(this).prev().val(),'yun','郭艳香');
		$('.footer input').val('');
		// test();
	})
});

/*测试数据*/
var arr=['我是小Q','好久没联系了！','你在想我么','怎么不和我说话','跟我聊会天吧'];
var imgarr=['images/touxiang.png','images/touxiangm.png'];
// test();
function test(){
	$(arr).each(function(i){
		setTimeout(function(){
			send("images/touxiang.png",arr[i],'bao','GG')
		},sj()*500)
	})
}
