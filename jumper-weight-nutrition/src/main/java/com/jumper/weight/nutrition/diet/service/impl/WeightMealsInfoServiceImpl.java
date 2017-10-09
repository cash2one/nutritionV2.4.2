package com.jumper.weight.nutrition.diet.service.impl;

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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.HttpClient;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.diet.entity.WeightMealsInfo;
import com.jumper.weight.nutrition.diet.mapper.WeightMealsInfoMapper;
import com.jumper.weight.nutrition.diet.service.WeightMealsInfoService;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;
import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfoPage;
import com.jumper.weight.nutrition.food.entity.WeightFood;
import com.jumper.weight.nutrition.report.mapper.WeightReportMapper;

@Service
public class WeightMealsInfoServiceImpl extends BaseServiceImpl<WeightMealsInfo> implements WeightMealsInfoService{
	
	@Autowired
	private WeightMealsInfoMapper weightMealsInfoMapper;
	@Autowired
	private WeightReportMapper weightReportMapper;
	
	@Override
	protected BaseMapper<WeightMealsInfo> getDao() {
		return weightMealsInfoMapper;
	}

	/**
	 * 条件查询用户饮食记录
	 */
	@Override
	public List<VOWeightMealsInfo> findUserMealsInfoList(int userId,
			String startTime, String endTime, int mealsType) {
		if(StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)){
			startTime = TimeUtils.dateFormatToString(TimeUtils.getDateByDaysLate(-1, new Date()), Const.YYYYMMDD);
			endTime = startTime;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("mealsType", mealsType);
		List<WeightMealsInfo> mealsInfoList = weightMealsInfoMapper.findUserMealsInfoList(map);
		List<VOWeightMealsInfo> voMealsInfoList = getVOMealsInfoList(mealsInfoList);
		if(voMealsInfoList != null && voMealsInfoList.size() > 0){
			return voMealsInfoList;
		}
		return null;
	}
	
	/**
	 * 查询用户饮食记录分页数据
	 */
	@Override
	public PageInfo findAllUserMealsInfoList(int userId,
			String startTime, String endTime, int pageIndex, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		PageHelper.startPage(pageIndex, pageSize);
		List<String> dateList =  weightMealsInfoMapper.findUserPageEatDates(map);
		PageInfo pageInfo = new PageInfo(dateList);
		if(dateList != null && dateList.size() > 0){
			List<VOWeightMealsInfoPage> pageList = new ArrayList<VOWeightMealsInfoPage>();
			for (String eatDate : dateList) {
				VOWeightMealsInfoPage voMealsInfoPage = new VOWeightMealsInfoPage();
				voMealsInfoPage.setEatDate(eatDate);
				map.put("eatDate", eatDate);
				List<WeightMealsInfo> mealsInfoList = weightMealsInfoMapper.findAllUserMealsInfoList(map);
				List<VOWeightMealsInfo> voMealsInfoList = getVOMealsInfoList(mealsInfoList);
				if(voMealsInfoList != null && voMealsInfoList.size() > 0){
					voMealsInfoPage.setInfoList(voMealsInfoList);
				}
				pageList.add(voMealsInfoPage);
			}
			pageInfo.setList(pageList);
		}
		return pageInfo;
	}
	
	/**
	 * 保存用户饮食记录
	 */
	@Override
	public boolean saveUserMealsInfo(List<WeightMealsInfo> mealsInfoList,
			int userId, String eatDate) {
		if(mealsInfoList != null && mealsInfoList.size() > 0){
			List<WeightMealsInfo> addMealsInfoList = new ArrayList<WeightMealsInfo>();
			List<WeightMealsInfo> updateMealsInfoList = new ArrayList<WeightMealsInfo>();
			for (WeightMealsInfo weightMealsInfo : mealsInfoList) {
				//所输克数对应的食材的热量值
				float calorie = (weightMealsInfo.getCalorie()/100)*weightMealsInfo.getFoodWeight();
				weightMealsInfo.setCalorie(calorie);
				weightMealsInfo.setUserId(userId);
				weightMealsInfo.setEatDate(TimeUtils.convertToDate(eatDate));
				//查询该条饮食记录是否已经存在
				WeightMealsInfo userMealsInfo = weightMealsInfoMapper.findUserMealsInfoByConds(weightMealsInfo);
				if(userMealsInfo != null){
					//存在但是克数或食材名不一致就修改
					weightMealsInfo.setId(userMealsInfo.getId());
					if((!weightMealsInfo.getFoodName().equals(userMealsInfo.getFoodName()))||(!weightMealsInfo.getFoodWeight().equals(userMealsInfo.getFoodWeight()))){
						updateMealsInfoList.add(weightMealsInfo);
					}
				}else{
					//不存在就添加(添加时间)
					weightMealsInfo.setAddTime(new Date());
					weightMealsInfo.setSugarPoint(0F);
					weightMealsInfo.setRecordType((byte) 1);
					addMealsInfoList.add(weightMealsInfo);
				}
			}
			try {
				if(addMealsInfoList != null && addMealsInfoList.size() > 0){
					//批量添加
					weightMealsInfoMapper.insertBatch(addMealsInfoList);
				}
				if(updateMealsInfoList != null && updateMealsInfoList.size() > 0){
					//批量修改
					weightMealsInfoMapper.updateBatch(updateMealsInfoList);
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return false;
	}


	/**
	 * 组装用户饮食记录
	 * @param mealsInfoList 饮食记录
	 * @param foodList 食材信息
	 * @return
	 */
	public List<VOWeightMealsInfo> getVOMealsInfoList(
			List<WeightMealsInfo> mealsInfoList) {
		if(mealsInfoList != null && mealsInfoList.size() > 0){
			List<Integer> ids = new ArrayList<Integer>();
			for (int i = 0; i < mealsInfoList.size(); i++) {
				ids.add(mealsInfoList.get(i).getFoodId());
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
				List<VOWeightMealsInfo> voMealsInfoList = new ArrayList<VOWeightMealsInfo>();
				if(mealsInfoList != null && mealsInfoList.size() > 0){
					for (WeightMealsInfo weightMealsInfo : mealsInfoList) {
						VOWeightMealsInfo voWeightMealsInfo = new VOWeightMealsInfo();
						voWeightMealsInfo.setId(weightMealsInfo.getId());
						voWeightMealsInfo.setUserId(weightMealsInfo.getUserId());
						voWeightMealsInfo.setMealsType(weightMealsInfo.getMealsType());
						voWeightMealsInfo.setAddTime(weightMealsInfo.getAddTime());
						voWeightMealsInfo.setEatDate(weightMealsInfo.getEatDate());
						voWeightMealsInfo.setFoodId(weightMealsInfo.getFoodId());
						voWeightMealsInfo.setFoodName(weightMealsInfo.getFoodName());
						voWeightMealsInfo.setFoodWeight(weightMealsInfo.getFoodWeight());
						voWeightMealsInfo.setUnitRemark("");
						voWeightMealsInfo.setTotalCalorie(Double.valueOf(weightMealsInfo.getCalorie()));
						if(foodList != null && foodList.size() > 0){
							for (WeightFood weightFood : foodList) {
								if(weightFood.getId().equals(weightMealsInfo.getFoodId())){
									voWeightMealsInfo.setUnitRemark(weightFood.getUnitRemark());
									voWeightMealsInfo.setCalorie(weightFood.getCalorie());
								}
							}
						}
						voMealsInfoList.add(voWeightMealsInfo);
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
	 * 删除用户一整天的饮食记录
	 */
	@Override
	public boolean deleteUserMealsInfo(int userId, String eatDate) {
		boolean b = false;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			param.put("eatDate", eatDate);
			weightMealsInfoMapper.deleteUserMealsInfo(param);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 重命名用户饮食记录日期
	 */
	@Override
	public boolean reNameUserMealsInfo(int userId, String oldDate,
			String newDate) {
		boolean b = false;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			param.put("oldDate", oldDate);
			param.put("newDate", newDate);
			weightMealsInfoMapper.reNameUserMealsInfo(param);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 查询用户近7天的饮食记录日期列表
	 */
	@Override
	public List<String> findUserLatestSevenDays(int userId) {
		List<String> dateList = weightMealsInfoMapper.findUserLatestSevenDays(userId);
		if(dateList != null && dateList.size() > 0){
			return dateList;
		}
		return null;
	}

	@Override
	public int findCount(int userId, String startTime, String endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		int count = weightMealsInfoMapper.findCount(param);
		return count;
	}

	/**
	 * 获取近7天用户饮食记录总食物个数
	 */
	@Override
	public int findFoodCount(int userId, List<String> dateList) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("dateList", dateList);
		int foodCount = weightMealsInfoMapper.findFoodCount(param);
		return foodCount;
	}

}
