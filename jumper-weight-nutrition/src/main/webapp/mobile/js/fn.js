/**
 * Created by Administrator on 2017/4/20 0020.
 */
var sDte = document.getElementById("searchDetele");
var sInput = document.getElementById("search-food-input");

searchDetele(sDte,sInput);


function searchDetele(sDte,sInput){
    sDte.onclick=function () {
        sInput.value="";
    }
}
// 禁止页面因为使用rem而产生缩放的动画



