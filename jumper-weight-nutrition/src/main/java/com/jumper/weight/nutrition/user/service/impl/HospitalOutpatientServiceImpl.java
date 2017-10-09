package com.jumper.weight.nutrition.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.ArrayUtils;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.common.util.WeightFormula;
import com.jumper.weight.nutrition.diet.mapper.DietSurveyMapper;
import com.jumper.weight.nutrition.hospital.entity.HospitalOutpatientReason;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.mapper.HospitalOutpatientReasonMapper;
import com.jumper.weight.nutrition.hospital.mapper.WeightHospitalSettingMapper;
import com.jumper.weight.nutrition.recipes.entity.UserRecipesPlan;
import com.jumper.weight.nutrition.recipes.mapper.UserRecipesPlanMapper;
import com.jumper.weight.nutrition.record.service.UserWeightRecordService;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.report.entity.WeightReport;
import com.jumper.weight.nutrition.report.mapper.WeightReportMapper;
import com.jumper.weight.nutrition.sport.mapper.SportSurveyMapper;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;
import com.jumper.weight.nutrition.user.service.HospitalOutpatientService;
import com.jumper.weight.nutrition.user.service.HospitalUserManageService;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VoHospitalOutpatient;
import com.jumper.weight.nutrition.user.vo.VoOutpChartSta;
import com.jumper.weight.nutrition.user.vo.VoOutpStatistics;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;

@Service
public class HospitalOutpatientServiceImpl extends BaseServiceImpl<HospitalOutpatient> implements HospitalOutpatientService {

	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;
	@Autowired
	private UserWeightRecordService userWeightRecordService;
	@Autowired 
	private UserInfoService userInfoService;
	@Autowired
	private UserRecipesPlanMapper userRecipesPlanMapper;
	@Autowired
	private WeightReportMapper weightReportMapper;
	@Autowired
	private HospitalUserManageService hospitalUserManageService;
	@Autowired
	private SportSurveyMapper sportSurveyMapper;
	@Autowired
	private DietSurveyMapper dietSurveyMapper;
	@Autowired
	private WeightHospitalSettingMapper weightHospitalSettingMapper;
	@Autowired
	private HospitalOutpatientReasonMapper hospitalOutpatientReasonMapper;
	
	@Override
	protected BaseMapper<HospitalOutpatient> getDao() {
		return hospitalOutpatientMapper;
	}

	@Override
	public PageInfo<VoHospitalOutpatient> listOutpatientUser(int hospitalId, String query, int page, int limit, int status) {
		List<VoHospitalOutpatient> returnList = new ArrayList<VoHospitalOutpatient>();
		PageHelper.startPage(page, limit);
		List<HospitalOutpatient> outpList = hospitalOutpatientMapper.listOutpatientByQuery(hospitalId, query, status);
		PageInfo pageInfo = new PageInfo(outpList);
		if (ArrayUtils.isEmpty(outpList)) {
			return pageInfo;
		}
		List<Integer> userIds = new ArrayList<Integer>();
		for (HospitalOutpatient outp : outpList) {
			userIds.add(outp.getUserId());
		}
		//查询用户信息集合
		List<VoUserInfo> voUserList = userInfoService.listVoUserByUId(userIds, hospitalId);
		//查询用户体重值集合
		List<VoUserWeightRecord> voWeightList = userWeightRecordService.listUserLastWeight(userIds);
		//封装
		VoUserInfo voUser = null;
		VoUserWeightRecord voWeight = new VoUserWeightRecord();
		//查询医院安全体重公式设置
		WeightHospitalSetting setting = weightHospitalSettingMapper.findSettingByHospId(hospitalId);
		int formula = (setting != null) ? setting.getSafeFormula() : 0; 
		for (HospitalOutpatient outp : outpList) {
			VoHospitalOutpatient vo = new VoHospitalOutpatient();
			vo.setId(outp.getId());
			vo.setOutpatientTime(TimeUtils.dateFormatToString(outp.getOutpatientTime(), Const.YYYYMMDD));
			vo.setStatus(outp.getStatus());
			//设置就诊原因文案
			HospitalOutpatientReason reason = hospitalOutpatientReasonMapper.findById(outp.getOutpatientReason());
			if (reason != null && StringUtils.isNotEmpty(reason.getOutpatientReason())) {
				vo.setOutpatientReason(reason.getOutpatientReason());
			} else {
				vo.setOutpatientReason("略");
			}
			for (VoUserInfo voU : voUserList) {
				if (outp.getUserId().intValue() == voU.getUserId()) {
					voUser = voU;
					break;
				}
			}
			for (VoUserWeightRecord voW : voWeightList) {
				if (outp.getUserId().intValue() == voW.getUserId()) {
					voWeight = voW;
					break;
				}
			}
			//设置当前BMI及状态
			if (voWeight != null && voUser != null) {
				Double bmi = FunctionUtils.getBMI(voUser.getHeight(), voWeight.getAverageValue());
				voWeight.setBmi(bmi);
				double[] safeWeight = WeightFormula.getSafeWeight(voUser.getBmi(), voUser.getWeight(), voUser.getPregnantWeek()[2], formula, voUser.getPregnantType());
				voWeight.setWeightStatus(FunctionUtils.getStatusByWeight(safeWeight, voWeight.getAverageValue()));
			}
			vo.setVoUserInfo(voUser);
			vo.setVoWeightRecord(voWeight);
			returnList.add(vo);
		}
		pageInfo.setList(returnList);
		return pageInfo;
	}

	@Override
	public boolean deleteOutpatient(List<Integer> idList) {
		if (ArrayUtils.isEmpty(idList)) {
			return true;
		}
		try {
			Integer ret = hospitalOutpatientMapper.deleteBatch(idList);
			if (ret > 0) {//同时为用户删除制定的方案，以及运动调查，膳食调查
				for (Integer id : idList) {
					userRecipesPlanMapper.deleteUserRecipesPlans(id);
					sportSurveyMapper.deleteByOutpId(id);
					dietSurveyMapper.deleteUserDietSurveyByOutpId(id);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public HospitalOutpatient findUserFirstOutpatient(int userId, int hospitalId) {
		return hospitalOutpatientMapper.findUserFirstOutpatient(hospitalId, userId);
	}

	@Override
	public int addOutpatient(int userId, int hospitalId, String userDefinedSport, int outpatientReason) {
		HospitalOutpatient outpatient = hospitalOutpatientMapper.findUserLastOutpatient(hospitalId, userId, null);
		HospitalOutpatient bean = new HospitalOutpatient();
		bean.setHospitalId(hospitalId);
		bean.setUserId(userId);
		bean.setOutpatientTime(new Date());
		bean.setIsFinish(0);//未完成诊断
		userDefinedSport = (StringUtils.isEmpty(userDefinedSport)) ? "" : userDefinedSport;
		bean.setUserDefinedSport(userDefinedSport);
		bean.setOutpatientReason(outpatientReason);
		if (outpatient == null) {
			//插入初诊记录
			bean.setStatus(0);
		} else if (outpatient.getIsFinish() == 1) {
			//插入复诊记录
			bean.setStatus(1);
		} else {
			//其他情况不做任何操作
			return outpatient.getId();
		}
		try {
			Integer num = hospitalOutpatientMapper.insert(bean);
			if (num < 1) {
				return 0;
			}
			return bean.getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public VoOutpStatistics getOutpStatistics(int hospitalId) {
		String startDate = TimeUtils.dateFormatToString(TimeUtils.getFirstDayOfMonth(new Date()), Const.YYYYMMDD);
		String endDate = TimeUtils.dateFormatToString(TimeUtils.getLastDayOfMonth(new Date()), Const.YYYYMMDD);
		String today = TimeUtils.dateFormatToString(new Date(), Const.YYYYMMDD);
		int dayFirstOutpNum = 0, monthFirstOutpNum = 0, dayOutpNum = 0, monthOutpNum = 0, dayRepeatNum = 0, monthRepeatNum = 0;
		List<VoOutpChartSta> allSta = getOutpChartSta(hospitalId, startDate, endDate);
		for (VoOutpChartSta sta : allSta) {
			monthFirstOutpNum += sta.getFirstOutpNum();
			monthOutpNum += sta.getOutpNum();
			monthRepeatNum += sta.getRepeatOutpNum();
			if (today.equals(sta.getDate())) {
				dayFirstOutpNum = sta.getFirstOutpNum();
				dayOutpNum = sta.getOutpNum();
				dayRepeatNum = sta.getRepeatOutpNum();
			}
		}
		VoOutpStatistics statis = new VoOutpStatistics(dayFirstOutpNum, monthFirstOutpNum, dayOutpNum, monthOutpNum, dayRepeatNum, monthRepeatNum);
		return statis;
	}

	@Override
	public List<VoHospitalOutpatient> listUserOutpatient(int hospitalId, int userId) {
		List<HospitalOutpatient> outpList = hospitalOutpatientMapper.listUserOutpatient(hospitalId, userId, 1);
		List<VoHospitalOutpatient> returnList = new ArrayList<VoHospitalOutpatient>();
		if (ArrayUtils.isEmpty(outpList)) {
			return returnList;
		}
		for (HospitalOutpatient outp : outpList) {
			VoHospitalOutpatient vo = new VoHospitalOutpatient();
			vo.setId(outp.getId());
			vo.setOutpatientTime(TimeUtils.dateFormatToString(outp.getOutpatientTime(), Const.YYYYMMDD));
			vo.setStatus(outp.getStatus());
			vo.setDoctorAdvice(outp.getDoctorAdvice());
			
			List<UserRecipesPlan> planList = userRecipesPlanMapper.findUserRecipesPlans(outp.getId());
			int isMakePlan = (ArrayUtils.isEmpty(planList)) ? 0 : 1;
			vo.setIsMakePlan(isMakePlan);
			if (outp.getStatus() == 0) {
				WeightReport report = weightReportMapper.findWeightReportByOutpatientId(outp.getId());
				Integer reportId = (report != null) ? report.getId() : null;
				vo.setReportId(reportId);
			}
			returnList.add(vo);
		}
		return returnList;
	}

	@Override
	public boolean updateOutp(HospitalOutpatient bean) {
		HospitalOutpatient outpatient = hospitalOutpatientMapper.findById(bean.getId());
		if (outpatient == null) {
			logger.info("----------没有该条门诊记录");
			return false;
		}
		int num = 0;
		try {
			num = hospitalOutpatientMapper.update(bean);
			if (num > 0 && bean.getIsFinish() == 1) {//完成诊断时需要更新孕妇管理表
				hospitalUserManageService.saveUserManage(outpatient.getHospitalId(), outpatient.getUserId());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public HospitalOutpatient findUserLastOutpatient(int hospitalId, int userId, Integer isFinish) {
		return hospitalOutpatientMapper.findUserLastOutpatient(hospitalId, userId, isFinish);
	}

	@Override
	public List<VoOutpChartSta> getOutpChartSta(int hospitalId, String startDate, String endDate) {
		List<HospitalOutpatient> outpList = hospitalOutpatientMapper.listOutPByDuring(hospitalId, startDate, endDate);
		List<Date> dateList = TimeUtils.getDatesBetweenTwoDate(TimeUtils.stringFormatToDate(startDate, Const.YYYYMMDD), TimeUtils.stringFormatToDate(endDate, Const.YYYYMMDD));
		List<VoOutpChartSta> returnList = new ArrayList<VoOutpChartSta>();
		VoOutpChartSta vo = null;
		for (Date date : dateList) {
			vo = new VoOutpChartSta();
			String dateStr = TimeUtils.dateFormatToString(date, Const.YYYYMMDD);
			vo.setDate(dateStr);
			int firstOutpNum = 0, outpTimes = 0;
			Set<Integer> repeatSet = new HashSet<Integer>();//复诊人数需去重
			for (HospitalOutpatient outp : outpList) {
				if (!TimeUtils.isSameDay(date, outp.getFinishTime())) {
					continue;
				}
				outpTimes++;
				if (outp.getStatus() == 0) {
					firstOutpNum++;
				} else if (outp.getStatus() == 1) {
					repeatSet.add(outp.getUserId());
				}
			}
			vo.setOutpTimes(outpTimes);
			vo.setFirstOutpNum(firstOutpNum);
			vo.setRepeatOutpNum(repeatSet.size());
			vo.setOutpNum(firstOutpNum + repeatSet.size());
			returnList.add(vo);
		}
		//计算累计人数或人次
		int addUpOutpNum = 0, addUpOutpTimes = 0;
		for (VoOutpChartSta chart : returnList) {
			addUpOutpNum += chart.getOutpNum();
			addUpOutpTimes += chart.getOutpTimes();
			chart.setAddUpOutpNum(addUpOutpNum);
			chart.setAddUpOutpTimes(addUpOutpTimes);
		}
		return returnList;
	}

	@Override
	public VoHospitalOutpatient findOutpById(int outpatientId) {
		HospitalOutpatient outp = hospitalOutpatientMapper.findById(outpatientId);
		if (outp == null) {
			return null;
		}
		VoHospitalOutpatient data = new VoHospitalOutpatient();
		data.setUserDefinedSport(outp.getUserDefinedSport());
		if (outp.getOutpatientReason() != null && outp.getOutpatientReason() != 0) {
			//设置就诊原因文案
			HospitalOutpatientReason reason = hospitalOutpatientReasonMapper.findById(outp.getOutpatientReason());
			data.setOutpatientReason(reason.getOutpatientReason());
		}
		return data;
	}
}
