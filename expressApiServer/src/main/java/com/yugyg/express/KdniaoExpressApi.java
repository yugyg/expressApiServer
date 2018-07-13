package com.yugyg.express;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yugyg.express.impl.KdniaoTrackQueryAPI;
import com.yugyg.express.impl.kdniao.KdniaoInfo;
import com.yugyg.express.impl.kdniao.KdniaoMessage;
import com.yugyg.message.ExpressInfo;
import com.yugyg.message.ExpressResponse;

public class KdniaoExpressApi  implements ExpressApi{

	private static final Logger logger = LoggerFactory.getLogger(KdniaoExpressApi.class);

	/**
	 * 转换公司编码
	 * 
	 * @param expCode
	 * @return
	 */
	public String expCodeConvert(String expCode) {
		return expCode;
	}

	/**
	 * 把快递鸟信息转换成标准的快递接口信息
	 * 
	 * @param kdinaoMessage
	 * @return
	 */
	private static ExpressResponse convertExpressReponse(KdniaoMessage kdinaoMessage) {
		ExpressResponse reponse = new ExpressResponse();
		// 快递公司编码
		reponse.setExpCode(kdinaoMessage.getLogisticCode());
		// 物流单号
		reponse.setExpNo(kdinaoMessage.getShipperCode());
		// 查询状态
		reponse.setStatus(kdinaoMessage.getSuccess());
		// 物流状态
		reponse.setExpStatus(kdinaoMessage.getState());

		// 物流详细信息
		List<KdniaoInfo> traces = kdinaoMessage.getTraces();
		List<ExpressInfo> infos = new ArrayList<>();
		for (KdniaoInfo kdniaoInfo : traces) {
			ExpressInfo info = new ExpressInfo();
			info.setExpStation(kdniaoInfo.getAcceptStation());
			info.setExpTime(info.getExpTime());
			info.setRemark(kdniaoInfo.getRemark());
			infos.add(info);
		}
		reponse.setTraces(infos);
		return reponse;
	}

	/**
	 * 调用快递鸟API查询物流信息,返回最终结果ExpressResponse
	 * 
	 * @param expCode
	 * @param expNo
	 * @return
	 */
	public  ExpressResponse traceExpNo(String expCode, String expNo) {

		// 转换公司编码
		String kdnioExpCode = expCodeConvert(expCode);
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		ExpressResponse result = null;
		try {
			String expressResult = api.getOrderTracesByJson(kdnioExpCode, expNo);

			KdniaoMessage kdniaoMessage = JSONObject.parseObject(expressResult, KdniaoMessage.class);
			result = convertExpressReponse(kdniaoMessage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("traceExpNo  exception: {}",e);
		}
		return result;

	}
}
