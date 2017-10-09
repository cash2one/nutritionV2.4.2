<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>聊天页面</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/css/index.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/react.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/blue2.css" />
</head>
<body>
	<input type="hidden" id="commonUrl" value="${url}"/>
	<textarea id="chat_base_info" style="display:none;">${data}</textarea>
	<div class="conter" id="conter">
			<div class="pleft" id="pleft">
				<ul id="friendList">
				</ul>
			</div>
			<div class="pright">
				<div class="tname">
					<!-- <span style="color: #919499"></span> -->
				</div>
				<div class="imMessageBox" id="imMessageCenterBox">
				</div>
				<div class="imgMessage">
						<a class="fl imga" href="javascript:;"></a>
				</div>
				<div class="sendMessageInput">
						<textarea id="messageBody" onkeydown="onTextareaKeyDown()"></textarea>
						<a class="sendMessageBotton" href="javascript:;" onclick="onSendMsg()">发送</a>
				</div>
			</div>		
		</div>
			<div id="uploadWindow">
				<h3 class="uploadTitle">
					<p class="fl">发送图片</p>
					<a class="fr" onclick="closeUpload();" href="javascript:void(0)"></a>
				</h3>
				<div class="uploadCenter">
					  <div  style="position: absolute;left: 80px;top:59px;" >
						<input type="file" class="fl" id="upload3" name="file" /> 
						<input type="submit" onclick="uploadImg()"  value="提&nbsp交" />
 					<iframe id="upframe" name="upframe" src="" style="display:none;"></iframe>
					 </div> 
				</div>
		</div>
	<!--工具类-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tool/jquery.nicescroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/sdk/json2.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tool/artDialog.source.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tool/iframeTools.source.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tool/tool.js" ></script>
	<!--Im类-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/sdk/webim.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/md5/spark-md5.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ajaxfileupload.js"></script>
	<!--控制类-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/public/public.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/public/public_kz.js"></script>
	<script type="text/javascript">
	 
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp      
    var curWwwPath=window.document.location.href;      
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp      
    var pathName=window.document.location.pathname;      
    var pos=curWwwPath.indexOf(pathName);      
    //获取主机地址，如： http://localhost:8083      
    var localhostPaht=curWwwPath.substring(0,pos);      
    //获取带"/"的项目名，如：/uimcardprj      
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);      
    /* var url ="http://localhost:8081/common"; */
	var url = localhostPaht+projectName; 
	//var url = $("#commonUrl").val(); 
	 
	$(function(){
		$("#imMessageCenterBox,#pleft").niceScroll({
				railpadding: { top:0, right: 1, left: 0, bottom:0 },
				cursorborder:"",
				cursoropacitymin: 0.2,
				cursoropacitymax: 0.2,
				cursorcolor:"#000000",
				zindex: "99",
				cursorwidth: "5px",
			});
		
		
		
	});
		//var url ="http://192.168.0.2:8081/common";
		var aMessage = $('#chat_base_info').val();
		var messageDate = JSON.parse(aMessage);
		var listInfo = messageDate[0];
		var busCode = listInfo.busCode;//业务代码
		// huangzg 2017-01-10 add 添加了consultantId
		if(typeof(listInfo.consultantId)!="undefined"){ 
			var consultantId = listInfo.consultantId;//业务代码
		}
		/*用户信息*/
		var userInfo = listInfo.accountList[0].fromAccount;
		var tName = userInfo.chatId;
		var userNickName = userInfo.nickName;
		var sig = userInfo.sig;
		var userHeadImg = userInfo.headUrl;
		if(!userNickName){
		 	userNickName = tName;
		};
		if(!userHeadImg){
		  	userHeadImg = "../resources/img/doctor.jpg";
		};
		/* $(".tname").find("span").text(userNickName); */
		/*收信人信息*/
		var recveInfo = listInfo.accountList[1].toAccount;
		var selToID = recveInfo.chatId;
		var recveImg = recveInfo.headUrl;
		var recveNickName = recveInfo.nickName;
		
		if(!recveNickName){
			recveNickName = selToID;
		};
		$('.tname').append('<span style="color: #919499;">'+ '和  ' +recveNickName + ' 的对话' + '</span>');
		if(!recveImg){
			recveImg = "../resources/img/2017.jpg";
		};
		var audioIcon = "../resources/img/audio.png";
		/*IM信息*/
		var sdkAppID = listInfo.sdkAppid;
	    var accountType = listInfo.accountType;
		/*确定使用哪个模板*/
		var color = listInfo.color;
		if(!color){
		   color = "blue";
		}
		var colorLink = '<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/css/'+color+'.css"/>';
		document.write(colorLink);
		/*确定是单聊还是多聊*/
		if(listInfo.accountList.length == 1){
			getFriendList(tName);
		}else{
		   	var allWidth = $("#conter").width();
		   	var leftWidth = $("#pleft").outerWidth();
		   	$("#pleft").remove();
		   	$("#conter").width(allWidth - leftWidth);
		   	
		   	setTimeout(function(){
    			getHistory(true);
    		},600);
		};
		
	</script>
	 <script type="text/javascript">
                  //当前用户身份
            var loginInfo = {
                    'sdkAppID': sdkAppID, //用户所属应用id,必填
                    'appIDAt3rd': sdkAppID, //用户所属应用id，必填
                    'accountType': accountType, //用户所属应用帐号类型，必填
                    'identifier': null, //当前用户ID,必须是否字符串类型，必填
                    'identifierNick': null, //当前用户昵称，选填
                    'userSig': null, //当前用户身份凭证，必须是字符串类型，必填
            };      
            var selType = webim.SESSION_TYPE.C2C;//当前聊天类型
            var selSess = null;//当前聊天会话对象
            var groupSystemNotifys={};
            
            //监听连接状态回调变化事件
            var onConnNotify=function(resp){
                switch(resp.ErrorCode){
                    case webim.CONNECTION_STATUS.ON:
                        webim.Log.warn('连接状态正常...');
                        break;
                    case webim.CONNECTION_STATUS.OFF:
                        webim.Log.warn('连接已断开，无法收到新消息，请检查下你的网络是否正常');
                        break;
                    default:
                        webim.Log.error('未知连接状态,status='+resp.ErrorCode);
                        break;
                }
            };
            
            //IE9(含)以下浏览器用到的jsonp回调函数
            function jsonpCallback(rspData) {
                webim.setJsonpLastRspData(rspData);
            }
            
            //监听事件
            var listeners = {
                "onConnNotify": onConnNotify,
                "jsonpCallback": jsonpCallback,//IE9(含)以下浏览器用到的jsonp回调函数
                "onMsgNotify": onMsgNotify,//监听新消息(私聊，群聊，群提示消息)事件
                "onGroupInfoChangeNotify": onGroupInfoChangeNotify,//监听群资料变化事件
                "groupSystemNotifys": groupSystemNotifys//监听（多终端同步）群系统消息事件
            };
 
                        
           var isLogOn=false;//是否开启控制台打印日志
        //var isLogOn=false;
                        
            //初始化时，其他对象，选填
            var options={};
            
            if(tName && sig){
            	independentModeLogin1(tName,sig);
            }else{
            	alert("传入的参数不完全,请联系管理员");
            };
            
            setTimeout(function(){
            	document.getElementById('imMessageCenterBox').scrollTop=4000;//通过scrollTop设置滚动到100位置
    		},900);
            
      </script>
</body>
</html>