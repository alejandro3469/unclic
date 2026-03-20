package mx.com.endtoend.domain.reports.cash.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OpeningOperationReport {

	private InputStream logo;

	private Date creationDate;

	private String branchName;

	private String employeeName;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	private List<OpeningOperationReportDetail> detail;

	public OpeningOperationReport() {
		super();
	}

	public OpeningOperationReport(Date creationDate, String branchName, String employeeName, BigDecimal totalAmount) {
		super();
		this.creationDate = creationDate;
		this.branchName = branchName;
		this.employeeName = employeeName;
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public InputStream getLogo() {
		return logo;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public List<OpeningOperationReportDetail> getDetail() {
		return detail;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public void setDetail(List<OpeningOperationReportDetail> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "OpeningOperationReport [logo=" + logo + ", creationDate=" + creationDate + ", branchName=" + branchName
				+ ", employeeName=" + employeeName + ", totalAmount=" + totalAmount + ", detail=" + detail + "]";
	}

}
