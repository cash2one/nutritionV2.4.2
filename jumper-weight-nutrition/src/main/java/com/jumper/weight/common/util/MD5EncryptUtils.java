package com.jumper.weight.common.util;

import java.security.MessageDigest;
import java.util.Locale;

public class MD5EncryptUtils {


	/**
	 * MD5加密密文返回
	 * */
	public static String getMd5Value(String sSecret) {
		String result = null;
		try {
			MessageDigest bmd5 = MessageDigest.getInstance("MD5");
			bmd5.update(sSecret.getBytes());
			StringBuffer buf = new StringBuffer();
			try {
				result = byte2hex(bmd5.digest(buf.toString().getBytes("UTF-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 二进制转字符串
	 */
	private static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase(Locale.ENGLISH);
	}
	
	/**
	 * 密钥验证
	 */
	public static String  SmsPwdMd5(String plainText ) { 
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes()); 
			byte b[] = md.digest(); 
	
			int i; 
	
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
				i = b[offset]; 
				if(i<0) i+= 256; 
				if(i<16) 
				buf.append("0"); 
				buf.append(Integer.toHexString(i)); 
			} 
			return buf.toString();
		}
		catch (Exception e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
			return "";
		} 
	}
}
