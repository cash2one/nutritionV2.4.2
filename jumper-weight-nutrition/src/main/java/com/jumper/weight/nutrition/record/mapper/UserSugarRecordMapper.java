package com.jumper.weight.nutrition.record.mapper;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.record.entity.UserSugarRecord;

public interface UserSugarRecordMapper extends BaseMapper<UserSugarRecord> {
    
	/**
	 * 查询用户最近一条血糖记录（按最晚的餐次查）
	 * @createTime 2017年9月13日,下午5:07:26
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	UserSugarRecord findUserLastSugar(int userId);
}