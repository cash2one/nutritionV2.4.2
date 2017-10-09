package com.jumper.weight.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jumper.weight.common.enums.WeightExceptionType;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.common.util.WeightFormula;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalSettingService;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.entity.UserInfo;
import com.jumper.weight.nutrition.user.entity.WeightUserOrder;
import com.jumper.weight.nutrition.user.mapper.HospitalUserInfoMapper;
import com.jumper.weight.nutrition.user.mapper.WeightUserOrderMapper;

/**
 * 体重营养用户定时器任务
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-5
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
@Component
public class WeightUserOrderTask {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WeightUserOrderMapper weightUserOrderMapper;
	@Autowired
	private HospitalUserInfoMapper hospitalUserInfoMapper;
	@Autowired
	private WeightHospitalSettingService weightHospitalSettingService;
	
	/**
	 * 同步用户信息到排序表以及医院用户表
	 * @createTime 2017-5-6,下午8:49:42
	 * @createAuthor fangxilin
	 */
	@Scheduled(cron="0 0/3 * * * ?")   //每3分钟执行一次
	public void synchorWeightUser() {
		try {
			//List<UserWeightRecord> weightList = userWeightRecordMapper.listHospUserWeights();
			
			List<HospitalUserInfo> physicalList = hospitalUserInfoMapper.listHospUserPhysical();
			if (ArrayUtils.isEmpty(physicalList)) {
				logger.info("--------医院用户信息列表为空");
				return;
			}
			List<WeightUserOrder> addList = new ArrayList<WeightUserOrder>();
			List<WeightUserOrder> updList = new ArrayList<WeightUserOrder>();
			List<WeightUserOrder> orderList = weightUserOrderMapper.listAll();
			WeightUserOrder bean = null;
			
			for (HospitalUserInfo record : physicalList) {
				bean = new WeightUserOrder();
				WeightUserOrder updBean = null;//是否更新的对象
				for (WeightUserOrder orUser : orderList) {
					if (orUser.getUserId().equals(record.getUserId())) {
						updBean = orUser;
						break;
					}
				}
				
				//设置未称重类型
				int weightType = 0;
				int day = TimeUtils.getTowDateMinusDay(new Date(), record.getLastWeightTime());
				if (day <= WeightExceptionType.neven.getDay()) {
					weightType = WeightExceptionType.neven.getType();
				} else if (day < WeightExceptionType.normal.getDay()) {
					weightType = WeightExceptionType.normal.getType();
				} else if (day < WeightExceptionType.three.getDay()) {
					weightType = WeightExceptionType.three.getType();
				} else if (day < WeightExceptionType.five.getDay()) {
					weightType = WeightExceptionType.five.getType();
				} else if (day < WeightExceptionType.seven.getDay()) {
					weightType = WeightExceptionType.seven.getType();
				} else {
					weightType = WeightExceptionType.fourteen.getType();
				}
				boolean f1 = false, f2 = false, f3 = false, f4 = false;
				if (updBean != null) {
					f1 = record.getCurrentWeight() == null || updBean.getCurrentWeight().equals(record.getCurrentWeight());
					f2 = updBean.getWeightExceptionType() == weightType;
					f3 = record.getLastWeightTime() == null || updBean.getLastWeightTime().compareTo(record.getLastWeightTime()) == 0;
					f4 = record.getCurrentSugar() == null || updBean.getCurrentSugar().equals(record.getCurrentSugar());
					if (f1 && f2 && f3 && f4) {
						continue;
					}
					bean.setId(updBean.getId());
				}
				bean.setCurrentWeight((f1) ? null : record.getCurrentWeight());
				bean.setWeightExceptionType((f2) ? null : weightType);//取枚举值未称重类型
				bean.setLastWeightTime((f3) ? null : record.getLastWeightTime());
				bean.setCurrentSugar((f4) ? null : record.getCurrentSugar());
				if (updBean != null) {
					updList.add(bean);
				} else {
					initUserOrder(bean);//初始化，防止插入报空指针
					bean.setUserId(record.getUserId());
					addList.add(bean);
				}
			}
			//所需更新医院用户表的集合
			List<HospitalUserInfo> updHUList = getUpdHUList(physicalList);
			//添加或更新排序表
			int num1 = 1, num2 = 1, num3 = 1;
			if (ArrayUtils.isNotEmpty(addList)) {
				num1 = weightUserOrderMapper.insertBatch(addList);
			}
			if (ArrayUtils.isNotEmpty(updList)) {
				num2 = weightUserOrderMapper.updateBatch(updList);
			}
			//更新医院用户表体重状态字段
			if (ArrayUtils.isNotEmpty(updHUList)) {
				num3 = hospitalUserInfoMapper.updateBatch(updHUList);
			}
			//判断是否成功
			if (num1 < 1 || num2 < 1 || num3 < 1) {
				logger.info("------------同步用户信息到排序表失败");
			}
		} catch (Exception e) {
			logger.error("---------同步用户信息到排序表异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 对WeightUserOrder可能为空的属性赋初始值，防止插入时报错
	 * @createTime 2017年9月14日,下午2:15:44
	 * @createAuthor fangxilin
	 * @param obj
	 * @return
	 */
	private WeightUserOrder initUserOrder(WeightUserOrder obj) {
		if (obj.getCurrentWeight() == null) {
			obj.setCurrentWeight(0D);
		}
		if (obj.getLastWeightTime() == null) {
			Date dt = TimeUtils.stringFormatToDate("2000-01-01 00:00:00", Const.YYYYMMDD_HHMMSS);
			obj.setLastWeightTime(dt);
		}
		if (obj.getCurrentSugar() == null) {
			obj.setCurrentSugar(0D);
		}
		return obj;
	}
	
	/**
	 * 对user可能为空的属性赋初始值，防止插入时报错
	 * @createTime 2017-5-8,下午2:50:38
	 * @createAuthor fangxilin
	 * @param user
	 * @return
	 */
	private UserInfo initUser(UserInfo user) {
		Date dt = TimeUtils.stringFormatToDate("1900-01-01", Const.YYYYMMDD);
		Date expectedDate = (user.getExpectedDateOfConfinement() != null) ? user.getExpectedDateOfConfinement() : dt;
		user.setExpectedDateOfConfinement(expectedDate);
		double currentWeight = (user.getCurrentWeight() != null) ? user.getCurrentWeight() : 0D;
		user.setCurrentWeight(currentWeight);
		Date birthday = (user.getBirthday() != null) ? user.getBirthday() : dt;
		user.setBirthday(birthday);
		Integer height = (user.getHeight() != null) ? user.getHeight() : 0;
		user.setHeight(height);
		String realName = (user.getRealName() != null) ? user.getRealName() : "";
		user.setRealName(realName);
		double weight = (user.getWeight() != null) ? user.getWeight() : 0D;
		user.setWeight(weight);
		return user;
	}
	
	/**
	 * 获取所需更新医院用户表的集合
	 * @createTime 2017-7-18,下午6:45:17
	 * @createAuthor fangxilin
	 * @param userList
	 * @return
	 */
	private List<HospitalUserInfo> getUpdHUList(List<HospitalUserInfo> physicalList) {
		//体重营养系统医院用户信息表
		List<HospitalUserInfo> hUserList = hospitalUserInfoMapper.listAll();//门诊列表不为空，医院用户信息表必不可能为空
		Set<Integer> hIds = new HashSet<Integer>();
		for (HospitalUserInfo hUser : hUserList) {
			hIds.add(hUser.getHospitalId());
		}
		List<Integer> hospIds = new ArrayList<Integer>(hIds);
		//医院安全体重范围公式设置列表
		Map<Integer, Integer> safeMap = weightHospitalSettingService.listHospSafeFormula(hospIds);
		List<HospitalUserInfo> updHUList = new ArrayList<HospitalUserInfo>();
		//医院用户表检索
		HospitalUserInfo wei = null;
		for (HospitalUserInfo hU : hUserList) {
			for (HospitalUserInfo record : physicalList) {
				if (record.getUserId().equals(hU.getUserId())) {
					wei = record;
					break;
				}
			}
			//当前医院安全体重公式
			int type = (safeMap.get(hU.getHospitalId()) != null) ? safeMap.get(hU.getHospitalId()) : 0;//为空的设置的话默认公式1
			double bmi = FunctionUtils.getBMI(hU.getHeight(), hU.getWeight());//孕前bmi
			//获取上次称体重时对应的孕周
			int[] weiPweek = FunctionUtils.calPregnantWeek(wei.getLastWeightTime(), hU.getExpectedDate());
			double[] safeWeight = WeightFormula.getSafeWeight(bmi, hU.getWeight(), weiPweek[2], type, hU.getPregnantType());
			//获取上次秤体重状态
			int weightStatus = FunctionUtils.getStatusByWeight(safeWeight, wei.getCurrentWeight());
			if (hU.getWeightStatus() != weightStatus) {//不相等时需要更新
				HospitalUserInfo upd = new HospitalUserInfo();
				upd.setId(hU.getId());
				upd.setWeightStatus(weightStatus);
				updHUList.add(upd);
			}
		}
		return updHUList;
	}
}
