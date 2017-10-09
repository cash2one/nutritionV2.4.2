package com.jumper.weight.nutrition.food.entity;

import java.io.Serializable;
/**
 * 食材entity
 * @author gyx
 * @time 2017年4月26日
 */
public class WeightFood implements Serializable{
	
	private static final long serialVersionUID = 2391067859222156457L;

	private Integer id;

    private String name;

    private Integer categoryId;
    
    private String categoryName;

    private Double calorie;

    private Double protein;

    private Double carbo;

    private Double fat;

    private Double fiber;

    private Double cholesterin;

    private Double na;

    private Double ca;

    private Double fe;

    private Double va;

    private Double vb1;

    private Double vb2;

    private Double ve;

    private Double vc;

    private String nickname;

    private Double giValue;

    private String description;

    private String dietPeople;

    private String img;

    private String compareImg;

    private Integer frequent;

    private Double se;

    private Double p;

    private Double ka;

    private Double mg;

    private Double cu;

    private Double f;

    private Double cr;

    private Double mn;

    private Double mo;

    private Double i;

    private Double vb3;

    private Double vb5;

    private Double vb6;

    private Double vb7;

    private Double vb9;

    private Double vb12;

    private Double vd;

    private Double dha;

    private Double zn;

    private Integer morningFre;

    private Integer lunchFre;

    private Integer dinnerFre;

    private Integer state;

    private String remarks;

    private Integer suitable;

    private String unitRemark;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbo() {
        return carbo;
    }

    public void setCarbo(Double carbo) {
        this.carbo = carbo;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public Double getCholesterin() {
        return cholesterin;
    }

    public void setCholesterin(Double cholesterin) {
        this.cholesterin = cholesterin;
    }

    public Double getNa() {
        return na;
    }

    public void setNa(Double na) {
        this.na = na;
    }

    public Double getCa() {
        return ca;
    }

    public void setCa(Double ca) {
        this.ca = ca;
    }

    public Double getFe() {
        return fe;
    }

    public void setFe(Double fe) {
        this.fe = fe;
    }

    public Double getVa() {
        return va;
    }

    public void setVa(Double va) {
        this.va = va;
    }

    public Double getVb1() {
        return vb1;
    }

    public void setVb1(Double vb1) {
        this.vb1 = vb1;
    }

    public Double getVb2() {
        return vb2;
    }

    public void setVb2(Double vb2) {
        this.vb2 = vb2;
    }

    public Double getVe() {
        return ve;
    }

    public void setVe(Double ve) {
        this.ve = ve;
    }

    public Double getVc() {
        return vc;
    }

    public void setVc(Double vc) {
        this.vc = vc;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Double getGiValue() {
        return giValue;
    }

    public void setGiValue(Double giValue) {
        this.giValue = giValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDietPeople() {
        return dietPeople;
    }

    public void setDietPeople(String dietPeople) {
        this.dietPeople = dietPeople == null ? null : dietPeople.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getCompareImg() {
        return compareImg;
    }

    public void setCompareImg(String compareImg) {
        this.compareImg = compareImg == null ? null : compareImg.trim();
    }

    public Integer getFrequent() {
        return frequent;
    }

    public void setFrequent(Integer frequent) {
        this.frequent = frequent;
    }

    public Double getSe() {
        return se;
    }

    public void setSe(Double se) {
        this.se = se;
    }

    public Double getP() {
        return p;
    }

    public void setP(Double p) {
        this.p = p;
    }

    public Double getKa() {
        return ka;
    }

    public void setKa(Double ka) {
        this.ka = ka;
    }

    public Double getMg() {
        return mg;
    }

    public void setMg(Double mg) {
        this.mg = mg;
    }

    public Double getCu() {
        return cu;
    }

    public void setCu(Double cu) {
        this.cu = cu;
    }

    public Double getF() {
        return f;
    }

    public void setF(Double f) {
        this.f = f;
    }

    public Double getCr() {
        return cr;
    }

    public void setCr(Double cr) {
        this.cr = cr;
    }

    public Double getMn() {
        return mn;
    }

    public void setMn(Double mn) {
        this.mn = mn;
    }

    public Double getMo() {
        return mo;
    }

    public void setMo(Double mo) {
        this.mo = mo;
    }

    public Double getI() {
        return i;
    }

    public void setI(Double i) {
        this.i = i;
    }

    public Double getVb3() {
        return vb3;
    }

    public void setVb3(Double vb3) {
        this.vb3 = vb3;
    }

    public Double getVb5() {
        return vb5;
    }

    public void setVb5(Double vb5) {
        this.vb5 = vb5;
    }

    public Double getVb6() {
        return vb6;
    }

    public void setVb6(Double vb6) {
        this.vb6 = vb6;
    }

    public Double getVb7() {
        return vb7;
    }

    public void setVb7(Double vb7) {
        this.vb7 = vb7;
    }

    public Double getVb9() {
        return vb9;
    }

    public void setVb9(Double vb9) {
        this.vb9 = vb9;
    }

    public Double getVb12() {
        return vb12;
    }

    public void setVb12(Double vb12) {
        this.vb12 = vb12;
    }

    public Double getVd() {
        return vd;
    }

    public void setVd(Double vd) {
        this.vd = vd;
    }

    public Double getDha() {
        return dha;
    }

    public void setDha(Double dha) {
        this.dha = dha;
    }

    public Double getZn() {
        return zn;
    }

    public void setZn(Double zn) {
        this.zn = zn;
    }

    public Integer getMorningFre() {
        return morningFre;
    }

    public void setMorningFre(Integer morningFre) {
        this.morningFre = morningFre;
    }

    public Integer getLunchFre() {
        return lunchFre;
    }

    public void setLunchFre(Integer lunchFre) {
        this.lunchFre = lunchFre;
    }

    public Integer getDinnerFre() {
        return dinnerFre;
    }

    public void setDinnerFre(Integer dinnerFre) {
        this.dinnerFre = dinnerFre;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getSuitable() {
        return suitable;
    }

    public void setSuitable(Integer suitable) {
        this.suitable = suitable;
    }

    public String getUnitRemark() {
        return unitRemark;
    }

    public void setUnitRemark(String unitRemark) {
        this.unitRemark = unitRemark == null ? null : unitRemark.trim();
    }
}