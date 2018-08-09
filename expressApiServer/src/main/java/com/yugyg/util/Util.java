package com.yugyg.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}
