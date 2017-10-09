package com.jumper.weight.nutrition.sport.entity;

import com.jumper.weight.common.base.BaseEntity;

public class SportInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String name;

    private Double calorie;

    private String img;

    private String effect;

    private String attention;

    private Long clickCount;
    
    /**运动代谢当量（消耗量=体重*MET*运动分钟/60）*/
    private Double met;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect == null ? null : effect.trim();
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention == null ? null : attention.trim();
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

	public Double getMet() {
		return met;
	}

	public void setMet(Double met) {
		this.met = met;
	}
    
}