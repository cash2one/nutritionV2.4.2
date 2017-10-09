$(function (){
	var accordionMenu = $('#menu');
    accordionMenu.find('p').bind('click',function(){
    	var $this = $(this),
        loadsrc = $this.attr('loadsrc');
    	$('#maincontent').removeData().load(loadsrc);
    });
    
    // 显示loading
    $.showLoading = function(){
        var $win = $(document),
            $loading = $('#ajax_loading');
        $loading.css({
            width: $win.width(),
            height: $win.height()
        })
            .children().css({
                left: ($win.width() - 100) / 2,
                top: ($win.height() - 100) / 2
            })
        ;
        $loading.show();
    };
    
   
    
    /**
     * 对Date的扩展，将 Date 转化为指定格式的String
     * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
     * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     * 用法:
     * (new Date()).format() ==> 2015-10-12
     * (new Date()).format("yyyy-M-d h:m:s.S") ==> 2015-10-12 2:20:16.725
     * (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2015-10-12 02:19:13.305
     * (new Date()).format("yyyy-MM-dd E HH:mm:ss") ==> 2015-10-12 一 14:19:29
     * (new Date()).format("yyyy-MM-dd EE hh:mm:ss") ==> 2015-10-12 周一 02:19:47
     * (new Date()).format("yyyy-MM-dd EEE hh:mm:ss") ==> 2015-10-12 星期一 02:20:02
     */
    Date.prototype.format=function(fmt) {
        // 默认格式：yyyy-M-d h:m:s
        fmt = fmt || 'yyyy-M-d h:m:s';
        var o = {
            "M+" : this.getMonth()+1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
            "H+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S" : this.getMilliseconds() //毫秒
        };
        var week = {
            "0" : "\u65e5",
            "1" : "\u4e00",
            "2" : "\u4e8c",
            "3" : "\u4e09",
            "4" : "\u56db",
            "5" : "\u4e94",
            "6" : "\u516d"
        };
        if(/(y+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        if(/(E+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };
    
    
    window.byteLength= function (str){
        var byteLen = 0, len = str.length;
        if(!str){return 0;}
        for( var i=0; i<len; i++ )
            byteLen += str.charCodeAt(i) > 255 ? 3 : 1;
        return byteLen;
    };
    
    /**
     * 提示框 
     * type 1: 成功 2: 错误
     */
    $.msgDialog = function (type, msgCont, time){
    	var path = $("#path").val();
    	if (type == 1){
    		$("#winC img").attr("src", path+"/resources/images/success.png");
    	}else if (type == 2){
    		$("#winC img").attr("src", path+"/resources/images/error.png");
			$(".not-wen").html(msgCont);
    	}
    	if (typeof(time)=="undefined"){
    		time = 3;
    	}
    	$(".not-wen").html(msgCont);
    	$(".win_3").fadeIn();
    	$(".win_3").fadeOut(time * 1000);
    }
    
    
   
});

    