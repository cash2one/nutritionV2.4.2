package com.jumper.weight.nutrition.sport.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.jumper.weight.nutrition.record.entity.UserWeightRecord;
import com.jumper.weight.nutrition.record.mapper.UserWeightRecordMapper;
import com.jumper.weight.nutrition.sport.entity.SportInfo;
import com.jumper.weight.nutrition.sport.entity.UserSportRecord;
import com.jumper.weight.nutrition.sport.mapper.SportInfoMapper;
import com.jumper.weight.nutrition.sport.mapper.UserSportRecordMapper;
import com.jumper.weight.nutrition.sport.service.UserSportRecordService;
import com.jumper.weight.nutrition.sport.vo.VoDailySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoQuerySportRecord;
import com.jumper.weight.nutrition.sport.vo.VoUserSportRecord;

@Service
public class UserSportRecordServiceImpl extends BaseServiceImpl<UserSportRecord> implements UserSportRecordService {
	
	@Autowired
	private UserSportRecordMapper userSportRecordMapper;
	@Autowired
	private SportInfoMapper sportInfoMapper;
	@Autowired
	private UserWeightRecordMapper userWeightRecordMapper;

	@Override
	protected BaseMapper<UserSportRecord> getDao() {
		return userSportRecordMapper;
	}

	@Override
	public boolean addSportRecordByDate(int userId, String date, List<VoUserSportRecord> voList) {
		if (ArrayUtils.isEmpty(voList)) {
			return false;
		}
		List<Integer> sportIds = new ArrayList<Integer>();
		for (VoUserSportRecord vo : voList) {
			sportIds.add(vo.getSportId());
		}
		//用户当天的运动记录
		List<UserSportRecord> allList = userSportRecordMapper.listByDate(userId, date);
		//用户最近的一条体重记录
		UserWeightRecord weight = userWeightRecordMapper.findUserLastWeight(userId);
		double currWeight = weight.getAverageValue();
		//查询运动集合
		List<SportInfo> sportList = sportInfoMapper.listByIds(sportIds);
		SportInfo sport = null;
		List<UserSportRecord> addList = new ArrayList<UserSportRecord>();//添加操作
		List<UserSportRecord> updList = new ArrayList<UserSportRecord>();//更新操作
		Date dt = TimeUtils.stringFormatToDate(date, Const.YYYYMMDD);
		for (VoUserSportRecord vo : voList) {
			UserSportRecord record = new UserSportRecord();
			record.setUserId(userId);
			record.setAddDate(dt);
			//设置默认的开始结束时间为凌晨
			record.setStartTime(dt);
			record.setEndTime(dt);
			//分类添加和更新列表
			boolean isUpd = false, isAdd = true;
			for (UserSportRecord usr : allList) {
				if (vo.getSportId().intValue() == usr.getSportId() && vo.getTimeLength().doubleValue() != usr.getTimeLength()) {
					record.setId(usr.getId());//更新的对象
					isUpd = true;
					break;
				} else if (vo.getSportId().intValue() == usr.getSportId() && vo.getTimeLength().doubleValue() == usr.getTimeLength()) {
					isAdd = false;//添加的对象
					break;
				}
			}
			record.setSportId(vo.getSportId());
			record.setTimeLength(vo.getTimeLength());//分钟
			for (SportInfo sp : sportList) {
				if (vo.getSportId().intValue() == sp.getId()) {
					sport = sp;
					break;
				}
			}
			//设置运动名以及消耗的卡洛里
			record.setSportName(sport.getName());
			double calories = FunctionUtils.getSpoConsumeCal(currWeight, sport.getMet(), vo.getTimeLength());
			record.setCalories(calories);
			//record.setCalories(vo.getTimeLength() / 30 * sport.getCalorie());
			if (isUpd) {
				updList.add(record);			
			} else if (isAdd) {
				addList.add(record);
			}
		}
		try {
			if (ArrayUtils.isNotEmpty(addList)) {
				userSportRecordMapper.insertBatch(addList);
			}
			if (ArrayUtils.isNotEmpty(updList)) {
				userSportRecordMapper.updateBatch(updList);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public VoDailySportRecord listSportRecordByDate(int userId, String date) {
		List<UserSportRecord> recordList = userSportRecordMapper.listByDate(userId, date);
		if (ArrayUtils.isEmpty(recordList)) {
			return null;
		}
		
		List<Integer> sportIds = new ArrayList<Integer>();
		for (UserSportRecord usp : recordList) {
			sportIds.add(usp.getSportId());
		}
		//查询运动集合
		List<SportInfo> sportList = sportInfoMapper.listByIds(sportIds);
		
		VoDailySportRecord vo = new VoDailySportRecord();
		Double totalTime = 0D, totalCalorie = 0D;
		List<VoUserSportRecord> voList = new ArrayList<VoUserSportRecord>();
		SportInfo sport = null;
		for (UserSportRecord usp : recordList) {
			totalTime += usp.getTimeLength();
			totalCalorie += usp.getCalories();
			VoUserSportRecord voUsp = new VoUserSportRecord();
			voUsp.setId(usp.getId());
			voUsp.setSportId(usp.getSportId());
			voUsp.setSportName(usp.getSportName());
			voUsp.setTimeLength(usp.getTimeLength());
			voUsp.setCalories(FunctionUtils.setDecimal(usp.getCalories(), 1));
			for (SportInfo sp : sportList) {
				if (usp.getSportId().intValue() == sp.getId()) {
					sport = sp;
					break;
				}
			}
			voUsp.setCalorie(sport.getCalorie());//计算卡洛里时要用到（废弃）
			voUsp.setMet(sport.getMet());//计算卡洛里时要用到
			voList.add(voUsp);
		}
		vo.setSportDate(date);
		vo.setTotalTime(totalTime);
		vo.setTotalCalorie(FunctionUtils.setDecimal(totalCalorie, 1));
		vo.setRecordList(voList);
		return vo;
	}

	//@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<VoDailySportRecord> listSportRecordByDuring(VoQuerySportRecord voQuery) {
		PageHelper.startPage(voQuery.getPage(), voQuery.getLimit());
		List<String> dateList = userSportRecordMapper.listSportDate(voQuery);
		PageInfo pageInfo = new PageInfo(dateList);
		if (ArrayUtils.isEmpty(dateList)) {
			return pageInfo;
		}
		List<VoDailySportRecord> voList = new ArrayList<VoDailySportRecord>();
		for (String date : dateList) {
			VoDailySportRecord vo = listSportRecordByDate(voQuery.getUserId(), date);
			voList.add(vo);
		}
		pageInfo.setList(voList);
		return pageInfo;
	}

	@Override
	public List<VoDailySportRecord> listSportRecordByDates(int userId, List<String> dates) {
		List<VoDailySportRecord> voList = new ArrayList<VoDailySportRecord>();
		if (ArrayUtils.isEmpty(dates)) {
			return voList;
		}
		for (String date : dates) {
			VoDailySportRecord vo = listSportRecordByDate(userId, date);
			voList.add(vo);
		}
		return voList;
	}

}
