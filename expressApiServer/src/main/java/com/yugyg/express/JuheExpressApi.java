package com.yugyg.express;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yugyg.express.impl.JuheTrackQueryAPI;
import com.yugyg.express.impl.juhe.JuheInfo;
import com.yugyg.express.impl.juhe.JuheMessage;
import com.yugyg.message.ExpressInfo;
import com.yugyg.message.ExpressResponse;

public class JuheExpressApi implements ExpressApi {
	
	private static final Logger logger = LoggerFactory.getLogger(KdniaoExpressApi.class);
	
	@Override
	public String expCodeConvert(String expCode) {
		JuheTrackQueryAPI api = new JuheTrackQueryAPI();
		try {
			JSONArray list = api.getCompineCode();
			for (Iterator<Object> tor=list.iterator();tor.hasNext();) {
				JSONObject job = (JSONObject)tor.next();
				if(job.get("com").equals(expCode)){
					expCode = job.get("cn").toString();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expCode;
	}

	@Override
	public ExpressResponse traceExpNo(String expCode, String expNo) {
		// 转换公司编码
		String juheExpCode = expCodeConvert(expCode);
		JuheTrackQueryAPI api = new JuheTrackQueryAPI();
		ExpressResponse result = null;
		try {
			String expressResult = api.getOrderTracesByJson(juheExpCode, expNo);
			System.out.println("expressResult--"+expressResult);
			JuheMessage juheMessage = JSONObject.parseObject(expressResult, JuheMessage.class);
			result = convertExpressReponse(juheMessage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("traceExpNo  exception: {}",e);
		}
		return result;
	}
	
	/**
	 *	聚合结果转换为标准结果
	 * 
	 * @param 
	 * @return
	 */
	private static ExpressResponse convertExpressReponse(JuheMessage juheMessage) {
		ExpressResponse reponse = new ExpressResponse();
		// 查询状态
		if("200".equals(juheMessage.getResultcode())) {
			reponse.setStatus("true");
			// 快递公司编码
			reponse.setExpCode(juheMessage.getResult().getCom());
			// 物流单号
			reponse.setExpNo(juheMessage.getResult().getNo());
			// 物流状态
			reponse.setExpStatus("1".equals(juheMessage.getResult().getStatus())?"已签收":"包裹正在路上");
			// 物流详细信息
			List<JuheInfo> traces = juheMessage.getResult().getList();
			List<ExpressInfo> infos = new ArrayList<>();
			for (JuheInfo juheInfo : traces) {
				ExpressInfo info = new ExpressInfo();
				info.setExpStation(juheInfo.getZone());
				info.setExpTime(juheInfo.getDatetime());
				info.setRemark(juheInfo.getRemark());
				infos.add(info);
			}
			reponse.setTraces(infos);
		}else {
			reponse.setMsg("查询失败");
			reponse.setStatus("false");
		}
		return reponse;
	}
	
}
