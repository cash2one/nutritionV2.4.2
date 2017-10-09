/**
 * Created by Apple on 2016/5/4 0004.
 */
$(document).ready(function(){
    $(".li_index").click(function(){
        $(this).find(".hover").slideToggle(180);
    });
    $(".li_index").toggle(
        function(){
            $(this).find(".title_img").css("transform","rotate(-90deg)");},
        function(){
            $(this).find(".title_img").css("transform","rotate(0deg)");
        }
    );
    $("#menu ul li .hover") .click(function(e){// e  事件event
        e.stopPropagation();//阻止点击事件，点击hover不收缩；
    });
    $("#menu ul li p").each(function(){
        $(this).click(function(){
            $(".target").removeClass("active");
            $(this).attr("class","target");
            $(this).addClass("active");
        })
    });
});
//修改window JS
function input_show(){
    abox1.style.display='none';
    changeBtn1.style.display='none'
    abox2.style.display='block';
    saveBtn1.style.display='block';
    outBtn1.style.display='block';
    table_a_box.style.display='block'
}
function input_hide(){
    abox1.style.display='block';
    changeBtn1.style.display='block'
    abox2.style.display='none';
    saveBtn1.style.display='none';
    outBtn1.style.display='none';
    table_a_box.style.display='none'
}

function input_show2(){
    dbox1.style.display='none';
    changeBtn2.style.display='none'
    dbox2.style.display='block';
    saveBtn2.style.display='block';
    outBtn2.style.display='block';
    table_d_box.style.display='block'
}
function input_hide2(){
    dbox1.style.display='block';
    changeBtn2.style.display='block'
    dbox2.style.display='none';
    saveBtn2.style.display='none';
    outBtn2.style.display='none';
    table_d_box.style.display='none'
}

