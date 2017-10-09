<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
	<script src="${pageContext.request.contextPath}/resources/scripts/jquery.min.js"></script>
	<script type="text/javascript">
		$(function (){
			
			var data = $('#data').val();
			var send_im_data = $('#send-im-data').val();
			var receiver_im_data = $('#receiver-im-data').val();

			console.log(data);
			console.log(send_im_data);
			console.log(receiver_im_data);
		});
	
	</script>
	
</head>
<body>
	<input type="hidden" id="data" value="${data}" >
	<input type="hidden" id="send-im-data" value="${sendImData}" >
	<input type="hidden" id="receiver-im-data" value="${receiverImData}" >
	<h1>Test</h1>
</body>
</html>