package mx.com.endtoend.domain.reports.sales.closingOperation.dto;

import java.util.Date;

public class ClosingOperationReportParamsDto {

	private String branchCode;

	private Date startDate;

	private Date endDate;

	private String format;

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "ClosingOperationReportParamsDto [branchCode=" + branchCode + ", startDate=" + startDate + ", endDate="
				+ endDate + ", format=" + format + "]";
	}

}
