package com.jumper.weight.nutrition.hospital.entity;

import java.util.Date;

import com.jumper.weight.common.base.BaseEntity;

public class HospitalInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**医院名称*/
    private String name;
    /**医院介绍*/
    private String introduction;
    /**医院图片路径*/
    private String imgUrl;
    /**医院地址*/
    private String address;
    /**省级ID*/
    private Integer provinceId;
    /**市级ID*/
    private Integer cityId;
    /**命令键*/
    private String orderKey;
    /**添加时间*/
    private Date addTime;
    /**等级*/
    private Integer level;
    /**上一级医院ID*/
    private Integer parentId;
    /**电话*/
    private String phone;
    /**是否验证有效*/
    private Integer isValid;
    /**不知道什么字段，全是null*/
    private Integer orderBy;
    /**是否开通远程*/
    private Integer isRemote;
    /***/
    private Integer isConsultant;
    /***/
    private Integer remotes;
    /***/
    private Integer consultants;
    /**是否开通体重营养*/
    private Integer isWeight;
    /***/
    private Integer isBlood;
    /**是否开通孕妇学校*/
    private Integer isSchool;
    /**是否开通设备租凭*/
    private Byte isLease;
    /***/
    private Byte isClass;
    /**是否开通孕妇学校视频课程，0：否；1：是*/
    private Byte isVideo;
    /**是否屏蔽手机号码 0:否 1:是*/
    private Byte isMobile;
    /**是否开通线上支付0.未开通，1已开通*/
    private Byte isPayment;
    /**是否是独立医院 0:不是 1:是独立医院 ； 默认为0*/
    private Integer isAutonomy;
    /**是否开通网络诊室 0.否;1.是*/
    private Byte isNetwork;
    /***/
    private String hospitalKey;
    /**医院是否开通一体化服务 0：未开通 1:开通*/
    private Byte isIntegratedService;
    /**是否允许该医院的医生开通胎心判读 1:是 0:否*/
    private Boolean isDoctorNst;


	public Boolean getIsDoctorNst() {
		return isDoctorNst;
	}

	public void setIsDoctorNst(Boolean isDoctorNst) {
		this.isDoctorNst = isDoctorNst;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey == null ? null : orderKey.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(Integer isRemote) {
        this.isRemote = isRemote;
    }

    public Integer getIsConsultant() {
        return isConsultant;
    }

    public void setIsConsultant(Integer isConsultant) {
        this.isConsultant = isConsultant;
    }

    public Integer getRemotes() {
        return remotes;
    }

    public void setRemotes(Integer remotes) {
        this.remotes = remotes;
    }

    public Integer getConsultants() {
        return consultants;
    }

    public void setConsultants(Integer consultants) {
        this.consultants = consultants;
    }

    public Integer getIsWeight() {
        return isWeight;
    }

    public void setIsWeight(Integer isWeight) {
        this.isWeight = isWeight;
    }

    public Integer getIsBlood() {
        return isBlood;
    }

    public void setIsBlood(Integer isBlood) {
        this.isBlood = isBlood;
    }

    public Integer getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(Integer isSchool) {
        this.isSchool = isSchool;
    }

    public Byte getIsLease() {
        return isLease;
    }

    public void setIsLease(Byte isLease) {
        this.isLease = isLease;
    }

    public Byte getIsClass() {
        return isClass;
    }

    public void setIsClass(Byte isClass) {
        this.isClass = isClass;
    }

    public Byte getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Byte isVideo) {
        this.isVideo = isVideo;
    }

    public Byte getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Byte isMobile) {
        this.isMobile = isMobile;
    }

    public Byte getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(Byte isPayment) {
        this.isPayment = isPayment;
    }

    public Integer getIsAutonomy() {
        return isAutonomy;
    }

    public void setIsAutonomy(Integer isAutonomy) {
        this.isAutonomy = isAutonomy;
    }

    public Byte getIsNetwork() {
        return isNetwork;
    }

    public void setIsNetwork(Byte isNetwork) {
        this.isNetwork = isNetwork;
    }

    public String getHospitalKey() {
        return hospitalKey;
    }

    public void setHospitalKey(String hospitalKey) {
        this.hospitalKey = hospitalKey == null ? null : hospitalKey.trim();
    }

    public Byte getIsIntegratedService() {
        return isIntegratedService;
    }

    public void setIsIntegratedService(Byte isIntegratedService) {
        this.isIntegratedService = isIntegratedService;
    }
}