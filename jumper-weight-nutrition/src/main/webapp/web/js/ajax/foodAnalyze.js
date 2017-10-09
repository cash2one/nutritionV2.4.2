/**
 * 营养素分析js
 */
var userId = GetQueryString("userId");

//初始化表格
$(function(){
	setTimeout(function(){
		$("#analyzeTable").load("common/foodAnalyze.html");
		//初始化页面数据
		foodAnalyze();
	},200);
});

function foodAnalyze(){
	var str = new Array();
	 $("#table-food-list input").each(function(){
		 var food_id = $(this).attr("food-id");
		 var weight = $(this).val()==""?0:$(this).val();
		 var subStr = {'foodId':food_id,'weight':weight};
		 str.push(subStr);
	 });
	 if(userId == null || userId == ""){
		 var param = {
			 voList:JSON.stringify(str)
		 };
	 }else{
		 var param = {
			 userId:userId,
			 voList:JSON.stringify(str),
			 hospitalId:hospitalId
		 };
	 }
	 $.ajax({
		 type:"POST",
		 url:basePath+"/foodAnalyze/nutritionAnalyze",
		 data:param,
		 dataType:"json",
		 cache:false,
		 success:function(json){
			 if(json.msg==1){
				 var data = json.data;
				 $("#energyIntake").text(data.energyIntake);
				 $("#energyRecommend").text(data.energyRecommend);
				 $("#energyPer").text(data.energyPer);
				 $("#proteinIntake").text(data.proteinIntake);
				 $("#proteinRecommend").text(data.proteinRecommend[0]+"~"+data.proteinRecommend[1]);
				 $("#proteinPer").text(data.proteinPer[0]+"~"+data.proteinPer[1]);
				 $("#fatIntake").text(data.fatIntake);
				 $("#fatRecommend").text(data.fatRecommend[0]+"~"+data.fatRecommend[1]);
				 $("#fatPer").text(data.fatPer[0]+"~"+data.fatPer[1]);
				 $("#carbonIntake").text(data.carbonIntake);
				 $("#carbonRecommend").text(data.carbonRecommend[0]+"~"+data.carbonRecommend[1]);
				 $("#carbonPer").text(data.carbonPer[0]+"~"+data.carbonPer[1]);
				 $("#caIntake").text(data.caIntake);
				 $("#caRecommend").text(data.caRecommend);
				 $("#caPer").text(data.caPer);
				 $("#feIntake").text(data.feIntake);
				 $("#feRecommend").text(data.feRecommend);
				 $("#fePer").text(data.fePer);
				 $("#znIntake").text(data.znIntake);
				 $("#znRecommend").text(data.znRecommend);
				 $("#znPer").text(data.znPer);
				 $("#seIntake").text(data.seIntake);
				 $("#seRecommend").text(data.seRecommend);
				 $("#sePer").text(data.sePer);
				 $("#cuIntake").text(data.cuIntake);
				 $("#cuRecommend").text(data.cuRecommend);
				 $("#cuPer").text(data.cuPer);
				 $("#naIntake").text(data.naIntake);
				 $("#naRecommend").text(data.naRecommend);
				 $("#naPer").text(data.naPer);
				 $("#iIntake").text(data.iIntake);
				 $("#iRecommend").text(data.iRecommend);
				 $("#iPer").text(data.iPer);
				 $("#pIntake").text(data.pIntake);
				 $("#pRecommend").text(data.pRecommend);
				 $("#pPer").text(data.pPer);
				 $("#kIntake").text(data.kIntake);
				 $("#kRecommend").text(data.kRecommend);
				 $("#kPer").text(data.kPer);
				 $("#mgIntake").text(data.mgIntake);
				 $("#mgRecommend").text(data.mgRecommend);
				 $("#mgPer").text(data.mgPer);
				 $("#mnIntake").text(data.mnIntake);
				 $("#mnRecommend").text(data.mnRecommend);
				 $("#mnPer").text(data.mnPer);
				 $("#vaIntake").text(data.vaIntake);
				 $("#vaRecommend").text(data.vaRecommend);
				 $("#vaPer").text(data.vaPer);
				 $("#vb6Intake").text(data.vb6Intake);
				 $("#vb6Recommend").text(data.vb6Recommend);
				 $("#vb6Per").text(data.vb6Per);
				 $("#vcIntake").text(data.vcIntake);
				 $("#vcRecommend").text(data.vcRecommend);
				 $("#vcPer").text(data.vcPer);
				 
				 $("#vb1Intake").text(data.vb1Intake);
				 $("#vb1Recommend").text(data.vb1Recommend);
				 $("#vb1Per").text(data.vb1Per);
				 
				 $("#vb2Intake").text(data.vb2Intake);
				 $("#vb2Recommend").text(data.vb2Recommend);
				 $("#vb2Per").text(data.vb2Per);
				 
				 $("#veIntake").text(data.veIntake);
				 $("#veRecommend").text(data.veRecommend);
				 $("#vePer").text(data.vePer);
				 //配置方案时隐藏推荐量和百分比
				 if(userId == null || userId == ""){
					 $("#analyzeTable").find('table tr').find('td:eq(3)').hide();
					 $("#analyzeTable").find('table tr').find('th:eq(3)').hide();
					 $("#analyzeTable").find('table tr').find('td:eq(2)').hide();
					 $("#analyzeTable").find('table tr').find('th:eq(2)').hide();
				 }
			 }
		 }
	 });
}
/**
 * 营养素分析(修改分量时调用)
 */
$(document).on("blur","#table-food-list input",function(){
	var weight = $(this).val();
	if(weight=="."){
//		 layer.msg("请输入数字！");
		 $(this).val("");
	 }else{
		 foodAnalyze();
	 }
});

/**
 * 删除食材时触发营养素分析
 */
 $(document).on("click",".delete-meals-food",function(){
	setTimeout(function(){
		foodAnalyze();
	},100);
 });

//回车键触发enterBtnEvent按钮单击事件
 $(".leftPlan").keydown(function(e){
	e = e||event;
 	var key=e.keyCode;
 	if(key == 13){
 		$(".enterBtnEvent").click();
 	}
 });
 //制定页面按enter向下操作
/* $(".rightPlanDetail").keydown(function(){
 	keyDown(event);
 });*/
 
 $(".rightPlanDetail").keydown(function(e) {
	e = e||event;
	var index = 0;
	var target = $(".rightPlanDetail").find("input[type='text']");
	target.each(function(i) {
		if ($(this).is(":focus")) {
			index = (i < target.length - 1) ? i+1 : 0;
			return false;
		}
	});
	var key = e.keyCode;
	if (key == 13) {
		target.eq(index).focus();
		setTimeout(function(){
			target.eq(index).select();
		},100);
	}
	});
 
/* function keyDown(event) {
	var inputs=document.getElementsByClassName("rightPlanDetail")[0].getElementsByTagName("INPUT");
	var inputsText = new Array();
	for(var i=0; i<inputs.length; i++){
		if(inputs[i].type=="text"){
			inputsText.push(inputs[i]);
		}
	}
	var focus=document.activeElement; 
	var key=event.keyCode; 
	var event=window.event||event;
	for(var i=0; i<inputsText.length; i++){
		if(inputsText[i]===focus) break; 
	}
	if(key == 13){
		if(i <inputsText.length-1) {
			inputsText[i+1].focus();
			setTimeout(function(){
				inputsText[i+1].select();
			},100);
		}else{
			setTimeout(function(){
				inputsText[0].select();
			},100);
		}
	}
 }*/ 
 
