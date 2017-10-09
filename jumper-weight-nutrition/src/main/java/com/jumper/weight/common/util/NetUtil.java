package com.jumper.weight.common.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 网络工具类
 * @author 秦晓伟
 * @version 1.0
 * @date 2013-4-15
 */
public class NetUtil {

	public static String ERROR = "-1";

	private static final Logger log = Logger.getLogger(NetUtil.class);

	/**
	 * 上传文件
	 * 
	 * @author 秦晓伟
	 * @version 1.0
	 * @date 2013-4-15
	 * @param path
	 * @param files
	 * @return
	 */
	public static String sendMultyPartRequest(String path, HashMap<String, String> params, HashMap<String, File> files) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		if (params.size() > 0) {
			Set<String> paramKeys = params.keySet();
			Iterator<String> paramIterator = paramKeys.iterator();
			while (paramIterator.hasNext()) {
				String key = paramIterator.next();
				try {
					StringBody stringBody = new StringBody(params.get(key), Charset.forName(HTTP.UTF_8));
					multipartEntity.addPart(key, stringBody);
				} catch (UnsupportedEncodingException e) {
					return ERROR;
				}
			}
		}
		if (files.size() > 0) {
			Set<String> fileKeys = files.keySet();
			Iterator<String> fileIterator = fileKeys.iterator();
			while (fileIterator.hasNext()) {
				String key = fileIterator.next();
				FileBody fileBody = new FileBody(files.get(key));
				multipartEntity.addPart(key, fileBody);
			}
		}
		httpPost.setEntity(multipartEntity);

		String result = null;
		try {
			HttpResponse response = httpClient.execute(httpPost);
			int statueCode = response.getStatusLine().getStatusCode();
			if (statueCode == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				result = ERROR;
			}
		} catch (Exception e) {
			result = ERROR;
			log.error(NetUtil.class, e);
			// e.printStackTrace();
		} finally {
			try {
				multipartEntity.consumeContent();
				multipartEntity = null;
				httpPost.abort();
			} catch (UnsupportedOperationException e) {
				log.error(NetUtil.class, e);
				// e.printStackTrace();
			} catch (IOException e) {
				log.error(NetUtil.class, e);
				// e.printStackTrace();
			}
		}
		return result;

	}

	/**
	 * POST 请求
	 * 
	 * @param path
	 * @param params
	 * @return
	 */
	public static String sendPost(String path, Map<String, String> params) {

		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		httpPost.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 300000);
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

		if (params.size() > 0) {
			Set<String> paramKeys = params.keySet();

			Iterator<String> paramIterator = paramKeys.iterator();

			while (paramIterator.hasNext()) {
				String key = paramIterator.next();
				String value = params.get(key);

				paramsList.add(new BasicNameValuePair(key, value));
			}
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(paramsList, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(httpPost);
			int statueCode = response.getStatusLine().getStatusCode();
			if (statueCode == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			} else {
				result = ERROR;
			}
		} catch (Exception e) {
			result = ERROR;
			log.error(NetUtil.class, e);
			// e.printStackTrace();
		} finally {
			httpPost.abort();
		}
		return result;
	}

	/**
	 * GET 请求
	 * 
	 * @param path
	 * @param params
	 * @return
	 */
	public static String sendGet(String path) {

		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(path);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			int statueCode = response.getStatusLine().getStatusCode();
			if (statueCode == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			} else {
				result = ERROR;
			}
		} catch (Exception e) {
			result = ERROR;
			log.error(NetUtil.class, e);
			// e.printStackTrace();
		} finally {
			httpGet.abort();
		}
		return result;
	}

	public static void main(String[] args) {
		HashMap<String, File> files = new HashMap<String, File>();
		files.put("fileData", new File("/Users/utoow/Desktop/1.jpg"));
		System.out.println(sendMultyPartRequest("http://127.0.0.1:8080/netFileTool/uploadFile.do", new HashMap<String, String>(), files));
		// System.out.println(sendMultyPartRequest("http://api.findkedo.com/fileserver/upload?path=2181283/123456.jpg",
		// new HashMap<String, String>(), files));

	}
}
