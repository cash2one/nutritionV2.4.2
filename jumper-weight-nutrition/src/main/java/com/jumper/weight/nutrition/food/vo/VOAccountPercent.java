package com.jumper.weight.nutrition.food.vo;

import java.io.Serializable;

/**
 * 三大能量物质、蛋白质来源分析(构成比)
 * @author gyx
 * @time 2017年6月22日
 */
public class VOAccountPercent implements Serializable{

	private static final long serialVersionUID = -9219078489340474066L;
	
	/** 蛋白质摄入能量 */
	private Double proIntakeCal;
	/** 蛋白质占比 */
	private Double proPercent;
	/** 脂肪摄入能量 */
	private Double fatIntakeCal;
	/** 脂肪占比 */
	private Double fatPercent;
	/** 碳水化合物摄入能量 */
	private Double carbonIntakeCal;
	/** 碳水化合物占比 */
	private Double carbonPercent;
	/** 优质蛋白占比 */
	private Double highQualityProPercent;
	/** 非优质蛋白占比 */
	private Double nonPrimeProPercent;
	public Double getProIntakeCal() {
		return proIntakeCal;
	}
	public void setProIntakeCal(Double proIntakeCal) {
		this.proIntakeCal = proIntakeCal;
	}
	public Double getProPercent() {
		return proPercent;
	}
	public void setProPercent(Double proPercent) {
		this.proPercent = proPercent;
	}
	public Double getFatIntakeCal() {
		return fatIntakeCal;
	}
	public void setFatIntakeCal(Double fatIntakeCal) {
		this.fatIntakeCal = fatIntakeCal;
	}
	public Double getFatPercent() {
		return fatPercent;
	}
	public void setFatPercent(Double fatPercent) {
		this.fatPercent = fatPercent;
	}
	public Double getCarbonIntakeCal() {
		return carbonIntakeCal;
	}
	public void setCarbonIntakeCal(Double carbonIntakeCal) {
		this.carbonIntakeCal = carbonIntakeCal;
	}
	public Double getCarbonPercent() {
		return carbonPercent;
	}
	public void setCarbonPercent(Double carbonPercent) {
		this.carbonPercent = carbonPercent;
	}
	public Double getHighQualityProPercent() {
		return highQualityProPercent;
	}
	public void setHighQualityProPercent(Double highQualityProPercent) {
		this.highQualityProPercent = highQualityProPercent;
	}
	public Double getNonPrimeProPercent() {
		return nonPrimeProPercent;
	}
	public void setNonPrimeProPercent(Double nonPrimeProPercent) {
		this.nonPrimeProPercent = nonPrimeProPercent;
	}
	
	
	
}
