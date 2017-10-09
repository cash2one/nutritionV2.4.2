package com.jumper.weight.nutrition.recipes.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户食谱详情VO(用于h5页面展示)
 * @author gyx
 * @time 2017年5月11日
 */
public class VOUserRecipesList implements Serializable{

	private static final long serialVersionUID = -4381026663470949073L;
	private int recipesId;
	
    private String recipesName;
    
    private List<VOUserRecipesDetail> detailList = new ArrayList<VOUserRecipesDetail>();

	public int getRecipesId() {
		return recipesId;
	}

	public void setRecipesId(int recipesId) {
		this.recipesId = recipesId;
	}

	public String getRecipesName() {
		return recipesName;
	}

	public void setRecipesName(String recipesName) {
		this.recipesName = recipesName;
	}

	public List<VOUserRecipesDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<VOUserRecipesDetail> detailList) {
		this.detailList = detailList;
	}

	
    
}
