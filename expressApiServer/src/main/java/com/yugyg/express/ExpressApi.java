
package com.yugyg.express;
import com.yugyg.message.ExpressResponse;

public interface ExpressApi {

	/**
	 * 转换成对应的公司编码
	 * @param expCode
	 * @return
	 */
	public  String expCodeConvert(String expCode) ;
	

	/**
	 * 调用快递鸟API查询物流信息,返回最终结果ExpressResponse
	 * 
	 * @param expCode
	 * @param expNo
	 * @return
	 */
	public ExpressResponse traceExpNo(String expCode, String expNo);
}
