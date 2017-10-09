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
				var nutritionConsult = json.data.nutritionConsult;
				$("#hideDiet").prop("checked",hideDiet);
				$("#hideSport").prop("checked",hideSport);
				$("#hideFoodtab").prop("checked",hideFoodtab);
				$("#hideExchange").prop("checked",hideExchange);
				$("#nutritionConsult").val(nutritionConsult);
			}
		}
	});
	
	//保存报告 设置
	$("#saveSettings").click(function(){
		var hideDiet = $("#hideDiet").prop("checked")==true?1:0;
		var hideSport = $("#hideSport").prop("checked")==true?1:0;
		var hideFoodtab = $("#hideFoodtab").prop("checked")==true?1:0;
		var hideExchange = $("#hideExchange").prop("checked")==true?1:0;
		var nutritionConsult = $("#nutritionConsult").val();
		var param = {
				hospitalId:hospitalId,
				hideDiet:hideDiet,
				hideSport:hideSport,
				hideFoodtab:hideFoodtab,
				hideExchange:hideExchange,
				nutritionConsult:nutritionConsult
		};
		$.ajax({
			url:basePath+"/settings/saveSetting",
			type:"post",
			dataType:"json",
			data:param,
			async:false,
			success:function(json){
				if(json.msg == 1){
					if(nutritionConsult == 1){
						$("#noRead").css("display","block");
						$.cookie("noRead","show");
					}else if(nutritionConsult == 0){
						$("#noRead").css("display","none");
						$.cookie("noRead","hidden");
					}
					layer.msg("保存成功",{time:1000});
				}
			}
		});
	});
	
	//加载短信模板数据
	/*$.ajax({
		url:basePath+"/settings/findHospitalTemplate",
		type:"post",
		dataType:"json",
		data:{hospitalId:hospitalId, type:0},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var data = json.data;
				var msgHtml = "";
				for (var i = 0; i < data.length; i++) {
					//通知模板
					if(data[i].type == 1){
						msgHtml += '<h5 class="textList ov"><span class="fl dataContent textSpan">'+data[i].content+'</span>'+
						'<span class="endBtn2 fr"><span class="bianji editMsgTemplate" editType="edit" templateId='+data[i].id+'>编辑</span>|<span class="del delMsgTemplate" templateId='+data[i].id+'>删除</span></span></h5>';
					}
				}
				
				if(msgHtml != ""){
					msgHtml = '<h5 class="textList">已添加模板：</h5>'+msgHtml;
					$("#msgHtml").html(msgHtml);
				}
			}
		}
	});*/
	
	//加载就诊原因和提示文案
	$.ajax({
		url:basePath+"/settings/findHospitalOutpatientReason",
		type:"post",
		dataType:"json",
		data:{hospitalId:hospitalId, type:0},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var data = json.data;
				var firstVisitHtml = "",subsequentVisitHtml = "";
				for (var i = 0; i < data.length; i++) {
					//初诊就诊原因
					firstVisitHtml += '<tr><td class="reason">'+data[i].outpatientReason+'</td><td class="tips">'+data[i].tips+'</td>'+
						'<td class="oper"><span class="bianji editOutpReason" editType="edit" outpType="'+data[i].type+'" reason_id="'+data[i].id+'">编辑</span>'+
						'<span class="bianji configOutpReason" outpType="'+data[i].type+'" reason_id="'+data[i].id+'" isSkipDiet="'+data[i].isSkipDiet+'" isSkipSport="'+data[i].isSkipSport+'">配置</span>';
					if(data[i].canDelete == 1){
						firstVisitHtml += '<span class="del delOutpReason" reason_id="'+data[i].id+'">删除</span>';
					}
					firstVisitHtml += '</td></tr>';
				}
				
				if(firstVisitHtml != ""){
					$("#firstVisit").css("display","").append(firstVisitHtml);
				}
			}
		}
	});
	$.ajax({
		url:basePath+"/settings/findHospitalOutpatientReason",
		type:"post",
		dataType:"json",
		data:{hospitalId:hospitalId, type:1},
		async:false,
		success:function(json){
			if(json.msg == 1){
				var data = json.data;
				var firstVisitHtml = "",subsequentVisitHtml = "";
				for (var i = 0; i < data.length; i++) {
					//复诊就诊原因
					subsequentVisitHtml += '<tr><td class="reason">'+data[i].outpatientReason+'</td><td class="tips">'+data[i].tips+'</td>'+
						'<td class="oper"><span class="bianji editOutpReason" editType="edit" outpType="'+data[i].type+'" reason_id="'+data[i].id+'">编辑</span>';
					if(data[i].canDelete == 1){
						subsequentVisitHtml += '<span class="del delOutpReason" reason_id="'+data[i].id+'">删除</span>';
					}
					subsequentVisitHtml += '</td></tr>';
				}
				if(subsequentVisitHtml != ""){
					$("#subsequentVisit").css("display","").append(subsequentVisitHtml);
				}
			}
		}
	});
	
	
	//删除模板设置
	$(document).on("click",".delMsgTemplate",function(){
		var $this = $(this);
		var templateId = $this.attr("templateId");
//		var template = $this.parents("h5").find(".dataContent").text();
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
	     					msgTotal = msgTotal - 1;
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
		if(editType == "add" && msgTotal >= 40){
			layer.msg("最多可添加40条通知模板");
			return;
		}
		var id = 0, content = "";
		if(editType == "edit"){
			id = $this.attr("templateId");
			content = $this.parent("span").siblings("span.dataContent").text();
		}
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
                }
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
         				if (json.msg != 1) {
         					layer.msg("编辑失败，请稍后重试！",{time:1000});
         					return;
						}
     					var data = json.data;
     					layer.close(index);
     					if(editType == "edit"){
     						$this.parent("span").siblings("span").text(data.content);
     					}else if(editType == "add"){
     						//初始化分页
     						msgInitPage(msgPageData);
     					}
         					
         			}
         		});
            }
        });
	});
	
	//删除就诊原因
	$(document).on("click",".delOutpReason",function(){
		var $this = $(this);
		var reasonId = $this.attr("reason_id");
		var param = {
			id:reasonId,
			isDelete:1
		};
		var reason = $this.parents("tr").find(".reason").text();
		layer.alert('确定删除'+reason+'就诊原因？', {
	         icon: 0,
	         title: '提示',
	         btn:['删除','取消'],
	         btnAlign: 'c',
	         yes: function(index, layero){
	        	 layer.close(index);
	             $.ajax({
	     			url:basePath+"/settings/saveHospitalOutpatientReason",
	     			type:"post",
	     			dataType:"json",
	     			contentType: "application/json;charset=utf-8",
	     			data:JSON.stringify(param),
	     			async:false,
	     			success:function(json){
	     				if(json.msg == 1){
	     					$this.parents("tr").remove();
	     				}else{
	     					layer.msg("删除失败，请稍后重试！",{time:1000});
	     				}
	     			}
	     		});
	         }
	     });
	});
	
	//编辑（添加）就诊原因
	$(document).on("click",".editOutpReason",function(){
		var $this = $(this);
		var editType = $this.attr("editType");
		var type = $this.attr("outpType");
		var id = 0, reason = "", tips = "",count = 0;
		if(editType == "edit"){
			id = $this.attr("reason_id");
			reason = $this.parent("td").siblings(".reason").text();
			tips = $this.parent("td").siblings(".tips").text();
		}
		if(editType == "add"){
			count = $this.next("table").find("tr").length-1;
			if(count>=10){
				layer.msg("最多可添加10条就诊原因");
			}
		}
		if(count<10 || editType == "edit"){
			layer.alert('', {
				title: '就诊原因与提示文案设置',
	            btn: ['确定','取消'] ,
	            content:'<label>就诊原因<input type="text" class="careCause" id="reason" placeholder="请输入用户就诊原因，不超过10个中文字符。"/></label>提示文案<textarea id="tips" class="layui-layer-input tInput" placeholder="请输入文案设置内容，不超过50个中文字符。"></textarea><p id="errorMsg"></p>',
	            success:function(layero, index){
	            	$("#reason").val(reason);
		        	$("#reason").focus().val(reason);
		        	$("#tips").val(tips).text(tips);
	            	/*var setFocusText = $("#tips");
	    			utilityHandle.setFocus(setFocusText,setFocusText.val().length);*/
		        },
	            yes: function(index, layero){
	                if($('#reason').val().trim() == ""){
	                    $("#errorMsg").text("就诊原因不能为空");
	                    return false;
	                }else if($('#reason').val().trim().length>10){
	                	$("#errorMsg").text("就诊原因最多可输入10个中文字符");
	                	return false;
	                }else if($('#tips').val().trim() == ""){
	                    $("#errorMsg").text("提示文案不能为空");
	                    return false;
	                }else if($('#tips').val().trim().length>50){
	                	$("#errorMsg").text("提示文案最多可输入50个中文字符");
	                	return false;
	                }else{
	                	$("#errorMsg").text("");
	                	var param = {
	                		id:id,
	                		hospitalId:hospitalId,
	                		type:type,
	                		outpatientReason:$('#reason').val().trim(),
	                		tips:$("#tips").val().trim()
	                	};
	                	//添加的时候判断是否是第一条，第一条默认不能删除
	                	if(editType == "add"){
	                		var canDelete = 1;
		                	if(count == 0){
		                		canDelete = 0;
		                	}
		                	param.canDelete = canDelete;
	                	}
	                	$.ajax({
	             			url:basePath+"/settings/saveHospitalOutpatientReason",
	             			type:"post",
	             			dataType:"json",
	             			contentType: "application/json;charset=utf-8",
	             			data:JSON.stringify(param),
	             			async:false,
	             			success:function(json){
	             				if(json.msg == 1){
	             					var data = json.data;
	             					var html = "";
	             					layer.close(index);
	             					if(editType == "edit"){
	             						$this.parent("td").siblings(".reason").text(data.outpatientReason);
	             						$this.parent("td").siblings(".tips").text(data.tips);
	             					}else if(editType == "add"){
	             						html += '<tr><td class="reason">'+data.outpatientReason+'</td><td class="tips">'+data.tips+'</td>'+
		        							'<td class="oper"><span class="bianji editOutpReason" editType="edit" outpType="'+data.type+'" reason_id="'+data.id+'">编辑</span>';
	             						if(data.type == 0){
	             							html += '<span class="bianji configOutpReason" outpType="'+data.type+'" reason_id="'+data.id+'" isSkipDiet="'+data.isSkipDiet+'" isSkipSport="'+data.isSkipSport+'">配置</span>';
	             						}
		        						if(data.canDelete == 1){
		        							html += '<span class="del delOutpReason" reason_id="'+data.id+'">删除</span>';
		        						}
		        						html += '</td></tr>';
		        						if(count == 0){
		        							$this.next("table").css("display","");
		        						}
		        						$this.next("table").append(html);
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
	
	//配置初诊就诊原因参数
	$(document).on("click",".configOutpReason",function(){
		var $this = $(this);
		var reasonId = $this.attr("reason_id");
		var isSkipDiet = $this.attr("isSkipDiet");
		var isSkipSport = $this.attr("isSkipSport");
		layer.alert('', {
			title: '配置参数',
            btn: ['确定','取消'] ,
            area: ['440px', '280px'],
            content:'<div class="l-box"><label>饮食记录</label><select class="sel" id="isSkipDiet"><option value="false">不跳过</option><option value="true">跳过</option></select></div>'+
            	'<div class="l-box"><label>运动记录</label><select class="sel" id="isSkipSport"><option value="false">不跳过</option><option value="true">跳过</option></select></div>',
            success:function(layero, index){
            	$("#isSkipDiet").val(isSkipDiet);
            	$("#isSkipSport").val(isSkipSport);
	        },
            yes: function(index, layero){
            	isSkipDiet = $("#isSkipDiet").val();
            	isSkipSport = $("#isSkipSport").val();
            	var param = {
            			id:reasonId,
            			isSkipDiet:isSkipDiet,
            			isSkipSport:isSkipSport
            	};
            	$.ajax({
         			url:basePath+"/settings/saveHospitalOutpatientReason",
         			type:"post",
         			dataType:"json",
         			contentType: "application/json;charset=utf-8",
         			data:JSON.stringify(param),
         			async:false,
         			success:function(json){
         				if(json.msg == 1){
         					var data = json.data;
         					layer.close(index);
         					$this.attr("isSkipDiet",data.isSkipDiet);
         					$this.attr("isSkipSport",data.isSkipSport);
         					layer.msg("保存成功",{time:1000});
         				}else{
         					layer.msg("编辑失败，请稍后重试！",{time:1000});
         				}
         			}
         		});
            }
        });
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