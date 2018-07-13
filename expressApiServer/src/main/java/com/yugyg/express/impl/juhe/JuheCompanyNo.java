package com.yugyg.express.impl.juhe;

import java.util.ArrayList;
import java.util.List;

public class JuheCompanyNo {

	private String resultcode;
	private String reason;
	private List<JeheCompanyInfo> result = new ArrayList<>();

	public static class JeheCompanyInfo {
		private String com;
		private String no;

		public String getCom() {
			return com;
		}

		public void setCom(String com) {
			this.com = com;
		}

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		@Override
		public String toString() {
			return "JeheCompanyInfo [com=" + com + ", no=" + no + "]";
		}

	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<JeheCompanyInfo> getResult() {
		return result;
	}

	public void setResult(List<JeheCompanyInfo> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "JuheCompanyNo [resultcode=" + resultcode + ", reason=" + reason + ", result=" + result + "]";
	}

}
