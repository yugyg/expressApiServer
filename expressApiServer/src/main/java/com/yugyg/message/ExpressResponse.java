package com.yugyg.message;

import java.util.ArrayList;
import java.util.List;

/**
 * 物流信息响应消息
 * 
 * @author sunning F
 */
public class ExpressResponse {

	private String com;// 公司编码
	private String nu;// 物流单号
	private String status;// 查询状态 200正常
	private String message;// 消息描述
	private String state;// 物流状态
	private List<ExpressInfo> data = new ArrayList<>();
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getNu() {
		return nu;
	}
	public void setNu(String nu) {
		this.nu = nu;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<ExpressInfo> getData() {
		return data;
	}
	public void setData(List<ExpressInfo> data) {
		this.data = data;
	}

	
}
