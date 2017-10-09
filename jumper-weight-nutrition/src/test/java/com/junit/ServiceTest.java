package com.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.jumper.common.service.sms.SMSService;
import com.jumper.common.utils.CommonReturnMsg;
import com.jumper.weight.common.util.Const;
import com.jumper.weight.nutrition.user.mapper.HospitalUserInfoMapper;
import com.jumper.weight.nutrition.user.service.UserInfoService;

public class ServiceTest extends JUnitDaoBase {
	
	@Autowired
	private HospitalUserInfoMapper hospitalUserInfoMapper;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SMSService sMSService;
	
	@Test
	@Rollback(false)
	public void examService() {
		try {
			//boolean ret = userInfoService.updateFindRollBack(8091, 6028);
			//logger.info("====================>"+user.toString());
			
			//VoUserInfo info = userInfoService.findVoUserByUId(11901, 42);
			
			List<Integer> userIds = new ArrayList<>();
			userIds.add(6028);
			userIds.add(11901);
			/*List<HospitalUserInfo> list = hospitalUserInfoMapper.listHospUserByUIdHospId(userIds, 42);*/
			
			/*List<VoUserInfo> list = userInfoService.listVoUserByUId(userIds, null);*/
			
			CommonReturnMsg retMsg = sMSService.sengSMSMsg("体重营养新建档", "18320995792", String.format(Const.NEW_BOOKBUILD, "18320995792", "19930606"));
			System.out.println("====================>" + String.format(Const.NEW_BOOKBUILD, "18320995792", "19930606"));
			System.out.println("====================>" + retMsg.getMsgbox());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
