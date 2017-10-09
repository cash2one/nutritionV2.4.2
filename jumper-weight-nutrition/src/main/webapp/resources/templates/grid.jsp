<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<script type="text/ecmascript" src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
	<script type="text/ecmascript" src="${pageContext.request.contextPath}/sfile/js/framework/jqgrid/js/grid.locale-cn.js"></script>
	<script type="text/ecmascript" src="${pageContext.request.contextPath}/sfile/js/framework/jqgrid/js/jquery.jqGrid.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/sfile/js/framework/jqgrid/css/ui.jqgrid-bootstrap.css" />
	<script>
		$.jgrid.defaults.width = 780;
		$.jgrid.defaults.height = 380;
	</script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<meta charset="utf-8" />
</head>
<body>
	<input type="hidden" value="<%=basePath %>" />
	<div style="margin: 10% 0 0 35%;">
		<table id="jqGrid"></table>
		<div id="jqGridPager"></div>
	</div>
	<script type="text/javascript">
		$(function (){
			var path = $("input[type=hidden]").val();
			$("#jqGrid").jqGrid({
				url : path+"/test/list.do",
				mtype: "POST",
			  	datatype : "json",
			  	styleUI : 'Bootstrap',
			  	colModel: [
					{ label: '编号', name: 'id', width: 75 },
					{ label: '名称', name: 'countryName', width: 90 },
					{ label: '代码', name: 'countryCode', width: 100 },
				],
		        rowNum : 10,
		        rowList : [ 10, 20, 30 ],
		        pager : '#jqGridPager',
		        viewrecords : true
			});
		});
	</script>
</body>
</html>