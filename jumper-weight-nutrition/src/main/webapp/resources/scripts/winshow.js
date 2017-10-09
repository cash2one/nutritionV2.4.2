/**
 * Created by Apple on 2016/5/5 0005.
 */
$(function (){
	/*$(".confirm-add").click(function(){
		$(".not-wen").html("操作错误！");
		$(".not-wen").html("操作成功！");
		$("#winC img").attr("src","");
	});*/
	
	
	/*$(".tureBtn_1").click(function(){
	    $(".win_xinz_2").fadeIn(120);
	    $(".win_3").fadeOut(120);
	});*/
	$(".tureBtn_2").click(function(){
	    $(".win_xinz_2").fadeOut(120);
	    $(".win_xinz_1").fadeIn(120);
	    
	});
	/*$(".offBtn_2").click(function(){
	    $(".win_xinz").fadeOut(120);
	});*/
	/*$(".active-but").click(function(){
		$(".win_2").fadeOut(120);
	    $(".win_3").fadeIn(120);
	});*/

	$(".table_d_btn li").click(function(){
	    $(this).css("background","#596280").siblings().css("background","#7a8599")
	});
	$(".shenheBtn").click(function(){
	    $(".win_1").fadeTo(120, 1);
	});
	$(".offImg").click(function(){
	    $(".win_1,.win_0").fadeTo(120,0);
	});
	$(".off_btn").click(function(){
	    $(".win_0").fadeTo(120, 1);
	});
	$(".deleteA,.oBox_delete").click(function(){
	    $(".win_2").fadeTo(120, 1);
	});
	$(".tureBtn").click(function(){
	    $(".win_2").fadeOut(120);
	    $(".win_3").fadeIn(120);
	});
	$(".offBtn").click(function(){
	    $(".win_2").fadeOut(120);
	});
	$(".offBtn2").click(function(){
	    $(".win_3").fadeOut(120);
	});
	
});



function app(index,val){
	var html="<div class='win_3 radius_4px' id='winC' style='z-index: 2002'>"+
       "<p style='line-height: 0;margin-top: 16px'> <img src='/resources/images/success.png'></p>"+
       "<p style='line-height: 0;margin-top: 24px'>操作成功</p>"+
       "<div>"+
          " <ul class='win_btn3'>"+
              " <li class='offBtn2'>确定</li>"+
           "</ul>"+
       "</div>"+
   "</div>";
}
