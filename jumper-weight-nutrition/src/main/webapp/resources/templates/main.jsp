<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" >
<head>
    <meta charset="utf-8">
    <title>天使医生 社交后台</title>
    <link href="${pageContext.request.contextPath}/resources/styles/ccs.css" type="text/css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/styles/default.css" type="text/css" rel="stylesheet" >
    
    <link href="${pageContext.request.contextPath}/resources/styles/foundation-datepicker.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/framework/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/framework/jqgrid/css/ui.jqgrid-bootstrap.css" rel="stylesheet" type="text/css">

	<script src="${pageContext.request.contextPath}/resources/scripts/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/scripts/table_click.js"></script>
	<script src="${pageContext.request.contextPath}/resources/scripts/foundation-datepicker.js"></script>
	<script src="${pageContext.request.contextPath}/resources/scripts/foundation-datepicker.zh-CN.js"></script>
	<script src="${pageContext.request.contextPath}/resources/scripts/common.js"></script>
	<script src="${pageContext.request.contextPath}/resources/scripts/menu.js"></script>
	<script src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
	<script src="${pageContext.request.contextPath}/resources/scripts/winshow.js"></script>
	<script src="${pageContext.request.contextPath}/resources/framework/jqgrid/js/grid.locale-cn.js"></script>
	<script src="${pageContext.request.contextPath}/resources/framework/jqgrid/js/jquery.jqGrid.min.js"></script>
	
	<script type="text/javascript">
		$.jgrid.defaults.height = 550;
		$(".caozuo .look_btn").click(function(){
		    $(".win_1").fadeTo(120, 1);
		});
		$(".win_1 .table_list img").click(function(){
		    $(".win_1").fadeTo(120, 0);
		});
	</script>
<body>
	<input type="hidden" value="${pageContext.request.contextPath}" id="path"/>
	<div id="content">
       <div id="menu" class="stop_select">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/resources/images/logo2.png" style="width: 80px;margin-bottom: 20px;">
            <h2>天使医生</h2>
            <p>社交管理系统</p>
            <div class="login">
                <ul>
                    <li class="iconfont">&#xe602;</li>
                    <li>黄光超</li>
                    <li>退出</li>
                </ul>
            </div>
        </div>
        <ul>
            <li class="li_index curr">
                <span class="title"><strong></strong>话题组</span><img class="title_img" style="" src="${pageContext.request.contextPath}/resources/images/1.png"/>
                <div class="hover first">
                    <div class="line"></div>
                    <p class="target"><strong class="ml-p"></strong>话题管理</p>
                    <p class="target"><strong class="ml-p"></strong>成员管理</p>
                    <p><strong class="ml-p"></strong>帖子管理</p>
                </div>
            </li>
            <li class="li_index">
                <span class="title"><strong  style="background-position:0 -20px"></strong>交流圈</span><img  class="title_img" src="${pageContext.request.contextPath}/resources/images/1.png"/></span>
                <div class="hover first">
                    <div class="line"></div>
                    <p class="target" loadsrc="${pageContext.request.contextPath}/resources/templates/group/im_group.jsp"><strong class="ml-p"></strong>圈子管理</p>
                    <p><strong class="ml-p"></strong>成员管理</p>
                </div>
            </li>
            <li class="li_index">
                <span class="title"><strong  style="background-position:0 -42px"></strong>更多的菜单</span><img  class="title_img" src="${pageContext.request.contextPath}/resources/images/1.png"/></span>
                <div class="hover first">
                    <div class="line"></div>    
                    <p loadsrc="${pageContext.request.contextPath}/resources/templates/report/report.jsp"><strong class="ml-p"></strong>举报中心</p>
                    <p><strong class="ml-p"></strong>运营管理</p>
                    <p><strong class="ml-p"></strong>操作记录</p>
                </div>
            </li>
        </ul>
    </div>
    <div id="maincontent"></div>
	<div id="footer">
		<a href="#">关于天使医生</a> |  <a href="#">联系邮箱</a><br/>Copyright © 2012-2015 天使医生. All Rights Reserved 粤ICP备07070821号-2
	</div>
	
	<div class="win_3 radius_4px" id="winC" style="z-index: 2002">
       <p style="line-height: 0;margin-top: 16px"> <img src=""></p>
       <p style="line-height: 0;margin-top: 24px" class="not-wen">操作成功</p>
       <div>
           <ul class="win_btn3">
               <li class="offBtn2">确定</li>
           </ul>
       </div>
   </div>
	
</div>
</body>
</html>