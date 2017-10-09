/**
 * 体重曲线图js---viewUserInformation.html
 */
//获取数据
var safeWeightList = [], minSafeList = [], maxSafeList = [], avgSafeList = [], userWeightList = [];
var expectedDate = new Date();

$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	var type = $('#container-weight').attr("chartType");//0:30天，1：整个孕期
	var data = {userId : userId, hospitalId : hospitalId, type : type};
	initWeightChart(data);
	//切换近30天和全部时期
	$(".sevenWeightData, .allWeightData").click(function() {
		$(this).addClass("active");
		if($(this).hasClass("sevenWeightData")) {//this.className
			$(".allWeightData").removeClass("active");
			data.type = 0;
		} else {
			$(".sevenWeightData").removeClass("active");
			data.type = 1;
		}
		initWeightChart(data);
	});
});

//加载曲线图数据
function initWeightChart(data) {
	var xMin = 0, xMax = 301, tickInterval = 7;
	//图表数据获取
	$.post(basePath + "/physical/getWeightChartData", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time: 600});
			return;
		}
		safeWeightList = ret.data.safeWeightList;
		userWeightList = ret.data.userWeightList;
		expectedDate = stringToDate(ret.data.expectedDate);
		//初始化上限，下限，推荐值
		initSafeList();
		if (data.type == 0) {//近7天
			xMin = ret.data.sevenPweek[0];
			xMax = ret.data.sevenPweek[1];
			tickInterval = 2;
		}
		var weightIncrease = ret.data.weightIncrease.toFixed(1);
		$(".weightIncrease").text(weightIncrease + "kg");
		var suggestIncrease = ret.data.suggestIncrease;
		$(".suggestIncrease").text(suggestIncrease[0].toFixed(1) + "~" + suggestIncrease[1].toFixed(1) + "kg");
	});
	
	//图表数据展示
    $('#container-weight').highcharts({
        title: {
            text: '体重数据管理'
        },
        xAxis: {
            min:xMin,
            max:xMax,
            tickInterval:tickInterval,
            title: {
                text: '孕周(week)'
            },
            labels: {
                formatter: function() {
                	if (data.type == 0) {
                		return "怀孕" + parseInt(this.value / 7) + "周" + this.value % 7 + "天";
					}
                    return this.value/7;
                }
            }
        },
        yAxis: {
        	/*min:initweight-5,
            max:initweight+30,*/
            title: {
                text: '体重(kg)'
            },
            labels: {
                formatter: function() {
                    return this.value 
                }
            }
        },
        tooltip: {
        	shared:true,
            crosshairs:true,
            formatter:function() {
            	var preDate = new Date(new Date(expectedDate) - (280 - this.x) * 3600 * 24 * 1000);
        		preDate = preDate.getFullYear() + '年' + (preDate.getMonth()+1) + '月' + preDate.getDate() + '日';
                var week = parseInt(this.x / 7);
                var day = this.x % 7;
                var s = "<b>" + week + "周" + day + "天(" + preDate + "):</b><br>";
                if (this.x <= 280) {
        			var safe = safeWeightList[this.x];
                    var avg = avgSafeList[this.x];
                    s += '<br><b>上限：</b>' + safe[2].toFixed(1) + 'kg' + '，<b>下限：</b>' + safe[1].toFixed(1) + 'kg';
             		s += '<br><b>推荐：</b>' + avg[1].toFixed(1) + 'kg';
				}
                if (this.series != null && this.series.name == "目前体重值") {//代表体重值的点
                	s += '<br><b>' + this.series.name + '：</b>' + this.y.toFixed(1) + 'kg';
                }
                return s;
            }
        },
        legend: {
        	layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            itemStyle: {
                font: '9pt Trebuchet MS, Verdana, sans-serif',
                color: 'black'
            }
        },
        plotOptions: {
            spline: {
                marker: {
                	radius: 0,
                    lineColor: '#E98F4B',
                    lineWidth: 1
                },
                enableMouseTracking: true//鼠标覆盖显示值
            },
            series: {
                cursor: 'pointer',
                marker: {
                    lineWidth: 1,
                    enabled: true
                }
            }
        },
        series: [{
        	lineWidth: 1,
        	type: 'spline',
            name: '上限',
            data: maxSafeList,
            states: {
                hover: {
                    lineWidth: 0
                }
            }
        },{
        	lineWidth: 1,
        	type: 'spline', 
            name: '下限',
            width:1,
            data: minSafeList,
            states: {
                hover: {
                    lineWidth: 0
                }
            }
            
        },{
        	lineWidth: 1,
        	type: 'spline',
            name: '推荐',
            data: avgSafeList,
            states: {
                hover: {
                    lineWidth: 0
                }
            }
        },{
        	type: 'scatter',
            name: '目前体重值',
            data: userWeightList,
            marker: {
                radius: 5
            }
        }]
    });
}


//初始化上限，下限，推荐值
function initSafeList() {
	var tempMin = [], tempMax = [], tempAvg = [];
	for (var i = 0; i < safeWeightList.length; i++) {
		var obj = safeWeightList[i];
		tempMin.push([obj[0], obj[1]]);
		tempMax.push([obj[0], obj[2]]);
		var avg = (obj[1] + obj[2]) / 2;
		tempAvg.push([obj[0], avg]);
	}
	minSafeList = tempMin;
	maxSafeList = tempMax;
	avgSafeList = tempAvg;
}

