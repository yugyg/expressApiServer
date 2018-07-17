package com.yugyg.express.impl.kdniao;

import java.util.ArrayList;
import java.util.List;

public class KdniaoMessage {

	private String LogisticCode;
	private String ShipperCode;
	private String State;
	private String EBusinessID;
	private String Success;
	private String reason;

	private List<KdniaoInfo> Traces = new ArrayList<>();

	public String getLogisticCode() {
		return LogisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		LogisticCode = logisticCode;
	}

	public String getShipperCode() {
		return ShipperCode;
	}

	public void setShipperCode(String shipperCode) {
		ShipperCode = shipperCode;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getEBusinessID() {
		return EBusinessID;
	}

	public void setEBusinessID(String eBusinessID) {
		EBusinessID = eBusinessID;
	}

	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String success) {
		Success = success;
	}

	public List<KdniaoInfo> getTraces() {
		return Traces;
	}

	public void setTraces(List<KdniaoInfo> traces) {
		Traces = traces;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "KdniaoMessage [LogisticCode=" + LogisticCode + ", ShipperCode=" + ShipperCode + ", State=" + State
				+ ", EBusinessID=" + EBusinessID + ", Success=" + Success + ", reason=" + reason + ", Traces=" + Traces
				+ "]";
	}

}
