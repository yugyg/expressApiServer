package com.yugyg.express.impl.juhe;

import java.util.ArrayList;
import java.util.List;

public class JuheMessage {
	private String resultcode;
	private String reason;

	private JuheInnerMessage result;

	class JuheInnerMessage {
		private String company;
		private String com;
		private String no;
		private String status;
		private List<JuheInfo> list = new ArrayList<JuheInfo>();

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<JuheInfo> getList() {
			return list;
		}

		public void setList(List<JuheInfo> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return "JuheInnerMessage [company=" + company + ", com=" + com + ", no=" + no + ", status=" + status
					+ ", list=" + list + "]";
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

	public JuheInnerMessage getResult() {
		return result;
	}

	public void setResult(JuheInnerMessage result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "JuheMessage [resultcode=" + resultcode + ", reason=" + reason + ", result=" + result + "]";
	}

}
