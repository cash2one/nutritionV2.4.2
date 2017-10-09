/**
 * 营养分析
 */
var outpatientId = GetQueryString("outpatientId");
var foodCatagoryAnalyzeData = new Array();
var mealsTypeIntakeAnalyzeData = new Array();
var threeSourceAnalyzeData = new Array();
var proteinAnalyzeData = new Array();
var flag = 1;
function getAnalyzeData(){
	//各类食物摄入量分析
	$.ajax({
		url:basePath+"/foodAnalyze/foodCatagoryAnalyze",
		type:"POST",
		dataType:"json",
		async:false,
		data:{outpatientId:outpatientId},
		success:function(json){
			if(json.msg==1){
				var data = json.data.analyzeList;
				var dateList = json.data.dateList;
				for (var i = 0; i < data.length; i++) {
					var array = new Array();
					array.push(data[i].catagoryName);
					array.push(data[i].foodWeight);
					foodCatagoryAnalyzeData.push(array);
				}
				var html = "";
				for(var j=0;j<dateList.length;j++){
					html += dateList[j]+"、";
				}
				$("#dateList").html(html.substring(0,html.length-1));
			}else if(json.msg == 10){
				flag = 0;
			}
		}
	});
	//餐次供能比分析 
	$.ajax({
		url:basePath+"/foodAnalyze/mealsTypeIntakeAnalyze",
		type:"POST",
		dataType:"json",
		async:false,
		data:{outpatientId:outpatientId},
		success:function(json){
			if(json.msg==1){
				var data = json.data;
				for (var i = 0; i < data.length; i++) {
					var array = new Array();
					array.push(data[i].mealsName+'：'+data[i].energyIntake+'kcal');
					array.push(data[i].accountPercent);
					mealsTypeIntakeAnalyzeData.push(array);
				}
			}
		}
	});
	
	//营养素摄入量、能量来源分析
	$.ajax({
		 url:basePath+"/foodAnalyze/threeSourceAnalyze",
		 type:"POST",
		 data:{outpatientId:outpatientId, hospitalId:hospitalId},
		 dataType:"json",
		 async:false,
		 success:function(json){
			 if(json.msg==1){
				 var data = json.data.voNutritionAnalyze;
				 var perData = json.data.voAccountPercent;
				 //营养素摄入量、能量来源分析
				 $("#energyIntake").text(data.energyIntake);
				 $("#energyRecommend").text(data.energyRecommend);
				 $("#energyPer").text(data.energyPer);
				 $("#energyStatus").text(CONST.evaluation[data.energyStatus]);
				 
				 $("#proteinIntake").text(data.proteinIntake);
				 $("#proteinRecommend").text(data.proteinRecommend[0]+"~"+data.proteinRecommend[1]);
				 $("#proteinPer").text(data.proteinPer[0]+"~"+data.proteinPer[1]);
				 $("#proteinStatus").text(CONST.evaluation[data.proteinStatus]);
				 
				 $("#fatIntake").text(data.fatIntake);
				 $("#fatRecommend").text(data.fatRecommend[0]+"~"+data.fatRecommend[1]);
				 $("#fatPer").text(data.fatPer[0]+"~"+data.fatPer[1]);
				 $("#fatStatus").text(CONST.evaluation[data.fatStatus]);
				 
				 $("#carbonIntake").text(data.carbonIntake);
				 $("#carbonRecommend").text(data.carbonRecommend[0]+"~"+data.carbonRecommend[1]);
				 $("#carbonPer").text(data.carbonPer[0]+"~"+data.carbonPer[1]);
				 $("#carbonStatus").text(CONST.evaluation[data.carbonStatus]);
				 
				 $("#caIntake").text(data.caIntake);
				 $("#caRecommend").text(data.caRecommend);
				 $("#caPer").text(data.caPer);
				 $("#caStatus").text(CONST.evaluation[data.caStatus]);
				 
				 $("#feIntake").text(data.feIntake);
				 $("#feRecommend").text(data.feRecommend);
				 $("#fePer").text(data.fePer);
				 $("#feStatus").text(CONST.evaluation[data.feStatus]);
				 
				 $("#znIntake").text(data.znIntake);
				 $("#znRecommend").text(data.znRecommend);
				 $("#znPer").text(data.znPer);
				 $("#znStatus").text(CONST.evaluation[data.znStatus]);
				 
				 $("#seIntake").text(data.seIntake);
				 $("#seRecommend").text(data.seRecommend);
				 $("#sePer").text(data.sePer);
				 $("#seStatus").text(CONST.evaluation[data.seStatus]);
				 
				 $("#cuIntake").text(data.cuIntake);
				 $("#cuRecommend").text(data.cuRecommend);
				 $("#cuPer").text(data.cuPer);
				 $("#cuStatus").text(CONST.evaluation[data.cuStatus]);
				 
				 $("#naIntake").text(data.naIntake);
				 $("#naRecommend").text(data.naRecommend);
				 $("#naPer").text(data.naPer);
				 $("#naStatus").text(CONST.evaluation[data.naStatus]);
				 
				 $("#iIntake").text(data.iIntake);
				 $("#iRecommend").text(data.iRecommend);
				 $("#iPer").text(data.iPer);
				 $("#iStatus").text(CONST.evaluation[data.iStatus]);
				 
				 $("#pIntake").text(data.pIntake);
				 $("#pRecommend").text(data.pRecommend);
				 $("#pPer").text(data.pPer);
				 $("#pStatus").text(CONST.evaluation[data.pStatus]);
				 
				 $("#kIntake").text(data.kIntake);
				 $("#kRecommend").text(data.kRecommend);
				 $("#kPer").text(data.kPer);
				 $("#kStatus").text(CONST.evaluation[data.kStatus]);
				 
				 $("#mgIntake").text(data.mgIntake);
				 $("#mgRecommend").text(data.mgRecommend);
				 $("#mgPer").text(data.mgPer);
				 $("#mgStatus").text(CONST.evaluation[data.mgStatus]);
				 
				 $("#mnIntake").text(data.mnIntake);
				 $("#mnRecommend").text(data.mnRecommend);
				 $("#mnPer").text(data.mnPer);
				 $("#mnStatus").text(CONST.evaluation[data.mnStatus]);
				 
				 $("#vaIntake").text(data.vaIntake);
				 $("#vaRecommend").text(data.vaRecommend);
				 $("#vaPer").text(data.vaPer);
				 $("#vaStatus").text(CONST.evaluation[data.vaStatus]);
				 
				 $("#vb6Intake").text(data.vb6Intake);
				 $("#vb6Recommend").text(data.vb6Recommend);
				 $("#vb6Per").text(data.vb6Per);
				 $("#vb6Status").text(CONST.evaluation[data.vb6Status]);
				 
				 $("#vcIntake").text(data.vcIntake);
				 $("#vcRecommend").text(data.vcRecommend);
				 $("#vcPer").text(data.vcPer);
				 $("#vcStatus").text(CONST.evaluation[data.vcStatus]);
				 
				 $("#vb1Intake").text(data.vb1Intake);
				 $("#vb1Recommend").text(data.vb1Recommend);
				 $("#vb1Per").text(data.vb1Per);
				 $("#vb1Status").text(CONST.evaluation[data.vb1Status]);
				 
				 $("#vb2Intake").text(data.vb2Intake);
				 $("#vb2Recommend").text(data.vb2Recommend);
				 $("#vb2Per").text(data.vb2Per);
				 $("#vb2Status").text(CONST.evaluation[data.vb2Status]);
				 
				 $("#veIntake").text(data.veIntake);
				 $("#veRecommend").text(data.veRecommend);
				 $("#vePer").text(data.vePer);
				 $("#veStatus").text(CONST.evaluation[data.veStatus]);
				 
				 //三大能量物质来源分析
				 var proteinData = new Array();
				 proteinData.push('蛋白质');//+'：<br>'+perData.proIntakeCal+'kcal'
				 proteinData.push(perData.proPercent);
				 threeSourceAnalyzeData.push(proteinData);
				 var fatData = new Array();
				 fatData.push('脂肪');//+'：<br>'+perData.fatIntakeCal+'kcal'
				 fatData.push(perData.fatPercent);
				 threeSourceAnalyzeData.push(fatData);
				 var carbonData = new Array();
				 carbonData.push('碳水化合物');//+'：<br>'+perData.carbonIntakeCal+'kcal'
				 carbonData.push(perData.carbonPercent);
				 threeSourceAnalyzeData.push(carbonData);
				 
				 //蛋白质来源分析['优质蛋白'+'<br>'+410+'Kacl'+'<br>',72.0]
				 var highQualityProData = new Array();
				 highQualityProData.push('优质蛋白'+'：<br>'+data.highQualityProIntake+'g');
				 highQualityProData.push(perData.highQualityProPercent);
				 proteinAnalyzeData.push(highQualityProData);
				 var nonPrimeProData = new Array();
				 nonPrimeProData.push('非优质蛋白'+'：<br>'+data.nonPrimeProIntake+'g');
				 nonPrimeProData.push(perData.nonPrimeProPercent);
				 proteinAnalyzeData.push(nonPrimeProData);
			 }
		 }
	 });
}
$(function(){
	setTimeout(function(){
		getAnalyzeData();
		if(flag == 1){
			showHighCharts();
		}else{
			//隐藏分析图表
			$(".analyze_nutrition").css("display","none");
		}
	},200);
	
	/**
	 * 点击返回
	 */
	$(document).on("click","#goBack",function(){
		window.history.go(-1);
	});

});
function showHighCharts(){
	//摄入量分析
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
                text: '摄入量（克）'
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
            data: foodCatagoryAnalyzeData,
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
	
	//三大能量物质来源分析
//	setTimeout(function(){
    	$('#container_2').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: ' '
            },
            tooltip: {
                headerFormat: '{series.name}',
                pointFormat: '{point.name}：<b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        distance: 2,
                        format: '<b>{point.name}：</b>{point.percentage:.1f} %',
                        style: {
                        	fontSize: '12px',
                        	fontWeight:'normal',
                        	fontFamily:"Microsoft yaHei",
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: ' ',
                data: threeSourceAnalyzeData
            }]
        });
//    },100);
	
	//蛋白质来源分析
//	setTimeout(function(){
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
                headerFormat: '{series.name}',
                pointFormat: '{point.name}(<b>{point.percentage:.1f}%</b>)'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        distance: 6,
                        format: '<b>{point.name}</b>({point.percentage:.1f} %)',
                        style: {
                        	fontFamily:"Microsoft yaHei",
                        	fontWeight:'normal',
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
                            fontSize:'12px'
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: ' ',
                data: proteinAnalyzeData
            }]
        });
//	},100);
	
	//餐次供能比
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
            headerFormat: '{series.name}',
            pointFormat: '{point.name}(<b>{point.percentage:.1f}%</b>)'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>({point.percentage:.1f} %)',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: ' ',
            data: mealsTypeIntakeAnalyzeData
        }]
    });
}
