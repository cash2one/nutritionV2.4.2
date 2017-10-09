package com.jumper.weight.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 基于google Zxing实现二维码生成
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-23
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class GenerateQRCode {
	
	private static final String FILE_DIR = System.getProperty("java.io.tmpdir");
	
	/**
	 * 生成二维码
	 * @param content
	 * @return
	 */
	public static String generate(String content) {
		String targetFileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
		String filePath = FILE_DIR + targetFileName;
        int width = 300; // 图像宽度  
        int height = 300; // 图像高度  
        String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix = null;
		try {
			// 生成矩阵  
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			Path path = FileSystems.getDefault().getPath(filePath);
		    MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
	}
	
	/**
	 * 生成二维码
	 * @param content
	 * @param outputStream
	 * @return void
	 */
	public static void generate(String content,OutputStream outputStream) throws Exception {
		String targetFileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
		String filePath = FILE_DIR + targetFileName;
        int width = 300; // 图像宽度  
        int height = 300; // 图像高度  
        String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix = null;
			// 生成矩阵  
		bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);

	}

}
