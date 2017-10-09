package com.jumper.weight.nutrition.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.common.base.BaseServiceImpl;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.common.util.FunctionUtils;
import com.jumper.weight.common.util.TimeUtils;
import com.jumper.weight.common.util.WeightFormula;
import com.jumper.weight.nutrition.hospital.entity.HospitalInfo;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.mapper.HospitalInfoMapper;
import com.jumper.weight.nutrition.hospital.mapper.WeightHospitalSettingMapper;
import com.jumper.weight.nutrition.record.service.UserWeightRecordService;
import com.jumper.weight.nutrition.record.vo.VoUserWeightRecord;
import com.jumper.weight.nutrition.report.entity.WeightReport;
import com.jumper.weight.nutrition.report.mapper.WeightReportMapper;
import com.jumper.weight.nutrition.report.service.WeightReportService;
import com.jumper.weight.nutrition.report.vo.VOWeightReport;
import com.jumper.weight.nutrition.user.entity.HospitalOutpatient;
import com.jumper.weight.nutrition.user.mapper.HospitalOutpatientMapper;
import com.jumper.weight.nutrition.user.service.UserInfoService;
import com.jumper.weight.nutrition.user.vo.VOUserMsg;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;
@Service
public class WeightReportServiceImpl extends BaseServiceImpl<WeightReport> implements WeightReportService{
	@Autowired
	private WeightReportMapper weightReportMapper;
	@Autowired
	private HospitalOutpatientMapper hospitalOutpatientMapper;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserWeightRecordService userWeightRecordService;
	@Autowired
	private HospitalInfoMapper hospitalInfoMapper;
	@Autowired
	private WeightHospitalSettingMapper weightHospitalSettingMapper;
	
	@Override
	protected BaseMapper<WeightReport> getDao() {
		return weightReportMapper;
	}

	/**
	 * 通过报告id查询报告信息
	 */
	@Override
	public VOWeightReport findUserWeightReport(int reportId) {
		WeightReport report = weightReportMapper.findById(reportId);
		if(report != null){
			VOWeightReport voWeightReport = getVOWeightReport(report);
			if(voWeightReport != null){
				return voWeightReport;
			}
		}
		return null;
	}
	
	/**
	 * 保存用户报告信息
	 */
	@Override
	public Integer saveUserWeightReport(WeightReport weightReport) {
		boolean b = false;
		//封装用户基本信息
		VoUserInfo voInfo = userInfoService.findVoUserByUId(weightReport.getUserId(),weightReport.getHospitalId());
		VoUserWeightRecord voUserWeightRecord = userWeightRecordService.findUserLastWeight(weightReport.getUserId(), weightReport.getHospitalId());
		VOUserMsg voUserMsg = getVOUserMsg(voInfo, voUserWeightRecord, weightReport.getHospitalId());
		if(voUserMsg != null){
			weightReport.setUserMsg(JSON.toJSONString(voUserMsg));
		}
		//医院名称
		HospitalInfo hospitalInfo = hospitalInfoMapper.findById(weightReport.getHospitalId());
		if(hospitalInfo != null && StringUtils.isNotEmpty(hospitalInfo.getName())){
			weightReport.setHospitalName(hospitalInfo.getName());
		}
		//添加时间
		weightReport.setAddTime(new Date());
		//报告编号
		String reportNumber = makeReportNumber(weightReport.getHospitalId());
		weightReport.setReportNumber(reportNumber);
		try {
			WeightReport weReport = weightReportMapper.findWeightReportByOutpatientId(weightReport.getOutpatientId());
			if(weReport != null){
				//存在即修改
				weightReport.setId(weReport.getId());
				weightReportMapper.update(weightReport);
			}else{
				//不存在即添加
				weightReportMapper.insert(weightReport);
			}
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(b){
			//返回用户id用于查询报告信息
			return weightReport.getId();
		}
		return null;
	}
	
	/**
	 * 生成报告编号
	 * @param hospitalId 医院id
	 * @return
	 */
	private String makeReportNumber(Integer hospitalId) {
		String number = hospitalId.toString();
		while(number.length()<5){
			StringBuffer str = new StringBuffer();
			str.append("0").append(number);
			number = str.toString();
		}
		if(number.length()>=6){
			number = number.substring(number.length()-5, number.length());
		}
        Date date = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");  
        number += sdf.format(date);  
        return number;
	}

	/**
	 * 封装报告记录
	 * @param report
	 * @return
	 */
	private VOWeightReport getVOWeightReport(WeightReport report) {
		if(report != null){
			VOWeightReport voWeightReport = new VOWeightReport();
			voWeightReport.setId(report.getId());
			voWeightReport.setUserId(report.getUserId());
			voWeightReport.setOutpatientId(report.getOutpatientId());
			voWeightReport.setHospitalId(report.getHospitalId());
			//添加时间
			voWeightReport.setAddTime(TimeUtils.dateFormatToString(report.getAddTime(), Const.YYYYMMDD));
			//报告编号
			if(StringUtils.isNotEmpty(report.getReportNumber())){
				voWeightReport.setReportNumber(report.getReportNumber());
			}
			HospitalOutpatient outpatient = hospitalOutpatientMapper.findById(report.getOutpatientId());
			if(outpatient != null){
				//饮食建议
				if(StringUtils.isNotEmpty(outpatient.getDietAdvice())){
					voWeightReport.setDietAdvice(outpatient.getDietAdvice());
				}
				//医生建议
				if(StringUtils.isNotEmpty(outpatient.getDoctorAdvice())){
					voWeightReport.setDoctorAdvice(outpatient.getDoctorAdvice());
				}
				//自定义运动
				if(StringUtils.isNotEmpty(outpatient.getUserDefinedSport())){
					voWeightReport.setUserDefinedSport(outpatient.getUserDefinedSport());
				}
			}
			//医院名称
			if(StringUtils.isNotEmpty(report.getHospitalName())){
				voWeightReport.setHospitalName(report.getHospitalName());
			}
			//用户基本信息
			VOUserMsg voUserMsg = new VOUserMsg();
			if(StringUtils.isNotEmpty(report.getUserMsg())){
				voUserMsg = JSON.parseObject(report.getUserMsg(), VOUserMsg.class);
				if(voUserMsg != null){
					voWeightReport.setVoUserMsg(voUserMsg);
				}
			}
			return voWeightReport;
		}
		return null;
	}
	

	/**
	 * 封装报告中的用户基本信息
	 * @param voInfo
	 * @param voUserWeightRecord
	 * @return
	 */
	private VOUserMsg getVOUserMsg(VoUserInfo voInfo, VoUserWeightRecord voUserWeightRecord, int hospitalId) {
		VOUserMsg voUserMsg = new VOUserMsg();
		if(voInfo != null){
			voUserMsg.setAge(voInfo.getAge());
			voUserMsg.setBeforeBMI(voInfo.getBmi());
			voUserMsg.setWeight(voInfo.getWeight());
			voUserMsg.setCurrentWeightAdd(FunctionUtils.setDecimal(voUserWeightRecord.getAverageValue()-voInfo.getWeight(),1));
			voUserMsg.setHeight(voInfo.getHeight());
			voUserMsg.setPregnantStage(voInfo.getPregnantStage());
			voUserMsg.setPregnantType(voInfo.getPregnantType());
			int[] week = voInfo.getPregnantWeek();
			String pregnantWeek = week[0]+"周";
			if(week[1] != 0){
				pregnantWeek += week[1]+"天";
			}
			voUserMsg.setPregnantWeek(pregnantWeek);
			voUserMsg.setRealName(voInfo.getRealName());
			//查询医院安全体重公式设置
			WeightHospitalSetting setting = weightHospitalSettingMapper.findSettingByHospId(hospitalId);
			int formula = (setting != null) ? setting.getSafeFormula() : 0; 
			double[] safeWeight = WeightFormula.getSafeWeight(voInfo.getBmi(), voInfo.getWeight(), 280, formula, voInfo.getPregnantType());
			double min = FunctionUtils.setDecimal(safeWeight[0]-voInfo.getWeight(), 1);
			double max = FunctionUtils.setDecimal(safeWeight[1]-voInfo.getWeight(), 1);
			voUserMsg.setSuggestWeightAdd(min+"~"+max);
			voUserMsg.setExpectedDate(voInfo.getExpectedDate());
		}
		if(voUserWeightRecord != null){
			voUserMsg.setCurrentWeight(voUserWeightRecord.getAverageValue());
			voUserMsg.setCurrentBMI(voUserWeightRecord.getBmi());
			voUserMsg.setBasalMetabolism(voUserWeightRecord.getBasalMetabolism());
			voUserMsg.setBodyFatRate(voUserWeightRecord.getBodyFatRate());
			voUserMsg.setMuscle(voUserWeightRecord.getMuscle());
			voUserMsg.setMoistureContent(voUserWeightRecord.getMoistureContent());
			voUserMsg.setAddWeightTime(voUserWeightRecord.getAddTime());
		}
		return voUserMsg;
	}

	/**
	 * 根据门诊id查询用户报告信息
	 */
	@Override
	public WeightReport findUserWeightReportByOutpId(int outpatientId) {
		WeightReport weightReport = weightReportMapper.findWeightReportByOutpatientId(outpatientId);
		if(weightReport != null){
			return weightReport;
		}
		return null;
	}
	
	
	
}
