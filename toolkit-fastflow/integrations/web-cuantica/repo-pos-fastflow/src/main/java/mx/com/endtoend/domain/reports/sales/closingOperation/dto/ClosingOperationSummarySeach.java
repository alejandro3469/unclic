package mx.com.endtoend.domain.reports.sales.closingOperation.dto;

import java.util.Date;

public class ClosingOperationSummarySeach {

	private String branchCode;

	private Long openingId;

	private Long closingId;

	private Date closingDate;

	private String employeeEmail;

	public ClosingOperationSummarySeach(String branchCode, Long openingId, Long closingId, Date closingDate,
			String employeeEmail) {
		super();
		this.branchCode = branchCode;
		this.openingId = openingId;
		this.closingId = closingId;
		this.closingDate = closingDate;
		this.employeeEmail = employeeEmail;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Long getOpeningId() {
		return openingId;
	}

	public void setOpeningId(Long openingId) {
		this.openingId = openingId;
	}

	public Long getClosingId() {
		return closingId;
	}

	public void setClosingId(Long closingId) {
		this.closingId = closingId;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	@Override
	public String toString() {
		return "ClosingOperationSummarySeach [branchCode=" + branchCode + ", openingId=" + openingId + ", closingId="
				+ closingId + ", closingDate=" + closingDate + ", employeeEmail=" + employeeEmail + "]";
	}

}
