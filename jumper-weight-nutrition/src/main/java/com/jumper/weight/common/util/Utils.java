package com.jumper.weight.common.util;

import java.io.File;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

/**
 * 工具类
 * @Description TODO
 * @author qinxiaowei
 * @date 2017-1-4
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Utils {
	
	/**
	 * 
	 * @version 1.0
	 * @createTime 2017-1-4,下午2:59:56
	 * @updateTime 2017-1-4,下午2:59:56
	 * @createAuthor qinxiaowei
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param file mp3文件路径
	 * @param durationFormat 时长格式 1：分:秒  2：时:分:秒
	 * @return
	 */
	public static String getDurationLength(File file, int durationFormat) {
		try {
			MP3File f = (MP3File)AudioFileIO.read(file);
			
			MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
			//mp3时长
			int seconds = audioHeader.getTrackLength();
			int temp=0;
			StringBuffer sb=new StringBuffer();
			if(durationFormat == 2) {
				//时
				temp = seconds/3600;
				sb.append((temp<10)?"0"+temp+":":""+temp+":");
			}
			//分
			temp=seconds%3600/60;
			sb.append((temp<10)?"0"+temp+":":""+temp+":");
			//秒
			temp=seconds%3600%60;
			sb.append((temp<10)?"0"+temp:""+temp);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
