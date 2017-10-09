package com.jumper.weight.nutrition.recipes.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.nutrition.recipes.entity.StandardRecipesDetail;
import com.jumper.weight.nutrition.recipes.mapper.StandardRecipesDetailMapper;
import com.jumper.weight.nutrition.recipes.service.StandardRecipesDetailService;

@Service
public class StandardRecipesDetailServiceImpl extends BaseServiceImpl<StandardRecipesDetail> implements StandardRecipesDetailService{
	@Autowired
	private StandardRecipesDetailMapper standardRecipesDetailMapper;
	
	@Override
	protected BaseMapper<StandardRecipesDetail> getDao() {
		return standardRecipesDetailMapper;
	}

	/**
	 * 获取某方案下的食谱列表
	 */
	@Override
	public List<StandardRecipesDetail> findStandardRecipesByPlan(int planId) {
		List<StandardRecipesDetail> recipesList = standardRecipesDetailMapper.findStandardRecipesByPlan(planId);
		if(recipesList != null && recipesList.size() > 0){
			return recipesList;
		}
		return null;
	}

	/**
	 * 保存食谱信息
	 */
	@Override
	public StandardRecipesDetail saveStandardRecipesDetail(
			StandardRecipesDetail recipesDetail) {
		boolean b = false;
		try {
			if(recipesDetail.getId()==0 || recipesDetail.getId()==null){
				//新增食谱
				recipesDetail.setAddTime(new Date());
				b = standardRecipesDetailMapper.insert(recipesDetail)>0;
			}else{
				//修改食谱
				b = standardRecipesDetailMapper.update(recipesDetail)>0;
				recipesDetail = standardRecipesDetailMapper.findById(recipesDetail.getId());
			}
			if(b){
				return recipesDetail;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
