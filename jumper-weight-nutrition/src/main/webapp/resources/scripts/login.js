$(function() {

	$(".loginBtn").click(function() {
		$.ajax({
			type: 'POST',
			url : 'base/login',
			context : $('#main').serialize(),
			success : function(data) {
				if (data.success){
					location.href = "base/main";
				}else {
					
					
					$("id").html("");
				}
			}
		});
	});
});