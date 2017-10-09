$(function (){
	var path = $("#path").val(),
		$table = $("#table1");
	$table.jqGrid({
		url : path + "/group_im/list.do",
		mtype : "POST",
		datatype : "json",
		styleUI : 'Bootstrap',
		colModel: [
		    { label: '圈子ID', name: 'groupId', hidden: true},
			{ label: '圈子名', name: 'groupName'},
			{ label: '简介', name: 'groupBriefIntr'},
			{ label: '二维码', name: 'groupImgUrl', formatter:function(value){
				return "<img class='img80' src='"+value+"'  alt='' />";
			}},
			{ label: '创建人', name: 'createUserId'},
			{ label: '创建时间', name: 'createUserTime', formatter:function(value){return value ? new Date(parseInt(value)).format() : ''}},
			{ label: '人数', name: 'peopleNumber'},
			{ label: '状态', name: 'dataStatus', formatter:function(value){
                if(value == 1){
                    return '禁用';
                }else{
                    return '正常';
                }}
            },
			{ label: '圈子类型', name: 'allowAdd', formatter:function(value){
                if(value == 1){
                    return '公开圈子';
                }else{
                    return '非公开圈子';
                }}
            },
			{ label:'操作', name: 'delete', width:200, formatter:function(a, b , c){
				if (c.dataStatus == 1){
					return "<a href='#' class='table_btn_yellow link-a' onclick='operation("+c.groupId+", "+c.dataStatus+")'>启用</a>" +
            				"<a class='look_btn table_btn_green tureBtn_1 link-a' href='#' onclick='load("+c.groupId+")' >编辑</a>";
	            }else {
	            	return "<a href='#' class='table_btn_red link-a' onclick='operation("+c.groupId+", "+c.dataStatus+")'>禁用</a>" +
            				"<a class='look_btn table_btn_green tureBtn_1 link-a' href='#' onclick='load("+c.groupId+")' >编辑</a>";
	            }}
	        }
		],
		rowNum : 5,
		rowList : [5, 10, 20, 30 ],
		pager : '#pager',
		multiselect : true,
		shrinkToFit : true,
		viewrecords : true
	});
	
	
	/**
	 * 检索查询
	 */
	$('.searchBtn').click(function (){
		var obj = $('.search-form').serialize();
		$('#table1').jqGrid('setGridParam', {
			url : path + "/group_im/list.do?t"+new Date(),
            postData: $('.search-form').serialize(), //发送数据
            page : 1,
        }).trigger('reloadGrid');
	});
	
	/**
	 * 打开新增窗口
	 */
	$(".tureBtn_1").click(function(){
	    $(".group-add").fadeIn(120);
	    $(".win_3").fadeOut(120);
	});
	
	/**
	 * 添加圈子
	 */
	$(".confirm-add").click(function (){
		$.ajax({
			type: 'POST',
			url : path + '/group_im/create.do?t='+new Date(),
			data : $('#add_group_form').serialize(),
			success : function(data) {
				$('#table1').jqGrid("setGridParam").trigger('reloadGrid');
				$(".win_2").fadeOut(120);
	    		$.msgDialog(1, data.msgbox, 2.5);
			},error: function (data){
				$.msgDialog(2, data.msgbox, 2.5);
			}
		});
	});
	
	/**
	 * 编辑保存
	 */
	$('.confirm-edit').click(function (){
		$.ajax({
			type: 'POST',
			url : path + '/group_im/edit.do?t='+new Date(),
			data : $('#edit_group_form').serialize(),
			success : function(data) {
				$('#table1').jqGrid("setGridParam").trigger('reloadGrid');
				$(".win_2").fadeOut(120);
				$.msgDialog(1, data.msgbox, 2.5);
			},error: function (data){
				$.msgDialog(2, data.msgbox, 2.5);
			}
		});
	});
	
	/**
	 * 取消
	 */
	$('.cancel-edit, .cancel-add, .offBtn_2').click(function (){
		var length = byteLength($("#edit_group textarea").val());
		$(".group-add").fadeOut(120);
		$("#edit_group").fadeOut(120);
	});
	
	/**
	 * 限制输入只许输入500字
	 */
	$("#edit_group textarea, .group-add textarea").keyup(function (){
		var $this = $(this);
		if (byteLength($this.val()) > 1500){
			var num = $this.val().substr(0, 1499);
			$this.val(num);
			$.msgDialog(2, "汉字不许超过500个!", 2.5);
			return;
		}
	});
	
});

/**
 * 禁用/启用
 * @param groupId 交流圈ID
 * @param type 操作类型
 */
function operation(groupId, type){
	var path = $("#path").val();
	$.ajax({
		type: 'POST',
		url : path + '/group_im/edit_state.do?t='+new Date(),
		data : "groupId="+groupId+"&dataStatus="+type,
		success : function(data) {
			$('#table1').jqGrid("setGridParam").trigger('reloadGrid');
			$.msgDialog(1, data.msgbox, 0.5);
		},error: function (data){
			$("#winC img").attr("src", path+"/resources/images/error.png");
			$.msgDialog(2, data.msgbox, 0.5);
		}
	});
}

/**
 * 根据交流圈ID查询交流圈信息
 * @param groupId
 */
function load(groupId){
	var path = $("#path").val();
	$.ajax({
		type: 'POST',
		url : path + '/group_im/load.do?t='+new Date(),
		data : 'groupId='+groupId,
		success : function(suc) {
			initData(suc.data);
		}
	});
	$("#edit_group").fadeIn(120);
}

/**
 * 初始化编辑页面
 * @param data 选中的数据
 */
function initData(data){
	$("#edit_group input[type='text']").each(function(i) {
		var $this =  $(this);
		$.each(data, function(name, value) {
			if(name==$this.attr("name")){
				$this.val(value);
			}
		});
	});
	
	$("#edit_group input[type='hidden']").each(function(i) {
		var $this =  $(this);
		$.each(data, function(name, value) {
			if(name==$this.attr("name")){
				$this.val(value);
			}
		});
	});
	
	$("#edit_group textarea").each(function(i) {
		var $this =  $(this);
		$.each(data, function(name, value) {
			if(name==$this.attr("name")){
				$this.val(value);
			}
		});
	});
	
	$("#edit_group input[type='checkbox']").each(function(i) {
		var $this =  $(this);
		$.each(data, function(name, value) {
			if(name==$this.attr("name")){
				if (value == 1){
					$this.attr("checked",'checked'); 
					$this.val(1);
				}
			}
		});
	});
}

