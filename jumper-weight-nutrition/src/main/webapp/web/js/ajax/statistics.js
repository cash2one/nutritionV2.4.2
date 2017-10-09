/**
 * 统计页面
 */

//折线图所需数据
var outpNum = [], firstOutpNum = [], repeatOutpNum = [], addUpOutpNum = [], addUpOutpTimes = [];
var allChartData = [outpNum, firstOutpNum, repeatOutpNum, addUpOutpNum, addUpOutpTimes];
var statisticsType = ["门诊人数", "初诊人数", "复诊人数", "累计门诊人数", "累计门诊人次"];
//默认近七天
var startDate = getDateByDaysLate(new Date(), -6);
var endDate = formatDate(new Date());
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	//刷新页面的时间
	var refreshTime = formatDatetime(new Date());
	$(".refreshTime").text(refreshTime);
	
	//加载今日和本月统计数据
	$.post(basePath + "/statistics/getOutpStatistics", {hospitalId : hospitalId}, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		$(".dayOutpNum").text(ret.data.dayOutpNum);
		$(".dayFirstOutpNum").text(ret.data.dayFirstOutpNum);
		$(".dayRepeatNum").text(ret.data.dayRepeatNum);
		$(".monthOutpNum").text(ret.data.monthOutpNum);
		$(".monthFirstOutpNum").text(ret.data.monthFirstOutpNum);
		$(".monthRepeatNum").text(ret.data.monthRepeatNum);
	});
	
	//默认近7天
	var data = {hospitalId : hospitalId, startDate : startDate, endDate : endDate};
	initChartData(data);
	initChart($("#statisticsType").val());
	
	//切换时间范围时，加载对应的天数
	$("#selectTian").change(function() {
		if ($(this).val() == "0") {
			startDate = getDateByDaysLate(new Date(), -6);
			endDate = formatDate(new Date());
		} else if ($(this).val() == "1") {
			startDate = getDateByDaysLate(new Date(), -30);
			endDate = formatDate(new Date());
		} else {
			$("#startDate").val(startDate);
			$("#endDate").val(endDate);
		}
		data.startDate = startDate;
		data.endDate = endDate;
		initChartData(data);
		initChart($("#statisticsType").val());
	});
	
	//单击自定义时间范围确认按钮时
	$(".checkSearchBtn").click(function() {
		startDate = $("#startDate").val();
		endDate = $("#endDate").val();
		data.startDate = startDate;
		data.endDate = endDate;
		initChartData(data);
		initChart($("#statisticsType").val());
	});
	
	//图表数据类型变化时
	$("#statisticsType").change(function() {
		initChart($(this).val());
	});
	
});

//初始化图表数据
function initChartData(data) {
	//折线图所需数据
	$.post(basePath + "/statistics/getOutpChartSta", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time : 1000});
			return;
		}
		//初始化清空数组
		outpNum.splice(0, outpNum.length); 
		firstOutpNum.splice(0, firstOutpNum.length); 
		repeatOutpNum.splice(0, repeatOutpNum.length);
		addUpOutpNum .splice(0, addUpOutpNum.length);
		addUpOutpTimes.splice(0, addUpOutpTimes.length);
		for (var i = 0; i < ret.data.length; i++) {
			var obj = ret.data[i];
			var date = stringToDate(obj.date);
			outpNum.push([Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()), obj.outpNum]);
			firstOutpNum.push([Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()), obj.firstOutpNum]);
			repeatOutpNum.push([Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()), obj.repeatOutpNum]);
			addUpOutpNum.push([Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()), obj.addUpOutpNum]);
			addUpOutpTimes.push([Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()), obj.addUpOutpTimes]);
		}
	});
}

//折线图初始化
function initChart(type) {
	var series = statisticsType[type];
	var chartData = allChartData[type];
	
	//折线图
	$('#container_8').highcharts({
		plotOptions: {  
	        series: {  
	            animation: false  
	        }  
	    },
        title: {
            text: ' '
        },
        xAxis: {
        	type: 'datetime',//时间轴
        	lineWidth: 2,//x轴线宽
        	gridLineWidth: 1,//网格线线条宽度，当设置为 0 时则不显示网格线
        	gridLineColor:'#ccc',//网格竖线颜色
        	title: {
                text: '日期',
                style: {
                    color: '#333',
                    fontWeight: 'bold',
                    fontSize: '13px',
                    fontFamily: 'Trebuchet MS, Verdana, sans-serif'
                }
            },
	        labels: {
	            formatter: function () {
	                return Highcharts.dateFormat('%m-%d', this.value);
	            }  
	        },
	        minPadding:0,//从原点开始
      　　		startOnTick:false
        },
        yAxis:{
        	lineWidth: 2,
        	title: {
                text: '人数',
                style: {
                    color: '#333',
                    fontWeight: 'bold',
                    fontSize: '12px',
                    fontFamily: 'Trebuchet MS, Verdana, sans-serif'
                }
            },
            minPadding:0,//从原点开始
  　　		startOnTick:false
        },
        tooltip: {
            /*shared: true,
            useHTML: true,
            headerFormat: '<div style="font-weight: bold;text-align: center;">{series.name}</div><span>{point.key}</span>:<span style="font-weight: bold">{point.y:f}</span>',
            pointFormat: ' ',
            footerFormat: ' ',
            valueDecimals: 2*/
        	formatter:function() {
	  	      	return '<strong>' + this.series.name + '：' + this.y + '</strong> <b/>' +
	  	      		'<br/>' + '<strong>日期：' + Highcharts.dateFormat('%Y-%m-%d', this.x) + '<strong/>';
	  	    }
        },
        series: [{
            name: series,
            data: chartData
        }]
    });
}