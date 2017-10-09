package com.jumper.weight.common.util;

import java.util.Comparator;

import com.jumper.weight.nutrition.diet.vo.VOWeightMealsInfo;

/**
 * 根据list中的mealsType正序排
 * @author gyx
 * @time 2016年12月9日
 */
@SuppressWarnings("rawtypes")
public class ComparatorList implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		VOWeightMealsInfo vo1 = (VOWeightMealsInfo) o1;
		VOWeightMealsInfo vo2 = (VOWeightMealsInfo) o2;
		int flag = vo1.getMealsType().compareTo(vo2.getMealsType());
		return flag;
	}
	
}
