/**
 * Created by Administrator on 2017/6/22 0022.
 */
//摄入量分析
$(function () {
    $('#container_1').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: ' '
        },

        xAxis: {
            type: 'category',
            labels: {
                rotation: 0,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '摄入量（g）'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '摄入量: <b>{point.y:.1f} g</b>'
        },
        series: [{
            name: '',
            data: [
                ['谷薯类', 160],
                ['豆制品类', 100],
                ['蔬菜类', 60],
                ['水果类', 123],
                ['肉蛋类', 223],
                ['乳制品类', 122],
                ['油脂类', 120],
                ['坚果类', 100]

            ],
            dataLabels: {
                enabled: true,
                color: '#666',
                align: 'left',
                format: '{point.y:.1f}', // one decimal
                y: 2, // 10 pixels down from the top
                style: {
                    fontSize: '12px',
                    fontweight: 'normal',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }]
    });
});
//三大能量物质来源分析
    //左边的
    $(function () {
        $('#container_2').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: 0,
                plotShadow: false
            },
            title: {
                text: ' '
            },
            tooltip: {
                headerFormat: '{series.name}',
                pointFormat: '{point.name}<b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                	
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        distance: -50,
                        format: '<b>{point.name}:{point.percentage:.1f} %',
                        style: {
                        	fontWeight:'normal',
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
                            shadow: false,
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: ' ',
                data: [
                    ['蛋白质'+'<br>'+120+'Kacl',20.0],
                    ['碳水化合物'+'<br>'+410+'Kacl',8.0],
                    ['脂肪'+'<br>'+410+'Kacl',72.0]
                    /*{
                        name: 'Chrome',
                        y: 12.8,
                        sliced: true,
                        selected: true
                    }*/
                ]
            }]
        });
    });
    //右边的
    $(function () {
        $('#container_3').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '蛋白质来源分析'
            },
            tooltip: {
                headerFormat: '{series.name}<br>',
                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                	shadow: false,
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        distance: -30,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                        	fontWeight:'normal',
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: ' ',
                data: [
                    ['非优质蛋白'+'<br>'+120+'Kacl',28.0],
                    ['优质蛋白'+'<br>'+410+'Kacl',72.0]
                ]
            }]
        });
    });
//餐次供能比
$(function () {
    $('#container_4').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: ' '
        },
        tooltip: {
            headerFormat: '{series.name}<br>',
            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    /*distance: -50,*/
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: ' ',
            data: [
                ['早餐'+'<br>'+120+'Kacl',5],
                ['早加'+'<br>'+410+'Kacl',25],
                ['晚加'+'<br>'+410+'Kacl',6],
                ['午餐'+'<br>'+410+'Kacl',22],
                ['午加'+'<br>'+410+'Kacl',12],
                ['晚餐'+'<br>'+410+'Kacl',30]
            ]
        }]
    });
});
//个体营养检测分析报告  各类运动时长占比分析图表
$(function () {
	 $('#container_5').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: '各类运动时长占比分析'
	        },
	        xAxis: {
	        	categories: ['2017-05-01', '2017-05-02', '2017-05-03', '2017-05-04','2017-05-10'],
	            title: {
	                text: null
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '各类运动时长',
	                align: 'high'
	            },
	            labels: {
	                overflow: 'justify'
	            }
	        },
	        tooltip: {
	            valueSuffix: 'min'
	        },
	        plotOptions: {
	            bar: {
	                dataLabels: {
	                    enabled: true,
	                    allowOverlap: true,
	                    format: '<b>{series.name}</b>:{y}min:({point.percentage:.0f}%)'
	                },
	            }
	        },
	        legend: {
	            align: 'right',
	            x: 0,
	            verticalAlign: 'top',
	            y: 25,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
	            borderColor: '#CCC',
	            shadow: true
	        },
	        credits: {
	            enabled: false
	        },
	        series: [{
	            name: '散步',
	            data: [107, 31, 635, 203, 2],
	            color:'#ccc'
	        }, {
	            name: '抬水',
	            data: [133, 156, 947, 408, 6]
	        }, {
	            name: '游泳',
	            data: [973, 914, 404, 732, 34]
	        }, {
	            name: '看书',
	            data: [97, 14, 454, 132, 340]
	        }]
	    });
});
//各类运动消耗能量占比分析
$(function () {
	 $('#container_6').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: '各类运能量消耗占比分析'
	        },
	        xAxis: {
	        	categories: ['2017-05-01', '2017-05-02', '2017-05-03', '2017-05-04','2017-05-10'],
	            title: {
	                text: null
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '各类运能量消耗',
	                align: 'high'
	            },
	            labels: {
	            	 formatter: function() {
	                        return this.value / 100 +'百';
	                    }
	            }
	        },
	        tooltip: {
	            valueSuffix: 'kacl'
	        },
	        plotOptions: {
	            bar: {
	                dataLabels: {
	                    enabled: true,
	                    allowOverlap: true,
	                    format: '<b>{series.name}</b>:{y}kacl'
	                },
	            }
	        },
	        legend: {
	            align: 'right',
	            x: 0,
	            verticalAlign: 'top',
	            y: 25,
	            floating: true,
	            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
	            borderColor: '#CCC',
	            shadow: true
	        },
	        credits: {
	            enabled: false
	        },
	        series: [{
	            name: '散步',
	            data: [107, 31, 635, 203, 2]
	        }, {
	            name: '抬水',
	            data: [133, 156, 947, 408, 6]
	        }, {
	            name: '游泳',
	            data: [973, 914, 404, 732, 34]
	        }, {
	            name: '看书',
	            data: [97, 14, 454, 132, 340]
	        }]
	    });
});
//体重增长曲线；
$(function(){
	$(function () {
	    $('#container-weight').highcharts({
	    	xAxis: {
	    		title:{
	    			text:'孕周'
	    		}
	        },
	        yAxis: {
	        	title:{
	    			text:'体重'
	    		}
	        },
	        title: {
	            text: '体重增长曲线'
	        },
	        tooltip: {
	            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>',
	            shared: true
	        },
	        series: [{
	            type: 'line',
	            name: '上限',
	            data: [[0, 2], [12,12],[40,70]],
	            marker: {
	                enabled: false
	            },
	            states: {
	                hover: {
	                    lineWidth: 0
	                }
	            },
	            enableMouseTracking: false
	        },{
	            type: 'line', 
	            name: '下限',
	            data: [[0, 2], [12, 10],[40,55]],
	            marker: {
	                enabled: false
	            },
	            states: {
	                hover: {
	                    lineWidth: 0
	                }
	            },
	            enableMouseTracking: false
	         },{
		            type: 'line', 
		            name: '推荐',
		            data: [[0, 2], [12, 11],[40,60]],
		            marker: {
		                enabled: false
		            },
		            states: {
		                hover: {
		                    lineWidth: 0
		                }
		            },
		            enableMouseTracking: false
		        },{
	            type: 'scatter',
	            name: '当前体重值',
	            data: [[25,40]],
	            marker: {
	                radius: 5
	            }
	        }]
	    });
	});
});
