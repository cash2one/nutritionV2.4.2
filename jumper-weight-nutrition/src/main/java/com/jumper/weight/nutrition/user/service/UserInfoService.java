package com.jumper.weight.nutrition.user.service;

import java.util.List;

import com.jumper.weight.common.base.BaseService;
import com.jumper.weight.nutrition.user.entity.UserInfo;
import com.jumper.weight.nutrition.user.vo.VoUserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
	
	/**
	 * 通过userId或hospitalId查询VoUserInfo
	 * @createTime 2017-4-26,上午11:54:18
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId 当不需查询医院用户信息时可传null
	 * @return
	 */
	VoUserInfo findVoUserByUId(int userId, Integer hospitalId);
	
	/**
	 * 通过userIds集合或hospitalId查询VoUserInfo集合
	 * @createTime 2017-4-26,下午3:56:59
	 * @createAuthor fangxilin
	 * @param userIds
	 * @param hospitalId 当不需查询医院用户信息时可传null
	 * @return
	 */
	List<VoUserInfo> listVoUserByUId(List<Integer> userIds, Integer hospitalId);
	
	/**
	 * 通过手机号查询（his版）
	 * @createTime 2017-4-27,下午2:25:56
	 * @createAuthor fangxilin
	 * @param mobile
	 * @param hospitalId 当不需查询医院用户信息时可传null 
	 * @return
	 */
	VoUserInfo findVoUserByMobile(String mobile, Integer hospitalId);
	
	/**
	 * 通过手机号查询天使用户信息
	 * @createTime 2017-7-28,下午4:33:19
	 * @createAuthor fangxilin
	 * @param mobile
	 * @return
	 */
	VoUserInfo findTsUserByMobile(String mobile);
	
	/**
	 * 添加或更新用户信息
	 * @createTime 2017-4-26,下午5:45:09
	 * @createAuthor fangxilin
	 * @param vo
	 * @return 成功返回userId，失败返回0
	 */
	int addOrUpdteUser(VoUserInfo vo);
	
	/**
	 * 新增用户时，给用户默认的设置
	 * @createTime 2017-4-27,上午9:27:16
	 * @createAuthor fangxilin
	 * @param userId
	 * @return
	 */
	boolean insertDefaultHealthySet(int userId);
	
	/**
	 * 医院批量发送短信，短信头部为医院名称
	 * @createTime 2017-7-12,下午3:23:41
	 * @createAuthor fangxilin
	 * @param mobileList
	 * @param hospitalId
	 * @param content
	 * @return
	 */
	boolean sendMsgBatch(List<String> mobileList, int hospitalId, String content);
	
	/**
	 * 通过手机号查询业务用户和天使用户（业务用户数据优先）
	 * @createTime 2017-7-27,下午2:10:08
	 * @createAuthor fangxilin
	 * @param mobile
	 * @param hospitalId
	 * @return
	 */
	VoUserInfo findWeiTsUserByMob(String mobile, Integer hospitalId);
	
	/**
	 * 通过userId查询业务用户和天使用户（业务用户数据优先）
	 * @createTime 2017年9月15日,下午5:43:13
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	VoUserInfo findWeiTsUserByUId(int userId, Integer hospitalId);
}