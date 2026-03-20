package mx.com.endtoend.domain.reports.sales.branchEmployee.dto;

import java.util.Date;

public class SaleAndNotesReportParamsDto {

	private String employeeEmail;

	private Long employeeNumber;

	private String branchCode;

	private Date startDate;

	private Date endDate;

	private String referenceCheck;

	private String referenceTransfer;

	private String format;

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public Long getEmployeeNumber() {
		return employeeNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getReferenceCheck() {
		return referenceCheck;
	}

	public String getReferenceTransfer() {
		return referenceTransfer;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public void setEmployeeNumber(Long employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setReferenceCheck(String referenceCheck) {
		this.referenceCheck = referenceCheck;
	}

	public void setReferenceTransfer(String referenceTransfer) {
		this.referenceTransfer = referenceTransfer;
	}

	@Override
	public String toString() {
		return "SaleReportBranchEmployeeParamsDto [employeeEmail=" + employeeEmail + ", employeeNumber="
				+ employeeNumber + ", branchCode=" + branchCode + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", referenceCheck=" + referenceCheck + ", referenceTransfer=" + referenceTransfer + ", format="
				+ format + "]";
	}

}
