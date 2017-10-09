package com.jumper.weight.nutrition.sport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jumper.weight.common.base.BaseMapper;
import com.jumper.weight.nutrition.sport.entity.UserSportRecord;
import com.jumper.weight.nutrition.sport.vo.VoQuerySportRecord;


public interface UserSportRecordMapper extends BaseMapper<UserSportRecord> {
	/**
	 * 删除用户某天的运动记录
	 * @createTime 2017-5-2,下午2:01:12
	 * @createAuthor fangxilin
	 * @param userId
	 * @param date
	 * @return
	 */
    boolean deleteByDate(@Param("userId") int userId, @Param("date") String date);
    
    /**
     * 查询用户某天的运动记录
     * @createTime 2017-5-2,下午3:27:28
     * @createAuthor fangxilin
     * @param userId
     * @param date
     * @return
     */
	List<UserSportRecord> listByDate(@Param("userId") int userId, @Param("date") String date);
	
	/**
	 * 按日期分组分页显示用户运动日期 
	 * @createTime 2017-5-4,下午3:42:08
	 * @createAuthor fangxilin
	 * @param voQuery
	 * @return
	 */
	List<String> listSportDate(VoQuerySportRecord voQuery);
	 
}