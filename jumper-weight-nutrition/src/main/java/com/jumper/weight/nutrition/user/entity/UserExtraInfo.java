package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class UserExtraInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer userId;

    private String loginIp;

    private Date loginTime;

    private Integer mobileType;

    private Integer age;

    private String identification;

    private String realName;

    private String contactPhone;

    private Integer height;

    private Double weight;

    private Date babyBirthday;

    private Byte babySex;

    private Integer commonHospital;

    private Byte currentIdentity;

    private Date lastPeriod;

    private Integer periodCycle;

    private Integer periodDay;

    private Integer isChinaUser;

    private String ogtt;

    private Double hba1c;

    private Double glu;

    private String bp;

    private String bloodFat;

    private Integer baojianhaoHospitalId;

    private Integer jieanState;

    private Date birthday;

    private Integer checkStatus;

    private String bindhospitalname;

    private String idenIcon;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getMobileType() {
        return mobileType;
    }

    public void setMobileType(Integer mobileType) {
        this.mobileType = mobileType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification == null ? null : identification.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(Date babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public Byte getBabySex() {
        return babySex;
    }

    public void setBabySex(Byte babySex) {
        this.babySex = babySex;
    }

    public Integer getCommonHospital() {
        return commonHospital;
    }

    public void setCommonHospital(Integer commonHospital) {
        this.commonHospital = commonHospital;
    }

    public Byte getCurrentIdentity() {
        return currentIdentity;
    }

    public void setCurrentIdentity(Byte currentIdentity) {
        this.currentIdentity = currentIdentity;
    }

    public Date getLastPeriod() {
        return lastPeriod;
    }

    public void setLastPeriod(Date lastPeriod) {
        this.lastPeriod = lastPeriod;
    }

    public Integer getPeriodCycle() {
        return periodCycle;
    }

    public void setPeriodCycle(Integer periodCycle) {
        this.periodCycle = periodCycle;
    }

    public Integer getPeriodDay() {
        return periodDay;
    }

    public void setPeriodDay(Integer periodDay) {
        this.periodDay = periodDay;
    }

    public Integer getIsChinaUser() {
        return isChinaUser;
    }

    public void setIsChinaUser(Integer isChinaUser) {
        this.isChinaUser = isChinaUser;
    }

    public String getOgtt() {
        return ogtt;
    }

    public void setOgtt(String ogtt) {
        this.ogtt = ogtt == null ? null : ogtt.trim();
    }

    public Double getHba1c() {
        return hba1c;
    }

    public void setHba1c(Double hba1c) {
        this.hba1c = hba1c;
    }

    public Double getGlu() {
        return glu;
    }

    public void setGlu(Double glu) {
        this.glu = glu;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp == null ? null : bp.trim();
    }

    public String getBloodFat() {
        return bloodFat;
    }

    public void setBloodFat(String bloodFat) {
        this.bloodFat = bloodFat == null ? null : bloodFat.trim();
    }

    public Integer getBaojianhaoHospitalId() {
        return baojianhaoHospitalId;
    }

    public void setBaojianhaoHospitalId(Integer baojianhaoHospitalId) {
        this.baojianhaoHospitalId = baojianhaoHospitalId;
    }

    public Integer getJieanState() {
        return jieanState;
    }

    public void setJieanState(Integer jieanState) {
        this.jieanState = jieanState;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getBindhospitalname() {
        return bindhospitalname;
    }

    public void setBindhospitalname(String bindhospitalname) {
        this.bindhospitalname = bindhospitalname == null ? null : bindhospitalname.trim();
    }

    public String getIdenIcon() {
        return idenIcon;
    }

    public void setIdenIcon(String idenIcon) {
        this.idenIcon = idenIcon == null ? null : idenIcon.trim();
    }
}