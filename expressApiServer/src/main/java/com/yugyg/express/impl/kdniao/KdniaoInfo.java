package com.yugyg.express.impl.kdniao;

public class KdniaoInfo {

	private String AcceptStation;
	private String AcceptTime;
	private String Remark;

	public String getAcceptStation() {
		return AcceptStation;
	}

	public void setAcceptStation(String acceptStation) {
		AcceptStation = acceptStation;
	}

	public String getAcceptTime() {
		return AcceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		AcceptTime = acceptTime;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	@Override
	public String toString() {
		return "KdniaoInfo [AcceptStation=" + AcceptStation + ", AcceptTime=" + AcceptTime + ", Remark=" + Remark + "]";
	}

}
