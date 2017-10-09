<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script src="${pageContext.request.contextPath}/resources/scripts/report/report.js"></script>
</head>
<body>

	<div id="titleBox">
		<ul class="titleBox-btn">
			<li class="ont1 active">被举报的帖子</li>
			<li class="ont2">被举报的评论</li>
			<li class="ont3">被举报的用户</li>
		</ul>
	</div>

	<div class="table_list">
		<dl class="table_list_title">
			<dt></dt>
			<dd>列表</dd>
		</dl>
	</div>

	<div id="box_show">
		<div id="box" class="li_width_3" style="height: 600px;">
			<!--被举报的帖子-->
			<table id="table1" class="ont-table1">
				<thead class="fixedThead">
					<tr>
						<th>内容</th>
						<th>所在话题</th>
						<th>发帖人</th>
						<th>举报次数</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody class="scrollTbody">
					<tr class="active">
						<td class="win320">今天在家里做了胎心，第一次听到了宝宝的声音，感觉真的很奇妙....今天在家里做了胎心,第一次听到了宝宝的声音，感觉真的很奇妙很奇妙很奇妙<a
							href="javascript:" class="tureBtn_2">[查看图片]</a></td>
						<td>孕期指导中心</td>
						<td>水水水水</td>
						<td>4次</td>
						<td>审核中</td>
						<td class="caozuo"><a href="#" class="table_btn_yellow">删除</a></td>
					</tr>
					<tr class="active">
						<td class="win320">今天在家里做了胎心，第一次听到了宝宝的声音，感觉真的很奇妙....今天在家里做了胎心,第一次听到了宝宝的声音，感觉真的很奇妙很奇妙很奇妙<a
							href="javascript:" class="tureBtn_2">[查看图片]</a></td>
						<td>孕期指导中心</td>
						<td>水水水水</td>
						<td><a class="tureBtn_1">5次</a></td>
						<td style="color: red">被举报次数过多帖子已隐藏</td>
						<td class="caozuo"><a href="#" class="table_btn_yellow">删除</a><a
							class="look_btn table_btn_green" href="#">恢复显示</a></td>
					</tr>

				</tbody>
			</table>
			<!--被举报的评论-->
			<table id="table1" class="ont-table2">
				<thead class="fixedThead">
					<tr>
						<th>内容</th>
						<th>所在话题</th>
						<th>评论人</th>
						<th>举报次数</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody class="scrollTbody">
					<tr class="active">
						<td class="win300">评论内容评论内容评论内容评论内容</td>
						<td>孕期指导中心</td>
						<td>水水水水</td>
						<td>4次</td>
						<td>审核中</td>
						<td class="caozuo"><a href="#" class="table_btn_yellow">禁言</a></td>
					</tr>
					<tr class="active">
						<td class="win300">评论内容评论内容评论内容评论内容</td>
						<td>孕期指导中心</td>
						<td>水水水水</td>
						<td><a class="tureBtn_1">5次</a></td>
						<td style="color: red">禁言中</td>
						<td class="caozuo"><a class="look_btn table_btn_green"
							href="#">解除禁言</a></td>
					</tr>

				</tbody>
			</table>

			<!--被举报的用户-->
			<table id="table1" class="ont-table3">
				<thead class="fixedThead">
					<tr>
						<th>内容</th>
						<th>所在交流圈</th>
						<th>发送人</th>
						<th>举报次数</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody class="scrollTbody">
					<tr class="active">
						<td class="win300">评论内sssss内容评论内容评论内容</td>
						<td>孕期指导中心</td>
						<td>水水水水</td>
						<td>4次</td>
						<td>审核中</td>
						<td class="caozuo"><a href="#" class="table_btn_yellow">禁言</a></td>
					</tr>
					<tr class="active">
						<td class="win300">评论内容评论内容评论内容评论内容</td>
						<td>孕期指导中心</td>
						<td>水水水水</td>
						<td><a class="tureBtn_1">5次</a></td>
						<td style="color: red">禁言中</td>
						<td class="caozuo"><a class="look_btn table_btn_green" href="#">解除禁言</a></td>
					</tr>
				</tbody>
			</table>
		</div>
		<ul id="page_ui">
			<li class="firstPage">首页</li>
			<li><</li>
			<li class="page_number">1</li>
			<li class="page_number">2</li>
			<li class="page_number">3</li>
			<li class="page_number">4</li>
			<li>></li>
			<li>尾页</li>
		</ul>
	</div>

</body>
</html>