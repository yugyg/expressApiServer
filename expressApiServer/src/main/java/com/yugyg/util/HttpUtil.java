package com.yugyg.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * HTTP请求工具类
 * 
 * @author sunning
 *
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static final OkHttpClient client = new OkHttpClient();
	private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("application/json; charset=utf-8");

	private static final HttpUtil instance =new HttpUtil();
	
	private HttpUtil() {
		
	}
	
	public static HttpUtil getInstance() {
		return instance;
	}

	/**
	 * 执行http post请求
	 * 
	 * @param postBody
	 * @param url
	 * @return
	 */
	public String httpExecute(String postBody, String url) {

		Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
				.build();
		try {
			Response response = client.newCall(request).execute();
			return response.body().toString();
		} catch (Exception e) {
			logger.debug(" 网络调用异常httpExecute : {}", e);
		}
		return null;
	}

	/**
	 * 转换成指定对象
	 * 
	 * @param postBody
	 * @param url
	 * @param clazz
	 * @return
	 */
	public Object httpExecute(String postBody, String url, Class clazz) {
		String rString = httpExecute(postBody, url);
		if (rString != null) {
			Object obj = JSONObject.parseObject(rString, clazz);
			return obj;
		}
		return null;

	}
	

	

}


