package com.yugyg.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import okhttp3.Callback;
import okhttp3.Headers;
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
			logger.debug("网络调用异常httpExecute : {}", e);
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
	

    /**
     * @TODO OKHttp请求发送,callBack不为空则是异步请求，POST请求则bodyJson必填
     * 
     */
    public final static int OK_CONNECT_TIMEOUT = 3;
    public final static int OK_READ_TIMEOUT = 10;
    public final static int OK_WRITE_TIMEOUT = 3;
    public final static int OK_CALL_TIMEOUT = 0;
    public final static String OK_METHOD_POST = "POST";
    public final static String OK_METHOD_GET = "GET";
    public final static String OK_BODY_NULL = "{}";
    public static final MediaType OK_MEDIA_JSON
    = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType OK_MEDIA_FORM
    = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
    
    public static String okSend(String url, String bodyJson,MediaType mediaType,
        final String method,Map<String,String> headers,Callback callBack) {
        
        OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(OK_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(OK_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OK_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build();
        okhttp3.Request.Builder builder = new Request.Builder();
        builder = builder.url(url);
        if(headers != null && !headers.isEmpty()) {
            builder = builder.headers(Headers.of(headers));
        }
        if(OK_METHOD_POST.equals(method)) {
            if(bodyJson == null) {
                bodyJson = OK_BODY_NULL;
            }
            RequestBody body = RequestBody.create(mediaType, bodyJson);
            builder = builder.post(body);
        }else if(OK_METHOD_GET.equals(method)) {
            builder = builder.get();
        }else {
            return null;
        }
        Request request = builder.build();
        
        try {
            
            if(callBack !=null) {
                client.newCall(request).enqueue(callBack);
            }else {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
        } catch (IOException e) {
            logger.info("request params===url=={}",url);
            logger.error("okSend异常===",e);
        }
        
        return null;
    }

    /**
     * @TODO:     拼接url和params
     * @CreateTime:  2019年3月7日下午1:16:05 
     * @CreateAuthor: chenwei
     */
    public static String createUrlParams(String baseUrl,String params) {
        if(baseUrl.indexOf("?") >= 0) {
            return baseUrl + "&" + params;
        }else {
            return baseUrl + "?" + params;
        }
        
    }
    /**
     * @TODO:     参数map拼接成url字符串
     * @CreateTime:  2019年3月8日上午10:00:28 
     * @CreateAuthor: chenwei
     */
    public static String createParamsByMap(Map<String,String> paramsMap) {
        StringBuffer sb = new StringBuffer();
        String params = "";
        if(paramsMap !=null && !paramsMap.isEmpty()) {
            try {
                for (String name : paramsMap.keySet()) {
                    sb.append(name)
                    .append("=")
                    .append(java.net.URLEncoder.encode(
                        paramsMap.get(name), "UTF-8")).append("&");
                }
                params = sb.toString();
                params = params.substring(0, params.length() - 1);
            } catch (Exception e) {
                logger.info("sendPost异常====",e);
                return null;
            }
        }
        return params;
    }
    

}


