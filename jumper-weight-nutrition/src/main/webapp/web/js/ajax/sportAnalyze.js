/**
 * 运动分析
 */
var totalDataTime = new Array();
var totalDataCalorie = new Array();
var dateList = new Array();//调查日期数组
var dailyList = new Array();
var flag = 1;
function getSportAnalyze(){
	if(spoList == "[]" ||spoList == "" || spoList == null){
		//隐藏分析图表
		$(".analyze_sport").css("display","none");
	}else{
		$.ajax({
			url:basePath+"/sportSurvey/getSportSurAnalyze",
			type:"POST",
			dataType:"json",
			async:false,
			data:{voList:JSON.stringify(spoList)},
			success:function(json){
				if(json.msg==1){
					var sportList = json.data.sportList;
					dailyList = json.data.dailyList;
					var colors = ['#82DAFB', '#FAC87D', '#90ee7e', '#91B8E6', '#aaeeee', '#AAB6FF', '#eeaaee',
					                '#B2E67E', '#FF8FA2', '#7AB1CC', '#EE92DA','#B8CCCC','#DFF089','#FA8E78','#71E6D8'];
					var colorJson = new Array();
					for(var x=0;x<sportList.length;x++){
						var json = {
							name:sportList[x],
							color:colors[x>14?x-15:x]
						};
						colorJson.push(json);
					}
					for(var i=0;i<dailyList.length;i++){
						var date = new Array();
						date.push(dailyList[i].sportDate);
						var recordList = dailyList[i].recordList;
						var dataTimeArray = new Array();
						var dataCalorieArray = new Array();
						for(var j=0;j<recordList.length;j++){
							var dataTime = new Array();
							dataTime.push(recordList[j].timeLength);
							var dataCalorie = new Array();
							dataCalorie.push(recordList[j].calories);
							for(var k=0;k<colorJson.length;k++){
								if(colorJson[k].name==recordList[j].sportName){
									var dataTimeSeries = {
											name:recordList[j].sportName,
											data:dataTime,
											color:colorJson[k].color
									};
									var dataCalorieSeries = {
											name:recordList[j].sportName,
											data:dataCalorie,
											color:colorJson[k].color
									}
								}
							}
							dataTimeArray.push(dataTimeSeries);
							dataCalorieArray.push(dataCalorieSeries);
						}
						//动态调整图表高度
						var length = recordList.length;
						$("#container_5").append('<div id="container_5'+i+'" style="_width:780px;"></div>');
						setHeight("#container_5"+i,length);
						highCharts("#container_5"+i,date,dataTimeArray,recordList,"各类运动时长(分钟)","min");
						$("#container_6").append('<div id="container_6'+i+'" style="_width:780px;"></div>');
						setHeight("#container_6"+i,length);
						highCharts("#container_6"+i,date,dataCalorieArray,recordList,"各类运能量消耗(千卡)","kcal");
					}
				}
			}
		});
	}
}
function setHeight(id,length){
	if(length >= 1 && length <= 2){
		$(id).css("height","140px");
	}else if(length >= 3 && length <= 4){
		$(id).css("height","190px");
	}else if(length >= 5 && length <= 6){
		$(id).css("height","250px");
	}else if(length >= 7 && length <= 8){
		$(id).css("height","310px");
	}else if(length >= 9 && length <= 10){
		$(id).css("height","370px");
	}
}

function highCharts(id,date,dataTimeArray,recordList,yAxisTitle,valueSuffix){
	//各类运动时长占比分析
	$(id).highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: ' '
        },
        xAxis: {
        	categories: date,
            title: {
                text: null,
                rotation: -90
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: yAxisTitle,
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: valueSuffix
        },
        plotOptions: {
            bar: {
            	pointWidth:16,
            	maxPointWidth:18,
            	pointPadding:0.5,
                dataLabels: {
                	style:{
                		color:'#293033',
                		fontFamily:"Microsoft yaHei"
                	},
                    enabled: true,
                    allowOverlap: true,
                    /*format: '<b>{series.name}</b>:{y}min'*/
                    formatter: function () {  
                    	var percent = 0;
                    	for(var k=0;k<recordList.length;k++){
							if(recordList[k].sportName==this.series.name){
								percent = recordList[k].timeLengthPer;
							}
						}
    	                return '<b>'+this.series.name+'</b>:'+this.y+''+valueSuffix+'('+percent+"%)";  
    	            }
                }
            }
        },
        legend: {
            enabled:false
        },
        credits: {
            enabled: false
        },
        series: dataTimeArray
    });
}
//个体营养检测分析报告  图表
$(function () {
	setTimeout(function(){
		getSportAnalyze();
	},200);
	 
});
