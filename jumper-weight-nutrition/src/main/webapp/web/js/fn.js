/**
 * Created by Administrator on 2017/4/28 0028.
 */

$(function(){
    activefn1($(".foodBtn span"),"foodBtnActive");
    tableHover($('.layui-table tbody tr'),'#fff','#f8f8f8','#c1e4f1');
    tableHover($('.daoruList li'),'#fff','#f8f8f8','#D7EAF5');
    //selectInputAll($("#selectAll"));
    //delCheckbox($(".delCheck"),$(".layui-table"));
    //delTr($(".delTr"));
    myBrowser();
    $(window).resize(function(){myBrowser();});
    //通用fn//////////////////////////////////////////////////////////////////

    //table  tr列表删除按钮js
    function delTr(btn) {
        btn.click(function () {
            $(this).parent().parent("tr").attr("id", "target");
            layer.alert('要删除这条记录么？', {
                icon: 0,
                title: '提示',
                btn: ['删除'],
                btnAlign: 'c',
                yes: function (index, layero) {
                    $("#target").remove();
                    layer.close(index);
                    layer.msg('操作成功', {time: 600});
                },
                //键盘 回车 ECS控制弹窗~
                success: function (layero, index) {
                    $(document).on('keydown', function (e) {
                        if ($(".layui-layer").hasClass("layui-layer")) {
                            if (e.keyCode == 13) {
                                layer.close(index);
                                $("#target").remove();
                                for (var i = 0; i < 1; i++) {
                                    layer.msg('操作成功', {time: 600});
                                }
                            }
                            if (e.keyCode == 27) {
                                layer.close(index);
                            }
                        }
                    });
                }
            });
        });
    }
    //table  tr列表删除按钮js
    /*$(document).on("click", ".trDel", function() {
        $(this).parent().parent("tr").attr("id","target");
        layer.alert('要删除这条记录么？', {
            icon: 0,
            title: '提示',
            btn:['删除'],
            btnAlign: 'c',
            yes: function(index, layero){
                $("#target").remove();
                layer.close(index);
                layer.msg('操作成功', {time: 600});
            },
            //键盘 回车 ECS控制弹窗~
            success: function(layero, index){
                $(document).on('keydown', function(e){
                    if($(".layui-layer").hasClass("layui-layer")){
                        if(e.keyCode == 13){
                            layer.close(index);
                            $("#target").remove();
                            for(var i= 0;i<1;i++){
                                layer.msg('操作成功', {time: 600});
                            }
                        }if(e.keyCode == 27){
                            layer.close(index);
                        }
                    }
                });
            }
        });
    });*/
    //table input 全选/不全选
    /*function selectInputAll(target) {
    $(target).click(function(){
        if($(target).is(':checked')){
                $('tbody').find('input').prop('checked',true).parents("tr").css("color","#f5682f");
            }else{
                $('tbody').find('input').prop('checked',false).parents("tr").css("color","#000");
            }

        });
        $('tbody input').click(function(){
            var i=0;
            var arr=$('tbody').find('input').length;
            $('tbody input').each(function(){
                if($(this).is(':checked')){
                    i++;
                }
            });
            if(i==arr){
                $('thead').find('input').prop('checked',true);
            }else{
                $('thead').find('input').prop('checked',false);
            }
        });
    }
    //tr 点击交互事件
    $(".menZhen tbody tr").click(function () {
        if($(this).find(".checkIn").is(":checked")){
            $(this).find(".checkIn").prop("checked",false);$(this).css("color","#000");
        }else {
            $(this).find(".checkIn").prop("checked",true);$(this).css("color","#ff334b");

        }
    });
            //checkbox冒泡事件阻止
    $("input[type='checkbox']").click(function(e){
        e.stopPropagation();
    });
    $(".menZhen input[type='checkbox']").click(function(e){
        if($(this).is(":checked")){
            $(this).parents("tr").css("color","#ff334b");
        }else {
            $(this).parents("tr").css("color","#000");
        }

    });*/
    //Jquery如何删除table里面checkbox选中的多个行
    function delCheckbox(btn,table) {
        btn.click(function() {
            var name = $("input[name='test']:checked");
            if(name.length == 0){
                layer.msg('请勾选要删除的孕妇', {time: 1000});
            }if(name.length > 0) {
                layer.alert('要删除这条记录么？', {
                    icon: 0,
                    title: '提示',
                    btn: ['删除'],
                    btnAlign: 'c',
                    yes: function (index, layero) {
                        checkedDel(name);
                        layer.close(index);
                        layer.msg('操作成功', {time: 600});
                    },
                    //键盘 回车 ECS控制弹窗~
                    success: function (layero, index) {
                        $(document).on('keydown', function (e) {
                            if ($(".layui-layer").hasClass("layui-layer")) {
                                if (e.keyCode == 13) {
                                    layer.close(index);
                                    checkedDel(name);
                                    for (var i = 0; i < 1; i++) {
                                        layer.msg('操作成功', {time: 600});
                                    }
                                }
                                if (e.keyCode == 27) {
                                    layer.close(index);
                                }
                            }
                        });
                    }
                });
            }

            function checkedDel(target) {
                target.each(function() { // 遍历选中的checkbox
                    var n = $(this).parents("tr").index()+1;  // 获取checkbox所在行的顺序
                    table.find("tr:eq("+n+")").remove();
                });
            }

        });
    }
    //点击切换
    function activefn1(target,active) {
        target.click(function(){
            $(this).addClass(active).siblings().removeClass(active);
        })
    }
    //table 交互样式
    function tableHover(target,color1,color2,color3){
        var  atr=target;
        var  arr= [color1,color2];
        for (var i=0;i<atr.length;i++){
            atr[i].index=i;
            atr[i].style.background=arr[i%arr.length];
            atr[i].onmouseover=function(){this.style.background=color3;};
            atr[i].onmouseout=function(){this.style.background=arr[this.index%arr.length]; };
        }
    }
    //动态获取window的高度/宽度设置给container层//////////////////////////////////////////////////
    var heights=$(window).height()-62;
    $(".container-left,.container,.container-right").height(heights);
    //ie6 hover兼容
    /*窗口处理*/
    //ie6
    var isIE = !!window.ActiveXObject;
    var isIE6 = isIE && !window.XMLHttpRequest;
    if (isIE) {
        window.onresize = function(){
            //动态获取window的高度/宽度设置给container层
            var heights=$(window).height()-42;
            $(".container-left,.container,.container-right").height(heights);
            if (isIE6) {
                var widths=window.screen.width;
                if(document.documentElement.clientWidth<1220){
                    $("body,.head").width(window.screen.width);
                    $(".container-right").width(widths);
                    $(".con-right-ul ul li").width("24.8%");
                }else{
                    $("body,.head").width(window.screen.width);
                    $(".container-right").width(widths);
                    $(".cen-table").width(widths-80);
                }
            }
        }
    }
    	function myBrowser(){
    	    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    	    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    	    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
    	    if (isIE) {
    	        var IE5 = IE55 = IE6 = IE7 = IE8 = false;
    	        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
    	        reIE.test(userAgent);
    	        var fIEVersion = parseFloat(RegExp["$1"]);
    	        IE55 = fIEVersion == 5.5;
    	        IE6 = fIEVersion == 6.0;
    	        IE7 = fIEVersion == 7.0;
    	        IE8 = fIEVersion == 8.0;
    	        if (IE6) {
    	        	 var a = document.documentElement.clientWidth;
    	             if(a<1280){
    	                 $('.tBox_3').width('51%');
    	             }else if ( a<1336 && a>1280){
    	                 $('.tBox_3').width('52%');
    	             }else if ( a<1436 && a>1336){
    	                 $('.tBox_3').width('54%');
    	             }else if ( a<1536 && a>1436){
    	                 $('.tBox_3').width('56%');
    	             }else if( a>1536 && a<1636) {
    	                 $('.tBox_3').width('58%');
    	             }else if( a>1636 && a<1836) {
    	                 $('.tBox_3').width('60%');
    	             }else if( a>1836) {
    	                 $('.tBox_3').width('63%');
    	             }
    	            return "IE6";
    	        }
    	    }
    	}
    $(function(){
        //ie6 hover兼容
        $(document).on({
            "mouseover":function(){
                $(this).addClass("active2");
            },
            "mouseout":function(){
                $(this).removeClass("active2");
            }
        },".container-left ul li");
    });
});
function changName(_this) {
    layer.alert('内容', {
        title: '修改名称',
        skin: 'layer-ext-moon',
        content:'<input type="text" placeholder="输入名称" value="" class="laNameTabel" />',
        yes: function(index, layero){
            _this.siblings('.tabelTitleN').text($(".laNameTabel").val());
            layer.close(index);
        }
    })
}
/*function addTabActive(_this) {
    _this.addClass("layui-this").siblings().removeClass("layui-this");

}*/

/*function addTableTitle() {

    if($(".layui-tab-title li").length < 7){
        $(".layui-tab-title").append(
            '<li  onclick="addTabActive($(this))"><span class="tabelTitleN">菜谱</span>'
            + (Math.random()*1000|0)
            + '<i class="layui-icon pen" onclick="changName($(this))">&#xe642;</i><i class="layui-icon layui-unselect layui-tab-close" onclick="removeTableTitle($(this))" >ဆ</i></li>'
        );
        //添加tab选项 acive自动移到新建的tab上~
        var tabTitle=$(".layui-tab-title li");
        tabTitle.eq(tabTitle.length-1).addClass("layui-this").siblings().removeClass("layui-this");
    }
}*/

/*function removeTableTitle(_this) {
    var _index = _this.parents("li").index();
    //移除tab选项
    _this.parents("li").remove();
    //移除tab选项后,active自动往前移动
    if(_this.parents("li").hasClass("layui-this")){
        $(".layui-tab-title li").eq(_index-1).addClass("layui-this");
    }
}*/
$(".back").click(function () {
    layer.alert('返回将会失去未保存的记录，确认返回？', {
        icon: 0,
        title: '提示',
        btn: ['确定'],
        btnAlign: 'c',
        yes: function (index, layero) {
            //返回操作
            window.location.href='#';
            layer.close(index);
//          layer.msg('操作成功', {time: 600});
        },
        //键盘 回车 ECS控制弹窗~
        success: function (layero, index) {
            $(document).on('keydown', function (e) {
                if ($(".layui-layer").hasClass("layui-layer")) {
                    if (e.keyCode == 13) {
                        layer.close(index);
                        //返回操作
                        window.location.href='#';
                    }
                    if (e.keyCode == 27) {
                        layer.close(index);
                    }
                }
            });
        }
    });
});
//textarea 字数交互 star
function countChar(textareaName,spanName)
{
    var textNam = document.getElementById(textareaName);
    var spanNam=  document.getElementById(spanName);
    spanNam.innerHTML =300-textNam.value.length;
    if(textNam.value.length >= 300){
        textNam.value=textNam.value.substring(0, 300)
    }
}
//input 数字输入范围限制方法以及提示框;
/*layui.use('layer', function(){
    var layer = layui.layer;
    function inputFn1(target,num1,num2) {
        $(document).on('keyup afterpaste', target, function () {
            $(this).val($(this).val().replace(/[^0-9]/g));
            if($(this).val()<num1 || $(this).val()>num2){
                layer.msg("请输入"+num1+"-"+num2+"的数字",{time: 1200});
                $(this).val(1);
            }
        });
    }
    inputFn1(".inputH",0,24);
    inputFn1(".inputM",0,60);
});*/