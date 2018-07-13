package com.yugyg.message;

/**
 * 物流请求消息
 * 
 * @author sunning
 *
 */
public class ExpressRequest {

	String expCode;// 公司编码
	String expNo;// 物流单号

	public String getExpCode() {
		return expCode;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}

	public String getExpNo() {
		return expNo;
	}

	public void setExpNo(String expNo) {
		this.expNo = expNo;
	}

	@Override
	public String toString() {
		return "ExpressRequest [expCode=" + expCode + ", expNo=" + expNo + "]";
	}

}
