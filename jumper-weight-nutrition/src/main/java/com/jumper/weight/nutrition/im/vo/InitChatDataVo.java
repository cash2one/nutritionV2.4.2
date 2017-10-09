/**
 * 
 */
package com.jumper.weight.nutrition.im.vo;

/**
 * 内部系统嵌套聊天页面参数实体类
 * 
 * @ClassName: InitChatDataReq
 * @author huangzg 2017年1月4日 下午5:34:00
 */
public class InitChatDataVo {

	/** 发送者UserId */
	private String fromUserId;
	/** 发送者昵称 */
	private String fromNickName;
	/** 发送者头像Url */
	private String fromHeadUrl;
	/** 发送者账户类型 帐号类型 1：医生 2：患者/用户 3：其他 */
	private Integer fromUserType;
	/** 接收者UserId */
	private String toUserId;
	/** 接收者昵称 */
	private String toNickName;
	/** 接收者头像Url */
	private String toHeadUrl;
	/** 接收者账户类型 帐号类型 1：医生 2：患者/用户 3：其他 */
	private Integer toUserType;
	/** 用户类型 1:天使医生 2：建海 3：弘扬 */
	private Integer userType;
	/** 聊天界面背景色 1：蓝色 2：粉色 3：白色 */
	private Integer color;
	/** 业务APPID */
	private Integer busCode;
	/** 话题ID */
	private String consultantId;
	
	public InitChatDataVo() {
		super();
	}

	public InitChatDataVo(String fromUserId, String fromNickName,
			String fromHeadUrl, Integer fromUserType, String toUserId,
			String toNickName, String toHeadUrl, Integer toUserType,
			Integer userType, Integer color, Integer busCode,
			String consultantId) {
		super();
		this.fromUserId = fromUserId;
		this.fromNickName = fromNickName;
		this.fromHeadUrl = fromHeadUrl;
		this.fromUserType = fromUserType;
		this.toUserId = toUserId;
		this.toNickName = toNickName;
		this.toHeadUrl = toHeadUrl;
		this.toUserType = toUserType;
		this.userType = userType;
		this.color = color;
		this.busCode = busCode;
		this.consultantId = consultantId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromNickName() {
		return fromNickName;
	}

	public void setFromNickName(String fromNickName) {
		this.fromNickName = fromNickName;
	}

	public String getFromHeadUrl() {
		return fromHeadUrl;
	}

	public void setFromHeadUrl(String fromHeadUrl) {
		this.fromHeadUrl = fromHeadUrl;
	}

	public Integer getFromUserType() {
		return fromUserType;
	}

	public void setFromUserType(Integer fromUserType) {
		this.fromUserType = fromUserType;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getToNickName() {
		return toNickName;
	}

	public void setToNickName(String toNickName) {
		this.toNickName = toNickName;
	}

	public String getToHeadUrl() {
		return toHeadUrl;
	}

	public void setToHeadUrl(String toHeadUrl) {
		this.toHeadUrl = toHeadUrl;
	}

	public Integer getToUserType() {
		return toUserType;
	}

	public void setToUserType(Integer toUserType) {
		this.toUserType = toUserType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Integer getBusCode() {
		return busCode;
	}

	public void setBusCode(Integer busCode) {
		this.busCode = busCode;
	}

	public String getConsultantId() {
		return consultantId;
	}

	public void setConsultantId(String consultantId) {
		this.consultantId = consultantId;
	}

}
