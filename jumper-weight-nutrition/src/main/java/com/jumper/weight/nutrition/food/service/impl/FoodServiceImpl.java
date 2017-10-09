package com.jumper.weight.nutrition.food.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.HttpClient;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.diet.entity.DietSurvey;
import com.jumper.weight.nutrition.diet.mapper.DietSurveyMapper;
import com.jumper.weight.nutrition.diet.service.WeightMealsInfoService;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;
import com.jumper.weight.nutrition.food.entity.WeightFood;
import com.jumper.weight.nutrition.food.mapper.WeightFoodMapper;
import com.jumper.weight.nutrition.food.service.FoodService;
import com.jumper.weight.nutrition.food.vo.VOAccountPercent;
import com.jumper.weight.nutrition.food.vo.VOFoodCatagoryAnalyze;
import com.jumper.weight.nutrition.food.vo.VOMealsTypeIntakeAnalyze;
import com.jumper.weight.nutrition.food.vo.VONutritionAnalyze;
import com.jumper.weight.nutrition.food.vo.VOTotalAnalyze;
import com.jumper.weight.nutrition.food.vo.VOWeightFood;
import com.jumper.weight.nutrition.food.vo.VoFoodWeight;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;

@Service
public class FoodServiceImpl extends BaseServiceImpl<WeightFood> implements FoodService{
	@Autowired
	private WeightFoodMapper weightFoodMapper;
	@Autowired 
	private UserInfoService userInfoService;
	@Autowired
	private DietSurveyMapper dietSurveyMapper;
	@Autowired
	private WeightMealsInfoService weightMealsInfoService;
	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;

	@Override
	protected BaseMapper<WeightFood> getDao() {
		return weightFoodMapper;
	}

	/**
	 * 通过食材名称查询食材信息
	 */
	@Override
	public List<VOWeightFood> findFoodByName(String foodName) {
		List<VOWeightFood> voFoodList = weightFoodMapper.findFoodByName(foodName);
		if(voFoodList != null && voFoodList.size() > 0){
			return voFoodList;
		}
		return null;
	}

	/**
	 * 通过食材id数组查询食材信息
	 */
	@Override
	public List<WeightFood> findFoodByIds(String[] foodIds) {
		List<WeightFood> foodList = weightFoodMapper.findFoodByIds(foodIds);
		if(foodList != null && foodList.size() > 0){
			return foodList;
		}
		return null;
	}

	@Override
	public VONutritionAnalyze getVONutritionAnalyze(List<VoFoodWeight> foodWeightList, int day, Integer userId, Integer hospitalId) {
		VONutritionAnalyze analyze = new VONutritionAnalyze();
		if (userId != null) {
			VoUserInfo voUser = userInfoService.findVoUserByUId(userId, hospitalId);
			//获取推荐卡洛里
			Double bmi = FunctionUtils.getBMI(voUser.getHeight(), voUser.getWeight());
			int suggestIntake = FunctionUtils.getSuggestIntake(voUser.getHeight().doubleValue(), bmi, 1);//默认公式2
			boolean flag = "孕中期".equals(voUser.getPregnantStage()) || "孕晚期".equals(voUser.getPregnantStage());
			suggestIntake = (flag) ? suggestIntake + 200 : suggestIntake;
			
			analyze.setEnergyRecommend(suggestIntake);
			if ("孕中期".equals(voUser.getPregnantStage())) {
				analyze.setCaRecommend(1000);
				analyze.setFeRecommend(24);
				analyze.setVaRecommend(770);
				analyze.setVcRecommend(115);
				analyze.setVb1Recommend(1.4);
				analyze.setVb2Recommend(1.4);
			} else if ("孕晚期".equals(voUser.getPregnantStage())) {
				analyze.setCaRecommend(1000);
				analyze.setFeRecommend(29);
				analyze.setVaRecommend(770);
				analyze.setVcRecommend(115);
				analyze.setVb1Recommend(1.5);
				analyze.setVb2Recommend(1.5);
			}
		}
		
		if (ArrayUtils.isEmpty(foodWeightList)) {
			return analyze;
		}
		StringBuilder sbIds = new StringBuilder();
		for (VoFoodWeight vo : foodWeightList) {
			sbIds.append(vo.getFoodId() + ",");
		}
		String foodIds = sbIds.substring(0, sbIds.length() - 1);
		String url = Consts.BASE_PATH + Consts.LIST_FOOD_PATH;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", foodIds);
		HttpClient client = new HttpClient(url, params);
		List<WeightFood> foodList = new ArrayList<WeightFood>();
		try {
			String retStr = client.post();
			ReturnMsg ret = JSON.parseObject(retStr, ReturnMsg.class);
			if (ret.getMsg() != 1 && ret.getData() == null) {
				return analyze;
			}
			foodList = JSON.parseArray(JSON.toJSONString(ret.getData()), WeightFood.class);
		} catch (IOException e) {
			e.printStackTrace();
			return analyze;
		}
		
		//开始封装VONutritionAnalyze
		Double energyIntake = 0D, proteinIntake = 0D, fatIntake = 0D, carbonIntake = 0D, naIntake = 0D,
				caIntake = 0D, feIntake = 0D, znIntake = 0D, pIntake = 0D, kIntake = 0D,
				mgIntake = 0D, cuIntake = 0D, mnIntake = 0D, seIntake = 0D, iIntake = 0D, 
				vaIntake = 0D, vb6Intake = 0D, vcIntake = 0D, highQualityProIntake = 0D, nonPrimeProIntake,
				vb1Intake = 0D, vb2Intake = 0D, veIntake = 0D;
		for (VoFoodWeight vo : foodWeightList) {
			WeightFood food = null;
			for (WeightFood f : foodList) {
				if (f.getId().intValue() == vo.getFoodId()) {
					food = f;
					break;
				}
			}
			if (food == null) {
				continue;
			}
			energyIntake += vo.getWeight() / 100 * food.getCalorie();
			proteinIntake += vo.getWeight() / 100 * food.getProtein();
			fatIntake += vo.getWeight() / 100 * food.getFat();
			carbonIntake += vo.getWeight() / 100 * food.getCarbo();
			naIntake += vo.getWeight() / 100 * food.getNa();
			caIntake += vo.getWeight() / 100 * food.getCa();
			feIntake += vo.getWeight() / 100 * food.getFe();
			znIntake += vo.getWeight() / 100 * food.getZn();
			pIntake += vo.getWeight() / 100 * food.getP();
			kIntake += vo.getWeight() / 100 * food.getKa();
			mgIntake += vo.getWeight() / 100 * food.getMg();
			cuIntake += vo.getWeight() / 100 * food.getCu();
			mnIntake += vo.getWeight() / 100 * food.getMn();
			seIntake += vo.getWeight() / 100 * food.getSe();//μg换算为mg
			iIntake += vo.getWeight() / 100 * food.getI() / 1000;//μg换算为mg
			vaIntake += vo.getWeight() / 100 * food.getVa();
			vb6Intake += vo.getWeight() / 100 * food.getVb6();
			vcIntake += vo.getWeight() / 100 * food.getVc();
			vb1Intake += vo.getWeight() / 100 * food.getVb1();
			vb2Intake += vo.getWeight() / 100 * food.getVb2();
			veIntake += vo.getWeight() / 100 * food.getVe();
			if (Const.FOOD_CATEGORY[1].equals(food.getCategoryName()) ||
				Const.FOOD_CATEGORY[4].equals(food.getCategoryName()) ||
				Const.FOOD_CATEGORY[5].equals(food.getCategoryName())) {
				highQualityProIntake += vo.getWeight() / 100 * food.getProtein();
			}
		}
		//设置摄入量，自动算出占比
		analyze.setEnergyIntake(FunctionUtils.setDecimal(energyIntake / day, 1));
		analyze.setProteinIntake(FunctionUtils.setDecimal(proteinIntake / day, 1));
		analyze.setFatIntake(FunctionUtils.setDecimal(fatIntake / day, 1));
		analyze.setCarbonIntake(FunctionUtils.setDecimal(carbonIntake / day, 1));
		analyze.setNaIntake(FunctionUtils.setDecimal(naIntake / day, 1));
		analyze.setCaIntake(FunctionUtils.setDecimal(caIntake / day, 1));
		analyze.setFeIntake(FunctionUtils.setDecimal(feIntake / day, 1));
		analyze.setZnIntake(FunctionUtils.setDecimal(znIntake / day, 1));
		analyze.setpIntake(FunctionUtils.setDecimal(pIntake / day, 1));
		analyze.setkIntake(FunctionUtils.setDecimal(kIntake / day, 1));
		analyze.setMgIntake(FunctionUtils.setDecimal(mgIntake / day, 1));
		analyze.setCuIntake(FunctionUtils.setDecimal(cuIntake / day, 1));
		analyze.setMnIntake(FunctionUtils.setDecimal(mnIntake / day, 1));
		analyze.setSeIntake(FunctionUtils.setDecimal(seIntake / day, 1));
		analyze.setiIntake(FunctionUtils.setDecimal(iIntake / day, 1));
		analyze.setVaIntake(FunctionUtils.setDecimal(vaIntake / day, 1));
		analyze.setVb6Intake(FunctionUtils.setDecimal(vb6Intake / day, 1));
		analyze.setVcIntake(FunctionUtils.setDecimal(vcIntake / day, 1));
		nonPrimeProIntake = proteinIntake - highQualityProIntake;
		analyze.setHighQualityProIntake(FunctionUtils.setDecimal(highQualityProIntake/day,1));
		analyze.setNonPrimeProIntake(FunctionUtils.setDecimal(nonPrimeProIntake/day,1));
		analyze.setVb1Intake(FunctionUtils.setDecimal(vb1Intake / day, 1));
		analyze.setVb2Intake(FunctionUtils.setDecimal(vb2Intake / day, 1));
		analyze.setVeIntake(FunctionUtils.setDecimal(veIntake / day, 1));
		return analyze;
	}

	@Override
	public Integer calculateSugIntake(int userId, int hospitalId) {
		VoUserInfo voUser = userInfoService.findVoUserByUId(userId, hospitalId);
		int suggestIntake = FunctionUtils.getSuggestIntake(voUser.getHeight().doubleValue(), voUser.getWeight(), 1);//默认公式2
		boolean flag = "孕中期".equals(voUser.getPregnantStage()) || "孕晚期".equals(voUser.getPregnantStage());
		suggestIntake = (flag) ? suggestIntake + 200 : suggestIntake;
		return suggestIntake;
	}

	/**
	 * 各类食物摄入量分析
	 */
	@Override
	public Map<String, Object> getFoodCatagoryAnalyzeList(
			Integer outpatientId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<VOFoodCatagoryAnalyze> analyzeList = new ArrayList<VOFoodCatagoryAnalyze>();
		Map<String, Object> map = getFoodCatagoryAnalyzeData(outpatientId);
		List<VOWeightMealsInfo> voMealsInfoList = (List<VOWeightMealsInfo>) map.get("voMealsInfoList");
		int days = (int) map.get("days");
		List<String> dateList = (List<String>) map.get("dateList");
		if(voMealsInfoList != null && voMealsInfoList.size() > 0){
			//获取膳食数据
			voMealsInfoList = getVOMealsInfoList(voMealsInfoList);
			//组装分析数据
			String[] categorys = Const.FOOD_CATEGORY;
			for (int i = 0; i < categorys.length; i++) {
				VOFoodCatagoryAnalyze analyze = new VOFoodCatagoryAnalyze();
				double weight = 0;
				for (VOWeightMealsInfo voWeightMealsInfo : voMealsInfoList) {
					if(StringUtils.isNotEmpty(voWeightMealsInfo.getCategoryName()) && categorys[i].equals(voWeightMealsInfo.getCategoryName())){
						weight += voWeightMealsInfo.getFoodWeight();
					}
				}
				analyze.setCatagoryName(categorys[i]);
				analyze.setFoodWeight(weight/days);
				analyzeList.add(analyze);
			}
			ret.put("analyzeList", analyzeList);
			ret.put("dateList", dateList);
			return ret;
		}
		return null;
	}
	
	/**
	 * 获取膳食分析数据
	 */
	public Map<String, Object> getFoodCatagoryAnalyzeData(Integer outpatientId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> dateList = new ArrayList<String>();
		//分析天数
		int days = 0;
		List<VOWeightMealsInfo> voMealsInfoList = new ArrayList<VOWeightMealsInfo>();
		//查询膳食调查记录,记录不为空，整合所有记录，，记录为空，如果是膳食调查页面分析，则取昨日记录
		List<DietSurvey> dietSurveyList = dietSurveyMapper.findUserDietSurveyList(outpatientId);
		if(dietSurveyList != null && dietSurveyList.size() > 0){
			days = dietSurveyList.size();
			for (DietSurvey dietSurvey : dietSurveyList) {
				dateList.add(TimeUtils.dateFormatToString(dietSurvey.getEatDate(), Const.YYYYMMDD));
				List<VOWeightMealsInfo> voMealsInfos = new ArrayList<VOWeightMealsInfo>();
				if(StringUtils.isNotBlank(dietSurvey.getDietMsg())){
					voMealsInfos = JSON.parseArray(dietSurvey.getDietMsg(), VOWeightMealsInfo.class);
					voMealsInfoList.addAll(voMealsInfos);
				}
			}
		}/*else{
			if(type == 0){
				//膳食调查页面，查询昨日记录
				HospitalOutpatient outp = hospitalOutpatientMapper.findById(outpatientId);
				voMealsInfoList = weightMealsInfoService.findUserMealsInfoList(outp.getUserId(), "", "", 0);
				if(voMealsInfoList != null && voMealsInfoList.size() > 0){
					days = 1;
				}
			}
		}*/
		if(voMealsInfoList != null && voMealsInfoList.size() > 0){
			map.put("voMealsInfoList", voMealsInfoList);
		}
		map.put("days", days);
		map.put("dateList", dateList);
		return map;
	}
	
	/**
	 * 组装各类食物膳食分析记录
	 * @param mealsInfoList 膳食记录
	 * @param foodList 食材信息
	 * @return
	 */
	public List<VOWeightMealsInfo> getVOMealsInfoList(
			List<VOWeightMealsInfo> voMealsInfoList) {
		if(voMealsInfoList != null && voMealsInfoList.size() > 0){
			List<Integer> ids = new ArrayList<Integer>();
			for (int i = 0; i < voMealsInfoList.size(); i++) {
				ids.add(voMealsInfoList.get(i).getFoodId());
			}
			String idstr = StringUtils.join(ids, ",");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ids", idstr);
			HttpClient httpClient = new HttpClient(Consts.BASE_PATH+"/food/findFoodByIds", param);
			String result = "";
			List<WeightFood> foodList = new ArrayList<WeightFood>();
			try {
				result= httpClient.post();
				if(StringUtils.isNotEmpty(result)){
					ReturnMsg returnMsg = JSON.parseObject(result, ReturnMsg.class);
					if(returnMsg.getMsg() == 1){
						foodList = JSON.parseArray(returnMsg.getData().toString(), WeightFood.class);
					}
				}
				if(voMealsInfoList != null && voMealsInfoList.size() > 0){
					for (VOWeightMealsInfo voWeightMealsInfo : voMealsInfoList) {
						if(foodList != null && foodList.size() > 0){
							for (WeightFood weightFood : foodList) {
								if(weightFood.getId().equals(voWeightMealsInfo.getFoodId())){
									voWeightMealsInfo.setCategoryId(weightFood.getCategoryId());
									voWeightMealsInfo.setCategoryName(weightFood.getCategoryName());
									voWeightMealsInfo.setCalorie(weightFood.getCalorie());
								}
							}
						}
					}
				}
				return voMealsInfoList;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 餐次功能比分析
	 */
	@Override
	public List<VOMealsTypeIntakeAnalyze> getMealsTypeIntakeAnalyzeList(
			Integer outpatientId) {
		List<VOMealsTypeIntakeAnalyze> analyzeList = new ArrayList<VOMealsTypeIntakeAnalyze>();
		Map<String, Object> map = getFoodCatagoryAnalyzeData(outpatientId);
		List<VOWeightMealsInfo> voMealsInfoList = (List<VOWeightMealsInfo>) map.get("voMealsInfoList");
		int days = (int) map.get("days");
		if(voMealsInfoList != null && voMealsInfoList.size() > 0){
			//获取膳食数据
			voMealsInfoList = getVOMealsInfoList(voMealsInfoList);
			//组装分析数据
			//摄入总能量
			double totalEnergy = 0;
			for (VOWeightMealsInfo voWeightMealsInfo : voMealsInfoList) {
				totalEnergy += voWeightMealsInfo.getFoodWeight()/100*voWeightMealsInfo.getCalorie();
			}
			//平均摄入能量
			totalEnergy = totalEnergy/days;
			for(int i=0;i<6;i++){
				VOMealsTypeIntakeAnalyze analyze = new VOMealsTypeIntakeAnalyze();
				double energy = 0;
				for (VOWeightMealsInfo voWeightMealsInfo : voMealsInfoList) {
					if(voWeightMealsInfo.getMealsType() == i+1){
						energy += voWeightMealsInfo.getFoodWeight()/100*voWeightMealsInfo.getCalorie();
					}
				}
				if(energy > 0){
					analyze.setMealsName(Const.MEALS_NAME[i]);
					analyze.setEnergyIntake(FunctionUtils.setDecimal(energy/days,1));
					analyze.setAccountPercent(FunctionUtils.setDecimal((energy/days)/totalEnergy*100,1));
					analyzeList.add(analyze);
				}
			}
			return analyzeList;
		}
		return null;
	}

	@Override
	public Map<String, Object> getVOMealsInfoList(Integer outpatientId) {
		Map<String, Object> map = getFoodCatagoryAnalyzeData(outpatientId);
		Map<String, Object> param = new HashMap<String, Object>();
		List<VOWeightMealsInfo> voMealsInfoList = (List<VOWeightMealsInfo>) map.get("voMealsInfoList");
		List<VoFoodWeight> foodWeightList = new ArrayList<VoFoodWeight>();
		if(voMealsInfoList != null && voMealsInfoList.size() > 0){
			for (VOWeightMealsInfo voWeightMealsInfo : voMealsInfoList) {
				VoFoodWeight foodWeight = new VoFoodWeight();
				foodWeight.setFoodId(voWeightMealsInfo.getFoodId());
				foodWeight.setWeight(Double.parseDouble(voWeightMealsInfo.getFoodWeight()+""));
				foodWeightList.add(foodWeight);
			}
			HospitalOutpatient outp = hospitalOutpatientMapper.findById(outpatientId);
			param.put("userId", outp.getUserId());
			param.put("foodWeightList", foodWeightList);
			param.put("days", map.get("days"));
			return param;
		}else{
			return null;
		}
	}

	/**
	 * 来源分析
	 */
	@Override
	public VOTotalAnalyze getVOTotalAnalyze(
			VONutritionAnalyze voNutritionAnalyze) {
		VOTotalAnalyze voTotalAnalyze = new VOTotalAnalyze();
		voTotalAnalyze.setVoNutritionAnalyze(voNutritionAnalyze);
		VOAccountPercent voAccountPercent = new VOAccountPercent();
		//三大能量物质分析
		double proIntakeCal = FunctionUtils.setDecimal(voNutritionAnalyze.getProteinIntake()*4,1);//蛋白质摄入能量
		double fatIntakeCal = FunctionUtils.setDecimal(voNutritionAnalyze.getFatIntake()*9,1);//脂肪摄入能量
		double carbonIntakeCal = FunctionUtils.setDecimal(voNutritionAnalyze.getCarbonIntake()*4,1);//碳水化合物摄入能量
		double totalCalories =  proIntakeCal+fatIntakeCal+carbonIntakeCal;//总能量
		double proPercent = FunctionUtils.setDecimal(proIntakeCal/totalCalories*100,1);//蛋白质摄入能量占比
		double fatPercent = FunctionUtils.setDecimal(fatIntakeCal/totalCalories*100,1);//脂肪摄入能量占比
		double carbonPercent = FunctionUtils.setDecimal(carbonIntakeCal/totalCalories*100,1);//碳水化合物摄入能量占比
		voAccountPercent.setProIntakeCal(proIntakeCal);
		voAccountPercent.setFatIntakeCal(fatIntakeCal);
		voAccountPercent.setCarbonIntakeCal(carbonIntakeCal);
		voAccountPercent.setProPercent(proPercent);
		voAccountPercent.setFatPercent(fatPercent);
		voAccountPercent.setCarbonPercent(carbonPercent);
		//蛋白质来源分析
		double totalWeight = voNutritionAnalyze.getHighQualityProIntake()+voNutritionAnalyze.getNonPrimeProIntake();//蛋白质总摄入量
		double highQualityProPercent = FunctionUtils.setDecimal(voNutritionAnalyze.getHighQualityProIntake()/totalWeight*100,1);//优质蛋白占比
		double nonPrimeProPercent = FunctionUtils.setDecimal(voNutritionAnalyze.getNonPrimeProIntake()/totalWeight*100,1);//非优质蛋白占比
		voAccountPercent.setHighQualityProPercent(highQualityProPercent);
		voAccountPercent.setNonPrimeProPercent(nonPrimeProPercent);
		voTotalAnalyze.setVoAccountPercent(voAccountPercent);
		return voTotalAnalyze;
	}
	
}
