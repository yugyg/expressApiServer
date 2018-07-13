package com.yugyg;

import com.yugyg.express.KdniaoExpressApi;
import com.yugyg.message.ExpressRequest;
import com.yugyg.message.ExpressResponse;
/**
 *  物流网关中心
 * @author sunning
 *
 */
public class ExpressApiCenter {

      /**
       * 根据不同的策略来追踪物流
       * @param request
       * @return
       */
	  public static  ExpressResponse  traceExpress(ExpressRequest  request) {
			String expCode = request.getExpCode();
			String expNo = request.getExpNo();
			//选择具体公司
			//最终策略选择
			ExpressResponse response = new KdniaoExpressApi().traceExpNo(expCode, expNo);
			return response;
	  }
}
