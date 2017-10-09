/**
 * 设置页面js
 */
$(function() {
	//加载二维码
	$("#QRCode").attr("src", basePath + "/settings/generateQRCode?hospitalId=" + hospitalId);
	
	//下载二维码
	$("#download").click(function() {
		window.location.href = basePath + "/settings/downloadQRCode?hospitalId=" + hospitalId
	});
	//加载设置数据
	$.ajax({
		url:basePath+"/settings/findSettingByHospId",
		type:"post",
		dataType:"json",
		data:{hospitalId:hospitalId},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var hideDiet = json.data.hideDiet;
				var hideSport = json.data.hideSport;
				var hideFoodtab = json.data.hideFoodtab;
				var hideExchange = json.data.hideExchange;
				$("#hideDiet").prop("checked",hideDiet);
				$("#hideSport").prop("checked",hideSport);
				$("#hideFoodtab").prop("checked",hideFoodtab);
				$("#hideExchange").prop("checked",hideExchange);
			}
		}
	});
	
	//保存报告 设置
	$("#saveSettings").click(function(){
		var hideDiet = $("#hideDiet").prop("checked")==true?1:0;
		var hideSport = $("#hideSport").prop("checked")==true?1:0;
		var hideFoodtab = $("#hideFoodtab").prop("checked")==true?1:0;
		var hideExchange = $("#hideExchange").prop("checked")==true?1:0;
		var param = {
				hospitalId:hospitalId,
				hideDiet:hideDiet,
				hideSport:hideSport,
				hideFoodtab:hideFoodtab,
				hideExchange:hideExchange
		};
		$.ajax({
			url:basePath+"/settings/saveSetting",
			type:"post",
			dataType:"json",
			data:param,
			async:false,
			success:function(json){
				if(json.msg == 1){
					layer.msg("保存成功",{time:1000});
				}
			}
		});
	});
	
	//加载模板设置数据
	$.ajax({
		url:basePath+"/settings/findHospitalTemplate",
		type:"post",
		dataType:"json",
		data:{hospitalId:hospitalId, type:0},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var data = json.data;
				var msgHtml = "",firstOutpHtml = "",visitHtml = "",outpReasonHtml = "";
				for (var i = 0; i < data.length; i++) {
					//通知模板
					if(data[i].type == 1){
						msgHtml += '<h5 class="textList ov"><span class="fl dataContent textSpan">'+data[i].content+'</span>'+
						'<span class="endBtn2 fr"><span class="bianji editMsgTemplate" editType="edit" templateId='+data[i].id+'>编辑</span>|<span class="del delMsgTemplate" templateId='+data[i].id+'>删除</span></span></h5>';
					}
					
					//用户扫码提示文案
					//初诊
					if(data[i].type == 3){
						firstOutpHtml = data[i].content;
						$("#editFirstOutp").attr("templateId",data[i].id);
					}
					//复诊
					if(data[i].type == 4){
						visitHtml = data[i].content;
						$("#editVisit").attr("templateId",data[i].id);
					}
					
					//用户就诊原因
					if(data[i].type == 2){
						outpReasonHtml += '<h5 class="textList ov"><span class="fl dataContent textSpan" style="width:210px;">'+data[i].content+' </span><span class="endBtn2 fr">'+
							'<span class="bianji editOutpReason" editType="edit" templateId='+data[i].id+'>编辑</span>|<span class="del delOutpReason" templateId='+data[i].id+'>删除</span></span></h5>';
					}
				}
				
				if(msgHtml != ""){
					msgHtml = '<h5 class="textList">已添加模板：</h5>'+msgHtml;
					$("#msgHtml").html(msgHtml);
				}
				if(firstOutpHtml != ""){
					$("#firstOutpHtml").html(firstOutpHtml);
				}
				if(visitHtml != ""){
					$("#visitHtml").html(visitHtml);
				}
				if(outpReasonHtml != ""){
					outpReasonHtml = '<h5 class="textList">已添加就诊原因：</h5>'+outpReasonHtml;
					$("#outpReasonHtml").html(outpReasonHtml);
				}
			}
		}
	});
	
	//删除模板设置
	$(document).on("click",".delMsgTemplate,.delOutpReason",function(){
		var $this = $(this);
		var templateId = $this.attr("templateId");
		layer.alert('是否确认删除？', {
	         icon: 0,
	         title: '提示',
	         btn:['删除','取消'],
	         btnAlign: 'c',
	         yes: function(index, layero){
	        	 layer.close(index);
	             $.ajax({
	     			url:basePath+"/settings/deleteHospitalTemplate",
	     			type:"post",
	     			dataType:"json",
	     			data:{templateId:templateId},
	     			async:false,
	     			success:function(json){
	     				if(json.msg == 1){
	     					$this.parents("h5").remove();
	     				}else{
	     					layer.msg("删除失败，请稍后重试！",{time:1000});
	     				}
	     			}
	     		});
	         }
	     });
	});
	
	//编辑模板
	$(document).on("click",".editMsgTemplate",function(){
		var $this = $(this);
		var editType = $this.attr("editType");
		var id = 0,content = "",count = 0;
		if(editType == "edit"){
			id = $this.attr("templateId");
			content = $this.parent("span").siblings("span.dataContent").text();
		}
		if(editType == "add"){
			count = $("#msgHtml").find(".editMsgTemplate").length;
			if(count>=5){
				layer.msg("最多可添加5条通知模板");
			}
		}
		if(count<5 || editType == "edit"){
			layer.alert('', {
	            title: '通知模板',
	            btn: ['确定','取消'] ,
	            content:'<textarea id="content" class="layui-layer-input tInput" placeholder="请输入通知内容，不超过55个中文字符。"></textarea><p id="errorMsg"></p>',
	            success:function(layero, index){
	            	$("#content").val(content).text(content);
	            	var setFocusText = $("#content");
	    			utilityHandle.setFocus(setFocusText,setFocusText.val().length);
		        },
	            yes: function(index, layero){
	                if($('#content').val().trim() == ""){
	                    $("#errorMsg").text("内容不能为空");
	                    return false;
	                }else if($('#content').val().trim().length>55){
	                	$("#errorMsg").text("超出55个字符的限制");
	                	return false;
	                }else {
	                	$("#errorMsg").text("");
	                	var param = {
	                		id:id,
	                		hospitalId:hospitalId,
	                		type:CONST.templateType[0],
	                		content:$('#content').val().trim()
	                	};
	                	$.ajax({
	             			url:basePath+"/settings/saveHospitalTemplate",
	             			type:"post",
	             			dataType:"json",
	             			contentType: "application/json;charset=utf-8",
	             			data:JSON.stringify(param),
	             			async:false,
	             			success:function(json){
	             				if(json.msg == 1){
	             					var data = json.data;
	             					layer.close(index);
	             					if(editType == "edit"){
	             						$this.parent("span").siblings("span").text(data.content);
	             					}else if(editType == "add"){
	             						var html = '<h5 class="textList ov"><span class="fl dataContent textSpan">'+data.content+'</span>'+
	            						'<span class="endBtn2 fr"><span class="bianji editMsgTemplate" editType="edit" templateId='+data.id+'>编辑</span>|<span class="del delMsgTemplate" templateId='+data.id+'>删除</span></span></h5>';
	             						$("#msgHtml").append(html);
	             					}
	             					
	             				}else{
	             					layer.msg("编辑失败，请稍后重试！",{time:1000});
	             				}
	             			}
	             		});
	                }
	            }
	        });
		}
	});
	
	//编辑初诊复诊扫码文案
	$(document).on("click","#editFirstOutp,#editVisit",function(){
		var id = $(this).attr("id");
		var templateId = $(this).attr("templateId");
		var type = 0,content = "";
		if(id == "editFirstOutp"){
			type = CONST.templateType[2];
			content = $("#firstOutpHtml").text();
		}else if(id == "editVisit"){
			type = CONST.templateType[3];
			content = $("#visitHtml").text();
		}
		layer.alert('', {
            title: '文案设置',
            btn: ['确定','取消'] ,
            content:'<textarea id="content" class="layui-layer-input tInput" placeholder="请输入文案设置内容，不超过50个中文字符。"></textarea><p id="errorMsg"></p>',
            success:function(layero, index){
            	$("#content").val(content).text(content);
            	var setFocusText = $("#content");
    			utilityHandle.setFocus(setFocusText,setFocusText.val().length);
	        },
            yes: function(index, layero){
                if($('#content').val().trim() == ""){
                    $("#errorMsg").text("内容不能为空");
                    return false;
                }else if($('#content').val().trim().length>50){
                	$("#errorMsg").text("超出50个字符的限制");
                	return false;
                }else {
                	$("#errorMsg").text("");
                	content = $('#content').val().trim();
                	var param = {
                		id:templateId,
                		hospitalId:hospitalId,
                		type:type,
                		content:content
                	};
                	$.ajax({
             			url:basePath+"/settings/saveHospitalTemplate",
             			type:"post",
             			dataType:"json",
             			contentType: "application/json;charset=utf-8",
             			data:JSON.stringify(param),
             			async:false,
             			success:function(json){
             				if(json.msg == 1){
             					layer.close(index);
             					if(id == "editFirstOutp"){
             						$("#firstOutpHtml").text(content);
             						$("#editFirstOutp").attr("templateId",json.data.id);
             					}else if(id == "editVisit"){
             						$("#visitHtml").text(content);
             						$("#editVisit").attr("templateId",json.data.id);
             					}
             				}else{
             					layer.msg("编辑失败，请稍后重试！",{time:1000});
             				}
             			}
             		});
                }
            }
        });
		
	});
	
	//编辑就诊原因
	$(document).on("click",".editOutpReason",function(){
		var $this = $(this);
		var editType = $this.attr("editType");
		var id = 0,content = "",count = 0;
		if(editType == "edit"){
			id = $this.attr("templateId");
			content = $this.parent("span").siblings("span.dataContent").text();
		}
		if(editType == "add"){
			count = $("#outpReasonHtml").find(".editOutpReason").length;
			if(count>=5){
				layer.msg("最多可添加5个就诊原因");
			}
		}
		if(count<5 || editType == "edit"){
			layer.alert('', {
	            title: '就诊原因',
	            btn: ['确定','取消'] ,
	            content:'<input type="text" id="content" style="width:320px;line-height:30px;" class="layui-layer-input" placeholder="请输入用户就诊原因"/><p id="errorMsg"></p>',
	            success:function(layero, index){
	            	$("#content").val(content);
		        	$("#content").focus().val(content);
		        },
	            yes: function(index, layero){
	                if($('#content').val().trim() == ""){
	                    $("#errorMsg").text("内容不能为空");
	                    return false;
	                }else if($('#content').val().trim().length>7){
	                	$("#errorMsg").text("最多可输入7个中文字符");
	                	return false;
	                }else {
	                	$("#errorMsg").text("");
	                	var param = {
	                		id:id,
	                		hospitalId:hospitalId,
	                		type:CONST.templateType[1],
	                		content:$('#content').val().trim()
	                	};
	                	$.ajax({
	             			url:basePath+"/settings/saveHospitalTemplate",
	             			type:"post",
	             			dataType:"json",
	             			contentType: "application/json;charset=utf-8",
	             			data:JSON.stringify(param),
	             			async:false,
	             			success:function(json){
	             				if(json.msg == 1){
	             					var data = json.data;
	             					layer.close(index);
	             					if(editType == "edit"){
	             						$this.parent("span").siblings("span").text(data.content);
	             					}else if(editType == "add"){
	             						var html = '<h5 class="textList ov"><span class="fl dataContent textSpan" style="width:210px;">'+data.content+' </span><span class="endBtn2 fr">'+
	        							'<span class="bianji editOutpReason" editType="edit" templateId='+data.id+'>编辑</span>|<span class="del delOutpReason" templateId='+data.id+'>删除</span></span></h5>';
	             						$("#outpReasonHtml").append(html);
	             					}
	             				}else{
	             					layer.msg("编辑失败，请稍后重试！",{time:1000});
	             				}
	             			}
	             		});
	                }
	            }
	        });
		}
	});
	
	var utilityHandle = {
		setFocus:function(objFocusEven,values){
			objFocusEven.focus();
			var objEven =  objFocusEven[0]; // 将Jquery对象转换为Dom对象
			if(window.getSelection){
			// 现代浏览器  
				objEven.selectionStart =objEven.selectionEnd =   values;  
			}else if(document.selection){
	            // Ie浏览器
	           var txt =  objEven.createTextRange();
	           // 将传入的控件对象转换为Dom对象，并创建一个TextRange对象
	           txt.moveStart('character', values);
	           // 设置光标显示的位置
	           txt.collapse(true);
	           txt.select();
		       }
		}};
});