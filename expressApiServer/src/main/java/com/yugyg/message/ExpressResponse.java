package com.yugyg.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 物流信息响应消息
 * 
 * @author sunning F
 */
public class ExpressResponse {

	private String expCode;// 公司编码
	private String expNo;// 物流单号
	private String status;// 查询状态 true/false
	private String msg;// 消息描述
	private String expStatus;// 物流状态

	private List<ExpressInfo> traces = new ArrayList<>();

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getExpStatus() {
		return expStatus;
	}

	public void setExpStatus(String expStatus) {
		this.expStatus = expStatus;
	}

	public List<ExpressInfo> getTraces() {
		return traces;
	}

	public void setTraces(List<ExpressInfo> traces) {
		this.traces = traces;
	}

	@Override
	public String toString() {
		return "ExpressResponse [expCode=" + expCode + ", expNo=" + expNo + ", status=" + status + ", msg=" + msg
				+ ", expStatus=" + expStatus + ", traces=" + traces + "]";
	}

}
