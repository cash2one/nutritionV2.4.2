package com.jumper.weight.nutrition.food.vo;

import java.io.Serializable;

/**
 * 来源分析总实体
 * @author gyx
 * @time 2017年6月22日
 */
public class VOTotalAnalyze implements Serializable{

	private static final long serialVersionUID = -2190298836249020636L;
	
	/** 能量、营养素分析 */
	private VONutritionAnalyze voNutritionAnalyze;
	
	/** 三大能量物质、蛋白质来源分析 */
	private VOAccountPercent voAccountPercent;

	public VONutritionAnalyze getVoNutritionAnalyze() {
		return voNutritionAnalyze;
	}

	public void setVoNutritionAnalyze(VONutritionAnalyze voNutritionAnalyze) {
		this.voNutritionAnalyze = voNutritionAnalyze;
	}

	public VOAccountPercent getVoAccountPercent() {
		return voAccountPercent;
	}

	public void setVoAccountPercent(VOAccountPercent voAccountPercent) {
		this.voAccountPercent = voAccountPercent;
	}
	
	
}
