<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script src="${pageContext.request.contextPath}/resources/scripts/group/group.js"></script>
</head>
<body>
	<div id="titleBox">
		<span>
			<button class="jiabutton tureBtn_1">
				<span style="font-size: 20PX; margin-right: 4px;">+</span>新增圈子
			</button>
		</span>
		<div class="search">
			<form class="search-form">
				<input placeholder="话题名字" type="text" name="groupName"  /><span class="searchBtn">搜索</span>
			</form>
		</div>
	</div>
	
	<div class="table_list" style="padding: 5px 20px;">
		<dl class="table_list_title dd-left">
			<dd>圈子推广</dd>
		</dl>
		<span class="spen-button  tureBtn_2">+</span>
	</div>
	<div id="box_show">
		<div id="box" class="li_width_3" style="min-height: 600px;">
			<table id="table1"></table>
			<div id="pager"></div>
		</div>
	
		<div class="win_2 radius_4px win_xinz win_xinz_2 group-add" id="winB" style="z-index: 2002;">
			<p class="win_xinz_p">
				新增话题 <img src="${pageContext.request.contextPath}/resources/images/close.gif" class="offBtn_2" alt="" />
			</p>
			<div class="input_table">
				<form id="add_group_form">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="text-right"><span class="color-red">*</span>交流圈名称:</td>
							<td><input type="text" name="groupName" class="win_250" placeholder="必填" /></td>
						</tr>
						<tr>
							<td class="text-right" style="vertical-align: top;">交流圈简介 :</td>
							<td><textarea name="groupBriefIntr" class="win_250" placeholder="最多输入500个字"></textarea></td>
						</tr>
						<tr>
							<td class="" colspan="2">该群禁止被搜索:<input type="checkbox" name="allowAdd" value="1" /></td>
						</tr>
					</table>
				</form>
				<p class="color-red color-ccc">勾选后，该交流圈在APP端不能被搜索，用户只能扫码加入</p>
				<p class="color-red">*：您输入有错误，重新检查输入</p>
				<div class="but-right">
					<button class="active-but confirm-add">确认</button>
					<button class="offBtn_2 cancel-add">取消</button>
				</div>
			</div>
		</div>
		
		<div class="win_2 radius_4px win_xinz win_xinz_2" id="edit_group" style="z-index: 2002;">
			<p class="win_xinz_p">
				编辑话题 <img src="${pageContext.request.contextPath}/resources/images/close.gif" class="offBtn_2" alt="" />
			</p>
			<div class="input_table">
				<form id="edit_group_form">
					<input type="hidden" name="groupId" />
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="text-right"><span class="color-red">*</span>交流圈名称:</td>
							<td><input type="text" name="groupName" class="win_250" placeholder="必填" /></td>
						</tr>
						<tr>
							<td class="text-right" style="vertical-align: top;">交流圈简介 :</td>
							<td><textarea name="groupBriefIntr" class="win_250" placeholder="最多输入500个字"></textarea></td>
						</tr>
						<tr>
							<td class="" colspan="2">该群禁止被搜索:<input type="checkbox" name="allowAdd" value="0"/></td>
						</tr>
					</table>
				</form>
				<p class="color-red color-ccc">勾选后，该交流圈在APP端不能被搜索，用户只能扫码加入</p>
				<p class="color-red">*：您输入有错误，重新检查输入</p>
				<div class="but-right">
					<button class="active-but confirm-edit">确认</button>
					<button class="offBtn_2 cancel-edit">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>