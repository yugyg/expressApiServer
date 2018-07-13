package com.yugyg.message;

public class ExpressInfo {

	private String expTime; //时间点
	private String expStation;//地点
	private String remark;//备注

	public String getExpTime() {
		return expTime;
	}

	public void setExpTime(String expTime) {
		this.expTime = expTime;
	}

	public String getExpStation() {
		return expStation;
	}

	public void setExpStation(String expStation) {
		this.expStation = expStation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "ExpressInfo [expTime=" + expTime + ", expStation=" + expStation + ", remark=" + remark + "]";
	}

}
