package com.jumper.weight.nutrition.record.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.common.util.WeightFormula;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.mapper.WeightHospitalSettingMapper;
import com.jumper.weight.nutrition.record.entity.UserRecordsSets;
import com.jumper.weight.nutrition.record.entity.UserWeightRecord;
import com.jumper.weight.nutrition.record.mapper.UserRecordsSetsMapper;
import com.jumper.weight.nutrition.record.mapper.UserWeightRecordMapper;
import com.jumper.weight.nutrition.record.service.UserWeightRecordService;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.record.vo.VoWeightChart;
import com.jumper.weight.nutrition.report.service.WeightReportService;
import com.jumper.weight.nutrition.report.vo.VOWeightReport;
import com.jumper.weight.nutrition.user.entity.HospitalUserInfo;
import com.jumper.weight.nutrition.user.mapper.HospitalUserInfoMapper;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;

@Service
public class UserWeightRecordServiceImpl extends BaseServiceImpl<UserWeightRecord> implements UserWeightRecordService {
	
	@Autowired
	private UserWeightRecordMapper userWeightRecordMapper;
	@Autowired
	private UserRecordsSetsMapper userRecordsSetsMapper;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private WeightReportService weightReportService;
	@Autowired
	private WeightHospitalSettingMapper weightHospitalSettingMapper;
	@Autowired
	private HospitalUserInfoMapper hospitalUserInfoMapper;

	@Override
	protected BaseMapper<UserWeightRecord> getDao() {
		return userWeightRecordMapper;
	}

	@Override
	public boolean addOrUpdateWeightRecord(VoUserWeightRecord vo, int userId, int hospitalId) {
		UserWeightRecord record = new UserWeightRecord();
		VoUserInfo voUser = userInfoService.findVoUserByUId(userId, hospitalId);
		
		record.setUserId(userId);
		record.setAverageValue(vo.getAverageValue());
		double[] safeWeight = WeightFormula.getSafeWeight(voUser.getBmi(), voUser.getWeight(), voUser.getPregnantWeek()[2], 0, 0);
		int status = 1;//正常
		if (vo.getAverageValue() < safeWeight[0]) {
			status = 0;//偏低
		} else if (vo.getAverageValue() > safeWeight[1]) {
			status = 2;//偏高
		}
		record.setWeightState(status);
		record.setTestWeek(voUser.getPregnantWeek()[0]);
		record.setTestDay(voUser.getPregnantWeek()[1]);
		record.setTestTime(new Date());
		record.setAddTime(new Date());
		record.setCurrentIdentity(voUser.getCurrentIdentity());
		int basalMetabolism = (vo.getBasalMetabolism() != null) ? vo.getBasalMetabolism() : 0;
		double bodyFatRate = (vo.getBodyFatRate() != null) ? vo.getBodyFatRate() : 0D;
		double muscle = (vo.getMuscle() != null) ? vo.getMuscle() : 0D;
		double moistureContent = (vo.getMoistureContent() != null) ? vo.getMoistureContent() : 0D;
		record.setBasalMetabolism(basalMetabolism);
		record.setBodyFatRate(bodyFatRate);
		record.setMuscle(muscle);
		record.setMoistureContent(moistureContent);
		record.setHospitalId(hospitalId);
		
		String nowDate = TimeUtils.dateFormatToString(new Date(), Const.YYYYMMDD);
		UserWeightRecord info = userWeightRecordMapper.findWeightRecordByDate(nowDate, userId);
		try {
			if (info != null) {//更新
				record.setId(info.getId());
				userWeightRecordMapper.update(record);
			} else {//添加
				userWeightRecordMapper.insert(record);
			}
			
			// 监护表插入数据,配合app插入UserRecordsSets这张表一条数据
			UserRecordsSets sets = new UserRecordsSets();
			sets.setAddTime(new Date());
			sets.setData(vo.getAverageValue().toString());
			sets.setIsHandle(false);
			sets.setObjectId(record.getId());
			sets.setRecordState(-1);
			sets.setRecordType(5);//5：代表体重
			sets.setUserId(userId);
			userRecordsSetsMapper.insert(sets);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public VoUserWeightRecord findUserLastWeight(int userId, int hospitalId) {
		UserWeightRecord record = userWeightRecordMapper.findUserLastWeight(userId);
		if (record == null) {
			return null;
		}
		VoUserWeightRecord vo = new VoUserWeightRecord();
		vo.setAverageValue(record.getAverageValue());
		vo.setBasalMetabolism(record.getBasalMetabolism());
		vo.setBodyFatRate(record.getBodyFatRate());
		vo.setMoistureContent(record.getMoistureContent());
		vo.setMuscle(record.getMuscle());
//		VoUserInfo voUser = userInfoService.findVoUserByUId(userId, hospitalId);
		VoUserInfo voUser = userInfoService.findWeiTsUserByUId(userId, hospitalId);
		//设置当前BMI及状态
		Double bmi = FunctionUtils.getBMI(voUser.getHeight(), record.getAverageValue());
		vo.setBmi(bmi);
		vo.setWeightStatus(FunctionUtils.getStatusByBmi(bmi));
		vo.setAddTime(TimeUtils.dateFormatToString(record.getAddTime(), Const.YYYYMMDD));
		vo.setRecordId(record.getId());
		return vo;
	}

	@Override
	public List<VoUserWeightRecord> listUserLastWeight(List<Integer> userIds) {
		List<VoUserWeightRecord> voList = new ArrayList<VoUserWeightRecord>();
		if (ArrayUtils.isEmpty(userIds)) {
			return voList;
		}
		//列表查询未设置当前BMI及状态
		List<UserWeightRecord> listUserLastWeight = userWeightRecordMapper.listUserLastWeight(userIds);
		for (UserWeightRecord record : listUserLastWeight) {
			VoUserWeightRecord vo = new VoUserWeightRecord();
			vo.setUserId(record.getUserId());
			vo.setAverageValue(record.getAverageValue());
			vo.setAddTime(TimeUtils.dateFormatToString(record.getAddTime(), Const.YYYYMMDD));
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public VoWeightChart getWeightChartData(int userId, int type, int hospitalId) {
		VoWeightChart vo = new VoWeightChart();
		VoUserInfo voUser = userInfoService.findVoUserByUId(userId, hospitalId);
		vo.setExpectedDate(voUser.getExpectedDate());//设置预产期
		if (StringUtils.isEmpty(voUser.getExpectedDate())) {
			logger.info(String.format("-------userId : %s预产期为空！", userId));
			return vo;
		}
		//查询医院安全体重公式设置
		WeightHospitalSetting setting = weightHospitalSettingMapper.findSettingByHospId(hospitalId);
		int formula = (setting != null) ? setting.getSafeFormula() : 0; 
		List<Object[]> safeWeightList = new ArrayList<Object[]>();
		for (int i = 0; i <= 280; i++) {
			double[] safeWeight = WeightFormula.getSafeWeight(voUser.getBmi(), voUser.getWeight(), i, formula, voUser.getPregnantType());
			Object[] obj = {i, safeWeight[0], safeWeight[1]};
			safeWeightList.add(obj);
		}
		vo.setSafeWeightList(safeWeightList);//设置安全体重范围集合
		UserWeightRecord lastWeight = userWeightRecordMapper.findUserLastWeight(userId);
		double weightIncrease = (lastWeight != null) ? lastWeight.getAverageValue() - voUser.getWeight() : 0;
		double minSug = FunctionUtils.setDecimal((double)safeWeightList.get(280)[1] - voUser.getWeight(), 1);
		double maxSug = FunctionUtils.setDecimal((double)safeWeightList.get(280)[2] - voUser.getWeight(), 1);
		Double[] suggestIncrease = {minSug, maxSug};
		vo.setWeightIncrease(weightIncrease);//当前增重
		vo.setSuggestIncrease(suggestIncrease);
		
		Date expDate = TimeUtils.convertToDate(voUser.getExpectedDate());
		Date startDate = TimeUtils.getDateByDaysLate(-280, expDate);//0周0天的日期
		Date endDate = TimeUtils.getDateByDaysLate(21, expDate);//43周0天的日期
		Date now = new Date();
		if (type == 0) {//表示近30天
			//今天日期比43周0天的日期大时，endDate就为43周0天的日期，否则为今天
			endDate = (now.compareTo(endDate) == 1) ? endDate : now;
			startDate = TimeUtils.getDateByDaysLate(-29, endDate);
			int[] max = FunctionUtils.calPregnantWeek(endDate, expDate);//第30天对应的孕周
			int[] min = FunctionUtils.calPregnantWeek(startDate, expDate);//第1天对应的孕周
			Integer[] sevenPweek = {min[2], max[2]};
			vo.setSevenPweek(sevenPweek);
		}
		String startStr = TimeUtils.dateFormatToString(startDate, Const.YYYYMMDD);
		String endStr = TimeUtils.dateFormatToString(endDate, Const.YYYYMMDD);
		List<UserWeightRecord> weightList = userWeightRecordMapper.listWeightByDuring(userId, startStr, endStr);
		if (ArrayUtils.isEmpty(weightList)) {
			return vo;
		}
		List<Object[]> userWeightList = new ArrayList<Object[]>();
		for (UserWeightRecord record : weightList) {
			int[] pweek = FunctionUtils.calPregnantWeek(record.getAddTime(), expDate);
			Object[] obj = {pweek[2], record.getAverageValue()};
			userWeightList.add(obj);
		}
		vo.setUserWeightList(userWeightList);
		return vo;
	}

	@Override
	public VoWeightChart getRepWeiChartData(int reportId) {
		VOWeightReport voReport = weightReportService.findUserWeightReport(reportId);
		VoWeightChart vo = new VoWeightChart();
		
		String expectedDate = voReport.getVoUserMsg().getExpectedDate();
		Date expDate = TimeUtils.convertToDate(expectedDate);
		if (StringUtils.isEmpty(voReport.getVoUserMsg().getExpectedDate())) {//如果预产期为空，按孕周算出预产期
			//怀孕天数
			String pweek = voReport.getVoUserMsg().getPregnantWeek();
			int pday = Integer.valueOf(pweek.substring(0, pweek.indexOf("周"))) * 7;
			int day = (pweek.indexOf("天") != -1) ? Integer.valueOf(pweek.substring(pweek.indexOf("周") + 1, pweek.indexOf("天"))) : 0;
			pday += day;
			expDate = FunctionUtils.getExpDateByPday(TimeUtils.convertToDate(voReport.getAddTime()), pday);
			expectedDate = TimeUtils.dateFormatToString(expDate, Const.YYYYMMDD);
		}
		vo.setExpectedDate(expectedDate);
		//查询医院安全体重公式设置
		WeightHospitalSetting setting = weightHospitalSettingMapper.findSettingByHospId(voReport.getHospitalId());
		int formula = (setting != null) ? setting.getSafeFormula() : 0;
		//获取用户怀孕类型，如果为空默认为单胎
		int pregnantType = (voReport.getVoUserMsg().getPregnantType() != null) ? voReport.getVoUserMsg().getPregnantType() : 0;
		//安全体重范围集合点
		List<Object[]> safeWeightList = new ArrayList<Object[]>();
		for (int i = 0; i <= 280; i++) {
			double[] safeWeight = WeightFormula.getSafeWeight(voReport.getVoUserMsg().getBeforeBMI(), voReport.getVoUserMsg().getWeight(), i, formula, pregnantType);
			Object[] obj = {i, safeWeight[0], safeWeight[1]};
			safeWeightList.add(obj);
		}
		vo.setSafeWeightList(safeWeightList);//设置安全体重范围集合
		Date addWeiTime = TimeUtils.stringFormatToDate(voReport.getVoUserMsg().getAddWeightTime(), Const.YYYYMMDD);
		if (StringUtils.isEmpty(voReport.getVoUserMsg().getAddWeightTime())) {//如果称重时间为空，那么出报告日期作为称重时间
			addWeiTime = TimeUtils.stringFormatToDate(voReport.getAddTime(), Const.YYYYMMDD);
		}
		List<Object[]> userWeightList = new ArrayList<Object[]>();//体重集合点
		Date endDate = TimeUtils.getDateByDaysLate(21, expDate);//43周0天的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		if (addWeiTime.compareTo(cal.getTime()) <= 0) {//当称重时间在43周0天之前，才显示该点，否则不显示
			int[] weiPweek = FunctionUtils.calPregnantWeek(addWeiTime, expDate);
			Object[] obj = {weiPweek[2], voReport.getVoUserMsg().getCurrentWeight()};
			userWeightList.add(obj);
		}
		vo.setUserWeightList(userWeightList);
		return vo;
	}

}
