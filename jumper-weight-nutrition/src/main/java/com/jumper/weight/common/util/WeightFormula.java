package com.jumper.weight.common.util;

/**
 * 安全体重范围计算公式
 * @Description TODO
 * @author fangxilin
 * @date 2017年8月16日
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class WeightFormula {
	/**
     * bmi  小于 18.5
     */
    public static final int BMI18P5 = 0;
    /**
     * bmi 18.5 - 24.9
     */
    public static final int BMI18P5_24P9 = 1;
    /**
     * bmi 25 - 29.9
     */
    public static final int BMI25_29P9 = 2;
    /**
     * bmi 大于 30
     */
    public static final int BMI30 = 3;
    public static final int[] WeightSamplesBeforeThriteen = {1, 3};
    private static final int WeekThriteen = 13;
    private static final int TOTALWEEK = 40;
    /**公式1（默认）单胎不同BMI区间对应的增量*/
    public static final double[][] WeightSamples1 = {
	    {12.5, 18},
	    {11.5, 16},
	    {7, 11.5},
	    {5, 9}
    };
    /**公式2（宝安妇幼版）单胎不同BMI区间对应的增量*/
    public static final double[][] WeightSamples2 = {
	    {13, 15},
	    {11.5, 12.5},
	    {10, 12},
	    {8, 11}
    };
    /**公式1,2多胎不同BMI区间对应的增量*/
    public static final double[][] WeightSamples3 = {
    	{12.5, 18},
	    {17, 25},
	    {14, 23},
	    {11, 19}
    };
    /**不同公式对应的bmi区间*/
    public static final double[][] BMI_RANG = {
    	{18.5, 25d, 30d}, 
    	{18.5, 24d, 28d}
    };
    
    /**
	 * 得到安全体重的范围,根据 孕前bmi，孕前体重，怀孕天数
	 * @param initBmi 孕前bmi
	 * @param initWeight 孕前体重
	 * @param totalday 怀孕天数
	 * @param formula 安全体重范围计算公式  0：默认，1：宝安妇幼版公式
	 * @param pregnantType 怀孕类型  0：单胎  1：多胎
	 * @return safeWeight[0]最小增长值, safeWeight[1]最大增长值
	 */
	public static double[] getSafeWeight(double initBmi, double initWeight, int totalday, int formula, int pregnantType) {
		double[] safeWeight = new double[2];
        double maxValue, minValue;
        
        int week = totalday / 7 ;
        int day = totalday % 7;
        
        int minThriteen = WeightSamplesBeforeThriteen[0];//1
        int maxThriteen = WeightSamplesBeforeThriteen[1];//3
        //这里是按公斤来算，
        if (week <= WeekThriteen) {
            minValue = totalday * minThriteen * 1.0d   / (WeekThriteen * 7 + 6 ) + initWeight;
            maxValue = totalday * maxThriteen * 1.0d / (WeekThriteen * 7 + 6 ) + initWeight;

        } else {
        	//求出对应bmi的体重增长区间量
        	double[] weightSample = getWeightSample(initBmi, formula, pregnantType);
            double minAllWeek = weightSample[0];
            double maxAllWeek = weightSample[1];
            int dayLast = TOTALWEEK * 7  - (WeekThriteen * 7 + 6);
            int weekDays = week * 7 - (WeekThriteen * 7 + 6) + day;
            minValue = weekDays * (minAllWeek - minThriteen) / dayLast  + minThriteen + initWeight;
            maxValue = weekDays * (maxAllWeek - maxThriteen) /dayLast  + maxThriteen +initWeight;
        }
        //保留一位小数
        safeWeight[0] = FunctionUtils.setDecimal(minValue, 1);
        safeWeight[1] = FunctionUtils.setDecimal(maxValue, 1);
        return safeWeight;
	}
	 
	/**
	 * 获取bmi对应的增量区间
	 * @createAuthor fangxilin
	 * @param bmi
	 * @param formula 安全体重范围计算公式  0：默认，1：宝安妇幼版公式
	 * @param pregnantType 怀孕类型  0：单胎  1：多胎
	 * @return
	 */
    private static double[] getWeightSample(Double bmi, int formula, int pregnantType) {
    	double[] currRang;//对应的bmi范围区间
    	double[][] weightSamples;//对应体重增量
    	if (formula == 0 && pregnantType == 0) {
    		currRang = BMI_RANG[0];
    		weightSamples = WeightSamples1;
		} else if (formula == 1 && pregnantType == 0) {
			currRang = BMI_RANG[1];
    		weightSamples = WeightSamples2;
		} else {
			currRang = BMI_RANG[0];
			weightSamples = WeightSamples3;
		}
    	
    	//求出给定bmi对应的状态
    	int bmiStatus = 1;
    	if (bmi < currRang[0]) {
    		bmiStatus = BMI18P5;
        } else if (bmi >= currRang[0] && bmi < currRang[1]) {
        	bmiStatus = BMI18P5_24P9;
        } else if (bmi >= currRang[1] && bmi < currRang[2]) {
        	bmiStatus = BMI25_29P9;
        } else {
        	bmiStatus = BMI30;
        }
    	
    	return weightSamples[bmiStatus];
	}
}
