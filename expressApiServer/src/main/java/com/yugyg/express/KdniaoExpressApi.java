package com.yugyg.express;

import com.yugyg.express.impl.KdniaoTrackQueryAPI;

public class KdniaoExpressApi {

	/**
	 * 转换公司编码
	 * 
	 * @param expCode
	 * @return
	 */
	private static String expCodeConvert(String expCode) {
		return expCode;
	}

	/**
	 * 
	 * @param expCode
	 * @param expNo
	 * @return
	 */
	public static String traceExpNo(String expCode, String expNo) {

		// 转换公司编码
		String kdnioExpCode = expCodeConvert(expCode);
		//
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		String result = null;
		try {
			result = api.getOrderTracesByJson("ANE", "210001633605");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}
}
