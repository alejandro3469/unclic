package mx.com.endtoend.domain.reports.cash.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClosingOperationReport {

	private String branchName;

	private Date creationDate;

	private Date closeDate;

	private String employeeName;

	private InputStream logo;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	private List<ClosingOperationReportDetail> detail;

	public ClosingOperationReport() {
		super();
	}

	public ClosingOperationReport(String branchName, Date creationDate, Date closeDate, String employeeName,
			BigDecimal totalAmount) {
		super();
		this.branchName = branchName;
		this.creationDate = creationDate;
		this.closeDate = closeDate;
		this.employeeName = employeeName;
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public String getBranchName() {
		return branchName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public InputStream getLogo() {
		return logo;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public List<ClosingOperationReportDetail> getDetail() {
		return detail;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public void setDetail(List<ClosingOperationReportDetail> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "ClosingOperationReport [branchName=" + branchName + ", creationDate=" + creationDate + ", closeDate="
				+ closeDate + ", employeeName=" + employeeName + ", logo=" + logo + ", totalAmount=" + totalAmount
				+ ", detail=" + detail + "]";
	}

}
