package com.jumper.weight.nutrition.hospital.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jumper.weight.common.base.BaseController;
import com.jumper.weight.common.util.Consts;
import com.jumper.weight.common.util.GenerateQRCode;
import com.jumper.weight.common.util.ReturnMsg;
import com.jumper.weight.nutrition.hospital.entity.HospitalOutpatientReason;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalSetting;
import com.jumper.weight.nutrition.hospital.entity.WeightHospitalTemplate;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalSettingService;
import com.jumper.weight.nutrition.hospital.service.WeightHospitalTemplateService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/settings")
@Api(value = "/settings", description = "医院设置")
public class SettingsController extends BaseController {
	
	@Autowired
	private WeightHospitalSettingService weightHospitalSettingService;
	@Autowired
	private WeightHospitalTemplateService weightHospitalTemplateService;
	
	/**
	 * 生成二维码
	 * @createTime 2017-5-23,上午11:43:55
	 * @createAuthor fangxilin
	 * @param response
	 * @param hospitalId
	 * @throws IOException
	 */
	@RequestMapping("/v1.0/generateQRCode")
	public void generateQRCode(HttpServletResponse response,
			@RequestParam(value = "hospitalId") int hospitalId) throws IOException {
		//设置页面不缓存  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        //设置输出的内容的类型为png图像  
        response.setContentType("image/png");
        String baseUrl=Consts.BASE_PATH + "/mobile/checkPage.html";
        logger.debug("baseUrl:"+baseUrl);
        String encodeUrl = URLEncoder.encode(baseUrl, "UTF-8");
        encodeUrl.toLowerCase();
        logger.debug("encodeUrl:" + encodeUrl);
        //owneId=0，表示是天使用户
        String content = Consts.USER_PORTAL_URL + "?hospitalId=" + hospitalId + "&owneId=0&url=" + encodeUrl;
        logger.debug(content);
        String filePath = GenerateQRCode.generate(content);
        File file = new File(filePath);
        BufferedImage bufferedImage = ImageIO.read(file);
        //写给浏览器  
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
        //最后删除临时文件
        FileUtils.deleteQuietly(file);
	}
	
	@RequestMapping("/generateQRCode")
	public void getQRCode(HttpServletResponse response,
			@RequestParam(value = "hospitalId") int hospitalId) throws Exception {
		//设置页面不缓存  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        //设置输出的内容的类型为png图像  
        response.setContentType("image/png");
       /* String baseUrl=Consts.BASE_PATH + "/mobile/checkPage.html";
        logger.debug("baseUrl:"+baseUrl);
        String encodeUrl = URLEncoder.encode(baseUrl, "UTF-8");
        encodeUrl.toLowerCase();
        logger.debug("encodeUrl:"+encodeUrl);*/
        //owneId=0，表示是天使用户
        String content = Consts.BASE_PATH + "/mobile/login.html?hospitalId=" + hospitalId;
        logger.debug(content);
        GenerateQRCode.generate(content,response.getOutputStream());
	}
	
	/**
	 * 下载二维码
	 * @createTime 2017-5-23,下午12:02:07
	 * @createAuthor fangxilin
	 * @param response
	 * @param hospitalId
	 * @throws IOException
	 */
	@RequestMapping("/downloadQRCode")
	public void downloadQRCode(HttpServletResponse response,
			@RequestParam(value = "hospitalId") int hospitalId) throws IOException {
		String fileName = "二维码.png";
		String encodeUrl = URLEncoder.encode(Consts.BASE_PATH + "/mobile/checkPage.html", "UTF-8");
		//owneId=0，表示是天使用户
//        String content = Consts.USER_PORTAL_URL + "?owneId=0&hospitalId=" + hospitalId + "&url=" + encodeUrl;
		String content = Consts.BASE_PATH + "/mobile/login.html?hospitalId=" + hospitalId;
        String filePath = GenerateQRCode.generate(content);
        OutputStream outStream = null;
        InputStream inStream = null;
        try {
	    	inStream = new BufferedInputStream(new FileInputStream(filePath));
		    byte[] buffer = new byte[inStream.available()];
		    inStream.read(buffer);
		    response.reset();
		    //去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		    response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso-8859-1"));
		    outStream = new BufferedOutputStream(response.getOutputStream());
		    response.setContentType("application/octet-stream");
		    outStream.write(buffer);// 输出文件
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inStream, outStream);
			 /** 最后将临时文件删除 **/
        	File file = new File(filePath);
        	FileUtils.deleteQuietly(file);
		}
	}
	
	/**
	 * 获取医院设置
	 * @createTime 2017-6-22,上午10:34:38
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findSettingByHospId")
	@ApiOperation(value = "获取医院设置", httpMethod = "POST", response = ReturnMsg.class, notes = "获取医院设置", position = 1)
	public ReturnMsg findSettingByHospId(@ApiParam(value = "医院id") @RequestParam int hospitalId) {
		try {
			WeightHospitalSetting data = weightHospitalSettingService.findSettingByHospId(hospitalId);
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取医院设置成功", data);
		} catch (Exception e) {
			logger.error("获取医院设置异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取医院设置异常");
		}
	}
	
	/**
	 * 保存医院设置
	 * @createTime 2017-6-22,上午11:24:46
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param hideDiet
	 * @param hideSport
	 * @param hideFoodtab
	 * @param hideExchange
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveSetting")
	@ApiOperation(value = "保存医院设置", httpMethod = "POST", response = ReturnMsg.class, notes = "保存医院设置", position = 2)
	public ReturnMsg saveSetting(@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "隐藏膳食调查（0：否，1：是）") @RequestParam int hideDiet,
			@ApiParam(value = "隐藏运动调查（0：否，1：是）") @RequestParam int hideSport,
			@ApiParam(value = "隐藏食物表（0：否，1：是）") @RequestParam int hideFoodtab,
			@ApiParam(value = "隐藏交换份（0：否，1：是）") @RequestParam int hideExchange,
			@ApiParam(value = "开启营养咨询（0：否，1：是）") @RequestParam int nutritionConsult) {
		try {
			WeightHospitalSetting setting = new WeightHospitalSetting(hospitalId, hideDiet, hideSport, hideFoodtab, hideExchange, nutritionConsult);
			boolean ret = weightHospitalSettingService.saveSetting(setting);
			if (!ret) {
				return new ReturnMsg(ReturnMsg.FAIL, "保存医院设置失败");
			}
			return new ReturnMsg(ReturnMsg.SUCCESS, "保存医院设置成功");
		} catch (Exception e) {
			logger.error("保存医院设置异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "保存医院设置异常");
		}
	}
	
	/**
	 * 查询设置模板信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findHospitalTemplate")
	@ApiOperation(value = "获取医院模板设置", httpMethod = "POST", response = ReturnMsg.class, notes = "获取医院模板设置", position = 3)
	public ReturnMsg findHospitalTemplate(@ApiParam(value = "医院id") @RequestParam int hospitalId, 
			@ApiParam(value = "模板类型:0表示全部；1表示短信模板；2表示用户复诊就诊原因；3表示初诊用户扫码文案；4表示复诊用户扫码文案") @RequestParam int type) {
		try {
			List<WeightHospitalTemplate> data = weightHospitalSettingService.findHospitalTemplate(hospitalId, type);
			if(data != null && data.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取医院设置成功", data);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "医院设置列表为空", data);
			}
		} catch (Exception e) {
			logger.error("获取医院模板设置异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取医院模板设置异常");
		}
	}
	
	/**
	 * 保存模板信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveHospitalTemplate")
	@ApiOperation(value = "保存医院模板设置", httpMethod = "POST", response = ReturnMsg.class, notes = "保存医院模板设置", position = 4)
	public ReturnMsg saveHospitalTemplate(@ApiParam(value = "医院模板信息") @RequestBody WeightHospitalTemplate weightHospitalTemplate) {
		try {
			WeightHospitalTemplate template = weightHospitalSettingService.saveHospitalTemplate(weightHospitalTemplate);
			if(template != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "保存医院模板设置成功",template);
			}else{
				return new ReturnMsg(ReturnMsg.FAIL, "保存医院模板设置失败");
			}
		} catch (Exception e) {
			logger.error("保存医院设置异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "保存医院模板设置异常");
		}
	}
	
	/**
	 * 删除模板信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deleteHospitalTemplate")
	@ApiOperation(value = "删除医院模板设置", httpMethod = "POST", response = ReturnMsg.class, notes = "删除医院模板设置", position = 5)
	public ReturnMsg deleteHospitalTemplate(@ApiParam(value = "模板id") @RequestParam int templateId) {
		try {
			boolean b = weightHospitalSettingService.deleteHospitalTemplate(templateId);
			if(b){
				return new ReturnMsg(ReturnMsg.SUCCESS, "删除医院模板设置成功");
			}else{
				return new ReturnMsg(ReturnMsg.FAIL, "删除医院模板设置失败");
			}
		} catch (Exception e) {
			logger.error("删除医院模板设置异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "删除医院模板设置异常");
		}
	}
	
	/**
	 * 查询医院就诊原因列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findHospitalOutpatientReason")
	@ApiOperation(value = "获取医院就诊原因", httpMethod = "POST", response = ReturnMsg.class, notes = "获取医院就诊原因", position = 6)
	public ReturnMsg findHospitalOutpatientReason(@ApiParam(value = "医院id") @RequestParam int hospitalId, 
			@ApiParam(value = "门诊类型:0表示初诊；1表示复诊") @RequestParam int type) {
		try {
			List<HospitalOutpatientReason> data = weightHospitalSettingService.addOrFindHospitalOutpatientReason(hospitalId, type);
			if(data != null && data.size() > 0){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取医院就诊原因成功", data);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "医院就诊原因列表为空", data);
			}
		} catch (Exception e) {
			logger.error("获取医院就诊原因异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取医院就诊原因异常");
		}
	}
	
	/**
	 * 保存医院就诊原因
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveHospitalOutpatientReason")
	@ApiOperation(value = "保存医院就诊原因", httpMethod = "POST", response = ReturnMsg.class, notes = "保存医院就诊原因", position = 7)
	public ReturnMsg saveHospitalOutpatientReason(@ApiParam(value = "医院就诊原因") @RequestBody HospitalOutpatientReason hospitalOutpatientReason) {
		try {
			HospitalOutpatientReason reason = weightHospitalSettingService.saveHospitalOutpatientReason(hospitalOutpatientReason);
			if(reason != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "保存医院就诊原因成功",reason);
			}else{
				return new ReturnMsg(ReturnMsg.FAIL, "保存医院就诊原因失败");
			}
		} catch (Exception e) {
			logger.error("保存医院就诊原因异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "保存医院就诊原因异常");
		}
	}
	
	/**
	 * 通过门诊id查询就诊原因数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findHospitalOutpatientReasonByOutpId")
	@ApiOperation(value = "获取医院就诊原因", httpMethod = "POST", response = ReturnMsg.class, notes = "获取医院就诊原因", position = 8)
	public ReturnMsg findHospitalOutpatientReasonByOutpId(@ApiParam(value = "门诊id") @RequestParam int outpatientId) {
		try {
			HospitalOutpatientReason data = weightHospitalSettingService.findHospitalOutpatientReasonByOutpId(outpatientId);
			if(data != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取医院就诊原因成功", data);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "医院就诊原因列表为空", data);
			}
		} catch (Exception e) {
			logger.error("获取医院就诊原因异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取医院就诊原因异常");
		}
	}
	
	/**
	 * 通过就诊原因id查询就诊原因数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/findHospitalOutpatientReasonById")
	@ApiOperation(value = "根据就诊原因ID获取医院就诊原因", httpMethod = "POST", response = ReturnMsg.class, notes = "根据就诊原因ID获取医院就诊原因", position = 9)
	public ReturnMsg findHospitalOutpatientReasonById(@ApiParam(value = "就诊原因id") @RequestParam int outpReasonId) {
		try {
			HospitalOutpatientReason data = weightHospitalSettingService.findHospitalOutpatientReasonById(outpReasonId);
			if(data != null){
				return new ReturnMsg(ReturnMsg.SUCCESS, "获取医院就诊原因成功", data);
			}else{
				return new ReturnMsg(ReturnMsg.DATA_NULL, "医院就诊原因列表为空", data);
			}
		} catch (Exception e) {
			logger.error("获取医院就诊原因异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取医院就诊原因异常");
		}
	}
	
	/**
	 * 分页查询通知模板
	 * @createTime 2017年9月22日,下午5:04:41
	 * @createAuthor fangxilin
	 * @param hospitalId
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/listTemplateByPage")
	@ApiOperation(value = "分页查询通知模板", httpMethod = "POST", response = ReturnMsg.class, notes = "分页查询通知模板", position = 10)
	public ReturnMsg listTemplateByPage(@ApiParam(value = "医院id") @RequestParam int hospitalId,
			@ApiParam(value = "当前页") @RequestParam int page,
			@ApiParam(value = "每页大小") @RequestParam int limit) {
		try {
			PageInfo<WeightHospitalTemplate> data = weightHospitalTemplateService.listTemplateByPage(hospitalId, page, limit);
			return new ReturnMsg(ReturnMsg.SUCCESS, "分页查询通知模板成功", data);
		} catch (Exception e) {
			logger.error("分页查询通知模板异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "分页查询通知模板异常");
		}
	}
	
}
