//共有的js位置

	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
	var checkin = $('#dpd1').fdatepicker({
		onRender: function (date) {
			return date.valueOf() > now.valueOf() ? 'disabled' : '';
		}
	}).on('changeDate', function (ev) {
		if (ev.date.valueOf() > checkout.date.valueOf()) {
			var newDate = new Date(ev.date)
			newDate.setDate(newDate.getDate() + 1);
			checkout.update(newDate);
		}
		checkin.hide();
		$('#dpd2')[0].focus();
	}).data('datepicker');
	var checkout = $('#dpd2').fdatepicker({
		onRender: function (date) {
			return date.valueOf() >= checkin.date.valueOf() ? 'disabled' : '';
		}
	}).on('changeDate', function (ev) {
		checkout.hide();
	}).data('datepicker');
//图与数据表的切换
function tabSelect(type){
	if(type == 0){
		$("#control").show(300);
		$("#box_show").hide(300);
		$(".control_li").css("background", "url(../images/1.png)");
		$(".box_li").css("background", "#DCF0FB");
		$(".calender_btn").show();
		$("#calendar").css("width","598px");
	}else if(type == 1){
		$("#control").hide(300);
		$("#box_show").show(300);
		$(".box_li").css("background", "url(../images/1.png)");
		$(".control_li").css("background", "#DCF0FB");
		$(".calender_btn").hide();
		$("#calendar").css("width","382px");
	}
}
//选择7天，30天时候日历隐藏与显示
 $(document).ready(function(){ 
        $('#calendar_choose').change(function(){ 
            if($(this).children('option:selected').val()==1) {
                $(".calendar_one").show();
                 $(".choose_week").show();
                 $(".choose_month").show();
            }
            else if ($(this).children('option:selected').val()==3||$(this).children('option:selected').val()==4){
                 $(".calendar_one").hide();
                 $(".choose_week").hide();
                 $(".choose_month").hide();

               }
            else if ($(this).children('option:selected').val()==2) {
               $(".calendar_one").hide();
                $(".choose_week").show();
                 $(".choose_month").show();
               }
        }) 
    }) 

 $(function(){
        $("#table1 tr").click(function(){
            $(this).addClass("tr_bg").siblings().removeClass("tr_bg");
        });
    });

//更新x轴
//按天数查看
  $(".choose_date").on("click", function(){
            var chart = $('#container').highcharts();
            chart.xAxis[0].update({
              min:5,
              max:200,
              gridLineWidth: 2,
              tickInterval:5,
              pointPadding:1,
              // pointStart: Date.UTC(2010, 0, 1),
              tickPixelInterval:2,
              minorGridLineColor:'rgb(241,137,168)',
              title: {
                  text: '天(day123)',
                  style: {
                      color: '#333',
                      fontWeight: 'bold',
                      fontSize: '15px',
                      fontFamily: 'Trebuchet MS, Verdana, sans-serif'
                  }
              },
              labels: {
                step: 2,//显示多少条数据
                formatter: function() {
                     return Highcharts.dateFormat('%Y-%m-%d', this.value)
                }
            }
            })
        });
  //按周查看
     $(".choose_week").on("click", function(){
            var chart = $('#container').highcharts();
            chart.xAxis[0].update({
              min:0,
              max:280,
              gridLineWidth: 1,
              tickInterval:14,
              pointPadding:1,
              tickPixelInterval:20,
              minorGridLineColor:'rgb(241,137,168)',
              title: {
                  text: '孕周(week)',
                  style: {
                      color: '#333',
                      fontWeight: 'bold',
                      fontSize: '15px',
                      fontFamily: 'Trebuchet MS, Verdana, sans-serif'
                  }
              },
              labels: {
                step: 1,
                formatter: function() {
                    return this.value/7
                }
            }
            })
        });
     //按月查看
     $(".choose_month").on("click", function(){
            var chart = $('#container').highcharts();
            chart.xAxis[0].update({
              min:1,
              max:12,
              gridLineWidth: 1,
              tickInterval:1,
              pointPadding:1,
              tickPixelInterval:10,
              minorGridLineColor:'rgb(241,137,168)',
              title: {
                  text: '月(month)',
                  style: {
                      color: '#333',
                      fontWeight: 'bold',
                      fontSize: '15px',
                      fontFamily: 'Trebuchet MS, Verdana, sans-serif'
                  }
              },
              labels: {
                step: 1,
                formatter: function() {
                    return this.value
                }
            }
            })
        });