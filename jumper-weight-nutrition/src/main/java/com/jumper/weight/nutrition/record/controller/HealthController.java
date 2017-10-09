package com.jumper.weight.nutrition.record.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.common.util.WeightFormula;
import com.jumper.weight.nutrition.diet.service.WeightMealsInfoService;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalSettingService;
import com.jumper.weight.nutrition.recipes.entity.UserRecipesPlan;
import com.jumper.weight.nutrition.recipes.service.UserRecipesPlanService;
import com.jumper.weight.nutrition.record.service.UserWeightRecordService;
import com.jumper.weight.nutrition.record.vo.VOHealthInfo;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.sport.service.UserSportRecordService;
import com.jumper.weight.nutrition.sport.vo.VoDailySportRecord;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
/**
 * 用户首页，用户个人健康记录controller
 * @author gyx
 * @time 2017年8月30日
 */
@RestController
@RequestMapping("/health")
@Api(value = "/health", description = "用户首页数据")
public class HealthController {
	@Autowired
	private UserWeightRecordService userWeightRecordService;
	@Autowired
	private WeightMealsInfoService weightMealsInfoService;
	@Autowired
	private UserSportRecordService userSportRecordService;
	@Autowired
	private UserRecipesPlanService userRecipesPlanService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private WeightHospitalSettingService weightHospitalSettingService;
	
	/**
	 * 获取用户首页用户数据记录
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getUserHealthInfo")
	public ReturnMsg getUserHealthInfo(@ApiParam(value = "用户id") @RequestParam int userId,
			@ApiParam(value = "医院id") @RequestParam int hospitalId){
		VOHealthInfo voHealthInfo;
		try {
			voHealthInfo = new VOHealthInfo();
			//用户最近一条体重记录
			VoUserWeightRecord data = userWeightRecordService.findUserLastWeight(userId, hospitalId);
			int recordId = 0;
			double weight = 0;
			String addTime = "";
			if(data != null){
				recordId = data.getRecordId();
				weight = data.getAverageValue();
				addTime = data.getAddTime();
			}
			voHealthInfo.setRecordId(recordId);
			voHealthInfo.setCurrWeight(weight);
			voHealthInfo.setCurrWeightTime(addTime);
			
			//查询用户今日饮食记录
			double eatKcal = 0;
			String today = TimeUtils.dateFormatToString(new Date(), Const.YYYYMMDD);
			List<VOWeightMealsInfo> voMealsInfoList = weightMealsInfoService.findUserMealsInfoList(userId, today, today, 0);
			if(voMealsInfoList != null && voMealsInfoList.size() > 0){
				for (VOWeightMealsInfo voWeightMealsInfo : voMealsInfoList) {
					eatKcal += voWeightMealsInfo.getTotalCalorie();
				}
			}
			voHealthInfo.setEatKcal(FunctionUtils.setDecimal(eatKcal, 1));
			//查询用户今日运动记录
			double sportKcal = 0;
			double sportTimeLength = 0;
			VoDailySportRecord sportRecord = userSportRecordService.listSportRecordByDate(userId, today);
			if(sportRecord != null){
				sportKcal = sportRecord.getTotalCalorie();
				sportTimeLength = sportRecord.getTotalTime();
			}
			voHealthInfo.setSportKcal(sportKcal);
			voHealthInfo.setSportTimeLength(sportTimeLength);
			//获取用户目标摄入量（根据医生制定的方案选择最大摄入量的食谱）和还需摄入量
			List<UserRecipesPlan> planList = userRecipesPlanService.findUserRecipesPlans(hospitalId, userId, 0);
			double suggestIntake = 0;
			if(planList != null && planList.size() > 0){
				suggestIntake = planList.get(0).getIntakeCalorie();
				for (UserRecipesPlan userRecipesPlan : planList) {
					if(userRecipesPlan.getIntakeCalorie() > suggestIntake){
						suggestIntake = userRecipesPlan.getIntakeCalorie();
					}
				}
			}
			voHealthInfo.setSuggestIntake(suggestIntake);
			voHealthInfo.setNeedIntake(suggestIntake == 0?0:FunctionUtils.setDecimal(suggestIntake-eatKcal, 1));
			
			//获取用户安全体重范围(调service查询孕前体重)
			VoUserInfo voUserInfo = userInfoService.findWeiTsUserByUId(userId, hospitalId);
			if(voHealthInfo != null){
				WeightHospitalSetting setting = weightHospitalSettingService.findSettingByHospId(hospitalId);
				int formula = (setting != null) ? setting.getSafeFormula() : 0; 
				int[] pregnantWeek = FunctionUtils.calPregnantWeek(new Date(), TimeUtils.convertToDate(voUserInfo.getExpectedDate()));
				double[] safeWeight = WeightFormula.getSafeWeight(voUserInfo.getBmi(), voUserInfo.getWeight(), pregnantWeek[2], formula, voUserInfo.getPregnantType()==null?0:voUserInfo.getPregnantType());
				voHealthInfo.setSafeWeightLow(safeWeight[0]);
				voHealthInfo.setSafeWeightHigh(safeWeight[1]);
				voHealthInfo.setBeforeWeight(voUserInfo.getWeight());
				voHealthInfo.setBeforeBMI(voUserInfo.getBmi());
				voHealthInfo.setHeight(voUserInfo.getHeight());
				voHealthInfo.setPregnantWeek(pregnantWeek);
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "查询用户首页数据成功", voHealthInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "查询用户首页数据异常");
		}
		
	}
}
