package com.yugyg.express;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
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
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		JSONArray list = api.getCompineCode();
		if(list.size()>0){
			for (Iterator<Object> tor=list.iterator();tor.hasNext();) {
				JSONObject job = (JSONObject)tor.next();
				if(job.get("com").equals(expCode)){
					expCode = job.get("cn").toString();
					break;
				}
			}
		}
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
		reponse.setCom(kdinaoMessage.getShipperCode());
		// 物流单号
		reponse.setNu(kdinaoMessage.getLogisticCode());
		// 查询状态
		reponse.setStatus(kdinaoMessage.getSuccess());
		// 物流状态 2-在途中,3-签收,4-问题件
		String gStatus = kdinaoMessage.getState();
		String status = "";
		switch (gStatus) {
		case "2":
			status = "在途中";
			break;
		case "3":
			status = "签收";
			break;
		case "4":
			status = "问题件";
			break;
		default:
			status = "unknown";
			break;
		}
		reponse.setState(status);
		//查询失败原因
		reponse.setMessage(kdinaoMessage.getReason());
		// 物流详细信息
		List<KdniaoInfo> traces = kdinaoMessage.getTraces();
		List<ExpressInfo> infos = new ArrayList<>();
		for (KdniaoInfo kdniaoInfo : traces) {
			ExpressInfo info = new ExpressInfo();
			info.setContext(kdniaoInfo.getAcceptStation());
			info.setFtime(kdniaoInfo.getAcceptTime());
			info.setTime(kdniaoInfo.getAcceptTime());
			info.setLocation(kdniaoInfo.getRemark());
			infos.add(info);
		}
		reponse.setData(infos);
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
