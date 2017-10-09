package com.jumper.weight.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作工具类
 * @Description TODO
 * @author fangxilin
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class FileUtils {
	public static String readStringFromStream(InputStream in) throws IOException {
	    /*   StringBuilder sb = new StringBuilder();
	       for (int i = in.read(); i != -1; i = in.read()) {
	           sb.append((char) i);
	       }
	       in.close();
	       return sb.toString();*/
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		in.close();
		return new String(data,"UTF-8");
	}
}
