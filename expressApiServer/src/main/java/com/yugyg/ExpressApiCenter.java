package com.yugyg;

import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yugyg.express.JuheExpressApi;
import com.yugyg.express.KdniaoExpressApi;
import com.yugyg.message.ExpressRequest;
import com.yugyg.message.ExpressResponse;
import com.yugyg.util.Constants;
import com.yugyg.util.ExcelUtil;
import com.yugyg.util.HttpUtil;
import com.yugyg.util.RedisUtil;
import com.yugyg.util.Util;
/**
 *  物流网关中心
 * @author sunning
 *
 */
public class ExpressApiCenter {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
      /**
       * 	根据不同的策略来追踪物流
       * @param request
       * @return
       */
	  public static  ExpressResponse  traceExpress(ExpressRequest  request) {
			String expCode = request.getExpCode();
			String expNo = request.getExpNo();
			//选择具体公司
			//最终策略选择
			logger.info("expCode--"+expCode);
			logger.info("expNo--"+expNo);
			ExpressResponse response = new ExpressResponse();
			response.setStatus("201");
			response.setCom(expCode);
			response.setNu(expNo);
			response.setMessage("查询失败");
			if (expCode == "" || expCode == null || expNo == "" || expNo == null ) {
				return response;
			}
			boolean isfind = false;
			String kdniaotimesdate = "";
			String kdniaotimes = "";
			try {
				kdniaotimesdate = RedisUtil.getJedisPara("kdniaotimesdate");
				if (!Util.dateFormat(new Date(), "yyyy-MM-dd").equals(kdniaotimesdate)) {
					RedisUtil.setJedisPara("kdniaotimesdate",Util.dateFormat(new Date(), "yyyy-MM-dd"));
					RedisUtil.setJedisPara("kdniaotimes","3000");
				}
				kdniaotimes = RedisUtil.getJedisPara("kdniaotimes");
			} catch (Exception e) {
				logger.info("redis出错啦啦啦啦啦啦啦啊---"+e.getMessage());
				kdniaotimes = "1";
			}
			boolean kdniao = false;
			if (kdniaotimes != "" && kdniaotimes != null && Long.parseLong(kdniaotimes) > 0) {
				//快递鸟
				JSONArray listkdniao = ExcelUtil.getCompanyCode(Constants.kdniao);
				if(listkdniao.size()>0){
					for (Iterator<Object> tor=listkdniao.iterator();tor.hasNext();) {
						JSONObject job = (JSONObject)tor.next();
						if(job.get("com").equals(expCode)){
							logger.info("kdniao search");
							kdniao = true;
							isfind = true;
							response = new KdniaoExpressApi().traceExpNo(expCode, expNo);
							try {
								RedisUtil.setJedisPara("kdniaotimes",(Long.valueOf(kdniaotimes)-1)+"");
							} catch (Exception e) {
								logger.info("redis出错啦啦啦啦啦啦啦啊----"+e.getMessage());
							}
							logger.info("kdniao rest time is "+ (Long.valueOf(kdniaotimes)-1)+"");
							if("false".equals(response.getStatus()) || "unknown".equals(response.getState())) {
								kdniao = false;
							}
							break;
						}
					}
				}
			}
			//聚合
			if (!kdniao) {
				JSONArray listjuhe = ExcelUtil.getCompanyCode(Constants.juhe);
				if(listjuhe.size()>0){
					for (Iterator<Object> tor=listjuhe.iterator();tor.hasNext();) {
						JSONObject job = (JSONObject)tor.next();
						if(job.get("com").equals(expCode)){
							logger.info("juhe search");
							isfind = true;
							response = new JuheExpressApi().traceExpNo(expCode, expNo);
							break;
						}
					}
				}
			}
			if (!isfind) {
				response.setMessage("不支持该物流");
				response.setStatus("202");
			}
			logger.info(response.toString());
			return response;
	  }
}
