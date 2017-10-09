
/*表格样式加载代码*/

function trcolor() 
//控制间隔行颜色显示不同 
{ 
	var tabNode = document.getElementById("styletable");
	//var tabNode = document.getElementsByTagName("table")[0]; 
//获取table节点 
	var trs = tabNode.rows; 
//获取table中的所有的行 
	for(var x=1; x<trs.length;x++) { 
		if(x%2 == 1) 
		trs[x].className ="evenrowcolor"; 
	else 
		trs[x].className ="oddrowcolor"; 
	} 
} 

//通过onload方法，在网页加载完成后，运行trcolor方法，实现表格的间隔行颜色。 
window.onload = function() { 
	trcolor();
}