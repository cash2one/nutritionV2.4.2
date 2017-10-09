package com.jumper.weight.nutrition.recipes.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesDetail;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesPlan;
import com.jumper.weight.nutrition.recipes.mapper.StandardRecipesDetailMapper;
import com.jumper.weight.nutrition.recipes.mapper.StandardRecipesPlanMapper;
import com.jumper.weight.nutrition.recipes.service.StandardRecipesPlanService;

@Service
public class StandardRecipesPlanServiceImpl extends BaseServiceImpl<StandardRecipesPlan> implements StandardRecipesPlanService{
	
	@Autowired
	private StandardRecipesPlanMapper standardRecipesPlanMapper;
	@Autowired
	private StandardRecipesDetailMapper standardRecipesDetailMapper;
	
	@Override
	protected BaseMapper<StandardRecipesPlan> getDao() {
		return standardRecipesPlanMapper;
	}

	/**
	 * 获取食谱方案列表
	 */
	@Override
	public List<StandardRecipesPlan> findStandardRecipesPlans(int hospitalId,
			String keywords) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hospitalId", hospitalId);
		param.put("keywords", keywords);
		List<StandardRecipesPlan> planList = standardRecipesPlanMapper.findStandardRecipesPlans(param);
		if(planList != null && planList.size() > 0){
			return planList;
		}
		return null;
	}

	/**
	 * 删除食谱方案
	 */
	@Override
	public boolean deleteStandardRecipesPlan(int planId) {
		boolean b = false;
		try {
			//删除方案
			standardRecipesPlanMapper.delete(planId);
			//删除方案下的食谱
			standardRecipesDetailMapper.deleteRecipesDetailByPlan(planId);
			b = true;
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
			throw new RuntimeException();
		}
		return b;
	}

	/**
	 * 保存方案
	 */
	@Override
	public StandardRecipesPlan saveStandardRecipesPlan(
			StandardRecipesPlan recipesPlan) {
		boolean b = false;
		try {
			if(recipesPlan.getId()==0 || recipesPlan.getId() == null){
				//添加
				recipesPlan.setAddTime(new Date());
				b = standardRecipesPlanMapper.insert(recipesPlan)>0;
			}else{
				//修改
				b = standardRecipesPlanMapper.update(recipesPlan)>0;
				recipesPlan = standardRecipesPlanMapper.findById(recipesPlan.getId());
			}
			if(b){
				return recipesPlan;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询方案名是否已存在
	 */
	@Override
	public StandardRecipesPlan findStandardRecipesPlanByName(
			StandardRecipesPlan recipesPlan) {
		StandardRecipesPlan plan = standardRecipesPlanMapper.findStandardRecipesPlanByName(recipesPlan);
		if(plan != null){
			return plan;
		}
		return null;
	}
	
	
}
