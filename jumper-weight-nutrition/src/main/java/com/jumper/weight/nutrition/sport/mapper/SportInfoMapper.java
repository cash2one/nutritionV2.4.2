package com.jumper.weight.nutrition.sport.mapper;

import java.util.List;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.sport.entity.SportInfo;


public interface SportInfoMapper extends BaseMapper<SportInfo> {
	/**
	 * 通过运动名模糊查询
	 * @createTime 2017-4-27,下午6:10:10
	 * @createAuthor fangxilin
	 * @param query
	 * @return
	 */
	List<SportInfo> listSportsByName(String query);
}