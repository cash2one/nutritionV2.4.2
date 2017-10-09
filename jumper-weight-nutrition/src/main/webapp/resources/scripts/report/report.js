(function(window, $){
	var path = $("#path").val();
	
	$(".ont-table2,.ont-table3").hide();
    $(".ont1").click(function(){
        $(".ont-table1").show();
        $(".ont-table2").hide();
        $(".ont-table3").hide();
        init(path, 1);
    });
    $(".ont2").click(function(){
        $(".ont-table2").show();
        $(".ont-table1").hide();
        $(".ont-table3").hide();
        init(path, 2);
    });
    $(".ont3").click(function(){
        $(".ont-table3").show();
        $(".ont-table2").hide();
        $(".ont-table1").hide();
        init(path, 3);
    });


    $(".titleBox-btn li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    $(".caozuo .look_btn").click(function(){
        $(".win_1").fadeTo(120, 1);
    });
    $(".win_1 .table_list img").click(function(){
        $(".win_1").fadeTo(120, 0);
    });
	
})(window, jQuery);


function init(path, type){
	$.ajax({
		url : 'page.php',
		processData : false,
		data : xmlDocument,
		success : handleResponse
	});
}

