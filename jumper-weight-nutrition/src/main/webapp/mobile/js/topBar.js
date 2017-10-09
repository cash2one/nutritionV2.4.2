$(document).ready(function(e) {
	setTimeout(function(){
    function getStyle(obj,attr){
        return obj.currentStyle? obj.currentStyle[attr]:getComputedStyle(obj,false)[attr];
    }

    var support = document.body.classList==undefined ? false : true ;

    var outer=document.getElementById("outer");
    var inner=document.getElementById("inner");
    var li=inner.getElementsByTagName("li");
    var innerWd=0,i=0;
	for(;i<li.length;i++){
        innerWd+=li[i].offsetWidth;
    }
    inner.style.width=innerWd+'px';
    var startX=0;
    var alls=0;
    var moveLeft=0;
    var iSpeedX = 0;
    var prevX = 0;
    var timer = null;
    var resetStartX=true;
    var maxDistance=parseInt(outer.offsetWidth-innerWd); //最大滑动距离
    var stopFlag=null;

    inner.addEventListener("touchstart",start,false);
    function start(e){
        if(inner.offsetWidth<outer.offsetWidth)return;//如果内部元素小于外框 不需要滑动
        if($("#inner li").length<5)return;
        var e=e||window.event;
        e.preventDefault();
        var touchs=e.touches[0];
        resetStartX=true;;
        //startX=prevX=touchs.pageX;
        startX=touchs.pageX;
        prevX=touchs.pageX;

        alls=this.offsetLeft;

        inner.classList.add('translateZ'); //classList 对已经增加的样式 不在判断是否添加  GPU缓存hack

        inner.addEventListener("touchmove",move,false);
        inner.addEventListener("touchend",end,false);

    }

    function move(e){
        var e=e||window.event;
        e.preventDefault();
        if (e.touches.length > 1 || e.scale && e.scale !== 1) return; // 当屏幕有多个touch或者页面被缩放过，就不执行move操作
        var touchs=e.changedTouches[0];

        iSpeedX = touchs.pageX - prevX;//获取出手一瞬间的速度
        prevX = touchs.pageX;//当前的始终覆盖上一个
        moveLeft=touchs.pageX-startX;

        var that=this;

        if(that.offsetLeft>=0){
            if(resetStartX){
                startX=touchs.pageX;
                resetStartX=false;
            }

            var  topNum=(touchs.pageX - startX)/3;
            that.style.left =topNum  + 'px';


        }else if(that.offsetLeft<=maxDistance){
            if(resetStartX){
                startX=touchs.pageX;
                resetStartX=false;
            }

            var midNum= (touchs.pageX - startX)/3 + (maxDistance);
            that.style.left = midNum+ 'px';

        }else{

            var botNum=touchs.pageX - startX + alls;
            that.style.left =botNum  + 'px';
        }

        //this.style.webkitTransform='translateZ(0)'; //移动时候  缓存到GPU层


    }



    function end(e){
        var e=e||window.event;
        e.preventDefault();
        var self=this;
        var touchs=e.changedTouches[0];
        var minDis= touchs.pageX-startX;

        function  sliderMove(){
            if(Math.abs(iSpeedX)<=1||self.offsetLeft>50||self.offsetLeft<maxDistance-50){

                if(self.offsetLeft>=0){
                    $(inner).animate({left:0},300,function(){
                        self.classList.remove('translateZ');   //移除PGU hack
                    });
                }
                if(self.offsetLeft<maxDistance){
                    $(inner).animate({left:maxDistance+'px'},300,function(){
                        self.classList.remove('translateZ');   //移除PGU hack
                    });
                }
                window.cancelAnimationFrame(stopFlag);//可以取消该次动画
                stopFlag=null;
                sliderMove=null;  //清空对象
            }else{
                iSpeedX *= 0.95;
                self.style.left = parseInt(self.offsetLeft + iSpeedX) + 'px';
                stopFlag=requestAnimationFrame(arguments.callee);
            }
        }

        if(Math.abs(minDis)>5) { //减少tap时候 bug  有时候轻轻一碰带来的问题 用户事项tap 时候
            sliderMove();
        }


        self.removeEventListener("touchmove",move,false);
        self.removeEventListener("touchend",end,false);
        //self.style.webkitTransform='';     

    }


//}
	},300);
});