package com.jumper.weight.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
/**
 * 加解密工具类
 * @author Administrator
 *
 */
public class DesEncryptUtils {
	
	public static final String KeyStr = "SDFL#)@F";
	
	/** 用于加密传递参数据KEY **/
	public static String PARAMS_KEYS = "*JUMPER*";
	
	/**
	 * 解密
	 * @author Administrator 
	 * @date 2016-8-19 下午3:16:06
	 * @param message
	 * @return
	 */
	public static String decrypt(String message){
		try {
			return Decrypt(message,PARAMS_KEYS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 加密
	 * @author Administrator 
	 * @date 2016-8-19 下午3:16:24
	 * @param message
	 * @return
	 */
	public static String encrypt(String message){
		try {
			return Encrypt(message,PARAMS_KEYS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 3DES解密
	 * @param message
	 * @param key
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static String Decrypt(String message, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte,"UTF-8");
	}

	/**
	 * 3DES加密
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return toHexString(cipher.doFinal(message.getBytes("UTF-8"))).toUpperCase(Locale.ENGLISH);
	}

	/**
	 * 字符串转Byte数组
	 * @param ss
	 * @return
	 */
	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	/**
	 * Byte数组转字符串
	 * @param b
	 * @return
	 */
	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(DesEncryptUtils.encrypt("12102"));
		System.out.println(DesEncryptUtils.decrypt("D5FC8060936EC9EDAEACE846D2C2951201DCFC59F9048EA5EE484E009E2DC61B571EFE5E2CE6594EBD19D3321C1F21215F7BA122D4E150A543C7E8B6DD16D51C6458D760C475DDC89F011194524A90BD218BEBDABA0D0AB772620DDA92A67D88"));
	}
}
