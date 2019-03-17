package com.yugyg.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class Util {
	// 时间格式转换为字符串格式
	public static String dateFormat(Date date, String format) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		String str = null;
		try {
			str = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}
	
	public static JSONObject getUriParams(String uri) {
	    if(uri == null || uri == "") {
	        return null;
	    }
	    if(uri.indexOf("?")>=0) {
	        uri = uri.split("\\?")[1];
	    }
	    String[] arr = uri.split("&");
	    if(arr.length<=1) {
	        return null;
	    }
	    JSONObject json = new JSONObject();
	    for(String str : arr) {
	        if(str.indexOf("=")>=0) {
	            String[] itemArr = str.split("=");
	            json.put(itemArr[0], itemArr[1]);
	        }
	    }
	    return json;
	}
	
}


