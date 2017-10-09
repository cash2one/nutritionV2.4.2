package com.jumper.weight.nutrition.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jumper.common.service.sms.SMSService;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.CheckIdCardNumber;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.HttpClient;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.common.util.SignatureUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.nutrition.hospital.entity.HospitalInfo;
import com.jumper.weight.nutrition.hospital.mapper.HospitalInfoMapper;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.entity.PregnantHealthySetting;
import com.jumper.weight.nutrition.user.entity.UserExtraInfo;
import com.jumper.weight.nutrition.user.entity.UserInfo;
import com.jumper.weight.nutrition.user.mapper.HospitalUserInfoMapper;
import com.jumper.weight.nutrition.user.mapper.PregnantHealthySettingMapper;
import com.jumper.weight.nutrition.user.mapper.UserInfoMapper;
import com.jumper.weight.nutrition.user.service.HospitalUserInfoService;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private HospitalUserInfoMapper hospitalUserInfoMapper;
	@Autowired
	private PregnantHealthySettingMapper pregnantHealthySettingMapper;
	@Autowired
	private SMSService sMSService;
	@Autowired
	private HospitalUserInfoService hospitalUserInfoService;
	@Autowired
	private HospitalInfoMapper hospitalInfoMapper;
	
	@Override
	protected BaseMapper<UserInfo> getDao() {
		return userInfoMapper;
	}
	
	@Override
	public VoUserInfo findVoUserByUId(int userId, Integer hospitalId) {
		//UserInfo userInfo = userInfoMapper.findUserByUserId(userId);
		HospitalUserInfo userInfo = hospitalUserInfoMapper.findHospUserByUIdHospId(userId, hospitalId);
		if (userInfo == null) {
			return null;
		}
		VoUserInfo vo = getVoUserInfo(userInfo);
		return vo;
	}
	
	@Override
	public List<VoUserInfo> listVoUserByUId(List<Integer> userIds, Integer hospitalId) {
		List<VoUserInfo> voList = new ArrayList<VoUserInfo>();
		if (ArrayUtils.isEmpty(userIds)) {
			return voList;
		}
		List<HospitalUserInfo> hospUserList = hospitalUserInfoMapper.listHospUserByUIdHospId(userIds, hospitalId);
		for (HospitalUserInfo user : hospUserList) {
			VoUserInfo vo = getVoUserInfo(user);
			voList.add(vo);
		}
		return voList;
	}
	
	@Override
	public VoUserInfo findVoUserByMobile(String mobile, Integer hospitalId) {
		//UserInfo userInfo = userInfoMapper.findByMobile(mobile);
		HospitalUserInfo userInfo = hospitalUserInfoMapper.findHospUserByMobHosp(mobile, hospitalId);
		if (userInfo == null) {
			return null;
		}
		return getVoUserInfo(userInfo);
	}
	
	@Override
	public VoUserInfo findTsUserByMobile(String mobile) {
		UserInfo userInfo = userInfoMapper.findByMobile(mobile);
		VoUserInfo data = getVoUserInfo(userInfo);
		return data;
	}
	
	private VoUserInfo getVoUserInfo(UserInfo userInfo) {
		if (userInfo == null) {
			return null;
		}
		VoUserInfo vo = new VoUserInfo();
		vo.setUserId(userInfo.getId());
		vo.setMobile(userInfo.getMobile());
		vo.setRealName(userInfo.getRealName());
		vo.setBirthday(TimeUtils.dateFormatToString(userInfo.getBirthday(), Const.YYYYMMDD));
		Integer age = (userInfo.getBirthday() != null) ? 
			TimeUtils.getTowDateMinusYear(new Date(), userInfo.getBirthday()) : userInfo.getAge();
		vo.setAge(age);
		vo.setWeight(userInfo.getWeight());
		vo.setHeight(userInfo.getHeight());
		vo.setLastPeriod(TimeUtils.dateFormatToString(userInfo.getLastPeriod(), Const.YYYYMMDD));
		vo.setExpectedDate(TimeUtils.dateFormatToString(userInfo.getExpectedDateOfConfinement(), Const.YYYYMMDD));
		vo.setCurrentIdentity(userInfo.getCurrentIdentity());
		int[] pweek = FunctionUtils.calPregnantWeek(new Date(), userInfo.getExpectedDateOfConfinement());
		vo.setPregnantWeek(pweek);
		String pregnantStage = FunctionUtils.calPregnantStage(pweek[2], userInfo.getCurrentIdentity());
		vo.setPregnantStage(pregnantStage);
		double bmi = FunctionUtils.getBMI(userInfo.getHeight(), userInfo.getWeight());
		vo.setBmi(bmi);
		vo.setWeightStatus(FunctionUtils.getStatusByBmi(bmi));
		return vo;
	}
	
	private VoUserInfo getVoUserInfo(HospitalUserInfo userInfo) {
		if (userInfo == null) {
			return null;
		}
		VoUserInfo vo = new VoUserInfo();
		vo.setUserId(userInfo.getUserId());
		vo.setMobile(userInfo.getMobile());
		vo.setRealName(userInfo.getRealName());
		vo.setBirthday(TimeUtils.dateFormatToString(userInfo.getBirthday(), Const.YYYYMMDD));
		Integer age = TimeUtils.getTowDateMinusYear(new Date(), userInfo.getBirthday());
		vo.setAge(age);
		vo.setWeight(userInfo.getWeight());
		vo.setHeight(userInfo.getHeight());
		vo.setExpectedDate(TimeUtils.dateFormatToString(userInfo.getExpectedDate(), Const.YYYYMMDD));
		Date lasPeriod = FunctionUtils.getLastPeriodByExp(userInfo.getExpectedDate());//末次月经
		vo.setLastPeriod(TimeUtils.dateFormatToString(lasPeriod, Const.YYYYMMDD));
		vo.setCurrentIdentity(0);//怀孕状态0：怀孕中，1：已有宝宝
		int[] pweek = FunctionUtils.calPregnantWeek(new Date(), userInfo.getExpectedDate());
		vo.setPregnantWeek(pweek);
		String pregnantStage = FunctionUtils.calPregnantStage(pweek[2], 0);
		vo.setPregnantStage(pregnantStage);
		double bmi = FunctionUtils.getBMI(userInfo.getHeight(), userInfo.getWeight());
		vo.setBmi(bmi);
		vo.setWeightStatus(FunctionUtils.getStatusByBmi(bmi));
		//设置医院用户信息
		vo.setPregnantType(userInfo.getPregnantType());
		vo.setOutpatientNum(userInfo.getOutpatientNum());
		vo.setHealthNum(userInfo.getHealthNum());
		vo.setIsDiabetes(userInfo.getIsDiabetes());
		return vo;
	}

	@Override
	public int addOrUpdteUser(VoUserInfo vo) {
		UserInfo user = new UserInfo();
		Date expDate = TimeUtils.stringFormatToDate(vo.getExpectedDate(), Const.YYYYMMDD);
		user.setExpectedDateOfConfinement(expDate);
		
		UserExtraInfo userExt = new UserExtraInfo();
		Date birth = TimeUtils.stringFormatToDate(vo.getBirthday(), Const.YYYYMMDD);
		userExt.setUserId(vo.getUserId());
		userExt.setBirthday(birth);
		userExt.setAge(TimeUtils.getTowDateMinusYear(new Date(), birth));
		userExt.setWeight(vo.getWeight());
		userExt.setHeight(vo.getHeight());
		userExt.setRealName(vo.getRealName());
		Date lastPeriod = FunctionUtils.getLastPeriodByExp(TimeUtils.stringFormatToDate(vo.getExpectedDate(), Const.YYYYMMDD));
		userExt.setLastPeriod(lastPeriod);
		Integer userId = vo.getUserId();
		try {
			/*if (vo.getUserId() != null) {
				//更新
				if (vo.getExpectedDate() != null) {
					user.setId(vo.getUserId());
					userInfoMapper.update(user);
				}
				userExtraInfoMapper.update(userExt);
			} else {
				//添加
				user.setMobile(vo.getMobile());
				String nickName = "天使用户" + RandomStringUtils.random(8, false, true);
				user.setNickName(nickName);
				user.setStatus(1);
				user.setRegTime(new Date());
				user.setIsSwitchPushMsg(1);
				//String pass = vo.getBirthday().replace("-", "");
				String pass = "123456";//默认的初始密码
				user.setPassword(MD5EncryptUtils.getMd5Value(pass));
				Integer num = userInfoMapper.insert(user);
				if (num != 1) {
					logger.info("---------插入UserInfo信息失败！");
					return 0;
				}
				userExt.setUserId(user.getId());
				userExt.setCurrentIdentity((byte) 0);
				userExt.setContactPhone(vo.getMobile());
				userExt.setCommonHospital(vo.getHospitalId());
				userExtraInfoMapper.insert(userExt);
				//插入默认的pregnant_healthy_setting集合
				insertDefaultHealthySet(user.getId());
				//调取发送短信的接口
				String content = String.format(Const.NEW_BOOKBUILD, vo.getMobile(), pass);
				//CommonReturnMsg retMsg = sMSService.sengSMSMsg("体重营养新建档", vo.getMobile(), content);
				Map<String, String> params = SignatureUtils.getSignatureParams(vo.getHospitalId(), vo.getMobile(), content);
				String sign = SignatureUtils.getSignature(params);
				CommonReturnMsg retMsg = sMSService.sendSMSByHb(vo.getHospitalId().longValue(), Long.valueOf(vo.getMobile()), content, sign);
				logger.info("----------建档发送短信的结果：" + retMsg.getMsgbox());
				userId = user.getId();
			}*/
			if (vo.getUserId() == null) {
				//添加用户
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("mobile", vo.getMobile());
				params.put("password", "123456");
				params.put("expect_date", vo.getExpectedDate());
				params.put("real_name", vo.getRealName());
				params.put("current_identity", 0);
				params.put("hospital_id", vo.getHospitalId());
				params.put("mom_birthday", vo.getBirthday());
				HttpClient client = new HttpClient(Consts.USER_PORTAL_URL+"/registerUser", params, HttpClient.TYPE_JSON);
				String result = client.post();
				if(result != null){
					ReturnMsg ret = JSON.parseObject(result, ReturnMsg.class);
					if(ret.getMsg() == 1){
						Map<String, Object> map = (Map<String, Object>) ret.getData();
						userId = (Integer) map.get("userId");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//插入或更新hospital_user_info
		HospitalUserInfo hospUser = new HospitalUserInfo();
		hospUser.setHospitalId(vo.getHospitalId());
		hospUser.setUserId(userId);
		hospUser.setOutpatientNum(vo.getOutpatientNum());
		hospUser.setPregnantType(vo.getPregnantType());
		hospUser.setAddTime(new Date());
		//设置基本信息
		hospUser.setMobile(vo.getMobile());
		hospUser.setRealName(vo.getRealName());
		hospUser.setHeight(vo.getHeight());
		hospUser.setWeight(vo.getWeight());
		hospUser.setExpectedDate(expDate);
		hospUser.setBirthday(birth);
		hospUser.setHealthNum(vo.getHealthNum());
		hospUser.setIsDiabetes(vo.getIsDiabetes());
		hospitalUserInfoService.addOrUpdateHospUser(hospUser);
		return userId;
	}

	@Override
	public boolean insertDefaultHealthySet(int userId) {
		int[] project = { 1, 2, 3, 5, 6, 7, 8, 10 };
		List<PregnantHealthySetting> plist = new ArrayList<PregnantHealthySetting>();
		for (int i = 0; i < project.length; i++) {
			PregnantHealthySetting pregnantHealthySetting = new PregnantHealthySetting();
			pregnantHealthySetting.setUserId(userId);
			pregnantHealthySetting.setProject(project[i]);
			pregnantHealthySetting.setState(0);
			pregnantHealthySetting.setAddTime(new Date());
			plist.add(pregnantHealthySetting);
		}
		Integer num = 0;
		try {
			num = pregnantHealthySettingMapper.insertBatch(plist);
			if (num == 0) {
				logger.info("----------插入默认健康设置失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (num > 0);
	}

	@Override
	public boolean sendMsgBatch(List<String> mobileList, int hospitalId, String content) {
		HospitalInfo hosp = hospitalInfoMapper.findById(hospitalId);
		String hospitalName = (hosp != null) ? hosp.getName() : "天使医生";
		content = "【" + hospitalName + "】" + content;
		try {
			for (String mobile : mobileList) {
				//调取发送短信的接口
				Map<String, String> params = SignatureUtils.getSignatureParams(hospitalId, mobile, content);
				String sign = SignatureUtils.getSignature(params);
				sMSService.sendSMSByHb(Long.valueOf(hospitalId), Long.valueOf(mobile), content, sign);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public VoUserInfo findWeiTsUserByMob(String mobile, Integer hospitalId) {
		VoUserInfo voUser = findVoUserByMobile(mobile, hospitalId);
		if (voUser != null) {
			return voUser;
		}
		voUser = findTsUserByMobile(mobile);//天使用户信息
		return voUser;
	}
	
	/**
	 * 转换宝安妇幼用户信息
	 * @createTime 2017-7-27,下午3:02:53
	 * @createAuthor fangxilin
	 * @param retData
	 * @return
	 */
	private VoUserInfo convertHttpUser(Map<String, Object> retData) {
		VoUserInfo data = new VoUserInfo();
		Map<String, Object> otherInfo = (Map<String, Object>) retData.get("otherInfo");
		if ("居民身份证".equals(otherInfo.get("1000CredType")) && !"".equals(otherInfo.get("1001CredNo"))) {
			CheckIdCardNumber idCard = new CheckIdCardNumber((String) otherInfo.get("1001CredNo"));
			Date birthday = TimeUtils.stringFormatToDate(idCard.getBirthday(), Const.YYYYMMDD1);
			String birth = TimeUtils.dateFormatToString(birthday, Const.YYYYMMDD);
			data.setBirthday(birth);
			data.setAge(TimeUtils.getTowDateMinusYear(new Date(), birthday));
		}
		data.setRealName((String) retData.get("stuName"));
		double weight = Double.valueOf((String) otherInfo.get("Item48"));
		data.setWeight(weight);
		int height = Integer.valueOf((String) otherInfo.get("Item47"));
		data.setHeight(height);
		data.setLastPeriod((String) retData.get("stuLastMenstrualPeriod"));
		Date expDate = FunctionUtils.getPregancyDay((String) retData.get("stuLastMenstrualPeriod"));
		data.setExpectedDate(TimeUtils.dateFormatToString(expDate, Const.YYYYMMDD));
		int[] pweek = FunctionUtils.calPregnantWeek(new Date(), expDate);
		data.setPregnantWeek(pweek);
		Double bmi = FunctionUtils.getBMI(height, weight);
		data.setBmi(bmi);
		return data;
	}

	@Override
	public VoUserInfo findWeiTsUserByUId(int userId, Integer hospitalId) {
		VoUserInfo data = findVoUserByUId(userId, hospitalId);
		if (data != null) {
			return data;
		}
		UserInfo userInfo = userInfoMapper.findUserByUserId(userId);
		data = getVoUserInfo(userInfo);
		return data;
	}
}
