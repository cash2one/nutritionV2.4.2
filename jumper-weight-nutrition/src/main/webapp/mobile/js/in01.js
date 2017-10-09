/**
 * Created by Administrator on 2016/11/25.
 */
setTimeout(function(){

    var n =0;

    var u,end,start,g,g2;

    var w2=52;

    var widthVal =$("div[data-page='profile1']").find(".row").eq(0).find(".number").attr("initial-value");

    $('.ruler .main').eq(0).css({
        '-webkit-transform':'translateX(-'+parseInt(widthVal*w2)+'px)'
    }).attr('value',widthVal*w2);

    for( var i =0 ; i < $('.ruler').length; i++){
        var liW = $('.ruler').eq(i).find("li").width();
        var size =  $('.ruler').eq(i).find('li').size();
    }

    //阻止默认事件
    $('body').on('touchcancel,touchend,touchmove,touchstart',function(e){
        e.preventDefault();
    });


    //标尺点击事件；
    $('.ruler ul').on("touchstart",function(e){
        var  initial = $(this).attr('data-initial');//获取 数据属性；
        e.stopPropagation();

        v = parseInt($(this).parent(".main").attr('value'));//获取value属性 用来区分量变级数
        if($(this).closest('.ruler').hasClass("ruler-weight")){//根据 不同的尺子设置参数；
            start = 0;
            end = -2185;
            g = 52;
        }


        if(initial == 'true'){
            startX = e.originalEvent.changedTouches[0].pageX+v;//触摸目标在页面时的x坐标。
            $(this).attr('data-initial','false');               //??
        }else{
            startX = e.originalEvent.changedTouches[0].pageX-v;// ？？？？
        }

    });
    //标尺滑动事件；
    $('.ruler ul').on("touchmove",function(e){

        var number = parseInt($(this).closest(".row").find('.number').attr('value'));
        moveX = e.originalEvent.changedTouches[0].pageX;//触摸目标在页面中的 滑动了多少x坐标。

        X = moveX - startX;//滑动的数值
        if(X>0){
            var vv = $(this).parent(".main").attr('value');

            if(vv >=start){

                start = X>start ? start : X;

                $(this).parent(".main").css({
                    '-webkit-transform':'translateX('+start+'px)'
                }).attr('value',start);
            }else{
                $(this).parent(".main").css({
                    '-webkit-transform':'translateX('+X+'px)'
                }).attr('value',X);
            }

            if($(this).closest('.ruler').hasClass("ruler-weight")){
                var val = (number-20+Math.abs(vv/g)/0.4).toFixed(1);//.replace('.0','');
                $(this).closest(".row").find('.number').text(val);
                getCurrBMI(val);
            }else{
                $(this).closest(".row").find('.number').text(Math.ceil(number-(vv/g)-10));

                if($(this).closest('.ruler').hasClass("ruler-age")){
                    var ageVal = $(this).closest(".row").find('.number').text();
                    $(this).closest(".row").find('.number').text(parseInt(ageVal-2))
                }
            }

        }else{

            var vv = $(this).parent(".main").attr('value');
            if($(this).parent(".main").attr('value') <=end){
                end = X< end ? end : X;
                $(this).parent(".main").css({
                    '-webkit-transform':'translateX('+end+'px)'
                }).attr('value',end);
            }else{
                $(this).parent(".main").css({
                    '-webkit-transform':'translateX('+X+'px)'
                }).attr('value',X);
            }

            if($(this).closest('.ruler').hasClass("ruler-weight")){

                var val = (number-20+Math.abs(vv/g)/0.4).toFixed(1);

                if(val == '100.1'){val=100;}

                $(this).closest(".row").find('.number').text(val);
                getCurrBMI(val);

            }else{
                $(this).closest(".row").find('.number').text(Math.ceil(number+Math.abs(vv/g)-10));

                if($(this).closest('.ruler').hasClass("ruler-age")){
                    var ageVal = $(this).closest(".row").find('.number').text();
                    $(this).closest(".row").find('.number').text(parseInt(ageVal-2))
                }
            }
        }
        e.preventDefault();
    });


    $('.ruler ul').on("touchend",function(e){

        e.stopPropagation();

        moveEndX = e.originalEvent.changedTouches[0].screenX;

        X = moveEndX - startX;
        var arr = new Array();

        if($(this).closest('.ruler').hasClass("ruler-age")){

            var value=  Math.abs($(this).parent(".main").attr("value"));

            var value2 = Math.round(Math.abs(value)/100)*100;

            if(value > value2){
                value2+=50;
            }

            $(this).parent(".main").css({
                '-webkit-transform':'translateX(-'+value2+'px)'
            }).attr('value','-'+value2);
        }


        $(this).closest(".page").find(".number").each(function(){
            var txt = $(this).text();
            arr.push(txt);
        });

        var arrayJoin = arr.join('##');

        $(this).closest(".page").find('input[type="hidden"]').val(arrayJoin);

    });
    //

},800);
