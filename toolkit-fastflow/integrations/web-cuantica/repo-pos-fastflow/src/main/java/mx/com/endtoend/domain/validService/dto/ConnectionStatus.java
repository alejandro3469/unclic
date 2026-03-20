package mx.com.endtoend.domain.validService.dto;

import java.util.List;

public class ConnectionStatus {

	private String companyCode;

	private List<ConnectionDBStatus> conectionStatus;

	public String getCompanyCode() {
		return companyCode;
	}

	public List<ConnectionDBStatus> getConectionStatus() {
		return conectionStatus;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setConectionStatus(List<ConnectionDBStatus> conectionStatus) {
		this.conectionStatus = conectionStatus;
	}

	@Override
	public String toString() {
		return "ConnectionStatus [companyCode=" + companyCode + ", conectionStatus=" + conectionStatus + "]";
	}

}
