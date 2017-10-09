package com.jumper.weight.nutrition.recipes.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.HttpClient;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.food.entity.WeightFood;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesDetail;
import com.jumper.weight.nutrition.recipes.entity.UserRecipesPlan;
import com.jumper.weight.nutrition.recipes.mapper.StandardRecipesDetailMapper;
import com.jumper.weight.nutrition.recipes.mapper.UserRecipesPlanMapper;
import com.jumper.weight.nutrition.recipes.service.UserRecipesPlanService;
import com.jumper.weight.nutrition.recipes.vo.VOUserRecipesDetail;
import com.jumper.weight.nutrition.recipes.vo.VOUserRecipesList;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;

@Service
public class UserRecipesPlanServiceImpl extends BaseServiceImpl<UserRecipesPlan> implements UserRecipesPlanService{
	
	@Autowired
	private UserRecipesPlanMapper userRecipesPlanMapper;
	
	@Autowired
	private StandardRecipesDetailMapper standardRecipesDetailMapper;
	
	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;
	
	@Override
	protected BaseMapper<UserRecipesPlan> getDao() {
		return userRecipesPlanMapper;
	}

	/**
	 * 查询用户食谱列表
	 */
	@Override
	public List<UserRecipesPlan> findUserRecipesPlans(int hospitalId,
			int userId, int outpatientId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hospitalId", hospitalId);
		param.put("userId", userId);
		param.put("outpatientId", outpatientId);
		List<UserRecipesPlan> planList = userRecipesPlanMapper.findUserRecipesPlans(outpatientId);
		if(planList != null && planList.size() > 0){
			return planList;
		}else{
			//本次门诊的食谱为空，就查询最新正在使用的食谱列表
			planList = userRecipesPlanMapper.findUserLeastRecipesPlans(param);
			if(planList != null && planList.size() > 0){
				return planList;
			}
		}
		return null;
	}

	/**
	 * 获取或另存用户食谱列表
	 */
	@Override
	public List<UserRecipesPlan> saveAsOrFindUserRecipesPlans(int hospitalId,
			int userId, int outpatientId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hospitalId", hospitalId);
		param.put("userId", userId);
		param.put("outpatientId", outpatientId);
		List<UserRecipesPlan> planList = userRecipesPlanMapper.findUserRecipesPlans(outpatientId);
		if(planList != null && planList.size() > 0){
			return planList;
		}else{
			//本次门诊的食谱为空，就查询最新正在使用的食谱列表，另存一份作为本次门诊的食谱以便修改
			planList = userRecipesPlanMapper.findUserLeastRecipesPlans(param);
			if(planList != null && planList.size() > 0){
				try {
					int realOutpatientId = planList.get(0).getOutpatientId();
					HospitalOutpatient hospitalOutpatient = hospitalOutpatientMapper.findById(realOutpatientId);//最新的一份食谱的门诊记录
					HospitalOutpatient outpatient = hospitalOutpatientMapper.findById(outpatientId);//当前流程中的门诊记录
					//另存一份饮食建议到当前流程的门诊记录中去
					outpatient.setDietAdvice(hospitalOutpatient.getDietAdvice());
					hospitalOutpatientMapper.update(outpatient);
					
					for (UserRecipesPlan userRecipesPlan : planList) {
						userRecipesPlan.setAddTime(new Date());
						userRecipesPlan.setOutpatientId(outpatientId);
					}
					boolean b = false;
					//另存一份食谱到本次门诊
					b = userRecipesPlanMapper.insertBatch(planList)>0;
					if(b){
						//查询插入后的记录
						planList = userRecipesPlanMapper.findUserRecipesPlans(outpatientId);
						return planList;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 导入已配置好的方案作为用户标准食谱
	 */
	@Override
	public List<UserRecipesPlan> importUserRecipesPlans(int hospitalId,
			int userId, int outpatientId, int planId) {
		List<UserRecipesPlan> userPlanList = new ArrayList<UserRecipesPlan>();
		try {
			//先删除用户本次门诊的食谱列表
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("hospitalId", hospitalId);
			param.put("userId", userId);
			param.put("outpatientId", outpatientId);
			userRecipesPlanMapper.deleteUserRecipesPlans(outpatientId);
			
			//将对应方案的食谱批量添加到用户食谱
			List<StandardRecipesDetail> recipesList = standardRecipesDetailMapper.findStandardRecipesByPlan(planId);
			if(recipesList != null && recipesList.size() > 0){
				List<UserRecipesPlan> planList = new ArrayList<UserRecipesPlan>();
				for (StandardRecipesDetail standardRecipesDetail : recipesList) {
					UserRecipesPlan plan = new UserRecipesPlan();
					plan.setAddTime(new Date());
					plan.setHospitalId(hospitalId);
					plan.setIntakeCalorie(standardRecipesDetail.getIntakeCalorie());
					plan.setOutpatientId(outpatientId);
					plan.setRecipesMsg(standardRecipesDetail.getRecipesMsg());
					plan.setRecipesName(standardRecipesDetail.getName());
					plan.setUserId(userId);
					planList.add(plan);
				}
				userRecipesPlanMapper.insertBatch(planList);
			}
			
			//查询出用户导入后的食谱列表
			userPlanList = userRecipesPlanMapper.findUserRecipesPlans(outpatientId);
			if(userPlanList != null && userPlanList.size() > 0){
				return userPlanList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}

	/**
	 * 保存用户食谱信息
	 */
	@Override
	public UserRecipesPlan saveUserRecipesPlan(UserRecipesPlan userRecipesPlan) {
		try {
			boolean b = false;
			if(userRecipesPlan.getId()==0 || userRecipesPlan.getId()==null){
				//添加
				userRecipesPlan.setAddTime(new Date());
				b = userRecipesPlanMapper.insert(userRecipesPlan)>0;
			}else{
				//修改
				b = userRecipesPlanMapper.update(userRecipesPlan)>0;
				userRecipesPlan = userRecipesPlanMapper.findById(userRecipesPlan.getId());
			}
			if(b){
				return userRecipesPlan;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 获取某次门诊用户食谱列表
	 */
	@Override
	public List<UserRecipesPlan> findUserRecipesPlansByOutPatientId(int outpatientId) {
		List<UserRecipesPlan> planList = userRecipesPlanMapper.findUserRecipesPlans(outpatientId);
		if(planList != null && planList.size() > 0){
			return planList;
		}
		return null;
	}
	
	/**
	 * h5页面查看用户最新食谱列表
	 */
	@Override
	public Map<String, Object> findUserRecipesList(int hospitalId, int userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hospitalId", hospitalId);
		param.put("userId", userId);
		List<UserRecipesPlan> planList = userRecipesPlanMapper.findUserLeastRecipesPlans(param);
		Map<String, Object> map = new HashMap<String, Object>();
		if(planList != null && planList.size() > 0){
			int outpatientId = planList.get(0).getOutpatientId();
			HospitalOutpatient hospitalOutpatient = hospitalOutpatientMapper.findById(outpatientId);
			if(hospitalOutpatient != null && StringUtils.isNotEmpty(hospitalOutpatient.getDietAdvice())){
				map.put("dietAdvice", hospitalOutpatient.getDietAdvice());
			}
			List<VOUserRecipesList> recpesList = new ArrayList<VOUserRecipesList>();
			for (UserRecipesPlan userRecipesPlan : planList) {
				VOUserRecipesList voUserRecipesList = getVOUserRecipesDetail(userRecipesPlan);
				recpesList.add(voUserRecipesList);
			}
			map.put("recipesList", recpesList);
		}
		return map;
	}

	/**
	 * 组装用户食谱数据
	 * @param userRecipesPlan
	 * @return
	 */
	private VOUserRecipesList getVOUserRecipesDetail(
			UserRecipesPlan userRecipesPlan) {
		VOUserRecipesList voUserRecipesList = new VOUserRecipesList();
		voUserRecipesList.setRecipesId(userRecipesPlan.getId());
		voUserRecipesList.setRecipesName(userRecipesPlan.getRecipesName());
		String recipesMsg = userRecipesPlan.getRecipesMsg();
		if(StringUtils.isNotEmpty(recipesMsg)){
			List<VOUserRecipesDetail> detailList = JSON.parseArray(recipesMsg, VOUserRecipesDetail.class);
			if(detailList != null && detailList.size() > 0){
				List<Integer> ids = new ArrayList<Integer>();
				for (int i = 0; i < detailList.size(); i++) {
					ids.add(detailList.get(i).getFoodId());
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
					for (VOUserRecipesDetail voUserRecipesDetail : detailList) {
						if(foodList != null && foodList.size() > 0){
							for (WeightFood weightFood : foodList) {
								if(weightFood.getId().equals(voUserRecipesDetail.getFoodId())){
									if(StringUtils.isNotEmpty(weightFood.getImg())){
										voUserRecipesDetail.setImg(Consts.WEIGHT_FOOD_IMG_URL+"/"+weightFood.getImg());
									}
								}
							}
						}
					}
					voUserRecipesList.setDetailList(detailList);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return voUserRecipesList;
	}

	/**
	 * 通过食谱id查询用户食谱记录
	 */
	@Override
	public VOUserRecipesList findUserRecipesById(int recipesId) {
		UserRecipesPlan plan = userRecipesPlanMapper.findById(recipesId);
		VOUserRecipesList voUserRecipesList = getVOUserRecipesDetail(plan);
		if(voUserRecipesList != null){
			return voUserRecipesList;
		}
		return null;
	}
	
}
