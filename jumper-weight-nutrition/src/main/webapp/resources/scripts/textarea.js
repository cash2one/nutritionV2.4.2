/**
 * Created by Apple on 2016/5/5 0005.
 */
function countChar(textareaName,spanName)
{
    document.getElementById(spanName).innerHTML =  document.getElementById(textareaName).value.length;
}
window.onload = function()
{
    document.getElementById(textareaName).onkeydown = function()
    {
        if(this.value.length >= 10)
            event.returnValue = false;
    }
};