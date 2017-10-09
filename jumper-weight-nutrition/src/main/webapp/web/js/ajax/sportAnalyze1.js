/**
 * 运动分析
 */
var totalDataTime = new Array();
var totalDataCalorie = new Array();
var dateList = new Array();//调查日期数组
var dailyList = "";
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
					for(var i=0;i<dailyList.length;i++){
						dateList.push(dailyList[i].sportDate);
					}
					for(var i=0;i<sportList.length;i++){
						var dataTime = new Array();
						var dataCalorie = new Array();
						for(var j=0;j<dailyList.length;j++){
							dataTime[j] = null;
							dataCalorie[j] = null;
							var recordList = dailyList[j].recordList;
							for(var k=0;k<recordList.length;k++){
								if(recordList[k].sportName==sportList[i]){
									dataTime[j] = recordList[k].timeLength;
									dataCalorie[j] = recordList[k].calories;
								}
							}
						}
						//运动时间占比
						var seriesSubTime = {
								name:sportList[i],
								data:dataTime
						};
						totalDataTime.push(seriesSubTime);
						//运动热量占比
						var seriesSubCalorie = {
								name:sportList[i],
								data:dataCalorie
						};
						totalDataCalorie.push(seriesSubCalorie);
					}
				}
			}
		});
	}
}
//个体营养检测分析报告  图表
$(function () {
	setTimeout(function(){
		getSportAnalyze();
		if(dateList.length==1){
			$("#container_5,#container_6").css("height","400px");
		}else if(dateList.length==2||dateList.length==3){
			$("#container_5,#container_6").css("height","550px");
		}else{
			$("#container_5,#container_6").css("height","860px");
		}
		//各类运动时长占比分析
		$('#container_5').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: '各类运动时长占比分析'
	        },
	        xAxis: {
	        	categories: dateList,
	            title: {
	                text: null
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '各类运动时长(分钟)',
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
//	            	pointWidth:20,
	            	maxPointWidth:30,
//	            	pointPadding:0.3,
	                dataLabels: {
	                    enabled: true,
	                    allowOverlap: true,
	                    /*format: '<b>{series.name}</b>:{y}min'*/
	                    formatter: function () {  
	                    	var percent = 0;
	                    	for(var i=0;i<dailyList.length;i++){
	                    		var date = dailyList[i].sportDate;
	                    		if(date == this.x){
	                    			var recordList = dailyList[i].recordList;
		                    		for(var k=0;k<recordList.length;k++){
		    							if(recordList[k].sportName==this.series.name){
		    								percent = recordList[k].timeLengthPer;
		    							}
		    						}
	                    		}
	                    	}
	    	                return '<b>'+this.series.name+'</b>:'+this.y+'min('+percent+"%)";  
	    	            }
	                }
	            }
	        },
	        credits: {
	            enabled: false
	        },
	        series: totalDataTime
	    });
		//各类运动消耗能量占比分析
		$('#container_6').highcharts({
	        chart: {
	            type: 'bar'
	        },
	        title: {
	            text: '各类运能量消耗占比分析'
	        },
	        xAxis: {
	        	categories: dateList,
	            title: {
	                text: null
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '各类运能量消耗(千卡)',
	                align: 'high'
	            },
	            labels: {
	            	 formatter: function() {
	                        return this.value;
	                    }
	            }
	        },
	        tooltip: {
	            valueSuffix: 'kcal'
	        },
	        plotOptions: {
	            bar: {
	            	//pointWidth:20,
	            	maxPointWidth:30,
	                dataLabels: {
	                    enabled: true,
	                    allowOverlap: true,
	                    /*format: '<b>{series.name}</b>:{y}kcal'*/
	                    formatter: function () {  
	                    	var percent = 0;
	                    	for(var i=0;i<dailyList.length;i++){
	                    		var date = dailyList[i].sportDate;
	                    		if(date == this.x){
	                    			var recordList = dailyList[i].recordList;
		                    		for(var k=0;k<recordList.length;k++){
		    							if(recordList[k].sportName==this.series.name){
		    								percent = recordList[k].caloriesPer;
		    							}
		    						}
	                    		}
	                    	}
	    	                return '<b>'+this.series.name+'</b>:'+this.y+'kcal('+percent+"%)";  
	    	            }
	                }
	            }
	        },
	        credits: {
	            enabled: false
	        },
	        series: totalDataCalorie
	    });
	},200);
	 
});
