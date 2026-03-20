package mx.com.endtoend.domain.reports.sales.articles.dto;

import java.util.Date;

public class SaleReportArticleParamsDto {

	private String employeeEmail;

	private String branchCode;

	private Date startDate;

	private Date endDate;

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

	public String getBranchCode() {
		return branchCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
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

	@Override
	public String toString() {
		return "SaleReportArticleParamsDto [employeeEmail=" + employeeEmail + ", branchCode=" + branchCode
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", format=" + format + "]";
	}

}
