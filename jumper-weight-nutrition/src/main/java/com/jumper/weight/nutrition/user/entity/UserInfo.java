package com.jumper.weight.nutrition.user.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class UserInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    private String mobile;

    private String email;

    private String nickName;

    private Integer country;

    private Integer province;

    private Integer city;

    private String userImg;

    private Date expectedDateOfConfinement;

    private Integer status;

    private Date regTime;

    private String password;

    private Integer isSwitchPushMsg;

    private String openId;

    private Integer userType;

    private Integer gold;

    private Integer pregnantStage;

    private Integer pregnantWeek;

    private String sinaOpenId;

    private String qqOpenId;

    private String weixinOpenId;

    private String medicalNum;
    
    //连表查询新增字段-----start
    private String realName;
    private Integer age;
    private Integer height;
    private Double weight;
    private Integer currentIdentity;
    private Date lastPeriod;
    private Date birthday;
    private Double currentWeight;
    private Date lastWeightTime;
    //连表查询新增字段-----end

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg == null ? null : userImg.trim();
    }

    public Date getExpectedDateOfConfinement() {
        return expectedDateOfConfinement;
    }

    public void setExpectedDateOfConfinement(Date expectedDateOfConfinement) {
        this.expectedDateOfConfinement = expectedDateOfConfinement;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getIsSwitchPushMsg() {
        return isSwitchPushMsg;
    }

    public void setIsSwitchPushMsg(Integer isSwitchPushMsg) {
        this.isSwitchPushMsg = isSwitchPushMsg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getPregnantStage() {
        return pregnantStage;
    }

    public void setPregnantStage(Integer pregnantStage) {
        this.pregnantStage = pregnantStage;
    }

    public Integer getPregnantWeek() {
        return pregnantWeek;
    }

    public void setPregnantWeek(Integer pregnantWeek) {
        this.pregnantWeek = pregnantWeek;
    }

    public String getSinaOpenId() {
        return sinaOpenId;
    }

    public void setSinaOpenId(String sinaOpenId) {
        this.sinaOpenId = sinaOpenId == null ? null : sinaOpenId.trim();
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId == null ? null : qqOpenId.trim();
    }

    public String getWeixinOpenId() {
        return weixinOpenId;
    }

    public void setWeixinOpenId(String weixinOpenId) {
        this.weixinOpenId = weixinOpenId == null ? null : weixinOpenId.trim();
    }

    public String getMedicalNum() {
        return medicalNum;
    }

    public void setMedicalNum(String medicalNum) {
        this.medicalNum = medicalNum == null ? null : medicalNum.trim();
    }

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public Integer getCurrentIdentity() {
		return currentIdentity;
	}

	public void setCurrentIdentity(Integer currentIdentity) {
		this.currentIdentity = currentIdentity;
	}

	public Date getLastPeriod() {
		return lastPeriod;
	}

	public void setLastPeriod(Date lastPeriod) {
		this.lastPeriod = lastPeriod;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(Double currentWeight) {
		this.currentWeight = currentWeight;
	}

	public Date getLastWeightTime() {
		return lastWeightTime;
	}

	public void setLastWeightTime(Date lastWeightTime) {
		this.lastWeightTime = lastWeightTime;
	}

}