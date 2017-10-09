/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jumper.weight.common.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.cglib.core.ReflectUtils;

public class HttpClient {
	protected static Logger logger = Logger.getLogger(HttpClient.class);
	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_JSON = 1;
	public static final String CONTENT_TYPE_DEFAULT = "application/x-www-form-urlencoded;charset=UTF-8";
	public static final String CONTENT_TYPE_JSON_ = "application/json;charset=UTF-8";
	Object params;
	String url;
	Map<String, String> headers;

	public HttpClient(String url) {
		this(url, null);
	}

	public HttpClient(String url, Object params) {
		this.headers = new HashMap<String, String>();
		headers.put("Content-Type", CONTENT_TYPE_DEFAULT);
		this.params = params;
		this.url = url;
		if (url == null)
			throw new NullPointerException("url");
	}
	
	public HttpClient(String url, Object params, int contentType) {
		this.headers = new HashMap<String, String>();
		if (contentType == TYPE_DEFAULT) {
			headers.put("Content-Type", CONTENT_TYPE_DEFAULT);
		} else {
			headers.put("Content-Type", CONTENT_TYPE_JSON_);
		}
		this.params = params;
		this.url = url;
		if (url == null)
			throw new NullPointerException("url");
	}

	private HttpURLConnection getUrlConnection(Method method)
			throws IOException {
		if (method == Method.GET) {
			String paramsStr = null;
			if ((this.params != null)
					&& ((paramsStr = getParamsStr(this.params)) != null)) {
				if (this.url.lastIndexOf("?") > 0)
					this.url = new StringBuilder().append(this.url).append("&")
							.append(paramsStr).toString();
				else {
					this.url = new StringBuilder().append(this.url).append("?")
							.append(paramsStr).toString();
				}
				logger.info(new StringBuilder().append("the url is ")
						.append(this.url).toString());
			}
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(this.url).openConnection();
		} catch (IOException e) {
			throw e;
		}
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(10000);
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		conn.setRequestProperty("contentEncoding", "UTF-8");
		conn.setRequestProperty("contentType", "utf-8");

		conn.setRequestMethod(method.toString());
		for (Map.Entry e : this.headers.entrySet()) {
			conn.addRequestProperty((String) e.getKey(), (String) e.getValue());
		}

		if (method == Method.POST) {
			conn.setDoInput(true);
		}
		return conn;
	}

	public String post() throws IOException {
		HttpURLConnection conn = null;
		try {
			conn = getUrlConnection(Method.POST);
			String cType = conn.getRequestProperty("Content-Type");
			conn.connect();
			if (this.params != null) {
				String paramsStr = null;
				if ((cType == null)
						|| ("application/x-www-form-urlencoded;charset=UTF-8"
								.contains(cType)))
					paramsStr = getParamsStr(this.params);
				else {
					JSONObject jsonObject = JSONObject.fromObject(this.params);
					paramsStr = jsonObject.toString();
				}
				logger.info(new StringBuilder().append("the params is ")
						.append(paramsStr).append(",url=").append(this.url)
						.toString());
				if (paramsStr != null) {
					conn.getOutputStream().write(paramsStr.getBytes("UTF-8"));
				}
			}
			int responseCode = conn.getResponseCode();
			if ((responseCode == 200) || (responseCode == 302)) {
				InputStream ins = conn.getInputStream();
				String str1 = FileUtils.readStringFromStream(ins);
				return str1;
			}
			logger.error(new StringBuilder().append("responseCode:")
					.append(conn.getResponseCode()).append(",url:")
					.append(this.url).toString());
			if (responseCode == 500) {
				InputStream ins = conn.getErrorStream();
				logger.error(FileUtils.readStringFromStream(ins));
			}
		} catch (IOException e) {
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	public String get() throws IOException {
		HttpURLConnection conn = null;
		InputStream ins = null;
		try {
			conn = getUrlConnection(Method.GET);
			conn.connect();
			int responseCode = conn.getResponseCode();
			if ((responseCode == 200) || (responseCode == 302)) {
				ins = conn.getInputStream();
				String str = FileUtils.readStringFromStream(ins);
				return str;
			}
			logger.error(new StringBuilder().append("responseCode:")
					.append(conn.getResponseCode()).append(",url:")
					.append(this.url).toString());
			if (responseCode == 500) {
				ins = conn.getErrorStream();
				logger.error(FileUtils.readStringFromStream(ins));
			}
		} catch (IOException e) {
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	public String getParamsStr(Object object) {
		if (object instanceof Map) {
			Map map = (Map) object;
			if (map.isEmpty()) {
				return null;
			}
			return joinMap(map);
		}
		return joinBean(object);
	}

	public String joinMap(Map<String, Object> map) {
		StringBuilder sBuilder = new StringBuilder();
		for (Iterator i$ = map.keySet().iterator(); i$.hasNext();) {
			Object key = i$.next();
			Object val = map.get(key);
			if (val != null) {
				sBuilder.append(key).append("=").append(val).append("&");
			}
		}
		if (sBuilder.length() > 0) {
			sBuilder.deleteCharAt(sBuilder.length() - 1);
		}
		return sBuilder.toString();
	}

	public String joinBean(Object bean) {
		StringBuilder sBuilder = new StringBuilder();
		PropertyDescriptor[] descriptors = ReflectUtils.getBeanProperties(bean
				.getClass());
		try {
			for (PropertyDescriptor descriptor : descriptors) {
				Object val = descriptor.getReadMethod().invoke(bean,
						new Object[0]);
				if (val != null) {
					sBuilder.append(descriptor.getName()).append("=")
							.append(val).append("&");
				}
			}
			if (sBuilder.length() > 0)
				sBuilder.deleteCharAt(sBuilder.length() - 1);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
		return sBuilder.toString();
	}

	public HttpClient addHeader(String property, String value) {
		this.headers.put(property, value);
		return this;
	}

	public static enum Method {
		POST, GET;
	}
}