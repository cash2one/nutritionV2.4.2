package com.jumper.weight.nutrition.recipes.entity;

import java.util.Date;

public class StandardRecipesDetail {
    private Integer id;

    private String name;

    private Integer recipesPlanId;

    private String recipesMsg;

    private Integer intakeCalorie;

    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRecipesPlanId() {
        return recipesPlanId;
    }

    public void setRecipesPlanId(Integer recipesPlanId) {
        this.recipesPlanId = recipesPlanId;
    }

    public String getRecipesMsg() {
        return recipesMsg;
    }

    public void setRecipesMsg(String recipesMsg) {
        this.recipesMsg = recipesMsg == null ? null : recipesMsg.trim();
    }

    public Integer getIntakeCalorie() {
        return intakeCalorie;
    }

    public void setIntakeCalorie(Integer intakeCalorie) {
        this.intakeCalorie = intakeCalorie;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}