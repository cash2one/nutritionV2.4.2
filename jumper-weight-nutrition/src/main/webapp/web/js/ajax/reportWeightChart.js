/**
 * 报告体重曲线图js---reportView.html
 */
var reportId = GetQueryString("reportId");

//获取数据
var safeWeightList = [], minSafeList = [], maxSafeList = [], avgSafeList = [], userWeightList = [];
var expectedDate = new Date();

$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	//0:7天，1：整个孕期(默认)
	var data = {reportId : reportId};
	initWeightChart(data);
});

//加载曲线图数据
function initWeightChart(data) {
	var xMin = 0, xMax = 301, tickInterval = 35;
	//图表数据获取
	$.post(basePath + "/physical/getRepWeiChartData", data, function(ret) {
		if (ret.msg != 1) {
			layer.msg(ret.msgbox, {time: 600});
			return;
		}
		safeWeightList = ret.data.safeWeightList;
		userWeightList = ret.data.userWeightList;//取最后一个点
		expectedDate = stringToDate(ret.data.expectedDate);
		//初始化上限，下限，推荐值
		initSafeList();
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
                    return this.value/7;
                }
            }
        },
        yAxis: {
        	/*min:initweight-5,
            max:initweight+30,*/
            title: {
                text: "体重(kg)"
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
        			var avg = (safe[1] + safe[2]) / 2;
                    s += '<br><b>上限：</b>' + safe[2].toFixed(1) + 'kg' + '，<b>下限：</b>' + safe[1].toFixed(1) + 'kg';
             		s += '<br><b>推荐：</b>' + avg.toFixed(1) + 'kg';
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
        	type: 'spline',
            name: '上限',
            data: maxSafeList,
            states: {
                hover: {
                    lineWidth: 0
                }
            }
        },{
        	type: 'spline', 
            name: '下限',
            data: minSafeList,
            states: {
                hover: {
                    lineWidth: 0
                }
            }
            
        },{
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
	for (var i = 0; i < safeWeightList.length; i+=35) {
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

